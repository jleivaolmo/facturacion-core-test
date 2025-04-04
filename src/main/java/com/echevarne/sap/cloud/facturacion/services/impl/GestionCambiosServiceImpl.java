package com.echevarne.sap.cloud.facturacion.services.impl;

import static com.echevarne.sap.cloud.facturacion.reflection.ObjectReflectionUtil.OBJECT_REFLECTION_UTIL;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import javax.persistence.EntityManager;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.echevarne.sap.cloud.facturacion.gestioncambios.EntityComparator;
import com.echevarne.sap.cloud.facturacion.gestioncambios.EntityComparatorItem;
import com.echevarne.sap.cloud.facturacion.gestioncambios.EntityComparatorResult;
import com.echevarne.sap.cloud.facturacion.gestioncambios.EntityMapUtil;
import com.echevarne.sap.cloud.facturacion.gestioncambios.SetComparator;
import com.echevarne.sap.cloud.facturacion.gestioncambios.SetComparatorResults;
import com.echevarne.sap.cloud.facturacion.gestionestados.util.EstadosUtils;
import com.echevarne.sap.cloud.facturacion.model.BasicEntity;
import com.echevarne.sap.cloud.facturacion.model.GestionCambio;
import com.echevarne.sap.cloud.facturacion.model.GestionCambioData;
import com.echevarne.sap.cloud.facturacion.model.entities.ConfiguracionFieldStatus;
import com.echevarne.sap.cloud.facturacion.model.masterdata.MasDataEstadosGrupo;
import com.echevarne.sap.cloud.facturacion.model.solicitudrecibida.SetComponent;
import com.echevarne.sap.cloud.facturacion.model.solicitudrecibida.SolicitudMuestreo;
import com.echevarne.sap.cloud.facturacion.repositories.GestionCambiosRep;
import com.echevarne.sap.cloud.facturacion.services.ConfiguracionFieldStatusService;
import com.echevarne.sap.cloud.facturacion.services.GestionCambioService;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.extern.slf4j.Slf4j;

/**
 * Implementation for the service{@link GestionCambiosServiceImpl}.
 *
 * <p>
 * This is a class for Services. . .
 * </p>
 *
 * @author Steven Ricardo Mendez Brenes
 * @version 1.0
 * @since 06/24/2019
 *
 */
