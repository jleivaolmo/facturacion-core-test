package com.echevarne.sap.cloud.facturacion.model.solicitudrecibida;

import java.util.Date;
import java.util.Set;

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
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Class for the Entity {@link PetMuesEstado}.
 *
 * <p>The persistent class. . .T_PetMuesEstado</p>
 * <p>This is a simple description of the class. . .</p>
 *
 * @author Steven Ricardo Mendez Brenes
 * @version 1.0
 * @since 06/24/2019
 *
 */
@Entity
@Table(name = ConstEntities.ENTIDAD_PETMUESESTADO,
indexes={@Index(name = "IDX_T_PetMuesEstado_unique",  columnList="fk_PeticionMuestreo,codigoEstado", unique=true)}
)
@SapEntitySet(creatable = false, updatable = false, searchable = true, deletable = false)
public class PetMuesEstado extends BasicEntity implements SetComponent {


	/**
	 *
	 */
	private static final long serialVersionUID = -712253355756408364L;

	@Basic
	private String codigoEstado;

	@JsonProperty("codigoEstadoSecundario")
	@Basic
	private String codigoEstadoSec;

	@Basic
	private Date fechaEstado;

	@ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name = "fk_PeticionMuestreo", nullable = false)
	@JsonBackReference
	private PeticionMuestreo peticion;

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
	 * @return the peticion
	 */
	public PeticionMuestreo getPeticion() {
		return peticion;
	}

	/**
	 * @param peticion
	 *            the peticion to set
	 */
	public void setPeticion(PeticionMuestreo peticion) {
		this.peticion = peticion;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean onEquals(Object obj) {
		if (this == obj)
			return true;
		if (getClass() != obj.getClass())
			return false;
		PetMuesEstado other = (PetMuesEstado) obj;
		if (codigoEstado != other.codigoEstado)
			return false;
		if (codigoEstadoSec != other.codigoEstadoSec)
			return false;
		if (fechaEstado == null) {
			if (other.fechaEstado != null)
				return false;
		} else if (!fechaEstado.equals(other.fechaEstado))
			return false;
		return true;
	}

	@Override
	public void setCabecera(BasicEntity cabecera) {
		this.setPeticion((PeticionMuestreo) cabecera);
	}

	@Override
	protected Set<String> getCopyWithoutIdBlacklistFields() {
		final Set<String> fields = super.getCopyWithoutIdBlacklistFields();
		fields.add("peticion");

		return fields;
	}

}
