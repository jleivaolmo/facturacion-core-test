package com.echevarne.sap.cloud.facturacion.odata.processor;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import com.echevarne.sap.cloud.facturacion.config.ContextProvider;
import com.echevarne.sap.cloud.facturacion.constants.ConstOData;
import com.echevarne.sap.cloud.facturacion.model.BasicEntity;
import com.echevarne.sap.cloud.facturacion.odata.annotations.JPAExit;
import com.echevarne.sap.cloud.facturacion.odata.extension.utils.EntityUtils;
import com.echevarne.sap.cloud.facturacion.odata.extension.utils.PropertyUtils;
import com.echevarne.sap.cloud.facturacion.util.ConversionUtils;
import com.echevarne.sap.cloud.facturacion.util.ODataFilterUtils;
import com.echevarne.sap.cloud.facturacion.validations.values.FieldValuesValidation;
import org.apache.commons.collections.CollectionUtils;
import org.apache.olingo.odata2.api.edm.EdmEntityType;
import org.apache.olingo.odata2.api.edm.EdmException;
import org.apache.olingo.odata2.api.exception.ODataException;
import org.apache.olingo.odata2.api.processor.ODataResponse;
import org.apache.olingo.odata2.api.uri.expression.ExpressionParserException;
import org.apache.olingo.odata2.api.uri.expression.FilterExpression;
import org.apache.olingo.odata2.api.uri.expression.OrderByExpression;
import org.apache.olingo.odata2.api.uri.info.GetEntitySetUriInfo;
import org.apache.olingo.odata2.core.uri.UriInfoImpl;
import org.apache.olingo.odata2.core.uri.expression.BinaryExpressionImpl;
import org.apache.olingo.odata2.core.uri.expression.ExpressionParserInternalError;
import org.apache.olingo.odata2.core.uri.expression.OrderByExpressionImpl;
import org.apache.olingo.odata2.core.uri.expression.OrderByParserImpl;
import org.apache.olingo.odata2.jpa.processor.core.model.JPAEdmMappingImpl;
import org.springframework.data.jpa.repository.JpaRepository;
import lombok.var;
import lombok.extern.slf4j.Slf4j;
import static com.echevarne.sap.cloud.facturacion.reflection.ObjectReflectionUtil.OBJECT_REFLECTION_UTIL;

@Slf4j
public class ODataGlobalProcessorExits {

	private static final String JSON = "json";
	private static final String SAVEANDFLUSH = "SAVEANDFLUSH";
	private static final String METHOD_POST_BATCH = "postBatch";
	private static final String METHOD_OVERRIDE_CREATE_ENTITY = "createEntity";
	private static final String METHOD_OVERRIDE_UPDATE_ENTITY = "updateEntity";
	private static final String METHOD_OVERRIDE_GET_TARGET = "getTargetEntity";
	static JpaRepository<?, Long> repositorio;
	static FieldValuesValidation fieldsValidations;

	/**
	 *
	 * @param jpaEntities
	 * @param uriParserResultView 
	 * @throws EdmException 
	 */
	public void readEntitySet(List<Object> jpaEntities, GetEntitySetUriInfo uriParserResultView) throws EdmException {

		UriInfoImpl uriInfo = (UriInfoImpl) uriParserResultView;
		JPAEdmMappingImpl mapping = ((JPAEdmMappingImpl) uriParserResultView.getTargetEntitySet().getEntityType().getMapping());
		Class<?> clazz = mapping.getJPAType();
		FilterExpression filter = uriInfo.getFilter();
		// Solo se añaden todos los comodines cuando el resultado no está filtrado. Cuando está filtrado por *, solo se envía *
		if (filter == null
				|| (filter.getExpression() instanceof BinaryExpressionImpl && ((BinaryExpressionImpl) filter.getExpression()).getRightOperand().getUriLiteral().equals("'*'"))) {
			// Elementos EMPTY y ALL
			JPAExit jpaExit = clazz.getAnnotation(JPAExit.class);
			if (jpaExit != null) {
				if (jpaExit.allowAll()) {
					Object element = createAllElement(clazz, jpaExit);
					if (element != null)
						jpaEntities.add(0, element);
				}
				if (jpaExit.allowEmpty() && filter == null) {
					Object element = createEmptyElement(clazz, jpaExit);
					if (element != null)
						jpaEntities.add(0, element);
				}
			}
		}

		if (CollectionUtils.isNotEmpty(jpaEntities)) {
			// Eliminación de 0's a la izquierda donde proceda
			removeLeadingZeros(jpaEntities);
		}
	}

