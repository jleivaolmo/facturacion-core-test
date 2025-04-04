package com.echevarne.sap.cloud.facturacion.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.echevarne.sap.cloud.facturacion.model.solicitudagrupada.SolAgrItemTexts;
import com.echevarne.sap.cloud.facturacion.model.solicitudagrupada.SolicitudesAgrupadas;

import java.util.List;
import java.util.Optional;

/**
 * 
 * @author Steven Ricardo Mendez Brenes
 * @version 1.0
 *
 */
@Repository("solicitudesAgrupadasRep")
public interface SolicitudesAgrupadasRep extends JpaRepository<SolicitudesAgrupadas, Long> {

	SolAgrItemTexts findBySalesOrderAgr(String salesOrderAgr);

	Optional<List<SolicitudesAgrupadas>> findByBillingDocument(String billingDocument);

	Optional<List<SolicitudesAgrupadas>> findByBillingDocumentAndSoldToParty(String billingDocument, String soldToParty);
	
	Optional<List<SolicitudesAgrupadas>> findBySalesOrderType(String salesOrderType);
	
//	@Modifying
//	@Query(value = "UPDATE SolicitudesAgrupadas sa SET salesOrderAgr = :salesOrderAgr, billingDocument = :billingDocument where sa.ID = :saID", nativeQuery = true)
//	void saveSapDocuments(@Param("salesOrder") String salesOrderAgr, @Param("billingDocument") String billingDocument, @Param("saID") Long saID);

}
