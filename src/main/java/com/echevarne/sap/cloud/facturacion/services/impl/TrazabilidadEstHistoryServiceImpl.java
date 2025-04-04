package com.echevarne.sap.cloud.facturacion.services.impl;

import java.util.Optional;
import javax.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.echevarne.sap.cloud.facturacion.model.masterdata.MasDataEstado;
import com.echevarne.sap.cloud.facturacion.model.trazabilidad.TrazabilidadEstHistory;
import com.echevarne.sap.cloud.facturacion.repositories.TrazabilidadEstHistoryRep;
import com.echevarne.sap.cloud.facturacion.services.TrazabilidadEstHistoryService;
import lombok.var;

@Service("trazabilidadEstHistorySrv")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class TrazabilidadEstHistoryServiceImpl extends CrudServiceImpl<TrazabilidadEstHistory, Long> implements TrazabilidadEstHistoryService  {

	private final TrazabilidadEstHistoryRep repo;
	
	@Autowired
	EntityManager entityManager;

	@Autowired
	public TrazabilidadEstHistoryServiceImpl(final TrazabilidadEstHistoryRep repo) {
		super(repo);
		this.repo = repo;
	}

	public Optional<TrazabilidadEstHistory> findByEstado(MasDataEstado estado){
		return repo.findByEstado(estado);
	}
	
	@Override
	public Integer insertTrazabilidadEstHistory(
            String userCreate,
            boolean automatico,
            String motivoVar1,
            String motivoVar2,
            String motivoVar3,
            String motivoVar4,
            Long fkEstado,
            Long fkMotivo,
            Long fkTrazabilidad) {
        
        var query = entityManager.createNativeQuery(
            "CALL INSERT_TRAZABILIDADESTHISTORY(?, ?, ?, ?, ?, ?, ?, ?, ?)"
        );
        query.setParameter(1, userCreate);
        query.setParameter(2, automatico);
        query.setParameter(3, motivoVar1);
        query.setParameter(4, motivoVar2);
        query.setParameter(5, motivoVar3);
        query.setParameter(6, motivoVar4);
        query.setParameter(7, fkEstado);
        query.setParameter(8, fkMotivo);
        query.setParameter(9, fkTrazabilidad);
 
        return query.executeUpdate();
    }
}
