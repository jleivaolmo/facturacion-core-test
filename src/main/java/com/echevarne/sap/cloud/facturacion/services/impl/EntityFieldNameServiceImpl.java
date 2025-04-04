package com.echevarne.sap.cloud.facturacion.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.echevarne.sap.cloud.facturacion.model.entities.EntityFieldName;
import com.echevarne.sap.cloud.facturacion.repositories.EntityFieldNameRep;
import com.echevarne.sap.cloud.facturacion.services.EntityFieldNameService;

@Service("entityFieldNameSrv")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class EntityFieldNameServiceImpl extends CrudServiceImpl<EntityFieldName, Long> implements EntityFieldNameService {

	@Autowired
	public EntityFieldNameServiceImpl(final EntityFieldNameRep entityFieldNameRep){
		super(entityFieldNameRep);
	}

}
