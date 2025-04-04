package com.echevarne.sap.cloud.facturacion.gestionestados;

import java.util.Calendar;
import java.util.Map;
import java.util.Objects;

import org.springframework.stereotype.Component;

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
import com.echevarne.sap.cloud.facturacion.util.DateUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component(Facturable.CODIGO)
public class Facturable extends FacturacionEstado {

	public static final String CODIGO = "FB";
	public static final String ANY = "*";
	public static final String CLIENTE = "cliente";
	public static final String OFICINA_VENTAS = "oficinaVentas";
	public static final String TIPO_PETICION = "tipoPeticion";
	public static final String COMPANIA = "compania";
	public static final String EMPRESA = "empresa";
	public static final String REMITENTE = "remitente";

	@Override
	public boolean doTransicion(PeticionMuestreo peticionMuestreo, MasDataEstado estadoOrigen, boolean manual) {
		boolean noHayAlertas = !validacionesPetMues.hasAlertasBloqueantes(peticionMuestreo).isValid();
		boolean itemsFacturables = validacionesPetMues.hasItemsStatus(peticionMuestreo, CODIGO).isValid();
		boolean puedeDesbloquear = validacionesPetMues.puedeDesbloquear(peticionMuestreo, estadoOrigen, manual)
				.isValid();
		log.info("peticion=" + peticionMuestreo.getCodigoPeticion() + " noHayAlertas=" + noHayAlertas + " itemsFacturables=" + itemsFacturables
				+ " puedeDesbloquear=" + puedeDesbloquear);
		return noHayAlertas && itemsFacturables && puedeDesbloquear;
	}
	
	@Override
	public boolean doTransicionV2(PeticionMuestreo peticionMuestreo, MasDataEstado estadoOrigen, boolean manual) {
		boolean noHayAlertas = !validacionesPetMues.hasAlertasBloqueantes(peticionMuestreo).isValid();
		boolean itemsFacturables = validacionesPetMues.hasItemsStatus(peticionMuestreo, CODIGO).isValid();
		boolean puedeDesbloquear = validacionesPetMues.puedeDesbloquear(peticionMuestreo, estadoOrigen, manual)
				.isValid();
		return noHayAlertas && itemsFacturables && puedeDesbloquear;
	}

	@Override
	public boolean doTransicion(PeticionMuestreoItems peticionMuestreoItem, MasDataEstado estadoOrigen, boolean manual) {
		boolean puedeDesbloquear = validacionesPetMuesItem.puedeDesbloquear(peticionMuestreoItem, estadoOrigen).isValid();
		boolean contieneValidada = peticionMuestreoItem.contieneValidada();
		if (log.isTraceEnabled())
			log.trace("peticionMuestreoItemId=" + peticionMuestreoItem.getId() + " contieneValidada=" + contieneValidada + " puedeDesbloquear="
					+ puedeDesbloquear);
		return contieneValidada && puedeDesbloquear;
	}
	
	@Override
	public boolean doTransicionV2(PeticionMuestreoItems peticionMuestreoItem, MasDataEstado estadoOrigen,
			boolean manual) {
		boolean puedeDesbloquear = validacionesPetMuesItem.puedeDesbloquear(peticionMuestreoItem, estadoOrigen)
				.isValid();
		return peticionMuestreoItem.contieneValidada() && puedeDesbloquear;
	}

