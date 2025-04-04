package com.echevarne.sap.cloud.facturacion.model.salidas;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Id;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class SalidaLiquidacionKey implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2038530524597724813L;

	@Basic
	@Id
	private String periodo;

	@Basic
	@Id
	private String proveedor;

	@Basic
	@Id
	private String factura;

	@Basic
	@Id
	private String peticion;
}
