package com.echevarne.sap.cloud.facturacion.services;

import com.echevarne.sap.cloud.facturacion.model.Log;

import org.apache.http.util.Args;

/**
 * Interface for the service{@link LogService}.
 * 
 * <p>This is a interface for Log Services. . .</p>
 *
 * @author Steven Ricardo Mendez Brenes
 * @version 1.0
 * @since 06/24/2019
 * 
 */
public interface LogService extends CrudService<Log, Long> {
	
	public void log(String level, String module, String className, String message, Throwable throwable);
	public void log(String level, String module, String className, String message, Throwable throwable, String codigoPeticion);
	public String format(String message, Object ... arguments);
}
