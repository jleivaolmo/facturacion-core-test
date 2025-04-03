package com.echevarne.sap.cloud.facturacion.odata.jpa.jpql;

import com.echevarne.sap.cloud.facturacion.odata.jpa.expressions.GroupByExpression;
import com.echevarne.sap.cloud.facturacion.odata.jpa.factory.CustomODataJPAFactory;

import org.apache.olingo.odata2.jpa.processor.api.exception.ODataJPAModelException;
import org.apache.olingo.odata2.jpa.processor.api.exception.ODataJPARuntimeException;
import org.apache.olingo.odata2.jpa.processor.api.jpql.JPQLContextType;
import org.apache.olingo.odata2.jpa.processor.api.jpql.JPQLContextView;

public abstract class CustomJPQLContext implements JPQLContextView {

    /**
     * An alias for Java Persistence Entity
     */
    protected String jpaEntityAlias;
    /**
     * Java Persistence Entity name
     */
    protected String jpaEntityName;
    /**
     * The type of JPQL context. Based on the type JPQL statements can be built.
     */
    protected JPQLContextType type;
    protected boolean pagingRequested = false;
    protected static final ThreadLocal<CustomJPQLContext> jpqlContext = new ThreadLocal<CustomJPQLContext>();
  
    /**
     * sets JPA Entity Name into the context
     * 
     * @param jpaEntityName
     * is the name of JPA Entity
     */
    protected final void setJPAEntityName(final String jpaEntityName) {
      this.jpaEntityName = jpaEntityName;
    }
  
    /**
     * sets JPA Entity alias name into the context
     * 
     * @param jpaEntityAlias
     * is the JPA entity alias name
     */
    protected final void setJPAEntityAlias(final String jpaEntityAlias) {
      this.jpaEntityAlias = jpaEntityAlias;
    }
  
    /**
     * gets the JPA entity alias name set into the context
     */
    @Override
    public final String getJPAEntityAlias() {
      return jpaEntityAlias;
    }
  
    /**
     * sets the JPQL context type into the context
     * 
     * @param type
     * is JPQLContextType
     */
    protected final void setType(final JPQLContextType type) {
      this.type = type;
    }
    
    /**
     * gets the JPA entity name set into the context
     */
    @Override
    public final String getJPAEntityName() {
      return jpaEntityName;
    }
  
    /**
     * gets the JPQL context type set into the context
     */
    @Override
    public final JPQLContextType getType() {
      return type;
    }
  
    protected void isPagingRequested(final boolean pagingRequested) {
      this.pagingRequested = pagingRequested;
    }
    
    /**
     * the method returns an instance of type
     * {@link org.apache.olingo.odata2.jpa.processor.api.jpql.CustomJPQLContext.JPQLContextBuilder} based on the
     * JPQLContextType. The context builder can be used for
     * building different JPQL contexts.
     * 
     * @param contextType
     * is the JPQLContextType
     * @param resultsView
     * is the OData request view
     * @return an instance of type {@link org.apache.olingo.odata2.jpa.processor.api.jpql.CustomJPQLContext.JPQLContextBuilder}
     * @throws ODataJPARuntimeException
     */
    public final static JPQLContextBuilder createBuilder(final JPQLContextType contextType, final Object resultsView)
        throws ODataJPARuntimeException {
      return JPQLContextBuilder.create(contextType, resultsView, false, null);
    }
  
    /**
     * the method returns an instance of type
     * {@link org.apache.olingo.odata2.jpa.processor.api.jpql.CustomJPQLContext.JPQLContextBuilder} based on the
     * JPQLContextType. The context builder can be used for
     * building different JPQL contexts.
     * 
     * @param contextType
     * is the JPQLContextType
     * @param resultsView
     * is the OData request view
     * @param withPaging
     * indicates whether to build the context with paging
     * @return an instance of type {@link org.apache.olingo.odata2.jpa.processor.api.jpql.CustomJPQLContext.JPQLContextBuilder}
     * @throws ODataJPARuntimeException
     */
    public final static JPQLContextBuilder createBuilder(final JPQLContextType contextType, final Object resultsView,
        final boolean withPaging)
        throws ODataJPARuntimeException {
      return JPQLContextBuilder.create(contextType, resultsView, withPaging, null);
    }

