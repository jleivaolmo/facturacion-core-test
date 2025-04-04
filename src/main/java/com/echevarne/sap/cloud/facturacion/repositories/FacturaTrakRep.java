package com.echevarne.sap.cloud.facturacion.repositories;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.echevarne.sap.cloud.facturacion.model.facturacion.FacturaTrak;

@Repository("facturaTrakRep")
public interface FacturaTrakRep extends JpaRepository<FacturaTrak, Long> {
	
	Optional<Set<FacturaTrak>> findByEstado(Integer estado);
	
	@Query("SELECT f.id FROM FacturaTrak f WHERE f.estado = ?1 ")
	List<Long> getByEstado(Integer estado);
}
