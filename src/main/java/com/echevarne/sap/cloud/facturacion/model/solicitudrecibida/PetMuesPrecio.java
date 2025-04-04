package com.echevarne.sap.cloud.facturacion.model.solicitudrecibida;

import java.math.BigDecimal;
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
 * Class for the Entity {@link PetMuesPrecio}.
 *
 * <p>The persistent class. . .T_PetMuesPrecio</p>
 * <p>This is a simple description of the class. . .</p>
 *
 * @author Steven Ricardo Mendez Brenes
 * @version 1.0
 * @since 06/24/2019
 *
 */
@Entity
@Table(name = ConstEntities.ENTIDAD_PETMUESPRECIO,
	indexes={@Index(name = "PetMuesPrecio_byMuestreo",  columnList="fk_PeticionMuestreo", unique=true)})
@SapEntitySet(creatable = false, updatable = false, searchable = true, deletable = false)
public class PetMuesPrecio extends BasicEntity implements SetComponent {

	/**
	 *
	 */
	private static final long serialVersionUID = -3256707538359698121L;

	@Basic
	private BigDecimal precioBase;

	@Basic
	private BigDecimal precioUnitario;

	@Basic
	private String codigoDivisa;

	@OneToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name = "fk_PeticionMuestreo", nullable = false)
	@JsonBackReference
	private PeticionMuestreo peticion;

	/**
	 * @return the codigoDivisa
	 */
	public String getCodigoDivisa() {
		return this.codigoDivisa;
	}

	/**
	 * @param codigoDivisa
	 *            the codigoDivisa to set
	 */
	public void setCodigoDivisa(String codigoDivisa) {
		this.codigoDivisa = codigoDivisa;
	}

	/**
	 * @return the precioBase
	 */
	public BigDecimal getPrecioBase() {
		return this.precioBase;
	}

	/**
	 * @param precioBase
	 *            the precioBase to set
	 */
	public void setPrecioBase(BigDecimal precioBase) {
		this.precioBase = precioBase;
	}

	/**
	 * @return the precioUnitario
	 */
	public BigDecimal getPrecioUnitario() {
		return this.precioUnitario;
	}

	/**
	 * @param precioUnitario
	 *            the precioUnitario to set
	 */
	public void setPrecioUnitario(BigDecimal precioUnitario) {
		this.precioUnitario = precioUnitario;
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
		PetMuesPrecio other = (PetMuesPrecio) obj;
		if (codigoDivisa == null) {
			if (other.codigoDivisa != null)
				return false;
		} else if (!codigoDivisa.equals(other.codigoDivisa))
			return false;
		if (precioBase == null) {
			if (other.precioBase != null)
				return false;
		} else if (!precioBase.equals(other.precioBase))
			return false;
		if (precioUnitario == null) {
			if (other.precioUnitario != null)
				return false;
		} else if (!precioUnitario.equals(other.precioUnitario))
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
