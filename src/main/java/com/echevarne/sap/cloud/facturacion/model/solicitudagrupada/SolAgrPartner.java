package com.echevarne.sap.cloud.facturacion.model.solicitudagrupada;

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
 * Class for the Entity {@link SolAgrPartner}.
 *
 * <p>The persistent class. . .T_SolAgrPartner</p>
 * <p>This is a simple description of the class. . .</p>
 *
 * @author Steven Ricardo Mendez Brenes
 * @version 1.0
 * @since 06/24/2019
 *
 */
@Entity
@Table(name = "T_SolAgrPartner")
@SapEntitySet(creatable = false, updatable = false, searchable = true, deletable = false)
public class SolAgrPartner extends BasicEntity implements Serializable{

	/**
	 *
	 */
	private static final long serialVersionUID = -2631814200871114188L;

	@Basic
	private String partnerFunction;
	@Basic
	private String customer;
	@Basic
	private String supplier;
	@Basic
	private String personnel;
	@Basic
	private String contactPerson;

	@ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JsonBackReference
	@JoinColumn(name = "fk_SolicitudAgrupada", nullable = false)
	private SolicitudesAgrupadas solicitudAgr;

	/**
	 * @return the partnerFunction
	 */
	public String getPartnerFunction() {
		return this.partnerFunction;
	}

	/**
	 * @param partnerFunction
	 *            the partnerFunction to set
	 */
	public void setPartnerFunction(String partnerFunction) {
		this.partnerFunction = partnerFunction;
	}

	/**
	 * @return the customer
	 */
	public String getCustomer() {
		return this.customer;
	}

	/**
	 * @param customer
	 *            the customer to set
	 */
	public void setCustomer(String customer) {
		this.customer = customer;
	}

	/**
	 * @return the supplier
	 */
	public String getSupplier() {
		return this.supplier;
	}

	/**
	 * @param supplier
	 *            the supplier to set
	 */
	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}

	/**
	 * @return the personnel
	 */
	public String getPersonnel() {
		return this.personnel;
	}

	/**
	 * @param personnel
	 *            the personnel to set
	 */
	public void setPersonnel(String personnel) {
		this.personnel = personnel;
	}

	/**
	 * @return the contactPerson
	 */
	public String getContactPerson() {
		return this.contactPerson;
	}

	/**
	 * @param contactPerson
	 *            the contactPerson to set
	 */
	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}

	/**
	 * @return the solicitudAgr
	 */
	public SolicitudesAgrupadas getSolicitudAgr() {
		return solicitudAgr;
	}

	/**
	 * @param solicitudAgr
	 *            the solicitudAgr to set
	 */
	public void setSolicitudAgr(SolicitudesAgrupadas solicitudAgr) {
		this.solicitudAgr = solicitudAgr;
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
		SolAgrPartner other = (SolAgrPartner) obj;
		if (contactPerson == null) {
			if (other.contactPerson != null)
				return false;
		} else if (!contactPerson.equals(other.contactPerson))
			return false;
		if (customer == null) {
			if (other.customer != null)
				return false;
		} else if (!customer.equals(other.customer))
			return false;
		if (partnerFunction == null) {
			if (other.partnerFunction != null)
				return false;
		} else if (!partnerFunction.equals(other.partnerFunction))
			return false;
		if (personnel == null) {
			if (other.personnel != null)
				return false;
		} else if (!personnel.equals(other.personnel))
			return false;
		if (supplier == null) {
			if (other.supplier != null)
				return false;
		} else if (!supplier.equals(other.supplier))
			return false;
		return true;
	}

}
