package com.echevarne.sap.cloud.facturacion.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.echevarne.sap.cloud.facturacion.model.masterdata.MasDataTipoPosicion;
import com.echevarne.sap.cloud.facturacion.repositories.MasDataTipoPosicionRep;
import com.echevarne.sap.cloud.facturacion.services.MasDataTipoPosicionService;

/**
 * Business Service implementation of {@link MasDataTipoPosicionService}
 */

@Service("masDataTipoPosicionSrv")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class MasDataTipoPosicionServiceImpl extends CrudServiceImpl<MasDataTipoPosicion, Long>
		implements MasDataTipoPosicionService {

	private final MasDataTipoPosicionRep masDataTipoPosicionRep;

	@Autowired
	public MasDataTipoPosicionServiceImpl(final MasDataTipoPosicionRep masDataTipoPosicionRep) {
		super(masDataTipoPosicionRep);
		this.masDataTipoPosicionRep = masDataTipoPosicionRep;
	}

	@Override
	public List<MasDataTipoPosicion> findByActive(boolean active) {
		return masDataTipoPosicionRep.findByActive(active);
	}

	@Override
	public Optional<MasDataTipoPosicion> findByTipoPosicion(String tipoPosicion) {
		return masDataTipoPosicionRep.findByTipoPosicion(tipoPosicion);
	}

}
