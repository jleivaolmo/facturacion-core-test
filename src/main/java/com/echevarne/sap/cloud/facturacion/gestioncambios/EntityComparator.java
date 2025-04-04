package com.echevarne.sap.cloud.facturacion.gestioncambios;

import static com.echevarne.sap.cloud.facturacion.reflection.ObjectReflectionUtil.OBJECT_REFLECTION_UTIL;
import static com.echevarne.sap.cloud.facturacion.services.impl.GestionCambiosServiceImpl.PACKAGE_PREFIX;
import static org.apache.commons.lang3.ClassUtils.getCanonicalName;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.math.BigDecimal;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Index;
import javax.persistence.Table;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.ObjectUtils;
import com.echevarne.sap.cloud.facturacion.model.BasicEntity;
import com.echevarne.sap.cloud.facturacion.model.GestionCambioData.Action;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.extern.slf4j.Slf4j;

/**
 * Class for the {@link EntityComparator}.
 *
 * <p>
 * Compare 2 objects and store the differences in the result attribute
 * </p>
 * <p>
 * This is the implementation of the differences
 * </p>
 * TODO: agregar mas informacion de lo que hace esta clase.
 *
 * @author Steven Ricardo Mendez Brenes
 * @version 1.0
 * @since 06/24/2019
 *
 */
@Slf4j
public class EntityComparator {
	public static final Set<String> IGNORE_FIELDS = new HashSet<>();
	public static final String INTERNAL_FIELDS = "$";

	static {
		IGNORE_FIELDS.add("id");
		IGNORE_FIELDS.add("entityCreationTimestamp");
		IGNORE_FIELDS.add("entityVersion");
		IGNORE_FIELDS.add("lastUpdatedTimestamp");
		IGNORE_FIELDS.add("userCreate");
		IGNORE_FIELDS.add("userUpdate");
	}


	private final Map<Class<?>, Set<String>> classIdMap = new HashMap<>();
	private final Date date = new Date();
	private final EntityComparatorResult result;

	@SuppressWarnings("rawtypes")
	public EntityComparator(Class clazz) {
		super();
		this.result = new EntityComparatorResult(clazz.getCanonicalName(), this.date);

		setClassIds(clazz);
	}

	public void compare(Object entityOld, Object entityNew) {
		compare(entityOld, entityNew, Action.UPDATE);
	}

