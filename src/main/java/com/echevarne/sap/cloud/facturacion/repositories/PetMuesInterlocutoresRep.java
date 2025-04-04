package com.echevarne.sap.cloud.facturacion.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.echevarne.sap.cloud.facturacion.model.solicitudrecibida.PetMuesInterlocutores;

/**
 * Class for repository {@link PetMuesInterlocutoresRep}.
 * 
 * <p>. . .</p>
 * <p>Repository for the Model: PetMuesInterlocutores</p>
 *
 * @author Steven Ricardo Mendez Brenes
 * @version 1.0
 * @since 06/24/2019
 * 
 */
@Repository("petMuesInterlocutoresRep")
public interface PetMuesInterlocutoresRep extends JpaRepository<PetMuesInterlocutores, Long> {

	Optional<PetMuesInterlocutores> findById(Long id);

}
