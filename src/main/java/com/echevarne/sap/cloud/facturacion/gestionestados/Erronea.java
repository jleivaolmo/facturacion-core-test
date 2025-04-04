package com.echevarne.sap.cloud.facturacion.gestionestados;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import com.echevarne.sap.cloud.facturacion.constants.ConstEstados;
import com.echevarne.sap.cloud.facturacion.constants.ConstFacturacion;
import com.echevarne.sap.cloud.facturacion.model.masterdata.MasDataEstado;
import com.echevarne.sap.cloud.facturacion.model.masterdata.MasDataMotivosEstado;
import com.echevarne.sap.cloud.facturacion.model.solicitudagrupada.SolAgrItems;
import com.echevarne.sap.cloud.facturacion.model.solicitudagrupada.SolicitudesAgrupadas;
import com.echevarne.sap.cloud.facturacion.model.solicitudindividual.SolIndItems;
import com.echevarne.sap.cloud.facturacion.model.solicitudindividual.SolicitudIndividual;
import com.echevarne.sap.cloud.facturacion.model.solicitudrecibida.PeticionMuestreo;
import com.echevarne.sap.cloud.facturacion.model.solicitudrecibida.PeticionMuestreoItems;
import com.echevarne.sap.cloud.facturacion.model.solicitudrecibida.SolicitudMuestreo;

@Component(Erronea.CODIGO)
public class Erronea extends FacturacionEstado {

	public static final String CODIGO = "ERR";


	@Override
	public boolean doTransicion(PeticionMuestreoItems peticionMuestreoItem, MasDataEstado estadoOrigen, boolean manual) {
		if (peticionMuestreoItem.getTrazabilidad()!=null && peticionMuestreoItem.getTrazabilidad().getItemInd()!=null) {
			return doTransicion(peticionMuestreoItem.getTrazabilidad().getItemInd(),estadoOrigen,manual);
		} else {
			return false;
		}
	}
	
	@Override
	public boolean doTransicion(SolicitudIndividual solicitudIndividual, MasDataEstado estadoOrigen, boolean manual) {
		boolean simulacionIncorrecta = !validacionesSolInd.hasSimulatedCorrectly(solicitudIndividual).isValid();
		boolean itemsErroneos = solicitudIndividual.getItems().stream().anyMatch(x -> x.esErronea());
		boolean puedeDesbloquear = validacionesSolInd.puedeDesbloquear(solicitudIndividual, estadoOrigen, manual).isValid();
		boolean hayPruebasDiferenteTipoEnMismoPerfil = !validacionesSolInd.checkPruebasPerfilesNoExcluidos(solicitudIndividual).isValid();
		// La simulación ha sido errónea o hay pruebas erroneas o pruebas diferente tipo en el mismo perfil
		return ( simulacionIncorrecta || itemsErroneos || hayPruebasDiferenteTipoEnMismoPerfil ) && puedeDesbloquear;
	}

