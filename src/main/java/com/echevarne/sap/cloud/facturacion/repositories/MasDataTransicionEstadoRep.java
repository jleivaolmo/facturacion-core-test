package com.echevarne.sap.cloud.facturacion.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.echevarne.sap.cloud.facturacion.model.masterdata.MasDataEstado;
import com.echevarne.sap.cloud.facturacion.model.masterdata.MasDataTransicionEstado;
import com.echevarne.sap.cloud.facturacion.services.MasDataBaseService;
import javax.persistence.QueryHint;

/**
 * Repository for the Model: {@link MasDataTransicionEstado}
 */
@Repository("masDataTransicionEstadoRep")
public interface MasDataTransicionEstadoRep extends JpaRepository<MasDataTransicionEstado,Long>, MasDataBaseService<MasDataTransicionEstado, Long> {

	@EntityGraph(
		type = EntityGraph.EntityGraphType.FETCH,
		attributePaths = {"destino"}
	)
	@QueryHints({
		@QueryHint(name = org.hibernate.jpa.QueryHints.HINT_CACHEABLE, value = "true"),
		@QueryHint(name = org.hibernate.jpa.QueryHints.HINT_FLUSH_MODE, value = "MANUAL"),
	})
	List<MasDataTransicionEstado> findAllByOrigenAndAutomatico(MasDataEstado origen, boolean automatico);

	@EntityGraph(
			type = EntityGraph.EntityGraphType.FETCH,
			attributePaths = {"destino"}
	)
	@QueryHints({
			@QueryHint(name = org.hibernate.jpa.QueryHints.HINT_CACHEABLE, value = "true"),
			@QueryHint(name = org.hibernate.jpa.QueryHints.HINT_FLUSH_MODE, value = "MANUAL"),
	})
	@Query("SELECT tE FROM MasDataTransicionEstado tE JOIN tE.destino eD WHERE tE.origen.codeEstado = :origenCodeEstado AND tE.active = :active AND eD.codeEstado IN (:estadosDestino)")
	List<MasDataTransicionEstado> findAllByOrigenAndActiveAndDestinoCodeEstado(@Param("origenCodeEstado") String origenCodeEstado, @Param("active") boolean active,
			@Param("estadosDestino") List<String> estadosDestino);
}
