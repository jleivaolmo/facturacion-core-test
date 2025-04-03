package com.echevarne.sap.cloud.facturacion.odata.dto;

import java.util.Date;
import java.util.Map;
import java.util.Set;

import lombok.Data;

@Data
public class ODataFacturacionAgrIndividuales {
    
    private String cliente;
	private String organizacionVentas;
	private Map<Integer, Set<String>> agrupaciones;
	private Date fechaIniFacturacion;
	private Date fechaFinFacturacion;

}
