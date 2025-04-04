package com.echevarne.sap.cloud.facturacion.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.echevarne.sap.cloud.facturacion.model.solicitudagrupada.SolAgrItemPricing;

/**
 * 
 * @author Steven Ricardo Mendez Brenes
 * @version 1.0
 *
 */
@Repository("solAgrItemPricingRep")
public interface SolAgrItemPricingRep extends JpaRepository<SolAgrItemPricing, Long> {

	SolAgrItemPricing findById(String id);

}
