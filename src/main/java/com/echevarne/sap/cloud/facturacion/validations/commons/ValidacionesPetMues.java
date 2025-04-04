package com.echevarne.sap.cloud.facturacion.validations.commons;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.echevarne.sap.cloud.facturacion.gestionestados.Bloqueada;
import com.echevarne.sap.cloud.facturacion.gestionestados.util.EstadosUtils;
import com.echevarne.sap.cloud.facturacion.model.masterdata.MasDataAlerta;
import com.echevarne.sap.cloud.facturacion.model.masterdata.MasDataEstado;
import com.echevarne.sap.cloud.facturacion.model.parametrizacion.AccionesUsuario;
import com.echevarne.sap.cloud.facturacion.model.solicitudrecibida.PeticionMuestreo;
import com.echevarne.sap.cloud.facturacion.model.trazabilidad.TrazabilidadSolicitudEstHistory;
import com.echevarne.sap.cloud.facturacion.model.views.ItemsEnProcesoFacturacion;
import com.echevarne.sap.cloud.facturacion.repositories.ItemsEnProcesoFacturacionRep;
import com.echevarne.sap.cloud.facturacion.services.BloqueoTecnicoService;
import com.echevarne.sap.cloud.facturacion.services.MasDataAlertaService;
import com.echevarne.sap.cloud.facturacion.validations.BasicValidation;
import com.echevarne.sap.cloud.facturacion.validations.ValidationResult;

import lombok.var;

@Component("validacionesPetMues")
public class ValidacionesPetMues extends BasicValidation implements PetMuesValidation {

	@Autowired
	private MasDataAlertaService alertasSrv;

	@Autowired
	private BloqueoTecnicoService bloqueoTecnicoService;
	
	@Autowired
	private ItemsEnProcesoFacturacionRep itemsEnProcesoFacturacionRep;

	private static final String NO_APLICA = "No se han cumplido las condiciones.";

	protected static List<PetMuesValidation> getValidationsActives() {
		List<PetMuesValidation> validaciones = new ArrayList<PetMuesValidation>();
		return validaciones;
	}

	public ValidationResult isTransformable(PeticionMuestreo peticion) {
		List<ItemsEnProcesoFacturacion> procesos = itemsEnProcesoFacturacionRep.findByCodigoPeticion(peticion.getCodigoPeticion());
		if (procesos != null && procesos.size() > 0)
			return ValidationResult.invalid("No se puede realizar la actualización de la petición porque ya tiene un proceso activo");

		if (bloqueoTecnicoService.tieneBloqueoTecnico(peticion))
			return ValidationResult.invalid("No se puede realizar la actualización de la petición porque tiene un bloqueoTecnico activo");
		
		MasDataEstado estadoActual = EstadosUtils.getEstadoActual(peticion.getTrazabilidad());
		if (estadoActual == null)
			return ValidationResult.invalid("No se puede realizar la actualización de la petición ya que esta no tiene estado ");
		
		boolean permitida = EstadosUtils.getAccionPermitida(AccionesUsuario.ActualizarPeticion, estadoActual);
		if (permitida)
			return ValidationResult.valid();
		else
			return ValidationResult.invalid("No se puede realizar la actualización de la petición por contener el estado " + estadoActual.getNombre());
	}

	public ValidationResult permiteModificarDatosProductivos(PeticionMuestreo peticion) {
		if (bloqueoTecnicoService.tieneBloqueoTecnico(peticion)) {
			return ValidationResult
					.invalid("No se puede realizar la actualización de la petición porque tiene un bloqueoTecnico activo");
		}
		MasDataEstado estadoActual = EstadosUtils.getEstadoActual(peticion.getTrazabilidad());
		if (estadoActual == null) {
			return ValidationResult
					.invalid("No se puede realizar la actualización de la petición ya que esta no tiene estado ");
		}
		boolean permitida = EstadosUtils.getAccionPermitida(AccionesUsuario.ActualizarDatosProductivos, estadoActual);
		if (permitida) {
			return ValidationResult.valid();
		} else {
			return ValidationResult
					.invalid("No se puede realizar la actualización de la petición por contener el estado "
							+ estadoActual.getNombre());
		}
	}

