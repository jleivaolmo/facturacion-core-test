package com.echevarne.sap.cloud.facturacion.odata.server;

import javax.naming.NamingException;

import org.apache.olingo.odata2.api.edm.EdmEntitySet;
import org.apache.olingo.odata2.api.ep.EntityProviderException;
import org.apache.olingo.odata2.api.exception.ODataException;
import org.apache.olingo.odata2.api.processor.ODataResponse;
import org.apache.olingo.odata2.api.uri.info.GetEntitySetUriInfo;
import org.apache.olingo.odata2.jpa.processor.api.ODataJPAContext;

import com.echevarne.sap.cloud.facturacion.odata.processor.ODataGlobalProcessor;

public class FacturacionODataJPAProcessor extends ODataGlobalProcessor {

	public FacturacionODataJPAProcessor(final ODataJPAContext oDataJPAContext) {
		super(oDataJPAContext);
	}

	public ODataResponse customReadEntitySet(EdmEntitySet entitySet, final GetEntitySetUriInfo uriParserResultView,
			final String contentType) throws EntityProviderException, ODataException, NamingException {
		return null;
	}

}