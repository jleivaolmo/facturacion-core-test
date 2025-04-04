package com.echevarne.sap.cloud.facturacion.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Repository;

import com.echevarne.sap.cloud.facturacion.model.masterdata.MasDataTipologiaFacturacion;
import com.echevarne.sap.cloud.facturacion.services.MasDataBaseService;

import javax.persistence.QueryHint;

/**
 * Repository for the Model {@link MasDataTipologiaFacturacion}
 * @author Hernan Girardi
 * @since 23/04/2020
 */
@Repository("masDataTipologiaFacturacionRep")
public interface MasDataTipologiaFacturacionRep extends JpaRepository<MasDataTipologiaFacturacion,Long>, MasDataBaseService<MasDataTipologiaFacturacion, Long> {

	@QueryHints({
		@QueryHint(name = org.hibernate.jpa.QueryHints.HINT_CACHEABLE, value = "true"),
		@QueryHint(name = org.hibernate.jpa.QueryHints.HINT_FLUSH_MODE, value = "MANUAL"),
	})
	MasDataTipologiaFacturacion findByCodeTipologia(String codeTipologia);

}
