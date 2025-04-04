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

import org.springframework.stereotype.Component;

@Component(Facturada.CODIGO)
public class Facturada extends FacturacionEstado {

	public static final String CODIGO = "F";

	@Override
	public boolean doTransicion(SolicitudMuestreo solicitudMuestreo, MasDataEstado estadoOrigen, boolean manual) {
		return true;
	}
	
	@Override
	public boolean doTransicionV2(SolicitudMuestreo solicitudMuestreo, MasDataEstado estadoOrigen, boolean manual) {
		return true;
	}

	@Override
	public boolean doTransicion(PeticionMuestreo peticionMuestreo, MasDataEstado estadoOrigen, boolean manual) {
		return true;
	}
	
	@Override
	public boolean doTransicionV2(PeticionMuestreo peticionMuestreo, MasDataEstado estadoOrigen, boolean manual) {
		return true;
	}

	@Override
	public boolean doTransicion(SolicitudIndividual solicitudIndividual, MasDataEstado estadoOrigen, boolean manual) {
		boolean algunaFacturada = solicitudIndividual.getItems().stream().anyMatch(x -> x.estaFacturada());
		boolean todasFacturadas = solicitudIndividual.getItems().stream().allMatch(x -> x.estaTotalmenteFacturadaONoEsFacturable());
		return algunaFacturada && todasFacturadas;
	}
	
	@Override
	public boolean doTransicionV2(SolicitudIndividual solicitudIndividual, MasDataEstado estadoOrigen, boolean manual) {
		boolean algunaFacturada = validacionesSolInd.algunaFacturada(solicitudIndividual);
		boolean todasFacturadas = validacionesSolInd.todasFacturadas(solicitudIndividual);
		return algunaFacturada && todasFacturadas;
	}

	@Override
	public boolean doTransicion(PeticionMuestreoItems peticionMuestreoItem, MasDataEstado estadoOrigen, boolean manual) {
		return true;
	}
	
	@Override
	public boolean doTransicionV2(PeticionMuestreoItems peticionMuestreoItem, MasDataEstado estadoOrigen, boolean manual) {
		return true;
	}

	@Override
	public boolean doTransicion(SolIndItems solicitudIndividualItem, MasDataEstado estadoOrigen, boolean manual) {
		return true;
	}
	
	@Override
	public boolean doTransicionV2(SolIndItems solicitudIndividualItem, MasDataEstado estadoOrigen, boolean manual) {
		return true;
	}

	@Override
	public boolean doTransicion(SolAgrItems items, MasDataEstado estadoOrigen, boolean manual) {
		//miramos que se haya forzado manualmente desde la recepcion de facturas
		return manual;
	}
	
	@Override
	public boolean doTransicionV2(SolAgrItems items, MasDataEstado estadoOrigen, boolean manual) {
		//miramos que se haya forzado manualmente desde la recepcion de facturas
		return manual;
	}

	@Override
	public boolean doTransicion(SolicitudesAgrupadas agrupadas, MasDataEstado estadoOrigen, boolean manual) {
		//miramos que se haya forzado manualmente desde la recepcion de facturas
		return manual;
	}
	
	@Override
	public boolean doTransicionV2(SolicitudesAgrupadas agrupadas, MasDataEstado estadoOrigen, boolean manual) {
		//miramos que se haya forzado manualmente desde la recepcion de facturas
		return manual;
	}

	@Override
	public Map<MasDataMotivosEstado, String[]> getMotivo(SolicitudMuestreo solicitudMuestreo, MasDataEstado estadoOrigen, boolean manual) {
		return findMotivo(CODIGO + ALBARAN);
	}

	@Override
	public Map<MasDataMotivosEstado, String[]> getMotivo(SolicitudIndividual solicitudIndividual, MasDataEstado estadoOrigen, boolean manual) {
		return findMotivo(CODIGO + SOLICITUD);
	}

	@Override
	public Map<MasDataMotivosEstado, String[]> getMotivo(PeticionMuestreoItems peticionMuestreoItem, MasDataEstado estadoOrigen, boolean manual) {
		return findMotivo(CODIGO + PRUEBA);
	}

	@Override
	public Map<MasDataMotivosEstado, String[]> getMotivo(SolIndItems solicitudIndividualItem, MasDataEstado estadoOrigen, boolean manual) {
		return findMotivo(CODIGO + PRUEBA);
	}

	@Override
	public Map<MasDataMotivosEstado, String[]> getMotivo(SolicitudesAgrupadas solicitudesAgrupadas, MasDataEstado estadoOrigen) {
		return findMotivo(CODIGO + SOLICITUD);
	}

	@Override
	public Map<MasDataMotivosEstado, String[]> getMotivo(SolAgrItems solAgrItems, MasDataEstado estadoOrigen) {
		return findMotivo(CODIGO + PRUEBA);
	}

	@Override
	public Map<MasDataMotivosEstado, String[]> getMotivo(PeticionMuestreo peticionMuestreo, MasDataEstado estadoOrigen, boolean manual) {
		return findMotivo(CODIGO + SOLICITUD);
	}

}
