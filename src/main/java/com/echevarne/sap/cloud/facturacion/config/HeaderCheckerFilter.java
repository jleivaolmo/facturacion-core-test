
package com.echevarne.sap.cloud.facturacion.config;

import java.io.IOException;
import java.util.Base64;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.sleuth.Span;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.OncePerRequestFilter;

import com.echevarne.sap.cloud.facturacion.util.Destinations;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class HeaderCheckerFilter extends OncePerRequestFilter {
	private static final String AUTH_HEADER_NAME="Authorization";
	private static final String X_FORWARDED_FOR="X-Forwarded-For";
	private static final String ETAG="Etag";

    @Autowired
    private Tracer tracer;  // Get Sleuth Tracer bean
    
    @Value("${vcap.application.instance_index}")
    private String thisInstanceIndex;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
        Span currentSpan = tracer.currentSpan();
        if (null==request.getHeader(AUTH_HEADER_NAME)) {
           currentSpan.tag("AUTHENTICATION-MISSING", "<null>");
           log.info("AUTHENTICATION-MISSING : <null>");
        }
        else {
           String decodedUserPwd=decodeBasicAuthHeader(request.getHeader(AUTH_HEADER_NAME));
           currentSpan.tag("AUTHENTICATION-PRESENT", decodedUserPwd);
           log.info("AUTHENTICATION-PRESENT : " + decodedUserPwd);
        }
        if (null!=request.getHeader(X_FORWARDED_FOR)) {
            currentSpan.tag(X_FORWARDED_FOR, request.getHeader(X_FORWARDED_FOR));
        }
        if (null!=request.getHeader(ETAG)) {
            currentSpan.tag(ETAG, request.getHeader(ETAG));
        }        
        currentSpan.tag("INSTANCE_INDEX", thisInstanceIndex);
		chain.doFilter(request, response);
	}
	
    private String decodeBasicAuthHeader(String authHeader) {
        if (authHeader.startsWith("Basic ")) {
            // Remove "Basic " prefix and decode the Base64 string
            String base64Credentials = authHeader.substring(6);
            byte[] decodedBytes = Base64.getDecoder().decode(base64Credentials);
            return new String(decodedBytes);
        } else {
            return "Invalid Basic Authentication header"; // Invalid or missing Basic Authentication header
        }
    }
}
