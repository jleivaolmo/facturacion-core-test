package com.echevarne.sap.cloud.facturacion.jms.model;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
public class UpdatePeticionesByParamsMessage {
	
	private String codigoCliente;
	private String organizacionVentas;
	private String delegacion;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")	
	private Date fechaPeticionDesde;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")	
	private Date fechaPeticionHasta;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")	
	private Date fechaCreacionDesde;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")	
	private Date fechaCreacionHasta;
	private List<String> codigosEstado;
	private List<String> listaCodigos;
	private String processId;
	
	public boolean insufficientData() {
		return StringUtils.isBlank(codigoCliente) &&
			   StringUtils.isBlank(organizacionVentas) && 
			   StringUtils.isBlank(delegacion) && 			
			   (fechaPeticionDesde==null) &&
			   (fechaPeticionHasta==null) && 	
			   (fechaCreacionDesde==null) && 	
			   (fechaCreacionHasta==null) &&
			   (codigosEstado.size()==0) &&
			   (listaCodigos.size()==0);			   
	}

	/*
	public static void main(String[] args) throws Exception {
	    ObjectMapper anObjectMapper = new ObjectMapper();
		anObjectMapper.setTimeZone(TimeZone.getDefault());
		anObjectMapper.setSerializationInclusion(Include.ALWAYS);
	    	
		UpdatePeticionesByParamsMessage upbpm = new UpdatePeticionesByParamsMessage();
		upbpm.setCodigoCliente("1codigoCliente");
		upbpm.setDelegacion("1delegacion");	
		upbpm.setFechaCreacionDesde(java.sql.Date.valueOf("2022-11-07"));
		upbpm.setFechaCreacionHasta(java.sql.Date.valueOf("2023-03-01"));
		upbpm.setFechaPeticionDesde(java.sql.Date.valueOf("2022-12-01"));
		upbpm.setFechaPeticionHasta(java.sql.Date.valueOf("2023-02-01"));
		upbpm.setCodigosEstado(Arrays.asList(new String[]{"1codigoCliente","2codigoCliente"}));
		upbpm.setListaCodigos(Arrays.asList(new String[]{"1listaCodigo","2listaCodigo"}));
		upbpm.setOrganizacionVentas("1organizacionVentas");
		System.out.println(anObjectMapper.writeValueAsString(upbpm));
	}
	*/
}
