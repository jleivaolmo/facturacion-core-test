package com.echevarne.sap.cloud.facturacion.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.echevarne.sap.cloud.facturacion.model.masterdata.MasDataSistemasLims;
import com.echevarne.sap.cloud.facturacion.repositories.MasDataSistemasLimsRep;
import com.echevarne.sap.cloud.facturacion.services.MasDataSistemasLimsService;

/**
 * Business Service implementation of {@link MasDataSistemasLimsService}
 */
@Service("masDataSistemasLimsSrv")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class MasDataSistemasLimsServiceImpl extends CrudServiceImpl<MasDataSistemasLims, Long>
        implements MasDataSistemasLimsService {

    private final MasDataSistemasLimsRep masDataSistemasLimsRep;

    @Autowired
    public MasDataSistemasLimsServiceImpl(MasDataSistemasLimsRep masDataSistemasLimsRep) {
        super(masDataSistemasLimsRep);
        this.masDataSistemasLimsRep = masDataSistemasLimsRep;
    }

	@Override
	public List<MasDataSistemasLims> findByActive(boolean active) {
		return masDataSistemasLimsRep.findByActive(active);
	}

}
