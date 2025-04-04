package com.echevarne.sap.cloud.facturacion.services.impl;

import java.util.List;

import com.echevarne.sap.cloud.facturacion.model.transformacion.Trans;
import com.echevarne.sap.cloud.facturacion.repositories.TransRepository;
import com.echevarne.sap.cloud.facturacion.services.TransService;
import com.echevarne.sap.cloud.facturacion.transformacion.rules.TransformerCallProcessor;
import com.echevarne.sap.cloud.facturacion.transformacion.rules.TransformerFunctions;
import com.echevarne.sap.cloud.facturacion.transformacion.rules.TransformerProcessor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


/**
 * The Class TransServicioImpl.
 */
@Service("transSrv")
public class TransServicioImpl extends CrudServiceImpl<Trans, Long> implements TransService {

	private final TransRepository transRepository;
	private final TransformerFunctions transformerFunctions;
	private final TransformerCallProcessor transformerCallProcessor;

	@Autowired
	public TransServicioImpl(
			final TransRepository transRepository,
			final TransformerFunctions transformerFunctions,
			final TransformerCallProcessor transformerCallProcessor
	){
		super(transRepository);
		this.transformerFunctions = transformerFunctions;
		this.transformerCallProcessor = transformerCallProcessor;
		this.transRepository = transRepository;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = true)
	public TransformerProcessor getTransformerProcessorWithTransList(final TransformerFunctions customFunctions) {
		final TransformerProcessor transformer = new TransformerProcessor(transformerFunctions, customFunctions, transformerCallProcessor);
		transformer.setTrans(transRepository.findAllWithRulesAndCriterias());
		return transformer;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = true)
	public List<Trans> getAll() {
		return transRepository.findAll();
	}
}
