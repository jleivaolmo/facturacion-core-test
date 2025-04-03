package com.echevarne.sap.cloud.facturacion.odata.client;

import org.apache.olingo.odata2.jpa.processor.api.OnJPAWriteContent;
import org.apache.olingo.odata2.jpa.processor.api.exception.ODataJPARuntimeException;


public class OnDBWriteContent implements OnJPAWriteContent {

	@Override
	public java.sql.Blob getJPABlob(byte[] arg0) throws ODataJPARuntimeException {
		return null;
	}

	@Override
	public java.sql.Clob getJPAClob(char[] arg0) throws ODataJPARuntimeException {
		return null;
	} 

}
