package com.echevarne.sap.cloud.facturacion.model.trazabilidad;

import javax.persistence.PostLoad;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TrazaHistEntityListener {
    @PostLoad
    public void postLoad(Object entity) {
        log.info("Carga de entidad TrazabilidadEstHistory");
    }
}