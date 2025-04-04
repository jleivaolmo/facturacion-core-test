package com.echevarne.sap.cloud.facturacion.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.echevarne.sap.cloud.facturacion.model.solicitudrecibida.PetMuesFechas;

/**
 * Class for repository {@link PetMuesFechasRep}.
 * 
 * <p>. . .</p>
 * <p>Repository for the Model: PetMuesFechas</p>
 *
 * @author Steven Ricardo Mendez Brenes
 * @version 1.0
 * @since 06/24/2019
 * 
 */
@Repository("petMuesFechasRep")
public interface PetMuesFechasRep extends JpaRepository<PetMuesFechas, Long> {

	PetMuesFechas findById(String id);

}
