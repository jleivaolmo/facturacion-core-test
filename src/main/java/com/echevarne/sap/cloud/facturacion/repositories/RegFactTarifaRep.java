package com.echevarne.sap.cloud.facturacion.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.echevarne.sap.cloud.facturacion.model.reglasfacturacion.RegFactTarifa;

@Repository("regFactTarifaRep")
public interface RegFactTarifaRep
		extends JpaRepository<RegFactTarifa, Long> {

}
