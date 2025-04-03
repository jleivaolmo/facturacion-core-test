package com.echevarne.sap.cloud.facturacion.util;

import java.util.Base64;

/**
 * Class for the Component {@link BasicAuth}.
 * 
 * <p>Utility Class with an encoder {@link Base64} used for encode header http Basic Authentication </p>
 *  
 * @author Hernan Girardi
 * @since 04/02/2020
 */
public final class BasicAuth {
	
	//hide public constructor
	private BasicAuth() {
	    throw new IllegalStateException(this.getClass().getName() + "is a Utility class, should not be instantiated");
	  }


	public static String encode( String user, String password ) {
		
		String text = user+":"+password;
		
		String encoded = new String(Base64.getEncoder().encode(text.getBytes()));
		
		return "Basic "+encoded;
	}
}
