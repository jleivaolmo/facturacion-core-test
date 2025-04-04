package com.echevarne.sap.cloud.facturacion.services;

import java.util.Optional;

import com.echevarne.sap.cloud.facturacion.model.masterdata.MasDataEstadosGrupo;
import com.echevarne.sap.cloud.facturacion.model.masterdata.MasDataMessagesGrupo;

/**
 * Business Services logic for Model: {@link MasDataEstadosGrupo}
 */
public interface MasDataMessagesGrupoService extends CrudService<MasDataMessagesGrupo, Long>,
		MasDataBaseService<MasDataMessagesGrupo, Long> {

    Optional<MasDataMessagesGrupo> findByCodeGrupo(String codeGrupo);
}
