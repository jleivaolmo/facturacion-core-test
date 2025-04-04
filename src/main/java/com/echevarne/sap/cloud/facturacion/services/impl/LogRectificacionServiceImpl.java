package com.echevarne.sap.cloud.facturacion.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.echevarne.sap.cloud.facturacion.model.facturacion.LogRectificacion;
import com.echevarne.sap.cloud.facturacion.repositories.LogRectificacionRep;
import com.echevarne.sap.cloud.facturacion.services.LogRectificacionService;

@Service("logRectificacionService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class LogRectificacionServiceImpl extends CrudServiceImpl<LogRectificacion, Long> implements LogRectificacionService {

	private final LogRectificacionRep rep;

	@Autowired
	public LogRectificacionServiceImpl(LogRectificacionRep rep) {
		super(rep);
		this.rep = rep;
	}
}
