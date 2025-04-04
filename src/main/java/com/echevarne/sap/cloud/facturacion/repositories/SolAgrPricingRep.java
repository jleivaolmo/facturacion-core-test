package com.echevarne.sap.cloud.facturacion.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.echevarne.sap.cloud.facturacion.model.solicitudagrupada.SolAgrItemTexts;
import com.echevarne.sap.cloud.facturacion.model.solicitudagrupada.SolAgrPricing;

/**
 * 
 * @author Steven Ricardo Mendez Brenes
 * @version 1.0
 *
 */
@Repository("solAgrPricingRep")
public interface SolAgrPricingRep  extends JpaRepository<SolAgrPricing, Long> {

	SolAgrItemTexts findById(String id);

}
