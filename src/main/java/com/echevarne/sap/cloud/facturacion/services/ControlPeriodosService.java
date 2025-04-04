package com.echevarne.sap.cloud.facturacion.services;

import com.echevarne.sap.cloud.facturacion.model.facturacion.ControlPeriodos;

/**
 * Class for services {@link ControlPeriodosService}.
 *
 * <p>. . .</p>
 * <p>Services for the bussiness logic of the Model: ControlPeriodos</p>
 *
 * @author Germán Laso
 * @version 1.0
 * @since 04/05/2021
 *
 */
public interface ControlPeriodosService extends CrudService<ControlPeriodos, Long> {

    void finalize(long id, int estado);

	void setControlPeriodosTotal(Integer total, long controlPeriodosId, String tipologia);
	
	void setControlPeriodosTotalInNewTx(Integer total, long controlPeriodosId, String tipologia);
}
