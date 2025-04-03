package com.echevarne.sap.cloud.facturacion.odata.jpa.factory;

import com.echevarne.sap.cloud.facturacion.odata.jpa.jpql.CustomJPQLBuilderFactory;

import org.apache.olingo.odata2.jpa.processor.api.factory.JPAAccessFactory;
import org.apache.olingo.odata2.jpa.processor.api.factory.ODataJPAAccessFactory;

public class CustomODataJPAFactory {

    private static final String IMPLEMENTATION =
        "com.echevarne.sap.cloud.facturacion.odata.jpa.factory.CustomODataJPAFactoryImpl";
    private static CustomODataJPAFactory factoryImpl;
  
    /**
     * Method creates a factory instance. The instance returned is singleton.
     * The instance of this factory can be used for creating other factory
     * implementations.
     * 
     * @return instance of type {@link org.apache.olingo.odata2.jpa.processor.api.factory.ODataJPAFactory} .
     */
    public static CustomODataJPAFactory createFactory() {
  
      if (factoryImpl != null) {
        return factoryImpl;
      } else {
        try {
          Class<?> clazz = Class.forName(CustomODataJPAFactory.IMPLEMENTATION);
  
          Object object = clazz.newInstance();
          factoryImpl = (CustomODataJPAFactory) object;
  
        } catch (Exception e) {
          throw new RuntimeException(e);
        }
  
        return factoryImpl;
      }
    }
  
    /**
     * The method returns a null reference to JPQL Builder Factory. Override
     * this method to return an implementation of JPQLBuilderFactory if default
     * implementation from library is not required.
     * 
     * @return instance of type {@link org.apache.olingo.odata2.jpa.processor.api.factory.JPQLBuilderFactory}
     */
    public CustomJPQLBuilderFactory getJPQLBuilderFactory() {
      return null;
    };
  
    /**
     * The method returns a null reference to JPA Access Factory. Override this
     * method to return an implementation of JPAAccessFactory if default
     * implementation from library is not required.
     * 
     * @return instance of type {@link org.apache.olingo.odata2.jpa.processor.api.factory.JPQLBuilderFactory}
     */
    public JPAAccessFactory getJPAAccessFactory() {
      return null;
    };
  
    /**
     * The method returns a null reference to OData JPA Access Factory. Override
     * this method to return an implementation of ODataJPAAccessFactory if
     * default implementation from library is not required.
     * 
     * @return instance of type {@link org.apache.olingo.odata2.jpa.processor.api.factory.ODataJPAAccessFactory}
     */
    public ODataJPAAccessFactory getODataJPAAccessFactory() {
      return null;
    };
  
  }
  