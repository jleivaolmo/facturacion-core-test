package com.echevarne.sap.cloud.facturacion.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.echevarne.sap.cloud.facturacion.model.MatConceptoFact;

@Repository("matConceptoFactRep")
public interface MatConceptoFactRep extends JpaRepository<MatConceptoFact, Long> {
	List<MatConceptoFact> findByCodigoMaterial(String codigoMaterial);
}
