package com.echevarne.sap.cloud.facturacion.odata.processor;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.apache.commons.io.IOUtils;
import org.apache.olingo.odata2.api.batch.BatchHandler;
import org.apache.olingo.odata2.api.batch.BatchRequestPart;
import org.apache.olingo.odata2.api.batch.BatchResponsePart;
import org.apache.olingo.odata2.api.commons.HttpStatusCodes;
import org.apache.olingo.odata2.api.edm.EdmEntityType;
import org.apache.olingo.odata2.api.edm.EdmException;
import org.apache.olingo.odata2.api.edm.EdmType;
import org.apache.olingo.odata2.api.ep.EntityProvider;
import org.apache.olingo.odata2.api.ep.EntityProviderBatchProperties;
import org.apache.olingo.odata2.api.exception.ODataException;
import org.apache.olingo.odata2.api.processor.ODataResponse;
import org.apache.olingo.odata2.api.uri.KeyPredicate;
import org.apache.olingo.odata2.api.uri.PathInfo;
import org.apache.olingo.odata2.api.uri.UriInfo;
import org.apache.olingo.odata2.api.uri.expression.CommonExpression;
import org.apache.olingo.odata2.api.uri.info.DeleteUriInfo;
import org.apache.olingo.odata2.api.uri.info.GetEntitySetUriInfo;
import org.apache.olingo.odata2.api.uri.info.PostUriInfo;
import org.apache.olingo.odata2.api.uri.info.PutMergePatchUriInfo;
import org.apache.olingo.odata2.core.uri.UriInfoImpl;
import org.apache.olingo.odata2.core.uri.expression.BinaryExpressionImpl;
import org.apache.olingo.odata2.core.uri.expression.FilterExpressionImpl;
import org.apache.olingo.odata2.core.uri.expression.FilterParserImpl;
import org.apache.olingo.odata2.core.uri.expression.LiteralExpressionImpl;
import org.apache.olingo.odata2.core.uri.expression.MemberExpressionImpl;
import org.apache.olingo.odata2.core.uri.expression.PropertyExpressionImpl;
import org.apache.olingo.odata2.jpa.processor.api.ODataJPAContext;
import org.apache.olingo.odata2.jpa.processor.api.ODataJPADefaultProcessor;
import org.springframework.data.jpa.repository.JpaRepository;
import com.echevarne.sap.cloud.facturacion.config.ContextProvider;
import com.echevarne.sap.cloud.facturacion.model.BasicEntity;
import com.echevarne.sap.cloud.facturacion.model.BasicMasDataEntity;
import com.echevarne.sap.cloud.facturacion.model.ValidityBasicEntity;
import com.echevarne.sap.cloud.facturacion.util.DateUtils;
import com.echevarne.sap.cloud.facturacion.util.StringUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

@Deprecated
@Slf4j
public class ODataWithSearchProcessor extends ODataJPADefaultProcessor {

	private static final String DUPLICATE_EXCEPTION = "duplicate";
	private static final String UNIQUE_EXCEPTION = "unique";
	private static final String SQL_INTEGRITY_CONSTRAINT_VIOLATION_EXCEPTION = "SQLIntegrityConstraintViolationException";
	private static final String SUFIJO_REPO = "Rep";
	private static final String SAVEANDFLUSH = "SAVEANDFLUSH";
	private static final String COUNT = "count";
	private static final String GET = "get";
	private static final String SEARCH = "search";
	private static final String CAMPOCODIGOCLIENTE = "codigoCliente";
	private static final String CAMPOCODIGOPRUEBA = "codigoPrueba";
	private static final String CAMPOCODIGOMATERIAL = "codigoMaterial";
	private static final String CAMPOCONCEPTOFACTURACION = "conceptoFacturacion";
	private static final String CAMPOINTERLOCUTORPACIENTE = "interlocutorPaciente";
	private static final String CAMPOINTERLOCUTOREMPRESA = "interlocutorEmpresa";
	private static final String CAMPOINTERLOCUTORREMITENTE = "interlocutorRemitente";
	private static final String CAMPOINTERLOCUTORCOMPANIA = "interlocutorCompania";
	static JpaRepository<?, Long> repositorio;
	
