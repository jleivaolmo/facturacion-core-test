package com.echevarne.sap.cloud.facturacion.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.echevarne.sap.cloud.facturacion.model.masterdata.MasDataTipologiaFacturacion;
import com.echevarne.sap.cloud.facturacion.repositories.MasDataTipologiaFacturacionRep;
import com.echevarne.sap.cloud.facturacion.services.MasDataTipologiaFacturacionService;

/**
 * Business Service implementation of {@link MasDataTipologiaFacturacionService}
 */
@Service("masDataTipologiaFacturacionSrv")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class MasDataTipologiaFacturacionServiceImpl extends CrudServiceImpl<MasDataTipologiaFacturacion, Long>
		implements MasDataTipologiaFacturacionService {

	private final MasDataTipologiaFacturacionRep masDataTipologiaFacturacionRep;

	@Autowired
	public MasDataTipologiaFacturacionServiceImpl(MasDataTipologiaFacturacionRep masDataTipologiaFacturacionRep) {
		super(masDataTipologiaFacturacionRep);
		this.masDataTipologiaFacturacionRep = masDataTipologiaFacturacionRep;
	}

	@Override
	public List<MasDataTipologiaFacturacion> findByActive(boolean active) {
		return masDataTipologiaFacturacionRep.findByActive(active);
	}

	@Override
	public MasDataTipologiaFacturacion findByCodeTipologia(String codeTipologia) {
		return masDataTipologiaFacturacionRep.findByCodeTipologia(codeTipologia);
	}
}