	@Override
	public boolean doTransicion(SolicitudIndividual solicitudIndividual, MasDataEstado estadoOrigen, boolean manual) {
		boolean noHayAlertas = !validacionesPetMues
				.hasAlertasBloqueantes(solicitudIndividual.getTrazabilidad().getPeticionRec()).isValid();
		boolean itemsFacturables = validacionesSolInd.hasItemsStatus(solicitudIndividual, CODIGO).isValid();
		boolean noHayItemsErroneos = solicitudIndividual.getItems().stream().noneMatch(x -> x.esErronea());
		boolean hasSimulatedCorrectly = validacionesSolInd.hasSimulatedCorrectly(solicitudIndividual).isValid();
		boolean puedeDesbloquear = validacionesSolInd.puedeDesbloquear(solicitudIndividual, estadoOrigen, manual)
				.isValid();
		boolean todasPruebasMismoPerfil = validacionesSolInd.checkPruebasPerfilesNoExcluidos(solicitudIndividual).isValid();
        boolean tieneItems = validacionesSolInd.hasPeticionItems(solicitudIndividual).isValid();
		log.info("peticion=" + solicitudIndividual.getPurchaseOrderByCustomer() + " tieneItems=" + tieneItems + " noHayAlertas=" + noHayAlertas
				+ " itemsFacturables=" + itemsFacturables + " noHayItemsErroneos=" + noHayItemsErroneos + " hasSimulatedCorrectly=" + hasSimulatedCorrectly
				+ " puedeDesbloquear=" + puedeDesbloquear + " todasPruebasMismoPerfil=" + todasPruebasMismoPerfil);
		return tieneItems && noHayAlertas && itemsFacturables && noHayItemsErroneos && hasSimulatedCorrectly && puedeDesbloquear && todasPruebasMismoPerfil;
	}
	
	@Override
	public boolean doTransicionV2(SolicitudIndividual solicitudIndividual, MasDataEstado estadoOrigen, boolean manual) {
		boolean noHayAlertas = !validacionesPetMues
				.hasAlertasBloqueantes(solicitudIndividual.getTrazabilidad().getPeticionRec()).isValid();
		boolean itemsFacturables = validacionesSolInd.hasItemsStatus(solicitudIndividual, CODIGO).isValid();
		boolean noHayItemsErroneos = solicitudIndividual.getItems().stream().noneMatch(x -> x.esErronea());
		boolean hasSimulatedCorrectly = validacionesSolInd.hasSimulatedCorrectly(solicitudIndividual).isValid();
		boolean puedeDesbloquear = validacionesSolInd.puedeDesbloquear(solicitudIndividual, estadoOrigen, manual)
				.isValid();
		boolean todasPruebasMismoPerfil = validacionesSolInd.checkPruebasPerfilesNoExcluidos(solicitudIndividual).isValid();
        boolean tieneItems = validacionesSolInd.hasPeticionItems(solicitudIndividual).isValid();
		return tieneItems && noHayAlertas && itemsFacturables && noHayItemsErroneos && hasSimulatedCorrectly && puedeDesbloquear && todasPruebasMismoPerfil;
	}

