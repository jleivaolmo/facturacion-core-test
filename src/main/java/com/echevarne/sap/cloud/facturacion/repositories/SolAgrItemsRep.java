package com.echevarne.sap.cloud.facturacion.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.echevarne.sap.cloud.facturacion.model.solicitudagrupada.SolAgrItems;

/**
 * 
 * @author Steven Ricardo Mendez Brenes
 * @version 1.0
 *
 */



@Repository("solAgrItemsRep")
public interface SolAgrItemsRep extends JpaRepository<SolAgrItems, Long> {

	SolAgrItems findById(String id);

}