    public final static JPQLContextBuilder createBuilder(final JPQLContextType contextType, final Object resultsView,
        final GroupByExpression groupsExpression)
        throws ODataJPARuntimeException {
      return JPQLContextBuilder.create(contextType, resultsView, false, groupsExpression);
    }
    
    protected static void setJPQLContext(CustomJPQLContext context) {
      jpqlContext.set(context);
    }
    
    public final static CustomJPQLContext getJPQLContext() {
      return jpqlContext.get();
    }
    
    public final static void removeJPQLContext() {
      jpqlContext.remove();
    }
    /**
     * The abstract class is extended by specific CustomJPQLContext builder for
     * building JPQLContexts.
     * 
     * 
     * 
     */
    public static abstract class JPQLContextBuilder {
      private static final String ALIAS = "E";
  
      /**
       * alias counter is an integer counter that is incremented by "1" for
       * every new alias name generation. The value of counter is used in the
       * generation of JPA entity alias names.
       */
      protected int aliasCounter = 0;
  
      protected boolean withPaging = false;
  
      protected JPQLContextBuilder() {}
  
      /**
       * the method instantiates an instance of type JPQLContextBuilder.
       * 
       * @param contextType
       * indicates the type of JPQLContextBuilder to instantiate.
       * @param resultsView
       * is the OData request view
       * @return an instance of type
       * {@link org.apache.olingo.odata2.jpa.processor.api.jpql.CustomJPQLContext.JPQLContextBuilder}
       * @throws ODataJPARuntimeException
       */
      private static JPQLContextBuilder create(final JPQLContextType contextType, final Object resultsView,
          final boolean withPaging, GroupByExpression groupsExpressions)
          throws ODataJPARuntimeException {
        JPQLContextBuilder contextBuilder =
            CustomODataJPAFactory.createFactory().getJPQLBuilderFactory().getContextBuilder(contextType);
        if (contextBuilder == null) {
          throw ODataJPARuntimeException.throwException(ODataJPARuntimeException.ERROR_JPQLCTXBLDR_CREATE, null);
        }
		    contextBuilder.setGroupBy(groupsExpressions);
        contextBuilder.setResultsView(resultsView);
        contextBuilder.withPaging = withPaging;
        return contextBuilder;
      }
  
      /**
       * The abstract method is implemented by specific JPQL context builders
       * to build JPQL Contexts. The build method makes use of information set
       * into the context to built JPQL Context Types.
       * 
       * @return an instance of {@link org.apache.olingo.odata2.jpa.processor.api.jpql.CustomJPQLContext}
       * @throws ODataJPAModelException
       * @throws ODataJPARuntimeException
       */
      public abstract CustomJPQLContext build() throws ODataJPAModelException, ODataJPARuntimeException;
  
      /**
       * The abstract method is implemented by specific JPQL context builder.
       * The method sets the OData request view into the JPQL context.
       * 
       * @param resultsView
       * is an instance representing OData request.
       */
      protected abstract void setResultsView(Object resultsView);

      /**
       * The abstract method is implemented by specific JPQL context builder.
       * The method sets the OData request view into the JPQL context.
       * 
       * @param resultsView
       * is an instance representing OData request.
       */
      protected abstract void setGroupBy(GroupByExpression groupByExpression);
  
      /**
       * The method resets the alias counter value to "0".
       */
      protected void resetAliasCounter() {
        aliasCounter = 0;
      }
  
      /**
       * The method returns a system generated alias name starting with prefix
       * "E" and ending with suffix "aliasCounter".
       * 
       * @return a String representing JPA entity alias name
       */
      protected String generateJPAEntityAlias() {
        return new String(ALIAS + ++aliasCounter);
      }
    }
  }