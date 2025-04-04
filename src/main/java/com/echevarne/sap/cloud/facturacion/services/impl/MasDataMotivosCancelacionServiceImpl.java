package com.echevarne.sap.cloud.facturacion.services.impl;

import java.util.List;
import java.util.Optional;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.echevarne.sap.cloud.facturacion.model.masterdata.MasDataMotivosCancelacion;
import com.echevarne.sap.cloud.facturacion.repositories.MasDataMotivosCancelacionRep;
import com.echevarne.sap.cloud.facturacion.services.MasDataMotivosCancelacionService;

/**
 * Business Service implementation of {@link MasDataMotivosCancelacionService}
 */
@Service("masDataMotivosCancelacionSrv")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class MasDataMotivosCancelacionServiceImpl extends CrudServiceImpl<MasDataMotivosCancelacion, Long>
		implements MasDataMotivosCancelacionService {

	private final MasDataMotivosCancelacionRep masDataMotivosCancelacionRep;

	@Autowired
	public MasDataMotivosCancelacionServiceImpl(MasDataMotivosCancelacionRep masDataMotivosCancelacionRep) {
		super(masDataMotivosCancelacionRep);
		this.masDataMotivosCancelacionRep = masDataMotivosCancelacionRep;
	}

	@Override
	public List<MasDataMotivosCancelacion> findByActive(boolean active) {
		return masDataMotivosCancelacionRep.findByActive(active);
	}

	@Override
	public Optional<MasDataMotivosCancelacion> findByCodigo(String codigo) {
		return masDataMotivosCancelacionRep.findByCodigoAndActive(codigo, true);
	}
}
