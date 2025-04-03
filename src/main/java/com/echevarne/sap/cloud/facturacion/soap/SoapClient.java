package com.echevarne.sap.cloud.facturacion.soap;

import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

public class SoapClient extends WebServiceGatewaySupport {

    public Object callWebService(String url, Object request) {
        return getWebServiceTemplate().marshalSendAndReceive(url, request);
    }

    public Object callWebService(Object request) {
        return getWebServiceTemplate().marshalSendAndReceive(request);
    }

}
