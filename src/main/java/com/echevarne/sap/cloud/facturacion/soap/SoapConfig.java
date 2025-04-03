package com.echevarne.sap.cloud.facturacion.soap;

import org.springframework.oxm.jaxb.Jaxb2Marshaller;

public interface SoapConfig {

	Jaxb2Marshaller marshaller();

	SoapClient soapClient(Jaxb2Marshaller marshaller);

}
