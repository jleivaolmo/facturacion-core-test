package com.echevarne.sap.cloud.facturacion.odata.jpa.jpql;

import java.util.List;

import com.echevarne.sap.cloud.facturacion.odata.jpa.jpql.CustomJPQLStatement.CustomJPQLStatementBuilder;

import org.apache.olingo.odata2.jpa.processor.api.access.JPAJoinClause;
import org.apache.olingo.odata2.jpa.processor.api.exception.ODataJPARuntimeException;
import org.apache.olingo.odata2.jpa.processor.api.jpql.JPQLContextView;
import org.apache.olingo.odata2.jpa.processor.api.jpql.JPQLJoinSelectSingleContextView;

public class CustomJPQLJoinSelectSingleStatementBuilder extends CustomJPQLStatementBuilder {

    CustomJPQLStatement jpqlStatement;
    private JPQLJoinSelectSingleContextView context;
  
    public CustomJPQLJoinSelectSingleStatementBuilder(final JPQLContextView context) {
      this.context = (JPQLJoinSelectSingleContextView) context;
    }
  
    @Override
    public CustomJPQLStatement build() throws ODataJPARuntimeException {
      jpqlStatement = createStatement(createJPQLQuery());
      this.context.setJPQLStatement(jpqlStatement.toString());
      return jpqlStatement;
  
    }
  
    private String createJPQLQuery() throws ODataJPARuntimeException {
  
      StringBuilder jpqlQuery = new StringBuilder();
      StringBuilder joinWhereCondition = null;
  
      jpqlQuery.append(CustomJPQLStatement.KEYWORD.SELECT).append(CustomJPQLStatement.DELIMITER.SPACE);
      jpqlQuery.append(context.getSelectExpression()).append(CustomJPQLStatement.DELIMITER.SPACE);
      jpqlQuery.append(CustomJPQLStatement.KEYWORD.FROM).append(CustomJPQLStatement.DELIMITER.SPACE);
  
      if (context.getJPAJoinClauses() != null && !context.getJPAJoinClauses().isEmpty()) {
        List<JPAJoinClause> joinClauseList = context.getJPAJoinClauses();
        JPAJoinClause joinClause = joinClauseList.get(0);
        String joinCondition = joinClause.getJoinCondition();
        joinWhereCondition = new StringBuilder();
        if (joinCondition != null) {
          joinWhereCondition.append(joinCondition);
        }
        String relationShipAlias = null;
        joinClause = joinClauseList.get(1);
        jpqlQuery.append(joinClause.getEntityName()).append(CustomJPQLStatement.DELIMITER.SPACE);
        jpqlQuery.append(joinClause.getEntityAlias());
  
        int i = 1;
        int limit = joinClauseList.size();
        relationShipAlias = joinClause.getEntityAlias();
        while (i < limit) {
          jpqlQuery.append(CustomJPQLStatement.DELIMITER.SPACE);
          jpqlQuery.append(CustomJPQLStatement.KEYWORD.JOIN).append(CustomJPQLStatement.DELIMITER.SPACE);
  
          joinClause = joinClauseList.get(i);
          jpqlQuery.append(relationShipAlias).append(CustomJPQLStatement.DELIMITER.PERIOD);
          jpqlQuery.append(joinClause.getEntityRelationShip()).append(CustomJPQLStatement.DELIMITER.SPACE);
          jpqlQuery.append(joinClause.getEntityRelationShipAlias());
  
          relationShipAlias = joinClause.getEntityRelationShipAlias();
          i++;
  
          joinCondition = joinClause.getJoinCondition();
          if (joinCondition != null) {
            joinWhereCondition.append(CustomJPQLStatement.DELIMITER.SPACE + CustomJPQLStatement.Operator.AND
                + CustomJPQLStatement.DELIMITER.SPACE);
  
            joinWhereCondition.append(joinCondition);
          }
  
        }
      } else {
        throw ODataJPARuntimeException.throwException(ODataJPARuntimeException.JOIN_CLAUSE_EXPECTED, null);
      }
  
      if (joinWhereCondition.length() > 0) {
        jpqlQuery.append(CustomJPQLStatement.DELIMITER.SPACE);
        jpqlQuery.append(CustomJPQLStatement.KEYWORD.WHERE).append(CustomJPQLStatement.DELIMITER.SPACE);
        jpqlQuery.append(joinWhereCondition.toString());
      }
  
      return jpqlQuery.toString();
  
    }
  
  }
  