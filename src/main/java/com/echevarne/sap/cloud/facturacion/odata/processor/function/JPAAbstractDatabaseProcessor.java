package com.echevarne.sap.cloud.facturacion.odata.processor.function;

import java.lang.reflect.Field;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TemporalType;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.From;

import com.echevarne.sap.cloud.facturacion.odata.jpa.annotation.AnalyticalParameter;
import com.echevarne.sap.cloud.facturacion.odata.processor.builders.JPASQLContext;
import com.echevarne.sap.cloud.facturacion.odata.processor.builders.JPASQLStatement;
import com.echevarne.sap.cloud.facturacion.odata.processor.contexts.JPASQLContextPlaceholder;
import com.echevarne.sap.cloud.facturacion.odata.processor.parser.JPASQLExpressionParser;
import com.echevarne.sap.cloud.facturacion.util.StringUtils;

import org.apache.olingo.odata2.api.edm.provider.EntityType;
import org.apache.olingo.odata2.api.exception.ODataException;
import org.apache.olingo.odata2.api.uri.info.GetEntitySetUriInfo;
import org.apache.olingo.odata2.jpa.processor.api.exception.ODataJPAModelException;
import org.apache.olingo.odata2.jpa.processor.core.model.JPAEdmMappingImpl;

public abstract class JPAAbstractDatabaseProcessor implements JPAODataDatabaseProcessor {

    static final String FUNC_NAME_PLACEHOLDER = "$PLACEHOLDER$";
    static final String PARAMETER_PLACEHOLDER = "$PARAMETER$";

    @Override
    public Expression<Boolean> createSearchWhereClause(CriteriaBuilder cb, CriteriaQuery<?> cq, From<?, ?> root,
            EntityType entityType) throws ODataException {
        // TODO Auto-generated method stub
        return null;
    }

    @SuppressWarnings("unchecked")
    public <T> List<T> executeQuery(GetEntitySetUriInfo uriInfo, JPASQLStatement statement,
            JPASQLContextPlaceholder context, JPADataBaseFunction jpaFunction, JPAEdmMappingImpl entityTarget,
            EntityManager em) throws ODataException {

        Class<?> clazz = entityTarget.getJPAType();
        final String queryString = generateQueryString(context, statement.toString(), jpaFunction);
        final Query functionQuery = em.createNativeQuery(queryString, clazz);
        fillParameters(context, statement, functionQuery);
        cleanContext();
        return functionQuery.getResultList();

    }

    private void cleanContext() {
        JPASQLContext.removeSQLContext();
        JPASQLExpressionParser.removePositionalParametersThreadLocal();
    }

    /**
     * @param contextType
     * @param jpqlContext
     * @param jpqlStatement
     * @param query
     */
    private Query fillParameters(JPASQLContextPlaceholder context, JPASQLStatement statement, Query query) {
        Map<String, Map<Integer, Object>> parameterizedMap = context.getParameterizedQueryMap();
        if (parameterizedMap != null && parameterizedMap.size() > 0) {
            for (Entry<String, Map<Integer, Object>> parameterEntry : parameterizedMap.entrySet()) {
                if (statement.toString().contains(parameterEntry.getKey())) {
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

    protected Long executeCountQuery(GetEntitySetUriInfo uriInfo, JPASQLStatement statement,
            JPASQLContextPlaceholder context, JPADataBaseFunction jpaFunction, JPAEdmMappingImpl entityTarget,
            EntityManager em) throws ODataException {

        Class<?> clazz = entityTarget.getJPAType();
        final String queryString = generateQueryString(context, statement.toString(), jpaFunction);
        final Query functionQuery = em.createNativeQuery(queryString, clazz);
        fillParameters(context, statement, functionQuery);
        cleanContext();
        return (Long) functionQuery.getSingleResult();

    }

    protected String generateQueryString(JPASQLContextPlaceholder context, final String queryPattern,
            final JPADataBaseFunction jpaFunction) throws ODataException {

        Map<String, List<Integer>> placeholderMap = context.getPlaceholderQueryMap();
        final StringBuilder parameterList = new StringBuilder();
        try {
            // int count = getActualCount(context);
            for (Field parameter : jpaFunction.getParameter()) {
                // count++;
                AnalyticalParameter analyticalParam = parameter.getDeclaredAnnotation(AnalyticalParameter.class);
                mapPlaceholderField(parameterList, parameter, analyticalParam, placeholderMap);
            }
        } catch (ODataJPAModelException e) {
            throw new ODataException(e);
        }
        return replacePlaceholder(queryPattern, parameterList);
    }

    private String replacePlaceholder(String queryPattern, StringBuilder parameterList) {
        String placeholder = StringUtils.EMPTY;
        if (!StringUtils.isNullOrEmpty(parameterList.toString())) {
            parameterList.deleteCharAt(0);
            placeholder = JPASQLStatement.DELIMITER.PARENTHESIS_LEFT + parameterList.toString()
                    + JPASQLStatement.DELIMITER.PARENTHESIS_RIGHT;
        }
        return queryPattern.replace(FUNC_NAME_PLACEHOLDER, placeholder).toString();
    }

    private void mapPlaceholderField(StringBuilder parameterList, Field field, AnalyticalParameter parameter,
            Map<String, List<Integer>> placeholderMap) {
        if (!placeholderMap.containsKey(field.getName()))
            return;
        parameterList.append(JPASQLStatement.DELIMITER.COMMA);
        parameterList.append(JPASQLStatement.KEYWORD.PLACEHOLDER);
        parameterList.append(JPASQLStatement.DELIMITER.PERIOD);
        parameterList.append(JPASQLStatement.DELIMITER.QUOTE + JPASQLStatement.FIELDS.PARAMETER_ENCODE);
        parameterList.append(parameter.name());
        parameterList.append(JPASQLStatement.FIELDS.PARAMETER_ENCODE + JPASQLStatement.DELIMITER.QUOTE);
        parameterList.append(JPASQLStatement.Operator.REF);
        mapPlaceholderParameters(parameterList, placeholderMap.get(field.getName()));
    }

    private void mapPlaceholderParameters(StringBuilder parameterList, List<Integer> list) {
        parameterList.append(JPASQLStatement.DELIMITER.SINGLE_QUOTE);
        for (Integer i : list) {
            parameterList.append(JPASQLStatement.FIELDS.PARAMETER + i);
            parameterList.append(JPASQLStatement.DELIMITER.COMMA);
        }
        parameterList.deleteCharAt(parameterList.length()-1);
        parameterList.append(JPASQLStatement.DELIMITER.SINGLE_QUOTE);
    }

    // private int getActualCount(JPASQLContextPlaceholder context) {
    // Map<String, Map<Integer, Object>> parameterizedMap =
    // context.getParameterizedQueryMap();
    // if (null != parameterizedMap && !parameterizedMap.isEmpty()) {
    // int index = 1;
    // int previousIndex = 1;
    // for (Entry<String, Map<Integer, Object>> parameter :
    // parameterizedMap.entrySet()) {
    // index = getIndexValue(parameter.getValue());
    // if (index > previousIndex) {
    // previousIndex = index;
    // }
    // }
    // return previousIndex;
    // }
    // return 0;
    // }

    // private int getIndexValue(Map<Integer, Object> map) {
    // int index = 1;
    // if (map != null) {
    // for (Entry<Integer, Object> entry : map.entrySet()) {
    // index = entry.getKey();
    // }
    // return index + 1;
    // } else {
    // return index;
    // }
    // }

}
