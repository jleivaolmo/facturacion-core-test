package com.echevarne.sap.cloud.facturacion.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.echevarne.sap.cloud.facturacion.model.solicitudindividual.SolIndItemPricing;
import com.echevarne.sap.cloud.facturacion.model.solicitudindividual.SolIndItems;

/**
 * @author Steven Mendez
 * @version 1.0
 * @since 09/04/2019
 *
 */
@Repository("solIndItemPricingRepository")
public interface SolIndItemPricingRepository extends JpaRepository<SolIndItemPricing, Long> {

	SolIndItemPricing findById(String id);

	List<SolIndItemPricing> findByConditionTypeAndPosicionOrderByConditionRateValue(String conditionType, SolIndItems posicion);
	
	List<SolIndItemPricing> findByConditionTypeAndConditionCurrencyAndPosicion(String conditionType, String conditionCurrency, SolIndItems posicion);
}
