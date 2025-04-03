package com.echevarne.sap.cloud.facturacion.odata.processor.statements;

import com.echevarne.sap.cloud.facturacion.odata.processor.builders.JPASQLContext;
import com.echevarne.sap.cloud.facturacion.odata.processor.builders.JPASQLStatement;
import com.echevarne.sap.cloud.facturacion.odata.processor.builders.JPASQLStatement.JPASQLStatementBuilder;
import com.echevarne.sap.cloud.facturacion.odata.processor.contexts.JPASQLContextPlaceholder;
import com.echevarne.sap.cloud.facturacion.util.StringUtils;

import org.apache.olingo.odata2.jpa.processor.api.exception.ODataJPARuntimeException;
import org.apache.olingo.odata2.jpa.processor.api.jpql.JPQLContextType;

public class JPASQLStatementPlaceholder extends JPASQLStatementBuilder {

    private static final String FUNC_NAME_PLACEHOLDER = "$PLACEHOLDER$";

    JPASQLStatement jpqlStatement;
    private JPASQLContextPlaceholder context;

    public JPASQLStatementPlaceholder(final JPASQLContext context) {
        this.context = (JPASQLContextPlaceholder) context;
    }

    @Override
    public JPASQLStatement build() throws ODataJPARuntimeException {
        jpqlStatement = createStatement(createJPQLQuery());
        return jpqlStatement;
    }

    @Override
    public JPASQLStatement buildNative() throws ODataJPARuntimeException {
        jpqlStatement = createStatement(createNativeQuery());
        return jpqlStatement;
    }

    @Override
    public JPASQLContextPlaceholder getContext() {
        return this.context;
    }

    private String createJPQLQuery() throws ODataJPARuntimeException {

        StringBuilder jpqlQuery = new StringBuilder();
        String tableAlias = context.getJpaEntityAlias();
        String fromClause = context.getJpaEntityName() + JPASQLStatement.DELIMITER.SPACE + tableAlias;

        jpqlQuery.append(JPASQLStatement.KEYWORD.SELECT).append(JPASQLStatement.DELIMITER.SPACE);
        if (context.getType().equals(JPQLContextType.SELECT_COUNT)) { // $COUNT
            jpqlQuery.append(JPASQLStatement.KEYWORD.COUNT).append(JPASQLStatement.DELIMITER.SPACE);
            jpqlQuery.append(JPASQLStatement.DELIMITER.PARENTHESIS_LEFT).append(JPASQLStatement.DELIMITER.SPACE);
            jpqlQuery.append(context.getSelectExpression()).append(JPASQLStatement.DELIMITER.SPACE);
            jpqlQuery.append(JPASQLStatement.DELIMITER.PARENTHESIS_RIGHT).append(JPASQLStatement.DELIMITER.SPACE);
        } else {// Normal
            jpqlQuery.append(context.getSelectExpression()).append(JPASQLStatement.DELIMITER.SPACE);
        }

        jpqlQuery.append(JPASQLStatement.KEYWORD.FROM).append(JPASQLStatement.DELIMITER.SPACE);
        jpqlQuery.append(fromClause);

        if (context.getWhereExpression() != null) {
            jpqlQuery.append(JPASQLStatement.DELIMITER.SPACE);
            jpqlQuery.append(JPASQLStatement.KEYWORD.WHERE).append(JPASQLStatement.DELIMITER.SPACE);
            jpqlQuery.append(context.getWhereExpression());
        }

        if (context.getGroupByCollection() != null && context.getGroupByCollection().length() > 0) {
            StringBuilder groupByBuilder = new StringBuilder();
            groupByBuilder.append(context.getGroupByCollection());
            jpqlQuery.append(JPASQLStatement.DELIMITER.SPACE);
            jpqlQuery.append(JPASQLStatement.KEYWORD.GROUPBY).append(JPASQLStatement.DELIMITER.SPACE);
            jpqlQuery.append(groupByBuilder);
        }

        if (context.getOrderByCollection() != null && context.getOrderByCollection().length() > 0) {
            StringBuilder orderByBuilder = new StringBuilder();
            orderByBuilder.append(context.getOrderByCollection());
            jpqlQuery.append(JPASQLStatement.DELIMITER.SPACE);
            jpqlQuery.append(JPASQLStatement.KEYWORD.ORDERBY).append(JPASQLStatement.DELIMITER.SPACE);
            jpqlQuery.append(orderByBuilder);
        }

        return jpqlQuery.toString();

    }