	public void readEntity(Object jpaEntity) {
		if (jpaEntity != null) {
			var listObj = new ArrayList<Object>();
			listObj.add(jpaEntity);
			//Eliminación de 0's a la izquierda donde proceda
			removeLeadingZeros(listObj);
		}
	}

	private void removeLeadingZeros(List<Object> jpaEntities) {
		Class<?> clazz = jpaEntities.get(0).getClass();
		var removeLeadingZerosFields = ConversionUtils.removeLeadingZerosFields;
		removeLeadingZerosFields.stream().forEach(fieldName -> {
			try {
				String getMethodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
				String setMethodName = "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
				Method getMethod = clazz.getMethod(getMethodName);
				Method setMethod = clazz.getMethod(setMethodName, String.class);
				jpaEntities.forEach(obj -> {
					try {
						var value = (String) getMethod.invoke(obj);
						var nonLZValue = Integer.valueOf(value).toString();
						setMethod.invoke(obj, nonLZValue);
					} catch (Exception e) {
						// El valor no era numerico
					}
				});
			} catch (Exception e) {
				// El campo no se encuentra en la entidad
			}
		});
	}

	private Object createAllElement(Class<?> clazz, JPAExit jpaExit) {
		Object e = instantiateJPAEntity(clazz);
		setAllDefaultID(e, clazz, jpaExit.fieldId());
		setAllDefaultDesc(e, clazz, jpaExit.fieldDescription());
		return e;
	}

	/**
	 *
	 * @param obj
	 * @param clazz
	 * @param fieldName
	 */
	private void setAllDefaultID(Object obj, Class<?> clazz, String fieldName) {
		try {
			Field field = clazz.getDeclaredField(fieldName);
			if (field.getType() == int.class)
				setFieldValue(obj, field, (int) 0);
			else if (field.getName().equals("tipoPeticion"))
				setFieldValue(obj, field, (String) "0");
			else if (field.getType() == String.class)
				setFieldValue(obj, field, (String) "*");
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException e) {
			log.error("Ops!", e);
		}
	}

	/**
	 *
	 * @param obj
	 * @param clazz
	 * @param fieldName
	 */
	private void setAllDefaultDesc(Object obj, Class<?> clazz, String fieldName) {
		try {
			Field field = clazz.getDeclaredField(fieldName);
			if (field.getType() == int.class)
				setFieldValue(obj, field, (int) 0);
			else if (field.getType() == String.class)
				setFieldValue(obj, field, (String) ConstOData.DESCRIPCION_VALOR_COMODIN);
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException e) {
			log.error("Ops!", e);
		}
	}

	private Object createEmptyElement(Class<?> clazz, JPAExit jpaExit) {
		Object e = instantiateJPAEntity(clazz);
		setEmptyDefaultID(e, clazz, jpaExit.fieldId());
		setEmptyDefaultDesc(e, clazz, jpaExit.fieldDescription());
		return e;
	}

	/**
	 *
	 * @param obj
	 * @param clazz
	 * @param fieldName
	 */
	private void setEmptyDefaultID(Object obj, Class<?> clazz, String fieldName) {
		try {
			Field field = clazz.getDeclaredField(fieldName);
			if (field.getType() == int.class)
				setFieldValue(obj, field, (int) 0);
			else if (field.getType() == String.class)
				setFieldValue(obj, field, (String) "#");
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException e) {
			log.error("Ops!", e);
		}
	}

	/**
	 *
	 * @param obj
	 * @param clazz
	 * @param fieldName
	 */
	private void setEmptyDefaultDesc(Object obj, Class<?> clazz, String fieldName) {
		try {
			Field field = clazz.getDeclaredField(fieldName);
			if (field.getType() == int.class)
				setFieldValue(obj, field, (int) 0);
			else if (field.getType() == String.class)
				setFieldValue(obj, field, (String) ConstOData.DESCRIPCION_VALOR_VACIO);
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException e) {
			log.error("Ops!", e);
		}
	}

