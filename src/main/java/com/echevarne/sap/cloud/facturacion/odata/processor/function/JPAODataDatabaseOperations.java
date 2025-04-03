package com.echevarne.sap.cloud.facturacion.odata.processor.function;

import javax.persistence.criteria.CriteriaBuilder;

public interface JPAODataDatabaseOperations {

    public void setCriterialBuilder(final CriteriaBuilder cb);

    // public <T extends Number> Expression<T> convert(final JPAArithmeticOperator jpaOperator)
    //         throws ODataApplicationException;

    // public Expression<Boolean> convert(final JPABooleanOperator jpaOperator) throws ODataApplicationException;

    // public <T extends Comparable<T>> Expression<Boolean> convert(final JPAComparisonOperator<T> jpaOperator)
    //         throws ODataApplicationException;

    // public <T> Expression<T> convert(final JPAMethodCall jpaFunction) throws ODataApplicationException;

    // public Expression<Boolean> convert(final JPAUnaryBooleanOperator jpaOperator) throws ODataApplicationException;

    // public Expression<Long> convert(final JPAAggregationOperation jpaOperator) throws ODataApplicationException;
    

}
