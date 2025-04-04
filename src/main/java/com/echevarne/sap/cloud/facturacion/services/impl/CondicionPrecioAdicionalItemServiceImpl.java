package com.echevarne.sap.cloud.facturacion.services.impl;

import java.util.Optional;

import com.echevarne.sap.cloud.facturacion.model.privados.CondicionPrecioAdicionalItem;
import com.echevarne.sap.cloud.facturacion.model.trazabilidad.Trazabilidad;
import com.echevarne.sap.cloud.facturacion.repositories.CondicionPrecioAdicionalItemRep;
import com.echevarne.sap.cloud.facturacion.services.CondicionPrecioAdicionalItemService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("condicionPrecioAdicionalItemService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class CondicionPrecioAdicionalItemServiceImpl extends CrudServiceImpl<CondicionPrecioAdicionalItem, Long>
		implements CondicionPrecioAdicionalItemService {

	private final CondicionPrecioAdicionalItemRep repo;

	@Autowired
	public CondicionPrecioAdicionalItemServiceImpl(final CondicionPrecioAdicionalItemRep repo){
		super(repo);
		this.repo = repo;
	}

	@Override
	public Optional<CondicionPrecioAdicionalItem> findByTrazabilidadAndConditionType(Trazabilidad trazabilidad, String conditionType) {
		return repo.findByTrazabilidadAndConditionType(trazabilidad, conditionType);
	}
}
