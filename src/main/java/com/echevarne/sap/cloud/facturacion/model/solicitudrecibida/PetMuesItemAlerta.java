package com.echevarne.sap.cloud.facturacion.model.solicitudrecibida;

import javax.persistence.*;

import com.echevarne.sap.cloud.facturacion.constants.ConstEntities;
import com.echevarne.sap.cloud.facturacion.model.BasicEntity;
import com.echevarne.sap.cloud.facturacion.odata.annotations.SapEntitySet;
import com.fasterxml.jackson.annotation.JsonBackReference;

import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

/**
 * Class for the Entity {@link PetMuesItemAlerta}.
 *
 * <p>The persistent class. . .T_PetMuesItemAlerta</p>
 * <p>This is a simple description of the class. . .</p>
 *
 * @author Steven Ricardo Mendez Brenes
 * @version 1.0
 * @since 06/24/2019
 *
 */
@Entity
@Table(name = ConstEntities.ENTIDAD_PETMUESITEMALERTA,
indexes={@Index(name = "PetMuesItemAlerta_byCodigoAlerta",  columnList="fk_PeticionMuestreoItems,codigoAlerta", unique=true)}
)
@SapEntitySet(creatable = false, updatable = false, searchable = true, deletable = false)
public class PetMuesItemAlerta extends BasicEntity implements SetComponent {

	private static final long serialVersionUID = -1192849899402736478L;

	@Basic
	private String codigoAlerta;

	@Basic
	private String textoAlerta;

	@ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name = "fk_PeticionMuestreoItems", nullable = false)
	@JsonBackReference
	private PeticionMuestreoItems prueba;

	/**
	 * @return the codigoAlerta
	 */
	public String getCodigoAlerta() {
		return this.codigoAlerta;
	}

	/**
	 * @param codigoAlerta
	 *            the codigoAlerta to set
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
	 * @param textoAlerta
	 *            the textoAlerta to set
	 */
	public void setTextoAlerta(String textoAlerta) {
		this.textoAlerta = textoAlerta;
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
		PetMuesItemAlerta other = (PetMuesItemAlerta) obj;
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
		this.setPrueba((PeticionMuestreoItems) cabecera);
	}

}
