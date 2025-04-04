package com.echevarne.sap.cloud.facturacion.services;

import java.util.Optional;

import com.echevarne.sap.cloud.facturacion.model.masterdata.MasDataEstado;
import com.echevarne.sap.cloud.facturacion.model.trazabilidad.TrazabilidadSolicitudEstHistory;

/**
 * Class for services {@link TrazabilidadSolicitudEstHistory}.
 * <p>Services for the bussiness logic of the Model: TrazabilidadSolicitudEstHistory</p>
 * 
 * @author Hernan Girardi
 * @since 23/06/2020
 */
public interface TrazabilidadSolEstHistoryService extends CrudService<TrazabilidadSolicitudEstHistory, Long> {
	
	public Optional<TrazabilidadSolicitudEstHistory> findByEstado(MasDataEstado estado);

	public Integer insertTrazabilidadEstHistory(
            String userCreate,
            boolean automatico,
            String motivoVar1,
            String motivoVar2,
            String motivoVar3,
            String motivoVar4,
            Long fkEstado,
            Long fkMotivo,
            Long fkTrazabilidad,
            boolean afectaImporte);

}
