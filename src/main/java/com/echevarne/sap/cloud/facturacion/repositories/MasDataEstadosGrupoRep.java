package com.echevarne.sap.cloud.facturacion.repositories;

import com.echevarne.sap.cloud.facturacion.model.masterdata.MasDataEstadosGrupo;
import com.echevarne.sap.cloud.facturacion.services.MasDataBaseService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Repository;

import javax.persistence.QueryHint;

/**
 * Repository for the Model {@link MasDataEstadosGrupo}
 */
@Repository("masDataEstadosGrupoRep")
public interface MasDataEstadosGrupoRep extends JpaRepository<MasDataEstadosGrupo, Long>,
		MasDataBaseService<MasDataEstadosGrupo, Long> {

	@QueryHints({
		@QueryHint(name = org.hibernate.jpa.QueryHints.HINT_CACHEABLE, value = "true"),
		@QueryHint(name = org.hibernate.jpa.QueryHints.HINT_FLUSH_MODE, value = "MANUAL"),
	})
	MasDataEstadosGrupo findByCodigo(String codigo);

}
