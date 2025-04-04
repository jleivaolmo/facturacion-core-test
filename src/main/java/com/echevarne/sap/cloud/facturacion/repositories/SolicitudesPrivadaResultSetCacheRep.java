package com.echevarne.sap.cloud.facturacion.repositories;

import java.util.List;

public interface SolicitudesPrivadaResultSetCacheRep {

	void saveSolPrivResultSet(String key, List<Long> solPrivIds);
	List<Long> retrieveSolPrivResultSet(String key);

}