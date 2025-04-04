package com.echevarne.sap.cloud.facturacion.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.echevarne.sap.cloud.facturacion.model.solicitudagrupada.SolAgrItemPartner;

/**
 * 
 * @author Steven Ricardo Mendez Brenes
 * @version 1.0
 *
 */
@Repository("solAgrItemPartnerRep")
public interface SolAgrItemPartnerRep extends JpaRepository<SolAgrItemPartner, Long> {

	SolAgrItemPartner findById(String id);

}
