package com.echevarne.sap.cloud.facturacion.services.impl;


import com.echevarne.sap.cloud.facturacion.model.trazabilidad.TrazabilidadSolAgrEstHistory;
import com.echevarne.sap.cloud.facturacion.repositories.TrazabilidadSolAgrEstHistoryRepository;
import com.echevarne.sap.cloud.facturacion.services.TrazabilidadSolAgrEstHistoryService;

import lombok.var;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("trazabilidadSolAgrEstHistorySrv")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class TrazabilidadSolAgrEstHistoryServiceImpl extends CrudServiceImpl<TrazabilidadSolAgrEstHistory, Long> implements TrazabilidadSolAgrEstHistoryService {
	
	@Autowired
	EntityManager entityManager;

    @Autowired
    public TrazabilidadSolAgrEstHistoryServiceImpl(final TrazabilidadSolAgrEstHistoryRepository repo) {
        super(repo);
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
            "CALL INSERT_TRAZABILIDADSOLAGRESTHISTORY(?, ?, ?, ?, ?, ?, ?, ?, ?)"
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
