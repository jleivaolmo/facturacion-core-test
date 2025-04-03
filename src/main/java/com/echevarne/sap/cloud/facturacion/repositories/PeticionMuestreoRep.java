package com.echevarne.sap.cloud.facturacion.repositories;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import com.echevarne.sap.cloud.facturacion.model.solicitudrecibida.PeticionMuestreo;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Repository;

import javax.persistence.QueryHint;

/**
 * Class for repository {@link PeticionMuestreoRep}.
 *
 * <p>. . .</p>
 * <p>Repository for the Model: PeticionMuestreo</p>
 *
 * @author Steven Ricardo Mendez Brenes
 * @version 1.0
 * @since 06/24/2019
 *
 */
@Repository("peticionMuestreoRep")
public interface PeticionMuestreoRep extends JpaRepository<PeticionMuestreo, Long>{

	@EntityGraph(
			type = EntityGraph.EntityGraphType.FETCH,
			attributePaths = {
					"pruebas"
			}
	)
	PeticionMuestreo findByCodigoPeticion(String codigoPeticion);

	@Query("SELECT pm FROM PeticionMuestreo pm WHERE pm.codigoPeticion IN ?1")
	Optional<List<PeticionMuestreo>> findAllByCodigoPeticion(Collection<String> codigoPeticion);

}
