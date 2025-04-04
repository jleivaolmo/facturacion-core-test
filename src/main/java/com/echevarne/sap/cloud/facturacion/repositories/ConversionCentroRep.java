package com.echevarne.sap.cloud.facturacion.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Repository;

import com.echevarne.sap.cloud.facturacion.model.conversion.ConversionCentro;

import javax.persistence.QueryHint;

/**
 * Class for repository {@link ConversionCentroRep}.
 *
 * Repository for the Model: ConversionCentro
 */
@Repository("conversionCentroRep")
public interface ConversionCentroRep extends JpaRepository<ConversionCentro, Long> {

	Optional<ConversionCentro> findById(Long id);

	@EntityGraph(
		type = EntityGraph.EntityGraphType.FETCH,
		attributePaths = {"centro"}
	)
	@QueryHints({
			@QueryHint(name = org.hibernate.jpa.QueryHints.HINT_FLUSH_MODE, value = "MANUAL"),
	})
	Optional<ConversionCentro> findByDelegacionProductiva(String delegacionProductiva);

}
