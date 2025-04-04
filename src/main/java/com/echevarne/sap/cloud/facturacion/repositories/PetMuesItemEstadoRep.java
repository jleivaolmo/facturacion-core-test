package com.echevarne.sap.cloud.facturacion.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.echevarne.sap.cloud.facturacion.model.solicitudrecibida.PetMuesItemEstado;

/**
 * Class for repository {@link PetMuesItemEstadoRep}.
 * 
 * <p>. . .</p>
 * <p>Repository for the Model: PetMuesItemEstado</p>
 *
 * @author Steven Ricardo Mendez Brenes
 * @version 1.0
 * @since 06/24/2019
 * 
 */
@Repository("petMuesItemEstadoRep")
public interface PetMuesItemEstadoRep extends JpaRepository<PetMuesItemEstado, Long> {

	Optional<PetMuesItemEstado> findById(Long id);

}