	@Override
	public boolean doTransicion(SolIndItems solicitudIndividualItem, MasDataEstado estadoOrigen, boolean manual) {
		boolean validada = solicitudIndividualItem.contieneValidada();
		boolean esPerfilValidado = validacionesSolIndItem.esPerfilValidado(solicitudIndividualItem);
		boolean hasPrice = solicitudIndividualItem.obtenerPriceBrutoOModificado().isPresent();
		boolean hasTax = solicitudIndividualItem.obtenerImpuestos().isPresent();
		boolean hasCapitativoAndZFI = validacionesSolIndItem.hasContratoCapitativoZFI(solicitudIndividualItem).isValid();
		boolean noEsExcluidaTipoCondicion = !validacionesSolIndItem.tipoCondicionExcluida(solicitudIndividualItem).isValid();
		boolean noEsExcluidaManual = !validacionesSolIndItem.hasExcluidaManual(solicitudIndividualItem).isValid();
		boolean noAplicaBloqueoCortesia = !validacionesSolIndItem.aplicaBloqueoCortesia(solicitudIndividualItem).isValid();
		boolean fechaPeticion = validacionesSolIndItem.facturaPorFechaPeticion(solicitudIndividualItem).isValid();
		boolean noEsMixta = !solicitudIndividualItem.getSolicitudInd().getTrazabilidad().getPeticionRec().getSolicitud().isEsMixta();
		boolean noMixtaBloqueada = estadoOrigen.getCodeEstado().equals(Bloqueada.CODIGO) ? noEsMixta : true;
		boolean puedeDesbloquear = validacionesSolIndItem.puedeDesbloquear(solicitudIndividualItem, estadoOrigen).isValid();
		boolean esPrivada = solicitudIndividualItem.getSolicitudInd().getTrazabilidad().getPeticionRec().isEsPrivado();
		boolean noEsrechazoDesdeTrack = Objects.isNull(solicitudIndividualItem.getSalesDocumentRjcnReason()) || solicitudIndividualItem.getSalesDocumentRjcnReason().isEmpty();
		boolean cumpleCondicionZPT = validacionesSolIndItem.esZPTConCotizacion(solicitudIndividualItem).isValid();
		boolean cumpleConceptoFacturacion = validacionesSolIndItem.tieneConcepto(solicitudIndividualItem);
		boolean esFacturable = validacionesSolIndItem.esFacturable(solicitudIndividualItem);
		boolean noEsPerfilErroneo = !validacionesSolIndItem.esPerfilConPruebasErroneas(solicitudIndividualItem);
		if (log.isTraceEnabled())
			log.trace("solicitudIndividualItemId=" + solicitudIndividualItem.getId() + " validada=" + validada + " esPerfilValidado=" + esPerfilValidado
					+ " hasPrice=" + hasPrice + " hasTax=" + hasTax + " hasCapitativoAndZFI=" + hasCapitativoAndZFI + " noEsExcluidaTipoCondicion="
					+ noEsExcluidaTipoCondicion + " noEsExcluidaManual=" + noEsExcluidaManual + " noAplicaBloqueoCortesia=" + noAplicaBloqueoCortesia
					+ " fechaPeticion=" + fechaPeticion + " noEsMixta=" + noEsMixta + " noMixtaBloqueada=" + noMixtaBloqueada + " puedeDesbloquear="
					+ puedeDesbloquear + " esPrivada=" + esPrivada + " noEsrechazoDesdeTrack=" + noEsrechazoDesdeTrack + " cumpleCondicionZPT="
					+ cumpleCondicionZPT + " cumpleConceptoFacturacion=" + cumpleConceptoFacturacion + " esFacturable=" + esFacturable + " noEsPerfilErroneo="
					+ noEsPerfilErroneo);
		return (validada || fechaPeticion || esPrivada || esPerfilValidado) && noMixtaBloqueada
				&& noAplicaBloqueoCortesia && noEsExcluidaTipoCondicion && puedeDesbloquear && hasPrice && hasTax
				&& hasCapitativoAndZFI && cumpleCondicionZPT && noEsExcluidaManual && noEsrechazoDesdeTrack
				&& cumpleConceptoFacturacion && esFacturable && noEsPerfilErroneo;
	}
	
	@Override
	public boolean doTransicionV2(SolIndItems solicitudIndividualItem, MasDataEstado estadoOrigen, boolean manual) {
		boolean validada = solicitudIndividualItem.contieneValidada();
		boolean esPerfilValidado = validacionesSolIndItem.esPerfilValidado(solicitudIndividualItem);
		boolean hasPrice = solicitudIndividualItem.obtenerPriceBrutoOModificado().isPresent();
		boolean hasTax = solicitudIndividualItem.obtenerImpuestos().isPresent();
		boolean hasCapitativoAndZFI = validacionesSolIndItem.hasContratoCapitativoZFI(solicitudIndividualItem).isValid();
		boolean noEsExcluidaTipoCondicion = !validacionesSolIndItem.tipoCondicionExcluida(solicitudIndividualItem).isValid();
		boolean noEsExcluidaManual = !validacionesSolIndItem.hasExcluidaManual(solicitudIndividualItem).isValid();
		boolean noAplicaBloqueoCortesia = !validacionesSolIndItem.aplicaBloqueoCortesia(solicitudIndividualItem).isValid();
		boolean fechaPeticion = validacionesSolIndItem.facturaPorFechaPeticion(solicitudIndividualItem).isValid();
		boolean noEsMixta = !solicitudIndividualItem.getSolicitudInd().getTrazabilidad().getPeticionRec().getSolicitud().isEsMixta();
		boolean noMixtaBloqueada = estadoOrigen.getCodeEstado().equals(Bloqueada.CODIGO) ? noEsMixta : true;
		boolean puedeDesbloquear = validacionesSolIndItem.puedeDesbloquear(solicitudIndividualItem, estadoOrigen).isValid();
		boolean esPrivada = solicitudIndividualItem.getSolicitudInd().getTrazabilidad().getPeticionRec().isEsPrivado();
		boolean noEsrechazoDesdeTrack = Objects.isNull(solicitudIndividualItem.getSalesDocumentRjcnReason()) || solicitudIndividualItem.getSalesDocumentRjcnReason().isEmpty();
		boolean cumpleCondicionZPT = validacionesSolIndItem.esZPTConCotizacion(solicitudIndividualItem).isValid();
		boolean cumpleConceptoFacturacion = validacionesSolIndItem.tieneConcepto(solicitudIndividualItem);
		boolean esFacturable = validacionesSolIndItem.esFacturable(solicitudIndividualItem);
		boolean noEsPerfilErroneo = !validacionesSolIndItem.esPerfilConPruebasErroneas(solicitudIndividualItem);
		
		return (validada || fechaPeticion || esPrivada || esPerfilValidado) && noMixtaBloqueada
				&& noAplicaBloqueoCortesia && noEsExcluidaTipoCondicion && puedeDesbloquear && hasPrice && hasTax
				&& hasCapitativoAndZFI && cumpleCondicionZPT && noEsExcluidaManual && noEsrechazoDesdeTrack
				&& cumpleConceptoFacturacion && esFacturable && noEsPerfilErroneo;
	}

