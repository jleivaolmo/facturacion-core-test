package com.echevarne.sap.cloud.facturacion.odata.processor.contexts;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import javax.persistence.Table;

import com.echevarne.sap.cloud.facturacion.odata.jpa.expressions.GroupByExpression;
import com.echevarne.sap.cloud.facturacion.odata.jpa.utils.ODataUtilsParser;
import com.echevarne.sap.cloud.facturacion.odata.processor.builders.JPASQLContext;
import com.echevarne.sap.cloud.facturacion.odata.processor.builders.JPASQLStatement;
import com.echevarne.sap.cloud.facturacion.odata.processor.parser.JPASQLExpressionParser;

import org.apache.olingo.odata2.api.edm.EdmEntityType;
import org.apache.olingo.odata2.api.edm.EdmException;
import org.apache.olingo.odata2.api.edm.EdmMapping;
import org.apache.olingo.odata2.api.exception.ODataException;
import org.apache.olingo.odata2.api.uri.expression.FilterExpression;
import org.apache.olingo.odata2.api.uri.info.GetEntitySetUriInfo;
import org.apache.olingo.odata2.jpa.processor.api.exception.ODataJPAModelException;
import org.apache.olingo.odata2.jpa.processor.api.exception.ODataJPARuntimeException;
import org.apache.olingo.odata2.jpa.processor.api.jpql.JPQLContextType;
import org.apache.olingo.odata2.jpa.processor.core.model.JPAEdmMappingImpl;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JPASQLContextPlaceholder extends JPASQLContext {

    protected GroupByExpression groupsExpressions;
    protected String groupByCollection;
    protected String selectExpression;
    protected String orderByCollection;
    protected String whereExpression;
    protected String placeholderExpression;
    protected Map<String, Map<Integer, Object>> parameterizedQueryMap;
    protected Map<String, List<Integer>> placeholderQueryMap;
    protected String jpqlStatement;

    protected boolean isCountOnly = false;

    public JPASQLContextPlaceholder(final boolean isCountOnly) {
        this.isCountOnly = isCountOnly;
    }

    public class JPASQLContextPlaceholderBuilder extends JPASQLContextBuilder {

        protected GetEntitySetUriInfo entitySetView;

        @Override
        public JPASQLContext build() throws ODataJPAModelException, ODataJPARuntimeException {
            if (entitySetView != null) {
                try {
                    if (isCountOnly)
                        setType(JPQLContextType.SELECT_COUNT);
                    else
                        setType(JPQLContextType.SELECT);
                    if (withPaging) {
                        setPagingRequested(withPaging);
                    }
                    EdmEntityType entityType = entitySetView.getTargetEntitySet().getEntityType();
                    EdmMapping mapping = entityType.getMapping();
                    if (mapping != null) {
                        setJpaEntityName(mapping.getInternalName());
                    } else {
                        setJpaEntityName(entityType.getName());
                    }
                    setJpaEntityAlias(generateJPAEntityAlias());
                    setDatabaseEntityName(generateDatabaseEntityName());
                    setOrderByCollection(generateOrderByFileds());
                    setGroupByCollection(generateGroupByFileds());
                    setSelectExpression(generateSelectExpression());
                    setWhereExpression(generateWhereExpression());
                    setPlaceholderExpression(generatePlaceholderExpression());
                    setSQLContext(JPASQLContextPlaceholder.this);

                } catch (ODataException e) {
                    throw ODataJPARuntimeException.throwException(ODataJPARuntimeException.INNER_EXCEPTION, e);
                }
            }

            return JPASQLContextPlaceholder.this;

        }

        @Override
        protected void setResultsView(final Object resultsView) {
            if (resultsView instanceof GetEntitySetUriInfo) {
                entitySetView = (GetEntitySetUriInfo) resultsView;
            }

        }

        private String generateDatabaseEntityName() {
            try {
                EdmEntityType entityType = entitySetView.getTargetEntitySet().getEntityType();
                Class<?> jpaType = ((JPAEdmMappingImpl) entityType.getMapping()).getJPAType();
                if (jpaType != null) {
                    Table tableAnnotation = jpaType.getDeclaredAnnotation(Table.class);
                    if (tableAnnotation != null) {
                        return tableAnnotation.name();
                    }
                }
                return getJpaEntityName();
            } catch (EdmException e) {
                return getJpaEntityName();
            }
        }

        /*
         * Generate Select Clause
         */
        protected String generateSelectExpression() throws EdmException {
            if (getGroupsExpressions() != null && !isCountOnly) {
                return getSelectFieldsWithGroupBy();
            } else {
                return getJpaEntityAlias();
            }
        }

        private String getSelectFieldsWithGroupBy() {
            try {
                EdmEntityType entityType = entitySetView.getTargetEntitySet().getEntityType();
                Class<?> jpaType = ((JPAEdmMappingImpl) entityType.getMapping()).getJPAType();
                return JPASQLStatement.KEYWORD.SELECT_NEW + JPASQLStatement.DELIMITER.SPACE + jpaType.getCanonicalName()
                        + JPASQLStatement.DELIMITER.PARENTHESIS_LEFT + getGroupByCollection()
                        + JPASQLStatement.DELIMITER.PARENTHESIS_RIGHT;
            } catch (EdmException e) {
                return getJpaEntityAlias();
            }
        }

        /*
         * Generate Order By Clause Fields
         */
        protected String generateOrderByFileds() throws ODataJPARuntimeException, EdmException {
            if (entitySetView.getOrderBy() != null && !isCountOnly) {
                EdmEntityType entityType = entitySetView.getTargetEntitySet().getEntityType();
                Class<?> jpaType = ((JPAEdmMappingImpl) entityType.getMapping()).getJPAType();
                return JPASQLExpressionParser.parseToJPAOrderByExpression(entitySetView.getOrderBy(),
                        getJpaEntityAlias(), jpaType);
            } else if ((entitySetView.getTop() != null || entitySetView.getSkip() != null || pagingRequested)
                    && !isCountOnly) {
                return JPASQLExpressionParser.parseKeyPropertiesToJPAOrderByExpression(
                        entitySetView.getTargetEntitySet().getEntityType().getKeyProperties(), getJpaEntityAlias());
            }
            return null;

        }

        /*
         * Generate Group By Clause Fields
         */
        protected String generateGroupByFileds() throws ODataJPARuntimeException, EdmException {
            if (getGroupsExpressions() != null && !isCountOnly) {
                return ODataUtilsParser.parseToJPAGroupByExpression(getGroupsExpressions(), getJpaEntityAlias());
            }
            return null;

        }

        /*
         * Generate Placeholder Clause Expression
         */
        protected String generatePlaceholderExpression() throws ODataException {
            // EdmEntityType entityType = entitySetView.getTargetEntitySet().getEntityType();
            // Class<?> jpaType = ((JPAEdmMappingImpl) entityType.getMapping()).getJPAType();
            FilterExpression filterExpression = entitySetView.getFilter();
            String placeholderExpression = whereExpression;
            if (filterExpression != null) {
                // if (null != parameterizedQueryMap && !parameterizedQueryMap.isEmpty()) {
                //     int index = 1;
                //     int previousIndex = 1;
                //     for (Entry<String, Map<Integer, Object>> parameter : parameterizedQueryMap.entrySet()) {
                //         index = getIndexValue(parameter.getValue());
                //         if (index > previousIndex) {
                //             previousIndex = index;
                //         }
                //     }
                //     placeholderExpression = JPASQLExpressionParser.parseToJPAExpression(filterExpression,
                //             getJpaEntityAlias(), previousIndex, new ConcurrentHashMap<Integer, Object>(),
                //             new ConcurrentHashMap<String, Integer>(), null, jpaType);
                // } else {
                //     placeholderExpression = JPASQLExpressionParser.parseToJPAExpression(filterExpression,
                //             getJpaEntityAlias(), jpaType);
                // }
                setPlaceholderQueryMap(JPASQLExpressionParser.getPlaceholderParametersThreadLocal());
                return placeholderExpression;
            }
            return placeholderExpression;
        }

        /*
         * Generate Where Clause Expression
         */
        protected String generateWhereExpression() throws ODataException {
            EdmEntityType entityType = entitySetView.getTargetEntitySet().getEntityType();
            Class<?> jpaType = ((JPAEdmMappingImpl) entityType.getMapping()).getJPAType();
            FilterExpression filterExpression = entitySetView.getFilter();
            String whereExpression = null;
            if (filterExpression != null) {
                if (null != parameterizedQueryMap && !parameterizedQueryMap.isEmpty()) {
                    int index = 1;
                    int previousIndex = 1;
                    for (Entry<String, Map<Integer, Object>> parameter : parameterizedQueryMap.entrySet()) {
                        index = getIndexValue(parameter.getValue());
                        if (index > previousIndex) {
                            previousIndex = index;
                        }
                    }
                    whereExpression = JPASQLExpressionParser.parseToJPAExpression(filterExpression,
                            getJpaEntityAlias(), previousIndex, new ConcurrentHashMap<Integer, Object>(),
                            new ConcurrentHashMap<String, List<Integer>>(), null, jpaType);
                } else {
                    whereExpression = JPASQLExpressionParser.parseToJPAExpression(filterExpression,
                            getJpaEntityAlias(), jpaType);
                }
                Map<String, Map<Integer, Object>> parameterizedExpressionMap = new HashMap<String, Map<Integer, Object>>();
                parameterizedExpressionMap.put(whereExpression,
                        JPASQLExpressionParser.getPositionalParametersThreadLocal());
                setParameterizedQueryMap(parameterizedExpressionMap);
            }
            return whereExpression;
        }

    }

    private int getIndexValue(Map<Integer, Object> map) {
        int index = 1;
        if (map != null) {
            for (Entry<Integer, Object> entry : map.entrySet()) {
                index = entry.getKey();
            }
            return index + 1;
        } else {
            return index;
        }
    }

}
