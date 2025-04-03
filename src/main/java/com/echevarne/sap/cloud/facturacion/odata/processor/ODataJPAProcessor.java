package com.echevarne.sap.cloud.facturacion.odata.processor;

import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.Tuple;

import org.apache.olingo.odata2.api.commons.InlineCount;
import org.apache.olingo.odata2.api.edm.EdmEntitySet;
import org.apache.olingo.odata2.api.edm.EdmEntityType;
import org.apache.olingo.odata2.api.edm.EdmException;
import org.apache.olingo.odata2.api.edm.EdmMapping;
import org.apache.olingo.odata2.api.ep.entry.ODataEntry;
import org.apache.olingo.odata2.api.exception.ODataBadRequestException;
import org.apache.olingo.odata2.api.exception.ODataException;
import org.apache.olingo.odata2.api.uri.SelectItem;
import org.apache.olingo.odata2.api.uri.UriInfo;
import org.apache.olingo.odata2.api.uri.info.GetEntitySetUriInfo;
import org.apache.olingo.odata2.api.uri.info.GetFunctionImportUriInfo;
import org.apache.olingo.odata2.api.uri.info.PostUriInfo;
import org.apache.olingo.odata2.api.uri.info.PutMergePatchUriInfo;
import org.apache.olingo.odata2.core.uri.UriInfoImpl;
import org.apache.olingo.odata2.jpa.processor.api.ODataJPAContext;
import org.apache.olingo.odata2.jpa.processor.api.ODataJPATombstoneContext;
import org.apache.olingo.odata2.jpa.processor.api.ODataJPATombstoneEntityListener;
import org.apache.olingo.odata2.jpa.processor.api.ODataJPATransaction;
import org.apache.olingo.odata2.jpa.processor.api.exception.ODataJPAModelException;
import org.apache.olingo.odata2.jpa.processor.api.exception.ODataJPARuntimeException;
import org.apache.olingo.odata2.jpa.processor.api.model.JPAEdmMapping;
import org.apache.olingo.odata2.jpa.processor.core.ODataEntityParser;
import org.apache.olingo.odata2.jpa.processor.core.access.data.JPAPage;
import org.apache.olingo.odata2.jpa.processor.core.access.data.JPAPage.JPAPageBuilder;
import org.apache.olingo.odata2.jpa.processor.core.access.data.JPAProcessorImpl;
import org.apache.olingo.odata2.jpa.processor.core.access.data.JPAQueryBuilder;
import org.apache.olingo.odata2.jpa.processor.core.model.JPAEdmMappingImpl;

