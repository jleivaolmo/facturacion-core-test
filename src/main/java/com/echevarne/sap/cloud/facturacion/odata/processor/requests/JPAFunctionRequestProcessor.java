package com.echevarne.sap.cloud.facturacion.odata.processor.requests;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import com.echevarne.sap.cloud.facturacion.odata.jpa.annotation.AnalyticalParameter;
import com.echevarne.sap.cloud.facturacion.odata.processor.builders.JPASQLContext;
import com.echevarne.sap.cloud.facturacion.odata.processor.builders.JPASQLStatement;
import com.echevarne.sap.cloud.facturacion.odata.processor.builders.JPASQLStatement.JPASQLStatementBuilder;
import com.echevarne.sap.cloud.facturacion.odata.processor.contexts.JPASQLContextPlaceholder;
import com.echevarne.sap.cloud.facturacion.odata.processor.function.HANAPlaceholderFunction;
import com.echevarne.sap.cloud.facturacion.odata.processor.function.JPADataBaseFunction;
import com.echevarne.sap.cloud.facturacion.odata.processor.function.JPADefaultDatabaseProcessor;
import com.echevarne.sap.cloud.facturacion.odata.processor.function.JPAODataDatabaseProcessor;

import org.apache.olingo.odata2.api.edm.EdmEntityType;
import org.apache.olingo.odata2.api.edm.EdmException;
import org.apache.olingo.odata2.api.edm.EdmProperty;
import org.apache.olingo.odata2.api.exception.ODataApplicationException;
import org.apache.olingo.odata2.api.exception.ODataException;
import org.apache.olingo.odata2.api.uri.info.GetEntitySetUriInfo;
import org.apache.olingo.odata2.jpa.processor.api.jpql.JPQLContextType;
import org.apache.olingo.odata2.jpa.processor.core.model.JPAEdmMappingImpl;

public class JPAFunctionRequestProcessor implements JPARequestProcessor {

    @Override
    public List<Object> retrieveData(GetEntitySetUriInfo uriInfo, EntityManager em)
            throws ODataApplicationException, ODataException {
        JPQLContextType contextType;
        if (!uriInfo.getStartEntitySet().getName().equals(uriInfo.getTargetEntitySet().getName())) {
            contextType = JPQLContextType.JOIN;
        } else {
            contextType = JPQLContextType.SELECT;
        }
        JPAODataDatabaseProcessor processor = new JPADefaultDatabaseProcessor();
        JPASQLContext context = JPASQLContext.createBuilder(contextType, uriInfo).build();
        JPASQLStatementBuilder builder = JPASQLStatement.createBuilder(context);
        JPASQLStatement statement = builder.buildNative();
        JPASQLContextPlaceholder contextPlaceholder = (JPASQLContextPlaceholder) builder.getContext();
        EdmEntityType entityType = uriInfo.getTargetEntitySet().getEntityType();
        JPAEdmMappingImpl mapping = ((JPAEdmMappingImpl) entityType.getMapping());
        JPADataBaseFunction jpaFunction = buildFunction(entityType, mapping, em);
        return processor.executeFunctionQuery(uriInfo, statement, contextPlaceholder, jpaFunction, mapping, em);
    }

    private JPADataBaseFunction buildFunction(EdmEntityType entityType, JPAEdmMappingImpl mapping, EntityManager em) throws EdmException {
        HANAPlaceholderFunction function = new HANAPlaceholderFunction();
        List<Field> properties = new ArrayList<Field>();
        for (Field property : mapping.getJPAType().getDeclaredFields()) {
            if (isAnalyticalParameter(property)){
                properties.add(property);
            }
        }
        function.setProperties(properties);
        return function;
    }

    private EdmProperty getEdmProperty(EdmEntityType entityType, String name) throws EdmException {
        return (EdmProperty) entityType.getProperty(name);
    }

    private boolean isAnalyticalParameter(Field property) {
        return property.getDeclaredAnnotation(AnalyticalParameter.class) != null;
    }

}
