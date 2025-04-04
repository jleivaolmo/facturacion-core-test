package com.echevarne.sap.cloud.facturacion.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.echevarne.sap.cloud.facturacion.model.solicitudindividual.SolIndTexts;

/**
 * @author Steven Mendez
 * @version 1.0
 * @since 09/02/2019
 *
 */
@Repository("solIndTextsRepository")
public interface SolIndTextsRepository extends JpaRepository<SolIndTexts, Long> {

	SolIndTexts findById(String id);

}
