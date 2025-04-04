package com.echevarne.sap.cloud.facturacion.services;

import java.util.Calendar;
import java.util.List;
import java.util.Set;
import com.echevarne.sap.cloud.facturacion.model.liquidaciones.PropuestaLiquidacion;
import com.echevarne.sap.cloud.facturacion.model.masterdata.MasDataTipoPosicion;

/**
 * Business Services logic for Model {@link MasDataTipoPosicion}
 * 
 * @author Germán Laso
 * @since 27/08/2020
 */
public interface PropuestaLiquidacionService extends CrudService<PropuestaLiquidacion, Long> {

	Long controlConcurrenciaLiquidacion(Integer tipoLiquidacion, String sociedad, String proveedor, String grupoProveedor, Calendar calFechaIni,
			Calendar calFechaFin, String nombreProceso, String codUsuario, String nombreUsuario);

	void finalizaControlConcurrenciaLiquidacion(Long idCtrl);

	Set<PropuestaLiquidacion> findByUuidInstanceInProcess(String instanceUUID);

	@SuppressWarnings("rawtypes")
	public void marcarPeticiones(List listId, int indexIdSol, Set<Long> idsCtrl, Set<Long> listaPetMarcadas);

}
