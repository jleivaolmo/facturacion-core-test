package com.echevarne.sap.cloud.facturacion.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.echevarne.sap.cloud.facturacion.model.solicitudrecibida.SolMuesEstado;
import com.echevarne.sap.cloud.facturacion.repositories.SolMuesEstadoRepository;
import com.echevarne.sap.cloud.facturacion.services.SolMuesEstadoService;

@Service("solMuesEstadoSrv")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class SolMuesEstadoServiceImpl extends CrudServiceImpl<SolMuesEstado, Long> implements SolMuesEstadoService {

	@Autowired
	public SolMuesEstadoServiceImpl(final SolMuesEstadoRepository solMuesEstadoRepository){
		super(solMuesEstadoRepository);
	}
}
