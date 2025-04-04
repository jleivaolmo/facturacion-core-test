package com.echevarne.sap.cloud.facturacion.model.actualizacion;

import javax.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.var;

import java.util.Calendar;

@Entity
@Data
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Getter
@Setter(AccessLevel.NONE)
@EqualsAndHashCode(callSuper = false)
@Table(name = "V_UPDATE_PETCLIENTE")
@Cacheable(false)
public class PeticionesCliente {
	
	@Id
	@Basic
	private Long idTrazabilidad;
	
	@Basic
	private String codigoCliente;
	
	@Basic
	private String codigoPeticion;

	@Basic
	private Long idSI;

	@Basic
	private Long idSM;

	@Basic
	private Long idPM;

	@Basic
	private String codigoDelegacion;

	@Basic
	private String organizacionVentas;

	@Basic
	private String codigoGrupoEstado;
	
	@Basic
	private String codigoEstado;
	
	@Basic
	private String areaVentas;
	
	@Basic
	private String cargoPeticion;
	
	@Basic
	private String codAlerta;
	
	@Basic
	private String codDivisa;
	
	@Basic
	private String codHistClinica;
	
	@Basic
	private String codCanal;

	@Basic
	private String codSector;
	
	@Basic
	private String codPaciente;
	
	@Basic
	private String codPetLims;

	@Basic
	private String codRefExt;
	
	@Basic
	private String codTarifa;
	
	@Basic
	private String codUsrExt;
	
	@Basic
	private String compania;
	
	@Basic
	private String conceptoFact;
	
	@Basic
	private String motivoEstado;
	
	@Basic
	private String numeroContrato;
	
	@Basic
	private String provRemitente;
	
	@Basic
	private String refCliente;

	@Basic
	private String remitente;
	
	@Basic
	private String tipoContrato;
	
	@Basic
	private String tipoCotizacion;
	
	@Basic
	private Integer tipoPeticion;
	
	@Basic
	private String material;
	
	@Basic
	private Boolean esMixta;
	
	@Basic
	private Boolean esMuestraRemitida;
	
	@Basic
	private Boolean esPrivada;
	
	@Basic
	private Calendar fechaRecMuestra;
	
	@Basic
	private Calendar fechaTomaMuestra;
	
	@Basic
	private Calendar fechaActualizacion;
	
	@Basic
	private Calendar fechaPeticion;
	
	@Basic
	private Calendar fechaCreacion;
	
	@Basic
	private Calendar fechaFactura;
	
	@Basic
	private String factura;
	
	@Basic
	private String lote;
	
	@Basic
	private String lugarMuestreo;
	
	@Basic
	private String nombreAnimal;
	
	@Basic
	private String codigoPropietario;
	
	@Basic
	private String nombreDescr;
	
	@Basic
	private String pedidoVenta;
	
	@Basic
	private String prefactura;

}
