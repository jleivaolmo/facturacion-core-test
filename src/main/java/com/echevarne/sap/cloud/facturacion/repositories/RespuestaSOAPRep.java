package com.echevarne.sap.cloud.facturacion.repositories;

import java.util.Optional;
import java.util.Set;

import javax.persistence.QueryHint;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Repository;

import com.echevarne.sap.cloud.facturacion.model.facturacion.RespuestaSOAP;

@Repository("respuestaSOAPRep")
public interface RespuestaSOAPRep extends JpaRepository<RespuestaSOAP, Long> {

	@QueryHints(value = @QueryHint(name = "jakarta.persistence.cache.retrieveMode", value="BYPASS"))	
	Optional<Set<RespuestaSOAP>> findByUuidAndSalesOrder(String uuid, String salesOrder);
	
	@QueryHints(value = @QueryHint(name = "jakarta.persistence.cache.retrieveMode", value="BYPASS"))
	Optional<Set<RespuestaSOAP>> findByEstado(Integer estado);

}
