package com.echevarne.sap.cloud.facturacion.model.views;

import java.io.Serializable;
import java.sql.Timestamp;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@EqualsAndHashCode
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PruebasIncongruentesKey implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -854335824980264930L;

	@Id
	private String materialProvocante;

	@Id
	private String materialRechazable;

	@Id
	private String codigoCliente;

	@Id
	private String codigoCompania;
	
	@Id
    private Timestamp fechaInicio;
	
}
