package com.echevarne.sap.cloud.facturacion.services;

import java.sql.Timestamp;
import java.util.List;

import com.echevarne.sap.cloud.facturacion.model.masterdata.MasDataEstado;
import com.echevarne.sap.cloud.facturacion.model.solicitudrecibida.PeticionMuestreoItems;

/**
 * Class for services {@link PeticionMuestreoItemsService}.
 * 
 * <p>. . .</p>
 * <p>Services for the bussiness logic of the Model: PeticionMuestreoItems</p>
 *
 * @author Steven Ricardo Mendez Brenes
 * @version 1.0
 * @since 06/24/2019
 * 
 */
public interface PeticionMuestreoItemsService extends CrudService<PeticionMuestreoItems, Long> {

    List<PeticionMuestreoItems> findPruebasMixtasPorEstadoYFecha(Timestamp from, MasDataEstado estado);

}
