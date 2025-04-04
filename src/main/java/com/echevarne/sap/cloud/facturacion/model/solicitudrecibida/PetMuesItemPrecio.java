package com.echevarne.sap.cloud.facturacion.model.solicitudrecibida;

import java.math.BigDecimal;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.echevarne.sap.cloud.facturacion.constants.ConstEntities;
import com.echevarne.sap.cloud.facturacion.model.BasicEntity;
import com.echevarne.sap.cloud.facturacion.odata.annotations.SapEntitySet;
import com.fasterxml.jackson.annotation.JsonBackReference;

import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

/**
 * Class for the Entity {@link PetMuesItemPrecio}.
 *
 * <p>The persistent class. . .T_PetMuesItemPrecio</p>
 * <p>This is a simple description of the class. . .</p>
 *
 * @author Steven Ricardo Mendez Brenes
 * @version 1.0
 * @since 06/24/2019
 *
 */
@Entity
@Table(name = ConstEntities.ENTIDAD_PETMUESITEMPRECIO,
indexes={@Index(name = "IDX_byPriceItem",  columnList="fk_PeticionMuestreoItems,codigoDivisa", unique=true)}
)
@SapEntitySet(creatable = false, updatable = false, searchable = true, deletable = false)
public class PetMuesItemPrecio extends BasicEntity implements SetComponent {

	private static final long serialVersionUID = 8483959603060313885L;

	@Column(precision = 16, scale = 3)
	private BigDecimal precioBase;

	@Column(precision = 16, scale = 3)
	private BigDecimal precioUnitario;

	@Basic
	private String codigoDivisa;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_PeticionMuestreoItems", nullable = false)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JsonBackReference
	private PeticionMuestreoItems prueba;

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
	 * @return the muestra
	 */
	public PeticionMuestreoItems getPrueba() {
		return prueba;
	}

	/**
	 * @param prueba
	 *            the muestra to set
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
		if (this == obj)
			return true;
		if (getClass() != obj.getClass())
			return false;
		PetMuesItemPrecio other = (PetMuesItemPrecio) obj;
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
		this.setPrueba((PeticionMuestreoItems) cabecera);
	}
}
