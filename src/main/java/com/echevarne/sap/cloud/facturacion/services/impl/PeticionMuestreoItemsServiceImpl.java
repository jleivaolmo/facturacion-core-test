package com.echevarne.sap.cloud.facturacion.services.impl;

import com.echevarne.sap.cloud.facturacion.model.masterdata.MasDataEstado;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.echevarne.sap.cloud.facturacion.model.solicitudrecibida.PeticionMuestreoItems;
import com.echevarne.sap.cloud.facturacion.repositories.PeticionMuestreoItemsRep;
import com.echevarne.sap.cloud.facturacion.services.PeticionMuestreoItemsService;

import java.sql.Timestamp;
import java.util.List;

@Service("peticionMuestreoItemsSrv")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class PeticionMuestreoItemsServiceImpl extends CrudServiceImpl<PeticionMuestreoItems, Long>
        implements PeticionMuestreoItemsService {

    private final PeticionMuestreoItemsRep peticionMuestreoItemsRep;

    @Autowired
    public PeticionMuestreoItemsServiceImpl(final PeticionMuestreoItemsRep peticionMuestreoItemsRep){
        super(peticionMuestreoItemsRep);
        this.peticionMuestreoItemsRep = peticionMuestreoItemsRep;
    }

    @Override
    public List<PeticionMuestreoItems> findPruebasMixtasPorEstadoYFecha(@Param("from") Timestamp from, @Param("estado") MasDataEstado estado) {
        return peticionMuestreoItemsRep.findPruebasMixtasPorEstadoYFecha(from,estado);
    }

}
