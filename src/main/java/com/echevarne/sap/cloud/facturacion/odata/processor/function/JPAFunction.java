package com.echevarne.sap.cloud.facturacion.odata.processor.function;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;

import com.echevarne.sap.cloud.facturacion.odata.processor.operations.JPAOperation;

import org.apache.olingo.odata2.jpa.processor.api.exception.ODataJPAModelException;

public interface JPAFunction extends JPAOperation {

    /**
     * 
     * @return List of import parameter
     * @throws ODataJPAModelException
     */
    public List<Field> getParameter() throws ODataJPAModelException;
  
    /**
     * 
     * @param internalName
     * @return
     * @throws ODataJPAModelException
     */
    public Optional<Field> getParameter(String internalName) throws ODataJPAModelException;
  
    /**
     * 
     * @return The type of function
     */
    public EdmFunctionType getFunctionType();
  
    public boolean isBound() throws ODataJPAModelException;

  }
