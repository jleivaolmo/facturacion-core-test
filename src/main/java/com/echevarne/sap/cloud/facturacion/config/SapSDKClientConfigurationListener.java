package com.echevarne.sap.cloud.facturacion.config;

import com.sap.cloud.sdk.cloudplatform.connectivity.DefaultHttpClientFactory;
import com.sap.cloud.sdk.cloudplatform.connectivity.HttpClientAccessor;
import com.sap.cloud.sdk.cloudplatform.connectivity.HttpClientFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class SapSDKClientConfigurationListener implements ApplicationListener<ApplicationReadyEvent> {

    private final static int TIMEOUT_SAP_MINS = 3*60; // 3h timeout configurado en SAP
    private final static int TIMEOUT_MINS = TIMEOUT_SAP_MINS + 1; // 1 Minute above the timeout in SAP

    @Override
    public void onApplicationEvent(final ApplicationReadyEvent event) {
        int timeoutMs = (int) TimeUnit.MINUTES.toMillis(TIMEOUT_MINS);

        final HttpClientFactory factory = DefaultHttpClientFactory.builder()
                .timeoutMilliseconds(timeoutMs)
                .build();
        HttpClientAccessor.setHttpClientFactory(factory);

        log.info("HttpClientFactory for SAP SDK customized. [Timeout: " + TIMEOUT_MINS + " minutes]");
    }
}
