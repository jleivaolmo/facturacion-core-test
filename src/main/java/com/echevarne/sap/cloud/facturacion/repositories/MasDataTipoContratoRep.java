package com.echevarne.sap.cloud.facturacion.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.echevarne.sap.cloud.facturacion.model.masterdata.MasDataTipoContrato;
import com.echevarne.sap.cloud.facturacion.model.masterdata.MasDataTipoPeticion;
import com.echevarne.sap.cloud.facturacion.services.MasDataBaseService;

/**
 * Repository for the Model: {@link MasDataTipoPeticion}
 * @author Germán Laso
 * @since 27/08/2020
 */
@Repository("masDataTipoContratoRep")
public interface MasDataTipoContratoRep extends JpaRepository<MasDataTipoContrato,Long>, MasDataBaseService<MasDataTipoContrato,Long>{

}
