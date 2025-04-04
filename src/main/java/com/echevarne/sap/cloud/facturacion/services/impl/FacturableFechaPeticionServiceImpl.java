package com.echevarne.sap.cloud.facturacion.services.impl;

import java.util.Calendar;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.echevarne.sap.cloud.facturacion.model.determinaciones.FacturableFechaPeticion;
import com.echevarne.sap.cloud.facturacion.repositories.FacturableFechaPeticionRep;
import com.echevarne.sap.cloud.facturacion.services.FacturableFechaPeticionService;

@Service("facturableFechaPeticionSrv")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class FacturableFechaPeticionServiceImpl extends CrudServiceImpl<FacturableFechaPeticion, Long>
        implements FacturableFechaPeticionService {

    private final FacturableFechaPeticionRep facturableFechaPeticionRep;

    @Autowired
    public FacturableFechaPeticionServiceImpl(FacturableFechaPeticionRep facturableFechaPeticionRep) {
        super(facturableFechaPeticionRep);
        this.facturableFechaPeticionRep = facturableFechaPeticionRep;
    }

    @Override
    public List<FacturableFechaPeticion> findByParams(Collection<String> organizaciones, Collection<String> clientes,
            Collection<String> oficinas, Collection<Integer> tipos, Collection<String> empresas,
            Collection<String> companias, Collection<String> remitentes, Calendar fechaValidez) {
        return facturableFechaPeticionRep.findByParams(organizaciones, clientes, oficinas, tipos, empresas, companias,
                remitentes, fechaValidez);
    }

}
