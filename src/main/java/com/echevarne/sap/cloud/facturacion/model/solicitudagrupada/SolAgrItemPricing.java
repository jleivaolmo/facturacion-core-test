package com.echevarne.sap.cloud.facturacion.model.solicitudagrupada;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
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
import org.apache.commons.beanutils.BeanUtils;

import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

/**
 * Class for the Entity {@link SolAgrItemPricing}.
 *
 * <p>The persistent class. . .T_SolAgrItemPricing</p>
 * <p>This is a simple description of the class. . .</p>
 *
 * @author Steven Ricardo Mendez Brenes
 * @version 1.0
 * @since 06/24/2019
 *
 */
@Entity
@Table(name = "T_SolAgrItemPricing")
@SapEntitySet(creatable = false, updatable = false, searchable = true, deletable = false)
public class SolAgrItemPricing extends BasicEntity implements Serializable{

	/**
	 *
	 */
	private static final long serialVersionUID = 7415815689794057012L;

	@Basic
	private String pricingProcedureStep;

	@Basic
	private String pricingProcedureCounter;

	@Basic
	private String conditionType;

	@Column(precision = 16, scale = 3)
	private BigDecimal conditionAmount;

	@Column(precision = 16, scale = 3)
	private BigDecimal conditionRateValue;

	@Basic
	private String conditionCurrency;

	@Column(precision = 5, scale = 0)
	private BigDecimal conditionQuantity;

	@Basic
	private String conditionQuantityUnit;

	@Basic
	private String transactionCurrency;

	@ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JsonBackReference
	@JoinColumn(name = "fk_SolicitudAgrupadaItem", nullable = false)
	private SolAgrItems posicion;

	public String getPricingProcedureStep() {
		return pricingProcedureStep;
	}

	public void setPricingProcedureStep(String pricingProcedureStep) {
		this.pricingProcedureStep = pricingProcedureStep;
	}

	public String getPricingProcedureCounter() {
		return pricingProcedureCounter;
	}

	public void setPricingProcedureCounter(String pricingProcedureCounter) {
		this.pricingProcedureCounter = pricingProcedureCounter;
	}

	public String getConditionType() {
		return conditionType;
	}

	public void setConditionType(String conditionType) {
		this.conditionType = conditionType;
	}

	public BigDecimal getConditionRateValue() {
		return conditionRateValue;
	}

	public void setConditionRateValue(BigDecimal conditionRateValue) {
		this.conditionRateValue = conditionRateValue;
	}

	public void addConditionRateValue(BigDecimal condRateValue) {
		setConditionRateValue(this.conditionRateValue.add(condRateValue));
	}

	public String getConditionCurrency() {
		return conditionCurrency;
	}

	public void setConditionCurrency(String conditionCurrency) {
		this.conditionCurrency = conditionCurrency;
	}

	public BigDecimal getConditionQuantity() {
		return conditionQuantity;
	}

	public void addConditionQuantity(BigDecimal condQuantity) {
		setConditionQuantity(this.conditionQuantity.add(condQuantity));
	}

	public void setConditionQuantity(BigDecimal conditionQuantity) {
		this.conditionQuantity = conditionQuantity;
	}

	public String getConditionQuantityUnit() {
		return conditionQuantityUnit;
	}

	public void setConditionQuantityUnit(String conditionQuantityUnit) {
		this.conditionQuantityUnit = conditionQuantityUnit;
	}

	public String getTransactionCurrency() {
		return transactionCurrency;
	}

	public void setTransactionCurrency(String transactionCurrency) {
		this.transactionCurrency = transactionCurrency;
	}

	public BigDecimal getConditionAmount() {
		return conditionAmount;
	}

	public void setConditionAmount(BigDecimal conditionAmount) {
		this.conditionAmount = conditionAmount;
	}

	public void addConditionAmount(BigDecimal condAmount) {
		setConditionAmount(this.conditionAmount.add(condAmount));
	}

	/**
	 * @return the posicion
	 */
	public SolAgrItems getPosicion() {
		return posicion;
	}

	/**
	 * @param posicion
	 *            the posicion to set
	 */
	public void setPosicion(SolAgrItems posicion) {
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
		SolAgrItemPricing other = (SolAgrItemPricing) obj;
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
		//Se ha de ignorar el conditionAmount en el equals.
		/*if (conditionAmount == null) {
			if (other.conditionAmount != null)
				return false;
		} else if (!conditionAmount.equals(other.conditionAmount))
			return false;*/
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
