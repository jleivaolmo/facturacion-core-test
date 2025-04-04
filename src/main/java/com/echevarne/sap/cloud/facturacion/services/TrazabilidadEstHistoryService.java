package com.echevarne.sap.cloud.facturacion.services;

import java.util.Optional;
import com.echevarne.sap.cloud.facturacion.model.masterdata.MasDataEstado;
import com.echevarne.sap.cloud.facturacion.model.trazabilidad.TrazabilidadEstHistory;

/**
 * Class for services {@link TrazabilidadEstHistory}.
 * <p>Services for the bussiness logic of the Model: TrazabilidadEstHistory</p>
 * 
 * @author Hernan Girardi
 * @since 23/06/2020
 */
public interface TrazabilidadEstHistoryService extends CrudService<TrazabilidadEstHistory, Long> {
	
	public Optional<TrazabilidadEstHistory> findByEstado(MasDataEstado estado);
	
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
