package com.echevarne.sap.cloud.facturacion.model.actividad;

import java.io.Serializable;

import javax.annotation.Nonnull;
import javax.persistence.Basic;
import javax.persistence.Column;
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
public class CabeceraAMSubKey implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5383111125544572419L;

	@Nonnull
	@Id
	@Column(length = 10)
	private String codigoCliente;
	
	@Nonnull
	@Id
    @Column(length = 4)
	private String codigoOrganizacion;
	
	@Nonnull
	@Id
	@Column(length = 2)
	private String canalDistribucion;
	
	// @Nonnull
	// @Id
	// @Column(length = 2)
	// private String sectorVentas;
	
	@Id
	@Basic
	private Long idReglaFacturacion;
	
	@Basic
	@Id
	private String codigoSeparacion;

}
