package com.echevarne.sap.cloud.facturacion.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.echevarne.sap.cloud.facturacion.model.EntityVersionHistory;

/**
 * Class for the repository{@link EntityVersionHistoryRep}.
 * 
 * <p>This is a the Entity repository for Version History. . .</p>
 *
 * @author Steven Ricardo Mendez Brenes
 * @version 1.0
 * @since 06/24/2019
 * 
 */
@Repository("entityVersionHistoryRep")
public interface EntityVersionHistoryRep  extends JpaRepository<EntityVersionHistory, Long> {

	EntityVersionHistory findByClassNameAndEntityIdAndVersion(String canonicalName, Long id, long entityVersion);

}
