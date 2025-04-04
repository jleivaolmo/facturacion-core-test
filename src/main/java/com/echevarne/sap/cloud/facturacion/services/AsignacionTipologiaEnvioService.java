package com.echevarne.sap.cloud.facturacion.services;


import com.echevarne.sap.cloud.facturacion.model.salidas.AsignacionTipologiaEnvio;

import java.util.Optional;

public interface AsignacionTipologiaEnvioService {

    boolean existsAsignacionTipologiaEnvio(AsignacionTipologiaEnvio.TipoReferencia tipoReferencia, Long idReferencia);

    Optional<AsignacionTipologiaEnvio> getAsignacionTipologiaEnvio(AsignacionTipologiaEnvio.TipoReferencia tipoReferencia, Long idReferencia);

}
