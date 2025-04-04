package com.echevarne.sap.cloud.facturacion.services;

import java.util.Calendar;
import java.util.List;
import com.echevarne.sap.cloud.facturacion.model.liquidaciones.ReglaLiquidacionProfesionales;

/**
 * Business Services logic for Model {@link MasDataTipoPosicion}
 * 
 * @author Germán Laso
 * @since 27/08/2020
 */
public interface ReglaLiquidacionProfesionalesService extends CrudService<ReglaLiquidacionProfesionales, Long> {

	public List<ReglaLiquidacionProfesionales> findReglas(String codigoCliente, String oficinaVentas, String codigoProfesional, String codigoCompania,
			String delProductiva, String undProductiva, Boolean esMuestraRemitida, String codigoPrueba, String codigoOrganizacion, String codigoConcepto,
			Integer tipoPeticion, Calendar fechaPeticion);
}
