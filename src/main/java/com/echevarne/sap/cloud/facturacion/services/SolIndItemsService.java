package com.echevarne.sap.cloud.facturacion.services;

import java.util.List;

import com.echevarne.sap.cloud.facturacion.model.solicitudindividual.SolIndItems;
import com.echevarne.sap.cloud.facturacion.model.solicitudindividual.SolicitudIndividual;

/**
 * Class for services {@link SolIndItemsService}.
 *
 * <p>
 * . . .
 * </p>
 * <p>
 * Services for the bussiness logic of the Model: SolIndItems
 * </p>
 *
 * @author Steven Ricardo Mendez Brenes
 * @version 1.0
 * @since 06/24/2019
 *
 */
public interface SolIndItemsService extends CrudService<SolIndItems, Long> {
	List<SolIndItems> findAllByIdWithTrazabilidadAndSolicitudIndAndItsTrazabilidad(List<Long> idSolItems);

	List<SolIndItems> findAllBySolicitudIndAndHigherLevelltemAndSalesOrderItemCategory(SolicitudIndividual si, int higherLevelItem,
			String salesOrderItemCategory);
}