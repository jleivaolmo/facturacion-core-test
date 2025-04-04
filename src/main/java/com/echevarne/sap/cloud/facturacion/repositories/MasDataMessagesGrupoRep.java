package com.echevarne.sap.cloud.facturacion.repositories;

import java.util.List;
import java.util.Optional;

import com.echevarne.sap.cloud.facturacion.model.masterdata.MasDataMessagesGrupo;
import com.echevarne.sap.cloud.facturacion.services.MasDataBaseService;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Repository;

import javax.persistence.QueryHint;

/**
 * Repository for the Model {@link MasDataMessagesGrupo}
 */
@Repository("masDataMessagesGrupoRep")
public interface MasDataMessagesGrupoRep extends JpaRepository<MasDataMessagesGrupo, Long>,
		MasDataBaseService<MasDataMessagesGrupo, Long> {

	@Override
	@QueryHints({
		@QueryHint(name = org.hibernate.jpa.QueryHints.HINT_CACHEABLE, value = "true"),
		@QueryHint(name = org.hibernate.jpa.QueryHints.HINT_FLUSH_MODE, value = "MANUAL"),
	})
	List<MasDataMessagesGrupo> findAll();

	@QueryHints({
		@QueryHint(name = org.hibernate.jpa.QueryHints.HINT_CACHEABLE, value = "true"),
		@QueryHint(name = org.hibernate.jpa.QueryHints.HINT_FLUSH_MODE, value = "MANUAL"),
	})
	Optional<MasDataMessagesGrupo> findByCodeGrupo(String codeGrupo);

}
