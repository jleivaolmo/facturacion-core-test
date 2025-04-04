package com.echevarne.sap.cloud.facturacion.services.impl;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.echevarne.sap.cloud.facturacion.repositories.SolicitudesPrivadaResultSetCacheRep;
import com.echevarne.sap.cloud.facturacion.repositories.SolicitudesPrivadaResultSetHeaderRep;
import com.echevarne.sap.cloud.facturacion.repositories.dto.ResultSetData;

@Service
public class SolicitudesPrivadaResultSetCacheServiceImpl implements SolicitudesPrivadaResultSetCacheService {

	@Autowired
	private SolicitudesPrivadaResultSetCacheRep repoDetalle;
	
	@Autowired
	private SolicitudesPrivadaResultSetHeaderRep repoCabecera;	

	@Override
	@Transactional
	public void saveSolPrivResultSet(ResultSetData cabecera) {
		repoCabecera.saveSolPrivResultSetHeader(cabecera);
		repoDetalle.saveSolPrivResultSet(cabecera.getUuid(), cabecera.getIdList());
	}
	
	@Override
	public ResultSetData retrieveSolPrivResultSetByURI(String uri, int maxAgeSeconds) {
		ResultSetData rsdata= repoCabecera.retrieveSolPrivResultSetHeaderByURI(uri);
		if (rsdata==null) return null;
		if (new Timestamp(System.currentTimeMillis()-1000L*maxAgeSeconds).after(rsdata.getLastUpdated())) {
			return null; // no empleamos datos con edad superior a maxAgeSeconds, forzamos query normal
		}
		rsdata.setIdList(repoDetalle.retrieveSolPrivResultSet(rsdata.getUuid()));
		return rsdata;
	}

	@Override
	public ResultSetData retrieveSolPrivResultSetByUUID(String uuid, int maxAgeSeconds) {
		// @TODO no se emplea por ahora
		return null;
	}
	
    @Override
    @Transactional    
    public void deleteSolPrivResultSet(String uuid) {
    	// suficiente con borrar la cabecera por la constraint de cascade delete en BD
    	repoCabecera.deleteSolPrivResultSetHeader(uuid);
    }
}
