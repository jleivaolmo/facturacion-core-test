package com.echevarne.sap.cloud.facturacion.model.salidas;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import com.echevarne.sap.cloud.facturacion.odata.annotations.SapEntitySet;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@Table(name = "V_SALIDALIQUIDACIONREMITENTES")
@SapEntitySet(creatable = false, updatable = false, searchable = false, deletable = false)
@IdClass(SalidaLiquidacionKey.class)
public class SalidaLiquidacionRemitentes implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8826340799954048722L;
	
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

	@Basic
	private String nombreProveedor;

	@Basic
	private String oficinaVentas;

	@Basic
	private String nombreOficina;

	@Basic
	private String remitente;

	@Basic
	private String nombreRemitente;

	@Basic
	private String tipo;

	@Basic
	private String compania;

	@Basic
	private String nombreCompania;

	@Basic
	private String paciente;

	@Basic
	private Timestamp fechaPeticion;

	@Basic
	private BigDecimal importe;
	
	@Basic
	private String moneda;

	@Basic
	private String codigoReferenciaCliente;

	@Basic
	private Timestamp fecha;
	
	@Basic
	private String listaEmailsDestinatarios;
	
	@Basic
	private String passwrd;
	
	@Basic
	private Long idAgrLiq;
	
	@Basic
	private Integer numPruebas;
}
