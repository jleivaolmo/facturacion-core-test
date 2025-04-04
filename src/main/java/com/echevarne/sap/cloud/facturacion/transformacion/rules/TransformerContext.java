package com.echevarne.sap.cloud.facturacion.transformacion.rules;

import java.util.HashMap;
import java.util.Map;


/**
 * Contexto de reglas de transformacion.
 *
 * @author Steven Mendez
 */
public class TransformerContext {
	
	
	/** The context map. */
	private Map<String,Object> contextMap = new HashMap<>();
	
	
	/**
	 * Put.
	 *
	 * @param name the name
	 * @param obj the obj
	 */
	public void put( String name, Object obj) {
		this.contextMap.put(name,obj);
	}

	/**
	 * Gets the.
	 *
	 * @param name the name
	 * @return the object
	 */
	public Object get( String name) {
		return this.contextMap.get(name);
	}

	/**
	 * Gets the context map.
	 *
	 * @return the context map
	 */
	public Map<String, Object> getContextMap() {
		return contextMap;
	}
	
	
}
