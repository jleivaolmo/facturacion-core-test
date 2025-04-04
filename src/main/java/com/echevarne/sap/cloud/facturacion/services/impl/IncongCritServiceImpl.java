package com.echevarne.sap.cloud.facturacion.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.echevarne.sap.cloud.facturacion.model.incongruentes.IncongCrit;
import com.echevarne.sap.cloud.facturacion.repositories.IncongCritRep;
import com.echevarne.sap.cloud.facturacion.services.IncongCritService;

@Service("incongCritSrv")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class IncongCritServiceImpl extends CrudServiceImpl<IncongCrit, Long>  implements IncongCritService {

	@Autowired
	public IncongCritServiceImpl(final IncongCritRep incongCritRep){
		super(incongCritRep);
	}

}