	/**
	 * For this method it is important to say that we are using reflections in order
	 * to know what are the difference among them because of we have to stablish
	 * difference to control versions
	 */
	public void compare(final Object objOld, final Object objNew, Action changeFieldAction) {
		final Class<?> clazz = getClassWithFallback(objOld, objNew);

		if (clazz != null && clazz.getCanonicalName().startsWith(PACKAGE_PREFIX)) {
			String objOldCanonicalName = getCanonicalName(objOld, null);
			String objNewCanonicalName = getCanonicalName(objNew, null);

			if (objOldCanonicalName != null && objNewCanonicalName != null && !StringUtils.equals(objOldCanonicalName, objNewCanonicalName)) {
				throw new RuntimeException("Objects to compare need to be of the same type: "
						+ objOldCanonicalName + " " + objNewCanonicalName);
			}

			Set<Field> fields = OBJECT_REFLECTION_UTIL.getAllFields(clazz);
			for (Field field : fields) {
				final String fieldName = field.getName();
				if (!IGNORE_FIELDS.contains(fieldName) &&
						field.getAnnotation(JsonBackReference.class) == null &&
						field.getAnnotation(JsonIgnore.class) == null &&
						hasGetter(clazz, field))
				{
					Object valueOld = null;
					if (objOld != null) {
						try {
							valueOld = OBJECT_REFLECTION_UTIL.getFieldValueFromObject(objOld, fieldName);
						} catch (NoSuchFieldException | InvocationTargetException | IllegalAccessException e) {
							throw new RuntimeException("Could not read old value [Class: " + objOld.getClass() + ", FieldName: " + fieldName +"]", e);
						}
					}

					Object valueNew = null;
					if (objNew != null) {
						try {
							valueNew = OBJECT_REFLECTION_UTIL.getFieldValueFromObject(objNew, fieldName);
						} catch (NoSuchFieldException | InvocationTargetException | IllegalAccessException e) {
							throw new RuntimeException("Could not read old value [Class: " + objNew.getClass() + ", FieldName: " + fieldName +"]", e);
						}
					}

					if (valueNew instanceof String || valueNew instanceof Integer || valueNew instanceof Boolean) {
						if (!valueNew.equals(valueOld))
							this.esDistinto(valueOld, valueNew, changeFieldAction, field, objOld, objNew, clazz);
					} else if (valueNew instanceof BigDecimal) {
						BigDecimal valueNewBd = (BigDecimal) valueNew;
						BigDecimal valueOldBd = (BigDecimal) valueOld;
						if (valueOldBd == null || valueNewBd.compareTo(valueOldBd) != 0)
							this.esDistinto(valueOld, valueNew, changeFieldAction, field, objOld, objNew, clazz);
					} else if (valueNew instanceof Time) {
						if (valueOld == null || (((Time) valueNew).compareTo((Time) valueOld) != 0))
							this.esDistinto(valueOld, valueNew, changeFieldAction, field, objOld, objNew, clazz);
					} else if (valueNew instanceof Date) {
						Date valueOldTs = (Date) valueOld;
						Date valueNewDate = (Date) valueNew;
						if (valueOldTs == null || (valueOldTs.getTime() != valueNewDate.getTime()))
							this.esDistinto(valueOld, valueNew, changeFieldAction, field, objOld, objNew, clazz);
					} else {
						if (valueNew != valueOld)
							this.esDistinto(valueOld, valueNew, changeFieldAction, field, objOld, objNew, clazz);
					}
				}
			}
		}
	}

	private void esDistinto(Object valueOld, Object valueNew, Action changeFieldAction, Field field, Object objOld,
			Object objNew, Class<?> clazz) {
		final Class<?> fieldClass = getClassWithFallback(valueOld, valueNew);

		if (fieldClass.getCanonicalName().startsWith(PACKAGE_PREFIX)) {
			compare(valueOld, valueNew, changeFieldAction);
		} else if (Set.class.isAssignableFrom(fieldClass)) {
			if (!setClassIds(field)) {
				log.warn("Can not compare list's items without unique-index for class " + setGenericClass(field));
			} else {
				compareSetValuesAdapter((Set<?>) valueOld, (Set<?>) valueNew);
			}
		} else if (BasicEntity.class.isAssignableFrom(fieldClass)) {
			final String idStr = getBasicEntityIdStrWithFallback(valueOld, valueNew);
			EntityComparatorItem diff = new EntityComparatorItem(idStr, clazz.getCanonicalName(), field.getName(),
					EntityComparator.valueOf(valueOld), EntityComparator.valueOf(valueNew), changeFieldAction);

			this.result.add(diff);
		} else if (Date.class.isAssignableFrom(fieldClass)) {
			Long valueOldTime = getTimeOrNull((Date) valueOld);
			Long valueNewTime = getTimeOrNull((Date) valueNew);
			if (!ObjectUtils.nullSafeEquals(valueOldTime, valueNewTime)) {
				final String idStr = getBasicEntityIdStrWithFallback(objOld, objNew);
				EntityComparatorItem diff = new EntityComparatorItem(idStr, clazz.getCanonicalName(), field.getName(),
						EntityComparator.valueOf(valueOld), EntityComparator.valueOf(valueNew), changeFieldAction);

				this.result.add(diff);
			}

		} else if (!Objects.equals(valueOld, valueNew)) {
			final String idStr = getBasicEntityIdStrWithFallback(objOld, objNew);
			EntityComparatorItem diff = new EntityComparatorItem(idStr, clazz.getCanonicalName(), field.getName(),
					EntityComparator.valueOf(valueOld), EntityComparator.valueOf(valueNew), changeFieldAction);

			this.result.add(diff);
		}
	}

