package com.echevarne.sap.cloud.facturacion.services.impl;

import com.echevarne.sap.cloud.facturacion.model.conversion.ConversionOficinaVentas;
import com.echevarne.sap.cloud.facturacion.repositories.ConversionOficinaVentasRep;
import com.echevarne.sap.cloud.facturacion.services.ConversionOficinaVentasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class ConversionOficinaVentasServiceImpl extends CrudServiceImpl<ConversionOficinaVentas, Long>
		implements ConversionOficinaVentasService {

	private final ConversionOficinaVentasRep repo;

	@Autowired
	public ConversionOficinaVentasServiceImpl(final ConversionOficinaVentasRep repo) {
		super(repo);
		this.repo = repo;
	}

	@Override
	public Optional<ConversionOficinaVentas> findByTrak(String trak) {
		return repo.findByTrak(trak);
	}

}
