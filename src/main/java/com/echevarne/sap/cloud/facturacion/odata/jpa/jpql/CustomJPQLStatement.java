package com.echevarne.sap.cloud.facturacion.odata.jpa.jpql;

import com.echevarne.sap.cloud.facturacion.odata.jpa.expressions.GroupByExpression;
import com.echevarne.sap.cloud.facturacion.odata.jpa.factory.CustomODataJPAFactory;

import org.apache.olingo.odata2.jpa.processor.api.exception.ODataJPARuntimeException;
import org.apache.olingo.odata2.jpa.processor.api.jpql.JPQLContextView;

public class CustomJPQLStatement {

    protected String statement;
  
    /**
     * The method is used for creating an instance of JPQL Statement Builder for
     * building JPQL statements. The JPQL Statement builder is created based
     * upon the JPQL Context.
     * 
     * @param context
     * a non null value of {@link org.apache.olingo.odata2.jpa.processor.api.jpql.JPQLContextView} . The context is
     * expected to be set to be built with no
     * errors.
     * @return an instance of JPQL statement builder
     * @throws ODataJPARuntimeException
     */
    public static CustomJPQLStatementBuilder createBuilder(final JPQLContextView context) throws ODataJPARuntimeException {
      return CustomJPQLStatementBuilder.create(context);
    }

    /**
     * The method is used for creating an instance of JPQL Statement Builder for
     * building JPQL statements. The JPQL Statement builder is created based
     * upon the JPQL Context.
     * 
     * @param context
     * a non null value of {@link org.apache.olingo.odata2.jpa.processor.api.jpql.JPQLContextView} . The context is
     * expected to be set to be built with no
     * errors.
     * @return an instance of JPQL statement builder
     * @throws ODataJPARuntimeException
     */
    public static CustomJPQLStatementBuilder createBuilder(final JPQLContextView context, final GroupByExpression groupExpressions) throws ODataJPARuntimeException {
      return CustomJPQLStatementBuilder.create(context, groupExpressions);
    }
  
    private CustomJPQLStatement(final String statement) {
      this.statement = statement;
    }
  
    /**
     * The method provides a String representation of JPQLStatement.
     */
    @Override
    public String toString() {
      return statement;
    }
  
    /**
     * The abstract class is extended by specific JPQL statement builders for
     * building JPQL statements like
     * <ol>
     * <li>Select statements</li>
     * <li>Select single statements</li>
     * <li>Select statements with Join</li>
     * <li>Insert/Modify/Delete statements</li>
     * </ol>
     * 
     * A default statement builder for building each kind of JPQL statements is
     * provided by the library.
     * 
     * 
     * 
     */
    public static abstract class CustomJPQLStatementBuilder {
  
      protected CustomJPQLStatementBuilder() {}
  
      private static final CustomJPQLStatementBuilder create(final JPQLContextView context) throws ODataJPARuntimeException {
        return CustomODataJPAFactory.createFactory().getJPQLBuilderFactory().getStatementBuilder(context);
      }

      private static final CustomJPQLStatementBuilder create(final JPQLContextView context, final GroupByExpression groupBy) throws ODataJPARuntimeException {
        return CustomODataJPAFactory.createFactory().getJPQLBuilderFactory().getStatementBuilder(context, groupBy);
      }
  
      protected final CustomJPQLStatement createStatement(final String statement) {
        return new CustomJPQLStatement(statement);
      }

      public abstract CustomJPQLStatement build() throws ODataJPARuntimeException;
  
    }
  
    public static final class Operator {
      public static final String EQ = "=";
      public static final String NE = "<>";
      public static final String LT = "<";
      public static final String LE = "<=";
      public static final String GT = ">";
      public static final String GE = ">=";
      public static final String AND = "AND";
      public static final String NOT = "NOT";
      public static final String OR = "OR";
      public static final String LIKE = "LIKE";
  
    }
  
    public static final class KEYWORD {
      public static final String SELECT = "SELECT";
      public static final String SELECT_NEW = "NEW";
      public static final String SELECT_DISTINCT = "SELECT DISTINCT";
      public static final String FROM = "FROM";
      public static final String WHERE = "WHERE";
      public static final String LEFT_OUTER_JOIN = "LEFT OUTER JOIN";
      public static final String OUTER = "OUTER";
      public static final String JOIN = "JOIN";
      public static final String ORDERBY = "ORDER BY";
      public static final String GROUPBY = "GROUP BY";
      public static final String COUNT = "COUNT";
      public static final String OFFSET = ".000";
      public static final String TIMESTAMP = "ts";
  
    }
  
    public static final class DELIMITER {
      public static final char SPACE = ' ';
      public static final char COMMA = ',';
      public static final char PERIOD = '.';
      public static final char PARENTHESIS_LEFT = '(';
      public static final char PARENTHESIS_RIGHT = ')';
      public static final char COLON = ':';
      public static final char HYPHEN = '-';
      public static final char LEFT_BRACE = '{';
      public static final char RIGHT_BRACE = '}';
      public static final char LONG = 'L';
    }
  
  }
  