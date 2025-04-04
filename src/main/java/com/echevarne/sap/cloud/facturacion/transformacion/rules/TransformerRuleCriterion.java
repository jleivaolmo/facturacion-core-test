package com.echevarne.sap.cloud.facturacion.transformacion.rules;


/**
 * Criterio de regla de transformacion.
 *
 * @author Steven Mendez
 */
public class TransformerRuleCriterion {

	/** The field name. */
	private String fieldName;
	
	/** The operator. */
	private TransformerOperator operator;
	
	/** The value 1. */
	private Object value1;
	
	/** The value 2. */
	private Object value2;
	
	/** The order. */
	private int order;

	/**
	 * Gets the operator.
	 *
	 * @return the operator
	 */
	public TransformerOperator getOperator() {
		return operator;
	}

	/**
	 * Sets the operator.
	 *
	 * @param operator the new operator
	 */
	public void setOperator(TransformerOperator operator) {
		this.operator = operator;
	}

	/**
	 * Gets the value 1.
	 *
	 * @return the value 1
	 */
	public Object getValue1() {
		return value1;
	}

	/**
	 * Sets the value 1.
	 *
	 * @param value1 the new value 1
	 */
	public void setValue1(Object value1) {
		this.value1 = value1;
	}

	/**
	 * Gets the value 2.
	 *
	 * @return the value 2
	 */
	public Object getValue2() {
		return value2;
	}

	/**
	 * Sets the value 2.
	 *
	 * @param value2 the new value 2
	 */
	public void setValue2(Object value2) {
		this.value2 = value2;
	}

	/**
	 * Gets the field name.
	 *
	 * @return the field name
	 */
	public String getFieldName() {
		return fieldName;
	}

	/**
	 * Sets the field name.
	 *
	 * @param fieldName the new field name
	 */
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	/**
	 * Gets the order.
	 *
	 * @return the order
	 */
	public int getOrder() {
		return order;
	}

	/**
	 * Sets the order.
	 *
	 * @param order the new order
	 */
	public void setOrder(int order) {
		this.order = order;
	}

	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return "TransformerRuleCriterion [fieldName=" + fieldName + ", operator=" + operator + ", value1=" + value1
				+ ", value2=" + value2 + ", order=" + order + "]";
	}

}
