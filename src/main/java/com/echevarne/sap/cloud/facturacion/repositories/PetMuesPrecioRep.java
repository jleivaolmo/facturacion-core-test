package com.echevarne.sap.cloud.facturacion.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.echevarne.sap.cloud.facturacion.model.solicitudrecibida.PetMuesPrecio;

/**
 * Class for repository {@link PetMuesPrecioRep}.
 * 
 * <p>. . .</p>
 * <p>Repository for the Model: PetMuesPrecio</p>
 *
 * @author Steven Ricardo Mendez Brenes
 * @version 1.0
 * @since 06/24/2019
 * 
 */
@Repository("petMuesPrecioRep")
public interface PetMuesPrecioRep extends JpaRepository<PetMuesPrecio, Long> {

	Optional<PetMuesPrecio> findById(Long id);

}
