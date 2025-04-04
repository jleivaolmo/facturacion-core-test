package com.echevarne.sap.cloud.facturacion.model.solicitudrecibida;

import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;


import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

import com.echevarne.sap.cloud.facturacion.constants.ConstEntities;
import com.echevarne.sap.cloud.facturacion.model.BasicEntity;
import com.echevarne.sap.cloud.facturacion.odata.annotations.SapEntitySet;
import com.fasterxml.jackson.annotation.JsonBackReference;

/**
 * Class for the Entity {@link PetMuesClinicos}.
 *
 * <p>The persistent class. . .T_PetMuesClinicos</p>
 * <p>This is a simple description of the class. . .</p>
 *
 * @author Steven Ricardo Mendez Brenes
 * @version 1.0
 * @since 06/24/2019
 *
 */
@Entity
@Table(name = ConstEntities.ENTIDAD_PETMUESCLINICOS, indexes = {
		@Index(name = "PetMuesClinicos_PetMuesAlerta_unique", columnList = "fk_PeticionMuestreo", unique = true) })
@SapEntitySet(creatable = false, updatable = false, searchable = true, deletable = false)
public class PetMuesClinicos extends BasicEntity implements SetComponent {

	private static final long serialVersionUID = 4284647020652399984L;

	@Basic
	private String codigoVisita;

	@Basic
	private String codigoEstudio;

	@Basic
	private String codigoSeleccion;

	@Basic
	private String codigoLote;

	@Basic
	private String codigoHistoriaClinica;

	@Basic
	private String codigoTratamiento;

	@Basic
	private String codigoHabitacion;

	@Basic
	private String codigoCama;

	@Basic
	private String codigoServicio;

	@Basic
	private String codigoLugarMuestreo;

	@Basic
	private String entidadPrescriptora;

	@Basic
	private String codigoOficina;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_PeticionMuestreo", nullable = false)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JsonBackReference
	private PeticionMuestreo peticion;

	/**
	 * @return the codigoVisita
	 */
	public String getCodigoVisita() {
		return this.codigoVisita;
	}

	/**
	 * @param codigoVisita
	 *            the codigoVisita to set
	 */
	public void setCodigoVisita(String codigoVisita) {
		this.codigoVisita = codigoVisita;
	}

	/**
	 * @return the codigoEstudio
	 */
	public String getCodigoEstudio() {
		return this.codigoEstudio;
	}

	/**
	 * @param codigoEstudio
	 *            the codigoEstudio to set
	 */
	public void setCodigoEstudio(String codigoEstudio) {
		this.codigoEstudio = codigoEstudio;
	}

	/**
	 * @return the codigoSeleccion
	 */
	public String getCodigoSeleccion() {
		return this.codigoSeleccion;
	}

	/**
	 * @param codigoSeleccion
	 *            the codigoSeleccion to set
	 */
	public void setCodigoSeleccion(String codigoSeleccion) {
		this.codigoSeleccion = codigoSeleccion;
	}

	/**
	 * @return the codigoLote
	 */
	public String getCodigoLote() {
		return this.codigoLote;
	}

	/**
	 * @param codigoLote
	 *            the codigoLote to set
	 */
	public void setCodigoLote(String codigoLote) {
		this.codigoLote = codigoLote;
	}

	/**
	 * @return the codigoHistoriaClinica
	 */
	public String getCodigoHistoriaClinica() {
		return this.codigoHistoriaClinica;
	}

	/**
	 * @param codigoHistoriaClinica
	 *            the codigoHistoriaClinica to set
	 */
	public void setCodigoHistoriaClinica(String codigoHistoriaClinica) {
		this.codigoHistoriaClinica = codigoHistoriaClinica;
	}

	/**
	 * @return the codigoTratamiento
	 */
	public String getCodigoTratamiento() {
		return this.codigoTratamiento;
	}

	/**
	 * @param codigoTratamiento
	 *            the codigoTratamiento to set
	 */
	public void setCodigoTratamiento(String codigoTratamiento) {
		this.codigoTratamiento = codigoTratamiento;
	}

	/**
	 * @return the codigoHabitacion
	 */
	public String getCodigoHabitacion() {
		return this.codigoHabitacion;
	}

