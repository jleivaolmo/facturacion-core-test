package com.echevarne.sap.cloud.facturacion.odata.processor;

import com.echevarne.sap.cloud.facturacion.model.BasicEntity;

public interface ODataExits {

	BasicEntity postBatch();
	BasicEntity createEntity(BasicEntity entity);
	BasicEntity updateEntity(BasicEntity entity);
	BasicEntity getTargetEntity();
	
}
