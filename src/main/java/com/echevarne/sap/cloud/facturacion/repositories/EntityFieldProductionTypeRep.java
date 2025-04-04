package com.echevarne.sap.cloud.facturacion.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.echevarne.sap.cloud.facturacion.model.entities.EntityFieldProductionType;

/**
 * Class for repository {@link EntityFieldProductionTypeRep}.
 * 
 * @author davidbolet
 * @version 1.0
 * @since 29/01/2021
 */
@Repository("entityFieldProductionTypeRep")
public interface EntityFieldProductionTypeRep extends JpaRepository<EntityFieldProductionType,Long> {

}
