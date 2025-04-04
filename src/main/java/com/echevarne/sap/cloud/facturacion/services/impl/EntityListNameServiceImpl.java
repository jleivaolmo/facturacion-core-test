package com.echevarne.sap.cloud.facturacion.services.impl;

import com.echevarne.sap.cloud.facturacion.repositories.dto.NombreEntidadAndFieldNameAndText;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.echevarne.sap.cloud.facturacion.model.entities.EntityListName;
import com.echevarne.sap.cloud.facturacion.repositories.EntityListNameRep;
import com.echevarne.sap.cloud.facturacion.services.EntityListNameService;

import java.util.List;

@Service("entityListNameSrv")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class EntityListNameServiceImpl extends CrudServiceImpl<EntityListName, Long> implements EntityListNameService {

	private final EntityListNameRep entityListNameRep;

	@Autowired
	public EntityListNameServiceImpl(final EntityListNameRep entityListNameRep){
		super(entityListNameRep);
		this.entityListNameRep = entityListNameRep;
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	@Override
	public List<NombreEntidadAndFieldNameAndText> findAllNombreEntidadAndFieldNameAndText(String idioma) {
		return entityListNameRep.findAllNombreEntidadAndFieldNameAndText(idioma);
	}
}
