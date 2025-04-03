package com.echevarne.sap.cloud.facturacion.util;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import com.echevarne.sap.cloud.facturacion.config.MicrosConfig;
import com.echevarne.sap.cloud.facturacion.config.MicrosConfig.SecretData;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component("basicAuthInterceptor")
@Profile("!test")
public class BasicAuthInterceptor implements ClientHttpRequestInterceptor {
    
    private String authorizationHeaderValue;
    
    @Autowired
    private MicrosConfig conf;

    @PostConstruct
    public void init() {
    	SecretData secret = this.conf.getSecret(MicrosConfig.Secret.MICROSERVICES_BASICAUTH);
        String auth = secret.getUserName() + ":" + secret.getValue();
        byte[] authBytes = auth.getBytes(StandardCharsets.UTF_8);
        this.authorizationHeaderValue = "Basic " + Base64.getEncoder().encodeToString(authBytes);
    }    

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        log.info("******* Sending authorization header");    	
        HttpHeaders headers = request.getHeaders();
        headers.add("Authorization", authorizationHeaderValue);
        return execution.execute(request, body);
    }
}
