package com.echevarne.sap.cloud.facturacion.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.echevarne.sap.cloud.facturacion.model.solicitudindividual.SolIndItemPartner;

/**
 * @author Steven Mendez
 * @version 1.0
 * @since 09/04/2019
 *
 */
@Repository("solIndItemPartnerRepository")
public interface SolIndItemPartnerRepository extends JpaRepository<SolIndItemPartner, Long> {

	SolIndItemPartner findById(String id);
}
