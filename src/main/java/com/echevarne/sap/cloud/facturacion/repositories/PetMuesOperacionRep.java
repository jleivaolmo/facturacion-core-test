package com.echevarne.sap.cloud.facturacion.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.echevarne.sap.cloud.facturacion.model.solicitudrecibida.PetMuesOperacion;

/**
 * Class for repository {@link PetMuesOperacionRep}.
 * 
 * <p>. . .</p>
 * <p>Repository for the Model: PetMuesOperacion</p>
 *
 * @author Steven Ricardo Mendez Brenes
 * @version 1.0
 * @since 06/24/2019
 * 
 */
@Repository("petMuesOperacionRep")
public interface PetMuesOperacionRep extends JpaRepository<PetMuesOperacion, Long> {

	Optional<PetMuesOperacion> findById(Long id);

}
