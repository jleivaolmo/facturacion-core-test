package com.echevarne.sap.cloud.facturacion.transformacion.rules;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.echevarne.sap.cloud.facturacion.model.transformacion.TransRule;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


/**
 * Ejecuta regla de transformacion CALL.
 */
@Slf4j
@Component
public class TransformerCallProcessor {

	/**
	 * Calls a referenced java method.
	 */
	public void apply(
			TransRule rule,
			ReflectionUtil ru,
			TransformerFunctions transformerFunctions
	)
			throws Exception {
		apply(rule, ru, transformerFunctions, null);
	}

	public  <T extends TransformerFunctions> void apply(
			TransRule rule,
			ReflectionUtil ru,
			TransformerFunctions transformerFunctions,
			T customFunctions
	) throws Exception {
		try {

			String methodName = rule.getValue().trim();

			Method m;
			Object o;
			try{
				m = TransformerFunctions.class.getDeclaredMethod(methodName, ReflectionUtil.class);
				o = transformerFunctions;
			}catch (NoSuchMethodException ex) {
				if(customFunctions != null){
					m = customFunctions.getClass().getDeclaredMethod(methodName, ReflectionUtil.class);
					o = customFunctions;
				}else throw ex;
			}

			Object result = m.invoke(o, ru);

			if (rule.getName() != null && rule.getName().trim().length() > 0) {
				ru.setValue(rule.getName(), result);
			}

		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			if (log.isDebugEnabled()) {
				log.debug("No se pudo aplicar la regla de transformación (apply)", e);
			}
		}
	}

}
