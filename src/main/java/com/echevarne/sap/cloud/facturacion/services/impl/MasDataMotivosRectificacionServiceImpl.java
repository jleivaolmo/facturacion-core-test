package com.echevarne.sap.cloud.facturacion.services.impl;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.echevarne.sap.cloud.facturacion.model.masterdata.MasDataMotivosRectificacion;
import com.echevarne.sap.cloud.facturacion.repositories.MasDataMotivosRectificacionRep;
import com.echevarne.sap.cloud.facturacion.services.MasDataMotivosRectificacionService;

/**
 * Business Service implementation of {@link MasDataMotivosRectificacionService}
 */
@Service("masDataMotivosRectificacionSrv")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class MasDataMotivosRectificacionServiceImpl extends CrudServiceImpl<MasDataMotivosRectificacion, Long>
		implements MasDataMotivosRectificacionService {

	private final MasDataMotivosRectificacionRep masDataMotivosRectificacionRep;

	@Autowired
	public MasDataMotivosRectificacionServiceImpl(final MasDataMotivosRectificacionRep masDataMotivosRectificacionRep){
		super(masDataMotivosRectificacionRep);
		this.masDataMotivosRectificacionRep = masDataMotivosRectificacionRep;
	}

	@Override
	public List<MasDataMotivosRectificacion> findByActive(boolean active) {
		return masDataMotivosRectificacionRep.findByActive(active);
	}

	@Override
	public Optional<MasDataMotivosRectificacion> findByCodigo(String codigo) {
		return masDataMotivosRectificacionRep.findByCodigoAndActive(codigo, true);
	}
}
