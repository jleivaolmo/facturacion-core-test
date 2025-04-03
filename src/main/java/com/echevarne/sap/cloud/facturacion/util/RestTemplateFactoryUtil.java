package com.echevarne.sap.cloud.facturacion.util;

import java.util.Collections;

import org.apache.http.client.HttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import com.echevarne.sap.cloud.facturacion.exception.RestTemplateErrorHandler;
import com.sap.cloud.sdk.cloudplatform.connectivity.DestinationAccessor;
import com.sap.cloud.sdk.cloudplatform.connectivity.HttpClientAccessor;
import com.sap.cloud.sdk.cloudplatform.connectivity.HttpDestination;

@Configuration(proxyBeanMethods = false)
public class RestTemplateFactoryUtil {

	@Autowired
	@Qualifier("theRestTemplateErrorHandler")
	private RestTemplateErrorHandler theRestTemplateErrorHandler;

	@Autowired
	@Qualifier("basicAuthInterceptor")
	private ClientHttpRequestInterceptor basicAuthInterceptor;

	@Bean
    @Scope(BeanDefinition.SCOPE_PROTOTYPE)	
    @Qualifier("restTemplateFactory")
    public RestTemplate restTemplateFactory(Destinations.Enum destination, int millisecondsTimeout) {
		HttpDestination httpDestination = DestinationAccessor.tryGetDestination(destination.value).get().asHttp();
		HttpClient httpClient = HttpClientAccessor.getHttpClient(httpDestination);

		final HttpComponentsClientHttpRequestFactory httpRequestFactory =  getHttpClientFactory(millisecondsTimeout);
		httpRequestFactory.setHttpClient(httpClient);

		final RestTemplate restTemplate = new RestTemplate();
		restTemplate.setRequestFactory(new BufferingClientHttpRequestFactory(httpRequestFactory));
        restTemplate.setInterceptors(Collections.singletonList(basicAuthInterceptor));		
		restTemplate.setErrorHandler(this.theRestTemplateErrorHandler);
		return restTemplate;
    }

	private HttpComponentsClientHttpRequestFactory getHttpClientFactory(int millisecondsTimeout) {
		HttpComponentsClientHttpRequestFactory httpRequestFactory = new HttpComponentsClientHttpRequestFactory();
		httpRequestFactory.setConnectionRequestTimeout(millisecondsTimeout);
		httpRequestFactory.setConnectTimeout(millisecondsTimeout);
		httpRequestFactory.setReadTimeout(millisecondsTimeout);
		return httpRequestFactory;
	}
}