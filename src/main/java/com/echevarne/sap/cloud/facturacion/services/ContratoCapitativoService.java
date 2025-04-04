package com.echevarne.sap.cloud.facturacion.services;

import java.util.Calendar;
import java.util.List;

import com.echevarne.sap.cloud.facturacion.model.contratos.ContratoCapitativo;

/**
 * Class for services {@link ContratoCapitativoService}.
 * 
 * <p>. . .</p>
 * <p>Services for the bussiness logic of the Model: ContratoCapitativo</p>
 *
 * @author Germán Laso
 * @version 1.0
 * @since 17/04/2020
 * 
 */
public interface ContratoCapitativoService extends CrudService<ContratoCapitativo, Long> {
	
	List<ContratoCapitativo> findContratoActivo(String OrganizacionVenta, String codigoCliente, Calendar startDate, Calendar endDate);

	boolean existsContratoCapitativo(Long id);
	
	void createAssociatedEntities(Long id);
}
