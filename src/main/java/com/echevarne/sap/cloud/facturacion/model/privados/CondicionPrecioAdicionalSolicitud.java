package com.echevarne.sap.cloud.facturacion.model.privados;

import java.math.BigDecimal;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

import com.echevarne.sap.cloud.facturacion.model.BasicEntity;
import com.echevarne.sap.cloud.facturacion.model.trazabilidad.TrazabilidadSolicitud;
import com.echevarne.sap.cloud.facturacion.odata.annotations.SapEntitySet;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "T_CondicionPrecioAdicionalSolicitud")
@SapEntitySet(creatable = true, updatable = true, searchable = true, deletable = true)
public class CondicionPrecioAdicionalSolicitud extends BasicEntity {

	/**
	 *
	 */
	private static final long serialVersionUID = -1120128189214282662L;

	@ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name = "fk_TrazabilidadSolicitud")
	@JsonIgnore
	private TrazabilidadSolicitud trazabilidadSolicitud;

	@ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name = "fk_MasDataDescuento", nullable = true)
	@JsonIgnore
	private MasDataDescuento descuento;

	@Basic
	private BigDecimal importeCondicion;

	@Basic
	private String conditionType;

	@Override
	public boolean onEquals(Object obj) {
		if (this == obj)
			return true;
		if (getClass() != obj.getClass())
			return false;
		CondicionPrecioAdicionalSolicitud other = (CondicionPrecioAdicionalSolicitud) obj;
		if (conditionType == null) {
			if (other.conditionType != null)
				return false;
		} else if (!conditionType.equals(other.conditionType))
			return false;
		if (importeCondicion == null) {
			if (other.importeCondicion != null)
				return false;
		} else if (!importeCondicion.equals(other.importeCondicion))
			return false;
		if (descuento == null) {
			if (other.descuento != null)
				return false;
		} else if (!descuento.equals(other.descuento))
			return false;
		if (trazabilidadSolicitud == null) {
			if (other.trazabilidadSolicitud != null)
				return false;
		} else if (!trazabilidadSolicitud.equals(other.trazabilidadSolicitud))
			return false;
		return true;
	}

}
