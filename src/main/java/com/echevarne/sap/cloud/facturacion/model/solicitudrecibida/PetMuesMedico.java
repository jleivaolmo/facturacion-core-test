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
 * Class for the Entity {@link PetMuesMedico}.
 *
 * <p>The persistent class. . .T_PetMuesMedico</p>
 * <p>This is a simple description of the class. . .</p>
 *
 * @author Steven Ricardo Mendez Brenes
 * @version 1.0
 * @since 06/24/2019
 *
 */
@Entity
@Table(name = ConstEntities.ENTIDAD_PETMUESMEDICO,
		indexes={@Index(name = "PetMuesMedico_byContenedor",  columnList="fk_PeticionMuestreo,codigoMedico", unique=true)})
@SapEntitySet(creatable = false, updatable = false, searchable = true, deletable = false)
public class PetMuesMedico extends BasicEntity implements SetComponent {

	/**
	 *
	 */
	private static final long serialVersionUID = -3364642937380784652L;

	@Basic
	private String codigoMedico;

	@Basic
	private String tipoMedico;

	@Basic
	private String especialidadMedico;

	@OneToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name = "fk_PeticionMuestreo", nullable = false)
	@JsonBackReference
	private PeticionMuestreo peticion;

	/**
	 * @return the codigoMedico
	 */
	public String getCodigoMedico() {
		return this.codigoMedico;
	}

	/**
	 * @param codigoMedico
	 *            the codigoMedico to set
	 */
	public void setCodigoMedico(String codigoMedico) {
		this.codigoMedico = codigoMedico;
	}

	/**
	 * @return the tipoMedico
	 */
	public String getTipoMedico() {
		return this.tipoMedico;
	}

	/**
	 * @param tipoMedico
	 *            the tipoMedico to set
	 */
	public void setTipoMedico(String tipoMedico) {
		this.tipoMedico = tipoMedico;
	}

	/**
	 * @return the especialidadMedico
	 */
	public String getEspecialidadMedico() {
		return this.especialidadMedico;
	}

	/**
	 * @param especialidadMedico
	 *            the especialidadMedico to set
	 */
	public void setEspecialidadMedico(String especialidadMedico) {
		this.especialidadMedico = especialidadMedico;
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
		PetMuesMedico other = (PetMuesMedico) obj;
		if (codigoMedico == null) {
			if (other.codigoMedico != null)
				return false;
		} else if (!codigoMedico.equals(other.codigoMedico))
			return false;
		if (especialidadMedico == null) {
			if (other.especialidadMedico != null)
				return false;
		} else if (!especialidadMedico.equals(other.especialidadMedico))
			return false;
		if (tipoMedico == null) {
			if (other.tipoMedico != null)
				return false;
		} else if (!tipoMedico.equals(other.tipoMedico))
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