	/**
	 *
	 * Setea el valor a la entidad
	 *
	 * @param obj
	 * @param field
	 * @param value
	 */
	private void setFieldValue(Object obj, Field field, Object value) {
		try {
			if (field != null) {
				OBJECT_REFLECTION_UTIL.setFieldValueFromObject(obj, field.getName(), (f) -> value);
			}
		} catch (SecurityException | IllegalArgumentException | IllegalAccessException | InvocationTargetException | NoSuchFieldException e) {
			log.error("Ops!", e);
		}
	}

	

	protected UriInfoImpl appendFieldsToFilter(UriInfoImpl filteredUri) {
		//Default implementation
		return filteredUri;
	}

	private UriInfoImpl applyOrderByFields(UriInfoImpl uriInfo) {
		try {
			Class<?> entity = EntityUtils.getJpaEntity(uriInfo);
			String sortableFields = PropertyUtils.getSortableFields(entity);
			String format = uriInfo.getFormat() == null?"":uriInfo.getFormat();
			
			if (!format.equals(JSON) && sortableFields.length() > 0) {
				updateOrderBy(uriInfo, sortableFields);
			} else if (format.equals(JSON)){
				String idField = PropertyUtils.getIdField(entity);
				updateOrderBy(uriInfo, idField);
			}
			return uriInfo;
		} catch (Exception e) {
			log.error("Ops!", e);
		}
		return uriInfo;
	}

	private void updateOrderBy(UriInfoImpl uriInfo, String fields) throws ExpressionParserException, ExpressionParserInternalError {
		OrderByExpression orderBy = uriInfo.getOrderBy();
		if (orderBy == null) {
			orderBy = new OrderByExpressionImpl("");
		}
		StringBuilder orderByString = new StringBuilder();
		String originalOrderBy = orderBy.getExpressionString();
		String fioriOrderBy = ODataFilterUtils.getFioriOrderBy(originalOrderBy);
		orderByString.append(fioriOrderBy);
		if (orderByString.length() > 0) {
			orderByString.append(",");
		}
		orderByString.append(fields);
		OrderByParserImpl orderByParser = new OrderByParserImpl((EdmEntityType) uriInfo.getTargetType());
		OrderByExpression newOrderBy = orderByParser.parseOrderByString(orderByString.toString());
		uriInfo.setOrderBy(newOrderBy);
	}

	/**
	 * Instanciamos la entidad JPA
	 *
	 * @param jpaType
	 * @return
	 */
	protected Object instantiateJPAEntity(Class<?> jpaType) {
		if (jpaType == null) {
			return null;
		}
		try {
			return jpaType.newInstance();
		} catch (IllegalAccessException | InstantiationException e) {
			log.error("Ops!", e);
		}
		return null;
	}

	/**
	 *
	 * @param traceObjects
	 */
	public void executeChangeSet(Map<String, BasicEntity> traceObjects) {
		if (traceObjects.size() > 0) {
			traceObjects.entrySet().stream()
					.forEach(entity -> executePostBatchMethod(traceObjects.get(entity.getKey())));
		}
	}

	/**
	 *
	 * Process post batch entity
	 *
	 * @param entity
	 */
	private void executePostBatchMethod(BasicEntity entity) {
		if (entity instanceof ODataExits) {
			try {
				BasicEntity updatedEntity = getEntity(entity);
				updatedEntity = runMethod(updatedEntity, METHOD_POST_BATCH);
				repositoryUpdateEntity(updatedEntity);
			} catch (Exception e) {
				log.error("Ops!", e);
			}
		}
	}

	/**
	 *
	 * Reemplaza la creación de una entidad
	 *
	 * @param entity
	 * @return
	 */
	public Object createEntity(Object entity) {
		if (entity instanceof ODataExits) {
			try {
				BasicEntity newEntity = runMethod(entity, METHOD_OVERRIDE_CREATE_ENTITY);
				repositoryCreateEntity(newEntity);
			} catch (Exception e) {
				log.error("Ops!", e);
			}
		}
		return null;
	}

