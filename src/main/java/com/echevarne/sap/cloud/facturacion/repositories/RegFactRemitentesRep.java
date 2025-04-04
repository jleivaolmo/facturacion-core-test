package com.echevarne.sap.cloud.facturacion.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.echevarne.sap.cloud.facturacion.model.reglasfacturacion.RegFactRemitentes;

@Repository("regFactRemitentesRep")
public interface RegFactRemitentesRep
		extends JpaRepository<RegFactRemitentes, Long> {

}
