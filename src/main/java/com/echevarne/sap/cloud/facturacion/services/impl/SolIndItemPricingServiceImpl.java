package com.echevarne.sap.cloud.facturacion.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.echevarne.sap.cloud.facturacion.model.solicitudindividual.SolIndItemPricing;
import com.echevarne.sap.cloud.facturacion.model.solicitudindividual.SolIndItems;
import com.echevarne.sap.cloud.facturacion.repositories.SolIndItemPricingRepository;
import com.echevarne.sap.cloud.facturacion.services.SolIndItemPricingService;

@Service("solIndItemPricingService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class SolIndItemPricingServiceImpl extends CrudServiceImpl<SolIndItemPricing, Long> implements SolIndItemPricingService {

	private final SolIndItemPricingRepository repo;

	@Autowired
	public SolIndItemPricingServiceImpl(final SolIndItemPricingRepository repo) {
		super(repo);
		this.repo = repo;
	}

	@Override
	public List<SolIndItemPricing> findByConditionTypeAndPosicionOrderByConditionRateValue(String conditionType, SolIndItems posicion) {
		return repo.findByConditionTypeAndPosicionOrderByConditionRateValue(conditionType, posicion);
	}

	@Override
	public List<SolIndItemPricing> findByConditionTypeAndConditionCurrencyAndPosicion(String conditionType, String conditionCurrency, SolIndItems posicion) {
		return repo.findByConditionTypeAndConditionCurrencyAndPosicion(conditionType, conditionCurrency, posicion);
	}
}
