package com.echevarne.sap.cloud.facturacion.util;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;

import com.echevarne.sap.cloud.facturacion.controllers.RequestCopyController;

import brave.Span;
import brave.Tracer;

@Component
public class TroublesomeRequestReporter extends OncePerRequestFilter {

	@Autowired
	private Tracer tracer;

	private static final long DURATION_THRESHOLD = 20000; // threshold in milliseconds

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			 throws ServletException, IOException { 
		long startTime = System.currentTimeMillis();
        ContentCachingRequestWrapper wrappedRequest = new ContentCachingRequestWrapper(request);
        try {
        	filterChain.doFilter(wrappedRequest, response);
        } finally {
    		long duration = System.currentTimeMillis() - startTime;
			if ((RequestCopyController.ADD_REQUESTBODY_TO_TRACE || duration > DURATION_THRESHOLD || response.getStatus() >= 400) 
					&& "POST".equals(request.getMethod())) {
	    		Span currentSpan = tracer.currentSpan();
	    		if (currentSpan != null) {
					String httpBodyPart = extractRequestBody(wrappedRequest);
					currentSpan.tag("request.body", httpBodyPart);
	    		}
    		}
        }
	}

    private static String extractRequestBody(ContentCachingRequestWrapper request) {
        byte[] buf = request.getContentAsByteArray();
        if (buf.length > 0) {
            try {
                String requestBody = new String(buf, 0, buf.length, request.getCharacterEncoding());
                return requestBody;
            } catch (Exception e) {
                return "error in reading request body";
            }
        }
        return "empty";
    }

//	private String extractHttpBodyPart(HttpServletRequest request) {
//		if (request instanceof HttpServletRequestWrapper) {
//		    HttpServletRequestWrapper wrappedRequest = (HttpServletRequestWrapper) request;
//	        StringBuilder stringBuilder = new StringBuilder();
//	        try (BufferedReader bufferedReader = wrappedRequest.getReader()) {
//	            char[] charBuffer = new char[128];
//	            int bytesRead;
//	            while ((bytesRead = bufferedReader.read(charBuffer)) != -1) {
//	                stringBuilder.append(charBuffer, 0, bytesRead);
//	            }
//	        } catch (IOException e) {
//	            return(this.getClass().getName()+"::"+ e.getMessage());
//	        }
//	        return stringBuilder.toString();
//		} else {
//		    return "could not read it!";
//		}
//	}
}
