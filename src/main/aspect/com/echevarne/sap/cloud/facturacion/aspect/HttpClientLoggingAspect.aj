package com.echevarne.sap.cloud.facturacion.aspect;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.util.EntityUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.echevarne.sap.cloud.facturacion.config.ContextProvider;
import com.echevarne.sap.cloud.facturacion.controllers.RequestCopyController;

@Aspect
public class HttpClientLoggingAspect {

    private static final Logger LOG = LoggerFactory.getLogger(HttpClientLoggingAspect.class);

    @Pointcut("adviceexecution() && within(HttpClientLoggingAspect)")
    public void myAdvice() {}
    
    // Define the pointcut for the target method execution
    @Pointcut("execution(* org.apache.http.impl.client.CloseableHttpClient.execute(..))")
//    @Pointcut("execution(* org.apache.http.client.HttpClient.execute(..))")    
//    @Pointcut("execution(* com.sap.cloud.sdk.cloudplatform.connectivity.HttpClientWrapper.execute(..))")
    public void httpClientExecute() {}

    // Define the pointcut for the control flow
    @Pointcut("httpClientExecute() && !cflowbelow(myAdvice())")
    public void httpClientControlFlow() {}

    @Around("httpClientControlFlow()")
    public Object logHttpClientRequest(ProceedingJoinPoint joinPoint) throws Throwable {
    	
//    	LOG.debug("********** hi there, i am on the advice");
        Object result = null;
        Throwable exception = null;
        
        try {
            result = joinPoint.proceed();
        } catch (Throwable e) {
            exception = e;
        }

        if (RequestCopyController.SENDCOPY_ENABLED) {
            // Get the request entity (if applicable)
            Object[] args = joinPoint.getArgs();
            HttpRequest request=null;
            HttpHost host=null;
            if (args != null && args.length > 0 && args[0] instanceof org.apache.http.client.methods.HttpUriRequest) 
                request = (org.apache.http.client.methods.HttpUriRequest) args[0];
            else
	            if (args != null && args.length > 1 && args[1] instanceof HttpRequest)  {
	                request = (HttpRequest) args[1];
	                host = (HttpHost) args[0];
	            }

            if (null!=request) {
                try {
					logEntity(convertEntityToJson(request));
					sendClonedRequest(request, host);
				} catch (Exception e) {
					e.printStackTrace();
				}
            }
        }

		// If an exception occurred previous to the interception logic, rethrow it
		if (exception != null) {
			throw exception;
		}

		return result;
	}

    private String convertEntityToJson(HttpRequest request) throws IOException {
        if (request instanceof HttpEntityEnclosingRequest) {
            HttpEntityEnclosingRequest entityRequest = (HttpEntityEnclosingRequest) request;
            HttpEntity entity = entityRequest.getEntity();
            if ((entity != null) &&
                (entity.getContentType() != null) &&
                (entity.getContentType().getValue().equalsIgnoreCase("application/json"))) {
                // Convert the entity to JSON (implementation depends on the specific library used)
                String entityContent = EntityUtils.toString(entity);
                // Perform JSON conversion (e.g., using Jackson, Gson, etc.) if necessary
                return entityContent;
            }
        }
        // Return null if the conditions are not met
        return null;
    }

    private void logEntity(String jsonEntity) {
        LOG.info("HTTP Entity: {}", jsonEntity);
    }

	private void sendClonedRequest(HttpRequest request, HttpHost host) throws UnsupportedEncodingException, URISyntaxException {

		// Create a new request with the same entity and headers, but a different URL

		StringBuffer newUriStr=new StringBuffer(RequestCopyController.SENDCOPY_URL);
		URI uri=new URI(request.getRequestLine().getUri());
		if (!StringUtils.isEmpty(uri.getRawPath())) {
//		    newUriStr.append(URLEncoder.encode(request.getURI().getPath(), StandardCharsets.UTF_8.name()));
		    newUriStr.append(uri.getRawPath());		    
		}
		if (!StringUtils.isEmpty(uri.getRawQuery())) {
//		    newUriStr.append('?').append(URLEncoder.encode(request.getURI().getQuery(), StandardCharsets.UTF_8.name()));
			newUriStr.append('?').append(uri.getRawQuery());
		}
		if (host==null) {		
			host=new HttpHost(uri.getHost(), uri.getPort(), uri.getScheme());
		}
		HttpUriRequest clonedRequest = RequestBuilder.copy(request)
				.setUri(newUriStr.toString())
				.addHeader("x-original-target", host.toString())				
				.removeHeaders("php-auth-pw")
				.removeHeaders("php-auth-user")
//				.removeHeaders("proxy-authorization")
//				.removeHeaders("authorization")
				.build();

		try {
//			HttpClient httpClient = HttpClientBuilder.create().build();
			HttpClient httpClient = (HttpClient)ContextProvider.getBean("trayHttpClientFactory");
		    HttpResponse response = httpClient.execute(clonedRequest);
		    // Handle the response if needed
		    // ...
		} catch (IOException e) {
			LOG.error("** Exception in HttpClientLoggingAspect:" + e.getMessage());
		    // ...
		}
	}
}
