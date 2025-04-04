package com.echevarne.sap.cloud.facturacion.gestionestados;

import java.util.Map;
import java.util.Objects;

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
import com.echevarne.sap.cloud.facturacion.validations.commons.ValidacionesPetMuesItem;
import com.echevarne.sap.cloud.facturacion.validations.commons.ValidacionesSolIndItem;

import org.springframework.stereotype.Component;

import lombok.var;

@Component(Excluida.CODIGO)
public class Excluida extends FacturacionEstado {

	public static final String CODIGO = "EX";

	public Excluida() {}

	public Excluida(ValidacionesSolIndItem  validacionesSolIndItem, ValidacionesPetMuesItem validacionesPetMuesItem) {
		this.validacionesSolIndItem = validacionesSolIndItem;
		this.validacionesPetMuesItem = validacionesPetMuesItem;
	}

	@Override
	public boolean doTransicion(PeticionMuestreo peticionMuestreo, MasDataEstado estadoOrigen, boolean manual) {
		boolean itemsExcluidos = validacionesPetMues.hasItemsStatus(peticionMuestreo, CODIGO).isValid();
		boolean puedeDesbloquear = validacionesPetMues.puedeDesbloquear(peticionMuestreo, estadoOrigen, manual).isValid();
		return itemsExcluidos && puedeDesbloquear;
	}

	@Override
	public boolean doTransicion(SolicitudIndividual solicitudIndividual, MasDataEstado estadoOrigen, boolean manual) {
		boolean itemsExcluidos = validacionesSolInd.hasItemsStatus(solicitudIndividual, CODIGO).isValid();
		boolean puedeDesbloquear = validacionesSolInd.puedeDesbloquear(solicitudIndividual, estadoOrigen, manual).isValid();
        boolean tieneItems = validacionesSolInd.hasPeticionItems(solicitudIndividual).isValid();
		return tieneItems && itemsExcluidos && puedeDesbloquear;
	}

	@Override
	public boolean doTransicion(SolIndItems solicitudIndividualItem, MasDataEstado estadoOrigen, boolean manual) {
		var esExcluidaTipoCondicion = validacionesSolIndItem.tipoCondicionExcluida(solicitudIndividualItem).isValid();
		boolean excluirPerfilAutomatico = validacionesSolIndItem.esPerfilConTodasPruebasExcluidas(solicitudIndividualItem);
        boolean noEsExcluidaManual = !validacionesSolIndItem.hasExcluidaManual(solicitudIndividualItem).isValid();
		return noEsExcluidaManual && (esExcluidaTipoCondicion || excluirPerfilAutomatico);
	}

	@Override
	public boolean doTransicion(PeticionMuestreoItems peticionMuestreoItem, MasDataEstado estadoOrigen, boolean manual) {
		var rechazoDesdeTrack = Objects.nonNull(peticionMuestreoItem.getMotivoRechazo()) && !peticionMuestreoItem.getMotivoRechazo().isEmpty();
		boolean puedeDesbloquear = validacionesPetMuesItem.puedeDesbloquear(peticionMuestreoItem, estadoOrigen).isValid();
		return rechazoDesdeTrack && puedeDesbloquear;
	}

	@Override
	public Map<MasDataMotivosEstado, String[]> getMotivo(SolicitudMuestreo solicitudMuestreo, MasDataEstado estadoOrigen, boolean manual) {
		return findMotivo(CODIGO + ALBARAN);
	}

	@Override
	public Map<MasDataMotivosEstado, String[]> getMotivo(SolicitudIndividual solicitudIndividual, MasDataEstado estadoOrigen, boolean manual) {
		boolean itemsExcluidos = validacionesSolInd.hasItemsStatus(solicitudIndividual, CODIGO).isValid();
		if (itemsExcluidos) {
			return findMotivo(ConstEstados.MOTIVO_ESTADO_EXS);
		}
		return findMotivo(CODIGO + SOLICITUD);
	}

	@Override
	public Map<MasDataMotivosEstado, String[]> getMotivo(PeticionMuestreoItems peticionMuestreoItem, MasDataEstado estadoOrigen, boolean manual) {
		if(Objects.nonNull(peticionMuestreoItem.getMotivoRechazo()) && !peticionMuestreoItem.getMotivoRechazo().isEmpty()){
			return findMotivo(ConstEstados.MOTIVO_ESTADO_EXMR);
		}
		return findMotivo(CODIGO + PRUEBA);
	}

	@Override
	public Map<MasDataMotivosEstado, String[]> getMotivo(SolIndItems solicitudIndividualItem, MasDataEstado estadoOrigen, boolean manual) {
		if(solicitudIndividualItem.getSalesOrderItemCategory().equals(ConstFacturacion.TIPO_POSICION_NO_FACTURABLE))
			return findMotivo(ConstEstados.MOTIVO_ESTADO_EXNF);
		else if(solicitudIndividualItem.getSalesOrderItemCategory().equals(ConstFacturacion.TIPO_POSICION_INCONGRUENTE))
			return findMotivo(ConstEstados.MOTIVO_ESTADO_EXIN);
		else if(solicitudIndividualItem.getSalesOrderItemCategory().equals(ConstFacturacion.TIPO_POSICION_BLOQUEO_CORTESIA))
			return findMotivo(ConstEstados.MOTIVO_ESTADO_EXBC);
		else if(solicitudIndividualItem.getSalesOrderItemCategory().equals(ConstFacturacion.TIPO_POSICION_BLOQUEO_RECHAZO))
			return findMotivo(ConstEstados.MOTIVO_ESTADO_EXMR);
		else if(solicitudIndividualItem.getSalesOrderItemCategory().equals(ConstFacturacion.TIPO_POSICION_EXCLUIDA)){
			return findMotivo(ConstEstados.MOTIVO_ESTADO_EXMA);
		}
		return findMotivo(CODIGO + PRUEBA);
	}

	@Override
	public Map<MasDataMotivosEstado, String[]> getMotivo(SolicitudesAgrupadas solicitudesAgrupadas, MasDataEstado estadoOrigen) {
		return findMotivo(CODIGO + SOLICITUD);
	}

	@Override
	public Map<MasDataMotivosEstado, String[]> getMotivo(SolAgrItems solAgrItems, MasDataEstado estadoOrigen) {
		return findMotivo(CODIGO + SOLICITUD);
	}

	@Override
	public Map<MasDataMotivosEstado, String[]> getMotivo(PeticionMuestreo peticionMuestreo, MasDataEstado estadoOrigen, boolean manual) {
		boolean itemsExcluidos = validacionesPetMues.hasItemsStatus(peticionMuestreo, CODIGO).isValid();
		if (itemsExcluidos) {
			return findMotivo(ConstEstados.MOTIVO_ESTADO_EXS);
		}
		return findMotivo(CODIGO + SOLICITUD);
	}

}
