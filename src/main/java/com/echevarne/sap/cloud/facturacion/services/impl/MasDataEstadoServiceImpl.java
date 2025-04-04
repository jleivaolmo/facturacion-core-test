package com.echevarne.sap.cloud.facturacion.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.echevarne.sap.cloud.facturacion.model.masterdata.MasDataEstado;
import com.echevarne.sap.cloud.facturacion.repositories.MasDataEstadoRep;
import com.echevarne.sap.cloud.facturacion.services.MasDataEstadoService;

/**
 * Business Service implementation of {@link MasDataEstadoService}
 *
 * @author Hernan Girardi
 * @since 24/04/2020
 */
@Service("masDataEstadoSrv")
public class MasDataEstadoServiceImpl extends CrudServiceImpl<MasDataEstado, Long> implements MasDataEstadoService {

	private final MasDataEstadoRep masDataEstadoRep;

	@Autowired
	public MasDataEstadoServiceImpl(final MasDataEstadoRep masDataEstadoRep){
		super(masDataEstadoRep);
		this.masDataEstadoRep = masDataEstadoRep;
	}
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = true)
	public List<MasDataEstado> findByActive(boolean active) {
		return masDataEstadoRep.findByActive(active);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = true)
	public MasDataEstado findByCodeEstado(String codeEstado) {
		return this.masDataEstadoRep.findByCodeEstado(codeEstado);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = true)
	public List<String> findDistinctCodeEstadoByActive(boolean active) {
		List<String> codigosEstados = new ArrayList<>();
		List<MasDataEstado> estadosActivos = masDataEstadoRep.findByActive(active);
		if (CollectionUtils.isNotEmpty(estadosActivos)) {
			codigosEstados = estadosActivos.stream()
					.map(MasDataEstado::getCodeEstado)
					.distinct()
					.collect(Collectors.toList());
		}
		return codigosEstados;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = true)
	public MasDataEstado getEstado(String codeEstado) {
		return findByCodeEstado(codeEstado);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = true)
	public List<String> findDistinctCodeEstado() {
		List<String> codigosEstados = new ArrayList<>();
		List<MasDataEstado> estadosActivos = masDataEstadoRep.findAll();
		if (CollectionUtils.isNotEmpty(estadosActivos)) {
			codigosEstados = estadosActivos.stream()
					.map(MasDataEstado::getCodeEstado)
					.distinct()
					.collect(Collectors.toList());
		}
		return codigosEstados;
	}
}