    private String createNativeQuery() throws ODataJPARuntimeException {

        StringBuilder jpqlQuery = new StringBuilder();
        String tableAlias = context.getJpaEntityAlias();
        String fromClause = context.getDatabaseEntityName();

        jpqlQuery.append(JPASQLStatement.KEYWORD.SELECT).append(JPASQLStatement.DELIMITER.SPACE);
        if (context.getType().equals(JPQLContextType.SELECT_COUNT)) { // $COUNT
            jpqlQuery.append(JPASQLStatement.KEYWORD.COUNT).append(JPASQLStatement.DELIMITER.SPACE);
            jpqlQuery.append(JPASQLStatement.DELIMITER.PARENTHESIS_LEFT)
                    .append(JPASQLStatement.DELIMITER.SPACE);
            if(context.getSelectExpression().equals(tableAlias)){
                jpqlQuery.append(JPASQLStatement.FIELDS.ALL).append(JPASQLStatement.DELIMITER.SPACE);
            }else{
                jpqlQuery.append(context.getSelectExpression()).append(JPASQLStatement.DELIMITER.SPACE);
            }
            jpqlQuery.append(JPASQLStatement.DELIMITER.PARENTHESIS_RIGHT)
                    .append(JPASQLStatement.DELIMITER.SPACE);
        } else {// Normal
            if(context.getSelectExpression().equals(tableAlias)){
                jpqlQuery.append(JPASQLStatement.FIELDS.ALL).append(JPASQLStatement.DELIMITER.SPACE);
            }else{
                jpqlQuery.append(context.getSelectExpression()).append(JPASQLStatement.DELIMITER.SPACE);
            }
        }

        jpqlQuery.append(JPASQLStatement.KEYWORD.FROM).append(JPASQLStatement.DELIMITER.SPACE);
        jpqlQuery.append(fromClause);

        // Place holder template
        jpqlQuery.append(FUNC_NAME_PLACEHOLDER);

        // Table alias
        jpqlQuery.append(JPASQLStatement.DELIMITER.SPACE + tableAlias);

        if (!StringUtils.isNullOrEmpty(context.getWhereExpression())) {
            jpqlQuery.append(JPASQLStatement.DELIMITER.SPACE);
            jpqlQuery.append(JPASQLStatement.KEYWORD.WHERE).append(JPASQLStatement.DELIMITER.SPACE);
            jpqlQuery.append(context.getWhereExpression());
        }

        if (context.getGroupByCollection() != null && context.getGroupByCollection().length() > 0) {
            StringBuilder groupByBuilder = new StringBuilder();
            groupByBuilder.append(context.getGroupByCollection());
            jpqlQuery.append(JPASQLStatement.DELIMITER.SPACE);
            jpqlQuery.append(JPASQLStatement.KEYWORD.GROUPBY).append(JPASQLStatement.DELIMITER.SPACE);
            jpqlQuery.append(groupByBuilder);
        }

        if (context.getOrderByCollection() != null && context.getOrderByCollection().length() > 0) {
            StringBuilder orderByBuilder = new StringBuilder();
            orderByBuilder.append(context.getOrderByCollection());
            jpqlQuery.append(JPASQLStatement.DELIMITER.SPACE);
            jpqlQuery.append(JPASQLStatement.KEYWORD.ORDERBY).append(JPASQLStatement.DELIMITER.SPACE);
            jpqlQuery.append(orderByBuilder);
        }

        return jpqlQuery.toString();

    }

}
