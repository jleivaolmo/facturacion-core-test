package com.echevarne.sap.cloud.facturacion.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.echevarne.sap.cloud.facturacion.model.entities.EntityFieldProductionType;
import com.echevarne.sap.cloud.facturacion.repositories.EntityFieldProductionTypeRep;
import com.echevarne.sap.cloud.facturacion.services.EntityFieldProductionTypeService;

@Service("entityFieldProductionTypeSrv")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class EntityFieldProductionTypeServiceImpl extends CrudServiceImpl<EntityFieldProductionType, Long>
		implements EntityFieldProductionTypeService {

	@Autowired
	private EntityFieldProductionTypeServiceImpl(final EntityFieldProductionTypeRep entityFieldProductionTypeRep){
		super(entityFieldProductionTypeRep);
	}
}
