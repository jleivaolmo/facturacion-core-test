package com.echevarne.sap.cloud.facturacion.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.echevarne.sap.cloud.facturacion.model.incongruentes.IncongCond;
import com.echevarne.sap.cloud.facturacion.repositories.IncongCondRep;
import com.echevarne.sap.cloud.facturacion.services.IncongCondService;

@Service("incongCondSrv")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class IncongCondServiceImpl  extends CrudServiceImpl<IncongCond, Long>  implements IncongCondService {

	@Autowired
	public IncongCondServiceImpl(final IncongCondRep incongCondRep) {
		super(incongCondRep);
	}

}
