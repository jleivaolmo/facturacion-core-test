package com.echevarne.sap.cloud.facturacion.odata.processor.builders;

import com.echevarne.sap.cloud.facturacion.odata.jpa.expressions.GroupByExpression;
import com.echevarne.sap.cloud.facturacion.odata.processor.contexts.JPASQLContextPlaceholder;

import org.apache.olingo.odata2.jpa.processor.api.exception.ODataJPAModelException;
import org.apache.olingo.odata2.jpa.processor.api.exception.ODataJPARuntimeException;
import org.apache.olingo.odata2.jpa.processor.api.jpql.JPQLContextType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class JPASQLContext {

    protected String jpaEntityAlias;
    protected String jpaEntityName;
    protected String databaseEntityName;
    protected JPQLContextType type;
    protected boolean pagingRequested = false;

    protected static final ThreadLocal<JPASQLContext> sqlContext = new ThreadLocal<JPASQLContext>();

    public final static JPASQLContextBuilder createBuilder(final JPQLContextType contextType, final Object resultsView)
            throws ODataJPARuntimeException {
        return JPASQLContextBuilder.create(contextType, resultsView, false, null);
    }

    public final static JPASQLContextBuilder createBuilder(final JPQLContextType contextType, final Object resultsView,
            final boolean withPaging) throws ODataJPARuntimeException {
        return JPASQLContextBuilder.create(contextType, resultsView, withPaging, null);
    }

    public final static JPASQLContextBuilder createBuilder(final JPQLContextType contextType, final Object resultsView,
            final GroupByExpression groupsExpression) throws ODataJPARuntimeException {
        return JPASQLContextBuilder.create(contextType, resultsView, false, groupsExpression);
    }

    protected static void setSQLContext(JPASQLContext context) {
        sqlContext.set(context);
    }

    public final static JPASQLContext getSQLContext() {
        return sqlContext.get();
    }

    public final static void removeSQLContext() {
        sqlContext.remove();
    }

    public static abstract class JPASQLContextBuilder {
        private static final String ALIAS = "E";
        protected int aliasCounter = 0;
        protected boolean withPaging = false;

        protected JPASQLContextBuilder() {
        }

        private static JPASQLContextBuilder create(final JPQLContextType contextType, final Object resultsView,
                final boolean withPaging, GroupByExpression groupsExpressions) throws ODataJPARuntimeException {
            JPASQLContextBuilder contextBuilder = getContextBuilder(contextType);
            if (contextBuilder == null) {
                throw ODataJPARuntimeException.throwException(ODataJPARuntimeException.ERROR_JPQLCTXBLDR_CREATE, null);
            }
            contextBuilder.withPaging = withPaging;
            contextBuilder.setResultsView(resultsView);
            return contextBuilder;
        }

        public abstract JPASQLContext build() throws ODataJPAModelException, ODataJPARuntimeException;
        protected abstract void setResultsView(Object resultsView);

        protected void resetAliasCounter() {
            aliasCounter = 0;
        }

        protected String generateJPAEntityAlias() {
            return new String(ALIAS + ++aliasCounter);
        }

        public static JPASQLContextBuilder getContextBuilder(final JPQLContextType contextType) {
            JPASQLContextBuilder contextBuilder = null;
            switch (contextType) {
            case SELECT:
                JPASQLContextPlaceholder selectContext = new JPASQLContextPlaceholder(false);
                contextBuilder = selectContext.new JPASQLContextPlaceholderBuilder();
                break;
            case SELECT_SINGLE:
                // JPQLSelectSingleContext singleSelectContext = new JPQLSelectSingleContext();
                // contextBuilder = singleSelectContext.new JPQLSelectSingleContextBuilder();
                break;
            case JOIN:
                // JPQLJoinSelectContext joinContext = new JPQLJoinSelectContext(false);
                // contextBuilder = joinContext.new JPQLJoinContextBuilder();
                break;
            case JOIN_SINGLE:
                // JPQLJoinSelectSingleContext joinSingleContext = new
                // JPQLJoinSelectSingleContext();
                // contextBuilder = joinSingleContext.new JPQLJoinSelectSingleContextBuilder();
                break;
            case SELECT_COUNT:
                JPASQLContextPlaceholder selectCountContext = new JPASQLContextPlaceholder(true);
                contextBuilder = selectCountContext.new JPASQLContextPlaceholderBuilder();
                break;
            case JOIN_COUNT:
                // JPQLJoinSelectContext joinCountContext = new JPQLJoinSelectContext(true);
                // contextBuilder = joinCountContext.new JPQLJoinContextBuilder();
                break;
            default:
                break;
            }

            return contextBuilder;
        }
    }

}
