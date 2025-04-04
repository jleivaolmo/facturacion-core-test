package com.echevarne.sap.cloud.facturacion.repositories.replicated;

import com.echevarne.sap.cloud.facturacion.model.replicated.S4SalesOfficeRegion;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Class for the repository{@link S4SalesOfficeRegion}.
 *
 * <p>This is the Entity repository for SolicitudMuestreo. . .</p>
 *
 */
@Repository("s4SalesOfficeRegionRepository")
public interface S4SalesOfficeRegionRepository extends JpaRepository<S4SalesOfficeRegion, String> {

}
