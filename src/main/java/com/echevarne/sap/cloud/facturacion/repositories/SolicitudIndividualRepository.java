package com.echevarne.sap.cloud.facturacion.repositories;

import com.echevarne.sap.cloud.facturacion.model.solicitudindividual.SolicitudIndividual;
import com.echevarne.sap.cloud.facturacion.model.solicitudrecibida.SolicitudMuestreo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository("solicitudIndividualRepository")
public interface SolicitudIndividualRepository extends JpaRepository<SolicitudIndividual, Long> {

    Optional<SolicitudIndividual> findById(Long id);

    @Query("SELECT si FROM SolicitudIndividual si WHERE si.purchaseOrderByCustomer = ?1")
    Optional<SolicitudIndividual> findByPurchaseOrderByCustomer(String purchaseOrderByCustomer);

    Optional<List<SolicitudIndividual>> findAllBySoldToParty(String soldToParty);

    @Query("SELECT si FROM SolicitudIndividual si WHERE " +
            "si.trazabilidad.peticionRec.esPresupuesto = true " +
            "and si.salesOrderDate < ?1")
    List<SolicitudIndividual> findPresupuestosAnterioresA(Date fecha);

    @Query("SELECT si FROM SolicitudIndividual si WHERE " +
            "si.trazabilidad.peticionRec.esPresupuesto = true")
    List<SolicitudIndividual> findPresupuestos();
}
