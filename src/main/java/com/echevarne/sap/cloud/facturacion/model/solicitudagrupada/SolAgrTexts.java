package com.echevarne.sap.cloud.facturacion.model.solicitudagrupada;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.echevarne.sap.cloud.facturacion.model.BasicEntity;
import com.echevarne.sap.cloud.facturacion.odata.annotations.SapEntitySet;
import com.fasterxml.jackson.annotation.JsonBackReference;
import org.apache.commons.beanutils.BeanUtils;

import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

/**
 * Class for the Entity {@link SolAgrTexts}.
 *
 * <p>The persistent class. . .T_SolAgrTexts</p>
 * <p>This is a simple description of the class. . .</p>
 *
 * @author Steven Ricardo Mendez Brenes
 * @version 1.0
 * @since 06/24/2019
 *
 */
@Entity
@Table(name = "T_SolAgrTexts")
@SapEntitySet(creatable = false, updatable = false, searchable = true, deletable = false)
public class SolAgrTexts extends BasicEntity implements Serializable{

	/**
	 *
	 */
	private static final long serialVersionUID = 4291217539706315499L;

	@Basic
	private String Language;

	@Basic
	private String LongTextID;

	@Basic
	private String LongTexto;

	@ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JsonBackReference
	@JoinColumn(name = "fk_SolicitudAgrupada", nullable = false)
	private SolicitudesAgrupadas solicitudAgr;

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
		Language = language;
	}

	public String getLongTexto() {
		return LongTexto;
	}

	public void setLongTexto(String longTexto) {
		LongTexto = longTexto;
	}

	public SolicitudesAgrupadas getSolicitudAgr() {
		return solicitudAgr;
	}

	public void setSolicitudAgr(SolicitudesAgrupadas solicitudAgr) {
		this.solicitudAgr = solicitudAgr;
	}

	/**
	 * @return the longTextID
	 */
	public String getLongTextID() {
		return this.LongTextID;
	}

	/**
	 * @param longTextID
	 *            the longTextID to set
	 */
	public void setLongTextID(String longTextID) {
		LongTextID = longTextID;
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
		LongTexto = longText;
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
		SolAgrTexts other = (SolAgrTexts) obj;
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
