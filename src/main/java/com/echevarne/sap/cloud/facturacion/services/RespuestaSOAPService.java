package com.echevarne.sap.cloud.facturacion.services;

import com.echevarne.sap.cloud.facturacion.model.facturacion.RespuestaSOAP;

public interface RespuestaSOAPService extends CrudService<RespuestaSOAP, Long> {
	
	public void setErrRespuestaSOAP(Long idRespuestaSOAP);
	
	public void setEnCursoRespuestaSOAP(Long idRespuestaSOAP, String instanceUUID);
	
	public void updateDateFromRespuestaSOAP(RespuestaSOAP respuestaSOAP);

}
