
package com.echevarne.sap.cloud.facturacion.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import brave.baggage.BaggageFields;
import brave.baggage.CorrelationScopeConfig.SingleCorrelationField;
import brave.context.slf4j.MDCScopeDecorator;
import brave.propagation.CurrentTraceContext.ScopeDecorator;

@Configuration 
public class CustomMdcHeadersConfig {

//    @Bean
//    public CorrelationScopeCustomizer mdcOverridingScopeCustomizer() {
//        return builder -> {
//            builder.clear();
//            // We're adding back the default ones related to tracing context
//            builder.add(CorrelationScopeConfig.SingleCorrelationField.create(BaggageFields.TRACE_ID));
//            builder.add(CorrelationScopeConfig.SingleCorrelationField.newBuilder(BaggageFields.TRACE_ID).name("correlation-id").build());      
//            builder.add(CorrelationScopeConfig.SingleCorrelationField.create(BaggageFields.SPAN_ID));
//            builder.add(CorrelationScopeConfig.SingleCorrelationField.create(BaggageFields.SAMPLED));
//        };
//    }
    
    @Bean 
    public ScopeDecorator addAliasForKibana() {
    	  return MDCScopeDecorator.newBuilder()
    	                         .clear()
    	                         .add(SingleCorrelationField.newBuilder(BaggageFields.TRACE_ID)
    	                                                    .name("correlation_id").build())
    	                         .build();
    }
}
