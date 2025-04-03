package com.echevarne.sap.cloud.facturacion.odata.processor.function;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.From;

import org.apache.olingo.odata2.api.edm.provider.EntityType;
import org.apache.olingo.odata2.api.exception.ODataApplicationException;
import org.apache.olingo.odata2.api.exception.ODataException;

public interface JPAODataDatabaseSearch {

    /**
     * @param cb           JPA Criteria Builder
     * @param cq           Criteria Query
     * @param root         From clause the search is related to
     * @param entityType   Metadata of the entity type the search belongs to
     * @param searchOption Parsed search operations
     * @return
     * @throws ODataApplicationException
     */
    Expression<Boolean> createSearchWhereClause(final CriteriaBuilder cb, final CriteriaQuery<?> cq,
            final From<?, ?> root, final EntityType entityType) //, final SearchOption searchOption)
            throws ODataException;

}
