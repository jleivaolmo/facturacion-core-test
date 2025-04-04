package com.echevarne.sap.cloud.facturacion.services;

import java.util.List;
import java.util.Set;
import com.echevarne.sap.cloud.facturacion.model.BasicMessagesEntity;
import com.echevarne.sap.cloud.facturacion.model.solicitudagrupada.SolicitudesAgrupadas;
import com.echevarne.sap.cloud.facturacion.model.solicitudindividual.SolIndItems;
import com.echevarne.sap.cloud.facturacion.model.solicitudindividual.SolicitudIndividual;

/**
 * Class for services {@link AgrupacionService}.
 * 
 * <p>Services for the business logic of the Model: SolicitudesAgrupadas</p>
 *
 */
public interface AgrupacionService extends CrudService<SolicitudesAgrupadas, Long> {

	public List<SolicitudesAgrupadas> findAll();
	
	public SolicitudesAgrupadas buildIndividual(SolicitudIndividual solicitudIndividual, boolean esPrivados);
	
	public SolicitudesAgrupadas buildMultiple(List<SolicitudIndividual> solicitudIndividual, boolean esPrivados);

	public void agruparItems(SolicitudesAgrupadas solicitudesAgrupadas, SolicitudIndividual si, boolean esPrivados) throws Exception;
	
	public void agruparIdItems(SolicitudesAgrupadas solicitudesAgrupadas, List<Long> items, boolean esPrivados) throws Exception;
	
	public void agruparIdItemsByTmpTable(SolicitudesAgrupadas solicitudesAgrupadas, List<Long> items, boolean esPrivados) throws Exception;
	
	public void agruparItems(SolicitudesAgrupadas solicitudesAgrupadas, List<SolIndItems> items, boolean esPrivados) throws Exception;

    public SolicitudesAgrupadas doSaveSolicitudesAgrupadas(SolicitudesAgrupadas solicitudAgrupada, Set<BasicMessagesEntity> messages);

	public void agruparIdItemsNoCompact(Long idSolAgr, List<Long> idSolItems) throws Exception;

}
