package com.echevarne.sap.cloud.facturacion.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.echevarne.sap.cloud.facturacion.model.incongruentes.IncongMat;

@Repository("incongMatRep")
public interface IncongMatRep extends JpaRepository<IncongMat, Long> {
	
	Optional<IncongMat> findByMatRechazado(String matRechazado);

}