	private Long getTimeOrNull(Date date) {
		Long time = null;
		if (date != null) {
			time = date.getTime();
		}

		return time;
	}

	private String getBasicEntityIdStrWithFallback(Object valueOld, Object valueNew) {
		return this.getBasicEntityIdWithFallback(valueOld, valueNew).toString();
	}

	private List<Object> getBasicEntityIdWithFallback(Object obj1, Object obj2) {
		List<Object> id;

		final Class<?> clazz = getClassWithFallback(obj1, obj2);
		final List<String> idFields = EntityMapUtil.getIds(clazz);
		if (obj1 != null) {
			id = EntityMapUtil.getKey(idFields, obj1);
		} else if(obj2 != null) {
			id = EntityMapUtil.getKey(idFields, obj2);
		} else {
			id = new ArrayList<>();
		}

		return id;
	}

	private boolean hasGetter(Class<?> clazz, Field field) {
		return OBJECT_REFLECTION_UTIL.hasGetter(clazz, field.getName());
	}

	private Class<?> getClassWithFallback(Object object1, Object object2) {
		Class<?> clazz = null;
		if (object1 != null) {
			clazz = object1.getClass();
		} else if(object2 != null) {
			clazz = object2.getClass();
		}

		return clazz;
	}

	private <T> void compareSetValuesAdapter(Set<T> setA, Set<?> setB) {
		// Force mapping to T
		compareSetValues(setA, (Set<T>)setB);
	}

	/**
	 * Comparator method to compare values among Set collections
	 */
	private <T> void compareSetValues(Set<T> setA, Set<T> setB) {

		Class<T> clazz = null;

		if (setA == null) {
			setA = new HashSet<>();
		}

		if (setB == null) {
			setB = new HashSet<>();
		}

		if (setA.size() > 0) {
			clazz = (Class<T>)setA.iterator().next().getClass();
		} else if (setB.size() > 0) {
			clazz = (Class<T>)setB.iterator().next().getClass();
		}

		if (clazz != null) {
			SetComparator sc = new SetComparator();
			SetComparatorResults<T> scResults = sc.compare(clazz, setA, setB);

			Set<T> deletedSet = scResults.getDeletedSet();
			Set<T> newSet = scResults.getNewSet();
			Set<T> modifiedSet = scResults.getModifiedSet();

			// Inactive modified or deleted items
			setInactiveItems(newSet, false);
			setInactiveItems(modifiedSet, false);
			setInactiveItems(deletedSet, true);

			// to remove values of the set and create again
			for(T deletedItem: deletedSet){
				compare(deletedItem, null, Action.DELETE);
			}
			for(T newItem: newSet){
				compare(null, newItem, Action.INSERT);
			}

			Map<String, Object[]> modifiedMap = getModifiedMap(modifiedSet, setA, setB);
			for (Object[] pair : modifiedMap.values()) {
				compare(pair[0], pair[1], Action.UPDATE);
			}
		}
	}

	@SuppressWarnings("rawtypes")
	private void setInactiveItems(Set set, boolean inactive) {
		for (Object item : set) {
			try {
				OBJECT_REFLECTION_UTIL.setFieldValueFromObject(item, "inactive", (field) -> inactive);
			} catch (IllegalAccessException | InvocationTargetException | NoSuchFieldException e) {
				throw new RuntimeException("Could not mark the object as inactive [Class: " + item.getClass().getSimpleName() + "]");
			}
			inactivateChilds(item, inactive);
		}
	}


	private void inactivateChilds(Object item, boolean inactive) {
		Field[] fieldsParent = item.getClass().getSuperclass().getDeclaredFields();
		Field[] fields = item.getClass().getDeclaredFields();
		List<Field> allFields = new ArrayList<>(Arrays.asList(fields));
		allFields.addAll(Arrays.asList(fieldsParent));
		for (Field field : allFields) {
			if (!Modifier.isStatic(field.getModifiers()) &&
					!field.getName().startsWith(INTERNAL_FIELDS) &&
					field.getType() == Set.class) {
				final Set<?> set;
				try {
					set = (Set<?>) OBJECT_REFLECTION_UTIL.getFieldValueFromObject(item, field.getName());
				} catch (NoSuchFieldException | InvocationTargetException | IllegalAccessException e) {
					throw new RuntimeException("Couldn't read the field value from the field. [FieldName: " + field.getName()+ "]", e);
				}
				if(!CollectionUtils.isEmpty(set)){
					setInactiveItems(set, inactive);
				}
			}
		}
	}

