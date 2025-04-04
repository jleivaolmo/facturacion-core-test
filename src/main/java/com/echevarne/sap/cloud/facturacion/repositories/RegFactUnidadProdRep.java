package com.echevarne.sap.cloud.facturacion.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.echevarne.sap.cloud.facturacion.model.reglasfacturacion.RegFactUnidadProd;

@Repository("regFactUnidadProdRep")
public interface RegFactUnidadProdRep
		extends JpaRepository<RegFactUnidadProd, Long> {

}
