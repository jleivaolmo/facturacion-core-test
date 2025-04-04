package com.echevarne.sap.cloud.facturacion.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.echevarne.sap.cloud.facturacion.model.contratos.ContCapitativoProvRemitente;
import com.echevarne.sap.cloud.facturacion.repositories.ContCapitativoProvRemitenteRep;
import com.echevarne.sap.cloud.facturacion.services.ContCapitativoProvRemitenteService;

/**
 *
 * @author Germán Laso
 * @version 1.0
 *
 */
@Service("contCapitativoProvRemitenteService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class ContCapitativoProvRemitenteServiceImpl extends CrudServiceImpl<ContCapitativoProvRemitente, Long> implements ContCapitativoProvRemitenteService {

    @Autowired
	public ContCapitativoProvRemitenteServiceImpl(final ContCapitativoProvRemitenteRep contCapitativoProvRemitenteRep) {
		super(contCapitativoProvRemitenteRep);
	}
}
