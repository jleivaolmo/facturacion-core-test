package com.echevarne.sap.cloud.facturacion.model.solicitudrecibida;

import java.math.BigDecimal;
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

/**
 * Class for the Entity {@link PetMuesContenedor}.
 *
 * <p>The persistent class. . .T_PetMuesContenedor</p>
 * <p>This is a simple description of the class. . .</p>
 *
 * @author Steven Ricardo Mendez Brenes
 * @version 1.0
 * @since 06/24/2019
 *
 */
@Entity
@Table(name = ConstEntities.ENTIDAD_PETMUESCONTENEDOR,
indexes={@Index(name = "IDX_byContenedor",  columnList="fk_PeticionMuestreo,codigoContenedor", unique=true)})
@SapEntitySet(creatable = false, updatable = false, searchable = true, deletable = false)
public class PetMuesContenedor extends BasicEntity implements SetComponent {

	/**
	 *
	 */
	private static final long serialVersionUID = 8926488989472441047L;

	@Basic
	private String codigoContenedor;

	@Basic
	private String descripcionContenedor;

	@Basic
	private BigDecimal cantidadContenedor;

	@ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name = "fk_PeticionMuestreo", nullable = false)
	@JsonBackReference
	private PeticionMuestreo peticion;

	public String getCodigoContenedor() {
		return codigoContenedor;
	}

	public void setCodigoContenedor(String codigoContenedor) {
		this.codigoContenedor = codigoContenedor;
	}

	public String getDescripcionContenedor() {
		return descripcionContenedor;
	}

	public void setDescripcionContenedor(String descripcionContenedor) {
		this.descripcionContenedor = descripcionContenedor;
	}

	public BigDecimal getCantidadContenedor() {
		return cantidadContenedor;
	}

	public void setCantidadContenedor(BigDecimal cantidadContenedor) {
		this.cantidadContenedor = cantidadContenedor;
	}

	public PeticionMuestreo getPeticion() {
		return peticion;
	}

	public void setPeticion(PeticionMuestreo peticion) {
		this.peticion = peticion;
	}
	@Override
	public boolean onEquals(Object obj) {
		if (this == obj)
			return true;
		if (getClass() != obj.getClass())
			return false;
		PetMuesContenedor other = (PetMuesContenedor) obj;
		if (cantidadContenedor == null) {
			if (other.cantidadContenedor != null)
				return false;
		} else if (!cantidadContenedor.equals(other.cantidadContenedor))
			return false;
		if (codigoContenedor == null) {
			if (other.codigoContenedor != null)
				return false;
		} else if (!codigoContenedor.equals(other.codigoContenedor))
			return false;
		if (descripcionContenedor == null) {
			if (other.descripcionContenedor != null)
				return false;
		} else if (!descripcionContenedor.equals(other.descripcionContenedor))
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
