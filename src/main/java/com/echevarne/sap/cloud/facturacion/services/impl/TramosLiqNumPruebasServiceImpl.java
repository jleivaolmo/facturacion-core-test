package com.echevarne.sap.cloud.facturacion.services.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.echevarne.sap.cloud.facturacion.model.liquidaciones.TramosLiqNumPruebas;
import com.echevarne.sap.cloud.facturacion.repositories.TramosLiqNumPruebasRep;
import com.echevarne.sap.cloud.facturacion.services.TramosLiqNumPruebasService;

@Service("tramosLiqNumPruebasSrv")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class TramosLiqNumPruebasServiceImpl extends CrudServiceImpl<TramosLiqNumPruebas, Long>
        implements TramosLiqNumPruebasService {

    private final TramosLiqNumPruebasRep tramosLiqNumPruebasRep;

    @Autowired
    public TramosLiqNumPruebasServiceImpl(final TramosLiqNumPruebasRep tramosLiqNumPruebasRep){
        super(tramosLiqNumPruebasRep);
        this.tramosLiqNumPruebasRep = tramosLiqNumPruebasRep;
    }

	@Override
	public Optional<TramosLiqNumPruebas> findByNumPruebas(int numPruebas) {
		return tramosLiqNumPruebasRep.findByNumPruebas(numPruebas);
	}

}
