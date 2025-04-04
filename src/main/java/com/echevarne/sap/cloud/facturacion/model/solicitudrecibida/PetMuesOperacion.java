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
 * Class for the Entity {@link PetMuesOperacion}.
 *
 * <p>The persistent class. . .T_PetMuesOperacion</p>
 * <p>This is a simple description of the class. . .</p>
 *
 * @author Steven Ricardo Mendez Brenes
 * @version 1.0
 * @since 06/24/2019
 *
 */
@Entity
@Table(name = ConstEntities.ENTIDAD_PETMUESOPERACION,
		indexes={@Index(name = "PetMuesOperacion_byMuestreo",  columnList="fk_PeticionMuestreo", unique=true)})
@SapEntitySet(creatable = false, updatable = false, searchable = true, deletable = false)
public class PetMuesOperacion extends BasicEntity implements SetComponent {

	private static final long serialVersionUID = 6507708735157234702L;

	@Basic
	private String codigoAutorizacion;

	@Basic
	private String codigoBeneficiario;

	@Basic
	private String codigoProceso;

	@Basic
	private String codigoOperacion;

	@Basic
	private String codigoRegistro;

	@OneToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name = "fk_PeticionMuestreo", nullable = false)
	@JsonBackReference
	private PeticionMuestreo peticion;


	/**
	 * @return the codigoAutorizacion
	 */
	public String getCodigoAutorizacion() {
		return this.codigoAutorizacion;
	}

	/**
	 * @param codigoAutorizacion
	 *            the codigoAutorizacion to set
	 */
	public void setCodigoAutorizacion(String codigoAutorizacion) {
		this.codigoAutorizacion = codigoAutorizacion;
	}

	/**
	 * @return the codigoBeneficiario
	 */
	public String getCodigoBeneficiario() {
		return this.codigoBeneficiario;
	}

	/**
	 * @param codigoBeneficiario
	 *            the codigoBeneficiario to set
	 */
	public void setCodigoBeneficiario(String codigoBeneficiario) {
		this.codigoBeneficiario = codigoBeneficiario;
	}

	/**
	 * @return the codigoProceso
	 */
	public String getCodigoProceso() {
		return this.codigoProceso;
	}

	/**
	 * @param codigoProceso
	 *            the codigoProceso to set
	 */
	public void setCodigoProceso(String codigoProceso) {
		this.codigoProceso = codigoProceso;
	}

	/**
	 * @return the codigoOperacion
	 */
	public String getCodigoOperacion() {
		return this.codigoOperacion;
	}

	/**
	 * @param codigoOperacion
	 *            the codigoOperacion to set
	 */
	public void setCodigoOperacion(String codigoOperacion) {
		this.codigoOperacion = codigoOperacion;
	}

	/**
	 * @return the codigoRegistro
	 */
	public String getCodigoRegistro() {
		return this.codigoRegistro;
	}

	/**
	 * @param codigoRegistro
	 *            the codigoRegistro to set
	 */
	public void setCodigoRegistro(String codigoRegistro) {
		this.codigoRegistro = codigoRegistro;
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
		PetMuesOperacion other = (PetMuesOperacion) obj;
		if (codigoAutorizacion == null) {
			if (other.codigoAutorizacion != null)
				return false;
		} else if (!codigoAutorizacion.equals(other.codigoAutorizacion))
			return false;
		if (codigoBeneficiario == null) {
			if (other.codigoBeneficiario != null)
				return false;
		} else if (!codigoBeneficiario.equals(other.codigoBeneficiario))
			return false;
		if (codigoOperacion == null) {
			if (other.codigoOperacion != null)
				return false;
		} else if (!codigoOperacion.equals(other.codigoOperacion))
			return false;
		if (codigoProceso == null) {
			if (other.codigoProceso != null)
				return false;
		} else if (!codigoProceso.equals(other.codigoProceso))
			return false;
		if (codigoRegistro == null) {
			if (other.codigoRegistro != null)
				return false;
		} else if (!codigoRegistro.equals(other.codigoRegistro))
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
