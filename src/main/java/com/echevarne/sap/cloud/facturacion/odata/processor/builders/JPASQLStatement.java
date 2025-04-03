package com.echevarne.sap.cloud.facturacion.odata.processor.builders;

import com.echevarne.sap.cloud.facturacion.odata.processor.statements.JPASQLStatementPlaceholder;

import org.apache.olingo.odata2.jpa.processor.api.exception.ODataJPARuntimeException;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JPASQLStatement {

    protected String statement;

    public static JPASQLStatementBuilder createBuilder(final JPASQLContext context) throws ODataJPARuntimeException {
        return JPASQLStatementBuilder.create(context);
    }

    private JPASQLStatement(final String statement) {
        this.statement = statement;
    }

    /**
     * The method provides a String representation of JPQLStatement.
     */
    @Override
    public String toString() {
        return statement;
    }

    public static abstract class JPASQLStatementBuilder {

        protected JPASQLStatementBuilder() {
        }

        private static final JPASQLStatementBuilder create(final JPASQLContext context)
                throws ODataJPARuntimeException {
            return getStatementBuilder(context);
        }

        protected final JPASQLStatement createStatement(final String statement) {
            return new JPASQLStatement(statement);
        }

        public abstract JPASQLStatement build() throws ODataJPARuntimeException;

        public abstract JPASQLStatement buildNative() throws ODataJPARuntimeException;

        public abstract JPASQLContext getContext();

        public static JPASQLStatementBuilder getStatementBuilder(final JPASQLContext context) {
            JPASQLStatementBuilder builder = null;
            switch (context.getType()) {
            case SELECT:
            case SELECT_COUNT: // for $count, Same as select
                builder = new JPASQLStatementPlaceholder(context);
                break;
            case SELECT_SINGLE:
                // builder = new CustomJPQLSelectSingleStatementBuilder(context);
                break;
            case JOIN:
            case JOIN_COUNT: // for $count, Same as join
                // builder = new CustomJPQLJoinStatementBuilder(context);
                break;
            case JOIN_SINGLE:
                // builder = new CustomJPQLJoinSelectSingleStatementBuilder(context);
                break;
            default:
                break;
            }
            return builder;
        }

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
        public static final String REF = "=>";
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
        public static final String PLACEHOLDER = "PLACEHOLDER";

    }

    public static final class FIELDS {
        public static final String ALL = "*";
        public static final String PARAMETER = "?";
        public static final String PARAMETER_ENCODE = "$$";
    }

    public static final class DELIMITER {
        public static final char SPACE = ' ';
        public static final char COMMA = ',';
        public static final char PERIOD = '.';
        public static final char PARENTHESIS_LEFT = '(';
        public static final char PARENTHESIS_RIGHT = ')';
        public static final char COLON = ':';
        public static final char QUOTE = '"';
        public static final char SINGLE_QUOTE = '\'';
        public static final char HYPHEN = '-';
        public static final char LEFT_BRACE = '{';
        public static final char RIGHT_BRACE = '}';
        public static final char LONG = 'L';
    }
}
