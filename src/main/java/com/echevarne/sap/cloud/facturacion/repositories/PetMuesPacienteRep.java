package com.echevarne.sap.cloud.facturacion.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.echevarne.sap.cloud.facturacion.model.solicitudrecibida.PetMuesPaciente;

/**
 * Class for repository {@link PetMuesPacienteRep}.
 * 
 * <p>. . .</p>
 * <p>Repository for the Model: PetMuesPaciente</p>
 *
 * @author Steven Ricardo Mendez Brenes
 * @version 1.0
 * @since 06/24/2019
 * 
 */
@Repository("petMuesPacienteRep")
public interface PetMuesPacienteRep extends JpaRepository<PetMuesPaciente, Long> {

	Optional<PetMuesPaciente> findById(Long id);

}
