package com.echevarne.sap.cloud.facturacion.services;

import com.echevarne.sap.cloud.facturacion.model.facturacion.FacturaTrak;


public interface FacturaTrakService extends CrudService<FacturaTrak, Long> {
	
	public void setEstadoFacturaTrak(FacturaTrak facturaTrak, Integer estado);

}
