package com.echevarne.sap.cloud.facturacion.util;

import java.sql.Timestamp;
import java.util.Date;

public class FechasBusquedasUtil {
	
	/**
	 * 
	 * @return
	 */
	public static Timestamp getContratoCapitativoDate() {
		return new Timestamp(System.currentTimeMillis());
	}

	public static Date getInterlocutorComisionistaDate() {
		Timestamp ts = new Timestamp(System.currentTimeMillis());
		return new Date(ts.getTime());
	}	
	
}
