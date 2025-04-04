package com.echevarne.sap.cloud.facturacion.repositories;

import java.util.Optional;

import com.echevarne.sap.cloud.facturacion.model.replicated.S4Customers;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Repository;

import javax.persistence.QueryHint;

/**
 * Class for repository {@link S4CustomersRep}.
 *
 * Repository for the Model: Clientes (SAP)
 */
@Repository("s4CustomersRep")
public interface S4CustomersRep extends JpaRepository<S4Customers, Long> {

	@QueryHints({
		@QueryHint(name = org.hibernate.jpa.QueryHints.HINT_FLUSH_MODE, value = "MANUAL"),
	})
	Optional<S4Customers> findByKUNNR(String kunnr);

}