	public ValidationResult isTratablePrivados(PeticionMuestreo peticion) {
		Set<String> codigosAlerta = peticion.obtieneAlertas();
		if (!codigosAlerta.isEmpty()) {
			List<MasDataAlerta> alertasPrivados = alertasSrv.findDistinctAlertasPrivados();
			if (!alertasPrivados.isEmpty()) {
				Optional<MasDataAlerta> resultado = alertasPrivados.stream().filter(privados -> codigosAlerta
						.stream().anyMatch(alerta -> privados.getCodigoAlerta().equals(alerta))).findAny();
				if (resultado.isPresent()) {
					return ValidationResult.valid();
				}
			}
		}
		return ValidationResult.invalid("No hay alertas que apliquen a privados");
	}

	public ValidationResult puedeDesbloquear(PeticionMuestreo peticion, MasDataEstado origen, boolean manual) {
		if (!origen.getCodeEstado().equals(Bloqueada.CODIGO))
			return ValidationResult.valid();
		boolean itemsNoBloqueados = !hasItemsStatus(peticion, Bloqueada.CODIGO).isValid();
		boolean noHayAlertas = !hasAlertasBloqueantes(peticion).isValid();
		boolean noHayBloqueoManual = !hasBloqueoManual(peticion).isValid();
		if (itemsNoBloqueados && noHayAlertas && (noHayBloqueoManual || manual))
			return ValidationResult.valid();
		return ValidationResult.invalid("No se puede desbloquear");
	}

	public ValidationResult hasBloqueoManual(PeticionMuestreo peticion) {
		Optional<TrazabilidadSolicitudEstHistory> ultimoEstado = peticion.getTrazabilidad().getLastEstado();
		if (ultimoEstado.isPresent()) {
			TrazabilidadSolicitudEstHistory last = ultimoEstado.get();
			if (last.getEstado().getCodeEstado().equals(Bloqueada.CODIGO))
				if (!last.isAutomatico())
					return ValidationResult.valid();
		}
		return ValidationResult.invalid("No hay bloqueo manual");
	}

	public ValidationResult hasAlertasBloqueantes(PeticionMuestreo peticion) {
		Set<String> codigosAlerta = peticion.obtieneAlertas();
		if (!codigosAlerta.isEmpty() && codigosAlerta != null) {
			var fechaPeticion = peticion.getFechas().getFechaPeticion();
			List<MasDataAlerta> alertasBloqueantes = alertasSrv.findDistinctAlertasBloquean(fechaPeticion);
			if (!alertasBloqueantes.isEmpty() && alertasBloqueantes != null) {
				Optional<MasDataAlerta> resultado = alertasBloqueantes.stream().filter(bloqueante -> codigosAlerta
						.stream().anyMatch(alerta -> bloqueante.getCodigoAlerta().equals(alerta))).findAny();
				if (resultado.isPresent()) {
					return ValidationResult.valid();
				}
			}
		}
		return ValidationResult.invalid("No hay alertas bloqueantes");
	}

	public ValidationResult hasItemsStatus(PeticionMuestreo entidad, String codigoEstado) {
		List<PetMuesValidation> validacionesTransformacion = new ArrayList<PetMuesValidation>();
		validacionesTransformacion.add(PetMuesValidation.hasAllItemsStatus(EstadosUtils.getEstadoByCode(codigoEstado)));
		setResultados(applyAll(entidad, validacionesTransformacion));
		boolean isValid = getResultados().values().stream().filter(vr -> vr.isValid()).count() > 0;
		if (isValid)
			return ValidationResult.valid();
		return ValidationResult.invalid(NO_APLICA);
	}


	@Override
	public ValidationResult apply(PeticionMuestreo entidad) {

		setResultados(applyAll(entidad, getValidationsActives()));
		boolean isValid = getResultados().values().stream().filter(vr -> !vr.isValid()).count() > 0;
		if (isValid)
			return ValidationResult.valid();
		return ValidationResult.invalid(NO_APLICA);

	}

}
