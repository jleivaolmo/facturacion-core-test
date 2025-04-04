package com.echevarne.sap.cloud.facturacion.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.echevarne.sap.cloud.facturacion.model.masterdata.MasDataTipoPeticion;
import com.echevarne.sap.cloud.facturacion.repositories.MasDataTipoPeticionRep;
import com.echevarne.sap.cloud.facturacion.services.MasDataTipoPeticionService;

/**
 * Business Service implementation of {@link MasDataTipoPeticionService}
 */
@Service("masDataTipoPeticionSrv")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class MasDataTipoPeticionServiceImpl extends CrudServiceImpl<MasDataTipoPeticion, Long>
		implements MasDataTipoPeticionService {

	private final MasDataTipoPeticionRep masDataTipoPeticionRep;

	@Autowired
	public MasDataTipoPeticionServiceImpl(MasDataTipoPeticionRep masDataTipoPeticionRep) {
		super(masDataTipoPeticionRep);
		this.masDataTipoPeticionRep = masDataTipoPeticionRep;
	}

	@Override
	public List<MasDataTipoPeticion> findByActive(boolean active) {
		return masDataTipoPeticionRep.findByActive(active);
	}
}
