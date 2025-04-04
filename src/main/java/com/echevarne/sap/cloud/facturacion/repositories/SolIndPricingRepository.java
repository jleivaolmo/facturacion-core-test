package com.echevarne.sap.cloud.facturacion.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.echevarne.sap.cloud.facturacion.model.solicitudindividual.SolIndPricing;

/**
 * @author Steven Mendez
 * @version 1.0
 * @since 09/04/2019
 *
 */
@Repository("solIndPricingRepository")
public interface SolIndPricingRepository extends JpaRepository<SolIndPricing, Long> {

	SolIndPricing findById(String id);
}