	@Override
	public boolean doTransicion(SolIndItems solicitudIndividualItem, MasDataEstado estadoOrigen, boolean manual) {
		boolean tienePrecio = solicitudIndividualItem.obtenerPriceBrutoOModificado().isPresent();
		boolean tieneImpuesto = solicitudIndividualItem.obtenerImpuestos().isPresent();
		boolean cumpleCondicionZPT = validacionesSolIndItem.esZPTConCotizacion(solicitudIndividualItem).isValid();
		boolean noEsExcluidaManual = !validacionesSolIndItem.hasExcluidaManual(solicitudIndividualItem).isValid();
		boolean esExcluidaTipoCondicion = validacionesSolIndItem.tipoCondicionExcluida(solicitudIndividualItem).isValid();
		boolean cumpleCondicionPrecio = validacionesSolIndItem.hasContratoCapitativoZFI(solicitudIndividualItem).isValid();
		boolean puedeDesbloquear = validacionesSolIndItem.puedeDesbloquear(solicitudIndividualItem, estadoOrigen)
				.isValid();
		boolean hayPruebasDiferenteTipoEnMismoPerfilNoExcluido = validacionesSolIndItem.esPerfilConPruebasDiferenteTipo(solicitudIndividualItem);
		boolean esPerfilErroneo = hayPruebasDiferenteTipoEnMismoPerfilNoExcluido || validacionesSolIndItem.esPerfilConPruebasErroneas(solicitudIndividualItem);
		boolean cumpleConceptoFacturacion = validacionesSolIndItem.tieneConcepto(solicitudIndividualItem);
		boolean esFacturable = validacionesSolIndItem.esFacturable(solicitudIndividualItem);

		return (!tienePrecio || !tieneImpuesto || !cumpleCondicionPrecio || esPerfilErroneo || !cumpleCondicionZPT
				|| !cumpleConceptoFacturacion || !esFacturable) && noEsExcluidaManual && puedeDesbloquear
				&& !esExcluidaTipoCondicion;
	}

	@Override
	public Map<MasDataMotivosEstado, String[]> getMotivo(SolicitudMuestreo solicitudMuestreo, MasDataEstado estadoOrigen, boolean manual) {
		return findMotivo(CODIGO + SOLICITUD);
	}

	@Override
	public Map<MasDataMotivosEstado, String[]> getMotivo(SolicitudIndividual solicitudIndividual, MasDataEstado estadoOrigen, boolean manual) {
		if(!validacionesSolInd.hasSimulatedCorrectly(solicitudIndividual).isValid()){
			if (solicitudIndividual.getSoldToParty() == null) {
				return findMotivo(ConstEstados.MOTIVO_ESTADO_ERRSIMC);
			} else {
				return findMotivo(ConstEstados.MOTIVO_ESTADO_ERRSIM);
			}
		} else if (solicitudIndividual.getItems().stream().anyMatch(x -> x.esErronea())) {
			Set<MasDataMotivosEstado> motivosError = solicitudIndividual.getItems().stream().filter(x -> x.esErronea() 
					&& !ConstFacturacion.TIPO_POSICION_PERFIL.equals(x.getSalesOrderItemCategory()))
					.map(x -> x.getTrazabilidad().getUltimoMotivo()).filter(m -> m!=null).collect(Collectors.toSet());
			if (motivosError.size() == 1)
				return findMotivo(motivosError.iterator().next().getCodigo() + SOLICITUD);
			else if (motivosError.size() > 1)
				return findMotivo(ConstEstados.MOTIVO_ESTADO_ERRNQ);
			else {
				motivosError = solicitudIndividual.getItems().stream().filter(x -> x.esErronea() 
						&& ConstFacturacion.TIPO_POSICION_PERFIL.equals(x.getSalesOrderItemCategory()))
						.map(x -> x.getTrazabilidad().getUltimoMotivo()).filter(m -> m!=null).collect(Collectors.toSet());
				if (motivosError.size() >= 1)
					return findMotivo(motivosError.iterator().next().getCodigo() + SOLICITUD);
			}
		} else if (!validacionesSolInd.checkPruebasPerfilesNoExcluidos(solicitudIndividual).isValid()) {
			return findMotivo(ConstEstados.MOTIVO_ESTADO_ERRPER);
		}
		return findMotivo(CODIGO + SOLICITUD);
	}

	@Override
	public Map<MasDataMotivosEstado, String[]> getMotivo(PeticionMuestreoItems peticionMuestreoItem, MasDataEstado estadoOrigen, boolean manual) {
		return findMotivo(CODIGO + PRUEBA);
	}

