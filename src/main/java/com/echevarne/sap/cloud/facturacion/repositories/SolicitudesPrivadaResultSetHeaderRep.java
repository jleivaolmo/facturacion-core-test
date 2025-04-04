package com.echevarne.sap.cloud.facturacion.repositories;

import com.echevarne.sap.cloud.facturacion.repositories.dto.ResultSetData;

public interface SolicitudesPrivadaResultSetHeaderRep {

    void saveSolPrivResultSetHeader(ResultSetData header);
    ResultSetData retrieveSolPrivResultSetHeaderByUUID(String uuid);
    ResultSetData retrieveSolPrivResultSetHeaderByURI(String uri);
    void deleteSolPrivResultSetHeader(String uuid);

}