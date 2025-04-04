package com.echevarne.sap.cloud.facturacion.services.impl;

import com.echevarne.sap.cloud.facturacion.model.masterdata.MasDataEstadosGrupo;
import com.echevarne.sap.cloud.facturacion.repositories.MasDataEstadosGrupoRep;
import com.echevarne.sap.cloud.facturacion.services.MasDataEstadosGrupoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Business Service implementation of {@link MasDataEstadosGrupoService}
 */
@Service("masDataEstadosGrupoSrv")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class MasDataEstadosGrupoServiceImpl extends CrudServiceImpl<MasDataEstadosGrupo, Long> implements MasDataEstadosGrupoService {

	private final MasDataEstadosGrupoRep repo;

	@Autowired
	public MasDataEstadosGrupoServiceImpl(final MasDataEstadosGrupoRep repo){
		super(repo);
		this.repo = repo;
	}

	@Override
	public MasDataEstadosGrupo findByCodigo(String codigo) {
		return repo.findByCodigo(codigo);
	}

	@Override
	public List<MasDataEstadosGrupo> findByActive(boolean active) {
		return repo.findByActive(active);
	}

}
