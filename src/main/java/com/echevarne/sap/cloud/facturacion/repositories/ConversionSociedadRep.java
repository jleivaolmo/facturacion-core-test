package com.echevarne.sap.cloud.facturacion.repositories;

import com.echevarne.sap.cloud.facturacion.model.BasicEntity;
import com.echevarne.sap.cloud.facturacion.model.conversion.ConversionSociedad;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * Class for repository {@link ConversionSociedadRep}.
 * <p>
 * Repository for the Model: ConversionSociedad
 */
@Repository("conversionSociedadRep")
public interface ConversionSociedadRep extends JpaRepository<ConversionSociedad, Long> {

    Optional<ConversionSociedad> findById(Long id);

    Optional<ConversionSociedad> findByClienteAndCodigoDelegacionAndPruebaAndSector(String cliente,
                                                                                    String codigoDelegacion,
                                                                                    String prueba,
                                                                                    String sector);

    List<ConversionSociedad> findByClienteInAndCodigoDelegacionInAndPruebaInAndSectorIn(
            Collection<String> cliente, Collection<String> codigoDelegacion,
            Collection<String> prueba, Collection<String> sector);

}
