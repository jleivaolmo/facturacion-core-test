package com.echevarne.sap.cloud.facturacion.services.impl;

import com.echevarne.sap.cloud.facturacion.model.masterdata.MasDataTipoDocumentoSAP;
import com.echevarne.sap.cloud.facturacion.repositories.MasDataTipoDocumentoSAPRep;
import com.echevarne.sap.cloud.facturacion.services.MasDataTipoDocumentoSAPService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Business Service implementation of {@link MasDataTipoDocumentoSAPService}
 */
@Service("masDataTipoDocumentoSrv")
public class MasDataTipoDocumentoSAPServiceImpl extends CrudServiceImpl<MasDataTipoDocumentoSAP, Long>
        implements MasDataTipoDocumentoSAPService {

    private final MasDataTipoDocumentoSAPRep masDataTipoDocumentoSAPRep;

    @Autowired
    public MasDataTipoDocumentoSAPServiceImpl(final MasDataTipoDocumentoSAPRep masDataTipoDocumentoSAPRep){
        super(masDataTipoDocumentoSAPRep);
        this.masDataTipoDocumentoSAPRep = masDataTipoDocumentoSAPRep;
    }

    @Override
    public List<MasDataTipoDocumentoSAP> findByActive(boolean active) {
        return masDataTipoDocumentoSAPRep.findByActive(active);
    }

    @Override
    public Optional<MasDataTipoDocumentoSAP> findByCodigo(String codigo) {
        return masDataTipoDocumentoSAPRep.findByCodigo(codigo);
    }
}
