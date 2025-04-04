package com.echevarne.sap.cloud.facturacion.transformacion.rules;

import java.util.Set;

import com.echevarne.sap.cloud.facturacion.model.transformacion.TransCriteria;
import com.echevarne.sap.cloud.facturacion.model.transformacion.TransRule.ValueType;
import com.echevarne.sap.cloud.facturacion.util.StringUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * Evaluador de criterios de reglas de transformacion.
 *
 * @author Steven Mendez
 */
@Slf4j
public class TransformerCriteriaEvaluator {

	/** The ru. */
	private ReflectionUtil ru;

	/**
	 * Instantiates a new transformer criteria evaluator.
	 *
	 * @param ru the ru
	 */
	public TransformerCriteriaEvaluator(ReflectionUtil ru) {
		this.ru = ru;
	}

	/**
	 *
	 * @param criteriaSet
	 * @return
	 */
	public boolean eval(Set<TransCriteria> criteriaSet) {
		return eval(criteriaSet, ValueType.VALUE);
	}

	/**
	 * Evaluates criteria assigned to a transformation rule
	 *
	 * @param criteriaSet the criteria set
	 * @param valueType
	 * @return true, if successful
	 */
	public boolean eval(Set<TransCriteria> criteriaSet, ValueType valueType) {
		if (criteriaSet == null || criteriaSet.size() == 0 || valueType == ValueType.RULE) {
			return true;
		}

		for (TransCriteria criteria : criteriaSet) {
			try {
				Object value1 = ru.get(criteria.getValue1());

				if( value1 == null ) {
					if(criteria.getOperator().equals("EQNL")) {
						continue;
					} else {
						return false;
					}
				}

				Object value2 = TransformerTypeConverter.convert(criteria.getValue2(), value1.getClass());

				switch( criteria.getOperator()) {
				case "EQ": //Equals
					if (!value1.equals(value2)) {
						return false;
					}
					break;
				case "LT": // Less than
					if (!(TransformerTypeConverter.compareTo(value1.getClass(), value1, value2 ) < 0)) {
						return false;
					}
					break;
				case "GT": // Grater than
					if (!(TransformerTypeConverter.compareTo(value1.getClass(), value1, value2 ) > 0)) {
						return false;
					}
					break;
				case "SW": // Starts With
					if (!(String.valueOf(criteria.getValue2()).startsWith(String.valueOf(value1)))) {
						return false;
					}
					break;
				case "CT": // Contains
					if (!(String.valueOf(criteria.getValue2()).contains(String.valueOf(value1)))) {
						return false;
					}
					break;
				case "NEQ": // Not Equals
					if (value1.equals(value2)) {
						return false;
					}
					break;
				case "NLT": // Not Less than
					if ((TransformerTypeConverter.compareTo(value1.getClass(), value1, value2 ) < 0)) {
						return false;
					}
					break;
				case "NGT": // Not Grater than
					if ((TransformerTypeConverter.compareTo(value1.getClass(), value1, value2 ) > 0)) {
						return false;
					}
					break;
				case "NSW": // Not Starts With
					if (String.valueOf(criteria.getValue2()).startsWith(String.valueOf(value1))) {
						return false;
					}
					break;
				case "NCT": // Not Contains
					if (String.valueOf(criteria.getValue2()).contains(String.valueOf(value1))) {
						return false;
					}
					break;
				case "EQNL":
					if(value1.getClass() == String.class)
						if(!String.valueOf(value1).equals(StringUtils.EMPTY))
							return false;
					break;

				case "NQNL":
					if(value1.getClass() == String.class)
						if(String.valueOf(value1).equals(StringUtils.EMPTY))
							return false;
					break;
				case "GTL": // Grater length than
					if(value1.getClass() == String.class)
						if(String.valueOf(value1).length()<=Integer.parseInt(String.valueOf(value2)))
							return false;
					break;

				default:
				    throw new Exception( "Operator not supported "+ criteria.getOperator());
				}

			} catch (Exception e) {
				log.info("No se ha podido aplicar el criterio {}", criteria.toText(), e);
				return false;
			}
		}

		return true;
	}
}
