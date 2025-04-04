package com.echevarne.sap.cloud.facturacion.model.solicitudindividual;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Basic;
import javax.persistence.Column;
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
 * Class for the Entity {@link SolIndItemPricing}.
 *
 * <p>
 * The persistent class. . .T_SolIndItemPricing
 * </p>
 * <p>
 * This is a simple description of the class. . .
 * </p>
 *
 * @author Steven Ricardo Mendez Brenes
 * @version 1.0
 * @since 06/24/2019
 *
 */
@Entity
@Table(name = "T_SolIndItemPricing")
@SapEntitySet(creatable = false, updatable = false, searchable = true, deletable = false)
public class SolIndItemPricing extends BasicEntity implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 8236980317332482574L;

	@Basic
	private String pricingProcedureStep;
	@Basic
	private String pricingProcedureCounter;
	@Basic
	private String conditionType;
	@Column(precision = 16, scale = 3)
	private BigDecimal conditionRateValue;
	@Basic
	private String conditionCurrency;
	@Column(precision = 16, scale = 3)
	private BigDecimal conditionQuantity;
	@Basic
	private String conditionQuantityUnit;
	@Basic
	private String transactionCurrency;
	@Column(precision = 16, scale = 3)
	private BigDecimal conditionAmount;

	@ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name = "fk_SolicitudIndividualItem", nullable = false)
	@JsonBackReference
	private SolIndItems posicion;

	/**
	 * @return the pricingProcedureStep
	 */
	public String getPricingProcedureStep() {
		return this.pricingProcedureStep;
	}

	/**
	 * @param pricingProcedureStep
	 *            the pricingProcedureStep to set
	 */
	public void setPricingProcedureStep(String pricingProcedureStep) {
		this.pricingProcedureStep = pricingProcedureStep;
	}

	/**
	 * @return the pricingProcedureCounter
	 */
	public String getPricingProcedureCounter() {
		return this.pricingProcedureCounter;
	}

	/**
	 * @param pricingProcedureCounter
	 *            the pricingProcedureCounter to set
	 */
	public void setPricingProcedureCounter(String pricingProcedureCounter) {
		this.pricingProcedureCounter = pricingProcedureCounter;
	}

	/**
	 * @return the conditionType
	 */
	public String getConditionType() {
		return this.conditionType;
	}

	/**
	 * @param conditionType
	 *            the conditionType to set
	 */
	public void setConditionType(String conditionType) {
		this.conditionType = conditionType;
	}

	/**
	 * @return the conditionRateValue
	 */
	public BigDecimal getConditionRateValue() {
		return this.conditionRateValue;
	}

	/**
	 * @param conditionRateValue
	 *            the conditionRateValue to set
	 */
	public void setConditionRateValue(BigDecimal conditionRateValue) {
		this.conditionRateValue = conditionRateValue;
	}

	/**
	 * @return the conditionCurrency
	 */
	public String getConditionCurrency() {
		return this.conditionCurrency;
	}

	/**
	 * @param conditionCurrency
	 *            the conditionCurrency to set
	 */
	public void setConditionCurrency(String conditionCurrency) {
		this.conditionCurrency = conditionCurrency;
	}

	/**
	 * @return the conditionQuantity
	 */
	public BigDecimal getConditionQuantity() {
		return this.conditionQuantity;
	}

	/**
	 * @param conditionQuantity
	 *            the conditionQuantity to set
	 */
	public void setConditionQuantity(BigDecimal conditionQuantity) {
		this.conditionQuantity = conditionQuantity;
	}

	/**
	 * @return the conditionQuantityUnit
	 */
	public String getConditionQuantityUnit() {
		return this.conditionQuantityUnit;
	}

	/**
	 * @param conditionQuantityUnit
	 *            the conditionQuantityUnit to set
	 */
	public void setConditionQuantityUnit(String conditionQuantityUnit) {
		this.conditionQuantityUnit = conditionQuantityUnit;
	}

	/**
	 * @return the transactionCurrency
	 */
	public String getTransactionCurrency() {
		return this.transactionCurrency;
	}

	/**
	 * @param transactionCurrency
	 *            the transactionCurrency to set
	 */
	public void setTransactionCurrency(String transactionCurrency) {
		this.transactionCurrency = transactionCurrency;
	}

	/**
	 * @return the conditionAmount
	 */
	public BigDecimal getConditionAmount() {
		return conditionAmount;
	}

	/**
	 * @param conditionAmount
	 *            the conditionAmount to set
	 */
	public void setConditionAmount(BigDecimal conditionAmount) {
		this.conditionAmount = conditionAmount;
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
		SolIndItemPricing other = (SolIndItemPricing) obj;
		if (conditionAmount == null) {
			if (other.conditionAmount != null)
				return false;
		} else if (!conditionAmount.equals(other.conditionAmount))
			return false;
		if (conditionCurrency == null) {
			if (other.conditionCurrency != null)
				return false;
		} else if (!conditionCurrency.equals(other.conditionCurrency))
			return false;
		if (conditionQuantity == null) {
			if (other.conditionQuantity != null)
				return false;
		} else if (!conditionQuantity.equals(other.conditionQuantity))
			return false;
		if (conditionQuantityUnit == null) {
			if (other.conditionQuantityUnit != null)
				return false;
		} else if (!conditionQuantityUnit.equals(other.conditionQuantityUnit))
			return false;
		if (conditionRateValue == null) {
			if (other.conditionRateValue != null)
				return false;
		} else if (!conditionRateValue.equals(other.conditionRateValue))
			return false;
		if (conditionType == null) {
			if (other.conditionType != null)
				return false;
		} else if (!conditionType.equals(other.conditionType))
			return false;
		if (pricingProcedureCounter == null) {
			if (other.pricingProcedureCounter != null)
				return false;
		} else if (!pricingProcedureCounter.equals(other.pricingProcedureCounter))
			return false;
		if (pricingProcedureStep == null) {
			if (other.pricingProcedureStep != null)
				return false;
		} else if (!pricingProcedureStep.equals(other.pricingProcedureStep))
			return false;
		if (transactionCurrency == null) {
			if (other.transactionCurrency != null)
				return false;
		} else if (!transactionCurrency.equals(other.transactionCurrency))
			return false;
		return true;
	}

}
