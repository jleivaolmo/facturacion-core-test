package com.echevarne.sap.cloud.facturacion.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.echevarne.sap.cloud.facturacion.model.solicitudrecibida.PetMuesEstado;

/**
 * Class for repository {@link PetMuesEstadoRep}.
 * 
 * <p>. . .</p>
 * <p>Repository for the Model: PetMuesEstado</p>
 *
 * @author Steven Ricardo Mendez Brenes
 * @version 1.0
 * @since 06/24/2019
 * 
 */
@Repository("petMuesEstadoRep")
public interface PetMuesEstadoRep extends JpaRepository<PetMuesEstado, Long> {

	Optional<PetMuesEstado> findById(Long id);

}