	public ODataWithSearchProcessor(ODataJPAContext oDataJPAContext) {
		super(oDataJPAContext);
	}

	@Override
	public ODataResponse readEntitySet(final GetEntitySetUriInfo uriParserResultView, final String contentType)
			throws ODataException {
		ODataResponse oDataResponse = null;
		try {
			oDataJPAContext.setODataContext(getContext());
			UriInfoImpl uriInfo = convertFilterValueToInternal(uriParserResultView);
			Integer skip = uriInfo.getSkip();
			Integer top = uriInfo.getTop();
			Map<String, String> customQueryOptions = uriInfo.getCustomQueryOptions();
			String search = customQueryOptions == null ? null : customQueryOptions.get(SEARCH);
			if (search != null) {
				uriInfo.setSkip(null);
				uriInfo.setTop(null);
			}
			List<Object> jpaEntities = jpaProcessor.process(uriParserResultView);
			List<Object> jpaEntitiesFiltered = search == null ? jpaEntities
					: filteredList(jpaEntities, uriInfo, search, skip, top);
			oDataResponse = responseBuilder.build(uriParserResultView, jpaEntitiesFiltered, contentType);
		} finally {
			close();
		}
		return oDataResponse;
	}

	/**
	 * 
	 * @param uriParserResultView
	 * @return
	 */
	private UriInfoImpl convertFilterValueToInternal(GetEntitySetUriInfo uriParserResultView) {
		UriInfoImpl uriInfo = (UriInfoImpl) uriParserResultView;
		try {
			FilterExpressionImpl filter = (FilterExpressionImpl) uriInfo.getFilter();
			if (filter != null && applyAlphaConvertion(filter)) {
				BinaryExpressionImpl expression = (BinaryExpressionImpl) filter.getExpression();
				LiteralExpressionImpl rightOp = (LiteralExpressionImpl) expression.getRightOperand();
				CommonExpression leftOp = expression.getLeftOperand();
				String campo = null;
				if (leftOp instanceof PropertyExpressionImpl) {
					PropertyExpressionImpl prExp = (PropertyExpressionImpl) leftOp;
					campo = prExp.getUriLiteral();
				} else if (leftOp instanceof MemberExpressionImpl) {
					MemberExpressionImpl mbrExp = (MemberExpressionImpl) leftOp;
					campo = mbrExp.getPath().getUriLiteral() + "/" + mbrExp.getProperty().getUriLiteral();
				}
				String valor = rightOp.getUriLiteral();
				String op = expression.getOperator().toUriLiteral();
				String valorNum = valor.substring(1, valor.length() - 1);
				if (StringUtils.isInteger(valorNum)) {
					String filterString = applyAlphaConversionForField(campo, op, Integer.valueOf(valorNum));
					FilterParserImpl filterParser = new FilterParserImpl((EdmEntityType) uriInfo.getTargetType());
					FilterExpressionImpl filterPadding = (FilterExpressionImpl) filterParser
							.parseFilterString(filterString);
					uriInfo.setFilter(filterPadding);
				}
			}
		} catch (Exception e) {
			log.error("Ops!", e);
		}
		return uriInfo;

	}

	@Override
	public ODataResponse createEntity(final PostUriInfo uriParserResultView, final InputStream content,
			final String requestContentType, final String contentType) throws ODataException {
		ODataResponse oDataResponse = null;
		try {
			oDataJPAContext.setODataContext(getContext());
			List<InputStream> inputStreamCopy = getInputStreamCopy(content);
			validateTextConstraints((UriInfo) uriParserResultView, inputStreamCopy.get(0),
					BasicEntity.class.getPackage(), false);
			Object createdJpaEntity = jpaProcessor.process(uriParserResultView, inputStreamCopy.get(1),
					requestContentType);
			oDataResponse = responseBuilder.build(uriParserResultView, createdJpaEntity, contentType);
		} catch (Exception e) {
			formatConstraintExceptionMessage(e);
			throw e;
		} finally {
			close();
		}
		return oDataResponse;
	}

