package com.echevarne.sap.cloud.facturacion.jms.services;

import javax.jms.BytesMessage;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.stereotype.Component;

import com.echevarne.sap.cloud.facturacion.jms.model.QueuesEnum;
import com.echevarne.sap.cloud.facturacion.jms.model.TypedJsonMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Component
@Profile({"event-mesh"})
public class JmsServiceImpl implements JmsService {
    
	@Autowired
	private ConnectionFactory connectionFactory;
	
	@Autowired
	@Qualifier("typedJsonMessageConverter")
	private MessageConverter typedJsonMessageConverter;
	
	@Autowired	
	@Qualifier("defaultObjectMapper")
	private ObjectMapper defaultObjectMapper;	

	@Override
	public void sendJMSMessage(Object message, String queueName) throws JMSException,JsonProcessingException {
	    log.info("Sending message={} to queue={}", message, queueName);

        String msgStr = defaultObjectMapper.writeValueAsString(message);
        Connection connection = connectionFactory.createConnection();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);   
        connection.start();
        BytesMessage byteMessage = session.createBytesMessage();
        byteMessage.writeBytes(msgStr.getBytes());
        if (message instanceof TypedJsonMessage) {
        	byteMessage.setStringProperty("_type", ((TypedJsonMessage)message).getMessageType());
        }
        Queue queue = session.createQueue(QUEUE_PREFIX + queueName); // even though the JMS API is "createQueue" the queue will not be created on the message broker
        MessageProducer producer = session.createProducer(queue);
        producer.send(byteMessage);
	}

	@Bean
	public JmsTemplate theJmsTemplate() {
	    JmsTemplate jmsTemplate = new JmsTemplate(connectionFactory);
	    jmsTemplate.setDefaultDestinationName(QueuesEnum.DEADLETTER.queueName);
	    jmsTemplate.setReceiveTimeout(10000);
	    jmsTemplate.setMessageConverter(typedJsonMessageConverter);
	    return jmsTemplate;
	}
	
	@Bean
	public JmsMessagingTemplate theJmsMessagingTemplate() { return new JmsMessagingTemplate(theJmsTemplate()); }	

//	@Bean
//	public MessagePostProcessor typedJsonMessagePostProcessor() {
//		return new MessagePostProcessor() {
//        @Override
//        public Message postProcessMessage(Message message) throws JMSException {
//            message.setJMSType(messageGroup);
//            return message;
//        }
//    };	
	
//	// Java Program to Illustrate Sending JMS Messages
//
//	package com.anuanu.springjms.sender;
//
//	// Importing required classes
//	import com.anuanu.springjms.config.JmsConfig;
//	import com.anuanu.springjms.model.GreetingMessage;
//	import java.util.UUID;
//	
//	import org.springframework.jms.core.JmsTemplate;
//	import org.springframework.scheduling.annotation.Scheduled;
//	import org.springframework.stereotype.Component;
//
//	// Annotations
//	@AllArgsConstructor @NoArgsConstructor
//	@Component

//	// Class
//	public class MessageSender {
//
//		// Class data member
//		private final JmsTemplate jmsTemplate;
//		private static Integer ID = 1;
//
//		// Annotation
//		@Scheduled(fixedRate = 2000)
//
//		// Method
//		public void sendMessage()
//		{
//			// Display command
//			System.out.println("Greetings user");
//
//			GreetingMessage message
//				= GreetingMessage.builder()
//					.id(UUID.randomUUID())
//					.message("Greetings user " + ID++)
//					.build();
//
//			jmsTemplate.convertAndSend(JmsConfig.QUEUE,
//									message);
//
//			// Display command
//			System.out.println("Message sent!!!");
//		}
//	}
	
}
