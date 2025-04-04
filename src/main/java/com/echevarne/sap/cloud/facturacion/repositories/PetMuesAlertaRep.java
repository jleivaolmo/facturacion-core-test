package com.echevarne.sap.cloud.facturacion.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.echevarne.sap.cloud.facturacion.model.solicitudrecibida.PetMuesAlerta;

/**
 * Class for repository {@link PetMuesAlertaRep}.
 * 
 * <p>. . .</p>
 * <p>Repository for the Model: PetMuesAlerta</p>
 *
 * @author Steven Ricardo Mendez Brenes
 * @version 1.0
 * @since 06/24/2019
 * 
 */
@Repository("petMuesAlertaRep")
public interface PetMuesAlertaRep extends JpaRepository<PetMuesAlerta, Long> {

	Optional<PetMuesAlerta> findById(Long id);

}
