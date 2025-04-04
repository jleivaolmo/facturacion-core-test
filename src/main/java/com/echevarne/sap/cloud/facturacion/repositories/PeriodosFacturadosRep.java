package com.echevarne.sap.cloud.facturacion.repositories;

import com.echevarne.sap.cloud.facturacion.model.facturacion.PeriodosFacturados;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Class for repository {@link PeriodosFacturadosRep}.
 * 
 * <p>. . .</p>
 * <p>Repository for the Model: PeriodosFacturados</p>
 *
 * @author Germán Laso
 * @version 1.0
 * @since 17/04/2020
 * 
 */
@Repository("periodosFacturadosRep")
public interface PeriodosFacturadosRep extends JpaRepository<PeriodosFacturados, Long> {
	
	@Query("SELECT pf FROM PeriodosFacturados pf JOIN pf.control c WHERE pf.uuidInstance = ?1 and c.estadoActual = ?2")
	Set<PeriodosFacturados> findByUuidInstanceInProcess(String UUID, Integer estado);
}
