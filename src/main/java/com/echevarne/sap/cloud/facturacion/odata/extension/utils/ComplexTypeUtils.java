package com.echevarne.sap.cloud.facturacion.odata.extension.utils;

import java.util.ArrayList;
import java.util.List;

import com.echevarne.sap.cloud.facturacion.dto.response.ResponseError;

import org.apache.olingo.odata2.api.edm.EdmSimpleTypeKind;
import org.apache.olingo.odata2.api.edm.FullQualifiedName;
import org.apache.olingo.odata2.api.edm.provider.ComplexProperty;
import org.apache.olingo.odata2.api.edm.provider.ComplexType;
import org.apache.olingo.odata2.api.edm.provider.Property;
import org.apache.olingo.odata2.api.edm.provider.SimpleProperty;

public class ComplexTypeUtils extends GlobalUtils {

    /**
     * 
     * @param string
     * @return
     */
    public static ComplexType getFacturarClienteComplexType(String namespace) {

        ComplexType complexType = new ComplexType();
        List<Property> properties = new ArrayList<Property>();

        SimpleProperty property = new SimpleProperty();
        property.setName("codigoCliente");
        property.setType(EdmSimpleTypeKind.String);
        properties.add(property);

        property = new SimpleProperty();
        property.setName("fechaInicioFacturacion");
        property.setType(EdmSimpleTypeKind.DateTime);
        properties.add(property);

        property = new SimpleProperty();
        property.setName("fechaFinFacturacion");
        property.setType(EdmSimpleTypeKind.DateTime);
        properties.add(property);

        property = new SimpleProperty();
        property.setName("esSimulacion");
        property.setType(EdmSimpleTypeKind.Boolean);
        properties.add(property);

        complexType.setName("FacturarCliente");
        complexType.setProperties(properties);
        return complexType;
    }

    /**
     * 
     * @param string
     * @return
     */
    public static ComplexType getResponseComplexType(String namespace) {

        ComplexType complexType = new ComplexType();
        List<Property> properties = new ArrayList<Property>();

        SimpleProperty property = new SimpleProperty();
        property.setName("status");
        property.setType(EdmSimpleTypeKind.String);
        properties.add(property);

        property = new SimpleProperty();
        property.setName("message");
        property.setType(EdmSimpleTypeKind.String);
        properties.add(property);

        property = new SimpleProperty();
        property.setName("responseBody");
        property.setType(EdmSimpleTypeKind.String);
        properties.add(property);

        ComplexProperty complex = new ComplexProperty();
        complex.setName("errors");
        complex.setType(new FullQualifiedName(namespace, ResponseError.class.getSimpleName()));
        properties.add(complex);

        complexType.setName("Response");
        complexType.setProperties(properties);
        return complexType;
    }

    /**
     * 
     * @param string
     * @return
     */
    public static ComplexType getResponseErrorComplexType(String namespace) {

        ComplexType complexType = new ComplexType();
        List<Property> properties = new ArrayList<Property>();

        SimpleProperty property = new SimpleProperty();
        property.setName("timestamp");
        property.setType(EdmSimpleTypeKind.DateTime);
        properties.add(property);

        property = new SimpleProperty();
        property.setName("message");
        property.setType(EdmSimpleTypeKind.String);
        properties.add(property);

        property = new SimpleProperty();
        property.setName("code");
        property.setType(EdmSimpleTypeKind.String);
        properties.add(property);

        complexType.setName("ResponseError");
        complexType.setProperties(properties);
        return complexType;
    }

    /**
     * 
     * @param string
     * @return
     */
    public static ComplexType getFacturacionIndividualComplexType(String namespace) {

        ComplexType complexType = new ComplexType();
        List<Property> properties = new ArrayList<Property>();

        SimpleProperty property = new SimpleProperty();
        property.setName("cliente");
        property.setType(EdmSimpleTypeKind.String);
        properties.add(property);

        property = new SimpleProperty();
        property.setName("organizacionVentas");
        property.setType(EdmSimpleTypeKind.String);
        properties.add(property);

        property = new SimpleProperty();
        property.setName("fechaIniFacturacion");
        property.setType(EdmSimpleTypeKind.DateTime);
        properties.add(property);

        property = new SimpleProperty();
        property.setName("fechaFinFacturacion");
        property.setType(EdmSimpleTypeKind.DateTime);
        properties.add(property);

        ComplexProperty complex = new ComplexProperty();
        complex.setName("agrupaciones");
        complex.setType(new FullQualifiedName(namespace, "ListadoAgrupaciones"));
        properties.add(complex);

        complexType.setName("FacturacionIndividual");
        complexType.setProperties(properties);
        return complexType;
    }

    /**
     * 
     * @param string
     * @return
     */
    public static ComplexType getListadoAgrupaciones(String namespace) {

        ComplexType complexType = new ComplexType();
        List<Property> properties = new ArrayList<Property>();

        SimpleProperty property = new SimpleProperty();
        property.setName("idTipologia");
        property.setType(EdmSimpleTypeKind.Int32);
        properties.add(property);

        ComplexProperty complex = new ComplexProperty();
        complex.setName("identificadores");
        complex.setType(new FullQualifiedName(namespace, "Identificadores"));
        properties.add(complex);

        complexType.setName("ListadoAgrupaciones");
        complexType.setProperties(properties);
        return complexType;

    }

    /**
     * 
     * @param string
     * @return
     */
    public static ComplexType getIdentificadorAgrupaciones(String namespace) {

        ComplexType complexType = new ComplexType();
        List<Property> properties = new ArrayList<Property>();

        SimpleProperty property = new SimpleProperty();
        property.setName("idAgrupacion");
        property.setType(EdmSimpleTypeKind.Int64);
        properties.add(property);

        complexType.setName("Identificadores");
        complexType.setProperties(properties);
        return complexType;
        
    }

}
