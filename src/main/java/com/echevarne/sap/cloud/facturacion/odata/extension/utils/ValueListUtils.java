package com.echevarne.sap.cloud.facturacion.odata.extension.utils;

import com.echevarne.sap.cloud.facturacion.odata.annotations.sap.common.ValueList;
import com.echevarne.sap.cloud.facturacion.odata.extension.ValueListsExtension;

import org.apache.olingo.odata2.api.edm.provider.AnnotationElement;

public class ValueListUtils extends GlobalUtils {

    /**
     * 
     * @param entityName
     * @param entityField
     * @param valueList
     * @return
     */
    public static AnnotationElement addValueList(String namespace, String entityName, String entityField,
            ValueList valueList) {

        return ValueListsExtension.addValueList(namespace, entityName, entityField, valueList.Label(),
                valueList.CollectionPath().entitySet, valueList.CollectionRoot(),
                Boolean.toString(valueList.SearchSupported()), valueList.Parameters());

    }

}
