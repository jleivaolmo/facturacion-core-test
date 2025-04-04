package com.echevarne.sap.cloud.facturacion.services;

import java.util.Calendar;
import java.util.List;
import com.echevarne.sap.cloud.facturacion.model.liquidaciones.ProveedorLiquidacion;

/**
 * Business Services logic for Model {@link MasDataTipoPosicion}
 * 
 * @author Germán Laso
 * @since 27/08/2020
 */
public interface ProveedorLiquidacionService extends CrudService<ProveedorLiquidacion, Long> {
	
	public List<ProveedorLiquidacion> findSolapamientos(String organizacionVentas, String delegacion,
			String remitenteProfesional, String sector, Calendar fechaIni, Calendar fechaFin);

	public List<ProveedorLiquidacion> findByProveedor(String organizacionVentas, String idProveedorLiquidacion);
	
}
