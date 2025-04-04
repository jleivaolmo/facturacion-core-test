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

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;

@Component(ParcialmenteProcesada.CODIGO)
@Slf4j
public class ParcialmenteProcesada extends FacturacionEstado {

	public static final String CODIGO = "PP";

	@Override
	public boolean doTransicion(SolicitudIndividual solicitudIndividual, MasDataEstado estadoOrigen, boolean manual) {
		boolean algunaFacturada = solicitudIndividual.getItems().stream().filter(x -> x.estaFacturada()).findAny().isPresent();
		boolean todasFacturadas = solicitudIndividual.getItems().stream().allMatch(x -> x.estaTotalmenteFacturadaONoEsFacturable());
		boolean esPedidoCreado = validacionesSolInd.hasItemsStatus(solicitudIndividual, PedidoCreado.CODIGO).isValid();
		boolean esPrefacturada = validacionesSolInd.hasItemsStatus(solicitudIndividual, Prefacturada.CODIGO).isValid();
		if (log.isTraceEnabled())
			log.trace("Peticion=" + solicitudIndividual.getPurchaseOrderByCustomer() + " algunaFacturada=" + algunaFacturada + " esPedidoCreado="
					+ esPedidoCreado + " esPrefacturada=" + esPrefacturada + " todasFacturadas=" + todasFacturadas);
		return algunaFacturada && !esPedidoCreado && !esPrefacturada && !todasFacturadas;
	}
	
	@Override
	public boolean doTransicionV2(SolicitudIndividual solicitudIndividual, MasDataEstado estadoOrigen, boolean manual) {
		boolean algunaFacturada = validacionesSolInd.algunaFacturada(solicitudIndividual);
		boolean todasFacturadas = validacionesSolInd.todasFacturadas(solicitudIndividual);
		boolean esPedidoCreado = validacionesSolInd.hasItemsStatusV2(solicitudIndividual, PedidoCreado.CODIGO).isValid();
		boolean esPrefacturada = validacionesSolInd.hasItemsStatusV2(solicitudIndividual, Prefacturada.CODIGO).isValid();
		//Solo pasaremos a este estado si hay pruebas facturadas y no facturadas
		return algunaFacturada && !esPedidoCreado && !esPrefacturada && !todasFacturadas;
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
		return findMotivo(CODIGO + PRUEBA);
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
