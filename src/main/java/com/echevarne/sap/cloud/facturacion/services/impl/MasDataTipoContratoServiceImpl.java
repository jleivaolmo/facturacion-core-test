package com.echevarne.sap.cloud.facturacion.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.echevarne.sap.cloud.facturacion.model.masterdata.MasDataTipoContrato;
import com.echevarne.sap.cloud.facturacion.repositories.MasDataTipoContratoRep;
import com.echevarne.sap.cloud.facturacion.services.MasDataTipoContratoService;
import com.echevarne.sap.cloud.facturacion.services.MasDataTipoPosicionService;

/**
 * Business Service implementation of {@link MasDataTipoPosicionService}
 */
@Service("masDataTipoContratoSrv")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class MasDataTipoContratoServiceImpl extends CrudServiceImpl<MasDataTipoContrato, Long>
		implements MasDataTipoContratoService {

	private final MasDataTipoContratoRep masDataTipoContratoRep;

	@Autowired
	public MasDataTipoContratoServiceImpl(final MasDataTipoContratoRep masDataTipoContratoRep) {
		super(masDataTipoContratoRep);
		this.masDataTipoContratoRep = masDataTipoContratoRep;
	}

	@Override
	public List<MasDataTipoContrato> findByActive(boolean active) {
		return masDataTipoContratoRep.findByActive(active);
	}

}
