package com.echevarne.sap.cloud.facturacion.services.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.echevarne.sap.cloud.facturacion.model.masterdata.MasDataAlerta;
import com.echevarne.sap.cloud.facturacion.repositories.MasDataAlertaRep;
import com.echevarne.sap.cloud.facturacion.services.MasDataAlertaService;
import com.echevarne.sap.cloud.facturacion.util.DateUtils;
import lombok.var;

/**
 * Business Service implementation of {@link MasDataAlertaService}
 */
@Service("masDataAlertaSrv")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class MasDataAlertaServiceImpl extends CrudServiceImpl<MasDataAlerta, Long> implements MasDataAlertaService {

	private final MasDataAlertaRep masDataAlertaRep;

	@Autowired
	public MasDataAlertaServiceImpl(final MasDataAlertaRep masDataAlertaRep){
		super(masDataAlertaRep);
		this.masDataAlertaRep = masDataAlertaRep;
	}

	@Override
	public List<MasDataAlerta> findByActive(boolean active) {
		return masDataAlertaRep.findByActive(active);
	}

	@Override
	public Optional<MasDataAlerta> findByCodigoAlerta(String codigoAlerta) {
		return masDataAlertaRep.findByCodigoAlertaAndActive(codigoAlerta,true);
	}

	@Override
	public List<MasDataAlerta> findDistinctAlertasBloquean(Date fechaPeticion) {
		var list = masDataAlertaRep.findByBloqueaAndActive(true, true);
		var fechaActual = DateUtils.today();
		long days = DateUtils.getDaysBetweenDates(fechaPeticion, fechaActual);
		var filteredList = list.stream().filter(a -> a.getDiasVigencia() == null || days < a.getDiasVigencia()).collect(Collectors.toList());
		return filteredList;
	}

	@Override
	public List<MasDataAlerta> findDistinctAlertasPrivados() {
		return masDataAlertaRep.findByTratablePrivadosAndActive(true, true);
	}

	@Override
	public void reloadData() {}
}
