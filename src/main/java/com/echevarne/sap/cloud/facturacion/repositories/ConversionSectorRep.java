package com.echevarne.sap.cloud.facturacion.repositories;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Repository;

import com.echevarne.sap.cloud.facturacion.model.conversion.ConversionSector;

import javax.persistence.QueryHint;
import java.util.Optional;

/**
 * Class for repository {@link ConversionSectorRep}.
 *
 * Repository for the Model: ConversionSector
 */
@Repository("conversionSectorRep")
public interface ConversionSectorRep extends JpaRepository<ConversionSector, Long> {

	Optional<ConversionSector> findById(Long id);

	@EntityGraph(
		type = EntityGraph.EntityGraphType.FETCH,
		attributePaths = {"sector"}
	)
	@QueryHints({
		@QueryHint(name = org.hibernate.jpa.QueryHints.HINT_FLUSH_MODE, value = "MANUAL"),
	})
	Optional<ConversionSector> findByTipoPeticion(int tipoPeticion);

}
