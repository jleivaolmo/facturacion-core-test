package com.echevarne.sap.cloud.facturacion.services.impl;

import java.util.List;
import java.util.Optional;

import com.echevarne.sap.cloud.facturacion.model.masterdata.MasDataMessagesGrupo;
import com.echevarne.sap.cloud.facturacion.repositories.MasDataMessagesGrupoRep;
import com.echevarne.sap.cloud.facturacion.services.MasDataMessagesGrupoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("masDataMessagesGrupoSrv")
//@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class MasDataMessagesGrupoServiceImpl extends CrudServiceImpl<MasDataMessagesGrupo, Long> implements MasDataMessagesGrupoService {

	private final MasDataMessagesGrupoRep repo;

	@Autowired
	public MasDataMessagesGrupoServiceImpl(MasDataMessagesGrupoRep repo) {
		super(repo);
		this.repo = repo;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public Optional<MasDataMessagesGrupo> findByCodeGrupo(String codeGrupo) {
		return repo.findByCodeGrupo(codeGrupo);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<MasDataMessagesGrupo> findByActive(boolean active) {
		return repo.findByActive(active);
	}
}
