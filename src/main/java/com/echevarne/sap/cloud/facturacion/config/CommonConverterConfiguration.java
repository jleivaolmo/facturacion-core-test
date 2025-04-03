package com.echevarne.sap.cloud.facturacion.config;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

import com.echevarne.sap.cloud.facturacion.jms.model.JobExecutionData;
import com.echevarne.sap.cloud.facturacion.jms.model.Notification;
import com.echevarne.sap.cloud.facturacion.jms.model.S4Companies;
import com.echevarne.sap.cloud.facturacion.jms.model.Trade;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class CommonConverterConfiguration {

   @Bean("typedJsonMessageConverter")
   public MessageConverter jacksonJmsMessageConverter() {
      MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
	  converter.setObjectMapper(defaultObjectMapper());
      converter.setEncoding(StandardCharsets.UTF_8.name());
      Map<String, Class<?>> aliasMap = new HashMap<String, Class<?>>();
      aliasMap.put(Notification.MESSAGE_TYPE, Notification.class);
      aliasMap.put(Trade.MESSAGE_TYPE, Trade.class);
      aliasMap.put(S4Companies.MESSAGE_TYPE, S4Companies.class);
      aliasMap.put(JobExecutionData.MESSAGE_TYPE, JobExecutionData.class);
      converter.setTypeIdMappings(aliasMap);
      converter.setTargetType(MessageType.TEXT);
      converter.setTypeIdPropertyName("_type");
      return converter;
   }

   @Bean("defaultObjectMapper")
   public ObjectMapper defaultObjectMapper() {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(MapperFeature.DEFAULT_VIEW_INCLUSION, false);
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	    objectMapper.setTimeZone(TimeZone.getDefault());
	    objectMapper.setSerializationInclusion(Include.ALWAYS);		
      return objectMapper;
   }
}