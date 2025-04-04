package com.echevarne.sap.cloud.facturacion.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.echevarne.sap.cloud.facturacion.model.solicitudrecibida.PetMuesPaciente;
import com.echevarne.sap.cloud.facturacion.repositories.PetMuesPacienteRep;
import com.echevarne.sap.cloud.facturacion.services.PetMuesPacienteService;

@Service("petMuesPacienteSrv")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class PetMuesPacienteServiceImpl extends CrudServiceImpl<PetMuesPaciente, Long> implements PetMuesPacienteService {
	@Autowired
	public PetMuesPacienteServiceImpl(final PetMuesPacienteRep petMuesPacienteRep){
		super(petMuesPacienteRep);
	}
}
