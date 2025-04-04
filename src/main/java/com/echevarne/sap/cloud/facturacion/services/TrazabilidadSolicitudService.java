package com.echevarne.sap.cloud.facturacion.services;

import com.echevarne.sap.cloud.facturacion.model.solicitudrecibida.PeticionMuestreo;
import com.echevarne.sap.cloud.facturacion.model.trazabilidad.TrazabilidadSolicitud;

/**
 * Class for services {@link TrazabilidadSolicitudService}.
 * 
 * <p>. . .</p>
 * <p>Services for the bussiness logic of the Model: TrazabilidadSolicitud</p>
 *
 * @author Steven Ricardo Mendez Brenes
 * @version 1.0
 * @since 06/24/2019
 * 
 */
public interface TrazabilidadSolicitudService extends CrudService<TrazabilidadSolicitud, Long> {

    TrazabilidadSolicitud findByPeticionMuestreo(PeticionMuestreo pm);

}