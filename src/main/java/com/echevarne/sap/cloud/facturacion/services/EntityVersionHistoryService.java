package com.echevarne.sap.cloud.facturacion.services;

import com.echevarne.sap.cloud.facturacion.model.BasicEntity;
import com.echevarne.sap.cloud.facturacion.model.EntityVersionHistory;

/**
 * Interface for the service{@link EntityVersionHistoryService}.
 * 
 * <p>This is a interface for Services. . .</p>
 *
 * @author Steven Ricardo Mendez Brenes
 * @version 1.0
 * @since 06/24/2019
 * 
 */
public interface EntityVersionHistoryService extends CrudService<EntityVersionHistory, Long> {

	void persistVersions(BasicEntity obj1, BasicEntity obj2);
	
	 <T> T getInstanceVersion( Class<T> clazz, long id, long version );

	 <T> String getJsonVersion( Class<T> clazz, long id, long version );
}
