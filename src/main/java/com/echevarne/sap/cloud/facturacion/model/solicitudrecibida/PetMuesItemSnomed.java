package com.echevarne.sap.cloud.facturacion.model.solicitudrecibida;

import javax.persistence.*;

import com.echevarne.sap.cloud.facturacion.constants.ConstEntities;
import com.echevarne.sap.cloud.facturacion.model.BasicEntity;
import com.echevarne.sap.cloud.facturacion.odata.annotations.SapEntitySet;
import com.fasterxml.jackson.annotation.JsonBackReference;

import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

/**
 * Class for the Entity {@link PetMuesItemSnomed}.
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
@Table(name = ConstEntities.ENTIDAD_PETMUESITEMSNOMED,
indexes={@Index(name = "IDX_byCodigoSnomed",  columnList="fk_PeticionMuestreoItems,codigoSnomed", unique=true)}
)
@SapEntitySet(creatable = false, updatable = false, searchable = true, deletable = false)
public class PetMuesItemSnomed extends BasicEntity implements SetComponent {

	/**
	 *
	 */
	private static final long serialVersionUID = -2305198315085996L;

	@Basic
	private String codigoSnomed;

	@ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name = "fk_PeticionMuestreoItems", nullable = false)
	@JsonBackReference
	private PeticionMuestreoItems prueba;

	/**
	 * @return the codigoEstado
	 */
	public String getCodigoSnomed() {
		return this.codigoSnomed;
	}

	/**
	 * @param codigoEstado
	 *            the codigoEstado to set
	 */
	public void setCodigoSnomed(String codigoEstado) {
		this.codigoSnomed = codigoEstado;
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
		if (this == obj)
			return true;
		if (getClass() != obj.getClass())
			return false;
		PetMuesItemSnomed other = (PetMuesItemSnomed) obj;
		if (codigoSnomed == null) {
			if (other.codigoSnomed != null)
				return false;
		} else if (!codigoSnomed.equals(other.codigoSnomed))
			return false;
		return true;
	}

	@Override
	public void setCabecera(BasicEntity cabecera) {
		this.setPrueba((PeticionMuestreoItems) cabecera);
	}

}
