package com.echevarne.sap.cloud.facturacion.odata.jpa.jpql;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import com.echevarne.sap.cloud.facturacion.odata.jpa.expressions.GroupByExpression;
import com.echevarne.sap.cloud.facturacion.odata.jpa.utils.ODataUtilsParser;

import org.apache.olingo.odata2.api.edm.EdmEntityType;
import org.apache.olingo.odata2.api.edm.EdmException;
import org.apache.olingo.odata2.api.edm.EdmMapping;
import org.apache.olingo.odata2.api.exception.ODataException;
import org.apache.olingo.odata2.api.uri.info.GetEntitySetUriInfo;
import org.apache.olingo.odata2.jpa.processor.api.exception.ODataJPAModelException;
import org.apache.olingo.odata2.jpa.processor.api.exception.ODataJPARuntimeException;
import org.apache.olingo.odata2.jpa.processor.api.jpql.JPQLContextType;
import org.apache.olingo.odata2.jpa.processor.api.jpql.JPQLSelectContextView;
import org.apache.olingo.odata2.jpa.processor.core.ODataExpressionParser;
import org.apache.olingo.odata2.jpa.processor.core.model.JPAEdmMappingImpl;

public class CustomJPQLSelectContext extends CustomJPQLContext implements JPQLSelectContextView {

    private GroupByExpression groupsExpressions;
    private String groupByCollection;
    protected String selectExpression;
    protected String orderByCollection;
    protected String whereCondition;
    protected Map<String, Map<Integer, Object>> parameterizedQueryMap;
    protected String jpqlStatement;
  
    protected boolean isCountOnly = false;// Support for $count
  
    public CustomJPQLSelectContext(final boolean isCountOnly) {
      this.isCountOnly = isCountOnly;
    }
  
    protected final void setOrderByCollection(final String orderByCollection) {
      this.orderByCollection = orderByCollection;
    }
  
    protected final void setWhereExpression(final String filterExpression) {
      whereCondition = filterExpression;
    }
    
    protected final void setParameterizedQueryMap(
        final Map<String, Map<Integer, Object>> parameterizedQueryMap) {
      if (null == this.parameterizedQueryMap || this.parameterizedQueryMap.isEmpty()) {
        this.parameterizedQueryMap = parameterizedQueryMap;
      } else {
        this.parameterizedQueryMap.putAll(parameterizedQueryMap);
      }
    }
  
    protected final void setSelectExpression(final String selectExpression) {
      this.selectExpression = selectExpression;
    }
  
    @Override
    public String getSelectExpression() {
      return selectExpression;
    }
  
    @Override
    public String getOrderByCollection() {
      return orderByCollection;
    }
  
    @Override
    public String getWhereExpression() {
      return whereCondition;
    }

    public void setGroupByCollection(String groupByCollection) {
        this.groupByCollection = groupByCollection;
    }

    public String getGroupByCollection() {
        return groupByCollection;
    }

    public GroupByExpression getGroupsExpressions() {
        return groupsExpressions;
    }

    public void setGroupsExpressions(GroupByExpression groupsExpressions) {
        this.groupsExpressions = groupsExpressions;
    }

    public class JPQLSelectContextBuilder extends JPQLContextBuilder {

        protected GetEntitySetUriInfo entitySetView;

        @Override
        public CustomJPQLContext build() throws ODataJPAModelException, ODataJPARuntimeException {
            if (entitySetView != null) {

                try {

                    if (isCountOnly) {
                        setType(JPQLContextType.SELECT_COUNT);
                    } else {
                        setType(JPQLContextType.SELECT);
                    }

                    if (withPaging) {
                        isPagingRequested(withPaging);
                    }

                    EdmEntityType entityType = entitySetView.getTargetEntitySet().getEntityType();
                    EdmMapping mapping = entityType.getMapping();
                    if (mapping != null) {
                        setJPAEntityName(mapping.getInternalName());
                    } else {
                        setJPAEntityName(entityType.getName());
                    }

                    setJPAEntityAlias(generateJPAEntityAlias());

                    setOrderByCollection(generateOrderByFileds());

                    setGroupByCollection(generateGroupByFileds());

                    setSelectExpression(generateSelectExpression());
                    // setSelectExpression(generateGroupByFileds());

                    setWhereExpression(generateWhereExpression());

                    setJPQLContext(CustomJPQLSelectContext.this);

                } catch (ODataException e) {
                    throw ODataJPARuntimeException.throwException(ODataJPARuntimeException.INNER_EXCEPTION, e);
                }
            }

            return CustomJPQLSelectContext.this;

        }

