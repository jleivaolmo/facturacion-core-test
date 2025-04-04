package com.echevarne.sap.cloud.facturacion.services.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.echevarne.sap.cloud.facturacion.model.facturacion.FacturaTrak;
import com.echevarne.sap.cloud.facturacion.repositories.FacturaTrakRep;
import com.echevarne.sap.cloud.facturacion.services.FacturaTrakService;

@Service("facturaTrakService")
public class FacturaTrakServiceImpl extends CrudServiceImpl<FacturaTrak, Long> implements FacturaTrakService {
	
	@SuppressWarnings("unused")
	private final FacturaTrakRep facturaTrakRep;

	public FacturaTrakServiceImpl(FacturaTrakRep facturaTrakRep) {
		super(facturaTrakRep);
		this.facturaTrakRep = facturaTrakRep;
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void setEstadoFacturaTrak(FacturaTrak facturaTrak, Integer estado) {
		facturaTrak.setEstado(estado);
		facturaTrakRep.save(facturaTrak);
	}

}
