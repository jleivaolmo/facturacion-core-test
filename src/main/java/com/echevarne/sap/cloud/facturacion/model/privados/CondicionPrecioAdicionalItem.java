package com.echevarne.sap.cloud.facturacion.model.privados;

import java.math.BigDecimal;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

import com.echevarne.sap.cloud.facturacion.model.BasicEntity;
import com.echevarne.sap.cloud.facturacion.model.trazabilidad.Trazabilidad;
import com.echevarne.sap.cloud.facturacion.odata.annotations.SapEntitySet;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "T_CondicionPrecioAdicionalItem")
@SapEntitySet(creatable = true, updatable = true, searchable = true, deletable = true)
public class CondicionPrecioAdicionalItem extends BasicEntity {

	/**
	 *
	 */
	private static final long serialVersionUID = -1120128189214282662L;

	@ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name = "fk_Trazabilidad")
	@JsonIgnore
	private Trazabilidad trazabilidad;

	@ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name = "fk_MasDataDescuento")
	@JsonIgnore
	private MasDataDescuento descuento;

	@Basic
	@Column(precision = 16, scale = 3)
	private BigDecimal importeCondicion;

	@Basic
	private String conditionType;

	@Override
	public boolean onEquals(Object obj) {
		if (this == obj)
			return true;
		if (getClass() != obj.getClass())
			return false;
		CondicionPrecioAdicionalItem other = (CondicionPrecioAdicionalItem) obj;
		if (descuento == null) {
			if (other.descuento != null)
				return false;
		} else if (!descuento.equals(other.descuento))
			return false;
		if (trazabilidad == null) {
			if (other.trazabilidad != null)
				return false;
		} else if (!trazabilidad.equals(other.trazabilidad))
			return false;
		return true;
	}

}