        @Override
        protected void setResultsView(final Object resultsView) {
            if (resultsView instanceof GetEntitySetUriInfo) {
                entitySetView = (GetEntitySetUriInfo) resultsView;
            }

        }

        @Override
        protected void setGroupBy(GroupByExpression groupsExpressions) {
            setGroupsExpressions(groupsExpressions);
        }

        /*
         * Generate Select Clause
         */
        protected String generateSelectExpression() throws EdmException {
            if (getGroupsExpressions() != null && !isCountOnly) {
                return getSelectFieldsWithOrderBy();
            }else{
                return getJPAEntityAlias();
            }
        }

        private String getSelectFieldsWithOrderBy() {
            try {
                EdmEntityType entityType = entitySetView.getTargetEntitySet().getEntityType();
                Class<?> jpaType = ((JPAEdmMappingImpl) entityType.getMapping()).getJPAType();
                return CustomJPQLStatement.KEYWORD.SELECT_NEW 
                + CustomJPQLStatement.DELIMITER.SPACE
                + jpaType.getCanonicalName()
                + CustomJPQLStatement.DELIMITER.PARENTHESIS_LEFT 
                + getGroupByCollection()
                + CustomJPQLStatement.DELIMITER.PARENTHESIS_RIGHT;
            } catch (EdmException e) {
                return  getJPAEntityAlias();
            }

        }

        /*
         * Generate Order By Clause Fields
         */
        protected String generateOrderByFileds() throws ODataJPARuntimeException, EdmException {

            if (entitySetView.getOrderBy() != null && !isCountOnly) {

                return ODataExpressionParser.parseToJPAOrderByExpression(entitySetView.getOrderBy(),
                        getJPAEntityAlias());

            } else if ((entitySetView.getTop() != null || entitySetView.getSkip() != null || pagingRequested)
                    && !isCountOnly) {

                return ODataExpressionParser.parseKeyPropertiesToJPAOrderByExpression(
                        entitySetView.getTargetEntitySet().getEntityType().getKeyProperties(), getJPAEntityAlias());
            } else {
                return null;
            }

        }

        /*
         * Generate Group By Clause Fields
         */
        protected String generateGroupByFileds() throws ODataJPARuntimeException, EdmException {

            if (getGroupsExpressions() != null && !isCountOnly) {

                return ODataUtilsParser.parseToJPAGroupByExpression(getGroupsExpressions(), getJPAEntityAlias());

            }  else {
                return null;
            }

        }

        /*
         * Generate Where Clause Expression
         */
        protected String generateWhereExpression() throws ODataException {
            if (entitySetView.getFilter() != null) {
                String whereExpression = null;
                if (null != parameterizedQueryMap && !parameterizedQueryMap.isEmpty()) {
                    int index = 1;
                    int previousIndex = 1;
                    for (Entry<String, Map<Integer, Object>> parameter : parameterizedQueryMap.entrySet()) {
                        index = getIndexValue(parameter.getValue());
                        if (index > previousIndex) {
                            previousIndex = index;
                        }
                    }
                    whereExpression = ODataExpressionParser.parseToJPAWhereExpression(entitySetView.getFilter(),
                            getJPAEntityAlias(), previousIndex, new ConcurrentHashMap<Integer, Object>(), null);
                } else {
                    whereExpression = ODataExpressionParser.parseToJPAWhereExpression(entitySetView.getFilter(),
                            getJPAEntityAlias());
                }
                Map<String, Map<Integer, Object>> parameterizedExpressionMap = new HashMap<String, Map<Integer, Object>>();
                parameterizedExpressionMap.put(whereExpression,
                        ODataExpressionParser.getPositionalParametersThreadLocal());
                setParameterizedQueryMap(parameterizedExpressionMap);
                return whereExpression;
            }
            return null;
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

    @Override
    public Map<String, Map<Integer, Object>> getParameterizedQueryMap() {
        return parameterizedQueryMap;
    }

    @Override
    public void setJPQLStatement(String jpqlStatement) {
        this.jpqlStatement = jpqlStatement;
    }

    @Override
    public String getJPQLStatement() {
        return jpqlStatement;
    }
}