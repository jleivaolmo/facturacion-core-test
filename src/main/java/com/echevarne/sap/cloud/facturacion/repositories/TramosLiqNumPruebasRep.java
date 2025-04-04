package com.echevarne.sap.cloud.facturacion.repositories;

import java.util.Optional;
import com.echevarne.sap.cloud.facturacion.model.liquidaciones.TramosLiqNumPruebas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Class for repository {@link TramosLiqNumPruebasRep}.
 */
@Repository("tramosLiqNumPruebasRep")
public interface TramosLiqNumPruebasRep extends JpaRepository<TramosLiqNumPruebas, Long> {

	@Query("SELECT t FROM TramosLiqNumPruebas t WHERE t.inicioTramo <= ?1 AND (t.finTramo >= ?1 OR t.finTramo IS NULL)")
	Optional<TramosLiqNumPruebas> findByNumPruebas(int numPruebas);	
}
