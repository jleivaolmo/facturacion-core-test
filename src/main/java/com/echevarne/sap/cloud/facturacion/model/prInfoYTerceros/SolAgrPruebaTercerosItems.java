package com.echevarne.sap.cloud.facturacion.model.prInfoYTerceros;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.echevarne.sap.cloud.facturacion.model.trazabilidad.Trazabilidad;
import com.echevarne.sap.cloud.facturacion.odata.annotations.SapEntitySet;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.EqualsAndHashCode;

import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "T_SolAgrPruebaTercerosItems")
@SapEntitySet(creatable = false, updatable = false, searchable = true, deletable = false)
public class SolAgrPruebaTercerosItems extends SolAgrPruebaItemBaseEntity {

	private static final long serialVersionUID = 1317821661399449861L;

	@ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name = "fk_SolAgrPruebaTerceros", nullable = false)
	@EqualsAndHashCode.Exclude
	@JsonBackReference
	private SolAgrPruebaTerceros agrupacion;

	@OneToMany(cascade = {CascadeType.REFRESH,CascadeType.MERGE}, mappedBy = "itemPruebaTerceros")
	@JsonIgnore
	private Set<Trazabilidad> trazabilidad;

	public SolAgrPruebaTercerosItems() {
		trazabilidad = new HashSet<Trazabilidad>();
	}

	@Override
	public void setAgrupacion(SolAgrPruebaBaseEntity agrupacion) {
		this.agrupacion = (SolAgrPruebaTerceros) agrupacion;
	}

	@Override
	public Set<Trazabilidad> getTrazabilidad() {
		return trazabilidad;
	}

	@Override
	public void addTrazabilidad(Trazabilidad trazabilidad) {
		this.trazabilidad.add(trazabilidad);
	}

	public SolAgrPruebaTerceros getAgrupacion() {
		return agrupacion;
	}
}

