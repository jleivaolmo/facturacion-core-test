package com.echevarne.sap.cloud.facturacion.model.transformacion;

import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.echevarne.sap.cloud.facturacion.model.BasicEntity;
import com.echevarne.sap.cloud.facturacion.odata.annotations.SapEntitySet;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.*;

/**
 * The Class TransRule.
 */
@Entity
@Table(name = "T_TransformacionRule")
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SapEntitySet(creatable = true, updatable = true, searchable = true, deletable = false)
public class TransRule  extends BasicEntity {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -3587440312730174951L;

	/**
	 * The Enum ValueType.
	 */
	public enum ValueType {
		/** The value. */ VALUE,
		/** The field. */ FIELD,
		/** The rule. */ RULE,
		/** The call. */ CALL,
		/** The jexl. */ JEXL,
		/** The var. */ VAR };

	/** The name. */
	@Basic
	private String name;

	/** The criteria. */
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "rule", fetch = FetchType.EAGER)
	@BatchSize(size = 100)
	@JsonManagedReference
	@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	private Set<TransCriteria> criteria;

	/** The value type. */
	@Basic
	@Enumerated(EnumType.STRING)
	private ValueType valueType;

	/** The value. */
	@Basic
	private String value;

	/** The exce order. */
	@Column(name="exec_order")
	private int exceOrder;

	/** The trans. */
	@ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name="fk_trans", nullable = false)
	@JsonBackReference
	private Trans trans;

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the criteria.
	 *
	 * @return the criteria
	 */
	public Set<TransCriteria> getCriteria() {
		return criteria;
	}

	/**
	 * Sets the criteria.
	 *
	 * @param criteria the new criteria
	 */
	public void setCriteria(Set<TransCriteria> criteria) {
		this.criteria = criteria;
	}

	/**
	 * Gets the value type.
	 *
	 * @return the value type
	 */
	public ValueType getValueType() {
		return valueType;
	}

	/**
	 * Sets the value type.
	 *
	 * @param valueType the new value type
	 */
	public void setValueType(ValueType valueType) {
		this.valueType = valueType;
	}

	/**
	 * Gets the value.
	 *
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * Sets the value.
	 *
	 * @param value the new value
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * Gets the trans.
	 *
	 * @return the trans
	 */
	public Trans getTrans() {
		return trans;
	}

	/**
	 * Sets the trans.
	 *
	 * @param trans the new trans
	 */
	public void setTrans(Trans trans) {
		this.trans = trans;
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
		TransRule other = (TransRule) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		if (valueType != other.valueType)
			return false;
		return true;
	}

	/**
	 * Gets the exce order.
	 *
	 * @return the exce order
	 */
	public int getExceOrder() {
		return exceOrder;
	}

	/**
	 * Sets the exce order.
	 *
	 * @param exceOrder the new exce order
	 */
	public void setExceOrder(int exceOrder) {
		this.exceOrder = exceOrder;
	}

}
