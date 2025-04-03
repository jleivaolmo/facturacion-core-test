package com.echevarne.sap.cloud.facturacion.odata.processor;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TemporalType;

import org.apache.olingo.odata2.api.edm.EdmEntitySet;
import org.apache.olingo.odata2.api.edm.EdmException;
import org.apache.olingo.odata2.api.uri.UriInfo;
import org.apache.olingo.odata2.api.uri.info.DeleteUriInfo;
import org.apache.olingo.odata2.api.uri.info.GetEntityCountUriInfo;
import org.apache.olingo.odata2.api.uri.info.GetEntitySetCountUriInfo;
import org.apache.olingo.odata2.api.uri.info.GetEntitySetUriInfo;
import org.apache.olingo.odata2.api.uri.info.GetEntityUriInfo;
import org.apache.olingo.odata2.api.uri.info.PutMergePatchUriInfo;
import org.apache.olingo.odata2.core.uri.UriInfoImpl;
import org.apache.olingo.odata2.jpa.processor.api.ODataJPAContext;
import org.apache.olingo.odata2.jpa.processor.api.ODataJPAQueryExtensionEntityListener;
import org.apache.olingo.odata2.jpa.processor.api.ODataJPATombstoneEntityListener;
import org.apache.olingo.odata2.jpa.processor.api.exception.ODataJPAModelException;
import org.apache.olingo.odata2.jpa.processor.api.exception.ODataJPARuntimeException;
import org.apache.olingo.odata2.jpa.processor.api.jpql.JPQLContext;
import org.apache.olingo.odata2.jpa.processor.api.jpql.JPQLContextType;
import org.apache.olingo.odata2.jpa.processor.api.jpql.JPQLJoinContextView;
import org.apache.olingo.odata2.jpa.processor.api.jpql.JPQLJoinSelectSingleContextView;
import org.apache.olingo.odata2.jpa.processor.api.jpql.JPQLSelectContextView;
import org.apache.olingo.odata2.jpa.processor.api.jpql.JPQLSelectSingleContextView;
import org.apache.olingo.odata2.jpa.processor.api.jpql.JPQLStatement;
import org.apache.olingo.odata2.jpa.processor.api.model.JPAEdmMapping;
import org.apache.olingo.odata2.jpa.processor.core.ODataExpressionParser;
import org.apache.olingo.odata2.jpa.processor.core.model.JPAEdmMappingImpl;
import org.hibernate.query.criteria.internal.compile.CriteriaQueryTypeQueryAdapter;

import com.echevarne.sap.cloud.facturacion.odata.annotations.JPAExit;
import com.echevarne.sap.cloud.facturacion.odata.jpa.annotation.CustomSelectEntity;
import com.echevarne.sap.cloud.facturacion.odata.jpa.listeners.CustomQueryListener;
import com.echevarne.sap.cloud.facturacion.odata.processor.function.JPADefaultDatabaseProcessor;

