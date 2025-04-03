package com.echevarne.sap.cloud.facturacion.util;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.stereotype.Component;

@Component
public class TracerHolder {

    private static Tracer tracer;

    @Autowired
    public TracerHolder(Tracer tracer) {
        TracerHolder.tracer = tracer;
    }

    public static Tracer getTracer() {
        return tracer;
    }
}
