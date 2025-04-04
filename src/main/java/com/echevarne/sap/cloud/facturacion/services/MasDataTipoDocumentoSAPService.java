package com.echevarne.sap.cloud.facturacion.services;

import com.echevarne.sap.cloud.facturacion.model.masterdata.MasDataTipoContrato;
import com.echevarne.sap.cloud.facturacion.model.masterdata.MasDataTipoDocumentoSAP;

import java.util.List;
import java.util.Optional;

/**
 * Business Services logic for Model {@link MasDataTipoContrato}
 *
 * @author Germán Laso
 * @since 27/08/2020
 */
public interface MasDataTipoDocumentoSAPService
        extends CrudService<MasDataTipoDocumentoSAP, Long>, MasDataBaseService<MasDataTipoDocumentoSAP, Long> {

    List<MasDataTipoDocumentoSAP> findByActive(boolean active);

    Optional<MasDataTipoDocumentoSAP> findByCodigo(String codigo);

}