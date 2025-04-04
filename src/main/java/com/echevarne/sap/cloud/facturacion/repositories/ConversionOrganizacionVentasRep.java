package com.echevarne.sap.cloud.facturacion.repositories;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.echevarne.sap.cloud.facturacion.model.conversion.ConversionOrganizacionVentas;

/**
 * Class for repository {@link ConversionOrganizacionVentasRep}.
 * 
 * Repository for the Model: ConversionOrganizacionVentas
 */
@Repository("conversionOrganizacionVentasRep")
public interface ConversionOrganizacionVentasRep extends JpaRepository<ConversionOrganizacionVentas, Long> {

	Optional<ConversionOrganizacionVentas> findById(Long id);

	Optional<ConversionOrganizacionVentas> findBySectorAndCodigoDelegacion(String sector, String codigoDelegacion);

	List<ConversionOrganizacionVentas> findBySectorInAndCodigoDelegacionIn(Collection<String> sector,
																		   Collection<String> codigoDelegacion);

}
