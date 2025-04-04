package com.echevarne.sap.cloud.facturacion.model.complexTypes;

import java.util.Date;

public class FacturarCliente {

	public String codigoCliente;
	public Date fechaInicioFacturacion;
	public Date fechaFinFacturacion;
	public boolean esSimulacion;

	public FacturarCliente(String codigoCliente, Date fechaInicioFacturacion, Date fechaFinFacturacion, boolean esSimulacion) {
		super();
		this.codigoCliente = codigoCliente;
		this.fechaInicioFacturacion = fechaInicioFacturacion;
		this.fechaFinFacturacion = fechaFinFacturacion;
		this.esSimulacion = esSimulacion;
	}

	public String getCodigoCliente() {
		return codigoCliente;
	}

	public void setCodigoCliente(String codigoCliente) {
		this.codigoCliente = codigoCliente;
	}

	public Date getFechaInicioFacturacion() {
		return fechaInicioFacturacion;
	}

	public void setFechaInicioFacturacion(Date fechaInicioFacturacion) {
		this.fechaInicioFacturacion = fechaInicioFacturacion;
	}
	
	public Date getFechaFinFacturacion() {
		return fechaFinFacturacion;
	}

	public void setFechaFinFacturacion(Date fechaFinFacturacion) {
		this.fechaFinFacturacion = fechaFinFacturacion;
	}

	public boolean isEsSimulacion() {
		return esSimulacion;
	}

	public void setEsSimulacion(boolean esSimulacion) {
		this.esSimulacion = esSimulacion;
	}

}
