package com.echevarne.sap.cloud.facturacion.services;

import com.echevarne.sap.cloud.facturacion.model.masterdata.MasDataEstado;
import com.echevarne.sap.cloud.facturacion.model.masterdata.MasDataEstadoLiquidacion;

/**
 * Business Services logic for Model: {@link MasDataEstado}
 * 
 * @author Hernan Girardi
 * @since 26/03/2020
 */
public interface MasDataEstadoLiquidacionService
		extends CrudService<MasDataEstadoLiquidacion, Long>, MasDataBaseService<MasDataEstadoLiquidacion, Long> {

	MasDataEstadoLiquidacion findByCodeEstado(Integer codeEstado);

	MasDataEstadoLiquidacion getEstado(Integer codeEstado);

}
