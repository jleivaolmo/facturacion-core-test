package com.echevarne.sap.cloud.facturacion.services;

import java.util.List;

import com.echevarne.sap.cloud.facturacion.model.solicitudindividual.SolIndItemPricing;
import com.echevarne.sap.cloud.facturacion.model.solicitudindividual.SolIndItems;

/**
 * Class for services {@link SolIndItemPricingService}.
 * 
 * <p>
 * . . .
 * </p>
 * <p>
 * Services for the bussiness logic of the Model: SolIndItemPricing
 * </p>
 *
 * @author Steven Ricardo Mendez Brenes
 * @version 1.0
 * @since 06/24/2019
 * 
 */
public interface SolIndItemPricingService extends CrudService<SolIndItemPricing, Long> {

	List<SolIndItemPricing> findByConditionTypeAndPosicionOrderByConditionRateValue(String conditionType, SolIndItems posicion);
	
	List<SolIndItemPricing> findByConditionTypeAndConditionCurrencyAndPosicion(String conditionType, String conditionCurrency, SolIndItems posicion);
}
