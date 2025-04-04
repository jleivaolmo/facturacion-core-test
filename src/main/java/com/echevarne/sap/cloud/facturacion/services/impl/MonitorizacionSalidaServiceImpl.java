package com.echevarne.sap.cloud.facturacion.services.impl;

import com.echevarne.sap.cloud.facturacion.model.salidas.FacturaPendienteSalida;
import com.echevarne.sap.cloud.facturacion.model.salidas.MonitorizacionSalida;
import com.echevarne.sap.cloud.facturacion.repositories.FacturaPendienteSalidaRepository;
import com.echevarne.sap.cloud.facturacion.repositories.MonitorizacionSalidaRepository;
import com.echevarne.sap.cloud.facturacion.services.MonitorizacionSalidaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class MonitorizacionSalidaServiceImpl implements MonitorizacionSalidaService {

    private MonitorizacionSalidaRepository monitorizacionSalidaRep;
    private FacturaPendienteSalidaRepository facturaPendienteSalidaRep;

    @Autowired
    public MonitorizacionSalidaServiceImpl(
            MonitorizacionSalidaRepository monitorizacionSalidaRep,
            FacturaPendienteSalidaRepository facturaPendienteSalidaRep
    ) {
        this.monitorizacionSalidaRep = monitorizacionSalidaRep;
        this.facturaPendienteSalidaRep = facturaPendienteSalidaRep;
    }

    @Override
    public MonitorizacionSalida create(MonitorizacionSalida monitorizacionSalida) {
        log.info("Creando Monitorizacion salida");
        return this.monitorizacionSalidaRep.save(monitorizacionSalida);
    }

    @Override
    public List<String> getFacturasPendientes() {
        log.info("Obteniendo facturas pendientes");
        return this.facturaPendienteSalidaRep.findAll().stream()
                .map(FacturaPendienteSalida::getBillingDocument)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getFacturasPendientes(Date startDate) {
        log.info("Obteniendo facturas pendientes a partir del " + startDate);
        return this.facturaPendienteSalidaRep.findByFechaFacturaGreaterThanEqual(startDate).stream()
                .map(FacturaPendienteSalida::getBillingDocument)
                .collect(Collectors.toList());
    }

}
