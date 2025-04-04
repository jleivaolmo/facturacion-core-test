package com.echevarne.sap.cloud.facturacion.services;

import com.echevarne.sap.cloud.facturacion.model.trazabilidad.TrazabilidadSolAgrEstHistory;

/**
 * Class for services {@link TrazabilidadSolAgrEstHistory}.
 * <p>Services for the bussiness logic of the Model: TrazabilidadSolicitudEstHistory</p>
 * 
 * @author David Bolet
 * @since 28/04/2021
 */
public interface TrazabilidadSolAgrEstHistoryService extends CrudService<TrazabilidadSolAgrEstHistory, Long> {

	public Integer insertTrazabilidadEstHistory(
            String userCreate,
            boolean automatico,
            String motivoVar1,
            String motivoVar2,
            String motivoVar3,
            String motivoVar4,
            Long fkEstado,
            Long fkMotivo,
            Long fkTrazabilidad);


}
