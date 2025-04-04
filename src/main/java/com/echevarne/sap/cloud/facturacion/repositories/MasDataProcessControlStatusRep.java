package com.echevarne.sap.cloud.facturacion.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Repository;

import com.echevarne.sap.cloud.facturacion.model.masterdata.MasDataProcessControlStatus;
import com.echevarne.sap.cloud.facturacion.services.MasDataBaseService;

import javax.persistence.QueryHint;

/**
 * Repository for the Model {@link MasDataProcessControlStatus}
 * @author Hernan Girardi
 * @since 23/04/2020
 */
@Repository("masDataProcessControlStatusRep")
public interface MasDataProcessControlStatusRep extends JpaRepository<MasDataProcessControlStatus,Long>, MasDataBaseService<MasDataProcessControlStatus, Long> {

	@QueryHints({
		@QueryHint(name = org.hibernate.jpa.QueryHints.HINT_CACHEABLE, value = "true"),
		@QueryHint(name = org.hibernate.jpa.QueryHints.HINT_FLUSH_MODE, value = "MANUAL"),
	})
	MasDataProcessControlStatus findByCodeStatus(String codeStatus);

}
