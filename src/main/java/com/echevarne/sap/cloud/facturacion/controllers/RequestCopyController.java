package com.echevarne.sap.cloud.facturacion.controllers;

import org.apache.http.client.HttpClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.echevarne.sap.cloud.facturacion.util.Loggable;
import com.sap.cloud.sdk.cloudplatform.connectivity.DestinationAccessor;
import com.sap.cloud.sdk.cloudplatform.connectivity.HttpClientAccessor;
import com.sap.cloud.sdk.cloudplatform.connectivity.HttpDestination;

@Profile("cf & debug")
@RestController
@RequestMapping("/api")
public class RequestCopyController {

	public static boolean SENDCOPY_ENABLED=false;
	public static boolean ADD_REQUESTBODY_TO_TRACE=false;
	public static String SENDCOPY_URL=null;	

	@PostMapping("/sendcopy/enable")
	@Loggable
	public String enableSendCopy() {
		SENDCOPY_ENABLED = true;
		return "enabled";
	}

    @PostMapping("/sendcopy/disable")
    public String disableSendCopy() {
    	SENDCOPY_ENABLED = false;
    	return "disabled";
    }
    
	@PostMapping("/keepbody/enable")
	@Loggable
	public String enableAddingRequestBodyToTrace() {
		ADD_REQUESTBODY_TO_TRACE = true;
		return "keepbody enabled";
	}

    @PostMapping("/keepbody/disable")
    public String disableAddingRequestBodyToTrace() {
    	ADD_REQUESTBODY_TO_TRACE = false;
    	return "keepbody disabled";
    }    

	@Bean("RequestTray")
	public HttpDestination getRequestTray() throws Exception{
		HttpDestination result=DestinationAccessor.getDestination("RequestTray").asHttp();
		SENDCOPY_URL=result.getUri().toString();
		return result;
	}

	@Bean("trayHttpClientFactory")
    @Scope(BeanDefinition.SCOPE_PROTOTYPE)
    public HttpClient trayHttpClientFactoryCF(@Qualifier("RequestTray") HttpDestination requestTray) {
		return HttpClientAccessor.getHttpClient(requestTray);
    }
}
