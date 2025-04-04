package com.echevarne.sap.cloud.facturacion.services.impl;

import com.echevarne.sap.cloud.facturacion.model.conversion.ConversionSector;
import com.echevarne.sap.cloud.facturacion.repositories.ConversionSectorRep;
import com.echevarne.sap.cloud.facturacion.services.ConversionSectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class ConversionSectorServiceImpl extends CrudServiceImpl<ConversionSector, Long>
		implements ConversionSectorService {

	private final ConversionSectorRep repo;
	@Autowired
	public ConversionSectorServiceImpl(final ConversionSectorRep repo){
		super(repo);
		this.repo = repo;
	}

	@Override
	public Optional<ConversionSector> findByTipoPeticion(int tipoPeticion) {
		return repo.findByTipoPeticion(tipoPeticion);
	}

}
