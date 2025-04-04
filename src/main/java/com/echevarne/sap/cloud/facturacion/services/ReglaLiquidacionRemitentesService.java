package com.echevarne.sap.cloud.facturacion.services;

import java.util.Calendar;
import java.util.List;
import com.echevarne.sap.cloud.facturacion.model.liquidaciones.ReglaLiquidacionRemitentes;

/**
 * Business Services logic for Model {@link MasDataTipoPosicion}
 * 
 * @author Germán Laso
 * @since 27/08/2020
 */
public interface ReglaLiquidacionRemitentesService extends CrudService<ReglaLiquidacionRemitentes, Long> {
	
	public List<ReglaLiquidacionRemitentes> findReglas(String codigoCliente, String oficinaVentas, String codigoRemitente, String codigoCompania,
			String delProductiva, String undProductiva, Boolean esMuestraRemitida, String codigoPrueba, String codigoOrganizacion, String codigoConcepto,
			Integer tipoPeticion, Calendar fechaPeticion);
}

