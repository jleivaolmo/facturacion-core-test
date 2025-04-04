package com.echevarne.sap.cloud.facturacion.repositories;

import com.echevarne.sap.cloud.facturacion.model.masterdata.MasDataSubTipologiaFacturacion;
import com.echevarne.sap.cloud.facturacion.services.MasDataBaseService;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Repository;

import javax.persistence.QueryHint;

/**
 * Repository for the Model {@link MasDataSubTipologiaFacturacion}
 * @author Hernan Girardi
 * @since 23/04/2020
 */
@Repository("masDataSubTipologiaFacturacionRep")
public interface MasDataSubTipologiaFacturacionRep extends JpaRepository<MasDataSubTipologiaFacturacion,Long>, MasDataBaseService<MasDataSubTipologiaFacturacion, Long> {

	@QueryHints({
		@QueryHint(name = org.hibernate.jpa.QueryHints.HINT_CACHEABLE, value = "true"),
		@QueryHint(name = org.hibernate.jpa.QueryHints.HINT_FLUSH_MODE, value = "MANUAL"),
	})
	MasDataSubTipologiaFacturacion findByCodeSubTipologia(String codeSubTipologia);
}
