package com.echevarne.sap.cloud.facturacion.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.echevarne.sap.cloud.facturacion.model.contratos.ContCapitativoPolizas;
import com.echevarne.sap.cloud.facturacion.repositories.ContCapitativoPolizasRep;
import com.echevarne.sap.cloud.facturacion.services.ContCapitativoPolizasService;

/**
 *
 * @author Germán Laso
 * @version 1.0
 *
 */
@Service("contCapitativoPolizasService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class ContCapitativoPolizasServiceImpl extends CrudServiceImpl<ContCapitativoPolizas, Long> implements ContCapitativoPolizasService {

    @Autowired
	public ContCapitativoPolizasServiceImpl(final ContCapitativoPolizasRep contCapitativoPolizasRep) {
		super(contCapitativoPolizasRep);
	}
}
