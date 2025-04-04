package com.echevarne.sap.cloud.facturacion.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.echevarne.sap.cloud.facturacion.model.masterdata.MasDataSistemasLims;
import com.echevarne.sap.cloud.facturacion.services.MasDataBaseService;

/**
 * Repository for the Model {@link MasDataSistemasLims}
 * @author Hernan Girardi
 * @since 23/04/2020
 */
@Repository("masDataSistemasLimsRep")
public interface MasDataSistemasLimsRep
		extends JpaRepository<MasDataSistemasLims, Long>, MasDataBaseService<MasDataSistemasLims, Long> {

}