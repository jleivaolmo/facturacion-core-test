package com.echevarne.sap.cloud.facturacion.config;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import com.echevarne.sap.cloud.facturacion.util.Destinations;
import com.sap.cloud.sdk.cloudplatform.connectivity.AuthenticationType;
import com.sap.cloud.sdk.cloudplatform.connectivity.DefaultHttpDestination;
import com.sap.cloud.sdk.cloudplatform.connectivity.DestinationAccessor;
import com.sap.cloud.sdk.cloudplatform.connectivity.Header;
import com.sap.cloud.sdk.cloudplatform.connectivity.HttpDestination;
import com.sap.cloud.sdk.cloudplatform.connectivity.HttpDestinationProperties;
import com.sap.cloud.sdk.s4hana.connectivity.DefaultErpHttpDestination;
import com.sap.cloud.sdk.s4hana.connectivity.ErpHttpDestination;
import com.sap.cloud.sdk.s4hana.connectivity.ErpHttpDestinationUtils;
import com.sap.core.connectivity.api.configuration.ConnectivityConfiguration;
import com.sap.core.connectivity.api.configuration.DestinationConfiguration;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class DestinationsConfig {

	private static final String SAP_CLIENT = "200";

	@Bean("TRAK")
	@Profile("cf|cfbatch")
	public HttpDestination getTrakDestinationHttps() throws Exception{
		return DestinationAccessor.getDestination(Destinations.Enum.TRAK.getValue()).asHttp();
	}

	@Bean("S4HC_SOAP_HTTPS")
	@Profile("cf|cfbatch")
	public HttpDestination getS4asSOAPDestinationHttps() throws Exception{
		return DestinationAccessor.getDestination("S4HC_SOAP_HTTPS").asHttp();
	}

	@Bean("S4HC_SOAP_HTTPS")
	@Profile("!cf & !cfbatch")
	public HttpDestination getS4asSOAPDestinationHttps_local(@Value("${salesorderbulk.service.address}") String serviceAddress) throws Exception{
		return DefaultHttpDestination
				.builder(serviceAddress)
				.name("S4HC_SOAP_HTTPS")
				.build();
	}

	@Bean("S4Destination")
	@Profile("cf|cfbatch")
	public static ErpHttpDestination setS4Destination() {
		log.info("Se comienza la carga de la destination S4HC");
		return ErpHttpDestinationUtils.getErpHttpDestination(Destinations.Enum.S4DESTINATION.getValue());
	}

	@Bean("S4Destination")
	@Profile({ "dev", "hana", "docker" })
	public static HttpDestinationProperties setHttpDestination() throws NamingException {

		log.info("Se comienza la carga de la destination S4HC (Entornos no-productivos)");
		DefaultHttpDestination destination = null;
		try {

			Context ctx = new InitialContext();
			ConnectivityConfiguration configuration = (ConnectivityConfiguration) ctx.lookup("java:comp/env/connectivityConfiguration");
			DestinationConfiguration destConfiguration = configuration.getConfiguration(Destinations.Enum.S4DESTINATION.getValue());

			destination = DefaultHttpDestination
					.builder(destConfiguration.getProperty(DestinationConfiguration.DESTINATION_URL))
					.name(destConfiguration.getProperty(DestinationConfiguration.DESTINATION_NAME))
					.authenticationType(AuthenticationType.BASIC_AUTHENTICATION)
					.user(destConfiguration.getProperty(DestinationConfiguration.DESTINATION_USER))
					.password(destConfiguration.getProperty(DestinationConfiguration.DESTINATION_PASSWORD))
					.header(new Header(DefaultErpHttpDestination.SAP_CLIENT_HEADER_NAME, SAP_CLIENT))
					.trustAllCertificates().build();


		} catch (NamingException e) {
			destination = DefaultHttpDestination
					.builder("https://sapdas.laboratorioechevarne.com:44380")
					.name("S4HCDevelopment")
					.authenticationType(AuthenticationType.BASIC_AUTHENTICATION)
					.user("SCP_FACT")
					.password("Laboratorio#20")
					.header(new Header(DefaultErpHttpDestination.SAP_CLIENT_HEADER_NAME, SAP_CLIENT))
					.trustAllCertificates().build();
		}

		return destination;

	}
}
