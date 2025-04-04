package com.echevarne.sap.cloud.facturacion.services.impl;

import com.echevarne.sap.cloud.facturacion.model.facturacion.ControlPeriodos;
import com.echevarne.sap.cloud.facturacion.model.facturacion.ControlPeriodosTipologia;
import com.echevarne.sap.cloud.facturacion.repositories.ControlPeriodosRep;
import com.echevarne.sap.cloud.facturacion.repositories.ControlPeriodosTipologiaRep;
import com.echevarne.sap.cloud.facturacion.services.ControlPeriodosService;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.function.BiConsumer;

@Service("controlPeriodosSrv")
public class ControlPeriodosServiceImpl extends CrudServiceImpl<ControlPeriodos, Long>
        implements ControlPeriodosService {

    private final ControlPeriodosRep controlPeriodosRep;
    
    @Autowired
    private ControlPeriodosTipologiaRep controlPeriodosTipologiaRep;
    
    @Autowired
    public ControlPeriodosServiceImpl(final ControlPeriodosRep controlPeriodosRep) {
        super(controlPeriodosRep);
        this.controlPeriodosRep = controlPeriodosRep;
    }

    @Override
    public void setControlPeriodosTotal(Integer total, long controlPeriodosId, String tipologia) {
    	ControlPeriodos control = controlPeriodosRep.findById(controlPeriodosId).get();
		var controlPeriodosTipologia = new ControlPeriodosTipologia();
		controlPeriodosTipologia.setNombreTipologia(tipologia);
		controlPeriodosTipologia.setTotal(total);
		controlPeriodosTipologia.setControl(control);
		control.getControlPeriodosTipologia().add(controlPeriodosTipologia);
		controlPeriodosTipologiaRep.save(controlPeriodosTipologia);
	}
    
    @Override
    @Transactional(propagation=Propagation.REQUIRES_NEW)
    public void setControlPeriodosTotalInNewTx(Integer total, long controlPeriodosId, String tipologia) {
    	setControlPeriodosTotal(total, controlPeriodosId, tipologia);
    }

    private <T> void updateValorYEstado(long controlPeriodosId, T value, int estado, BiConsumer<ControlPeriodos, T> setConsumer) {
        ControlPeriodos controlPeriodos = controlPeriodosRep.findById(controlPeriodosId).get();
        controlPeriodos.setEstadoActual(estado);
        setConsumer.accept(controlPeriodos, value);
        controlPeriodosRep.save(controlPeriodos);
    }

    @Override
    @Transactional
    public void finalize(long id, int estado) {
        final Timestamp fechaFin = new Timestamp(System.currentTimeMillis());
        this.updateValorYEstado(id, fechaFin, estado, ControlPeriodos::setFechaFin);
    }
}
