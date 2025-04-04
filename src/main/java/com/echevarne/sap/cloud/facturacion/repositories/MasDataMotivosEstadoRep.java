package com.echevarne.sap.cloud.facturacion.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Repository;

import com.echevarne.sap.cloud.facturacion.model.masterdata.MasDataAlerta;
import com.echevarne.sap.cloud.facturacion.model.masterdata.MasDataMotivosEstado;
import com.echevarne.sap.cloud.facturacion.services.MasDataBaseService;

import javax.persistence.QueryHint;

/**
 * Repository for the Model {@link MasDataMotivosEstado}
 * @author Hernan Girardi
 * @since 25/08/2020
 */

@Repository("masDataMotivosEstadoRep")
public interface MasDataMotivosEstadoRep extends JpaRepository<MasDataMotivosEstado,Long>, MasDataBaseService<MasDataMotivosEstado,Long> {

	@QueryHints({
		@QueryHint(name = org.hibernate.jpa.QueryHints.HINT_CACHEABLE, value = "true"),
		@QueryHint(name = org.hibernate.jpa.QueryHints.HINT_FLUSH_MODE, value = "MANUAL"),
	})
	Optional<MasDataMotivosEstado> findByAlertaAndActive(MasDataAlerta alerta, boolean active);

	@QueryHints({
		@QueryHint(name = org.hibernate.jpa.QueryHints.HINT_CACHEABLE, value = "true"),
		@QueryHint(name = org.hibernate.jpa.QueryHints.HINT_FLUSH_MODE, value = "MANUAL"),
	})
	Optional<MasDataMotivosEstado> findByAlertaCodigoAlertaAndActive(String codigoAlerta, boolean active);

	@QueryHints({
		@QueryHint(name = org.hibernate.jpa.QueryHints.HINT_CACHEABLE, value = "true"),
		@QueryHint(name = org.hibernate.jpa.QueryHints.HINT_FLUSH_MODE, value = "MANUAL"),
	})
	List<MasDataMotivosEstado> findByDescripcionContains(String descripciontoFind);

	@QueryHints({
		@QueryHint(name = org.hibernate.jpa.QueryHints.HINT_CACHEABLE, value = "true"),
		@QueryHint(name = org.hibernate.jpa.QueryHints.HINT_FLUSH_MODE, value = "MANUAL"),
	})
	List<MasDataMotivosEstado> findByAlertaAndDescripcionContainsAndActive(MasDataAlerta alerta, String descripciontoFind, boolean active);

	@QueryHints({
		@QueryHint(name = org.hibernate.jpa.QueryHints.HINT_CACHEABLE, value = "true"),
		@QueryHint(name = org.hibernate.jpa.QueryHints.HINT_FLUSH_MODE, value = "MANUAL"),
	})
	Optional<MasDataMotivosEstado> findByCodigoAndActive(String codigo, boolean active);
}
