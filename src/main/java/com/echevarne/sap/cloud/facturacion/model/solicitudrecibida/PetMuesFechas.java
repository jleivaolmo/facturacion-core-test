package com.echevarne.sap.cloud.facturacion.model.solicitudrecibida;

import java.sql.Time;
import java.util.Date;
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
import com.echevarne.sap.cloud.facturacion.util.CustomLocalTimeDeserializer;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * Class for the Entity {@link PetMuesFechas}.
 *
 * <p>The persistent class. . .T_PetMuesFechas</p>
 * <p>This is a simple description of the class. . .</p>
 *
 * @author Steven Ricardo Mendez Brenes
 * @version 1.0
 * @since 06/24/2019
 *
 */
@Entity
@Table(name = ConstEntities.ENTIDAD_PETMUESFECHAS,
indexes={@Index(name = "IDX_T_PetMuesFechas_unique",  columnList="fk_PeticionMuestreo", unique=true)}
)
@SapEntitySet(creatable = false, updatable = false, searchable = true, deletable = false)
public class PetMuesFechas extends BasicEntity implements SetComponent {

	/**
	 *
	 */
	private static final long serialVersionUID = 7653801586486544513L;

	@Basic
	private Date fechaPeticion;

	@Basic
	private Date fechaTomaMuestra;

	@Basic
	private Date fechaRecepcionMuestra;

	@Basic
	private Date fechaOperacion;

	@Basic
	@JsonDeserialize(using = CustomLocalTimeDeserializer.class)
	private Time horaOperacion;

	@Basic
	private Date fechaDerivacion;

	@Basic
	private Date fechaPrescripcion;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_PeticionMuestreo", nullable = false)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JsonBackReference
	private PeticionMuestreo peticion;

	/**
	 * @return the fechaDerivacion
	 */
	public Date getFechaDerivacion() {
		return this.fechaDerivacion;
	}

	/**
	 * @param fechaDerivacion
	 *            the fechaDerivacion to set
	 */
	public void setFechaDerivacion(Date fechaDerivacion) {
		this.fechaDerivacion = fechaDerivacion;
	}

	/**
	 * @return the fechaOperacion
	 */
	public Date getFechaOperacion() {
		return this.fechaOperacion;
	}

	/**
	 * @param fechaOperacion
	 *            the fechaOperacion to set
	 */
	public void setFechaOperacion(Date fechaOperacion) {
		this.fechaOperacion = fechaOperacion;
	}

	/**
	 * @return the fechaPeticion
	 */
	public Date getFechaPeticion() {
		return this.fechaPeticion;
	}

	/**
	 * @param fechaPeticion
	 *            the fechaPeticion to set
	 */
	public void setFechaPeticion(Date fechaPeticion) {
		this.fechaPeticion = fechaPeticion;
	}

	/**
	 * @return the fechaPrescripcion
	 */
	public Date getFechaPrescripcion() {
		return this.fechaPrescripcion;
	}

	/**
	 * @param fechaPrescripcion
	 *            the fechaPrescripcion to set
	 */
	public void setFechaPrescripcion(Date fechaPrescripcion) {
		this.fechaPrescripcion = fechaPrescripcion;
	}

	/**
	 * @return the fechaRecepcion
	 */
	public Date getFechaRecepcionMuestra() {
		return this.fechaRecepcionMuestra;
	}

	/**
	 * @param fechaRecepcionMuestra
	 *            the fechaRecepcionMuestra to set
	 */
	public void setFechaRecepcionMuestra(Date fechaRecepcionMuestra) {
		this.fechaRecepcionMuestra = fechaRecepcionMuestra;
	}

	/**
	 * @return the fechaTomaMuestra
	 */
	public Date getFechaTomaMuestra() {
		return this.fechaTomaMuestra;
	}

	/**
	 * @param fechaTomaMuestra
	 *            the fechaTomaMuestra to set
	 */
	public void setFechaTomaMuestra(Date fechaTomaMuestra) {
		this.fechaTomaMuestra = fechaTomaMuestra;
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

	public Time getHoraOperacion() {
		return horaOperacion;
	}

	public void setHoraOperacion(Time horaOperacion) {
		this.horaOperacion = horaOperacion;
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
		PetMuesFechas other = (PetMuesFechas) obj;
		if (fechaDerivacion == null) {
			if (other.fechaDerivacion != null)
				return false;
		} else if (!fechaDerivacion.equals(other.fechaDerivacion))
			return false;
		if (fechaOperacion == null) {
			if (other.fechaOperacion != null)
				return false;
		} else if (!fechaOperacion.equals(other.fechaOperacion))
			return false;
		if (fechaPeticion == null) {
			if (other.fechaPeticion != null)
				return false;
		} else if (!fechaPeticion.equals(other.fechaPeticion))
			return false;
		if (fechaPrescripcion == null) {
			if (other.fechaPrescripcion != null)
				return false;
		} else if (!fechaPrescripcion.equals(other.fechaPrescripcion))
			return false;
		if (fechaRecepcionMuestra == null) {
			if (other.fechaRecepcionMuestra != null)
				return false;
		} else if (!fechaRecepcionMuestra.equals(other.fechaRecepcionMuestra))
			return false;
		if (fechaTomaMuestra == null) {
			if (other.fechaTomaMuestra != null)
				return false;
		} else if (!fechaTomaMuestra.equals(other.fechaTomaMuestra))
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