import lombok.var;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JPAQueryBuilderCustom extends JPADefaultDatabaseProcessor {

    private static final String HANA_BUG_ENTITY_STR = "SolicitudesPrivada";

    enum UriInfoType {
        GetEntitySet, GetEntity, GetEntitySetCount, GetEntityCount, PutMergePatch, Delete
    }

    private EntityManager em = null;
    private int pageSize = 0;

    public JPAQueryBuilderCustom(ODataJPAContext odataJPAContext) {
        this.em = odataJPAContext.getEntityManager();
        this.pageSize = odataJPAContext.getPageSize();
    }

    public JPAQueryInfoCustom buildCustom(GetEntitySetUriInfo uriInfo) throws ODataJPARuntimeException {
        JPAQueryInfoCustom queryInfo = new JPAQueryInfoCustom();
        Query query = null;
        try {
            CustomQueryListener listener = new CustomQueryListener();
            query = listener.getQuery(uriInfo, em);
        } catch (Exception e) {
            throw ODataJPARuntimeException.throwException(ODataJPARuntimeException.ERROR_JPQL_QUERY_CREATE, e);
        } finally {
            JPQLContext.removeJPQLContext();
            ODataExpressionParser.removePositionalParametersThreadLocal();
        }
        queryInfo.setQuery(query);
        return queryInfo;

        // JPQLContextType contextType;
        // if (!uriInfo.getStartEntitySet().getName().equals(uriInfo.getTargetEntitySet().getName())) {
        //     contextType = JPQLContextType.JOIN;
        // } else {
        //     contextType = JPQLContextType.SELECT;
        // }
        // CustomJPQLContext jpqlContext = CustomJPQLContext.createBuilder(contextType, uriInfo).build();
        // CustomJPQLStatement jpqlStatement = CustomJPQLStatement.createBuilder(jpqlContext).build();
        // Query query = em.createQuery(jpqlStatement.toString(), entityClass);
        // getParameterizedQuery(contextType, jpqlContext, jpqlStatement, query);
        // return query;
    }

    public JPAQueryInfoCustom build(GetEntitySetUriInfo uriInfo) throws ODataJPARuntimeException, EdmException {
       JPAQueryInfoCustom queryInfo = new JPAQueryInfoCustom();
       Query query = null;
       Class<?> ec = getJpaEntity(uriInfo);
       try {
         ODataJPATombstoneEntityListener listener = getODataJPATombstoneEntityListener((UriInfo) uriInfo);
         if (listener != null) {
             if(!(hasSelectAnnotation(ec) && uriInfo.getSelect().isEmpty())){
                 query = listener.getQuery(uriInfo, em);
                 JPQLContext jpqlContext = JPQLContext.getJPQLContext();
                 query = getParameterizedQueryForListeners(jpqlContext, query);
             }
         }
         if (query == null) {
           query = buildQuery((UriInfo) uriInfo, UriInfoType.GetEntitySet);
         } else {
           queryInfo.setTombstoneQuery(true);
         }
       } catch (Exception e) {
         throw ODataJPARuntimeException.throwException(
             ODataJPARuntimeException.ERROR_JPQL_QUERY_CREATE, e);
       } finally {
         JPQLContext.removeJPQLContext();
         ODataExpressionParser.removePositionalParametersThreadLocal();
       }
       queryInfo.setQuery(query);
       return queryInfo;
     }

    @SuppressWarnings("unchecked")
	public void getCount(GetEntitySetUriInfo uriInfo, List<Object> result) throws ODataJPARuntimeException {
       JPAQueryInfoCustom queryInfo = new JPAQueryInfoCustom();
       Query query = null;
       UriInfoImpl info = (UriInfoImpl)uriInfo;
       boolean count = info.isCount();
       info.setCount(true);
       try {
         ODataJPAQueryExtensionEntityListener listener = getODataJPAQueryEntityListener((UriInfo) uriInfo);
         if (listener != null) {
           query = listener.getQuery((GetEntitySetCountUriInfo)uriInfo, em);
           if(query != null){
             JPQLContextType contextType = determineJPQLContextType(info, UriInfoType.GetEntitySetCount);
             JPQLContext jpqlContext = buildJPQLContext(contextType, info);
             JPQLStatement jpqlStatement = JPQLStatement.createBuilder(jpqlContext).build();
             jpqlContext.setJPQLStatement(jpqlStatement.toString());
             query = getParameterizedQueryForListeners(jpqlContext, query);
           }
         }
         if (query == null) {
           query = buildQuery((UriInfo) uriInfo, UriInfoType.GetEntitySetCount);
         } else {
           queryInfo.setTombstoneQuery(true);
         }
       } catch (Exception e) {
         throw ODataJPARuntimeException.throwException(
             ODataJPARuntimeException.ERROR_JPQL_QUERY_CREATE, e);
       } finally {
         JPQLContext.removeJPQLContext();
         ODataExpressionParser.removePositionalParametersThreadLocal();
       }
       queryInfo.setQuery(query);
       Query countQuery = queryInfo.getQuery();
       List<Object> countList = countQuery.getResultList();
       info.setCount(count);
       if(countList!= null && !countList.isEmpty()){
         String countNumber = countList.get(0).toString();
         setCountValue(info, result, Integer.valueOf(countNumber));
       }
    }
    
    public void setCountValue(UriInfoImpl info, List<Object> result, int count) {
        //Si se van a mostrar los comodines, se han de añadir elementos al contador
        int numComodines = getWildcardsNumber(result);
        String countTotal = String.valueOf(count + numComodines);
        Map<String, String> customQueryOptions = new HashMap<String, String>();
        customQueryOptions.put("count", countTotal);
        info.setCustomQueryOptions(customQueryOptions);    	
    }

	private int getWildcardsNumber(List<Object> listEntities) {
		int result = 0;
		if (listEntities.size() > 0) {
			var obj = listEntities.get(0);
			Class<?> clazz = obj.getClass();
			JPAExit jpaExit = clazz.getAnnotation(JPAExit.class);
			if (jpaExit != null) {
				if (jpaExit.allowAll()) {
					result++;
				}
				if (jpaExit.allowEmpty()) {
					result++;
				}
			}
		}
		return result;
	}

	@SuppressWarnings("unchecked")
    public void getCustomCount(GetEntitySetUriInfo uriInfo) throws ODataJPARuntimeException {
        JPAQueryInfoCustom queryInfo = new JPAQueryInfoCustom();
        Query query = null;
        UriInfoImpl info = (UriInfoImpl) uriInfo;
        boolean count = info.isCount();
        info.setCount(true);
        try {
            ODataJPAQueryExtensionEntityListener listener = getODataJPAQueryEntityListener((UriInfo) uriInfo);
            if (listener != null) {
                query = listener.getQuery(uriInfo, em);
                if (query != null) {
                    JPQLContextType contextType = determineJPQLContextType(info, UriInfoType.GetEntitySetCount);
                    JPQLContext jpqlContext = buildJPQLContext(contextType, info);
                    JPQLStatement jpqlStatement = JPQLStatement.createBuilder(jpqlContext).build();
                    jpqlContext.setJPQLStatement(jpqlStatement.toString());
                    query = getParameterizedQueryForListeners(jpqlContext, query);
                }
            }
            if (query == null) {
                query = buildQuery((UriInfo) uriInfo, UriInfoType.GetEntitySetCount);
            } else {
                queryInfo.setTombstoneQuery(true);
            }
        } catch (Exception e) {
            throw ODataJPARuntimeException.throwException(ODataJPARuntimeException.ERROR_JPQL_QUERY_CREATE, e);
        } finally {
            JPQLContext.removeJPQLContext();
            ODataExpressionParser.removePositionalParametersThreadLocal();
        }
        queryInfo.setQuery(query);
        Query countQuery = queryInfo.getQuery();
        List<Object> countList = countQuery.getResultList();
        info.setCount(count);
        if (countList != null && !countList.isEmpty()) {
            String countNumber = String.valueOf(countList.size());
            Map<String, String> customQueryOptions = new HashMap<String, String>();
            customQueryOptions.put("count", countNumber);
            info.setCustomQueryOptions(customQueryOptions);
        }
    }

    public Query build(GetEntityUriInfo uriInfo) throws ODataJPARuntimeException {
        Query query = null;
        try {
            ODataJPAQueryExtensionEntityListener listener = getODataJPAQueryEntityListener((UriInfo) uriInfo);
            if (listener != null) {
                query = listener.getQuery(uriInfo, em);
                JPQLContext jpqlContext = JPQLContext.getJPQLContext();
                query = getParameterizedQueryForListeners(jpqlContext, query);
            }
            if (query == null) {
                query = buildQuery((UriInfo) uriInfo, UriInfoType.GetEntity);
            }
        } catch (Exception e) {
            throw ODataJPARuntimeException.throwException(ODataJPARuntimeException.ERROR_JPQL_QUERY_CREATE, e);
        } finally {
            JPQLContext.removeJPQLContext();
            ODataExpressionParser.removePositionalParametersThreadLocal();
        }
        return query;
    }

    public Query build(GetEntitySetCountUriInfo uriInfo) throws ODataJPARuntimeException {
        Query query = null;
        try {
            ODataJPAQueryExtensionEntityListener listener = getODataJPAQueryEntityListener((UriInfo) uriInfo);
            if (listener != null) {
                query = listener.getQuery(uriInfo, em);
                JPQLContext jpqlContext = JPQLContext.getJPQLContext();
                query = getParameterizedQueryForListeners(jpqlContext, query);
            }
            if (query == null) {
                query = buildQuery((UriInfo) uriInfo, UriInfoType.GetEntitySetCount);
            }
        } catch (Exception e) {
            throw ODataJPARuntimeException.throwException(ODataJPARuntimeException.ERROR_JPQL_QUERY_CREATE, e);
        } finally {
            JPQLContext.removeJPQLContext();
            ODataExpressionParser.removePositionalParametersThreadLocal();
        }
        return query;
    }

    public Query build(GetEntityCountUriInfo uriInfo) throws ODataJPARuntimeException {
        Query query = null;
        try {
            ODataJPAQueryExtensionEntityListener listener = getODataJPAQueryEntityListener((UriInfo) uriInfo);
            if (listener != null) {
                query = listener.getQuery(uriInfo, em);
                JPQLContext jpqlContext = JPQLContext.getJPQLContext();
                query = getParameterizedQueryForListeners(jpqlContext, query);
            }
            if (query == null) {
                query = buildQuery((UriInfo) uriInfo, UriInfoType.GetEntityCount);
            }
        } catch (Exception e) {
            throw ODataJPARuntimeException.throwException(ODataJPARuntimeException.ERROR_JPQL_QUERY_CREATE, e);
        } finally {
            JPQLContext.removeJPQLContext();
            ODataExpressionParser.removePositionalParametersThreadLocal();
        }
        return query;
    }

    public Query build(DeleteUriInfo uriInfo) throws ODataJPARuntimeException {
        Query query = null;
        try {
            ODataJPAQueryExtensionEntityListener listener = getODataJPAQueryEntityListener((UriInfo) uriInfo);
            if (listener != null) {
                query = listener.getQuery(uriInfo, em);
                JPQLContext jpqlContext = JPQLContext.getJPQLContext();
                query = getParameterizedQueryForListeners(jpqlContext, query);
            }
            if (query == null) {
                query = buildQuery((UriInfo) uriInfo, UriInfoType.Delete);
            }
        } catch (Exception e) {
            throw ODataJPARuntimeException.throwException(ODataJPARuntimeException.ERROR_JPQL_QUERY_CREATE, e);
        } finally {
            JPQLContext.removeJPQLContext();
            ODataExpressionParser.removePositionalParametersThreadLocal();
        }
        return query;
    }

    public Query build(PutMergePatchUriInfo uriInfo) throws ODataJPARuntimeException {
        Query query = null;
        try {
            ODataJPAQueryExtensionEntityListener listener = getODataJPAQueryEntityListener((UriInfo) uriInfo);
            if (listener != null) {
                query = listener.getQuery(uriInfo, em);
                JPQLContext jpqlContext = JPQLContext.getJPQLContext();
                query = getParameterizedQueryForListeners(jpqlContext, query);
            }
            if (query == null) {
                query = buildQuery((UriInfo) uriInfo, UriInfoType.PutMergePatch);
            }
        } catch (Exception e) {
            throw ODataJPARuntimeException.throwException(ODataJPARuntimeException.ERROR_JPQL_QUERY_CREATE, e);
        } finally {
            JPQLContext.removeJPQLContext();
            ODataExpressionParser.removePositionalParametersThreadLocal();
        }
        return query;
    }

    private Query buildQuery(UriInfo uriParserResultView, UriInfoType type)
            throws EdmException, ODataJPAModelException, ODataJPARuntimeException {

        JPQLContextType contextType = determineJPQLContextType(uriParserResultView, type);
        JPQLContext jpqlContext = buildJPQLContext(contextType, uriParserResultView);
        JPQLStatement jpqlStatement = JPQLStatement.createBuilder(jpqlContext).build();
        String queryStr = jpqlStatement.toString();

        Query query = em.createQuery(queryStr);
        modifyQueryToAvoidHanaBugIfNeeded(query);
        getParameterizedQuery(contextType, jpqlContext, jpqlStatement, query);
        return query;
    }

    /*
    Adds join to the query identified, that causes sometimes a bug in SAP HANA, forzing HANA to use another
    query plan.
    The bug has been reported to SAP: https://launchpad.support.sap.com/#/incident/pointer/002075129500005993702022
     */
	private Query modifyQueryToAvoidHanaBugIfNeeded(Query inputQuery) {
		var query = inputQuery.unwrap(org.hibernate.query.Query.class);
		if (query.getQueryString().contains(HANA_BUG_ENTITY_STR))
			if (query.getQueryString().contains("JOIN")) {
				query.addQueryHint("NO_SUBPLAN_SHARING");
			} else if (!query.getQueryString().contains("COUNT")) {
				EntityGraph<?> namedEntityGraph = em.getEntityGraph("privados-inicial-graph");
				query.setHint("javax.persistence.fetchgraph", namedEntityGraph);
				query.setHint("jakarta.persistence.fetchgraph", namedEntityGraph);
			}
		return query;
	}

    private Query getParameterizedQuery(JPQLContextType contextType, JPQLContext jpqlContext,
            JPQLStatement jpqlStatement, Query query) {
        Map<String, Map<Integer, Object>> parameterizedMap = null;
        if (contextType == JPQLContextType.JOIN || contextType == JPQLContextType.JOIN_COUNT) {
            parameterizedMap = ((JPQLJoinContextView) jpqlContext).getParameterizedQueryMap();
        } else if (contextType == JPQLContextType.JOIN_SINGLE) {
            parameterizedMap = ((JPQLJoinSelectSingleContextView) jpqlContext).getParameterizedQueryMap();
        } else if (contextType == JPQLContextType.SELECT || contextType == JPQLContextType.SELECT_COUNT) {
            parameterizedMap = ((JPQLSelectContextView) jpqlContext).getParameterizedQueryMap();
        } else if (contextType == JPQLContextType.SELECT_SINGLE) {
            parameterizedMap = ((JPQLSelectSingleContextView) jpqlContext).getParameterizedQueryMap();
        }
        if (parameterizedMap != null && parameterizedMap.size() > 0) {
        	Integer indexDatePeriod = 0;
            for (Entry<String, Map<Integer, Object>> parameterEntry : parameterizedMap.entrySet()) {
                if (jpqlStatement.toString().contains(parameterEntry.getKey())) {
                    Map<Integer, Object> positionalParameters = parameterEntry.getValue();
                    for (Entry<Integer, Object> param : positionalParameters.entrySet()) {
                        if (param.getValue() instanceof Calendar || param.getValue() instanceof Timestamp) {
                        	indexDatePeriod = formatBeginAndEndDates(indexDatePeriod, (Calendar) param.getValue());
                            query.setParameter(param.getKey(), (Calendar) param.getValue(), TemporalType.TIMESTAMP);
                        } else if (param.getValue() instanceof Time) {
                            query.setParameter(param.getKey(), (Time) param.getValue(), TemporalType.TIME);
                        } else {
                            if (param.getValue().toString().contains("*") && !param.getValue().toString().equals("*")) {
                                query.setParameter(param.getKey(), param.getValue().toString().replace('*', '%'));
                            } else {
                                query.setParameter(param.getKey(), param.getValue());
                            }
                        }
                    }
                }
            }
        }
        return query;
    }

    private Integer formatBeginAndEndDates(Integer index, Calendar value) {
    	log.info("Start formatBeginAndEndDates. Index:" + index + " Date:" + value);
    	if (index == 0) {
			value.set(Calendar.HOUR_OF_DAY, 00);
			value.set(Calendar.MINUTE, 00);
			value.set(Calendar.SECOND, 00);
		}
    	else if (index == 1) {
			value.set(Calendar.HOUR_OF_DAY, 23);
			value.set(Calendar.MINUTE, 59);
			value.set(Calendar.SECOND, 59);
		}
    	log.info("End formatBeginAndEndDates. Index:" + index + " Date:" + value);
    	index ++;
		return index;
	}

	private Query getParameterizedQueryForListeners(JPQLContext jpqlContext, Query query) {
        Map<String, Map<Integer, Object>> parameterizedMap = null;
        String jpqlStatement = null;
        if (jpqlContext instanceof JPQLJoinContextView) {
            parameterizedMap = ((JPQLJoinContextView) jpqlContext).getParameterizedQueryMap();
            jpqlStatement = jpqlContext.getJPQLStatement();
        } else if (jpqlContext instanceof JPQLJoinSelectSingleContextView) {
            parameterizedMap = ((JPQLJoinSelectSingleContextView) jpqlContext).getParameterizedQueryMap();
            jpqlStatement = jpqlContext.getJPQLStatement();
        } else if (jpqlContext instanceof JPQLSelectContextView) {
            parameterizedMap = ((JPQLSelectContextView) jpqlContext).getParameterizedQueryMap();
            jpqlStatement = jpqlContext.getJPQLStatement();
        } else if (jpqlContext instanceof JPQLSelectSingleContextView) {
            parameterizedMap = ((JPQLSelectSingleContextView) jpqlContext).getParameterizedQueryMap();
            jpqlStatement = jpqlContext.getJPQLStatement();
        }
        if (parameterizedMap != null && parameterizedMap.size() > 0 && !(query instanceof CriteriaQueryTypeQueryAdapter<?>)) {
            for (Entry<String, Map<Integer, Object>> parameterEntry : parameterizedMap.entrySet()) {
                if (jpqlStatement.contains(parameterEntry.getKey())) {
                    Map<Integer, Object> positionalParameters = parameterEntry.getValue();
                    for (Entry<Integer, Object> param : positionalParameters.entrySet()) {
                        if (param.getValue() instanceof Calendar || param.getValue() instanceof Timestamp) {
                            query.setParameter(param.getKey(), (Calendar) param.getValue(), TemporalType.TIMESTAMP);
                        } else if (param.getValue() instanceof Time) {
                            query.setParameter(param.getKey(), (Time) param.getValue(), TemporalType.TIME);
                        } else {
                            query.setParameter(param.getKey(), param.getValue());
                        }
                    }
                }
            }
        }
        return query;
    }

    public ODataJPAQueryExtensionEntityListener getODataJPAQueryEntityListener(UriInfo uriInfo)
            throws EdmException, InstantiationException, IllegalAccessException {
        ODataJPAQueryExtensionEntityListener queryListener = null;
        ODataJPATombstoneEntityListener listener = getODataJPATombstoneEntityListener(uriInfo);
        if (listener instanceof ODataJPAQueryExtensionEntityListener) {
            queryListener = (ODataJPAQueryExtensionEntityListener) listener;
        }
        return queryListener;
    }

    public ODataJPATombstoneEntityListener getODataJPATombstoneEntityListener(UriInfo uriParserResultView)
            throws InstantiationException, IllegalAccessException, EdmException {
        JPAEdmMapping mapping = (JPAEdmMapping) uriParserResultView.getTargetEntitySet().getEntityType().getMapping();
        if (null != mapping && mapping.getODataJPATombstoneEntityListener() != null) {
            return (ODataJPATombstoneEntityListener) mapping.getODataJPATombstoneEntityListener().newInstance();
        }
        return null;
    }

    public JPQLContext buildJPQLContext(JPQLContextType contextType, UriInfo uriParserResultView)
            throws ODataJPAModelException, ODataJPARuntimeException {
        if (pageSize > 0 && (contextType == JPQLContextType.SELECT || contextType == JPQLContextType.JOIN)) {
            return JPQLContext.createBuilder(contextType, uriParserResultView, true).build();
        } else {
            return JPQLContext.createBuilder(contextType, uriParserResultView).build();
        }
    }

    public JPQLContextType determineJPQLContextType(UriInfo uriParserResultView, UriInfoType type) {
        JPQLContextType contextType = null;

        if (uriParserResultView.getNavigationSegments() != null
                && !uriParserResultView.getNavigationSegments().isEmpty()) {
            if (type == UriInfoType.GetEntitySet) {
                contextType = JPQLContextType.JOIN;
            } else if (type == UriInfoType.Delete || type == UriInfoType.GetEntity
                    || type == UriInfoType.PutMergePatch) {
                contextType = JPQLContextType.JOIN_SINGLE;
            } else if (type == UriInfoType.GetEntitySetCount || type == UriInfoType.GetEntityCount) {
                contextType = JPQLContextType.JOIN_COUNT;
            }
        } else {
            if (type == UriInfoType.GetEntitySet) {
                contextType = JPQLContextType.SELECT;
            } else if (type == UriInfoType.Delete || type == UriInfoType.GetEntity
                    || type == UriInfoType.PutMergePatch) {
                contextType = JPQLContextType.SELECT_SINGLE;
            } else if (type == UriInfoType.GetEntitySetCount || type == UriInfoType.GetEntityCount) {
                contextType = JPQLContextType.SELECT_COUNT;
            }
        }
        return contextType;
    }

    private static final Pattern NORMALIZATION_NEEDED_PATTERN = Pattern.compile(".*[\\s(](\\S+\\.\\S+\\.\\S+).*");
    private static final Pattern VALUE_NORM_PATTERN = Pattern.compile("(?:^|\\s|\\()'(([^']*)')");
    private static final Pattern JOIN_ALIAS_PATTERN = Pattern.compile(".*\\sJOIN\\s(\\S*\\s\\S*).*");

    /**
     * Check if the statement contains ORDERBY having x.y.z kind of format It will
     * remove those values before checking for normalization and later added back
     */
    private static String removeExtraClause(String jpqlQuery) {
        String query = jpqlQuery;
        if (query.contains(JPQLStatement.KEYWORD.ORDERBY)) {
            int index = query.indexOf(JPQLStatement.KEYWORD.ORDERBY);
            query = query.substring(0, index);
        }
        return query;
    }

    /**
     * Check if the statement contains string values having x.y.z kind of format It
     * will replace those values with parameters before checking for normalization
     * and later added back
     */
    private static String checkConditionValues(String jpqlQuery) {
        int i = 0;
        StringBuffer query = new StringBuffer();
        query.append(jpqlQuery);
        Matcher valueMatcher = VALUE_NORM_PATTERN.matcher(query);
        while (valueMatcher.find()) {
            String val = valueMatcher.group();
            int index = query.indexOf(val);
            String var = "[" + ++i + "] ";
            query.replace(index, index + val.length(), var);
            valueMatcher = VALUE_NORM_PATTERN.matcher(query);
        }
        return query.toString();
    }

    private static int ordinalIndexOf(String str, char s, int n) {
        int pos = str.indexOf(s, 0);
        while (n-- > 0 && pos != -1) {
            pos = str.indexOf(s, pos + 1);
        }
        return pos;
    }

    final class JPAQueryInfoCustom {
        private Query query = null;
        private boolean isTombstoneQuery = false;

        public Query getQuery() {
            return query;
        }

        public void setQuery(Query query) {
            this.query = query;
        }

        public boolean isTombstoneQuery() {
            return isTombstoneQuery;
        }

        public void setTombstoneQuery(boolean isTombstoneQuery) {
            this.isTombstoneQuery = isTombstoneQuery;
        }
    }

    /**
     *
     * @param uriInfo
     * @return
     * @throws EdmException
     */
    private Class<?> getJpaEntity(GetEntitySetUriInfo uriInfo) throws EdmException {
        return getJpaEntity(uriInfo.getTargetEntitySet());
    }

    /**
     *
     * @param entitySet
     * @return
     * @throws EdmException
     */
    private Class<?> getJpaEntity(EdmEntitySet entitySet) throws EdmException {
        return ((JPAEdmMappingImpl) entitySet.getEntityType().getMapping()).getJPAType();
    }

    private boolean hasSelectAnnotation(Class<?> ec) {
        return ec.getDeclaredAnnotation(CustomSelectEntity.class)!=null;
    }
}
