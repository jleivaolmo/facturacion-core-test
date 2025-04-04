package com.echevarne.sap.cloud.facturacion.services;

import com.echevarne.sap.cloud.facturacion.model.masterdata.MasDataTipologiaFacturacion;
import org.springframework.data.jpa.repository.QueryHints;

import javax.persistence.QueryHint;

/**
 * Business Services logic for Model: {@link MasDataTipologiaFacturacion}
 *
 * @author Hernan Girardi
 * @since 26/03/2020
 */

public interface MasDataTipologiaFacturacionService
		extends CrudService<MasDataTipologiaFacturacion, Long>, MasDataBaseService<MasDataTipologiaFacturacion, Long> {

	@QueryHints({
		@QueryHint(name = org.hibernate.jpa.QueryHints.HINT_CACHEABLE, value = "true"),
		@QueryHint(name = org.hibernate.jpa.QueryHints.HINT_FLUSH_MODE, value = "MANUAL"),
	})
	MasDataTipologiaFacturacion findByCodeTipologia(String codeTipologia);

}
