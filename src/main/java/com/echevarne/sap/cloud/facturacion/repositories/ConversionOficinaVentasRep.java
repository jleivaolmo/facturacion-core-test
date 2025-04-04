package com.echevarne.sap.cloud.facturacion.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.echevarne.sap.cloud.facturacion.model.conversion.ConversionOficinaVentas;
import com.echevarne.sap.cloud.facturacion.model.conversion.ConversionSociedad;

import java.util.Optional;

/**
 * Class for repository {@link ConversionOficinaVentasRep}.
 * 
 * Repository for the Model: ConversionOficinaVentas
 */
@Repository("conversionOficinaVentasRep")
public interface ConversionOficinaVentasRep extends JpaRepository<ConversionOficinaVentas, Long> {

	Optional<ConversionOficinaVentas> findById(Long id);

	Optional<ConversionOficinaVentas> findByTrak(String trak);

}
