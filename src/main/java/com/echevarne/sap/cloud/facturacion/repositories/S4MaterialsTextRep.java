package com.echevarne.sap.cloud.facturacion.repositories;

import java.util.Optional;

import com.echevarne.sap.cloud.facturacion.model.replicated.S4MaterialTexts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Repository;

import javax.persistence.QueryHint;

/**
 * Class for repository {@link S4MaterialsTextRep}.
 *
 * Repository for the Model: ConversionCentro
 */
@Repository("s4MaterialsText")
public interface S4MaterialsTextRep extends JpaRepository<S4MaterialTexts, Long> {

	@QueryHints({
		@QueryHint(name = org.hibernate.jpa.QueryHints.HINT_FLUSH_MODE, value = "MANUAL"),
	})
	Optional<S4MaterialTexts> findByMATNRAndSPRAS(String matnr, String spras);

}