	/**
	 * Copy values between sets since we have different values
	 */
	private Map<String, Object[]> getModifiedMap(Set<?> modifiedSet, Set<?> setA, Set<?> setB) {
		Map<String, Object[]> map = new HashMap<>();

		Set<String> keySet = new HashSet<>();

		for (Object obj : modifiedSet) {
			String key = getPk(obj);
			keySet.add(key);
		}

		for (Object obj : setA) {
			String key = getPk(obj);
			if (keySet.contains(key)) {
				Object[] pair = new Object[2];
				pair[0] = obj;
				map.put(key, pair);
			}
		}

		for (Object obj : setB) {
			String key = getPk(obj);
			if (keySet.contains(key)) {
				if (!map.containsKey(key)) {
					map.put(key, new Object[2]);
				}
				Object[] pair = map.get(key);
				pair[1] = obj;
			}
		}

		return map;
	}

	/**
	 * @param obj to get ID
	 */
	private String getPk(Object obj) {

		StringBuilder sb = new StringBuilder();

		Class<?> clazz = obj.getClass();

		Set<String> ids = this.classIdMap.get(clazz);
		for (String id : ids) {
			final Object fieldValue;
			try {
				fieldValue = OBJECT_REFLECTION_UTIL.getFieldValueFromObject(obj, id);
			} catch (NoSuchFieldException | InvocationTargetException | IllegalAccessException e) {
				throw new RuntimeException("Could not read PK from object [Field: " + id + ", Class: " + clazz.getSimpleName() + "]", e);
			}
			final String value = String.valueOf(fieldValue);
			sb.append(value).append(":");
		}
		return sb.toString();
	}

	private Class<?> setGenericClass(Field f) {
		ParameterizedType stringListType = (ParameterizedType) f.getGenericType();
		return (Class<?>) stringListType.getActualTypeArguments()[0];
	}

	/**
	 * @param f
	 * @return
	 */
	private boolean setClassIds(Field f) {
		return setClassIds(setGenericClass(f));
	}

	/**
	 * Agrega un Set con campos de clave de la clase ecibida como parametro en el
	 * mapa classIdMap con el nombre de la clase como clave. Siempre devuelve true.
	 * Porque ???
	 *
	 * @param clazz la clase a la que se le buscan los ids y se los guarda en el
	 *              mapa.
	 * @return true siempre
	 */
	private boolean setClassIds(Class clazz) {

		if (this.classIdMap.containsKey(clazz)) {
			return true;
		}
		Set<String> ids = new LinkedHashSet<>();
		Table table = (Table) clazz.getDeclaredAnnotation(Table.class);
		if (table != null) {
			for (Index index : table.indexes()) {
				if (index.unique()) {
					int count = 0;
					for (String id : index.columnList().split(",")) {
						count++;
						if (count == 1)
							continue; // first field is assumed to be parent's relational id
						ids.add(id);
					}
				}
			}
		}

		if (ids.size() == 0) {
			Field[] fields = clazz.getDeclaredFields();
			for (Field f : fields) {
				Column column = f.getAnnotation(Column.class);
				if (column != null && column.unique()) {
					ids.add(f.getName());
					break;
				}
			}
		}

		if (ids.size() == 0) {
			ids.add("id");
		}

		if (ids.size() > 0) {
			this.classIdMap.put(clazz, ids);
		}

		return true;
	}

	private static String valueOf(final Object pValue) {
		if (pValue != null)
			return String.valueOf(pValue);

		return null;
	}

	public EntityComparatorResult getResult() {
		return this.result;
	}

	public Date getDate() {
		return date;
	}
}
