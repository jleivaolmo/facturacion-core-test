package com.echevarne.sap.cloud.facturacion.gestionestados;

import java.util.Map;

import com.echevarne.sap.cloud.facturacion.model.masterdata.MasDataEstado;
import com.echevarne.sap.cloud.facturacion.model.masterdata.MasDataMotivosEstado;
import com.echevarne.sap.cloud.facturacion.model.solicitudagrupada.SolAgrItems;
import com.echevarne.sap.cloud.facturacion.model.solicitudagrupada.SolicitudesAgrupadas;
import com.echevarne.sap.cloud.facturacion.model.solicitudindividual.SolIndItems;
import com.echevarne.sap.cloud.facturacion.model.solicitudindividual.SolicitudIndividual;
import com.echevarne.sap.cloud.facturacion.model.solicitudrecibida.PeticionMuestreo;
import com.echevarne.sap.cloud.facturacion.model.solicitudrecibida.PeticionMuestreoItems;
import com.echevarne.sap.cloud.facturacion.model.solicitudrecibida.SolicitudMuestreo;

public interface Procesable {
	
	// Transicion de estado para SolicitudMuestreo
	default boolean doTransicion(SolicitudMuestreo solicitudMuestreo, MasDataEstado estadoOrigen, boolean manual) {
		return false;
	}
	
	default boolean doTransicionV2(SolicitudMuestreo solicitudMuestreo, MasDataEstado estadoOrigen, boolean manual) {
		return false;
	}

	// Transicion de estado para PeticionMuestreo
	default boolean doTransicion(PeticionMuestreo peticionMuestreo, MasDataEstado estadoOrigen, boolean manual) {
		return false;
	}
	
	default boolean doTransicionV2(PeticionMuestreo peticionMuestreo, MasDataEstado estadoOrigen, boolean manual) {
		return false;
	}
	
	// Transicion de estado para SolicitudIndividual
	default boolean doTransicion(SolicitudIndividual solicitudIndividual, MasDataEstado estadoOrigen, boolean manual) {
		return false;
	}

	// Transicion de estado para SolicitudIndividual
	default boolean doTransicionV2(SolicitudIndividual solicitudIndividual, MasDataEstado estadoOrigen, boolean manual) {
		return false;
	}
	
	// Transicion de estado para PeticionMuestreoItem
	default boolean doTransicion(PeticionMuestreoItems peticionMuestreoItem, MasDataEstado estadoOrigen, boolean manual) {
		return false;
	}

	// Transicion de estado para PeticionMuestreoItem
	default boolean doTransicionV2(PeticionMuestreoItems peticionMuestreoItem, MasDataEstado estadoOrigen, boolean manual) {
		return false;
	}
	
	// Transicion de estado para SolicitudIndividualItem
	default boolean doTransicion(SolIndItems solicitudIndividualItem, MasDataEstado estadoOrigen, boolean manual) {
		return false;
	}
	
	// Transicion de estado para SolicitudIndividualItem
	default boolean doTransicionV2(SolIndItems solicitudIndividualItem, MasDataEstado estadoOrigen, boolean manual) {
		return false;
	}

	default boolean doTransicion(SolAgrItems solAgrItems, MasDataEstado estadoOrigen, boolean manual) {
		return false;
	}
	
	default boolean doTransicionV2(SolAgrItems solAgrItems, MasDataEstado estadoOrigen, boolean manual) {
		return false;
	}

	default boolean doTransicion(SolicitudesAgrupadas solicitudesAgrupadas, MasDataEstado estadoOrigen, boolean manual) {
		return false;
	}

	// Transicion de estado para SolicitudIndividual
	default boolean doTransicionV2(SolicitudesAgrupadas solicitudesAgrupadas, MasDataEstado estadoOrigen, boolean manual) {
		return false;
	}

	Map<MasDataMotivosEstado, String[]> getMotivo(SolicitudMuestreo solicitudMuestreo, MasDataEstado estadoOrigen, boolean manual);

	Map<MasDataMotivosEstado, String[]> getMotivo(PeticionMuestreo peticionMuestreo, MasDataEstado estadoOrigen, boolean manual);

	Map<MasDataMotivosEstado, String[]> getMotivo(SolicitudIndividual solicitudIndividual, MasDataEstado estadoOrigen, boolean manual);

	Map<MasDataMotivosEstado, String[]> getMotivo(PeticionMuestreoItems peticionMuestreoItem, MasDataEstado estadoOrigen, boolean manual);

	Map<MasDataMotivosEstado, String[]> getMotivo(SolIndItems solicitudIndividualItem, MasDataEstado estadoOrigen, boolean manual);

	Map<MasDataMotivosEstado, String[]> getMotivo(SolicitudesAgrupadas solicitudesAgrupadas, MasDataEstado estadoOrigen);

	Map<MasDataMotivosEstado, String[]> getMotivo(SolAgrItems solAgrItems, MasDataEstado estadoOrigen);
}
