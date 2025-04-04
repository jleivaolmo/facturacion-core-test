package com.echevarne.sap.cloud.facturacion.model.solicitudrecibida;

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

/**
 * Class for the Entity {@link PetMuesAlerta}.
 *
 * <p>
 * The persistent class. . .T_PetMuesAlerta
 * </p>
 * <p>
 * This is a simple description of the class. . .
 * </p>
 *
 * @author Steven Ricardo Mendez Brenes
 * @version 1.0
 * @since 06/24/2019
 *
 */
@Entity
@Table(name = ConstEntities.ENTIDAD_PETMUESALERTA, indexes = {
		@Index(name = "IDX_T_PetMuesAlerta_unique", columnList = "fk_PeticionMuestreo,codigoAlerta", unique = true) })
@SapEntitySet(creatable = false, updatable = false, searchable = true, deletable = false)
public class PetMuesAlerta extends BasicEntity implements SetComponent {

	/**
	 *
	 */
	private static final long serialVersionUID = -5184469536025769779L;

	@Basic
	private String codigoAlerta;

	@Basic
	private String textoAlerta;

	@ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name = "fk_PeticionMuestreo", nullable = false)
	@JsonBackReference
	private PeticionMuestreo peticion;

	/**
	 * @return the codigoAlerta
	 */
	public String getCodigoAlerta() {
		return this.codigoAlerta;
	}

	/**
	 * @param codigoAlerta the codigoAlerta to set
	 */
	public void setCodigoAlerta(String codigoAlerta) {
		this.codigoAlerta = codigoAlerta;
	}

	/**
	 * @return the textoAlerta
	 */
	public String getTextoAlerta() {
		return this.textoAlerta;
	}

	/**
	 * @param textoAlerta the textoAlerta to set
	 */
	public void setTextoAlerta(String textoAlerta) {
		this.textoAlerta = textoAlerta;
	}

	/**
	 * @return the peticion
	 */
	public PeticionMuestreo getPeticion() {
		return peticion;
	}

	/**
	 * @param peticion the peticion to set
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
		PetMuesAlerta other = (PetMuesAlerta) obj;
		if (codigoAlerta == null) {
			if (other.codigoAlerta != null)
				return false;
		} else if (!codigoAlerta.equals(other.codigoAlerta))
			return false;
		if (textoAlerta == null) {
			if (other.textoAlerta != null)
				return false;
		} else if (!textoAlerta.equals(other.textoAlerta))
			return false;
		return true;
	}

	@Override
	public void setCabecera(BasicEntity cabecera) {
		this.setPeticion((PeticionMuestreo) cabecera);
	}
}
