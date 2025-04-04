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
 * Class for the Entity {@link SolIndTexts}.
 *
 * <p>The persistent class. . .T_SolIndTexts</p>
 * <p>This is a simple description of the class. . .</p>
 *
 * @author Steven Ricardo Mendez Brenes
 * @version 1.0
 * @since 06/24/2019
 *
 */
@Entity
@Table(name = "T_SolIndTexts")
@SapEntitySet(creatable = false, updatable = false, searchable = true, deletable = false)
public class SolIndTexts extends BasicEntity implements Serializable {


	/**
	 *
	 */
	private static final long serialVersionUID = -5866403696917786734L;

	@Basic
	private String Language;

	@Basic
	private String LongTextId;

	@Basic
	private String LongTexto;

	@ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name = "fk_SolicitudIndividual", nullable = false)
	@JsonBackReference
	private SolicitudIndividual solicitudInd;

	/**
	 * @return the language
	 */
	public String getLanguage() {
		return this.Language;
	}

	/**
	 * @param language
	 *            the language to set
	 */
	public void setLanguage(String language) {
		this.Language = language;
	}

	/**
	 * @return the longTextId
	 */
	public String getLongTextId() {
		return this.LongTextId;
	}

	/**
	 * @param longTextId
	 *            the longTextId to set
	 */
	public void setLongTextId(String longTextId) {
		this.LongTextId = longTextId;
	}

	/**
	 * @return the longText
	 */
	public String getLongText() {
		return this.LongTexto;
	}

	/**
	 * @param longText
	 *            the longText to set
	 */
	public void setLongText(String longText) {
		this.LongTexto = longText;
	}

	/**
	 * @return the solicitudInd
	 */
	public SolicitudIndividual getSolicitudInd() {
		return solicitudInd;
	}

	/**
	 * @param solicitudInd
	 *            the solicitudInd to set
	 */
	public void setSolicitudInd(SolicitudIndividual solicitudInd) {
		this.solicitudInd = solicitudInd;
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
		SolIndTexts other = (SolIndTexts) obj;
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
		if (LongTextId == null) {
			if (other.LongTextId != null)
				return false;
		} else if (!LongTextId.equals(other.LongTextId))
			return false;
		return true;
	}

}
