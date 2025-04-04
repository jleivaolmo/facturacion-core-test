package com.echevarne.sap.cloud.facturacion.services.impl;

import java.util.Set;

import com.echevarne.sap.cloud.facturacion.exception.AbstractExceptionHandler;
import com.echevarne.sap.cloud.facturacion.model.BasicMessagesEntity;
import com.echevarne.sap.cloud.facturacion.model.enums.ProcessStatus;
import com.echevarne.sap.cloud.facturacion.model.masterdata.MasDataProcessControlStatus;
import com.echevarne.sap.cloud.facturacion.model.trazabilidad.TrazabilidadSolicitud;
import com.echevarne.sap.cloud.facturacion.model.trazabilidad.TrazabilidadSolicitudMessages;
import com.echevarne.sap.cloud.facturacion.model.trazabilidad.TrazabilidadSolicitudProcessStatus;
import com.echevarne.sap.cloud.facturacion.services.ProcessControlService;
import com.echevarne.sap.cloud.facturacion.services.TrazabilidadSolicitudProcessStatusService;
import com.echevarne.sap.cloud.facturacion.util.MessagesUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>Implementa la gestion del flujo de procesamiento</p
 * <p>
 * 	<li>registra Estados</li>
 *  <li>gestiona, invoca y resulve resultados de las Validaciones</li>
 * </p>
 * @author Hernan Girardi
 * @since 29/04/2020
 */
@Service("processControlSrv")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class ProcessControlServiceImpl extends AbstractExceptionHandler implements ProcessControlService {

	@Autowired
	TrazabilidadSolicitudProcessStatusService trzSolProcStatusSrv;

	@Autowired
	MasDataProcessControlStatusServiceImpl processStatusSrv;

	@Override
	public <T extends BasicMessagesEntity> void saveSolicitud(TrazabilidadSolicitud trazabilidad,
			Set<T> messages, MasDataProcessControlStatus status) {
		TrazabilidadSolicitudProcessStatus trazabilidadStatus = new TrazabilidadSolicitudProcessStatus();
		trazabilidadStatus.setTrazabilidad(trazabilidad);
		trazabilidadStatus.setProcessControlStatus(status);
		trazabilidadStatus.setSequenceOrder(trazabilidad.getOrderProceso());
		Set<TrazabilidadSolicitudMessages> msgTraza = MessagesUtils.mapMessagesToTrazSolMessages(messages);
		trazabilidadStatus.setTrzSolMessages(msgTraza);
		trazabilidadStatus.setHaveErrors(MessagesUtils.haveErrors(messages));
		trazabilidad.addEstadosProceso(trazabilidadStatus);
		trzSolProcStatusSrv.create(trazabilidadStatus);
	}

	@Override
	public <T extends BasicMessagesEntity> void saveSolicitud(TrazabilidadSolicitud trazabilidad,
			Set<T> messages,
			String processStatus) {
		MasDataProcessControlStatus status = processStatusSrv.findByCodeStatus(processStatus);
		saveSolicitud(trazabilidad, messages, status);
	}

    @Override
    public <T extends BasicMessagesEntity> void saveSolicitudRecepcionada(TrazabilidadSolicitud trazabilidad,
    		Set<T> messages) {
        MasDataProcessControlStatus status = getStatus(ProcessStatus.RECEPCIONADA.getValue());
        saveSolicitud(trazabilidad, messages, status);
    }

    @Override
    public <T extends BasicMessagesEntity> void saveSolicitudSimulada(TrazabilidadSolicitud trazabilidad,
    		Set<T> messages) {
        MasDataProcessControlStatus status = getStatus(ProcessStatus.SIMULADA.getValue());
        saveSolicitud(trazabilidad, messages, status);
    }

    @Override
    public <T extends BasicMessagesEntity> void saveSolicitudClasificada(TrazabilidadSolicitud trazabilidad,
    		Set<T> messages) {
        MasDataProcessControlStatus status = getStatus(ProcessStatus.CLASIFICADA.getValue());
        saveSolicitud(trazabilidad, messages, status);
    }

    @Override
    public <T extends BasicMessagesEntity> void saveSolicitudTranformada(TrazabilidadSolicitud trazabilidad,
    		Set<T> messages) {
        MasDataProcessControlStatus status = getStatus(ProcessStatus.TRANSFORMADA.getValue());
        saveSolicitud(trazabilidad, messages, status);
    }

    @Override
	public <T extends BasicMessagesEntity> void saveSolicitudInterlocutores(TrazabilidadSolicitud trazabilidad,
			Set<T> messages) {
    	MasDataProcessControlStatus status = getStatus(ProcessStatus.INTERLOCUTORES.getValue());
        saveSolicitud(trazabilidad, messages, status);
	}


    /**
     *
     * @param processStatus
     * @return status
     */
    private MasDataProcessControlStatus getStatus(String processStatus) {
    	return processStatusSrv.findByCodeStatus(processStatus);
    }





}
