package com.echevarne.sap.cloud.facturacion.odata.processor.function;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.apache.olingo.odata2.api.edm.EdmException;
import org.apache.olingo.odata2.api.edm.EdmProperty;
import org.apache.olingo.odata2.api.edm.EdmTypeKind;
import org.apache.olingo.odata2.jpa.processor.api.exception.ODataJPAModelException;

import lombok.Setter;

public class HANAPlaceholderFunction implements JPADataBaseFunction {

    private static final String FUNCTION_NAME = "PLACEHOLDER";

    @Setter
    List<Field> properties = Collections.emptyList();

    @Override
    public List<Field> getParameter() throws ODataJPAModelException {
        return properties;
    }

    @Override
    public Optional<Field> getParameter(String internalName) throws ODataJPAModelException {
        for (Field property : properties) {
            try {
                if (property.getName().equals(internalName))
                    return Optional.of(property);
            } catch (Exception e) {
                throw ODataJPAModelException.throwException(ODataJPAModelException.REF_ATTRIBUTE_NOT_FOUND, e);
            }
        }
        return Optional.empty();
    }

    @Override
    public EdmFunctionType getFunctionType() {
        return EdmFunctionType.UserDefinedFunction;
    }

    @Override
    public boolean isBound() throws ODataJPAModelException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public EdmTypeKind getReturnType() {
        return EdmTypeKind.ENTITY;
    }

    @Override
    public String getDBName() {
        return FUNCTION_NAME;
    }

}
