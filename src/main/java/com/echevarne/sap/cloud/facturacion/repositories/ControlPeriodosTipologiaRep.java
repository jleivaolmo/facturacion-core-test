package com.echevarne.sap.cloud.facturacion.repositories;

import com.echevarne.sap.cloud.facturacion.model.facturacion.ControlPeriodosTipologia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;


@Repository("controlPeriodosTipologiaRep")
public interface ControlPeriodosTipologiaRep extends JpaRepository<ControlPeriodosTipologia, Long> {

    Optional<ControlPeriodosTipologia> findById(Long id);
}
