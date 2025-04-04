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
@Table(name = "T_SolAgrPruebaInformadaItems")
@SapEntitySet(creatable = false, updatable = false, searchable = true, deletable = false)
public class SolAgrPruebaInformadaItems extends SolAgrPruebaItemBaseEntity {

	private static final long serialVersionUID = -8738792793179433214L;

	@ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name = "fk_SolAgrPruebaInformada", nullable = false)
	@EqualsAndHashCode.Exclude
	@JsonBackReference
	private SolAgrPruebaInformada agrupacion;

	@OneToMany(cascade = {CascadeType.REFRESH,CascadeType.MERGE}, mappedBy = "itemPruebaInformada")
	@JsonIgnore
	private Set<Trazabilidad> trazabilidad;

	public SolAgrPruebaInformadaItems() {
		trazabilidad = new HashSet<Trazabilidad>();
	}

	@Override
	public void setAgrupacion(SolAgrPruebaBaseEntity agrupacion) {
		this.agrupacion = (SolAgrPruebaInformada) agrupacion;
	}

	@Override
	public Set<Trazabilidad> getTrazabilidad() {
		return trazabilidad;
	}

	@Override
	public void addTrazabilidad(Trazabilidad trazabilidad) {
		this.trazabilidad.add(trazabilidad);
	}

	public SolAgrPruebaInformada getAgrupacion() {
		return agrupacion;
	}
}

