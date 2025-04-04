package com.echevarne.sap.cloud.facturacion.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.echevarne.sap.cloud.facturacion.model.incongruentes.IncongSec;
import com.echevarne.sap.cloud.facturacion.repositories.IncongSecRep;
import com.echevarne.sap.cloud.facturacion.services.IncongSecService;

@Service("incongSecSrv")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class IncongSecServiceImpl extends CrudServiceImpl<IncongSec, Long>  implements IncongSecService {

	@Autowired
	public IncongSecServiceImpl(final IncongSecRep incongSecRep) {
		super(incongSecRep);
	}

}
