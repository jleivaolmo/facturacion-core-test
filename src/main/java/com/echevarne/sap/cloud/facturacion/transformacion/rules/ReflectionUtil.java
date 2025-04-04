package com.echevarne.sap.cloud.facturacion.transformacion.rules;

import java.util.*;

import com.echevarne.sap.cloud.facturacion.reflection.impl.ObjectReflectionUtilImpl;
import com.echevarne.sap.cloud.facturacion.util.StringUtils;
import lombok.extern.slf4j.Slf4j;


/**
 * Utility class to manage transformer context.
 *
 * @author Steven Mendez
 */
@Slf4j
public class ReflectionUtil {

	public static final ObjectReflectionUtilImpl OBJECT_REFLECTION_UTIL = new ObjectReflectionUtilImpl();

	/** The context. */
	private final Map<String, Object> context;

	/**
	 * Instantiates a new reflection util.
	 *
	 * @param context the context
	 */
	public ReflectionUtil(final Map<String, Object> context) {
		super();
		this.context = context;
	}

	/**
	 * Asigna un valor al contexto
	 *
	 * @param key the name
	 * @param value the path
	 */
	public void setContextValue(String key, Object value) {
		this.context.put(key, value);
	}

	/**
	 * Obtiene el valor de un path.
	 *
	 * @param path the path
	 * @return the object
	 * @throws Exception the exception
	 */
	public Object get(String path) throws Exception {
		Object value;

		final Object contextObj = this.getContextObj(path);
		final String pathWoContext = this.removeContextFromPath(path);
		try{
			if(pathWoContext.isEmpty()){
				value = contextObj;
			} else {
				value = OBJECT_REFLECTION_UTIL.get(contextObj, pathWoContext);
			}
		}catch (Exception e){
			throw new Exception("Object not found for path " + path, e);
		}

		return value;
	}

	/**
	 * Asigna un valor a un path.
	 *
	 * @param path the path
	 * @param objValue the obj value
	 * @throws Exception the exception
	 */
	public void setValue(final String path, Object objValue) throws Exception {
		final Object contextObj = getContextObj(path);
		final String pathWoContext = this.removeContextFromPath(path);

		OBJECT_REFLECTION_UTIL.setValue(pathWoContext, contextObj, objValue);

	}

	public Collection<?> createAndSetTargetCollection(String path) throws Exception {
		final Object contextObj = getContextObj(path);
		final String pathWoContext = this.removeContextFromPath(path);

		return OBJECT_REFLECTION_UTIL.createAndSetTargetCollection(contextObj, pathWoContext);
	}

	private String removeContextFromPath(String path) {
		String pathWoContext;

		int idx = path.indexOf(".");
		if (idx > -1) {
			pathWoContext = path.substring(idx + 1);
		} else {
			pathWoContext = StringUtils.EMPTY;
		}

		return pathWoContext;
	}

	private Object getContextObj(String path){
		Object contextObject;

		int idx = path.indexOf(".");
		if (idx > -1) {
			String name = path.substring(0, idx);
			contextObject = context.get(name);
		} else {
			contextObject = this.context.get(path);
		}

		return contextObject;
	}
}
