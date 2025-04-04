package com.echevarne.sap.cloud.facturacion.services.impl;

import com.echevarne.sap.cloud.facturacion.repositories.dto.ResultSetData;

public interface SolicitudesPrivadaResultSetCacheService {

	void saveSolPrivResultSet(ResultSetData cabecera);

	ResultSetData retrieveSolPrivResultSetByURI(String uri, int maxAgeSeconds);

	ResultSetData retrieveSolPrivResultSetByUUID(String uuid, int maxAgeSeconds);
	
	void deleteSolPrivResultSet(String uuid);

}