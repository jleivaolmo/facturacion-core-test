package com.echevarne.sap.cloud.facturacion.odata.jpa.jpql;

import com.echevarne.sap.cloud.facturacion.odata.jpa.expressions.GroupByExpression;
import com.echevarne.sap.cloud.facturacion.odata.jpa.jpql.CustomJPQLStatement.CustomJPQLStatementBuilder;
import com.echevarne.sap.cloud.facturacion.odata.jpa.jpql.CustomJPQLContext.JPQLContextBuilder;

import org.apache.olingo.odata2.jpa.processor.api.access.JPAMethodContext.JPAMethodContextBuilder;
import org.apache.olingo.odata2.jpa.processor.api.jpql.JPQLContextType;
import org.apache.olingo.odata2.jpa.processor.api.jpql.JPQLContextView;

public interface CustomJPQLBuilderFactory {

    /**
     * The method returns JPQL statement builder for building JPQL statements.
     * 
     * @param context
     * is {@link org.apache.olingo.odata2.jpa.processor.api.jpql.JPQLContext} that determines the type of JPQL statement
     * builder. The
     * parameter cannot be null.
     * @return an instance of JPQLStatementBuilder
     */
    public CustomJPQLStatementBuilder getStatementBuilder(JPQLContextView context);

    /**
     * The method returns JPQL statement builder for building JPQL statements.
     * 
     * @param context
     * is {@link org.apache.olingo.odata2.jpa.processor.api.jpql.JPQLContext} that determines the type of JPQL statement
     * builder. The
     * parameter cannot be null.
     * @return an instance of JPQLStatementBuilder
     */
    public CustomJPQLStatementBuilder getStatementBuilder(JPQLContextView context, GroupByExpression groupBy);
  
    /**
     * The method returns a JPQL context builder for building JPQL Context
     * object.
     * 
     * @param contextType
     * is {@link org.apache.olingo.odata2.jpa.processor.api.jpql.JPQLContextType} that determines the type of JPQL context
     * builder. The
     * parameter cannot be null.
     * @return an instance of JPQLContextBuilder
     */
    public JPQLContextBuilder getContextBuilder(JPQLContextType contextType);
  
    /**
     * The method returns a JPA method context builder for building JPA Method
     * context object.
     * 
     * @param contextType
     * is {@link org.apache.olingo.odata2.jpa.processor.api.jpql.JPQLContextType} that determines the type of JPQL context
     * builder. The
     * parameter cannot be null.
     * @return an instance of JPAMethodContextBuilder
     */
    public JPAMethodContextBuilder getJPAMethodContextBuilder(JPQLContextType contextType);
  }
  