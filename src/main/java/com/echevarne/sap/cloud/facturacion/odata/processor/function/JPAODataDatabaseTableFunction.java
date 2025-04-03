package com.echevarne.sap.cloud.facturacion.odata.processor.function;

import java.util.List;

import javax.persistence.EntityManager;

import com.echevarne.sap.cloud.facturacion.odata.processor.builders.JPASQLStatement;
import com.echevarne.sap.cloud.facturacion.odata.processor.contexts.JPASQLContextPlaceholder;

import org.apache.olingo.odata2.api.exception.ODataException;
import org.apache.olingo.odata2.api.uri.info.GetEntitySetUriInfo;
import org.apache.olingo.odata2.jpa.processor.core.model.JPAEdmMappingImpl;

public interface JPAODataDatabaseTableFunction {

    <T> List<T> executeFunctionQuery(GetEntitySetUriInfo uriInfo, final JPASQLStatement statement,
            final JPASQLContextPlaceholder contextPlaceholder, final JPADataBaseFunction jpaFunction,
            JPAEdmMappingImpl entityTarget, final EntityManager em) throws ODataException;

}
