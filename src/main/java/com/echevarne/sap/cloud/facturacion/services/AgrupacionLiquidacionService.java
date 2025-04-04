package com.echevarne.sap.cloud.facturacion.services;

import java.util.List;

import com.echevarne.sap.cloud.facturacion.model.liquidaciones.AgrupacionLiquidacion;
import com.echevarne.sap.cloud.facturacion.model.masterdata.MasDataTipoPosicion;

/**
 * Business Services logic for Model {@link MasDataTipoPosicion}
 * 
 * @author Germán Laso
 * @since 27/08/2020
 */
public interface AgrupacionLiquidacionService extends CrudService<AgrupacionLiquidacion, Long> {

	public List<AgrupacionLiquidacion> findByEstado(Integer estado);

	public void cambioEstadoInNewTx(Long idAgrupacionLiquidacion, int tipoLiquidacion, String metodo);
	
	void updateEnviadaByIds(List<Long> ids);
}
