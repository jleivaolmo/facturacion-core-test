package com.echevarne.sap.cloud.facturacion.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.echevarne.sap.cloud.facturacion.model.solicitudagrupada.SolAgrTexts;

/**
 * 
 * @author Steven Ricardo Mendez Brenes
 * @version 1.0
 *
 */
@Repository("solAgrTextsRep")
public interface SolAgrTextsRep extends JpaRepository<SolAgrTexts, Long> {

	SolAgrTexts findById(String id);

}
