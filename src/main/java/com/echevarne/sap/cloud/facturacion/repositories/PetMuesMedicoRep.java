package com.echevarne.sap.cloud.facturacion.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.echevarne.sap.cloud.facturacion.model.solicitudrecibida.PetMuesMedico;

/**
 * Class for repository {@link PetMuesMedicoRep}.
 * 
 * <p>. . .</p>
 * <p>Repository for the Model: PetMuesMedico</p>
 *
 * @author Steven Ricardo Mendez Brenes
 * @version 1.0
 * @since 06/24/2019
 * 
 */
@Repository("petMuesMedicoRep")
public interface PetMuesMedicoRep extends JpaRepository<PetMuesMedico, Long> {

	Optional<PetMuesMedico> findById(Long id);

}
