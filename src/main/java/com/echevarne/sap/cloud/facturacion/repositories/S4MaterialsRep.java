package com.echevarne.sap.cloud.facturacion.repositories;

import java.util.Optional;

import com.echevarne.sap.cloud.facturacion.model.replicated.S4Material;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Repository;

import javax.persistence.QueryHint;

/**
 * Class for repository {@link S4MaterialsRep}.
 *
 * Repository for the Model: S4Materials
 */
@Repository("s4Materials")
public interface S4MaterialsRep extends JpaRepository<S4Material, Long> {

	@QueryHints({
		@QueryHint(name = org.hibernate.jpa.QueryHints.HINT_FLUSH_MODE, value = "MANUAL"),
	})
	Optional<S4Material> findByMATNR(String matnr);

}
