package com.echevarne.sap.cloud.facturacion.repositories;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Repository;

import com.echevarne.sap.cloud.facturacion.model.masterdata.MasDataConditionPrice;
import com.echevarne.sap.cloud.facturacion.services.MasDataBaseService;

import javax.persistence.QueryHint;

/**
 * Repository for the Model {@link MasDataConditionPrice}
 *
 * @author Hernan Girardi
 * @since 04/06/2020
 */
@Repository("masDataConditionPriceRep")
public interface MasDataConditionPriceRep
		extends JpaRepository<MasDataConditionPrice, Long>, MasDataBaseService<MasDataConditionPrice, Long> {

	@QueryHints({
			@QueryHint(name = org.hibernate.jpa.QueryHints.HINT_CACHEABLE, value = "true"),
			@QueryHint(name = org.hibernate.jpa.QueryHints.HINT_FLUSH_MODE, value = "MANUAL"),
	})
	Set<MasDataConditionPrice> findByUngroup(boolean ungroup);

	@QueryHints({
			@QueryHint(name = org.hibernate.jpa.QueryHints.HINT_CACHEABLE, value = "true"),
			@QueryHint(name = org.hibernate.jpa.QueryHints.HINT_FLUSH_MODE, value = "MANUAL"),
	})
	Set<MasDataConditionPrice> findByEnviarAFacturar(boolean enviarAFacturar);
}
