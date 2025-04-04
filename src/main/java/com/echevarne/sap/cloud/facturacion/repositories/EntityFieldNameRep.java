package com.echevarne.sap.cloud.facturacion.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.echevarne.sap.cloud.facturacion.model.entities.EntityFieldName;

/**
 * Class for repository {@link EntityFieldNameRep}.
 *
 * <p>. . .</p>
 * <p>Repository for the Model: EntityFieldName</p>
 *
 * @author Germán Laso
 * @version 1.0
 * @since 20/04/2020
 *
 */
@Repository("entityFieldNameRep")
public interface EntityFieldNameRep extends JpaRepository<EntityFieldName, Long> {

}
