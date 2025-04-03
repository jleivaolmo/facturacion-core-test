package com.echevarne.sap.cloud.facturacion.exception;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatus.Series;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestClientResponseException;

import com.google.common.io.ByteStreams;

@Configuration
@Qualifier("theRestTemplateErrorHandler")
public class RestTemplateErrorHandler implements ResponseErrorHandler{
	
	@Override
	public boolean hasError(ClientHttpResponse httpResponse) throws IOException {
		return httpResponse.getStatusCode()!=HttpStatus.OK; 
	}

	@Override
	public void handleError(ClientHttpResponse httpResponse) throws IOException {
		if (httpResponse.getStatusCode().series() == Series.CLIENT_ERROR || httpResponse.getStatusCode().series() == Series.SERVER_ERROR) {
			byte[] bodyAsByte = ByteStreams.toByteArray(httpResponse.getBody());
			throw new RestClientResponseException(null, httpResponse.getRawStatusCode(),httpResponse.getStatusText(), httpResponse.getHeaders(), bodyAsByte, null);   
		}
//		else {
//		                throw new NotFoundException();
//		}
	}
}
