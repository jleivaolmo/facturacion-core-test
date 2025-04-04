package com.echevarne.sap.cloud.facturacion.services.impl;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.echevarne.sap.cloud.facturacion.model.masterdata.MasDataConditionPrice;
import com.echevarne.sap.cloud.facturacion.repositories.MasDataConditionPriceRep;
import com.echevarne.sap.cloud.facturacion.services.MasDataConditionPriceService;

/**
 * Business Service implementation of {@link MasDataConditionPriceService}
 */
@Service("masDataConditionPriceSrv")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class MasDataConditionPriceServiceImpl extends CrudServiceImpl<MasDataConditionPrice, Long>
		implements MasDataConditionPriceService {

	private final MasDataConditionPriceRep masDataConditionPriceRep;

	@Autowired
	public MasDataConditionPriceServiceImpl(final MasDataConditionPriceRep masDataConditionPriceRep) {
		super(masDataConditionPriceRep);
		this.masDataConditionPriceRep = masDataConditionPriceRep;
	}

	@Override
	public List<MasDataConditionPrice> findByActive(boolean active) {
		return masDataConditionPriceRep.findByActive(active);
	}

	@Override
	public Set<MasDataConditionPrice> findByUngroup(boolean ungroup) {
		return masDataConditionPriceRep.findByUngroup(ungroup);
	}

	@Override
	public Set<MasDataConditionPrice> findByEnviarAFacturar(boolean enviarAFacturar) {
		return masDataConditionPriceRep.findByEnviarAFacturar(enviarAFacturar);
	}

}
