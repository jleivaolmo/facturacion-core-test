package com.echevarne.sap.cloud.facturacion.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Repository;

import com.echevarne.sap.cloud.facturacion.model.masterdata.MasDataMotivosRectificacion;
import com.echevarne.sap.cloud.facturacion.services.MasDataBaseService;

import javax.persistence.QueryHint;

/**
 * Repository for the Model {@link MasDataMotivosEstado}
 * @author Hernan Girardi
 * @since 25/08/2020
 */

@Repository("masDataMotivosRectificacionRep")
public interface MasDataMotivosRectificacionRep
		extends JpaRepository<MasDataMotivosRectificacion, Long>, MasDataBaseService<MasDataMotivosRectificacion, Long> {

	@QueryHints({
		@QueryHint(name = org.hibernate.jpa.QueryHints.HINT_CACHEABLE, value = "true"),
		@QueryHint(name = org.hibernate.jpa.QueryHints.HINT_FLUSH_MODE, value = "MANUAL"),
	})
	Optional<MasDataMotivosRectificacion> findByCodigoAndActive(String codigo, boolean active);

}
