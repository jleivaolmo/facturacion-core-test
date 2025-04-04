package com.echevarne.sap.cloud.facturacion.model.solicitudindividual;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.echevarne.sap.cloud.facturacion.model.BasicEntity;
import com.echevarne.sap.cloud.facturacion.odata.annotations.SapEntitySet;
import com.fasterxml.jackson.annotation.JsonBackReference;

import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

/**
 * Class for the Entity {@link SolIndItemTexts}.
 *
 * <p>The persistent class. . .T_SolIndItemTexts</p>
 * <p>This is a simple description of the class. . .</p>
 *
 * @author Steven Ricardo Mendez Brenes
 * @version 1.0
 * @since 06/24/2019
 *
 */
@Entity
@Table(name = "T_SolIndItemTexts")
@SapEntitySet(creatable = false, updatable = false, searchable = true, deletable = false)
public class SolIndItemTexts extends BasicEntity implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -5264811941422172743L;

	@Basic
	private String Language;
	@Basic
	private String LongTextID;
	@Basic
	private String LongTexto;

	@ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name = "fk_SolicitudIndividualItem", nullable = false)
	@JsonBackReference
	private SolIndItems posicion;

	/**
	 * @return the language
	 */
	public String getLanguage() {
		return this.Language;
	}
	/**
	 * @param language the language to set
	 */
	public void setLanguage(String language) {
		this.Language = language;
	}
	/**
	 * @return the longTextID
	 */
	public String getLongTextID() {
		return this.LongTextID;
	}
	/**
	 * @param longTextID the longTextID to set
	 */
	public void setLongTextID(String longTextID) {
		this.LongTextID = longTextID;
	}
	/**
	 * @return the longText
	 */
	public String getLongText() {
		return this.LongTexto;
	}
	/**
	 * @param longText the longText to set
	 */
	public void setLongText(String longText) {
		this.LongTexto = longText;
	}

	/**
	 * @return the posicion
	 */
	public SolIndItems getPosicion() {
		return posicion;
	}

	/**
	 * @param posicion
	 *            the posicion to set
	 */
	public void setPosicion(SolIndItems posicion) {
		this.posicion = posicion;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean onEquals(Object obj) {
		if (this == obj)
			return true;
		if (getClass() != obj.getClass())
			return false;
		SolIndItemTexts other = (SolIndItemTexts) obj;
		if (Language == null) {
			if (other.Language != null)
				return false;
		} else if (!Language.equals(other.Language))
			return false;
		if (LongTexto == null) {
			if (other.LongTexto != null)
				return false;
		} else if (!LongTexto.equals(other.LongTexto))
			return false;
		if (LongTextID == null) {
			if (other.LongTextID != null)
				return false;
		} else if (!LongTextID.equals(other.LongTextID))
			return false;
		return true;
	}

}
