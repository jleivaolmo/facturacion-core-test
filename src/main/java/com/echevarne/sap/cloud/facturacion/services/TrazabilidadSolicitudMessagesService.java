package com.echevarne.sap.cloud.facturacion.services;

import com.echevarne.sap.cloud.facturacion.model.solicitudindividual.SolIndItems;
import com.echevarne.sap.cloud.facturacion.model.trazabilidad.TrazabilidadSolicitudMessages;

public interface TrazabilidadSolicitudMessagesService extends CrudService<TrazabilidadSolicitudMessages, Long> {

	TrazabilidadSolicitudMessages getInitTransform(String codigoPeticion);

	TrazabilidadSolicitudMessages getSolIndTransform(String codigoPeticion);

	TrazabilidadSolicitudMessages getSimul(String codigoPeticion);

	TrazabilidadSolicitudMessages getFinTransform(String codigoPeticion);

	TrazabilidadSolicitudMessages getErrorClasif(String codigoPeticion, SolIndItems item);

	TrazabilidadSolicitudMessages getErrorTipologiaClasif(String codigoPeticion, SolIndItems item);

	TrazabilidadSolicitudMessages getClasif(String codigoPeticion);

	TrazabilidadSolicitudMessages getInitCreate(String codigoPeticion);

	TrazabilidadSolicitudMessages getUpdate(String codigoPeticion);

	TrazabilidadSolicitudMessages getCreate(String codigoPeticion);
	
	String getTextMessage(TrazabilidadSolicitudMessages trzSolMessages);

}
