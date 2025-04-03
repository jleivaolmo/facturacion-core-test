package com.echevarne.sap.cloud.facturacion.jms.services;

import javax.jms.JMSException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;

@Component
@Profile({"!event-mesh"})
/**
 * 
 * Implementación vacía para permitir compilación cuando no hay un event-mesh disponible
 *
 */
public class DummyJmsServiceImpl implements JmsService {

	@Override
	public void sendJMSMessage(Object message, String queueName) throws JMSException,JsonProcessingException {
		throw new UnsupportedOperationException(this.getClass().getName()+"::sendJMSMessage()");
	}
	
	@Bean
	public JmsTemplate theJmsTemplate() {
	    JmsTemplate jmsTemplate = new JmsTemplate() {
			@Override
			public void afterPropertiesSet() {}
	    };
	    return jmsTemplate;
	}
	
	@Bean
	public JmsMessagingTemplate theJmsMessagingTemplate() { 
		return new JmsMessagingTemplate(theJmsTemplate()) {
			@Override
			public void afterPropertiesSet() {}			
		};
	 }	
	
}
