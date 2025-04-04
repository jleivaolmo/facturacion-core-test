package com.echevarne.sap.cloud.facturacion.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.echevarne.sap.cloud.facturacion.model.entities.EntityListNameText;

/**
 * Class for repository {@link EntityListNameTextRep}.
 * 
 * <p>. . .</p>
 * <p>Repository for the Model: EntityListNameText</p>
 *
 * @author Germán Laso
 * @version 1.0
 * @since 20/04/2020
 * 
 */
@Repository("entityListNameTextRep")
public interface EntityListNameTextRep extends JpaRepository<EntityListNameText, Long> {

	Optional<EntityListNameText> findById(Long id);

}
