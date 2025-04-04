package com.echevarne.sap.cloud.facturacion.repositories;

import org.hibernate.FlushMode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Repository;

import com.echevarne.sap.cloud.facturacion.model.masterdata.MasDataEstado;
import com.echevarne.sap.cloud.facturacion.services.MasDataBaseService;

import javax.persistence.QueryHint;
import java.util.List;

/**
 * Repository for the Model {@link MasDataEstado}
 * @author Hernan Girardi
 * @since 18/06/2020
 */
@Repository("masDataEstadoRep")
public interface MasDataEstadoRep extends JpaRepository<MasDataEstado, Long>, MasDataBaseService<MasDataEstado, Long> {

	@Override
	@QueryHints({
		@QueryHint(name = org.hibernate.jpa.QueryHints.HINT_CACHEABLE, value = "true"),
		@QueryHint(name = org.hibernate.jpa.QueryHints.HINT_FLUSH_MODE, value = "MANUAL"),
	})
	List<MasDataEstado> findAll();

	@QueryHints({
		@QueryHint(name = org.hibernate.jpa.QueryHints.HINT_CACHEABLE, value = "true"),
		@QueryHint(name = org.hibernate.jpa.QueryHints.HINT_FLUSH_MODE, value = "MANUAL"),
	})
	MasDataEstado findByCodeEstado(String codeEstado);

}
