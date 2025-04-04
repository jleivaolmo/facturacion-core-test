package com.echevarne.sap.cloud.facturacion.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.echevarne.sap.cloud.facturacion.model.entities.EntityFieldNameText;
import com.echevarne.sap.cloud.facturacion.repositories.EntityFieldNameTextRep;
import com.echevarne.sap.cloud.facturacion.services.EntityFieldNameTextService;

/**
 *
 * @author Steven Ricardo Mendez Brenes
 * @version 1.0
 *
 */
@Service("entityFieldNameTextSrv")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class EntityFieldNameTextServiceImpl extends CrudServiceImpl<EntityFieldNameText, Long> implements EntityFieldNameTextService {

	@Autowired
	public EntityFieldNameTextServiceImpl(final EntityFieldNameTextRep entityFieldNameTextRep) {
		super(entityFieldNameTextRep);
	}
}
