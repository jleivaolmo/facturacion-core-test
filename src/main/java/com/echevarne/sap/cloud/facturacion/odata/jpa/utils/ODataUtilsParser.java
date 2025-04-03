package com.echevarne.sap.cloud.facturacion.odata.jpa.utils;

import java.util.List;

import com.echevarne.sap.cloud.facturacion.odata.jpa.expressions.GroupByExpression;
import com.echevarne.sap.cloud.facturacion.odata.jpa.expressions.GroupExpression;

import org.apache.olingo.odata2.api.edm.EdmException;
import org.apache.olingo.odata2.api.edm.EdmMapping;
import org.apache.olingo.odata2.api.edm.EdmProperty;
import org.apache.olingo.odata2.jpa.processor.api.exception.ODataJPARuntimeException;
import org.apache.olingo.odata2.jpa.processor.api.jpql.JPQLStatement;

public class ODataUtilsParser {

    public static final String EMPTY = ""; //$NON-NLS-1$
    public static final Character[] EMPTY_CHARACTER_ARRAY = new Character[0];

    /**
     * This method parses the order by condition in the query.
     *
     * @param groupByExpression
     * @return a map of JPA attributes and their sort order
     * @throws ODataJPARuntimeException
     */
    public static String parseToJPAGroupByExpression(final GroupByExpression groupByExpression, final String tableAlias)
            throws ODataJPARuntimeException {
        String jpqlGroupByExpression = "";
        if (groupByExpression != null && groupByExpression.getGroups() != null) {
            List<GroupExpression> groupBys = groupByExpression.getGroups();
            for (GroupExpression groupBy : groupBys) {
                String fieldGroup = groupBy.getFieldGroup();
                if (!fieldGroup.isEmpty() && fieldGroup != null) {
                    jpqlGroupByExpression += tableAlias + "." + groupBy.getFieldGroup() + " , ";
                }
            }
        }
        return normalizeGroupByExpression(jpqlGroupByExpression);
    }

    public static String parseKeyPropertiesToJPAGroupByExpression(final List<EdmProperty> edmPropertylist,
            final String tableAlias) throws ODataJPARuntimeException {
        String propertyName = null;
        String orderExpression = "";
        if (edmPropertylist == null) {
            return orderExpression;
        }
        for (EdmProperty edmProperty : edmPropertylist) {
            try {
                EdmMapping mapping = edmProperty.getMapping();
                if (mapping != null && mapping.getInternalName() != null) {
                    propertyName = mapping.getInternalName();// For embedded/complex keys
                } else {
                    propertyName = edmProperty.getName();
                }
            } catch (EdmException e) {
                throw ODataJPARuntimeException
                        .throwException(ODataJPARuntimeException.GENERAL.addContent(e.getMessage()), e);
            }
            orderExpression += tableAlias + JPQLStatement.DELIMITER.PERIOD + propertyName + " , ";
        }
        return normalizeGroupByExpression(orderExpression);
    }

    private static String normalizeGroupByExpression(final String jpqlOrderByExpression) {
        if (jpqlOrderByExpression != "") {
            return jpqlOrderByExpression.substring(0, jpqlOrderByExpression.length() - 3);
        } else {
            return jpqlOrderByExpression;
        }
    }

}
