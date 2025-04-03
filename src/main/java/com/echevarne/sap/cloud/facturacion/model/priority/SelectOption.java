package com.echevarne.sap.cloud.facturacion.model.priority;

import java.util.HashMap;
import java.util.Map;

import lombok.Data;

@Data
public class SelectOption {

    private Map<String, SelectOptionItem> map = new HashMap<>();

    public void put(String fieldName, SelectOptionItem soi) {
        map.put(fieldName, soi);
    }

    public boolean containsFieldName(String fieldName) {
        return map.containsKey(fieldName);
    }

    public SelectOptionItem get(String fieldName) {
        return map.get(fieldName);
    }
}
