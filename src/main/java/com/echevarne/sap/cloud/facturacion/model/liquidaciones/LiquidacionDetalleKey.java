package com.echevarne.sap.cloud.facturacion.model.liquidaciones;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Id;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class LiquidacionDetalleKey implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 249524121709874465L;

	@Id
	@Basic
	private String proveedor;
	
	@Id
	@Basic
    private Long idTrazabilidad;
	
	

}
