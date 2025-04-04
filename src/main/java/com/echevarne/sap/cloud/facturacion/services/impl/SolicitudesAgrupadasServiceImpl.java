package com.echevarne.sap.cloud.facturacion.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.echevarne.sap.cloud.facturacion.model.solicitudagrupada.SolicitudesAgrupadas;
import com.echevarne.sap.cloud.facturacion.repositories.SolicitudesAgrupadasRep;
import com.echevarne.sap.cloud.facturacion.services.SolicitudesAgrupadasService;

import java.util.List;
import java.util.Optional;

@Service("solicitudesAgrupadasSrv")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class SolicitudesAgrupadasServiceImpl extends CrudServiceImpl<SolicitudesAgrupadas, Long>
		implements SolicitudesAgrupadasService {

	private final SolicitudesAgrupadasRep solicitudesAgrupadasRep;


	@Autowired
	public SolicitudesAgrupadasServiceImpl(final SolicitudesAgrupadasRep solicitudesAgrupadasRep){
		super(solicitudesAgrupadasRep);
		this.solicitudesAgrupadasRep = solicitudesAgrupadasRep;
	}

	@Override
	public Optional<List<SolicitudesAgrupadas>> findByBillingDocument(String billingDocument) {
		return solicitudesAgrupadasRep.findByBillingDocument(billingDocument);
	}

	@Override
	public Optional<List<SolicitudesAgrupadas>> findByBillingDocumentAndSoldToParty(String billingDocument,
			String soldToParty) {
		return solicitudesAgrupadasRep.findByBillingDocumentAndSoldToParty(billingDocument, soldToParty);
	}

	@Override
	public Optional<List<SolicitudesAgrupadas>> findBySalesOrderType(String salesOrderType) {
		return solicitudesAgrupadasRep.findBySalesOrderType(salesOrderType);
	}
}
