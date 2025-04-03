package com.echevarne.sap.cloud.facturacion.util;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import com.google.gson.JsonObject;
import com.sap.cloud.sdk.cloudplatform.ScpCfCloudPlatform;
import com.sap.cloud.security.config.Environments;
import com.sap.cloud.security.config.OAuth2ServiceConfiguration;
import com.sap.cloud.security.xsuaa.client.ClientCredentials;
import com.sap.cloud.security.xsuaa.client.OAuth2TokenResponse;
import com.sap.cloud.security.xsuaa.client.XsuaaDefaultEndpoints;
import com.sap.cloud.security.xsuaa.client.XsuaaOAuth2TokenService;
import com.sap.cloud.security.xsuaa.tokenflows.TokenCacheConfiguration;
import com.sap.cloud.security.xsuaa.tokenflows.TokenFlowException;
import com.sap.cloud.security.xsuaa.tokenflows.XsuaaTokenFlows;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@Profile("cf")
public class XuaaTokenGenerator {

	private final XsuaaTokenFlows tokenFlows;

	public XuaaTokenGenerator() {
		OAuth2ServiceConfiguration oAuthConfig = Environments.getCurrent().getXsuaaConfiguration();  
	    ScpCfCloudPlatform sccp = ScpCfCloudPlatform.getInstanceOrThrow();
	    JsonObject connectivityServiceCredentials = sccp.getConnectivityServiceCredentials();
        final String clientId = connectivityServiceCredentials.get("clientid").getAsString();
        final String clientSecret = connectivityServiceCredentials.get("clientsecret").getAsString();
	    this.tokenFlows = new XsuaaTokenFlows(
	                new XsuaaOAuth2TokenService(TokenCacheConfiguration.cacheDisabled()),
	                new XsuaaDefaultEndpoints(oAuthConfig.getUrl()),
	                new ClientCredentials(clientId, clientSecret));		
	}

	public OAuth2TokenResponse getNewCloudConnectorToken() {
	    OAuth2TokenResponse clientCredentialsToken = null;
	    try {
	        clientCredentialsToken = this.tokenFlows.clientCredentialsTokenFlow().execute();
	        if (log.isDebugEnabled()) {
	        	log.debug("JWT token=" + clientCredentialsToken.getAccessToken());
	        }
	    } catch (IllegalArgumentException|TokenFlowException e) {
	    	log.error(this.getClass().getName()+"::getCloudConnectorToken()" + e.getMessage());
	        e.printStackTrace();
	    }
		return clientCredentialsToken;
	}
}
