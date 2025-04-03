
package com.echevarne.sap.cloud.facturacion.config;

import java.util.Map;

import javax.jms.ConnectionFactory;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.jms.connection.CachingConnectionFactory;

import com.sap.cloud.servicesdk.xbem.core.MessagingServiceFactory;
import com.sap.cloud.servicesdk.xbem.core.exception.MessagingException;
import com.sap.cloud.servicesdk.xbem.core.impl.MessagingServiceFactoryCreator;
import com.sap.cloud.servicesdk.xbem.extension.sapcp.jms.MessagingServiceJmsConnectionFactory;
import com.sap.cloud.servicesdk.xbem.extension.sapcp.jms.MessagingServiceJmsSettings;

import io.pivotal.cfenv.core.CfCredentials;
import io.pivotal.cfenv.core.CfEnv;

@Configuration
@Profile({"event-mesh"})
public class MessagingServiceConfig {
	
    @Value("${event.mesh.instance.name:facturacion-event-mesh}")
    private String eventMeshInstanceName;

    @Bean
    public MessagingServiceFactory getMessagingServiceFactory() {
        CfEnv cfEnv = new CfEnv();
        CfCredentials cfCredentials = cfEnv.findCredentialsByName(eventMeshInstanceName);
        Map<String, Object> credentials = cfCredentials.getMap();
        if (credentials == null) {
            throw new IllegalStateException("Unable to create the MessagingService.");
        }
        return MessagingServiceFactoryCreator.createFactoryFromCredentials(credentials);
    }

    @Bean
	@Primary    
    public ConnectionFactory getMessagingServiceJmsConnectionFactory(MessagingServiceFactory messagingServiceFactory) {
        try {
            /*
             * The settings object is preset with default values (see JavaDoc)
             * and can be adjusted. The settings aren't required and depend on
             * the use-case. Note: a connection will be closed after an idle
             * time of 5 minutes.
             */
            MessagingServiceJmsSettings settings = new MessagingServiceJmsSettings();
            settings.setFailoverMaxReconnectAttempts(5); // use -1 for unlimited attempts
            settings.setFailoverInitialReconnectDelay(3000);
            settings.setFailoverReconnectDelay(3000);
            settings.setJmsRequestTimeout(30000);
            settings.setAmqpIdleTimeout(-1);
//            settings.setAmqpTraceFrame(true);

            // Custom provided authentication request, it is not mandatory. Emjapi can request token from the client info.
            //TokenRequest tokenRequest = new TokenRequest();
            //settings.setAuthenticationRequest(tokenRequest::requestToken);

            MessagingServiceJmsConnectionFactory msjcf= messagingServiceFactory.createConnectionFactory(MessagingServiceJmsConnectionFactory.class, settings);
    		CachingConnectionFactory ccf=new CachingConnectionFactory(msjcf);
    		ccf.setCacheProducers(false);
    		ccf.setSessionCacheSize(10);
    		return ccf;
        } catch (MessagingException e) {
            throw new IllegalStateException("Unable to create the Connection Factory", e);
        }
    } 
}