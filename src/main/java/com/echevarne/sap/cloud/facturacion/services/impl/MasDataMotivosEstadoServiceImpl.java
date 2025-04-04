package com.echevarne.sap.cloud.facturacion.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.echevarne.sap.cloud.facturacion.model.masterdata.MasDataAlerta;
import com.echevarne.sap.cloud.facturacion.model.masterdata.MasDataMotivosEstado;
import com.echevarne.sap.cloud.facturacion.repositories.MasDataMotivosEstadoRep;
import com.echevarne.sap.cloud.facturacion.services.MasDataMotivosEstadoService;

/**
 * Business Service implementation of {@link MasDataMotivosEstadoService}
 */
@Service("masDataMotivosEstadoSrv")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class MasDataMotivosEstadoServiceImpl extends CrudServiceImpl<MasDataMotivosEstado, Long> implements MasDataMotivosEstadoService {

	private final MasDataMotivosEstadoRep masDataMotivosRep;

	@Autowired
	public MasDataMotivosEstadoServiceImpl(final MasDataMotivosEstadoRep masDataMotivosRep){
		super(masDataMotivosRep);
		this.masDataMotivosRep = masDataMotivosRep;
	}
	@Override
	public List<MasDataMotivosEstado> findByActive(boolean active) {
		return masDataMotivosRep.findByActive(active);
	}

	@Override
	public Optional<MasDataMotivosEstado> findByAlerta(MasDataAlerta alerta) {
		return masDataMotivosRep.findByAlertaAndActive(alerta, true);
	}

	@Override
	public Optional<MasDataMotivosEstado> findByAlertaCodigoAlerta(String codigoAlerta) {
		return masDataMotivosRep.findByAlertaCodigoAlertaAndActive(codigoAlerta, true);
	}

	@Override
	public List<MasDataMotivosEstado> findByDescripcionContains(String descripciontoFind) {
		return masDataMotivosRep.findByDescripcionContains(descripciontoFind);
	}

	/**
	 *
	 * @return {@code List<MasDataMotivosEstado>} WHERE descripcion Like '%descripcionToFind%' AND alerta = alerta OR descripcion Like '%descripcionToFind%' AND alerta is NULL, Si {@code alerta == null}
	 * @param
	 * 		alerta allows null
	 * @param
	 * 		descripcionToFind performed as LIKE '%descripcionToFind%'
	 **/
	@Override
	public List<MasDataMotivosEstado> findByAlertaAndDescripcionContains(MasDataAlerta alerta,String descripcionToFind) {
		return masDataMotivosRep.findByAlertaAndDescripcionContainsAndActive(alerta, descripcionToFind, true);
	}

	@Override
	public Optional<MasDataMotivosEstado> findByCodigoAndActive(String codigo, boolean active) {
		return masDataMotivosRep.findByCodigoAndActive(codigo, active);
	}
}
