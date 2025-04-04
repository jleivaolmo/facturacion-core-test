package com.echevarne.sap.cloud.facturacion.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.echevarne.sap.cloud.facturacion.model.incongruentes.IncongMat;
import com.echevarne.sap.cloud.facturacion.repositories.IncongMatRep;
import com.echevarne.sap.cloud.facturacion.services.IncongMatService;

@Service("incongMatSrv")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class IncongMatServiceImpl extends CrudServiceImpl<IncongMat, Long>  implements IncongMatService {

	@Autowired
	public IncongMatServiceImpl(final IncongMatRep incongMatRep){
		super(incongMatRep);
	}
}
