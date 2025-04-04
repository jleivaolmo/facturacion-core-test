package com.echevarne.sap.cloud.facturacion.services;

import java.util.List;

import com.echevarne.sap.cloud.facturacion.model.MatConceptoFact;

public interface MatConceptoFactService extends CrudService<MatConceptoFact, Long> {
	
	List<MatConceptoFact> findByCodigoMaterial(String codigoMaterial);

}
