package com.echevarne.sap.cloud.facturacion.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.echevarne.sap.cloud.facturacion.model.incongruentes.IncongSec;

@Repository("incongSecRep")
public interface IncongSecRep extends JpaRepository<IncongSec, Long> {
	
	Optional<IncongSec> findByClienteAndCia(boolean cliente, boolean cia);

}
