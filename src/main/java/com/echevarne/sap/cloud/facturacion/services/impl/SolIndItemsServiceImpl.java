package com.echevarne.sap.cloud.facturacion.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.echevarne.sap.cloud.facturacion.model.solicitudindividual.SolIndItems;
import com.echevarne.sap.cloud.facturacion.model.solicitudindividual.SolicitudIndividual;
import com.echevarne.sap.cloud.facturacion.repositories.SolIndItemsRepository;
import com.echevarne.sap.cloud.facturacion.services.SolIndItemsService;

import java.util.List;

@Service("solIndItemsService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class SolIndItemsServiceImpl extends CrudServiceImpl<SolIndItems, Long> implements SolIndItemsService {

    private final SolIndItemsRepository repo;

    @Autowired
    public SolIndItemsServiceImpl(final SolIndItemsRepository repo){
        super(repo);
        this.repo = repo;
    }

    @Override
    public List<SolIndItems> findAllByIdWithTrazabilidadAndSolicitudIndAndItsTrazabilidad(final List<Long> idSolItems) {
        return repo.findAllByIdWithTrazabilidadAndSolicitudIndAndItsTrazabilidad(idSolItems);
	}

	@Override
	public List<SolIndItems> findAllBySolicitudIndAndHigherLevelltemAndSalesOrderItemCategory(SolicitudIndividual si, int higherLevelItem,
			String salesOrderItemCategory) {
		return repo.findAllBySolicitudIndAndHigherLevelltemAndSalesOrderItemCategory(si, higherLevelItem, salesOrderItemCategory);
	}
}
