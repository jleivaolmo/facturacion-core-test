package com.echevarne.sap.cloud.facturacion.services;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import com.echevarne.sap.cloud.facturacion.model.solicitudrecibida.PeticionMuestreo;
;

/**
 * Class for services {@link PeticionMuestreoService}.
 * 
 * <p>. . .</p>
 * <p>Services for the bussiness logic of the Model: PeticionMuestreo</p>
 *
 * @author Steven Ricardo Mendez Brenes
 * @version 1.0
 * @since 06/24/2019
 * 
 */
public interface PeticionMuestreoService extends CrudService<PeticionMuestreo, Long> {

    /**
     * 
     * Obtiene la petición según su código
     * 
     * @param string
     * @return
     */
    PeticionMuestreo findByCodigoPeticion(String string);

    /**
     * 
     * Obtiene la petición según su código
     * 
     * @param codigoPeticion
     * @return
     */
    Optional<List<PeticionMuestreo>> findByCodigosPeticion(Collection<String> codigoPeticion);

}
