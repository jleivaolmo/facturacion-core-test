package com.echevarne.sap.cloud.facturacion.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.echevarne.sap.cloud.facturacion.model.masterdata.MasDataProcessControlStatus;
import com.echevarne.sap.cloud.facturacion.repositories.MasDataProcessControlStatusRep;
import com.echevarne.sap.cloud.facturacion.services.MasDataProcessControlStatusService;

/**
 * Business Service implementation of {@link MasDataProcessControlStatusService}
 */
@Service("masDataProcessControlStatusSrv")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class MasDataProcessControlStatusServiceImpl extends CrudServiceImpl<MasDataProcessControlStatus, Long>
        implements MasDataProcessControlStatusService {

	private final MasDataProcessControlStatusRep masDataProcessControlStatusRep;

	@Autowired
	public MasDataProcessControlStatusServiceImpl(final MasDataProcessControlStatusRep masDataProcessControlStatusRep){
		super(masDataProcessControlStatusRep);
		this.masDataProcessControlStatusRep = masDataProcessControlStatusRep;
	}

	@Override
	public List<MasDataProcessControlStatus> findByActive(boolean active) {
		return masDataProcessControlStatusRep.findByActive(active);
	}

	@Override
	public MasDataProcessControlStatus findByCodeStatus(String codeStatus) {
		return masDataProcessControlStatusRep.findByCodeStatus(codeStatus);
	}
}