	/**
	 * @param codigoHabitacion
	 *            the codigoHabitacion to set
	 */
	public void setCodigoHabitacion(String codigoHabitacion) {
		this.codigoHabitacion = codigoHabitacion;
	}

	/**
	 * @return the codigoCama
	 */
	public String getCodigoCama() {
		return this.codigoCama;
	}

	/**
	 * @param codigoCama
	 *            the codigoCama to set
	 */
	public void setCodigoCama(String codigoCama) {
		this.codigoCama = codigoCama;
	}

	/**
	 * @return the codigoServicio
	 */
	public String getCodigoServicio() {
		return this.codigoServicio;
	}

	/**
	 * @param codigoServicio
	 *            the codigoServicio to set
	 */
	public void setCodigoServicio(String codigoServicio) {
		this.codigoServicio = codigoServicio;
	}

	/**
	 * @return the codigoLugarMuestreo
	 */
	public String getCodigoLugarMuestreo() {
		return this.codigoLugarMuestreo;
	}

	/**
	 * @param codigoLugarMuestreo
	 *            the codigoLugarMuestreo to set
	 */
	public void setCodigoLugarMuestreo(String codigoLugarMuestreo) {
		this.codigoLugarMuestreo = codigoLugarMuestreo;
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

	/**
	 * @return the entidadPrescriptora
	 */
	public String getEntidadPrescriptora() {
		return this.entidadPrescriptora;
	}

	/**
	 * @param entidadPrescriptora
	 *            the entidadPrescriptora to set
	 */
	public void setEntidadPrescriptora(String entidadPrescriptora) {
		this.entidadPrescriptora = entidadPrescriptora;
	}

	/**
	 * @return the codigoOficina
	 */
	public String getCodigoOficina() {
		return this.codigoOficina;
	}

	/**
	 * @param codigoOficina
	 *            the codigoOficina to set
	 */
	public void setCodigoOficina(String codigoOficina) {
		this.codigoOficina = codigoOficina;
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
		PetMuesClinicos other = (PetMuesClinicos) obj;
		if (codigoCama == null) {
			if (other.codigoCama != null)
				return false;
		} else if (!codigoCama.equals(other.codigoCama))
			return false;
		if (codigoEstudio == null) {
			if (other.codigoEstudio != null)
				return false;
		} else if (!codigoEstudio.equals(other.codigoEstudio))
			return false;
		if (codigoHabitacion == null) {
			if (other.codigoHabitacion != null)
				return false;
		} else if (!codigoHabitacion.equals(other.codigoHabitacion))
			return false;
		if (codigoHistoriaClinica == null) {
			if (other.codigoHistoriaClinica != null)
				return false;
		} else if (!codigoHistoriaClinica.equals(other.codigoHistoriaClinica))
			return false;
		if (codigoLote == null) {
			if (other.codigoLote != null)
				return false;
		} else if (!codigoLote.equals(other.codigoLote))
			return false;
		if (codigoLugarMuestreo == null) {
			if (other.codigoLugarMuestreo != null)
				return false;
		} else if (!codigoLugarMuestreo.equals(other.codigoLugarMuestreo))
			return false;
		if (codigoSeleccion == null) {
			if (other.codigoSeleccion != null)
				return false;
		} else if (!codigoSeleccion.equals(other.codigoSeleccion))
			return false;
		if (codigoServicio == null) {
			if (other.codigoServicio != null)
				return false;
		} else if (!codigoServicio.equals(other.codigoServicio))
			return false;
		if (codigoTratamiento == null) {
			if (other.codigoTratamiento != null)
				return false;
		} else if (!codigoTratamiento.equals(other.codigoTratamiento))
			return false;
		if (codigoVisita == null) {
			if (other.codigoVisita != null)
				return false;
		} else if (!codigoVisita.equals(other.codigoVisita))
			return false;
		if (entidadPrescriptora == null) {
			if (other.entidadPrescriptora != null)
				return false;
		} else if (!entidadPrescriptora.equals(other.entidadPrescriptora))
			return false;
		if (codigoOficina == null) {
			if (other.codigoOficina != null)
				return false;
		} else if (!codigoOficina.equals(other.codigoOficina))
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
