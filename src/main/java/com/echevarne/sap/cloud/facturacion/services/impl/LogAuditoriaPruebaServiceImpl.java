package com.echevarne.sap.cloud.facturacion.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.echevarne.sap.cloud.facturacion.model.logAuditoria.LogAuditoriaPrueba;
import com.echevarne.sap.cloud.facturacion.repositories.LogAuditoriaPruebaRepository;
import com.echevarne.sap.cloud.facturacion.services.LogAuditoriaPruebaService;

@Service("logAuditoriaPruebaSrv")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class LogAuditoriaPruebaServiceImpl extends CrudServiceImpl<LogAuditoriaPrueba, Long>
		implements LogAuditoriaPruebaService {

	private final LogAuditoriaPruebaRepository logAuditoriaPruebaRepository;

	@Autowired
	public LogAuditoriaPruebaServiceImpl(LogAuditoriaPruebaRepository logAuditoriaPruebaRepository) {
		super(logAuditoriaPruebaRepository);
		this.logAuditoriaPruebaRepository = logAuditoriaPruebaRepository;
	}
}
