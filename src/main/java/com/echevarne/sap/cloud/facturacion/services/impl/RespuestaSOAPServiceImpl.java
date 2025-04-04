package com.echevarne.sap.cloud.facturacion.services.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.echevarne.sap.cloud.facturacion.model.facturacion.RespuestaSOAP;
import com.echevarne.sap.cloud.facturacion.repositories.RespuestaSOAPRep;
import com.echevarne.sap.cloud.facturacion.services.RespuestaSOAPService;
import com.echevarne.sap.cloud.facturacion.util.DateUtils;

import lombok.var;

@Service("respuestaSOAPService")
public class RespuestaSOAPServiceImpl extends CrudServiceImpl<RespuestaSOAP, Long> implements RespuestaSOAPService {

	private final RespuestaSOAPRep respuestaSOAPRep;

	public RespuestaSOAPServiceImpl(RespuestaSOAPRep respuestaSOAPRep) {
		super(respuestaSOAPRep);
		this.respuestaSOAPRep = respuestaSOAPRep;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void setErrRespuestaSOAP(Long idRespuestaSOAP) {
		var optRespuestaSOAP = respuestaSOAPRep.findById(idRespuestaSOAP);
		if (optRespuestaSOAP.isPresent()) {
			var respuestaSOAP = optRespuestaSOAP.get();
			respuestaSOAP.setEstado(RespuestaSOAP.ERROR);
		}
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void setEnCursoRespuestaSOAP(Long idRespuestaSOAP, String instanceUUID) {
		var optRespuestaSOAP = respuestaSOAPRep.findById(idRespuestaSOAP);
		if (optRespuestaSOAP.isPresent()) {
			var respuestaSOAP = optRespuestaSOAP.get();
			respuestaSOAP.setEstado(RespuestaSOAP.EN_CURSO);
			respuestaSOAP.setUuidInstance(instanceUUID);
		}
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void updateDateFromRespuestaSOAP(RespuestaSOAP respuestaSOAP) {
		respuestaSOAP.setLastUpdatedTimestamp(DateUtils.getTimestampNow());
	}
	
	

}
