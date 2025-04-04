package com.echevarne.sap.cloud.facturacion.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Repository;
import com.echevarne.sap.cloud.facturacion.model.masterdata.MasDataMotivosCancelacion;
import com.echevarne.sap.cloud.facturacion.services.MasDataBaseService;

import javax.persistence.QueryHint;

/**
 * Repository for the Model {@link MasDataMotivosEstado}
 * @author Hernan Girardi
 * @since 25/08/2020
 */

@Repository("masDataMotivosCancelacionRep")
public interface MasDataMotivosCancelacionRep
		extends JpaRepository<MasDataMotivosCancelacion, Long>, MasDataBaseService<MasDataMotivosCancelacion, Long> {

	@QueryHints({
		@QueryHint(name = org.hibernate.jpa.QueryHints.HINT_CACHEABLE, value = "true"),
		@QueryHint(name = org.hibernate.jpa.QueryHints.HINT_FLUSH_MODE, value = "MANUAL"),
	})
	Optional<MasDataMotivosCancelacion> findByCodigoAndActive(String codigo, boolean active);

}
