package com.echevarne.sap.cloud.facturacion.odata.services;

import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;

import org.apache.olingo.odata2.core.servlet.ODataServlet;

@WebServlet(urlPatterns = {
		"/technicals.svc/*",
}, initParams = {
        @WebInitParam(name = "javax.ws.rs.Application", value = "org.apache.olingo.odata2.core.rest.app.ODataApplication"),
        @WebInitParam(name = "org.apache.olingo.odata2.service.factory", value = "com.echevarne.sap.cloud.facturacion.odata.server.FacturacionODataJPAServiceFactory")

})
public class ODataServices extends ODataServlet {

	/**
	 *
	 */
	private static final long serialVersionUID = -3159470264659994094L;

}
