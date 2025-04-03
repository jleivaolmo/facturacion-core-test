package com.echevarne.sap.cloud.facturacion.odata.processor.function;

public interface JPADataBaseFunction extends JPAFunction {
    
    /**
     * 
     * @return Name of the function on the database
     */
    public String getDBName();

  }