import com.echevarne.sap.cloud.facturacion.odata.extension.utils.EntityUtils;
import com.echevarne.sap.cloud.facturacion.odata.jpa.annotation.CustomSelectEntity;
import com.echevarne.sap.cloud.facturacion.odata.jpa.annotation.JPANativeBuilder;
import com.echevarne.sap.cloud.facturacion.odata.jpa.annotation.OverrideEntity;
import com.echevarne.sap.cloud.facturacion.odata.jpa.jpql.CustomJPAQueryBuilder;
import com.echevarne.sap.cloud.facturacion.odata.processor.JPAPageCustom.JPAPageBuilderCustom;
import com.echevarne.sap.cloud.facturacion.odata.processor.JPAQueryBuilderCustom.JPAQueryInfoCustom;
import com.echevarne.sap.cloud.facturacion.odata.processor.requests.JPAFunctionRequestProcessor;
import com.echevarne.sap.cloud.facturacion.odata.processor.requests.JPARequestProcessor;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ODataJPAProcessor extends JPAProcessorImpl {

	private static final String DELTATOKEN = "!deltatoken";
	protected ODataJPAContext oDataJPAContext;
	protected EntityManager em;

	public ODataJPAProcessor(final ODataJPAContext oDataJPAContext) {
		super(oDataJPAContext);
		this.oDataJPAContext = oDataJPAContext;
		this.em = oDataJPAContext.getEntityManager();
	}

	@Override
	public Object process(final PostUriInfo createView, final Map<String, Object> content)
			throws ODataJPAModelException, ODataJPARuntimeException {
		return customProcessCreate(createView, null, content, null);
	}

	/* Process Create Entity Request */
	@Override
	public Object process(final PostUriInfo createView, final InputStream content, final String requestedContentType)
			throws ODataJPAModelException, ODataJPARuntimeException {
		return customProcessCreate(createView, content, null, requestedContentType);
	}

	/* Process Get Entity Set Request (Query) */
	@Override
	public List<Object> process(final GetEntitySetUriInfo uriParserResultView)
			throws ODataJPAModelException, ODataJPARuntimeException {
		if (isOverrideJPAQueryBuilder(uriParserResultView)) {
			return handleGetProcessor(uriParserResultView);
		} else{
			List<Object> result = null;
			if (uriParserResultView.getFunctionImport() != null) {
				return super.process((GetFunctionImportUriInfo) uriParserResultView);
			}
			InlineCount inlineCount = uriParserResultView.getInlineCount();
			final int top = uriParserResultView.getTop() == null ? 1 : uriParserResultView.getTop();
			boolean hasNoAllPages = inlineCount == null || !inlineCount.equals(InlineCount.ALLPAGES);
			if (top == 0 && hasNoAllPages) {
			  return new ArrayList<>();
			}
			try {
			  Map<String, String> customQueryOptions = uriParserResultView.getCustomQueryOptions();
			  String deltaToken = null;
			  if (customQueryOptions != null) {
				deltaToken = uriParserResultView.getCustomQueryOptions().get(DELTATOKEN);
			  }
			  if (deltaToken != null) {
				ODataJPATombstoneContext.setDeltaToken(deltaToken);
			  }
			  JPAEdmMapping mapping = (JPAEdmMapping) uriParserResultView.getTargetEntitySet().getEntityType().getMapping();
			  JPAQueryBuilderCustom queryBuilder = new JPAQueryBuilderCustom(oDataJPAContext);
			  JPAQueryInfoCustom queryInfo = queryBuilder.build(uriParserResultView);
			  Query query = queryInfo.getQuery();
			  query.setHint("javax.persistence.query.timeout", 300000);
			  ODataJPATombstoneEntityListener listener =
				  queryBuilder.getODataJPATombstoneEntityListener((UriInfo) uriParserResultView);
			  if (listener != null && (!queryInfo.isTombstoneQuery() && listener.isTombstoneSupported()) && !(isCustomSelectEntity(uriParserResultView) && uriParserResultView.getSelect().isEmpty())) {
				query.getResultList();
				List<Object> deltaResult = ODataJPATombstoneContext.getDeltaResult(((EdmMapping) mapping).getInternalName());
				result = handlePaging(deltaResult, uriParserResultView);
			  } else {
				result = handlePaging(query, uriParserResultView);
			  }
			  // crear nuevo deltaToken solo si la query no incluia uno
			  if (listener != null && listener.isTombstoneSupported() && deltaToken==null) {
				ODataJPATombstoneContext.setDeltaToken(listener.generateDeltaToken(result, query));
			  }
			  final UriInfoImpl info = (UriInfoImpl) uriParserResultView;			  
			  if (isOverrideEntity(uriParserResultView)){
				if(isCustomSelectEntity(uriParserResultView) && !uriParserResultView.getSelect().isEmpty()){
					Class<?> clazz = ((JPAEdmMappingImpl) uriParserResultView.getTargetEntitySet().getEntityType().getMapping()).getJPAType();
					info.setSelect(sortSelect(info.getSelect(), clazz.getDeclaredFields()));

					if(result != null && !result.isEmpty() && Tuple.class.isAssignableFrom(result.get(0).getClass())){
						List<SelectItem> select = uriParserResultView.getSelect();
						AtomicInteger counter = new AtomicInteger(1);
						result = result.stream().map(t -> {
							Object entry = null;
							try {
								Long id = Long.valueOf(String.valueOf(counter.getAndIncrement()));
								entry = clazz.getConstructor(select.getClass(), Tuple.class, Long.class).newInstance(select, t, id);
							} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
									 | InvocationTargetException | NoSuchMethodException | SecurityException e) {
								e.printStackTrace();
							}
							return entry;
						}).collect(Collectors.toList());
					}
				}
				if(result!= null && !result.isEmpty()){
					if (InlineCount.ALLPAGES.equals(uriParserResultView.getInlineCount())){
						boolean allResultsRead = (info.getSkip()==null ||info.getSkip()==0) && (info.getTop() == null || result.size()< info.getTop());
						if (!allResultsRead)
							customCount(uriParserResultView, queryBuilder);
						else{
							info.setCount(true);
							queryBuilder.setCountValue(info, result, result.size());
						}
					}else{
						info.setCount(true);
						queryBuilder.setCountValue(info, result, result.size());
					}
				}
			  } else if (InlineCount.ALLPAGES.equals(uriParserResultView.getInlineCount())){
					boolean allResultsRead = (info.getSkip()==null ||info.getSkip()==0) && (info.getTop() == null || result.size()< info.getTop());
					if (!allResultsRead)
						queryBuilder.getCount(uriParserResultView, result);
					else{
						info.setCount(true);
						queryBuilder.setCountValue(info, result, result.size());
					}
			  }
			  result = result == null ? new ArrayList<>() : result;
			} catch (EdmException | InstantiationException | IllegalAccessException e) {
			  throw ODataJPARuntimeException.throwException(
				  ODataJPARuntimeException.ERROR_JPQL_QUERY_CREATE, e);
			} catch (SecurityException | IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return result;
		}
	}

	private List<SelectItem> sortSelect(List<SelectItem> select, Field[] declaredFields) {
		List<SelectItem> selectAux = new ArrayList<>();
		for (Field field : declaredFields) {
            if (CustomJPAQueryBuilder.fieldInSelection(select, field)) {
                selectAux.add(getFieldInSelection(select, field));
            }
        }
		return selectAux;
	}

	private SelectItem getFieldInSelection(List<SelectItem> select, Field field){
        return select.stream().filter(s -> {
            try {
                return s.getProperty().getName().equals(field.getName());
            } catch (EdmException e) {
                e.printStackTrace();
                return false;
            }
        }).findFirst().get();
    }

	private void customCount(GetEntitySetUriInfo uriParserResultView, JPAQueryBuilderCustom queryBuilder) throws ODataJPARuntimeException{
		queryBuilder.getCustomCount(uriParserResultView);
	}

	private List<Object> handleGetProcessor(GetEntitySetUriInfo uriParserResultView) throws ODataJPARuntimeException {
		try {
			final JPARequestProcessor p = createProcessor(uriParserResultView);
			return p.retrieveData(uriParserResultView, em);
		} catch (ODataException e) {
			throw ODataJPARuntimeException.throwException(ODataJPARuntimeException.ERROR_JPQL_QUERY_CREATE, e);
		}
	}

	private JPARequestProcessor createProcessor(GetEntitySetUriInfo uriParserResultView) {
		// TODO implementar esto para que el request sea en base a anotaciones o algo
		return new JPAFunctionRequestProcessor();
	}

	private boolean isOverrideEntity(final GetEntitySetUriInfo uriParserResultView) {
		return entityHasAnnotation(uriParserResultView, OverrideEntity.class);
	}

	private boolean isCustomSelectEntity(final GetEntitySetUriInfo uriParserResultView) {
		return entityHasAnnotation(uriParserResultView, CustomSelectEntity.class);
	}

	private boolean isOverrideJPAQueryBuilder(final GetEntitySetUriInfo uriParserResultView) {
		return entityHasAnnotation(uriParserResultView, JPANativeBuilder.class);
	}

	private <A extends Annotation> boolean entityHasAnnotation(
			final GetEntitySetUriInfo uriParserResultView,
			final Class<A> annotationClass
	) {
		boolean hasAnnotation = false;
		try {
			final Class<?> entityClass = EntityUtils.getJpaEntity(uriParserResultView);
			hasAnnotation = entityClass.getDeclaredAnnotation(annotationClass) != null;
		} catch (EdmException e) {
			// Nothing to do
		}
		return hasAnnotation;
	}

	/**
	 * Convierte un contenido a una entidad conocida
	 */
	public Object toEntity(final PostUriInfo createView, final Map<String, Object> content)
			throws ODataJPAModelException, ODataJPARuntimeException {
		return convertToEntity(createView, null, content, null);
	}

	/**
	 * Convierte un contenido a una entidad conocida
	 */
	public Object toEntity(final PutMergePatchUriInfo updateView, final Map<String, Object> content)
			throws ODataJPAModelException, ODataJPARuntimeException {
		return convertToEntity(updateView, null, content, null);
	}

	/**
	 * Recupera los datos actuales de la entidad
	 */
	public Object readEntity(final PutMergePatchUriInfo updateView)
			throws ODataJPAModelException, ODataJPARuntimeException {
		return readEntity(new JPAQueryBuilder(oDataJPAContext).build(updateView));
	}

	/**
	 * Lectura del objeto
	 */
	private Object readEntity(final Query query) throws ODataJPARuntimeException {
		Object selectedObject = null;
		@SuppressWarnings("rawtypes")
		final List resultList = query.getResultList();
		if (!resultList.isEmpty()) {
			selectedObject = resultList.get(0);
		}
		return selectedObject;
	}

	/**
	 *
	 * Implementamos la personalización de la creación para crear los deep entities
	 * solo en caso de que sea necesario
	 *
	 * Esta clase es genérica (para todas las entidades) solo se deberian realizar
	 * ajustes mínimos
	 *
	 * @param createView
	 * @param content
	 * @param properties
	 * @param requestedContentType
	 * @return
	 * @throws ODataJPAModelException
	 * @throws ODataJPARuntimeException
	 */
	private Object customProcessCreate(final PostUriInfo createView, final InputStream content,
			final Map<String, Object> properties, final String requestedContentType)
			throws ODataJPAModelException, ODataJPARuntimeException {
		try {
			final EdmEntitySet oDataTargetEntitySet = createView.getTargetEntitySet();
			final EdmEntityType oDataEntityType = oDataTargetEntitySet.getEntityType();
			final CustomJPAEntity virtualJPAEntity = new CustomJPAEntity(oDataEntityType, oDataTargetEntitySet,
					oDataJPAContext);
			Object jpaEntity = null;

			if (content != null) {
				final ODataEntityParser oDataEntityParser = new ODataEntityParser(oDataJPAContext);
				final ODataEntry oDataEntry = oDataEntityParser.parseEntry(oDataTargetEntitySet, content,
						requestedContentType, false);
				virtualJPAEntity.create(oDataEntry);
			} else if (properties != null) {
				virtualJPAEntity.create(properties);
			} else {
				return null;
			}

			boolean isLocalTransaction = setTransaction();
			jpaEntity = virtualJPAEntity.getJPAEntity();

			em.persist(jpaEntity);
			if (em.contains(jpaEntity)) {
				if (isLocalTransaction) {
					oDataJPAContext.getODataJPATransaction().commit();
				}
				return jpaEntity;
			}
		} catch (ODataBadRequestException e) {
			throw ODataJPARuntimeException.throwException(ODataJPARuntimeException.ERROR_JPQL_QUERY_CREATE, e);
		} catch (EdmException e) {
			throw ODataJPARuntimeException.throwException(ODataJPARuntimeException.ERROR_JPQL_QUERY_CREATE, e);
		}
		return null;
	}

	/**
	 * @param createView
	 * @param content
	 * @param properties
	 * @param requestedContentType
	 * @return
	 * @throws ODataJPAModelException
	 * @throws ODataJPARuntimeException
	 */
	private Object convertToEntity(final PostUriInfo createView, final InputStream content,
			final Map<String, Object> properties, final String requestedContentType)
			throws ODataJPAModelException, ODataJPARuntimeException {
		try {

			final EdmEntitySet oDataTargetEntitySet = createView.getTargetEntitySet();
			final EdmEntityType oDataEntityType = oDataTargetEntitySet.getEntityType();
			final CustomJPAEntity virtualJPAEntity = new CustomJPAEntity(oDataEntityType, oDataTargetEntitySet,
					oDataJPAContext);

			if (content != null) {
				final ODataEntityParser oDataEntityParser = new ODataEntityParser(oDataJPAContext);
				final ODataEntry oDataEntry = oDataEntityParser.parseEntry(oDataTargetEntitySet, content,
						requestedContentType, false);
				virtualJPAEntity.create(oDataEntry);
			} else if (properties != null) {
				virtualJPAEntity.create(properties);
			} else {
				return null;
			}

			return virtualJPAEntity.getJPAEntity();

		} catch (ODataBadRequestException e) {
			throw ODataJPARuntimeException.throwException(ODataJPARuntimeException.ERROR_JPQL_QUERY_CREATE, e);
		} catch (EdmException e) {
			throw ODataJPARuntimeException.throwException(ODataJPARuntimeException.ERROR_JPQL_QUERY_CREATE, e);
		}
	}

	/**
	 * @param updateView
	 * @param content
	 * @param properties
	 * @param requestContentType
	 * @return
	 * @throws ODataJPAModelException
	 * @throws ODataJPARuntimeException
	 */
	private Object convertToEntity(final PutMergePatchUriInfo updateView, final InputStream content,
			final Map<String, Object> properties, final String requestContentType)
			throws ODataJPAModelException, ODataJPARuntimeException {

		Object jpaEntity = null;
		try {

			jpaEntity = readEntity(new JPAQueryBuilder(oDataJPAContext).build(updateView));

			if (jpaEntity == null) {
				throw ODataJPARuntimeException.throwException(ODataJPARuntimeException.RESOURCE_NOT_FOUND, null);
			}
			final EdmEntitySet oDataEntitySet = updateView.getTargetEntitySet();
			final EdmEntityType oDataEntityType = oDataEntitySet.getEntityType();
			final CustomJPAEntity virtualJPAEntity = new CustomJPAEntity(oDataEntityType, oDataEntitySet,
					oDataJPAContext);
			virtualJPAEntity.setJPAEntity(jpaEntity);
			if (properties != null) {
				virtualJPAEntity.update(properties);
			} else {
				return null;
			}

			return virtualJPAEntity.getJPAEntity();

		} catch (EdmException e) {
			throw ODataJPARuntimeException.throwException(ODataJPARuntimeException.ERROR_JPQL_QUERY_CREATE, e);
		}
	}

	private boolean setTransaction() {
		ODataJPATransaction transaction = oDataJPAContext.getODataJPATransaction();
		if (!transaction.isActive()) {
			transaction.begin();
			return true;
		}
		return false;
	}

	private List<Object> handlePaging(final List<Object> result, final GetEntitySetUriInfo uriParserResultView) {
		if (result == null) {
		  return null;
		}
		JPAPageBuilder pageBuilder = new JPAPageBuilder();
		pageBuilder.pageSize(oDataJPAContext.getPageSize())
			.entities(result)
			.skipToken(uriParserResultView.getSkipToken());

		// $top/$skip with $inlinecount case handled in response builder to avoid multiple DB call
		if (uriParserResultView.getSkip() != null) {
		  pageBuilder.skip(uriParserResultView.getSkip().intValue());
		}

		if (uriParserResultView.getTop() != null) {
		  pageBuilder.top(uriParserResultView.getTop().intValue());
		}

		JPAPage page = pageBuilder.build();
		oDataJPAContext.setPaging(page);

		return page.getPagedEntities();
	}

	private List<Object> handlePaging(final Query query, final GetEntitySetUriInfo uriParserResultView) {

		JPAPageBuilderCustom pageBuilder = new JPAPageBuilderCustom();
		pageBuilder.pageSize(oDataJPAContext.getPageSize()).query(query).skipToken(uriParserResultView.getSkipToken());

		// $top/$skip with $inlinecount case handled in response builder to avoid
		// multiple DB call
		if (uriParserResultView.getSkip() != null) {
			pageBuilder.skip(uriParserResultView.getSkip().intValue());
		}

		if (uriParserResultView.getTop() != null) {
			pageBuilder.top(uriParserResultView.getTop().intValue());
		}

		JPAPageCustom page = pageBuilder.build();
		oDataJPAContext.setPaging(page);

		return page.getPagedEntities();

	}

}
