package com.echevarne.sap.cloud.facturacion.dto;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import lombok.Data;

@Data
public class ConfirmacionFactura implements Serializable, Cloneable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3017072680399704907L;

	private String numeroPeticion;
	private String numeroFactura;
	private String fechaFactura;
	private String horaFactura;
	private String indicador;
	private String numeroRectificativa;
	
	public ConfirmacionFactura(String numeroPeticion, String numeroFactura, Date facturaDate, 
            String indicador, String numeroRectificativa) {
		this.numeroPeticion = numeroPeticion;
		this.numeroFactura = numeroFactura;
		this.indicador = indicador;
		this.numeroRectificativa = numeroRectificativa;
        this.fechaFactura = new SimpleDateFormat("dd/MM/yyyy").format(facturaDate);
        this.horaFactura = new SimpleDateFormat("hh:mm").format(facturaDate);
	}

}