	@Override
	public Map<MasDataMotivosEstado, String[]> getMotivo(SolIndItems solicitudIndividualItem,
			MasDataEstado estadoOrigen, boolean manual) {
		if (!validacionesSolInd.hasSimulatedCorrectly(solicitudIndividualItem.getSolicitudInd()).isValid()) {
			if (solicitudIndividualItem.getSolicitudInd().getSoldToParty() == null)
				return findMotivo(ConstEstados.MOTIVO_ESTADO_ERRSIMC);
			else if (validacionesSolIndItem.tieneConcepto(solicitudIndividualItem)) {
				if (validacionesSolIndItem.conceptoIgualAMaestro(solicitudIndividualItem)) {
					if (validacionesSolIndItem.tienePrecioResultadoCorrecto(solicitudIndividualItem))
						return findMotivo(ConstEstados.MOTIVO_ESTADO_ERRSC1);
					else
						return findMotivo(ConstEstados.MOTIVO_ESTADO_ERRSC2);
				} else {
					return findMotivo(ConstEstados.MOTIVO_ESTADO_ERRSC3);
				}
			} else {
				if (!validacionesSolIndItem.esFacturable(solicitudIndividualItem))
					return findMotivo(ConstEstados.MOTIVO_ESTADO_ERRSC4);

				if (validacionesSolIndItem.tienePrecioResultadoCorrecto(solicitudIndividualItem))
					return findMotivo(ConstEstados.MOTIVO_ESTADO_ERRSC1);
				else
					return findMotivo(ConstEstados.MOTIVO_ESTADO_ERRSC2);
			}
		}

		if (!validacionesSolIndItem.hasItemPrice(solicitudIndividualItem).isValid())
			return findMotivo(ConstEstados.MOTIVO_ESTADO_ERRP);
		else if (!validacionesSolIndItem.hasItemTax(solicitudIndividualItem).isValid())
			return findMotivo(ConstEstados.MOTIVO_ESTADO_ERRP2);
		else if (validacionesSolIndItem.esPerfilConPruebasErroneas(solicitudIndividualItem))
			return findMotivo(ConstEstados.MOTIVO_ESTADO_ERRPER2);
		else if (!validacionesSolIndItem.hasContratoCapitativoZFI(solicitudIndividualItem).isValid())
			return findMotivo(ConstEstados.MOTIVO_ESTADO_ERRZFI);
		else if (!validacionesSolIndItem.esZPTConCotizacion(solicitudIndividualItem).isValid())
			return findMotivo(ConstEstados.MOTIVO_ESTADO_ERRZPT);
		else if (validacionesSolIndItem.esPerfilConPruebasDiferenteTipo(solicitudIndividualItem))
			return findMotivo(ConstEstados.MOTIVO_ESTADO_ERRPER);
		else if (validacionesSolIndItem.tieneConcepto(solicitudIndividualItem)) {
			if (validacionesSolIndItem.conceptoIgualAMaestro(solicitudIndividualItem)) {
				if (validacionesSolIndItem.tienePrecioResultadoCorrecto(solicitudIndividualItem))
					return findMotivo(ConstEstados.MOTIVO_ESTADO_ERRSC1);
				else
					return findMotivo(ConstEstados.MOTIVO_ESTADO_ERRSC2);
			} else {
				return findMotivo(ConstEstados.MOTIVO_ESTADO_ERRSC3);
			}
		} else if (!validacionesSolIndItem.tieneConcepto(solicitudIndividualItem)) {
			if (!validacionesSolIndItem.esFacturable(solicitudIndividualItem))
				return findMotivo(ConstEstados.MOTIVO_ESTADO_ERRSC4);

			if (validacionesSolIndItem.tienePrecioResultadoCorrecto(solicitudIndividualItem))
				return findMotivo(ConstEstados.MOTIVO_ESTADO_ERRSC1);
			else
				return findMotivo(ConstEstados.MOTIVO_ESTADO_ERRSC2);
		} else {
			return findMotivo(CODIGO + PRUEBA);
		}
	}

	@Override
	public Map<MasDataMotivosEstado, String[]> getMotivo(PeticionMuestreo peticionMuestreo, MasDataEstado estadoOrigen, boolean manual) {
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

}
