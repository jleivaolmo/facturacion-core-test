package com.echevarne.sap.cloud.facturacion.services.impl;

import com.echevarne.sap.cloud.facturacion.model.salidas.AsignacionTipologiaEnvio;
import com.echevarne.sap.cloud.facturacion.repositories.AsignacionTipologiaEnvioRep;
import com.echevarne.sap.cloud.facturacion.services.AsignacionTipologiaEnvioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service("asignacionTipologiaEnvioSrv")
public class AsignacionTipologiaEnvioServiceImpl implements AsignacionTipologiaEnvioService {

    private AsignacionTipologiaEnvioRep asignacionTipologiaEnvioRep;

    @Autowired
    public AsignacionTipologiaEnvioServiceImpl(AsignacionTipologiaEnvioRep asignacionTipologiaEnvioRep) {
        this.asignacionTipologiaEnvioRep = asignacionTipologiaEnvioRep;
    }

    @Override
    public boolean existsAsignacionTipologiaEnvio(AsignacionTipologiaEnvio.TipoReferencia tipoReferencia, Long idReferencia) {
        return asignacionTipologiaEnvioRep.existsByTipoReferenciaAndIdReferencia(tipoReferencia, idReferencia);
    }

    @Override
    public Optional<AsignacionTipologiaEnvio> getAsignacionTipologiaEnvio(
            AsignacionTipologiaEnvio.TipoReferencia tipoReferencia,
            Long idReferencia
    ) {
        return Optional.ofNullable(asignacionTipologiaEnvioRep.findByTipoReferenciaAndIdReferencia(tipoReferencia, idReferencia));
    }
}
