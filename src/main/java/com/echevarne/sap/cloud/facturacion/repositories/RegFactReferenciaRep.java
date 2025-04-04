package com.echevarne.sap.cloud.facturacion.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.echevarne.sap.cloud.facturacion.model.reglasfacturacion.RegFactReferencia;

@Repository("regFactReferenciaRep")
public interface RegFactReferenciaRep
		extends JpaRepository<RegFactReferencia, Long> {

}
