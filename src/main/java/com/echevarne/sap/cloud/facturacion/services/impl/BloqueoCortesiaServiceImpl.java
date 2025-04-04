package com.echevarne.sap.cloud.facturacion.services.impl;

import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.echevarne.sap.cloud.facturacion.model.determinaciones.BloqueoCortesia;
import com.echevarne.sap.cloud.facturacion.repositories.BloqueoCortesiaRep;
import com.echevarne.sap.cloud.facturacion.services.BloqueoCortesiaService;

@Service("bloqueoCortesiaService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class BloqueoCortesiaServiceImpl extends CrudServiceImpl<BloqueoCortesia, Long>
        implements BloqueoCortesiaService {

    private final BloqueoCortesiaRep bloqueoCortesiaRep;

    @Autowired
    public BloqueoCortesiaServiceImpl(final BloqueoCortesiaRep bloqueoCortesiaRep){
        super(bloqueoCortesiaRep);
        this.bloqueoCortesiaRep = bloqueoCortesiaRep;
    }

    @Override
    @Transactional(readOnly = true)
    public List<BloqueoCortesia> findByParams(Collection<String> organizaciones, Collection<String> clientes,
                Collection<String> oficinas, Collection<Integer> tipos, Collection<String> empresas,
                Collection<String> companias, Collection<String> remitentes, Calendar fechaValidez) {
        return bloqueoCortesiaRep.findByParams(organizaciones, clientes, oficinas, tipos, empresas, companias,
                remitentes, fechaValidez);
    }
}
