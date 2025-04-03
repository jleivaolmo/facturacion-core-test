package com.echevarne.sap.cloud.facturacion.odata.jpa.jpql;

import com.echevarne.sap.cloud.facturacion.odata.jpa.expressions.GroupByExpression;
import com.echevarne.sap.cloud.facturacion.odata.jpa.jpql.CustomJPQLStatement.CustomJPQLStatementBuilder;

import org.apache.olingo.odata2.jpa.processor.api.exception.ODataJPARuntimeException;
import org.apache.olingo.odata2.jpa.processor.api.jpql.JPQLContextType;
import org.apache.olingo.odata2.jpa.processor.api.jpql.JPQLContextView;

public class CustomJPQLSelectStatementBuilder extends CustomJPQLStatementBuilder {

  CustomJPQLStatement jpqlStatement;
  private CustomJPQLSelectContext context;

  public CustomJPQLSelectStatementBuilder(final JPQLContextView context) {
    this.context = (CustomJPQLSelectContext) context;
  }

  public CustomJPQLSelectStatementBuilder(final JPQLContextView context, final GroupByExpression groupsExpressions) {
    this.context = (CustomJPQLSelectContext) context;
    this.context.setGroupsExpressions(groupsExpressions);
  }

  @Override
  public CustomJPQLStatement build() throws ODataJPARuntimeException {
    jpqlStatement = createStatement(createJPQLQuery());
    this.context.setJPQLStatement(jpqlStatement.toString());
    return jpqlStatement;

  }

  private String createJPQLQuery() throws ODataJPARuntimeException {

    StringBuilder jpqlQuery = new StringBuilder();
    String tableAlias = context.getJPAEntityAlias();
    String fromClause = context.getJPAEntityName() + CustomJPQLStatement.DELIMITER.SPACE + tableAlias;

    jpqlQuery.append(CustomJPQLStatement.KEYWORD.SELECT).append(CustomJPQLStatement.DELIMITER.SPACE);
    if (context.getType().equals(JPQLContextType.SELECT_COUNT)) { // $COUNT
      jpqlQuery.append(CustomJPQLStatement.KEYWORD.COUNT).append(CustomJPQLStatement.DELIMITER.SPACE);
      jpqlQuery.append(CustomJPQLStatement.DELIMITER.PARENTHESIS_LEFT).append(CustomJPQLStatement.DELIMITER.SPACE);
      jpqlQuery.append(context.getSelectExpression()).append(CustomJPQLStatement.DELIMITER.SPACE);
      jpqlQuery.append(CustomJPQLStatement.DELIMITER.PARENTHESIS_RIGHT).append(CustomJPQLStatement.DELIMITER.SPACE);
    } else {// Normal
      jpqlQuery.append(context.getSelectExpression()).append(CustomJPQLStatement.DELIMITER.SPACE);
    }

    jpqlQuery.append(CustomJPQLStatement.KEYWORD.FROM).append(CustomJPQLStatement.DELIMITER.SPACE);
    jpqlQuery.append(fromClause);

    if (context.getWhereExpression() != null) {
      jpqlQuery.append(CustomJPQLStatement.DELIMITER.SPACE);
      jpqlQuery.append(CustomJPQLStatement.KEYWORD.WHERE).append(CustomJPQLStatement.DELIMITER.SPACE);
      jpqlQuery.append(context.getWhereExpression());
    }

    if (context.getGroupByCollection() != null && context.getGroupByCollection().length() > 0) {
      StringBuilder groupByBuilder = new StringBuilder();
      groupByBuilder.append(context.getGroupByCollection());
      jpqlQuery.append(CustomJPQLStatement.DELIMITER.SPACE);
      jpqlQuery.append(CustomJPQLStatement.KEYWORD.GROUPBY).append(CustomJPQLStatement.DELIMITER.SPACE);
      jpqlQuery.append(groupByBuilder);
    }

    if (context.getOrderByCollection() != null && context.getOrderByCollection().length() > 0) {
      StringBuilder orderByBuilder = new StringBuilder();
      orderByBuilder.append(context.getOrderByCollection());
      jpqlQuery.append(CustomJPQLStatement.DELIMITER.SPACE);
      jpqlQuery.append(CustomJPQLStatement.KEYWORD.ORDERBY).append(CustomJPQLStatement.DELIMITER.SPACE);
      jpqlQuery.append(orderByBuilder);
    }

    return jpqlQuery.toString();

  }

}
