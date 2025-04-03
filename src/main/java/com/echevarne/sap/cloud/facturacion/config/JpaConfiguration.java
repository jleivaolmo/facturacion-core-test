package com.echevarne.sap.cloud.facturacion.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = {"com.echevarne.sap.cloud.facturacion"})
@EntityScan( basePackages = {"com.echevarne.sap.cloud.facturacion"} )
public class JpaConfiguration {

}
