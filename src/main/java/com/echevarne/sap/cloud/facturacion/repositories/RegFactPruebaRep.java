package com.echevarne.sap.cloud.facturacion.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.echevarne.sap.cloud.facturacion.model.reglasfacturacion.RegFactPrueba;

@Repository("regFactPruebaRep")
public interface RegFactPruebaRep
		extends JpaRepository<RegFactPrueba, Long> {

}
