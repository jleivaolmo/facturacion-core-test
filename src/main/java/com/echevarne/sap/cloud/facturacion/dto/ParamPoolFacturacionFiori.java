package com.echevarne.sap.cloud.facturacion.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import lombok.Data;

@Data
public class ParamPoolFacturacionFiori implements Serializable {
	
	
	/**
     *
     */
    private static final long serialVersionUID = -7284282027405824804L;
    
    private Set<String> codigoCliente;
	private Set<String> codigoOrganizacion;
	private Set<String> codigoDelegacion;
	private Set<String> codigoRemitente;
	private Set<String> codigoCompania;
	private Set<Integer> tipoPeticion;
	private Set<String> codigoPrueba;
	private Set<String> codigoSector;
	private Set<String> codigoPeticion;
	private Date fechaIniFacturacion;
	private Date fechaFinFacturacion;

}