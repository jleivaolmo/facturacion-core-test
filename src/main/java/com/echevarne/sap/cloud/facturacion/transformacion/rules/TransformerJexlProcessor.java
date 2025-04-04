package com.echevarne.sap.cloud.facturacion.transformacion.rules;

import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.jexl3.JexlBuilder;
import org.apache.commons.jexl3.JexlContext;
import org.apache.commons.jexl3.JexlEngine;
import org.apache.commons.jexl3.JexlExpression;
import org.apache.commons.jexl3.MapContext;

import com.echevarne.sap.cloud.facturacion.model.transformacion.TransRule;


/**
 * Procesador de reglas JEXL.
 *
 * @author Steven Mendez
 */
public class TransformerJexlProcessor {

	/** The Constant jexl. */
	private static final JexlEngine jexl = new JexlBuilder().cache(512).strict(true).silent(false).create();
	
	/**
	 * Evaluates a JEXL expression.
	 *
	 * @param rule the rule
	 * @param transformerContext the transformer context
	 * @param ru the ru
	 * @throws Exception the exception
	 */
	public static void apply(TransRule rule, Map<String,Object> transformerContext, ReflectionUtil ru) throws Exception {
		
		JexlExpression expression = jexl.createExpression(rule.getValue());

		// populate the context
		JexlContext context = new MapContext();
		for( Entry<String,Object> entry : transformerContext.entrySet()) {
			context.set(entry.getKey(), entry.getValue());
		}

		// work it out
		Object result = expression.evaluate(context);
		
		ru.setValue( rule.getName(), result );
		
	}

}
