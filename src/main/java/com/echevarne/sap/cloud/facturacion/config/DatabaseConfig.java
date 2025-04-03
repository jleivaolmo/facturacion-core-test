package com.echevarne.sap.cloud.facturacion.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.PostConstruct;
import java.util.Calendar;

/**
 * Class for the {@link ContextProvider}.
 *
 * <p>. . .</p>
 * <p>This is the configuration beans </p>
 *
 * @author Steven Ricardo Mendez Brenes
 * @version 1.0
 * @since 06/24/2019
 *
 */
@Configuration
@EnableTransactionManagement
@EnableConfigurationProperties
@Slf4j
public class DatabaseConfig {
    @Bean
    public static PropertySourcesPlaceholderConfigurer propConfig(){
        PropertySourcesPlaceholderConfigurer placeholderConfigurer = new PropertySourcesPlaceholderConfigurer();
        placeholderConfigurer.setLocations( new ClassPathResource( "application.properties" ) );
        placeholderConfigurer.setIgnoreUnresolvablePlaceholders( true );
        return placeholderConfigurer;
    }

    @PostConstruct
    public void logTimezone(){
        Calendar calendar = Calendar.getInstance();
        log.info("Running with java timezone [Timezone: " + calendar.getTimeZone().getDisplayName() +"]");
    }

}
