package com.echevarne.sap.cloud.facturacion.odata.processor;

import java.io.InputStream;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import javax.persistence.RollbackException;

import org.apache.olingo.odata2.api.batch.BatchHandler;
import org.apache.olingo.odata2.api.batch.BatchRequestPart;
import org.apache.olingo.odata2.api.batch.BatchResponsePart;
import org.apache.olingo.odata2.api.commons.HttpStatusCodes;
import org.apache.olingo.odata2.api.commons.ODataHttpHeaders;
import org.apache.olingo.odata2.api.edm.EdmAssociationSet;
import org.apache.olingo.odata2.api.edm.EdmEntitySet;
import org.apache.olingo.odata2.api.edm.EdmEntityType;
import org.apache.olingo.odata2.api.edm.EdmException;
import org.apache.olingo.odata2.api.edm.EdmMultiplicity;
import org.apache.olingo.odata2.api.edm.EdmNavigationProperty;
import org.apache.olingo.odata2.api.edm.EdmReferentialConstraint;
import org.apache.olingo.odata2.api.edm.EdmType;
import org.apache.olingo.odata2.api.edm.EdmTyped;
import org.apache.olingo.odata2.api.ep.EntityProvider;
import org.apache.olingo.odata2.api.ep.EntityProviderBatchProperties;
import org.apache.olingo.odata2.api.ep.entry.ODataEntry;
import org.apache.olingo.odata2.api.exception.ODataBadRequestException;
import org.apache.olingo.odata2.api.exception.ODataException;
import org.apache.olingo.odata2.api.processor.ODataRequest;
import org.apache.olingo.odata2.api.processor.ODataResponse;
import org.apache.olingo.odata2.api.uri.KeyPredicate;
import org.apache.olingo.odata2.api.uri.PathInfo;
import org.apache.olingo.odata2.api.uri.PathSegment;
import org.apache.olingo.odata2.api.uri.UriSyntaxException;
import org.apache.olingo.odata2.api.uri.info.DeleteUriInfo;
import org.apache.olingo.odata2.api.uri.info.GetEntitySetUriInfo;
import org.apache.olingo.odata2.api.uri.info.GetEntityUriInfo;
import org.apache.olingo.odata2.api.uri.info.GetMetadataUriInfo;
import org.apache.olingo.odata2.api.uri.info.PostUriInfo;
import org.apache.olingo.odata2.api.uri.info.PutMergePatchUriInfo;
import org.apache.olingo.odata2.core.PathInfoImpl;
import org.apache.olingo.odata2.core.edm.provider.EdmSimplePropertyImplProv;
import org.apache.olingo.odata2.jpa.processor.api.ODataJPAContext;
import org.apache.olingo.odata2.jpa.processor.api.ODataJPADefaultProcessor;
import org.apache.olingo.odata2.jpa.processor.core.ODataEntityParser;
import org.apache.olingo.odata2.jpa.processor.core.edm.ODataJPAEdmProvider;
import org.apache.olingo.odata2.jpa.processor.core.model.JPAEdmMappingImpl;
import org.springframework.data.jpa.repository.JpaRepository;
import com.echevarne.sap.cloud.facturacion.config.ContextProvider;
import com.echevarne.sap.cloud.facturacion.model.BasicEntity;
import com.echevarne.sap.cloud.facturacion.model.BasicMasDataEntity;
import com.echevarne.sap.cloud.facturacion.model.ValidityBasicEntity;
import com.echevarne.sap.cloud.facturacion.odata.extension.metadata.ODataEdmMetadataImpl;
import com.echevarne.sap.cloud.facturacion.util.DateUtils;
import com.echevarne.sap.cloud.facturacion.util.ODataUriUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.var;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ODataGlobalProcessor extends ODataJPADefaultProcessor {

	protected ODataJPAProcessor customJpaProcessor;
	protected ODataGlobalProcessorExits processorExits;
	protected static final String SQL_INTEGRITY_CONSTRAINT_VIOLATION_EXCEPTION_SAP_DB = "SQLIntegrityConstraintViolationExceptionSapDB";
	protected static final String CONSTRAINT_VIOLATION_EXCEPTION = "ConstraintViolationException";
	protected static final List<String> CONSTRAINT_VIOLATION_EXCEP_ARRAY = Arrays
			.asList(SQL_INTEGRITY_CONSTRAINT_VIOLATION_EXCEPTION_SAP_DB, CONSTRAINT_VIOLATION_EXCEPTION);
	protected static final String SUFIJO_REPO = "Rep";
	protected static final String SAVEANDFLUSH = "SAVEANDFLUSH";

	protected static JpaRepository<?, Long> repositorio;
	protected Map<String, BasicEntity> traceObjects = new HashMap<String, BasicEntity>();

	public ODataGlobalProcessor(ODataJPAContext oDataJPAContext) {
		super(oDataJPAContext);
		this.customJpaProcessor = new ODataJPAProcessor(oDataJPAContext);
		this.processorExits = new ODataGlobalProcessorExits();
	}
	
	public void customValidations(Object toEntity) throws ODataException {
		log.debug("No se ha definido implementación en customValidations");
	}

	

	@Override
	public ODataResponse readEntity(final GetEntityUriInfo uriParserResultView, final String contentType)
			throws ODataException {
		ODataResponse oDataResponse = null;
		try {
			oDataJPAContext.setODataContext(getContext());
			Object jpaEntity = jpaProcessor.process(uriParserResultView);
			processorExits.readEntity(jpaEntity);
			oDataResponse = responseBuilder.build(uriParserResultView, jpaEntity, contentType);
		} finally {
			close();
		}
		return oDataResponse;
	}

	@Override
	public ODataResponse createEntity(final PostUriInfo uriParserResultView, final InputStream content,
			final String requestContentType, final String contentType) throws ODataException {
		ODataResponse oDataResponse = null;
		try {
			oDataJPAContext.setODataContext(getContext());
			Map<String, Object> object = contentToMap(uriParserResultView, content, requestContentType);
			Object toCreateEntity = customJpaProcessor.toEntity(uriParserResultView, object);
			customValidations(toCreateEntity);
			processorExits.validateFieldsBeforeCreation(toCreateEntity, object);
			Object createdJpaEntity = processorExits.createEntity(toCreateEntity);
			if (createdJpaEntity == null) {
				createdJpaEntity = customJpaProcessor.process(uriParserResultView, object);
				saveTraceObject(uriParserResultView.getTargetEntitySet(), createdJpaEntity);
				oDataResponse = responseBuilder.build(uriParserResultView, createdJpaEntity, contentType);
			} else {
				oDataResponse = responseBuilder.build(uriParserResultView, toCreateEntity, contentType);

			}
		} catch (RollbackException e) {
			if (e.getCause() != null && e.getCause().getCause() != null && CONSTRAINT_VIOLATION_EXCEP_ARRAY
					.contains(e.getCause().getCause().getCause().getClass().getSimpleName())) {
				var objResponse = convertToJson(new ODataException("Registro duplicado"));
				var response = ODataResponse.entity(objResponse).status(HttpStatusCodes.NOT_ACCEPTABLE).build();
				return response;
			} else {
				throw e;
			}
		} catch (ODataException e) {
			log.error("createEntity", e);
			var objResponse = convertToJson(new ODataException(e.getMessage()));
			var response = ODataResponse.entity(objResponse).status(HttpStatusCodes.NOT_ACCEPTABLE).build();
			return response;
		} finally {
			close();
		}
		return oDataResponse;
	}

	@Override
	public ODataResponse updateEntity(final PutMergePatchUriInfo uriParserResultView, final InputStream content,
			final String requestContentType, final boolean merge, final String contentType) throws ODataException {
		ODataResponse oDataResponse = null;
		try {
			oDataJPAContext.setODataContext(getContext());
			Map<String, Object> object = contentToMap(uriParserResultView, content, requestContentType);
			Object virtualEntity = customJpaProcessor.toEntity(uriParserResultView, object);
			customValidations(virtualEntity);
			Object jpaEntity = processorExits.updateEntity(virtualEntity);
			if (jpaEntity == null) {
				jpaEntity = jpaProcessor.process(uriParserResultView, object);
			}
			oDataResponse = responseBuilder.build(uriParserResultView, jpaEntity);
		} catch (RollbackException e) {
			if (e.getCause() != null && e.getCause().getCause() != null && CONSTRAINT_VIOLATION_EXCEP_ARRAY
					.contains(e.getCause().getCause().getCause().getClass().getSimpleName())) {
				var objResponse = convertToJson(new ODataException("Registro duplicado"));
				var response = ODataResponse.entity(objResponse).status(HttpStatusCodes.NOT_ACCEPTABLE).build();
				return response;
			} else {
				throw e;
			}
		} catch (ODataException e) {
			log.error("updateEntity", e);
			var objResponse = convertToJson(new ODataException(e.getMessage()));
			var response = ODataResponse.entity(objResponse).status(HttpStatusCodes.NOT_ACCEPTABLE).build();
			return response;
		} finally {
			close();
		}
		return oDataResponse;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ODataResponse deleteEntity(final DeleteUriInfo uriParserResultView, final String contentType)
			throws ODataException {
		ODataResponse response = null;
		JPAEdmMappingImpl mapping = ((JPAEdmMappingImpl) uriParserResultView.getTargetEntitySet().getEntityType()
				.getMapping());
		EdmType type = uriParserResultView.getTargetType();
		try {
			String name = type.getName();
			Class<?> act = mapping.getJPAType();
			Class<?> superAct = act.getSuperclass();
			Class<?> superSuperAct = superAct.getSuperclass();
			if (superAct == BasicMasDataEntity.class
					|| superAct == ValidityBasicEntity.class || superSuperAct == BasicMasDataEntity.class) {
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
			log.info("No se ha podido eliminar la entidad");
			response = ODataResponse.entity("error").status(HttpStatusCodes.NOT_ACCEPTABLE).build();
		}
		return response;
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
				try {
					batchResponseParts.add(handler.handleBatchPart(batchPart));
				} catch (ODataException e) {
					if (e.getCause() != null && e.getCause().getCause() != null
							&& e.getCause().getCause().getCause() != null && CONSTRAINT_VIOLATION_EXCEP_ARRAY
									.contains(e.getCause().getCause().getCause().getClass().getSimpleName())) {
						var objResponse = convertToJson(new ODataException("Registro duplicado"));
						var response = ODataResponse.entity(objResponse).status(HttpStatusCodes.NOT_ACCEPTABLE).build();
						List<ODataResponse> responses = new ArrayList<ODataResponse>();
						responses.add(response);
						BatchResponsePart brp = BatchResponsePart.responses(responses).build();
						batchResponseParts.add(brp);
					} else {
						throw e;
					}
				}
			}
			batchResponse = EntityProvider.writeBatchResponse(batchResponseParts);
		} finally {
			close(true);
		}
		return batchResponse;
	}

	@Override
	public BatchResponsePart executeChangeSet(final BatchHandler handler, final List<ODataRequest> requests)
			throws ODataException {
		List<ODataResponse> responses = new ArrayList<ODataResponse>();
		try {
			oDataJPAContext.getODataJPATransaction().begin();
			for (ODataRequest request : requests) {
				oDataJPAContext.setODataContext(getContext());
				ODataRequest finalRequest = modifyRequest(request);
				ODataResponse response = handler.handleRequest(finalRequest);
				if (response.getStatus().getStatusCode() >= HttpStatusCodes.BAD_REQUEST.getStatusCode()) {
					// Rollback
					oDataJPAContext.getODataJPATransaction().rollback();
					List<ODataResponse> errorResponses = new ArrayList<ODataResponse>(1);
					errorResponses.add(response);
					return BatchResponsePart.responses(errorResponses).changeSet(false).build();
				}
				responses.add(response);
			}
			oDataJPAContext.getODataJPATransaction().commit();
			postProcessor(traceObjects);
			processorExits.executeChangeSet(traceObjects);
			return BatchResponsePart.responses(responses).changeSet(true).build();
		} catch (Exception e) {
			throw new ODataException("Error on processing request content:" + e.getMessage(), e);
		} finally {
			close(true);
		}
	}

	protected void postProcessor(Map<String, BasicEntity> traceObjects) {
		log.info("No se ha definido implementación en postProcessor");
	}

	@Override
	public ODataResponse readMetadata(final GetMetadataUriInfo uriInfo, final String contentType)
			throws ODataException {
		ODataJPAEdmProvider provider = new ODataJPAEdmProvider(oDataJPAContext);
		ODataEdmMetadataImpl edmServiceMetadata = new ODataEdmMetadataImpl(provider);
		return ODataResponse.status(HttpStatusCodes.OK)
				.header(ODataHttpHeaders.DATASERVICEVERSION, edmServiceMetadata.getDataServiceVersion())
				.entity(edmServiceMetadata.getMetadata()).build();
	}

	/**
	 *
	 * Modificamos el request para setear el ID correcto en lugar del temporal
	 *
	 * @param request
	 * @return
	 * @throws ODataException
	 */
	private ODataRequest modifyRequest(ODataRequest request) throws ODataException {

		List<PathSegment> odataSegments = request.getPathInfo().getODataSegments();
		String entityId = replaceTemporalID(odataSegments);
		if (!odataSegments.isEmpty() && odataSegments.get(0).getPath().contains(ODataUriUtils.PREFIX_ID_TEMPORAL)) {
			if (entityId == null)
				return request;
		} else
			return request;
		PathInfoImpl pathInfo = ODataUriUtils.createPathInfo(request, entityId, odataSegments);
		ODataRequest modifiedRequest = ODataRequest.fromRequest(request).pathInfo(pathInfo).build();
		return modifiedRequest;
	}

	/**
	 *
	 * Obtenemos el ID en base a la operacion anterior
	 *
	 * @param pathSegment
	 * @return
	 * @throws UriSyntaxException
	 */
	private String replaceTemporalID(List<PathSegment> pathSegment) throws UriSyntaxException {
		String entityPath = ODataUriUtils.getEntityFromURI(pathSegment);
		if (traceObjects.containsKey(entityPath)) {
			BasicEntity entity = traceObjects.get(entityPath);
			return entityPath + "(" + String.valueOf(entity.getId()) + "L)";
		}
		return null;
	}

	/**
	 *
	 * Almacenamos todos los objetos creados
	 *
	 * @param entitySet
	 * @param createdJpaEntity
	 * @throws EdmException
	 */
	protected void saveTraceObject(EdmEntitySet entitySet, Object createdJpaEntity) throws EdmException {
		BasicEntity entity = (BasicEntity) createdJpaEntity;
		traceObjects.put(entitySet.getName(), entity);
	}

	/**
	 *
	 * @param createView
	 * @param content
	 * @param requestContentType
	 * @return
	 * @throws EdmException
	 * @throws ODataBadRequestException
	 */
	protected Map<String, Object> contentToMap(PostUriInfo createView, InputStream content, String requestContentType)
			throws EdmException, ODataBadRequestException {
		final EdmEntitySet entitySet = createView.getTargetEntitySet();
		final EdmEntityType entityType = entitySet.getEntityType();
		final ODataEntityParser oDataEntityParser = new ODataEntityParser(oDataJPAContext);
		final ODataEntry odataEntry = oDataEntityParser.parseEntry(entitySet, content, requestContentType, false);
		Map<String, Object> oDataEntryProperties = odataEntry.getProperties();
		mapNavigationProperties(createView, entitySet, entityType, oDataEntryProperties);
		return oDataEntryProperties;
	}

	/**
	 *
	 * @param updateView
	 * @param content
	 * @param requestContentType
	 * @return
	 * @throws EdmException
	 * @throws ODataBadRequestException
	 */
	protected Map<String, Object> contentToMap(PutMergePatchUriInfo updateView, InputStream content,
			String requestContentType) throws EdmException, ODataBadRequestException {
		final EdmEntitySet entitySet = updateView.getTargetEntitySet();
		final EdmEntityType entityType = entitySet.getEntityType();
		final ODataEntityParser oDataEntityParser = new ODataEntityParser(oDataJPAContext);
		final ODataEntry odataEntry = oDataEntityParser.parseEntry(entitySet, content, requestContentType, false);
		Map<String, Object> oDataEntryProperties = odataEntry.getProperties();
		mapNavigationProperties(updateView, entitySet, entityType, oDataEntryProperties);
		handleForeignKeys(updateView, entitySet, entityType, oDataEntryProperties);
		return oDataEntryProperties;
	}

	private void handleForeignKeys(PutMergePatchUriInfo updateView, EdmEntitySet entitySet, EdmEntityType entityType,
			Map<String, Object> oDataEntryProperties) {
		for (Entry<String, Object> property : oDataEntryProperties.entrySet()) {
			try {
				if (!property.getKey().toLowerCase().contains("fk"))
					continue;
				EdmTyped edmTyped = entityType.getProperty(property.getKey());
				EdmMultiplicity multiplicity = edmTyped.getMultiplicity();
				if (multiplicity.equals(EdmMultiplicity.ZERO_TO_ONE)) {
					if (edmTyped instanceof EdmSimplePropertyImplProv) {
						EdmSimplePropertyImplProv simpleProperty = (EdmSimplePropertyImplProv) edmTyped;
						JPAEdmMappingImpl mapping = (JPAEdmMappingImpl) simpleProperty.getMapping();
						Class<?> clazz = mapping.getJPAType();
						Optional<BasicEntity> entity = findById(clazz, (Long) property.getValue());
						if (entity.isPresent()) {
							for (String propertyName : entityType.getNavigationPropertyNames()) {
								EdmNavigationProperty navProperty = (EdmNavigationProperty) entityType
										.getProperty(propertyName);
								EdmAssociationSet association = entitySet.getEntityContainer()
										.getAssociationSet(entitySet, navProperty);
								EdmReferentialConstraint constrain = association.getAssociation()
										.getReferentialConstraint();
								if (constrain != null) {
									List<String> refNames = constrain.getDependent().getPropertyRefNames();
									if (refNames.contains(property.getKey()))
										oDataEntryProperties.put(propertyName, entity.get());
								}
							}
						}
					}
				}
			} catch (EdmException e) {
				log.info("No se ha podido establecer la asociacion mediante clave foránea.");
			}
		}
	}

	/**
	 *
	 * Establecemos el navigation property con la entidad creada para el deep entity
	 *
	 * @param createView
	 *
	 * @param entitySet
	 * @param entityType
	 * @param properties
	 * @throws EdmException
	 */
	private void mapNavigationProperties(PostUriInfo createView, EdmEntitySet entitySet, EdmEntityType entityType,
			Map<String, Object> properties) throws EdmException {
		for (String propertyName : entityType.getNavigationPropertyNames()) {
			EdmNavigationProperty navProperty = (EdmNavigationProperty) entityType.getProperty(propertyName);
			String destEntitySetName = entitySet.getRelatedEntitySet(navProperty).getName();
			String startEntitySet = createView.getStartEntitySet().getName();
			if (traceObjects.containsKey(destEntitySetName)) {
				properties.put(propertyName, traceObjects.get(destEntitySetName));
			} else if (startEntitySet.equals(destEntitySetName)) {
				Optional<BasicEntity> entity = getEntityById(createView);
				if (entity.isPresent())
					properties.put(propertyName, entity.get());
			}
		}
	}

	/**
	 *
	 * Establecemos el navigation property con la entidad creada para el deep entity
	 *
	 * @param updateView
	 *
	 * @param entitySet
	 * @param entityType
	 * @param properties
	 * @throws EdmException
	 */
	private void mapNavigationProperties(PutMergePatchUriInfo updateView, EdmEntitySet entitySet,
			EdmEntityType entityType, Map<String, Object> properties) throws EdmException {
		for (String propertyName : entityType.getNavigationPropertyNames()) {
			EdmNavigationProperty navProperty = (EdmNavigationProperty) entityType.getProperty(propertyName);
			EdmAssociationSet association = entitySet.getEntityContainer().getAssociationSet(entitySet, navProperty);
			EdmReferentialConstraint constrain = association.getAssociation().getReferentialConstraint();
			if (constrain != null) {
				String destEntitySetName = entitySet.getRelatedEntitySet(navProperty).getName();
				if (traceObjects.containsKey(destEntitySetName)) {
					properties.put(propertyName, traceObjects.get(destEntitySetName));
				}
			}
		}
	}

	/**
	 *
	 * @param createView
	 * @return
	 * @throws EdmException
	 */
	private Optional<BasicEntity> getEntityById(PostUriInfo createView) throws EdmException {
		EdmEntityType entityType = createView.getStartEntitySet().getEntityType();
		JPAEdmMappingImpl mapping = ((JPAEdmMappingImpl) entityType.getMapping());
		List<KeyPredicate> listKeys = createView.getKeyPredicates();
		if (listKeys.size() > 0) {
			KeyPredicate key = listKeys.get(0);
			String strId = key.getLiteral();
			Long lngId = Long.valueOf(strId);
			Class<?> entityClazz = mapping.getJPAType();
			return findById(entityClazz, lngId);
		}
		return Optional.empty();
	}

	/**
	 * Obtenemos una entidad por su ID
	 *
	 * @param entityClazz
	 * @param lngId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private Optional<BasicEntity> findById(Class<?> entityClazz, Long lngId) {
		repositorio = (JpaRepository<?, Long>) ContextProvider.getRepository(entityClazz);
		if (repositorio != null) {
			return (Optional<BasicEntity>) repositorio.findById(lngId);
		}
		return Optional.empty();
	}

	public ODataJPAProcessor getCustomJpaProcessor() {
		return customJpaProcessor;
	}

	public void setCustomJpaProcessor(ODataJPAProcessor customJpaProcessor) {
		this.customJpaProcessor = customJpaProcessor;
	}

	protected String convertToJson(Object object) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.writeValueAsString(object);
		} catch (JsonProcessingException e) {
			log.error("Errror al convertir el objeto en JSON (convertToJson)", e);
			return "";
		}
	}
	
	protected Date formatDate(Date date) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        return formatter.parse(formatter.format(date));
    }

    protected Date formatEndDate(Date date) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        Date resetDate = formatter.parse(formatter.format(date));
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(resetDate);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        return DateUtils.convertToDate(calendar);
    }
}
