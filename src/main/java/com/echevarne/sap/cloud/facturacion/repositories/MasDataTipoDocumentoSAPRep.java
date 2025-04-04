package com.echevarne.sap.cloud.facturacion.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Repository;
import com.echevarne.sap.cloud.facturacion.model.masterdata.MasDataTipoDocumentoSAP;
import com.echevarne.sap.cloud.facturacion.services.MasDataBaseService;

import javax.persistence.QueryHint;

@Repository("masDataTipoDocumentoSAPRep")
public interface MasDataTipoDocumentoSAPRep
		extends JpaRepository<MasDataTipoDocumentoSAP, Long>, MasDataBaseService<MasDataTipoDocumentoSAP, Long> {

	@QueryHints({
		@QueryHint(name = org.hibernate.jpa.QueryHints.HINT_CACHEABLE, value = "true"),
		@QueryHint(name = org.hibernate.jpa.QueryHints.HINT_FLUSH_MODE, value = "MANUAL"),
	})
	Optional<MasDataTipoDocumentoSAP> findByCodigo(String codigo);

}
