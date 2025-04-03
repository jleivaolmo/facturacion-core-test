package com.echevarne.sap.cloud.facturacion.util;

import java.util.Locale;
import java.util.ResourceBundle;

import org.apache.olingo.odata2.api.processor.ODataContext;

public class ODataContextUtil {

	private static ThreadLocal<ODataContext> oDataContext = new ThreadLocal<ODataContext>();
	
	private static ThreadLocal<ODataContext> oDataContextPrivados = new ThreadLocal<ODataContext>();

	public static void setODataContext(ODataContext c) {
		oDataContext.set(c);
	}

	public static ODataContext getODataContext() {
		return oDataContext.get();
	}
	
	public static void setODataContextPrivados(ODataContext c) {
		oDataContextPrivados.set(c);
	}

	public static ODataContext getODataContextPrivados() {
		return oDataContextPrivados.get();
	}
    // Clean up the ODataContext to prevent memory leaks
    public static void cleanupContextPrivados() {
    	oDataContextPrivados.remove();
    }	

	public static ResourceBundle getResourceBundle(String name) {
		
		ResourceBundle i18n = null;
		
		if (oDataContext.get() != null) {
			
			for (Locale locale : oDataContext.get().getAcceptableLanguages()) {
				i18n = ResourceBundle.getBundle(name, locale);
				if (i18n.getLocale().equals(locale))
					break;
			}
			
			return i18n;
			
		} else {
			
			return null;
			
		}
		
	}
}