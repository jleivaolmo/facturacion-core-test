package com.echevarne.sap.cloud.facturacion.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.echevarne.sap.cloud.facturacion.model.reglasfacturacion.RegFactPolizas;

@Repository("regFactPolizasRep")
public interface RegFactPolizasRep
		extends JpaRepository<RegFactPolizas, Long> {

}