@Slf4j
@Service("gestionCambiosSrv")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class GestionCambiosServiceImpl extends CrudServiceImpl<GestionCambio, Long> implements GestionCambioService {

	public static final String PACKAGE_PREFIX = "com.echevarne.sap.cloud.facturacion.model.";
	public static final String KEY_FIELDS = "id|serialVersionUID|entityCreationTimestamp|lastUpdatedTimestamp|entityVersion";
	public static final String INTERNAL_FIELDS = "$";

	private final ConfiguracionFieldStatusService configuracionFieldStatusSrv;

	private final EntityManager entityManager;

	private final GestionCambiosRep gestionCambiosRep;


	public GestionCambiosServiceImpl(ConfiguracionFieldStatusService configuracionFieldStatusService, final EntityManager entityManager, GestionCambiosRep gestionCambiosRep) {
		super(gestionCambiosRep);
		this.configuracionFieldStatusSrv = configuracionFieldStatusService;
		this.entityManager = entityManager;
		this.gestionCambiosRep = gestionCambiosRep;
	}

	public Optional<GestionCambio> findByEntityId(Long entityId) {
		return gestionCambiosRep.findByEntityId(entityId);
	}

	/**
	 * Compare all the differences at the same time update set, and generates set
	 * new in the case is needed
	 */
	@Override
	public EntityComparatorResult compare(Object entityOld, Object entityNew) {
		EntityComparatorResult result = null;
		try {
			EntityComparator ec = new EntityComparator(entityOld.getClass());
			ec.compare(entityOld, entityNew);
			result = ec.getResult();
		} catch (Exception ex) {
			log.error("Ops!", ex);
		}
		return result;
	}

	/**
	 * Main method to process arrives of the solitud muestreo, compare values,
	 * generate history and management of the elements within the collections
	 */
	@Override
	public SolicitudMuestreo process(SolicitudMuestreo newSolicitudMuestreo, SolicitudMuestreo oldSolicitudMuestreo)
			throws Exception {
		EntityComparatorResult diff = compare(oldSolicitudMuestreo, newSolicitudMuestreo);
		if (diff == null || diff.getDiffs().size() <= 0)
			return oldSolicitudMuestreo;
		GestionCambio gestionCambio = createGestionCambio(oldSolicitudMuestreo, diff);
		gestionCambio = this.create(gestionCambio);
		log.debug("GestionCambio solicitudes con id {}", gestionCambio.getId());
		MasDataEstadosGrupo estado = EstadosUtils.getGrupoEstadoActual(oldSolicitudMuestreo.getPeticionesActivas().get(0).getTrazabilidad());
		Map<String, ConfiguracionFieldStatus> camposNoPermitidos = new HashMap<>();
		camposNoPermitidos = getAllowedChanges(estado, diff.getDiffs());
		updateNewValues(oldSolicitudMuestreo, newSolicitudMuestreo, Arrays.asList("serialVersionUID","userUpdate","userCreate"), diff, estado, camposNoPermitidos);
		return oldSolicitudMuestreo;
	}

	private Map<String, ConfiguracionFieldStatus> getAllowedChanges(MasDataEstadosGrupo state,
			List<EntityComparatorItem> diffs) {
		Map<String, ConfiguracionFieldStatus> result = new HashMap<>();

		Optional<List<ConfiguracionFieldStatus>> configurationFields = configuracionFieldStatusSrv.findAllActives();
		if (configurationFields.isPresent() && Objects.nonNull(state)) {
			Optional<ConfiguracionFieldStatus> elemOpt;
			for (EntityComparatorItem item : diffs) {
				final String name = getClassName(item.getClassName());
				elemOpt = configurationFields.get().stream().filter(cf -> name.equals(cf.getEntityFieldName().getEntity().getNombreEntidad())
						&& cf.getMasDataEstadosGrupo().getCodigo().equals(state.getCodigo()) && cf.getEntityFieldName()
								.getNombreCampo().equalsIgnoreCase(item.getAttributeName()))
						.findAny();
				if (elemOpt.isPresent()) {
					result.put(name + "-" + item.getAttributeName(), elemOpt.get());
				}
			}
		}
		return result;
	}

	/**
	 * Update values one by one that were filled on the EntityComparation phase
	 */
	protected void updateNewValues(Object objOld, Object objNew, List<String> ignoreFields, EntityComparatorResult diff, MasDataEstadosGrupo estadoAConsiderar,
			Map<String, ConfiguracionFieldStatus> camposNoPermitidos) throws Exception {
		String camposKey;
		if (objOld == null)
			return;

		Field[] fieldsParent = objOld.getClass().getSuperclass().getDeclaredFields();
		Field[] fields = objOld.getClass().getDeclaredFields();
		List<Field> allFields = new ArrayList<>(Arrays.asList(fields));
		allFields.addAll(Arrays.asList(fieldsParent));
		allFields.removeIf(this::isStaticField);
		allFields.removeIf(this::isInternalField);

		for (Field field : allFields) {
			final String fieldName = field.getName();
			if (Modifier.isStatic(field.getModifiers())) {
				continue;
			}
			if (ignoreFields.contains(fieldName)) {
				continue;
			}
			if (KEY_FIELDS.contains(fieldName))
				continue;
			if (field.getAnnotation(JsonBackReference.class) != null || field.getAnnotation(JsonIgnore.class) != null)
				continue;
			if (fieldName.contains(INTERNAL_FIELDS)) {
				continue;
			}
			if (!hasGetter(objOld.getClass(), field))
				continue;
			if (field.getType().getCanonicalName().startsWith(PACKAGE_PREFIX)) {

				final Object ovOld = OBJECT_REFLECTION_UTIL.getFieldValueFromObject(objOld, fieldName);
				final Object ovNew = OBJECT_REFLECTION_UTIL.getFieldValueFromObject(objNew, fieldName);
				updateNewValues(ovOld, ovNew, ignoreFields, diff, estadoAConsiderar, camposNoPermitidos);
			} else if (field.getType() == Set.class) {
				final Set setOld = (Set) OBJECT_REFLECTION_UTIL.getFieldValueFromObject(objOld, fieldName);
				final Set setNew = (Set) OBJECT_REFLECTION_UTIL.getFieldValueFromObject(objNew, fieldName);
				updateSetValues(objOld, setOld, setNew, ignoreFields, diff, estadoAConsiderar, camposNoPermitidos);
			} else {
				camposKey = getClassName(objOld.getClass().getCanonicalName()) + "-" + fieldName;
				if (!camposNoPermitidos.containsKey(camposKey)) {
					// Permitimos cambios si no existe campo en la parametrizacion.
					log.debug("Field {} does not have a configuration", fieldName);
					Object newValue = OBJECT_REFLECTION_UTIL.getFieldValueFromObject(objNew, fieldName);
					OBJECT_REFLECTION_UTIL.setFieldValueFromObject(objOld, fieldName, (f) -> newValue);
				} else if (camposNoPermitidos.get(camposKey).getAllowChanges()) {
					Object newValue = OBJECT_REFLECTION_UTIL.getFieldValueFromObject(objNew, fieldName);
					OBJECT_REFLECTION_UTIL.setFieldValueFromObject(objOld, fieldName, (f) -> newValue);
				} else {
					log.info("Field {} of the class {} does not allow changes ", fieldName,
							objOld.getClass().getCanonicalName());
				}
			}
		}
	}

	private boolean isInternalField(Field field) {
		return field.getName().startsWith("$");
	}

	private boolean isStaticField(Field field) {
		return Modifier.isStatic(field.getModifiers());
	}

	private String getClassName(String classWithPackage) {
		return classWithPackage.substring(classWithPackage.lastIndexOf('.') + 1);
	}

	private boolean hasGetter(Class cls, Field field) {
		String getter = "get" + StringUtils.capitalize(field.getName());
		List<Method> m = Arrays.asList(cls.getMethods());
		boolean result = m.stream().anyMatch(x -> x.getName().equals(getter) || x.getName().equals("inactive"));
		if (!result && field.getType().equals(boolean.class)) {
			String getter2 = "is" + StringUtils.capitalize(field.getName());
			result = m.stream().anyMatch(x -> x.getName().equals(getter2));
		}
		return result;
	}

	private <T> void updateSetValues(Object fatherObject, Set<T> setOld, Set<T> setNew, List<String> ignoreFields,
			EntityComparatorResult diff, MasDataEstadosGrupo estadoAConsiderar,
			Map<String, ConfiguracionFieldStatus> camposNoPermitidos) throws Exception {
		Class<T> clazz = null;

		if (setOld != null && setOld.size() > 0) {
			clazz = (Class<T>)getPrimerElemento(setOld).getClass();
		} else if (setNew != null && setNew.size() > 0) {
			clazz = (Class<T>)getPrimerElemento(setNew).getClass();
		}
		if (clazz == null)
			return;

		SetComparator sc = new SetComparator();
		SetComparatorResults<T> scResults = sc.compare(clazz, setOld, setNew);

		Set<T> deletedSet = scResults.getDeletedSet();
		Set<T> newSet = scResults.getNewSet();
		Set<T> modifiedSet = scResults.getModifiedSet();


		List<String> ids = EntityMapUtil.getIds(clazz);
		Map<List<Object>, T> oldEntriesByKey = EntityMapUtil.getMap(ids, setOld);

		for (Object object : deletedSet) {
			if (checkIsAllow(fatherObject, object, camposNoPermitidos, Arrays.asList("serialVersionUID","userUpdate","userCreate"))) {
				List<Object> key = EntityMapUtil.getKey(ids, object);
				oldEntriesByKey.remove(key);

				entityManager.remove(object);
			}
		}

		for (T object : newSet) {
			if (checkIsAllow(fatherObject, object, camposNoPermitidos, Arrays.asList("serialVersionUID","userUpdate","userCreate"))) {
				List<Object> key = EntityMapUtil.getKey(ids, object);
				oldEntriesByKey.put(key, object);
				updateReference(fatherObject, object);
			}
		}
		for (T obj : modifiedSet) {
			List<Object> key = EntityMapUtil.getKey(ids, obj);
			Object objA = oldEntriesByKey.get(key);
			updateNewValues(objA, obj, ignoreFields, diff, estadoAConsiderar, camposNoPermitidos);
		}

		setOld.clear();
		setOld.addAll(oldEntriesByKey.values());
	}

	private <T> T getPrimerElemento(Set<T> objectSet) {
		if (objectSet.size()>0) {
			return objectSet.iterator().next();
		}
		else
			return null;
	}

	private void updateReferences(Object fatherObject, Set<Object> newSet) {
		if (getPrimerElemento(newSet) instanceof SetComponent) {
			newSet.stream().map( x -> (SetComponent) x).forEach(e -> e.setCabecera((BasicEntity) fatherObject));
		}
	}

	private void updateReference(Object fatherObject, Object newObject) {
		if (newObject instanceof SetComponent) {
			((SetComponent) newObject).setCabecera((BasicEntity) fatherObject);
		}
	}

	/**
	 *
	 * Creamos la gestión de cambios
	 *
	 * @param basicEntity
	 * @param diff
	 * @return
	 */
	private GestionCambio createGestionCambio(BasicEntity basicEntity, EntityComparatorResult diff) {

		GestionCambio gestionCambio = new GestionCambio();

		gestionCambio.setEntityId(basicEntity.getId());
		gestionCambio.setClassName(basicEntity.getClass().getCanonicalName());
		gestionCambio.setEntityCreationTimestamp(new Timestamp(System.currentTimeMillis()));
		gestionCambio.setEntityVersion(basicEntity.getEntityVersion() + 1);
		gestionCambio.setData(new HashSet<>());

		Set<GestionCambioData> dataSet = gestionCambio.getData();
		for (EntityComparatorItem di : diff.getDiffs()) {
			GestionCambioData gcd = new GestionCambioData();
			gcd.setEntityCreationTimestamp(new Timestamp(System.currentTimeMillis()));
			gcd.setLastUpdatedTimestamp(new Timestamp(System.currentTimeMillis()));
			gcd.setAction(di.getDiffType());
			gcd.setEntityClass(di.getClassName());
			gcd.setEntityField(di.getAttributeName());
			gcd.setEntityKey(di.getId());
			gcd.setOldValue(di.getValueA());
			gcd.setNewValue(di.getValueB());
			gestionCambio.addGestionCambioData(gcd);
			dataSet.add(gcd);
		}
		return gestionCambio;
	}

	private boolean checkIsAllow(Object fatherObject, Object object, Map<String, ConfiguracionFieldStatus> camposNoPermitidos,
			List<String> ignoreFields) throws Exception {
		if (object == null)
			return true;
		Field[] fields = object.getClass().getDeclaredFields();
		List<Field> allFields = new ArrayList<>(Arrays.asList(fields));
		allFields.removeIf(this::isStaticField);
		allFields.removeIf(this::isInternalField);

		for (Field field : allFields) {
			final String fieldName = field.getName();
			if (field.getType().getCanonicalName().equals(fatherObject.getClass().getCanonicalName()))
				continue;
			if (ignoreFields.contains(fieldName))
				continue;
			if (KEY_FIELDS.contains(fieldName))
				continue;
			if (field.getAnnotation(JsonBackReference.class) != null || field.getAnnotation(JsonIgnore.class) != null)
				continue;
			if (fieldName.startsWith(INTERNAL_FIELDS))
				continue;
			if (!hasGetter(object.getClass(), field))
				continue;
			if (field.getType().getCanonicalName().startsWith(PACKAGE_PREFIX)) {
				final Object ov = OBJECT_REFLECTION_UTIL.getFieldValueFromObject(object, fieldName);
				if (!checkIsAllow(object, ov, camposNoPermitidos, ignoreFields))
					return false;
			} else if (field.getType() == Set.class) {
				final Set set = (Set) OBJECT_REFLECTION_UTIL.getFieldValueFromObject(object, fieldName);
				for (Object ov : set) {
					if (!checkIsAllow(object, ov, camposNoPermitidos, ignoreFields))
						return false;
				}
			} else {
				String camposKey = getClassName(object.getClass().getCanonicalName()) + "-" + fieldName;
				if (camposNoPermitidos.containsKey(camposKey) && !camposNoPermitidos.get(camposKey).getAllowChanges())
					return false;
			}
		}
		return true;
	}

}
