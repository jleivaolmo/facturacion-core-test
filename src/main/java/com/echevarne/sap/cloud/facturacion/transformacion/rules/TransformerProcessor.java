package com.echevarne.sap.cloud.facturacion.transformacion.rules;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

import com.echevarne.sap.cloud.facturacion.model.transformacion.Trans;
import com.echevarne.sap.cloud.facturacion.model.transformacion.TransRule;

import lombok.extern.slf4j.Slf4j;

import static com.echevarne.sap.cloud.facturacion.reflection.ObjectReflectionUtil.OBJECT_REFLECTION_UTIL;

/**
 * Procesador de reglas de transformacion.
 */
@Slf4j
public class TransformerProcessor {

	private final TransformerFunctions transformerFunctions;
	private final TransformerCallProcessor transformerCallProcessor;

	/** The context. */
	private final TransformerContext context;

	/** The ru. */
	private final ReflectionUtil ru;

	/** The trans map. */
	private Map<String, Trans> transMap;

	/** The evaluator. */
	private final TransformerCriteriaEvaluator evaluator;

	/** The custom functions */
	private TransformerFunctions customFunctions;

	/**
	 * Instantiates a new transformer processor.
	 */
	public TransformerProcessor(
			final TransformerFunctions transformerFunctions,
			final TransformerFunctions customFunctions,
			final TransformerCallProcessor transformerCallProcessor
	) {
		this.transformerFunctions = transformerFunctions;
		this.transformerCallProcessor = transformerCallProcessor;
		this.customFunctions = customFunctions;
		this.context = new TransformerContext();
		this.ru = new ReflectionUtil(this.context.getContextMap());
		this.evaluator = new TransformerCriteriaEvaluator( ru );
	}

	/**
	 * Adds object the to context.
	 */
	public void addToContext(String name, Object obj) {
		this.context.put(name, obj);
	}

	/**
	 * Process a transformation.
	 */
	public void process(String transformerName) throws Exception {
		Trans t = this.transMap.get(transformerName);
		if (t != null) {
			Object source = this.context.get(t.getInAlias());
			if (source == null)
				throw new Exception("Instance with name " + t.getInAlias() + " was not found in context");
			Object target = Class.forName(t.getOutClass()).newInstance();
			this.context.put(t.getOutAlias(), target);
			applyRules(target, t.getRules());
		} else {
			throw new Exception("Process name " + transformerName + " is not defined");
		}
	}

	/**
	 * David Bolet: Modificacion de campos productivos
	 * Process a transformation.
	 */
	public void processModification(String transformerName, Object target) throws Exception {
		Trans t = this.transMap.get(transformerName);
		if (t != null) {
			Object source = this.context.get(t.getInAlias());
			if (source == null)
				throw new Exception("Instance with name " + t.getInAlias() + " was not found in context");
			this.context.put(t.getOutAlias(), target);
			applyRules(target, t.getRules());
		} else {
			throw new Exception("Process name " + transformerName + " is not defined");
		}
	}

	/**
	 * Apply rules.
	 */
	private void applyRules(Object target, List<TransRule> rules) throws Exception {
		if( rules == null) throw new Exception("Rule set is null for target " + target.getClass().getCanonicalName());
		for (TransRule t : rules) {
			try {
				doAction(t);
			} catch (Exception e) {
				if(log.isDebugEnabled()){
					log.debug("Se ha producido una excepción al ejecutar la regla {} (doAction)", t.getValue(), e);
				} else {
					log.info("Se ha producido una excepción al ejecutar la regla {} (doAction)", t.getValue());
				}
			}
		}
	}

	/**
	 * Do rule action.
	 */
	private void doAction(TransRule rule) throws Exception {
		if( !evaluator.eval(rule.getCriteria(), rule.getValueType())) return;
		switch (rule.getValueType()) {
		case VALUE:
			applyValue(rule);
			break;
		case FIELD:
			applyFunction(rule);
			break;
		case RULE:
			applyRule(rule);
			break;
		case CALL:
			applyCall(rule);
			break;
		case JEXL:
			applyJexl(rule);
			break;
		case VAR:
			applyVar(rule);
			break;
		default:
			throw new Exception( "Rule type not supported" );
		}
	}

	/**
	 * Set a new context variable.
	 */
	private void applyVar(TransRule rule) {
		try {
			final Object value = ru.get(rule.getValue());
			ru.setContextValue(rule.getName(), value);
		} catch (Exception e) {
			if (log.isDebugEnabled()){
				log.debug("El valor de la regla {} no ha podido determinarse (applyVar)", rule.getValue(), e);
			} else {
				log.info("El valor de la regla {} no ha podido determinarse (applyVar)", rule.getValue());
			}
		}
	}

