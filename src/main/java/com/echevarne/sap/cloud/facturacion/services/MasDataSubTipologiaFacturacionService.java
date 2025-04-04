package com.echevarne.sap.cloud.facturacion.services;

import com.echevarne.sap.cloud.facturacion.model.masterdata.MasDataSubTipologiaFacturacion;

/**
 * Business Services logic for Model: {@link MasDataSubTipologiaFacturacion}
 * 
 * @author Hernan Girardi
 * @since 26/03/2020
 */

public interface MasDataSubTipologiaFacturacionService
		extends CrudService<MasDataSubTipologiaFacturacion, Long>, MasDataBaseService<MasDataSubTipologiaFacturacion, Long> {
	
	MasDataSubTipologiaFacturacion findByCodeSubTipologia(String codeSubTipologia);
	
}