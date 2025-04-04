package com.echevarne.sap.cloud.facturacion.gestionestados;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

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

@Component(Bloqueada.CODIGO)
public class Bloqueada extends FacturacionEstado {

	public static final String CODIGO = "BLK";

	@Override
	public boolean doTransicion(PeticionMuestreo peticionMuestreo, MasDataEstado estadoOrigen, boolean manual) {
		boolean tieneBloqueoManual = validacionesPetMues.hasBloqueoManual(peticionMuestreo).isValid();
		boolean hayAlertas = validacionesPetMues.hasAlertasBloqueantes(peticionMuestreo).isValid();
		boolean itemsBloqueados = validacionesPetMues.hasItemsStatus(peticionMuestreo, CODIGO).isValid();
		if(estadoOrigen.getCodeEstado().equals(Bloqueada.CODIGO)) {
			if (tieneBloqueoManual)
				return ( hayAlertas || itemsBloqueados ) && manual;
			else
				return manual;
		}
		return hayAlertas || itemsBloqueados || manual;
	}

	@Override
	public boolean doTransicion(PeticionMuestreoItems peticionMuestreoItem, MasDataEstado estadoOrigen, boolean manual) {
		boolean estuvoBloqueadaPorMixta = peticionMuestreoItem.getTrazabilidad().getEstados().stream().filter(e -> CODIGO.equals(e.getEstado().getCodeEstado()) && e.getMotivo()!=null && ConstEstados.PREFIJO_ESTADO_BLKPAU.equals(e.getMotivo().getCodigo())).findAny().isPresent();
		return peticionMuestreoItem.getPeticion().getSolicitud().isEsMixta() && !peticionMuestreoItem.getPeticion().isEsPrivado() && !estuvoBloqueadaPorMixta;
	}

	@Override
	public boolean doTransicion(SolicitudIndividual solicitudIndividual, MasDataEstado estadoOrigen, boolean manual) {
        boolean tieneItems = validacionesSolInd.hasPeticionItems(solicitudIndividual).isValid();
        if (!tieneItems) {
            return false;
        }
		boolean tieneBloqueoManual = validacionesSolInd.hasBloqueoManual(solicitudIndividual).isValid();
		boolean hayAlertas = validacionesSolInd.hasAlertasBloqueantes(solicitudIndividual).isValid();
		boolean itemsBloqueados = validacionesSolInd.hasItemsStatus(solicitudIndividual, CODIGO).isValid();
		if(estadoOrigen.getCodeEstado().equals(Bloqueada.CODIGO)) {
			if (tieneBloqueoManual)
				return ( hayAlertas || itemsBloqueados ) && manual;
			else
				return manual;
		}
		return hayAlertas || itemsBloqueados || manual;
	}

	@Override
	public boolean doTransicion(SolIndItems solicitudIndividualItem, MasDataEstado estadoOrigen, boolean manual) {
		return doTransicion(solicitudIndividualItem.getTrazabilidad().getItemRec(), estadoOrigen, manual);
	}

	@Override
	public Map<MasDataMotivosEstado, String[]> getMotivo(SolicitudMuestreo solicitudMuestreo, MasDataEstado estadoOrigen, boolean manual) {
		return findMotivo(CODIGO + SOLICITUD);
	}

	@Override
	public Map<MasDataMotivosEstado, String[]> getMotivo(SolicitudIndividual solicitudIndividual, MasDataEstado estadoOrigen, boolean manual) {
		Date fecha = new Date();
		boolean hayAlertas = validacionesSolInd.hasAlertasBloqueantes(solicitudIndividual).isValid();
		boolean itemsBloqueados = validacionesSolInd.hasItemsStatus(solicitudIndividual, CODIGO).isValid();
		if(hayAlertas){
			return findMotivo(ConstEstados.PREFIJO_ESTADO_BLKA);
		} else if (itemsBloqueados ) {
			if (solicitudIndividual.getTrazabilidad().getPeticionRec().getFechas()!=null && solicitudIndividual.getTrazabilidad().getPeticionRec().getFechas().getFechaPeticion()!=null)
			{
				fecha = solicitudIndividual.getTrazabilidad().getPeticionRec().getFechas().getFechaPeticion();
			}
			Calendar calendar = DateUtils.convertToCalendar(DateUtils.addDays(fecha,20));
			return findMotivo(ConstEstados.PREFIJO_ESTADO_BLKPAU, new String[] {DateUtils.formattedDate(calendar)});
		} else {
			return findMotivo(CODIGO + SOLICITUD);
		}
	}

	@Override
	public Map<MasDataMotivosEstado, String[]> getMotivo(PeticionMuestreoItems peticionMuestreoItem, MasDataEstado estadoOrigen, boolean manual) {
		boolean mixtaNoPrivada = peticionMuestreoItem.getPeticion().getSolicitud().isEsMixta() && !peticionMuestreoItem.getPeticion().isEsPrivado();
		boolean bloqueoAutomatico = peticionMuestreoItem.isBloqueoAutomatico();
		if(mixtaNoPrivada && bloqueoAutomatico && peticionMuestreoItem.getPeticion().getFechas()!=null && peticionMuestreoItem.getPeticion().getFechas().getFechaPeticion()!=null){
			Calendar calendar = DateUtils.convertToCalendar(DateUtils.addDays(peticionMuestreoItem.getPeticion().getFechas().getFechaPeticion(),20));
			return findMotivo(ConstEstados.PREFIJO_ESTADO_BLKPM, new String[] {DateUtils.formattedDate(calendar)});
		}  
		return findMotivo(CODIGO + PRUEBA);
	}

	@Override
	public Map<MasDataMotivosEstado, String[]> getMotivo(SolIndItems solicitudIndividualItem, MasDataEstado estadoOrigen, boolean manual) {
		return findMotivo(CODIGO + PRUEBA);
	}

	@Override
	public Map<MasDataMotivosEstado, String[]> getMotivo(PeticionMuestreo peticionMuestreo, MasDataEstado estadoOrigen, boolean manual) {
		boolean hayAlertas = validacionesPetMues.hasAlertasBloqueantes(peticionMuestreo).isValid();
		if(hayAlertas){
			return findMotivo(ConstEstados.PREFIJO_ESTADO_BLKA);
		} else {
			boolean mixtaNoPrivada = peticionMuestreo.getSolicitud().isEsMixta() && !peticionMuestreo.isEsPrivado();
			if(mixtaNoPrivada && peticionMuestreo.getFechas()!=null && peticionMuestreo.getFechas().getFechaPeticion()!=null){
				Calendar calendar = DateUtils.convertToCalendar(DateUtils.addDays(peticionMuestreo.getFechas().getFechaPeticion(),20));
				return findMotivo(ConstEstados.PREFIJO_ESTADO_BLKPAU, new String[] {DateUtils.formattedDate(calendar)});
			} else {
				return findMotivo(CODIGO + PRUEBA);
			}
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
}
