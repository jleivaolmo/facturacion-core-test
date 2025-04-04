package com.echevarne.sap.cloud.facturacion.services.impl;

import java.util.List;

import com.echevarne.sap.cloud.facturacion.model.masterdata.MasDataSubTipologiaFacturacion;
import com.echevarne.sap.cloud.facturacion.repositories.MasDataSubTipologiaFacturacionRep;
import com.echevarne.sap.cloud.facturacion.services.MasDataSubTipologiaFacturacionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("masDataSubTipologiaFacturacionSrv")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class MasDataSubTipologiaFacturacionServiceImpl extends CrudServiceImpl<MasDataSubTipologiaFacturacion, Long>
        implements MasDataSubTipologiaFacturacionService {

    private final MasDataSubTipologiaFacturacionRep masDataSubTipologiaFacturacionRep;

    @Autowired
    public MasDataSubTipologiaFacturacionServiceImpl(final MasDataSubTipologiaFacturacionRep masDataSubTipologiaFacturacionRep) {
        super(masDataSubTipologiaFacturacionRep);
        this.masDataSubTipologiaFacturacionRep = masDataSubTipologiaFacturacionRep;
    }

    @Override
	public List<MasDataSubTipologiaFacturacion> findByActive(boolean active) {
        return masDataSubTipologiaFacturacionRep.findByActive(active);
	}

    @Override
	public MasDataSubTipologiaFacturacion findByCodeSubTipologia(String codeTipologia) {
		return masDataSubTipologiaFacturacionRep.findByCodeSubTipologia(codeTipologia);
	}
}
