package com.echevarne.sap.cloud.facturacion.model.transformacion;

import javax.persistence.*;

import com.echevarne.sap.cloud.facturacion.model.BasicEntity;
import com.echevarne.sap.cloud.facturacion.odata.annotations.SapEntitySet;
import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;


/**
 * The Class TransCriteria.
 */
@Entity
@Table(name = "T_TransformacionCriteria")
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SapEntitySet(creatable = true, updatable = true, searchable = true, deletable = false)
public class TransCriteria  extends BasicEntity {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -3184885356212016685L;

	/** The value 1. */
	@Basic
	private String value1;

	/** The operator. */
	@Basic
	private String operator;

	/** The value 2. */
	@Basic
	private String value2;

	/** The rule. */
	@ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name = "fk_TransRule", nullable = false)
	@JsonBackReference
	private TransRule rule;

	/**
	 * Gets the value 1.
	 *
	 * @return the value 1
	 */
	public String getValue1() {
		return value1;
	}

	/**
	 * Sets the value 1.
	 *
	 * @param value1 the new value 1
	 */
	public void setValue1(String value1) {
		this.value1 = value1;
	}

	/**
	 * Gets the operator.
	 *
	 * @return the operator
	 */
	public String getOperator() {
		return operator;
	}

	/**
	 * Sets the operator.
	 *
	 * @param operator the new operator
	 */
	public void setOperator(String operator) {
		this.operator = operator;
	}

	/**
	 * Gets the value 2.
	 *
	 * @return the value 2
	 */
	public String getValue2() {
		return value2;
	}

	/**
	 * Sets the value 2.
	 *
	 * @param value2 the new value 2
	 */
	public void setValue2(String value2) {
		this.value2 = value2;
	}

	/**
	 * Gets the rule.
	 *
	 * @return the rule
	 */
	public TransRule getRule() {
		return rule;
	}

	/**
	 * Sets the rule.
	 *
	 * @param rule the new rule
	 */
	public void setRule(TransRule rule) {
		this.rule = rule;
	}

	/**
	 * Gets the serialversionuid.
	 *
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	/**
	 * On equals.
	 *
	 * @param obj the obj
	 * @return true, if successful
	 */
	@Override
	public boolean onEquals(Object obj) {
		if (this == obj)
			return true;
		if (getClass() != obj.getClass())
			return false;
		TransCriteria other = (TransCriteria) obj;
		if (operator == null) {
			if (other.operator != null)
				return false;
		} else if (!operator.equals(other.operator))
			return false;
		if (value1 == null) {
			if (other.value1 != null)
				return false;
		} else if (!value1.equals(other.value1))
			return false;
		if (value2 == null) {
			if (other.value2 != null)
				return false;
		} else if (!value2.equals(other.value2))
			return false;
		return true;
	}

	/**
	 * To text.
	 *
	 * @return the string
	 */
	public String toText() {
		return "TransCriteria [value1=" + value1 + ", operator=" + operator + ", value2=" + value2 + "]";
	}

}
