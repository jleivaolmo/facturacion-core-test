package com.echevarne.sap.cloud.facturacion.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.echevarne.sap.cloud.facturacion.model.logAuditoria.LogAuditoriaFacturacion;
import com.echevarne.sap.cloud.facturacion.repositories.LogAuditoriaFacturacionRepository;
import com.echevarne.sap.cloud.facturacion.services.LogAuditoriaFacturacionService;

@Service("logAuditoriaFacturacionSrv")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class LogAuditoriaFacturacionServiceImpl extends CrudServiceImpl<LogAuditoriaFacturacion, Long>
		implements LogAuditoriaFacturacionService {

	private final LogAuditoriaFacturacionRepository logAuditoriaFacturacionRepository;

	@Autowired
	public LogAuditoriaFacturacionServiceImpl(LogAuditoriaFacturacionRepository logAuditoriaFacturacionRepository) {
		super(logAuditoriaFacturacionRepository);
		this.logAuditoriaFacturacionRepository = logAuditoriaFacturacionRepository;
	}
}
