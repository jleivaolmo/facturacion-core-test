package com.echevarne.sap.cloud.facturacion.gestionestados;

import java.util.Map;

import com.echevarne.sap.cloud.facturacion.constants.ConstEstados;
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

@Component(CreadaRecibida.CODIGO)
public class CreadaRecibida extends FacturacionEstado {

	public static final String CODIGO = "CR";

	@Override
	public boolean doTransicion(SolicitudMuestreo solicitudMuestreo, MasDataEstado estadoOrigen, boolean manual) {
		return true;
	}

	@Override
	public boolean doTransicion(PeticionMuestreo peticionMuestreo, MasDataEstado estadoOrigen, boolean manual) {
		boolean puedeDesbloquear = validacionesPetMues.puedeDesbloquear(peticionMuestreo, estadoOrigen, manual).isValid();
		return puedeDesbloquear;
	}

	@Override
	public boolean doTransicion(PeticionMuestreoItems peticionMuestreoItem, MasDataEstado estadoOrigen, boolean manual) {
		boolean puedeDesbloquear = validacionesPetMuesItem.puedeDesbloquear(peticionMuestreoItem, estadoOrigen).isValid();
		return puedeDesbloquear;
	}

	@Override
	public boolean doTransicion(SolicitudIndividual solicitudIndividual, MasDataEstado estadoOrigen, boolean manual) {
		boolean simulacionIncorrecta = !validacionesSolInd.hasSimulatedCorrectly(solicitudIndividual).isValid();
		boolean itemsErroneos = solicitudIndividual.getItems().stream().anyMatch(x -> x.esErronea());
		boolean hayPruebasDiferenteTipoEnMismoPerfil = !validacionesSolInd.checkPruebasPerfilesNoExcluidos(solicitudIndividual).isValid();
		boolean esErronea = simulacionIncorrecta || itemsErroneos || hayPruebasDiferenteTipoEnMismoPerfil;
		boolean itemsFacturables = validacionesSolInd.hasItemsStatus(solicitudIndividual, Facturable.CODIGO).isValid();
		boolean esExcluida = validacionesSolInd.hasItemsStatus(solicitudIndividual,Excluida.CODIGO).isValid();
		boolean esBloqueoAutomatico = validacionesSolInd.hasItemsStatus(solicitudIndividual,Bloqueada.CODIGO).isValid();
		boolean ningunaFacturada = !validacionesSolInd.hasFacturada(solicitudIndividual).isValid();
		boolean puedeDesbloquear = validacionesSolInd.puedeDesbloquear(solicitudIndividual, estadoOrigen, manual).isValid();
		return !itemsFacturables && !esErronea && !esExcluida && !esBloqueoAutomatico && ningunaFacturada && puedeDesbloquear;
	}

	@Override
	public boolean doTransicion(SolIndItems solicitudIndividualItem, MasDataEstado estadoOrigen, boolean manual) {
		boolean doTransicion = false;

		boolean puedeDesbloquear = validacionesSolIndItem.puedeDesbloquear(solicitudIndividualItem, estadoOrigen)
				.isValid();
		if(puedeDesbloquear){
			boolean esPrivada = solicitudIndividualItem.getSolicitudInd().getTrazabilidad().getPeticionRec().isEsPrivado();
			if(!esPrivada){
				boolean contieneValidad = solicitudIndividualItem.contieneValidada();
				if(!contieneValidad){
					boolean tieneImpuesto = solicitudIndividualItem.obtenerImpuestos().isPresent();
					if(tieneImpuesto){
						boolean tienePrecio = solicitudIndividualItem.obtenerPriceBrutoOModificado().isPresent();
						if(tienePrecio){

							boolean hayPruebasDiferenteTipoEnMismoPerfil = validacionesSolIndItem.esPerfilConPruebasDiferenteTipo(solicitudIndividualItem);
							boolean esPerfilErroneo = hayPruebasDiferenteTipoEnMismoPerfil || validacionesSolIndItem.esPerfilConPruebasErroneas(solicitudIndividualItem);
							if(!esPerfilErroneo){
								doTransicion =!validacionesSolIndItem.hasExcluidaManual(solicitudIndividualItem).isValid()
										&& !validacionesSolIndItem.facturaPorFechaPeticion(solicitudIndividualItem).isValid()
										&& !validacionesSolIndItem.tipoCondicionExcluida(solicitudIndividualItem).isValid();
							}
						}
					}
				}
			}
		}

		return doTransicion;
	}

	@Override
	public boolean doTransicion(SolAgrItems items, MasDataEstado estadoOrigen, boolean manual) {
		return true;
	}

	@Override
	public boolean doTransicion(SolicitudesAgrupadas agrupadas, MasDataEstado estadoOrigen, boolean manual) {
		return estadoOrigen.getCodeEstado().equals(SinEstado.CODIGO);
	}

	@Override
	public Map<MasDataMotivosEstado, String[]> getMotivo(SolicitudMuestreo solicitudMuestreo, MasDataEstado estadoOrigen, boolean manual) {
		return findMotivo(CODIGO + SOLICITUD);
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
		boolean contieneValidad = solicitudIndividualItem.contieneValidada();
		boolean fechaPeticion = validacionesSolIndItem.facturaPorFechaPeticion(solicitudIndividualItem).isValid();
		if (!fechaPeticion && !contieneValidad) {
			return findMotivo(ConstEstados.MOTIVO_ESTADO_CRNV);
		}
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
