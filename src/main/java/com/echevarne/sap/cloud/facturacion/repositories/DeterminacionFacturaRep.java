package com.echevarne.sap.cloud.facturacion.repositories;

import com.echevarne.sap.cloud.facturacion.model.salidas.DeterminacionFactura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("DeterminacionFacturaRep")
public interface DeterminacionFacturaRep extends JpaRepository<DeterminacionFactura, Long> {

}
