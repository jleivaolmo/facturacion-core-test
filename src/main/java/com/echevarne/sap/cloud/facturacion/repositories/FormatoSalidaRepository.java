package com.echevarne.sap.cloud.facturacion.repositories;

import com.echevarne.sap.cloud.facturacion.model.salidas.FormatoSalida;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("FormatoSalidaRep")
public interface FormatoSalidaRepository extends JpaRepository<FormatoSalida, Long> {
}
