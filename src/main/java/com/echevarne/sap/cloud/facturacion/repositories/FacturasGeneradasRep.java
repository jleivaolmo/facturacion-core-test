package com.echevarne.sap.cloud.facturacion.repositories;

import com.echevarne.sap.cloud.facturacion.model.facturacion.FacturasGeneradas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Class for repository {@link FacturasGeneradasRep}.
 *
 * <p>. . .</p>
 * <p>Repository for the Model: FacturasGeneradas</p>
 *
 * @author Germán Laso
 * @version 1.0
 * @since 30/04/2021
 *
 */
@Repository("facturasGeneradasRep")
public interface FacturasGeneradasRep extends JpaRepository<FacturasGeneradas, Long> {

	@Query("SELECT fg FROM FacturasGeneradas fg WHERE " +
			"fg.idAgrupacion = ?1 ORDER BY fg.entityCreationTimestamp DESC")
    List<FacturasGeneradas> findByIdAgrupacion(Long idAgrupacion);

    Optional<FacturasGeneradas> findBySalesOrder(String salesOrder);
    
    Optional<FacturasGeneradas> findByUUID(String UUID);

}
