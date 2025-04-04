package com.echevarne.sap.cloud.facturacion.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.echevarne.sap.cloud.facturacion.model.contratos.ContCapitativoInterlocutores;
import com.echevarne.sap.cloud.facturacion.repositories.ContCapitativoInterlocutoresRep;
import com.echevarne.sap.cloud.facturacion.services.ContCapitativoInterlocutoresService;

/**
 *
 * @author Germán Laso
 * @version 1.0
 *
 */
@Service("contCapitativoInterlocutoresService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class ContCapitativoInterlocutoresServiceImpl extends CrudServiceImpl<ContCapitativoInterlocutores, Long> implements ContCapitativoInterlocutoresService {

    @Autowired
	public ContCapitativoInterlocutoresServiceImpl(final ContCapitativoInterlocutoresRep contCapitativoInterlocutoresRep) {
		super(contCapitativoInterlocutoresRep);
	}
}
