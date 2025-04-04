package com.echevarne.sap.cloud.facturacion.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.echevarne.sap.cloud.facturacion.model.masterdata.MasDataTipoPeticion;
import com.echevarne.sap.cloud.facturacion.services.MasDataBaseService;

/**
 * Repository for the Model: {@link MasDataTipoPeticion}
 * @author Hernan Girardi
 * @since 26/03/2020
 */
@Repository("masDataTipoPeticionRep")
public interface MasDataTipoPeticionRep extends JpaRepository<MasDataTipoPeticion,Long>, MasDataBaseService<MasDataTipoPeticion,Long>{

}
