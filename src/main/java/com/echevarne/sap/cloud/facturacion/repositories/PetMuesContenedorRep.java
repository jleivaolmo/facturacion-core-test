package com.echevarne.sap.cloud.facturacion.repositories;

import com.echevarne.sap.cloud.facturacion.model.solicitudrecibida.PetMuesClinicos;
import com.echevarne.sap.cloud.facturacion.model.solicitudrecibida.PetMuesContenedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Class for repository {@link PetMuesContenedorRep}.
 * 
 * <p>. . .</p>
 * <p>Repository for the Model: PetMuesClinicos</p>
 *
 * @author Steven Ricardo Mendez Brenes
 * @version 1.0
 * @since 06/24/2019
 * 
 */
@Repository("petMuesContenedorRep")
public interface PetMuesContenedorRep extends JpaRepository<PetMuesContenedor, Long> {

	PetMuesContenedor findById(String id);

}
