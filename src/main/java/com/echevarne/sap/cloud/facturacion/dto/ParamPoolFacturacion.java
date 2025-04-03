package com.echevarne.sap.cloud.facturacion.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ParamPoolFacturacion implements Serializable, Cloneable {

	/**
	 *
	 */
	private static final long serialVersionUID = -3017072680399704907L;

	private Set<String> clientes = new HashSet<>();
	private Set<String> organizacionesVentas = new HashSet<>();
	private Set<String> codigosDelegacion = new HashSet<>();
	private Set<String> remitentes = new HashSet<>();
	private Set<String> companias = new HashSet<>();
	private Set<Integer> tiposPeticion = new HashSet<>();
	private Set<String> pruebas = new HashSet<>();
	private Set<String> sectores = new HashSet<>();
	private Set<String> codigosPeticion = new HashSet<>();
	private Date fechaIniFacturacion = new Date(0L);
	private Date fechaFinFacturacion = new Date(0L);

	public ParamPoolFacturacion clone() {
		ParamPoolFacturacion paramPoolFactClone = new ParamPoolFacturacion();
		paramPoolFactClone.clientes.addAll(this.clientes);
		paramPoolFactClone.organizacionesVentas.addAll(this.organizacionesVentas);
		paramPoolFactClone.codigosDelegacion.addAll(this.codigosDelegacion);
		paramPoolFactClone.remitentes.addAll(this.remitentes);
		paramPoolFactClone.companias.addAll(this.companias);
		paramPoolFactClone.tiposPeticion.addAll(this.tiposPeticion);
		paramPoolFactClone.pruebas.addAll(this.pruebas);
		paramPoolFactClone.sectores.addAll(this.sectores);
		paramPoolFactClone.codigosPeticion.addAll(this.codigosPeticion);
		paramPoolFactClone.setFechaIniFacturacion(this.fechaIniFacturacion);
		paramPoolFactClone.setFechaFinFacturacion(this.fechaFinFacturacion);
		return paramPoolFactClone;
	}

}
