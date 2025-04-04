package com.echevarne.sap.cloud.facturacion.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.echevarne.sap.cloud.facturacion.model.GestionCambio;

/**
 * Class for the repository{@link GestionCambiosRep}.
 * 
 * <p>This is a the Entity repository for Changes management. . .</p>
 *
 * @author Steven Ricardo Mendez Brenes
 * @version 1.0
 * @since 06/24/2019
 * 
 */
@Repository("gestionCambiosRep")
public interface GestionCambiosRep extends JpaRepository<GestionCambio, Long> {
	
	@Query("SELECT gc from GestionCambio gc WHERE gc.entityId=:entityId")
	Optional<GestionCambio> findByEntityId(@Param("entityId") Long entityId);

}
