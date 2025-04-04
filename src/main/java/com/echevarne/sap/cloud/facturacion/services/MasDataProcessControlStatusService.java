package com.echevarne.sap.cloud.facturacion.services;

import com.echevarne.sap.cloud.facturacion.model.masterdata.MasDataProcessControlStatus;

/**
 * Business Services logic for Model: {@link MasDataProcessControlStatus}
 * 
 * @author Hernan Girardi
 * @since 26/03/2020
 */

public interface MasDataProcessControlStatusService
		extends CrudService<MasDataProcessControlStatus, Long>, MasDataBaseService<MasDataProcessControlStatus, Long> {
	
	MasDataProcessControlStatus findByCodeStatus(String codeStatus);
	
}