package com.echevarne.sap.cloud.facturacion.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.echevarne.sap.cloud.facturacion.model.MatConceptoFact;
import com.echevarne.sap.cloud.facturacion.repositories.MatConceptoFactRep;
import com.echevarne.sap.cloud.facturacion.services.MatConceptoFactService;

@Service("matConceptoFactSrv")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class MatConceptoFactServiceImpl extends CrudServiceImpl<MatConceptoFact, Long> implements MatConceptoFactService {

	private final MatConceptoFactRep matConceptoFactRep;

	@Autowired
	public MatConceptoFactServiceImpl(final MatConceptoFactRep matConceptoFactRep) {
		super(matConceptoFactRep);
		this.matConceptoFactRep = matConceptoFactRep;
	}

	@Override
	public List<MatConceptoFact> findByCodigoMaterial(String codigoMaterial) {
		return matConceptoFactRep.findByCodigoMaterial(codigoMaterial);
	}
}
