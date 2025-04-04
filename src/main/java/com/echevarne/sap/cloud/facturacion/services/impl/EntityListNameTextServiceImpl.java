package com.echevarne.sap.cloud.facturacion.services.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.echevarne.sap.cloud.facturacion.model.entities.EntityListNameText;
import com.echevarne.sap.cloud.facturacion.repositories.EntityListNameTextRep;
import com.echevarne.sap.cloud.facturacion.services.EntityListNameTextService;

@Service("entityListNameTextSrv")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class EntityListNameTextServiceImpl extends CrudServiceImpl<EntityListNameText, Long> implements EntityListNameTextService {

	@Autowired
	public EntityListNameTextServiceImpl(final EntityListNameTextRep entityListNameRep){
		super(entityListNameRep);
	}
}
