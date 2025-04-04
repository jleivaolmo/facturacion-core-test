package com.echevarne.sap.cloud.facturacion.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.echevarne.sap.cloud.facturacion.model.solicitudrecibida.PetMuesItemPrecio;

/**
 * Class for repository {@link PetMuesItemPrecioRep}.
 * 
 * <p>. . .</p>
 * <p>Repository for the Model: PetMuesItemPrecio</p>
 *
 * @author Steven Ricardo Mendez Brenes
 * @version 1.0
 * @since 06/24/2019
 * 
 */
@Repository("petMuesItemPrecioRep")
public interface PetMuesItemPrecioRep extends JpaRepository<PetMuesItemPrecio, Long> {

	Optional<PetMuesItemPrecio> findById(Long id);

}