	/**
	 *
	 * Reemplaza la actualización de una entidad
	 *
	 * @param entity
	 * @param toUpdateEntity
	 * @param virtualEntity
	 * @return
	 */
	public Object updateEntity(Object actualEntity) {
		if (actualEntity instanceof ODataExits) {
			try {
				// Obtenemos la entidad que realmente se requiere actualizar
				BasicEntity realEntity = runMethod(actualEntity, METHOD_OVERRIDE_GET_TARGET);
				BasicEntity targetEntity = getEntity(realEntity);
				BasicEntity newEntity = runMethod(actualEntity, METHOD_OVERRIDE_UPDATE_ENTITY, targetEntity);
				repositoryUpdateEntity(newEntity);
				return actualEntity;
			} catch (Exception e) {
				log.error("Ops!", e);
			}
		}
		return null;
	}

	/**
	 *
	 * Obtiene la ultima versión de la entidad
	 *
	 * @param entity
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private BasicEntity getEntity(BasicEntity entity) {
		repositorio = (JpaRepository<?, Long>) ContextProvider.getRepository(entity.getClass());
		if (repositorio != null) {
			return (BasicEntity) repositorio.getOne(entity.getId());
		}
		return entity;
	}

	/**
	 *
	 * Update the entity
	 *
	 * @param entity
	 */
	@SuppressWarnings("unchecked")
	private void repositoryUpdateEntity(BasicEntity entity) {
		repositorio = (JpaRepository<?, Long>) ContextProvider.getRepository(entity.getClass());
		if (repositorio != null) {
			runRepositoryMethod(entity, SAVEANDFLUSH);
		}
	}

	/**
	 *
	 * Update the entity
	 *
	 * @param entity
	 */
	@SuppressWarnings("unchecked")
	private void repositoryCreateEntity(BasicEntity entity) {
		repositorio = (JpaRepository<?, Long>) ContextProvider.getRepository(entity.getClass());
		if (repositorio != null) {
			runRepositoryMethod(entity, SAVEANDFLUSH);
		}
	}

	/**
	 *
	 * Ejecutamos un metodo del repositorio
	 *
	 * @param entity
	 */
	private void runRepositoryMethod(BasicEntity entity, String nombreMetodo) {
		Class<?> claseRep = repositorio.getClass();
		Method[] methods = claseRep.getMethods();
		List<Method> methodsList = Arrays.asList(methods);
		Optional<Method> optSaveFlush = methodsList.stream()
				.filter(m -> m.getName().toUpperCase().contains(nombreMetodo)).findFirst();
		Method metodo = optSaveFlush.get();
		try {
			metodo.invoke(repositorio, entity);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			log.error("Ops!", e);
		}
	}

	/**
	 *
	 * Calls a referenced java method.
	 *
	 * @param entity
	 * @param methodName
	 * @return
	 * @throws Exception
	 */
	private BasicEntity runMethod(BasicEntity entity, String methodName) throws Exception {

		try {
			Method m = entity.getClass().getDeclaredMethod(methodName);
			m.setAccessible(true);
			return (BasicEntity) m.invoke(entity);

		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			log.error("Ops!", e);
		}
		return null;

	}

	/**
	 *
	 * Calls a referenced java method.
	 *
	 * @param entity
	 * @param methodName
	 * @return
	 * @throws Exception
	 */
	private BasicEntity runMethod(Object entity, String methodName, Object... args) throws Exception {

		try {

			Optional<Method> method = Arrays.stream(entity.getClass().getMethods())
					.filter(x -> x.getName().equals(methodName)).findFirst();
			if (method.isPresent()) {
				Method m = method.get();
				m.setAccessible(true);
				return (BasicEntity) m.invoke(entity, args);
			} else {
				log.error("No se ha encontrado el método " + methodName + " entre los métodos de la clase " + entity);
				return null;
			}
				

		} catch (SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			log.error("Error en runMethod: " + entity + " " + methodName + " " + args, e);
		}
		return null;

	}

	public void executeBatch(ODataResponse batchResponse) {
		// TODO implementar esta exits
	}

	public void validateFieldsBeforeCreation(Object toCreateEntity, Map<String, Object> object) throws ODataException {
		// if(fieldsValidations == null){
		// fieldsValidations = (FieldValuesValidation)
		// ContextProvider.getRepository("validacionesFieldValue");
		// }
		// ValidationResult validation =
		// fieldsValidations.validateAllFields(toCreateEntity, object);
		// if(!validation.isValid()){
		// throw new ODataException("Los valores ingresados son invalidos.");
		// }
	}

	

}
