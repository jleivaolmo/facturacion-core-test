package com.echevarne.sap.cloud.facturacion.odata.dto;

import java.util.List;

import lombok.Data;

@Data
public class ODataRequestInfoDTO {
    private List<String> selectFields;
    private String filterExpression;
    private List<String> expandEntities;
    private int skip;
    private int top;
    private String inlineCount;

    // Constructors, Getters, and Setters
}
