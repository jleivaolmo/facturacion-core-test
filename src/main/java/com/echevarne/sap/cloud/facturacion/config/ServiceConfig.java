package com.echevarne.sap.cloud.facturacion.config;

import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ServletComponentScan(basePackages = {"com.echevarne.sap.cloud.facturacion"})
public class ServiceConfig {

}
