package com.echevarne.sap.cloud.facturacion.model.solicitudrecibida;

import java.util.Date;
import java.util.Objects;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

import com.echevarne.sap.cloud.facturacion.constants.ConstEntities;
import com.echevarne.sap.cloud.facturacion.model.BasicEntity;
import com.echevarne.sap.cloud.facturacion.odata.annotations.SapEntitySet;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

/**
 * Class for the Entity {@link PetMuesItemEstado}.
 *
 * <p>The persistent class. . .T_PetMuesItemEstado</p>
 * <p>This is a simple description of the class. . .</p>
 *
 * @author Steven Ricardo Mendez Brenes
 * @version 1.0
 * @since 06/24/2019
 *
 */
@Entity
@Table(name = ConstEntities.ENTIDAD_PETMUESITEMESTADO,
indexes={@Index(name = "IDX_byCodigoEstado",  columnList="fk_PeticionMuestreoItems,codigoEstado", unique=true)}
)
@SapEntitySet(creatable = false, updatable = false, searchable = true, deletable = false)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class PetMuesItemEstado extends BasicEntity implements SetComponent {

	/**
	 *
	 */
	private static final long serialVersionUID = -2305198315085996L;

	@Basic
	private String codigoEstado;

	@Basic
	@JsonProperty("codigoEstadoSecundario")
	private String codigoEstadoSec;

	@Basic
	private Date fechaEstado;

	@ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name = "fk_PeticionMuestreoItems", nullable = false)
	@JsonBackReference
	private PeticionMuestreoItems prueba;

	/**
	 * @return the codigoEstado
	 */
	public String getCodigoEstado() {
		return this.codigoEstado;
	}

	/**
	 * @param codigoEstado
	 *            the codigoEstado to set
	 */
	public void setCodigoEstado(String codigoEstado) {
		this.codigoEstado = codigoEstado;
	}

	/**
	 * @return the codigoEstadoSec
	 */
	public String getCodigoEstadoSec() {
		return this.codigoEstadoSec;
	}

	/**
	 * @param codigoEstadoSec
	 *            the codigoEstadoSec to set
	 */
	public void setCodigoEstadoSec(String codigoEstadoSec) {
		this.codigoEstadoSec = codigoEstadoSec;
	}

	/**
	 * @return the fechaEstado
	 */
	public Date getFechaEstado() {
		return this.fechaEstado;
	}

	/**
	 * @param fechaEstado
	 *            the fechaEstado to set
	 */
	public void setFechaEstado(Date fechaEstado) {
		this.fechaEstado = fechaEstado;
	}

	/**
	 * @return the prueba
	 */
	public PeticionMuestreoItems getPrueba() {
		return prueba;
	}

	/**
	 * @param prueba
	 *            the prueba to set
	 */
	public void setPrueba(PeticionMuestreoItems prueba) {
		this.prueba = prueba;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean onEquals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		PetMuesItemEstado other = (PetMuesItemEstado) obj;
		return  Objects.equals(this.codigoEstado, other.codigoEstado) &&
				Objects.equals(this.codigoEstadoSec, other.codigoEstadoSec) &&
				Objects.equals(this.fechaEstado, other.fechaEstado);
	}

	@Override
	public void setCabecera(BasicEntity cabecera) {
		if (!(cabecera instanceof PeticionMuestreoItems)) return;
		this.setPrueba((PeticionMuestreoItems)cabecera);
	}
}
