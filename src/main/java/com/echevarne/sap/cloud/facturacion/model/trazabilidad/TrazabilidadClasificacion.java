package com.echevarne.sap.cloud.facturacion.model.trazabilidad;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;


import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

import com.echevarne.sap.cloud.facturacion.model.BasicEntity;
import com.echevarne.sap.cloud.facturacion.model.masterdata.MasDataTipologiaFacturacion;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Define para cada {@Link SolIndItems} una {@link ClaseTipologiaFacturacion} en base a {@link TipologiaFacturacion}
 * @author Hernan Girardi
 * @since 28/04/2020
 */
@Entity
@Table(name = "T_TrazabilidadClasificacion")
public class TrazabilidadClasificacion extends BasicEntity {

	private static final long serialVersionUID = 7606471629277557185L;

	@OneToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name = "fk_Trazabilidad", nullable = false)
	@JsonIgnore
	private Trazabilidad trazabilidadPrueba;

	@ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name = "fk_MasDataTipologiaFacturacion", nullable = true)
	@JsonIgnore
	private MasDataTipologiaFacturacion tipologia;

	private String tipologiaValue;

	public Trazabilidad getTrazabilidadPrueba() {
		return this.trazabilidadPrueba;
	}

	public void setTrazabilidadPrueba(Trazabilidad trazabilidadPrueba) {
		this.trazabilidadPrueba = trazabilidadPrueba;
	}

	public MasDataTipologiaFacturacion getTipologia() {
		return this.tipologia;
	}

	public void setTipologia(MasDataTipologiaFacturacion tipologia) {
		this.tipologia = tipologia;
	}

	public String getTipologiaValue() {
		return this.tipologiaValue;
	}

	public void setTipologiaValue(String tipologiaValue) {
		this.tipologiaValue = tipologiaValue;
	}

	@Override
	public boolean onEquals(Object obj) {
		if (this == obj)
			return true;
		if (getClass() != obj.getClass())
			return false;
		TrazabilidadClasificacion other = (TrazabilidadClasificacion) obj;
		if (trazabilidadPrueba == null) {
			if (other.trazabilidadPrueba != null)
				return false;
		} else if (!trazabilidadPrueba.equals(other.trazabilidadPrueba))
			return false;
		if (tipologia==null) {
			if (other.tipologia != null)
				return false;
		} else if (!tipologia.equals(other.tipologia))
			return false;
		if (tipologiaValue.isEmpty()) {
			if (other.tipologiaValue != null)
				return false;
		} else if (!tipologiaValue.equals(other.tipologiaValue))
			return false;
		return true;
	}
}
