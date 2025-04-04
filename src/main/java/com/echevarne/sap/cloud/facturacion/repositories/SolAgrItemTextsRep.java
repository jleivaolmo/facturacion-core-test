package com.echevarne.sap.cloud.facturacion.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.echevarne.sap.cloud.facturacion.model.solicitudagrupada.SolAgrItemTexts;

/**
 * 
 * @author Steven Ricardo Mendez Brenes
 * @version 1.0
 *
 */
@Repository("solAgrItemTextsRep")
public interface SolAgrItemTextsRep extends JpaRepository<SolAgrItemTexts, Long> {

	SolAgrItemTexts findById(String id);

}
