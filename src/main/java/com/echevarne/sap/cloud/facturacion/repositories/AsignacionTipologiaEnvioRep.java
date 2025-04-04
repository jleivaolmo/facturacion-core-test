package com.echevarne.sap.cloud.facturacion.repositories;

import com.echevarne.sap.cloud.facturacion.model.salidas.AsignacionTipologiaEnvio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("AsignacionTipologiaEnvioRep")
public interface AsignacionTipologiaEnvioRep extends JpaRepository<AsignacionTipologiaEnvio, Long> {

    boolean existsByTipoReferenciaAndIdReferencia(AsignacionTipologiaEnvio.TipoReferencia tipoReferencia, Long idReferencia);

    AsignacionTipologiaEnvio findByTipoReferenciaAndIdReferencia(
            AsignacionTipologiaEnvio.TipoReferencia tipoReferencia,
            Long idReferencia
    );
}
