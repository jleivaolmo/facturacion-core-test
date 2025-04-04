package com.echevarne.sap.cloud.facturacion.services;

import java.util.List;

import com.echevarne.sap.cloud.facturacion.model.masterdata.MasDataEstado;
import com.echevarne.sap.cloud.facturacion.model.masterdata.MasDataTransicionEstado;

public interface MasDataTransicionEstadoService extends CrudService<MasDataTransicionEstado, Long>, MasDataBaseService<MasDataTransicionEstado, Long> {
	List<MasDataTransicionEstado> findAllByOrigenAndAutomatico(MasDataEstado estadoOrigen, boolean b);

	void reloadData();

    List<MasDataTransicionEstado> findAllActiveByOrigenCodeAndDestinoCode(MasDataEstado estadoOrigen, List<String> estadosDestino);
    
    List<MasDataTransicionEstado> findAllActiveByOrigenCodeAndDestinoCode(String estadoOrigen, List<String> estadosDestino);

}
