package com.echevarne.sap.cloud.facturacion.services.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.echevarne.sap.cloud.facturacion.model.masterdata.MasDataEstadoLiquidacion;
import com.echevarne.sap.cloud.facturacion.repositories.MasDataEstadoLiquidacionRep;
import com.echevarne.sap.cloud.facturacion.services.MasDataEstadoLiquidacionService;
import com.echevarne.sap.cloud.facturacion.services.MasDataEstadoService;

/**
 * Business Service implementation of {@link MasDataEstadoService}
 *
 * @author Hernan Girardi
 * @since 24/04/2020
 */
@Service("masDataEstadoLiquidacionSrv")
public class MasDataEstadoLiquidacionServiceImpl extends CrudServiceImpl<MasDataEstadoLiquidacion, Long>
		implements MasDataEstadoLiquidacionService {

	private final MasDataEstadoLiquidacionRep masDataEstadoLiquidacionRep;

	@Autowired
	public MasDataEstadoLiquidacionServiceImpl(final MasDataEstadoLiquidacionRep masDataEstadoLiquidacionRep) {
		super(masDataEstadoLiquidacionRep);
		this.masDataEstadoLiquidacionRep = masDataEstadoLiquidacionRep;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = true)
	public MasDataEstadoLiquidacion getEstado(Integer codeEstado) {
		return findByCodeEstado(codeEstado);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = true)
	public MasDataEstadoLiquidacion findByCodeEstado(Integer codeEstado) {
		return this.masDataEstadoLiquidacionRep.findByCodigo(codeEstado);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = true)
	public List<MasDataEstadoLiquidacion> findByActive(boolean active) {
		return masDataEstadoLiquidacionRep.findByActive(active);
	}
}
