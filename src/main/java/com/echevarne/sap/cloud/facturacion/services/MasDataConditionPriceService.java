package com.echevarne.sap.cloud.facturacion.services;

import java.util.Set;
import com.echevarne.sap.cloud.facturacion.model.masterdata.MasDataConditionPrice;

/**
 * Business Services logic for Model: {@link MasDataConditionPrice}
 * @author Hernan Girardi
 * @since 04/06/2020
 */
public interface MasDataConditionPriceService
		extends CrudService<MasDataConditionPrice, Long>, MasDataBaseService<MasDataConditionPrice, Long> {

	Set<MasDataConditionPrice> findByUngroup(boolean ungroup);

	Set<MasDataConditionPrice> findByEnviarAFacturar(boolean enviarAFacturar);
}