	protected List<InputStream> getInputStreamCopy(InputStream content) throws ODataException {
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];
			int len;
			while ((len = content.read(buffer)) > -1) {
				baos.write(buffer, 0, len);
			}
			baos.flush();
			InputStream is1 = new ByteArrayInputStream(baos.toByteArray());
			InputStream is2 = new ByteArrayInputStream(baos.toByteArray());
			List<InputStream> response = new ArrayList<InputStream>();
			response.add(is1);
			response.add(is2);
			return response;
		} catch (IOException e) {
			throw new ODataException(e);
		}
	}

	protected void validateTextConstraints(UriInfo uriParserResultView, InputStream content, Package pck,
			boolean getAll) throws ODataException {
		try {
			final String json = IOUtils.toString(content, StandardCharsets.UTF_8);
			ObjectMapper mapper = new ObjectMapper();
			Map<String, ?> valuesReceived = mapper.readValue(json, Map.class);
			String entityName = uriParserResultView.getTargetType().getName();
			Class<?> entityClass = Class.forName(pck.getName() + "." + entityName);
			Method[] methods = entityClass.getMethods();
			List<Method> methodsList = Arrays.asList(methods);
			List<String> listMsgError = new ArrayList<String>();
			methodsList.stream().filter(m -> m.getReturnType().getSimpleName().endsWith("Text"))
					.map(m -> m.getReturnType().getName()).forEach(n -> {
						String msgError = validateTexts(n, valuesReceived, entityClass, getAll);
						if (msgError != null) {
							listMsgError.add(msgError);
						}
					});
			if (!listMsgError.isEmpty()) {
				throw new ODataException(listMsgError.get(0));
			}
		} catch (ClassNotFoundException | EdmException | SecurityException | IOException e) {
			log.error("Ops!", e);
		}
	}

	private String validateTexts(String nameText, Map<String, ?> valuesReceived, Class<?> mainClass, boolean getAll) {
		// try {
		// Class<?> entityClass = Class.forName(nameText);
		// Field[] mainFields = mainClass.getDeclaredFields();
		// List<Field> mainFieldsList = Arrays.asList(mainFields);
		// Field textField = mainFieldsList.stream().filter(f -> f.getType() ==
		// entityClass).findFirst().get();
		// Annotation[] arrayAnnotation =
		// textField.getAnnotationsByType(javax.persistence.JoinColumn.class);
		// Annotation annotationJoin = arrayAnnotation[0];
		// String[] strValues = annotationJoin.toString().split(",");
		// List<String> valuesList = Arrays.asList(strValues);
		// Optional<String> optAssignedName = valuesList.stream().filter(v ->
		// v.contains("name=")).findFirst();
		// String assignedName = optAssignedName.get();
		// String name = assignedName.split("=")[1];
		// String value = (String) valuesReceived.get(name);
		// if (value == null) {
		// return null;
		// }

		// GetEntitySetUriInfo uriInfo = new UriInfoImpl();
		// UriInfoImpl uriInfoImpl = (UriInfoImpl) uriInfo;
		// uriInfoImpl.setInlineCount(InlineCount.ALLPAGES);
		// EdmEntitySetImpl targetEntitySet = new EdmEntitySetImplProv();
		// uriInfoImpl.setTargetEntitySet(targetEntitySet);
		// EdmEntitySetImplProv edmEntityType = new EdmEntitySetImplProv();
		// targetEntitySet.setEdmEntityType(edmEntityType);
		// edmEntityType.setName(entityClass.getSimpleName());
		// EdmComplexTypeImplProv structuralType = new EdmComplexTypeImplProv();
		// edmEntityType.setStructuralType(structuralType);
		// JPAEdmMappingImpl mapping = new JPAEdmMappingImpl();
		// structuralType.setMapping(mapping);
		// mapping.setJPAType(entityClass);
		// mapping.setInternalName(entityClass.getSimpleName());
		// List<Object> totalJPAEntities = new ArrayList<Object>();
		// List<Object> jpaEntities = null;
		// int skipReg = oDataJPAContext.getPageSize();
		// int totalSkipReg = 0;
		// while (jpaEntities == null || jpaEntities.size() == skipReg) {
		// uriInfoImpl.setSkip(totalSkipReg);
		// jpaEntities = jpaProcessor.process(uriInfo);
		// totalJPAEntities.addAll(jpaEntities);
		// totalSkipReg += skipReg;
		// }

		// Field[] entityFields = entityClass.getDeclaredFields();
		// List<Field> entityFieldsList = Arrays.asList(entityFields);
		// Field keyField = entityFieldsList.stream()
		// .filter(f -> f.getAnnotationsByType(javax.persistence.Id.class).length >
		// 0).findFirst().get();
		// char[] chrName = keyField.getName().toCharArray();
		// chrName[0] = Character.toUpperCase(chrName[0]);
		// String newName = new String(chrName);
		// Method[] methods = entityClass.getMethods();
		// List<Method> methodsList = Arrays.asList(methods);
		// Optional<Method> optGetMethod = methodsList.stream().filter(m ->
		// m.getName().equals("get" + newName))
		// .findFirst();
		// Method getMethod = optGetMethod.get();
		// List<String> correctValues = new ArrayList<String>();
		// if (getAll) {
		// correctValues.add("*");
		// }
		// totalJPAEntities.stream().forEach(o -> {
		// try {
		// String keyValue = (String) getMethod.invoke(o);
		// correctValues.add(keyValue);
		// } catch (IllegalAccessException | IllegalArgumentException |
		// InvocationTargetException e) {
		// log.error("Ops!", e);
		// }
		// });
		// boolean existsValue = correctValues.stream().anyMatch(v -> v.equals(value));
		// if (!existsValue) {
		// return "El valor " + value + " no es correcto";
		// }

		// } catch (ODataJPAModelException | ODataJPARuntimeException | EdmException |
		// ClassNotFoundException
		// | SecurityException e) {
		// log.error("Ops!", e);
		// }
		return null;
	}

	@Override
	public ODataResponse updateEntity(final PutMergePatchUriInfo uriParserResultView, final InputStream content,
			final String requestContentType, final boolean merge, final String contentType) throws ODataException {
		ODataResponse oDataResponse = null;
		try {
			oDataJPAContext.setODataContext(getContext());
			List<InputStream> inputStreamCopy = getInputStreamCopy(content);
			validateTextConstraints((UriInfo) uriParserResultView, inputStreamCopy.get(0),
					BasicEntity.class.getPackage(), false);
			Object jpaEntity = jpaProcessor.process(uriParserResultView, inputStreamCopy.get(1), requestContentType);
			oDataResponse = responseBuilder.build(uriParserResultView, jpaEntity);
		} catch (Exception e) {
			formatConstraintExceptionMessage(e);
			throw e;
		} finally {
			close();
		}
		return oDataResponse;
	}

	@Override
	public ODataResponse executeBatch(final BatchHandler handler, final String contentType, final InputStream content)
			throws ODataException {
		ODataResponse batchResponse = null;
		try {
			oDataJPAContext.setODataContext(getContext());
			List<BatchResponsePart> batchResponseParts = new ArrayList<BatchResponsePart>();
			PathInfo pathInfo = getContext().getPathInfo();
			EntityProviderBatchProperties batchProperties = EntityProviderBatchProperties.init().pathInfo(pathInfo)
					.build();
			List<BatchRequestPart> batchParts = EntityProvider.parseBatchRequest(contentType, content, batchProperties);
			for (BatchRequestPart batchPart : batchParts) {
				batchResponseParts.add(handler.handleBatchPart(batchPart));
			}
			batchResponse = EntityProvider.writeBatchResponse(batchResponseParts);
		} catch (Exception e) {
			formatConstraintExceptionMessage(e);
			throw e;
		} finally {
			close(true);
		}
		return batchResponse;
	}

	private void formatConstraintExceptionMessage(Exception e) throws ODataException {
		Throwable causeExcep = null;
		if (e.getCause() != null) {
			Throwable cause = e.getCause().getCause();
			causeExcep = cause == null ? null : cause.getCause() == null ? cause : cause.getCause();
		}
		if (causeExcep != null
				&& causeExcep.getClass().getSimpleName().contains(SQL_INTEGRITY_CONSTRAINT_VIOLATION_EXCEPTION)) {
			String msg = causeExcep.getMessage().toLowerCase();
			if (msg.contains(DUPLICATE_EXCEPTION) || msg.contains(UNIQUE_EXCEPTION)) {
				throw new ODataException("Registro duplicado");
			} else {
				throw new ODataException("Valores incorrectos");
			}
		}
	}

	/**
	 * 
	 * @param filter
	 * @return
	 */
	private boolean applyAlphaConvertion(FilterExpressionImpl filter) {
		String expressionString = filter.getExpressionString().toLowerCase();
		if (expressionString.contains(CAMPOCODIGOCLIENTE.toLowerCase())) {
			return true;
		} else if (expressionString.contains(CAMPOCODIGOPRUEBA.toLowerCase())) {
			return true;
		} else if (expressionString.contains(CAMPOCODIGOMATERIAL.toLowerCase())) {
			return true;
		} else if (expressionString.contains(CAMPOCONCEPTOFACTURACION.toLowerCase())) {
			return true;
		} else if (expressionString.contains(CAMPOINTERLOCUTORPACIENTE.toLowerCase())) {
			return true;
		} else if (expressionString.contains(CAMPOINTERLOCUTOREMPRESA.toLowerCase())) {
			return true;
		} else if (expressionString.contains(CAMPOINTERLOCUTORREMITENTE.toLowerCase())) {
			return true;
		} else if (expressionString.contains(CAMPOINTERLOCUTORCOMPANIA.toLowerCase())) {
			return true;
		}
		return false;
	}

	
	

	
	/**
	 * 
	 */

	/**
	 * 
	 * @param fieldName
	 * @param operator
	 * @param externalValue
	 * @return
	 */
	private String applyAlphaConversionForField(String fieldName, String operator, int externalValue) {
		String format = getFormatForField(fieldName);
		String internalValue = "'" + String.format(format, externalValue) + "'";
		String filterString = fieldName + " " + operator + " " + internalValue;
		return filterString;
	}

	/**
	 * 
	 * @param fieldName
	 * @param externalValue
	 * @return
	 */
	private String applyAlphaConversionForField(String fieldName, int externalValue) {
		String format = getFormatForField(fieldName);
		return String.format(format, externalValue);
	}

	/**
	 * 
	 * @param fieldName
	 * @return
	 */
	private String getFormatForField(String fieldName) {
		if (fieldName.equalsIgnoreCase(CAMPOCODIGOCLIENTE)) {
			return "%010d";
		} else if (fieldName.equalsIgnoreCase(CAMPOCODIGOPRUEBA)) {
			return "%018d";
		} else if (fieldName.equalsIgnoreCase(CAMPOCODIGOMATERIAL)) {
			return "%018d";
		} else if (fieldName.equalsIgnoreCase(CAMPOCONCEPTOFACTURACION)) {
			return "%018d";
		} else if (fieldName.equalsIgnoreCase(CAMPOINTERLOCUTORPACIENTE)) {
			return "%010d";
		} else if (fieldName.equalsIgnoreCase(CAMPOINTERLOCUTOREMPRESA)) {
			return "%010d";
		} else if (fieldName.equalsIgnoreCase(CAMPOINTERLOCUTORREMITENTE)) {
			return "%010d";
		} else if (fieldName.equalsIgnoreCase(CAMPOINTERLOCUTORCOMPANIA)) {
			return "%010d";
		}
		return "%010d";
	}

	/**
	 * 
	 * 
	 * 
	 * @param jpaEntities
	 * @param uriInfo
	 * @param search
	 * @param skip
	 * @param top
	 * @return
	 */
	private List<Object> filteredList(List<Object> jpaEntities, UriInfoImpl uriInfo, String search, Integer skip,
			Integer top) {
		List<Object> jpaEntitiesFiltered = new ArrayList<Object>();
		if (jpaEntities.size() > 0) {
			Method[] methods = jpaEntities.get(0).getClass().getMethods();
			List<Method> methodsList = Arrays.asList(methods);
			jpaEntities.stream().forEach(o -> {
				methodsList.stream().forEach(m -> {
					if (m.getName().contains(GET)) {
						Object value = null;
						try {
							value = m.invoke(o);
						} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
							log.error("Ops!", e);
						}
						String valueStr = "";
						if (value instanceof String) {
							valueStr = (String) value;
						}
						if (value instanceof Integer) {
							valueStr = String.valueOf(value);
						}
						if (valueStr.contains(search) && !jpaEntitiesFiltered.contains(o)) {
							jpaEntitiesFiltered.add(o);
						}
					}
				});
			});
		}
		int size = jpaEntitiesFiltered.size();
		uriInfo.getCustomQueryOptions().put(COUNT, String.valueOf(size));
		if (skip == null || top == null) {
			return jpaEntitiesFiltered;
		}
		if (size > skip && size < skip + top) {
			return jpaEntitiesFiltered.subList(skip, size);
		}
		if (size > skip && size >= skip + top) {
			return jpaEntitiesFiltered.subList(skip, skip + top);
		}
		return jpaEntitiesFiltered;
	}

	@Override
	public ODataResponse deleteEntity(final DeleteUriInfo uriParserResultView, final String contentType)
			throws ODataException {
		ODataResponse response = null;
		EdmType type = uriParserResultView.getTargetType();
		try {
			String name = type.getName();
			String pkg = BasicEntity.class.getPackage().getName();
			Class<?> act = Class.forName(pkg + "." + name);
			Class<?> superAct = act.getSuperclass();
			if (superAct == BasicMasDataEntity.class
					|| superAct == ValidityBasicEntity.class) {
				char[] chrName = name.toCharArray();
				chrName[0] = Character.toLowerCase(chrName[0]);
				String newName = new String(chrName);
				repositorio = (JpaRepository<?, Long>) ContextProvider.getBean(newName + SUFIJO_REPO);
				List<KeyPredicate> listKeys = uriParserResultView.getKeyPredicates();
				KeyPredicate key = listKeys.get(0);
				String strId = key.getLiteral();
				Long lngId = Long.valueOf(strId);
				Optional<?> opt = repositorio.findById(lngId);
				BasicEntity entity = (BasicEntity) opt.get();
				if (entity instanceof ValidityBasicEntity) {
					int dayInMs = 1000 * 60 * 60 * 24;
					Calendar previousDay = DateUtils.convertToCalendar(new Date(new Date().getTime() - dayInMs));
					((ValidityBasicEntity) entity).setValidezHasta(previousDay);
				} else if (entity instanceof BasicMasDataEntity) {
					((BasicMasDataEntity) entity).setActive(false);
				}
				Class<?> claseRep = repositorio.getClass();
				Method[] methods = claseRep.getMethods();
				List<Method> methodsList = Arrays.asList(methods);
				Optional<Method> optSaveFlush = methodsList.stream()
						.filter(m -> m.getName().toUpperCase().contains(SAVEANDFLUSH)).findFirst();
				Method metodo = optSaveFlush.get();
				metodo.invoke(repositorio, entity);
				response = responseBuilder.build(uriParserResultView, entity);
			} else {
				return super.deleteEntity(uriParserResultView, contentType);
			}
		} catch (Exception e) {
			log.error("Ops!", e);
			response = ODataResponse.entity("error").status(HttpStatusCodes.NOT_ACCEPTABLE).build();
		}
		return response;
	}

}