	/**
	 * Apply call
	 */
	private void applyCall(TransRule rule) {
		try {
			transformerCallProcessor.apply(rule, ru, transformerFunctions, customFunctions);
		} catch (Exception e) {
			if(log.isDebugEnabled()){
				log.debug("La llamada a la función {} no ha podido determinarse (applyCall)", rule.getValue(), e);
			}else{
				log.info("La llamada a la función {} no ha podido determinarse (applyCall)", rule.getValue());
			}

		}
	}

	/**
	 * Apply jexl.
	 *
	 * @param rule the rule
	 */
	private void applyJexl(TransRule rule) {
		try {
			TransformerJexlProcessor.apply(rule, context.getContextMap(), ru);
		} catch (Exception e) {
			if(log.isDebugEnabled()){
				log.debug("La expresión {} no ha podido determinarse (applyJexl)", rule.getValue(), e);
			}else{
				log.info("La expresión {} no ha podido determinarse (applyJexl)", rule.getValue());
			}

		}
	}

	/**
	 * Apply value.
	 *
	 * @param rule the rule
	 */
	private void applyValue(TransRule rule) {
		try {
			ru.setValue(rule.getName(), rule.getValue());
		} catch (Exception e) {
			if(log.isDebugEnabled()){
				log.debug("El valor de la regla {} no ha podido determinarse (applyValue)", rule.getValue(), e);
			}else{
				log.info("El valor de la regla {} no ha podido determinarse (applyValue)", rule.getValue());
			}

		}
	}

	/**
	 * Apply function.
	 *
	 * @param rule the rule
	 */
	private void applyFunction(TransRule rule) {
		try {
			final Object obj = ru.get(rule.getValue());
			ru.setValue(rule.getName(), obj);
		} catch (Exception e) {
			if(log.isDebugEnabled()){
				log.debug("El valor de la regla {} no ha podido determinarse (applyFunction)", rule.getValue(), e);
			}else{
				log.info("El valor de la regla {} no ha podido determinarse (applyFunction)", rule.getValue());
			}
		}
	}

	/**
	 * Genera una Collection al que le agrega cada una de las transformaciones de los elementos del Set de entrada.
	 * El valor de la rule es de la forma : transName(instanceName)
	 * donde
	 * transName es el nombre de la transformacion que se va a usar para procesar los elementos del Set de entrada
	 * instanceName es el nombre del atributo que contiene el Set de entrada
	 *
	 * @param rule the rule
	 */
	private void applyRule(TransRule rule) {
		try {
			Collection targetCollection = ru.createAndSetTargetCollection(rule.getName());
			String ruleRef = rule.getValue();
			int idx = ruleRef.indexOf("(");
			if (idx > -1) {
				String transName = ruleRef.substring(0, idx);
				String instanceName = ruleRef.substring(idx + 1).replace(")", "").trim();
				Trans trans = this.transMap.get(transName);
				// Iterate all source set
				Set set = getInputSet(instanceName);
				for (Object obj : set) {
					// put set item into context
					this.context.put(trans.getInAlias(), obj);
					// check active item
					if( isRowInactive(obj) )
						continue;
					// apply transformation rule
					if( evaluator.eval(rule.getCriteria()))
						process(trans.getName());
					else
						continue;
					// get outcome from context
					Object outObj = ru.get(trans.getOutAlias());
					// add outcome to target set
					targetCollection.add(outObj);
				}
			}
		} catch (Exception e) {
			if(log.isDebugEnabled()){
				log.debug("La regla {} no ha podido determinarse (applyRule)", rule, e);
			}else{
				log.info("La regla {} no ha podido determinarse (applyRule)", rule);
			}
		}
	}

	private boolean isRowInactive(Object item) {
		boolean isInactive = false;

		try {
			isInactive = (boolean) OBJECT_REFLECTION_UTIL.getFieldValueFromObject(item, "inactive");
		} catch (IllegalArgumentException | IllegalAccessException | InvocationTargetException | NoSuchFieldException e) {
			log.error("Ops!", e);
		}

		return isInactive;
	}

	// A Set of 1 element is returned if the input read from the context is not a Set
	private Set getInputSet(String instanceName) throws Exception {
		Object input = ru.get(instanceName);
		if(input instanceof Set) {
			return (Set) input;
		} else {
			Set set = new HashSet();
			set.add(input);
			return set;
		}
	}

	/**
	 * Gets the from context.
	 *
	 * @param name the name
	 * @return the from context
	 */
	public Object getFromContext(String name) {
		return context.get(name);
	}

	/**
	 * Sets the rules.
	 *
	 * @param transList the new rules
	 */
	public void setTrans(List<Trans> transList) {
		this.transMap = new HashMap<>();
		for (Trans t : transList) {
			this.transMap.put(t.getName(), t);
		}
	}
}
