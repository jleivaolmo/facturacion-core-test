package com.echevarne.sap.cloud.facturacion.services;

import java.util.Set;

import com.echevarne.sap.cloud.facturacion.model.BasicMessagesEntity;
import com.echevarne.sap.cloud.facturacion.model.masterdata.MasDataProcessControlStatus;
import com.echevarne.sap.cloud.facturacion.model.trazabilidad.TrazabilidadSolicitud;

public interface ProcessControlService {

	/**
	 * 
	 * @param trazabilidad
	 * @param messages
	 * @param status
	 */
	public <T extends BasicMessagesEntity> void saveSolicitud(TrazabilidadSolicitud trazabilidad, 
			Set<T> messages, 
			MasDataProcessControlStatus status);

	/**
	 * 
	 * @param trazabilidad
	 * @param messages
	 * @param processStatus
	 */
	public <T extends BasicMessagesEntity> void saveSolicitud(TrazabilidadSolicitud trazabilidad, 
			Set<T> messages,
			String processStatus);
	
	/**
	 * 
	 * @param trazabilidad
	 * @param messages
	 */
    public <T extends BasicMessagesEntity> void saveSolicitudRecepcionada(TrazabilidadSolicitud trazabilidad, 
    		Set<T> messages);
    
    /**
	 * 
	 * @param trazabilidad
	 * @param messages
	 */
    public <T extends BasicMessagesEntity> void saveSolicitudInterlocutores(TrazabilidadSolicitud trazabilidad, 
    		Set<T> messages);
    
    /**
     * 
     * @param trazabilidad
     * @param messages
     */
    public <T extends BasicMessagesEntity> void saveSolicitudSimulada(TrazabilidadSolicitud trazabilidad, 
    		Set<T> messages);
    
    /**
     * 
     * @param trazabilidad
     * @param messages
     */
    public <T extends BasicMessagesEntity> void saveSolicitudClasificada(TrazabilidadSolicitud trazabilidad, 
    		Set<T> messages);
    
    /**
     * 
     * @param trazabilidad
     * @param messages
     */
    public <T extends BasicMessagesEntity> void saveSolicitudTranformada(TrazabilidadSolicitud trazabilidad, 
    		Set<T> messages);
	
}
