package com.echevarne.sap.cloud.facturacion.services;

import com.echevarne.sap.cloud.facturacion.model.solicitudrecibida.PeticionMuestreoItems;
import com.echevarne.sap.cloud.facturacion.model.trazabilidad.Trazabilidad;

import java.util.List;
import java.util.Optional;

/**
 * Class for services {@link TrazabilidadService}.
 * 
 * <p>. . .</p>
 * <p>Services for the bussiness logic of the Model: Trazabilidad</p>
 *
 * @author Steven Ricardo Mendez Brenes
 * @version 1.0
 * @since 06/24/2019
 * 
 */
public interface TrazabilidadService extends CrudService<Trazabilidad, Long> {

	// por si se necesita hacer update de forma Nativa en SQL por ID
	int updateTrazabilidaSetItemAgr(Long idItem , Long idTrz);

	Optional<Trazabilidad> findByItemRec(PeticionMuestreoItems item);

	List<Trazabilidad> getByBillingDocument(String billingDocument);
	
	Long getCountBySalesOrder(String salesOrder);

}
