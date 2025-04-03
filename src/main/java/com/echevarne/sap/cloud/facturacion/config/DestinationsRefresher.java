package com.echevarne.sap.cloud.facturacion.config;

import java.lang.reflect.Field;
import java.net.URI;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.echevarne.sap.cloud.facturacion.util.XuaaTokenGenerator;
import com.sap.cloud.sdk.cloudplatform.connectivity.Header;
import com.sap.cloud.sdk.cloudplatform.connectivity.HttpDestination;
import com.sap.cloud.sdk.cloudplatform.connectivity.HttpDestinationProperties;
import com.sap.cloud.sdk.s4hana.connectivity.DefaultErpHttpDestination;
import com.sap.cloud.sdk.s4hana.connectivity.ErpHttpDestination;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@Profile("cf")
public class DestinationsRefresher implements InitializingBean {

	private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
	
	@Autowired
	private XuaaTokenGenerator xuaaTokenGenerator;

	@Autowired
	@Qualifier("S4Destination")
	private ErpHttpDestination s4Destination;

	@Autowired	
	@Qualifier("S4HC_SOAP_HTTPS")
	private HttpDestination s4SoapDestination;
	
	@Autowired	
	@Qualifier("TRAK")
	private HttpDestination trakDestination;

	@Override
	public void afterPropertiesSet() throws Exception {
		scheduler.scheduleAtFixedRate(this::refreshAllCloudConnectorTokens, 6L, 6L, TimeUnit.HOURS);
	}
	
	private void refreshAllCloudConnectorTokens() {
		refreshCloudConnectorJWTTokenErp(this.s4Destination);
		refreshCloudConnectorJWTTokenHttp(this.s4SoapDestination);
		refreshCloudConnectorJWTTokenHttp(this.trakDestination);
	}

	private void refreshCloudConnectorJWTTokenErp(ErpHttpDestination destination) {
			log.info(this.getClass().getName()+"::refreshCloudConnectorJWTTokenErp() starts");
			try {
				Field baseDestinationField = DefaultErpHttpDestination.class.getDeclaredField("baseDestination");
				baseDestinationField.setAccessible(true);
				HttpDestinationProperties baseDestination=(HttpDestinationProperties)baseDestinationField.get(destination);
				for (Header h:baseDestination.getHeaders(new URI("doesntmatter"))) {
					log.debug("Header [" + h.getName() + "]=" + h.getValue().toString());
					if ("Proxy-Authorization".equalsIgnoreCase(h.getName())) {
						Field valueField = Header.class.getDeclaredField("value");
						valueField.setAccessible(true);
						String newValue="Bearer " + xuaaTokenGenerator.getNewCloudConnectorToken().getAccessToken();
						// atomic update of cached "Proxy-Authorization" header value with a freshly created JWT token
						valueField.set(h,newValue);
						log.debug("After update Header [" + h.getName() + "]=" + h.getValue().toString());		
						break;
					};
				}
			} catch (Exception e) {
				log.error(this.getClass().getName()+"::refreshCloudConnectorJWTTokenErp() failed!!");
				e.printStackTrace();
			}
			log.info(this.getClass().getName()+"::refreshCloudConnectorJWTTokenErp() ends");			
	}
	
	private void refreshCloudConnectorJWTTokenHttp(HttpDestination destination) {
		log.info(this.getClass().getName() + "::refreshCloudConnectorJWTTokenHttp() starts");
		try {
			for (Header h : destination.getHeaders(new URI("doesntmatter"))) {
				log.debug("Header [" + h.getName() + "]=" + h.getValue().toString());
				if ("Proxy-Authorization".equalsIgnoreCase(h.getName())) {
					Field valueField = Header.class.getDeclaredField("value");
					valueField.setAccessible(true);
					String newValue = "Bearer " + xuaaTokenGenerator.getNewCloudConnectorToken().getAccessToken();
					// atomic update of cached "Proxy-Authorization" header value with a freshly
					// created JWT token
					valueField.set(h, newValue);
					log.debug("After update Header [" + h.getName() + "]=" + h.getValue().toString());
					break;
				}
			}
		} catch (Exception e) {
			log.error(this.getClass().getName() + "::refreshCloudConnectorJWTTokenHttp() failed!!");
			e.printStackTrace();
		}
		log.info(this.getClass().getName() + "::refreshCloudConnectorJWTTokenHttp() ends");
	}
/*	
	private void checkS4DestinationIsHealthy() {
	    log.info("Checking S4Destination bean...");
	    try {
			log.info(executeBusinessPartnerQuery());			
		} catch (Exception e) {
			log.error(this.getClass().getName()+"::checkS4DestinationBean(): " + e.getMessage());
			e.printStackTrace();
		}
	}
	
    private String executeBusinessPartnerQuery() throws IOException {
    	final String CATEGORY_PERSON = "1";
        try {
            final List<BusinessPartner> businessPartners =
                    new DefaultBusinessPartnerService()
                            .getAllBusinessPartner()
                            .select(BusinessPartner.BUSINESS_PARTNER,
                                    BusinessPartner.LAST_NAME,
                                    BusinessPartner.FIRST_NAME,
                                    BusinessPartner.IS_MALE,
                                    BusinessPartner.IS_FEMALE,
                                    BusinessPartner.CREATION_DATE)
                            .filter(BusinessPartner.BUSINESS_PARTNER_CATEGORY.eq(CATEGORY_PERSON))
                            .top(10)
                            .orderBy(BusinessPartner.CREATION_DATE, Order.DESC)
                            .executeRequest(this.s4Destination);
            log.info(this.getClass().getName()+"::executeBusinessPartnerQuery(): *** OK");            
            return new Gson().toJson(businessPartners);
        } catch (final ODataException e) {
            log.error(this.getClass().getName()+"::executeBusinessPartnerQuery(): " + e.getCause().getMessage(), e.getCause());
            return "error";
        }
    }	
*/
}

