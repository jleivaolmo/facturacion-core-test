package com.echevarne.sap.cloud.facturacion.odata.jpa.jpql;

import java.util.HashMap;
import java.util.Map;

import com.echevarne.sap.cloud.facturacion.odata.jpa.jpql.CustomJPQLStatement.CustomJPQLStatementBuilder;

import org.apache.olingo.odata2.jpa.processor.api.exception.ODataJPARuntimeException;
import org.apache.olingo.odata2.jpa.processor.api.jpql.JPQLContextView;
import org.apache.olingo.odata2.jpa.processor.api.jpql.JPQLSelectSingleContextView;
import org.apache.olingo.odata2.jpa.processor.core.ODataExpressionParser;

public class CustomJPQLSelectSingleStatementBuilder extends CustomJPQLStatementBuilder {

    CustomJPQLStatement jpqlStatement;
    private JPQLSelectSingleContextView context;
  
    public CustomJPQLSelectSingleStatementBuilder(final JPQLContextView context) {
      this.context = (JPQLSelectSingleContextView) context;
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
      jpqlQuery.append(context.getSelectExpression()).append(CustomJPQLStatement.DELIMITER.SPACE);
      jpqlQuery.append(CustomJPQLStatement.KEYWORD.FROM).append(CustomJPQLStatement.DELIMITER.SPACE);
      jpqlQuery.append(fromClause);
  
      if (context.getKeyPredicates() != null && !context.getKeyPredicates().isEmpty()) {
        jpqlQuery.append(CustomJPQLStatement.DELIMITER.SPACE);
        jpqlQuery.append(CustomJPQLStatement.KEYWORD.WHERE).append(CustomJPQLStatement.DELIMITER.SPACE);
        String keyString = ODataExpressionParser
            .parseKeyPredicates(context.getKeyPredicates(), context.getJPAEntityAlias());
        Map<String, Map<Integer, Object>> parameterizedExpressionMap = 
            new HashMap<String, Map<Integer,Object>>();
        if (keyString != null) { 
          // parameterizedExpressionMap.put(keyString, 
          //     ODataExpressionParser.getPositionalParametersThreadLocal());
          // ((JPQLSelectSingleContext)this.context).setParameterizedQueryMap(parameterizedExpressionMap);
        }
        jpqlQuery.append(keyString);
      }
  
      return jpqlQuery.toString();
  
    }
  
  }
  