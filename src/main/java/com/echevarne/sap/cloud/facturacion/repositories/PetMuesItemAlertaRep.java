package com.echevarne.sap.cloud.facturacion.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.echevarne.sap.cloud.facturacion.model.solicitudrecibida.PetMuesItemAlerta;

/**
 * Class for repository {@link PetMuesItemAlertaRep}.
 * 
 * <p>. . .</p>
 * <p>Repository for the Model: PetMuesItemAlerta</p>
 *
 * @author Steven Ricardo Mendez Brenes
 * @version 1.0
 * @since 06/24/2019
 * 
 */
@Repository("petMuesItemAlertaRep")
public interface PetMuesItemAlertaRep extends JpaRepository<PetMuesItemAlerta, Long> {

	Optional<PetMuesItemAlerta> findById(Long id);

}
