package com.echevarne.sap.cloud.facturacion.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.echevarne.sap.cloud.facturacion.model.entities.EntityFieldNameText;

/**
 * Class for repository {@link EntityFieldNameTextRep}.
 * 
 * <p>. . .</p>
 * <p>Repository for the Model: EntityFieldNameText</p>
 *
 * @author Germán Laso
 * @version 1.0
 * @since 20/04/2020
 * 
 */
@Repository("entityFieldNameTextRep")
public interface EntityFieldNameTextRep extends JpaRepository<EntityFieldNameText, Long> {

	Optional<EntityFieldNameText> findById(Long id);

}
