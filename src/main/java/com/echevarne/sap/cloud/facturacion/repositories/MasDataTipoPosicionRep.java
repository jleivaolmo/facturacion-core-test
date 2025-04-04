package com.echevarne.sap.cloud.facturacion.repositories;

import java.util.Optional;

import com.echevarne.sap.cloud.facturacion.model.masterdata.MasDataTipoPosicion;
import com.echevarne.sap.cloud.facturacion.services.MasDataBaseService;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Repository;

import javax.persistence.QueryHint;

/**
 * Repository for the Model: {@link MasDataTipoPosicion}
 * @author Hernan Girardi
 * @since 26/03/2020
 */
@Repository("masDataTipoPosicionRep")
public interface MasDataTipoPosicionRep extends JpaRepository<MasDataTipoPosicion, Long>, MasDataBaseService<MasDataTipoPosicion, Long> {

	@QueryHints({
		@QueryHint(name = org.hibernate.jpa.QueryHints.HINT_CACHEABLE, value = "true"),
		@QueryHint(name = org.hibernate.jpa.QueryHints.HINT_FLUSH_MODE, value = "MANUAL"),
	})
	Optional<MasDataTipoPosicion> findByTipoPosicion(String tipoPosicion);

}
