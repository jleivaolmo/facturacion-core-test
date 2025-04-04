package com.echevarne.sap.cloud.facturacion.gestionestados;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
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

@Component(PedidoAbono.CODIGO)
public class PedidoAbono extends FacturacionEstado {

	public static final String CODIGO = "PA";

	@Override
	public boolean doTransicion(PeticionMuestreo peticionMuestreo, MasDataEstado estadoOrigen, boolean manual) {
		return true;
	}

	@Override
	public boolean doTransicion(SolicitudIndividual solicitudIndividual, MasDataEstado estadoOrigen, boolean manual) {
		Set<String> estadosSet = Stream.of(CODIGO, Excluida.CODIGO).collect(Collectors.toSet());
		boolean todasPedidoAbono = solicitudIndividual.getItems().stream().allMatch(x -> estadosSet.contains(x.obtieneTrazabilidad().getUltimoEstado()));
		return todasPedidoAbono;
	}

	@Override
	public boolean doTransicion(PeticionMuestreoItems peticionMuestreoItem, MasDataEstado estadoOrigen, boolean manual) {
		return true;
	}

	@Override
	public boolean doTransicion(SolIndItems solicitudIndividualItem, MasDataEstado estadoOrigen, boolean manual) {
		return true;
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
