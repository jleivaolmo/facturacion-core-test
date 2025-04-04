package com.echevarne.sap.cloud.facturacion.services;

import java.util.Optional;

import com.echevarne.sap.cloud.facturacion.model.masterdata.MasDataEstado;
import com.echevarne.sap.cloud.facturacion.model.trazabilidad.TrazabilidadAlbaranEstHistory;

/**
 * Class for services {@link TrazabilidadAlbEstHistoryService}.
 * <p>Services for the bussiness logic of the Model: TrazabilidadAlbaranEstHistory</p>
 * 
 * @author Germán Laso
 * @since 15/02/2021
 */
public interface TrazabilidadAlbEstHistoryService extends CrudService<TrazabilidadAlbaranEstHistory, Long> {
	
	public Optional<TrazabilidadAlbaranEstHistory> findByEstado(MasDataEstado estado);

}
