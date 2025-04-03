package com.echevarne.sap.cloud.facturacion.jms.services;

import javax.jms.JMSException;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface JmsService {
	// mandatory prefix for connection to a queue in SAP event mesh.	
    static final String QUEUE_PREFIX = "queue:";

	void sendJMSMessage(Object message, String queueName) throws JMSException, JsonProcessingException;

}