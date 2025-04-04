package com.echevarne.sap.cloud.facturacion.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.echevarne.sap.cloud.facturacion.model.solicitudrecibida.PetMuesClinicos;

/**
 * Class for repository {@link PetMuesClinicosRep}.
 * 
 * <p>. . .</p>
 * <p>Repository for the Model: PetMuesClinicos</p>
 *
 * @author Steven Ricardo Mendez Brenes
 * @version 1.0
 * @since 06/24/2019
 * 
 */
@Repository("petMuesClinicosRep")
public interface PetMuesClinicosRep extends JpaRepository<PetMuesClinicos, Long> {

	PetMuesClinicos findById(String id);

}
