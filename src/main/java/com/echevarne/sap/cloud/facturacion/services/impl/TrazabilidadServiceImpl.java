package com.echevarne.sap.cloud.facturacion.services.impl;

import com.echevarne.sap.cloud.facturacion.model.solicitudrecibida.PeticionMuestreoItems;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.echevarne.sap.cloud.facturacion.model.trazabilidad.Trazabilidad;
import com.echevarne.sap.cloud.facturacion.repositories.TrazabilidadRepository;
import com.echevarne.sap.cloud.facturacion.services.TrazabilidadService;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;


@Service("trazabilidadService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class TrazabilidadServiceImpl extends CrudServiceImpl<Trazabilidad, Long> implements TrazabilidadService {
	
	@Autowired
	private EntityManager entityManager;

    private final TrazabilidadRepository repo;

    @Autowired
    public TrazabilidadServiceImpl(final TrazabilidadRepository repo){
        super(repo);
        this.repo = repo;
    }

	@Override
	public int updateTrazabilidaSetItemAgr(Long idItem, Long idTrz) {
		return repo.updateTrazabilidaSetFKItemAgrForTrzIdNative(idItem, idTrz);
	}

	@Override
	public Optional<Trazabilidad> findByItemRec(PeticionMuestreoItems item) {
        return repo.findByItemRec(item);
    }

    @Override
    public List<Trazabilidad> getByBillingDocument(String billingDocument) {
        return repo.findAllByBillingDocument(billingDocument).orElse(new ArrayList<>());
    }
    
    @Override
    public Long getCountBySalesOrder(String salesOrder) {
    	String sql = "SELECT COUNT (t.ID) FROM T_TRAZABILIDAD t WHERE t.SALESORDER='" + salesOrder + "'";
		BigInteger responseBint = (BigInteger) entityManager.createNativeQuery(sql).getSingleResult();
		return responseBint.longValue();
    }

}
