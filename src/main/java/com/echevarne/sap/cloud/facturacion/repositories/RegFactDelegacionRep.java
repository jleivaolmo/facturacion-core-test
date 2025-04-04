package com.echevarne.sap.cloud.facturacion.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.echevarne.sap.cloud.facturacion.model.reglasfacturacion.RegFactDelegacion;

@Repository("regFactDelegacionRep")
public interface RegFactDelegacionRep
		extends JpaRepository<RegFactDelegacion, Long> {

}
