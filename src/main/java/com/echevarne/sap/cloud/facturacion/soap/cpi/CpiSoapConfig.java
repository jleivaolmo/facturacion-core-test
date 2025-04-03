package com.echevarne.sap.cloud.facturacion.soap.cpi;

import static com.echevarne.sap.cloud.facturacion.util.Destinations.Enum.CPI_SENDDOCUMENTS;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import com.echevarne.sap.cloud.facturacion.soap.DestinationMessageSender;
import com.echevarne.sap.cloud.facturacion.soap.SoapClient;
import com.echevarne.sap.cloud.facturacion.soap.SoapConfig;
import com.echevarne.sap.cloud.facturacion.util.Destinations;

@Configuration
public class CpiSoapConfig implements SoapConfig {

	@Value("${core.cpi.schema.package:com.echevarne.sap.cloud.facturacion.soap.cpi.schema}")
	private String cpiSchemaPackage;

	private final Destinations destinations;

	@Autowired
	public CpiSoapConfig(Destinations destinations) {
		this.destinations = destinations;
	}

	@Bean
	public Jaxb2Marshaller marshaller() {
		Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
		marshaller.setContextPath(cpiSchemaPackage);
		return marshaller;
	}

	@Bean
	public SoapClient soapClient(Jaxb2Marshaller marshaller) {
		DestinationMessageSender cpiDestinationMessageSender = cpiDestinationMessageSender();

		SoapClient client = new SoapClient();
		client.setDefaultUri(cpiDestinationMessageSender.getDestinationURL());
		client.setMarshaller(marshaller);
		client.setUnmarshaller(marshaller);
		client.setMessageSender(cpiDestinationMessageSender);
		return client;
	}

	@Bean("cpiDestinationMessageSender")
	@Profile("cf|cfbatch")
	public DestinationMessageSender cpiDestinationMessageSender() {
		return destinations.getDestinationMessageSender(CPI_SENDDOCUMENTS.getValue());
	}

	@Bean("cpiDestinationMessageSender")
	@Profile("!cf & !cfbatch")
	public DestinationMessageSender cpiDestinationMessageSender_ko() {
		// no permite su uso como parte del soapclient, pero sí permite satisfacer 
		// las dependencias necesarias para arrancar con el perfil "hana"
		return new DestinationMessageSender();
	}	
}
