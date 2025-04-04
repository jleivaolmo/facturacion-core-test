package com.echevarne.sap.cloud.facturacion.repositories;

import com.echevarne.sap.cloud.facturacion.model.parametrizacion.Periodicidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository("periodicidadRep")
public interface PeriodicidadRep extends JpaRepository<Periodicidad, Long> {

    Optional<Periodicidad> findByNombre(String nombre);
}