	@Override
	public boolean doTransicion(SolAgrItems items, MasDataEstado estadoOrigen, boolean manual) {
		return true;
	}
	
	@Override
	public boolean doTransicionV2(SolAgrItems items, MasDataEstado estadoOrigen, boolean manual) {
		return true;
	}

	@Override
	public boolean doTransicion(SolicitudesAgrupadas agrupadas, MasDataEstado estadoOrigen, boolean manual) {
		return true;
	}
	
	@Override
	public boolean doTransicionV2(SolicitudesAgrupadas agrupadas, MasDataEstado estadoOrigen, boolean manual) {
		return true;
	}

	@Override
	public Map<MasDataMotivosEstado, String[]> getMotivo(SolicitudMuestreo solicitudMuestreo,
			MasDataEstado estadoOrigen, boolean manual) {
		return findMotivo(CODIGO + ALBARAN);
	}

	@Override
	public Map<MasDataMotivosEstado, String[]> getMotivo(SolicitudIndividual solicitudIndividual,
			MasDataEstado estadoOrigen, boolean manual) {
		return findMotivo(CODIGO + SOLICITUD);
	}

	@Override
	public Map<MasDataMotivosEstado, String[]> getMotivo(PeticionMuestreoItems peticionMuestreoItem,
			MasDataEstado estadoOrigen, boolean manual) {
		return findMotivo(CODIGO + PRUEBA);
	}

	@Override
	public Map<MasDataMotivosEstado, String[]> getMotivo(SolIndItems solicitudIndividualItem,
			MasDataEstado estadoOrigen, boolean manual) {
		boolean esPrivada = solicitudIndividualItem.getSolicitudInd().getTrazabilidad().getPeticionRec().isEsPrivado();
		if (esPrivada || validacionesSolIndItem.facturaPorFechaPeticion(solicitudIndividualItem).isValid()) {
			return findMotivo(ConstEstados.MOTIVO_ESTADO_FBPFP);
		} else if (solicitudIndividualItem.contieneValidada()) {
			if (solicitudIndividualItem.getFechaValidacion() != null) {
				Calendar fechaValidacion = DateUtils.convertToCalendar(solicitudIndividualItem.getFechaValidacion());
				return findMotivo(ConstEstados.MOTIVO_ESTADO_FBPVA, new String[] {DateUtils.formattedDate(fechaValidacion)});
			} else 
				return findMotivo(ConstEstados.MOTIVO_ESTADO_FBPVA);
		} else {
			return findMotivo(CODIGO + PRUEBA);
		}
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
	public Map<MasDataMotivosEstado, String[]> getMotivo(PeticionMuestreo peticionMuestreo, MasDataEstado estadoOrigen,
			boolean manual) {
		return findMotivo(CODIGO + SOLICITUD);
	}

}
