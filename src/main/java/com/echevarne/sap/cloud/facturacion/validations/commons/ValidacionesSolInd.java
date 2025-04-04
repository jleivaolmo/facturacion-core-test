package com.echevarne.sap.cloud.facturacion.validations.commons;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;

import com.echevarne.sap.cloud.facturacion.gestionestados.Bloqueada;
import com.echevarne.sap.cloud.facturacion.gestionestados.util.EstadosUtils;
import com.echevarne.sap.cloud.facturacion.model.masterdata.MasDataAlerta;
import com.echevarne.sap.cloud.facturacion.model.masterdata.MasDataEstado;
import com.echevarne.sap.cloud.facturacion.model.parametrizacion.AccionesUsuario;
import com.echevarne.sap.cloud.facturacion.model.replicated.S4Customers;
import com.echevarne.sap.cloud.facturacion.model.solicitudindividual.SolIndItems;
import com.echevarne.sap.cloud.facturacion.model.solicitudindividual.SolicitudIndividual;
import com.echevarne.sap.cloud.facturacion.model.trazabilidad.TrazabilidadSolicitudEstHistory;
import com.echevarne.sap.cloud.facturacion.services.BloqueoTecnicoService;
import com.echevarne.sap.cloud.facturacion.services.MasDataAlertaService;
import com.echevarne.sap.cloud.facturacion.services.S4CustomersService;
import com.echevarne.sap.cloud.facturacion.validations.BasicValidation;
import com.echevarne.sap.cloud.facturacion.validations.ValidationResult;

import lombok.var;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ValidacionesSolInd extends BasicValidation implements SolIndValidation {

	@Autowired
	private MasDataAlertaService alertasSrv;
	@Autowired
	private ValidacionesSolIndItem validacionesSolIndItem;
	@Autowired
	private BloqueoTecnicoService bloqueoTecnicoService;
	@Autowired
	private S4CustomersService s4CustomersService;
	@Autowired
	private EntityManager entityManager;
	
	private static final String NO_APLICA = "No se han cumplido las condiciones.";
	private static final String PRUEBAS_POR_PERFIL = "Existen pruebas que no son del mismo tipo que su perfil.";

	protected static List<SolIndValidation> getValidationsActives() {
		List<SolIndValidation> validaciones = new ArrayList<SolIndValidation>();
		return validaciones;
	}

	/**
	 * 
	 * Retorna si la simulación se ha hecho correctamente
	 * 
	 * @param entidad
	 * @return
	 */
	public ValidationResult hasSimulatedCorrectly(SolicitudIndividual entidad) {
		List<SolIndValidation> validacionesTransformacion = new ArrayList<SolIndValidation>();
		validacionesTransformacion.add(SolIndValidation.hasPeticionNetAmount());
		setResultados(applyAll(entidad, validacionesTransformacion));
		boolean isValid = getResultados().values().stream().filter(vr -> !vr.isValid()).count() > 0;
		if (isValid)
			return ValidationResult.valid();
		return ValidationResult.invalid(NO_APLICA);

	}

	public ValidationResult hasPartnerInS4(String codigoInterlocutor) {
		Optional<S4Customers> customer= s4CustomersService.findByKUNNR(codigoInterlocutor);
		if (customer.isPresent())
			return ValidationResult.valid();
		return ValidationResult.invalid(NO_APLICA);
	}

	/**
	 * Valida que todas las pruebas de un perfil son del mismo tipo del perfil.
	 * @param entidad entidad a chequear
	 * @return ValidationResult
	 */
	public ValidationResult checkPruebasPerfilesNoExcluidos(final SolicitudIndividual entidad) {
		Set<SolIndItems> perfiles = entidad.getItems().stream().filter(i -> i.getTrazabilidad().getItemRec().getEsPerfil()).collect(Collectors.toSet());
		boolean isValid = !perfiles.stream().anyMatch(prueba -> validacionesSolIndItem.esPerfilConPruebasDiferenteTipo(prueba));
		if (isValid)
			return ValidationResult.valid();
		return ValidationResult.invalid(PRUEBAS_POR_PERFIL);
	}

	/**
	 * 
	 * Retorna si la peticion no tiene pruebas
	 * 
	 * @param entidad
	 * @return
	 */
	public ValidationResult hasPeticionItems(SolicitudIndividual entidad) {

		List<SolIndValidation> validacionesTransformacion = new ArrayList<>();
		validacionesTransformacion.add(SolIndValidation.hasPeticionItems());
		setResultados(applyAll(entidad, validacionesTransformacion));
		boolean isValid = getResultados().values().stream().filter(vr -> !vr.isValid()).count() > 0;
		if (isValid)
			return ValidationResult.valid();
		return ValidationResult.invalid(NO_APLICA);

	}

	public ValidationResult hasFacturada(SolicitudIndividual entidad) {
		boolean isValid = entidad.getItems().stream().filter(x -> x.estaFacturada()).findAny().isPresent();
		if (isValid)
			return ValidationResult.valid();
		return ValidationResult.invalid(NO_APLICA);
	}
	
	
	public ValidationResult hasItemsStatus(SolicitudIndividual entidad, String codigoEstado) {
		List<SolIndValidation> validacionesTransformacion = new ArrayList<SolIndValidation>();
		validacionesTransformacion.add(SolIndValidation.hasAllItemsStatus(EstadosUtils.getEstadoByCode(codigoEstado)));
		setResultados(applyAll(entidad, validacionesTransformacion));
		boolean isValid = getResultados().values().stream().filter(vr -> vr.isValid()).count() > 0;
		if (isValid)
			return ValidationResult.valid();
		return ValidationResult.invalid(NO_APLICA);

	}
	

	public ValidationResult hasItemsStatusV2(SolicitudIndividual entidad, String codigoEstado) {
		List<SolIndValidation> validacionesTransformacion = new ArrayList<SolIndValidation>();
		validacionesTransformacion.add(SolIndValidation.hasAllItemsStatusV2(EstadosUtils.getEstadoByCode(codigoEstado), entityManager));
		setResultados(applyAllV2(entidad, validacionesTransformacion));
		boolean isValid = getResultados().values().stream().filter(vr -> vr.isValid()).count() > 0;
		if (isValid)
			return ValidationResult.valid();
		return ValidationResult.invalid(NO_APLICA);

	}

	public ValidationResult hasOrigenAndItemsStatus(SolicitudIndividual solicitudIndividual, MasDataEstado origen) {
		List<SolIndValidation> validacionesTransformacion = new ArrayList<SolIndValidation>();
		validacionesTransformacion
				.add(SolIndValidation.hasAllItemsStatus(EstadosUtils.getEstadoByCode(origen.getCodeEstado())));
		setResultados(applyAll(solicitudIndividual, validacionesTransformacion));
		boolean isValid = getResultados().values().stream().filter(vr -> vr.isValid()).count() > 0;
		if (isValid)
			return ValidationResult.valid();
		return ValidationResult.invalid(NO_APLICA);
	}

	/**
	 * Devuelve si existe alguna prueba con el estado validada.
	 * 
	 * @param entidad
	 * @return
	 */
	public ValidationResult hasValidatedItems(SolicitudIndividual entidad) {
		return entidad.contieneValidada() ? ValidationResult.valid()
				: ValidationResult.invalid("No exite ninguna prueba con el estado validada");
	}

	public ValidationResult puedeDesbloquear(SolicitudIndividual solicitudIndividual, MasDataEstado origen,
			boolean manual) {
		if (!origen.getCodeEstado().equals(Bloqueada.CODIGO))
			return ValidationResult.valid();
		if (bloqueoTecnicoService.tieneBloqueoTecnico(solicitudIndividual)) {
			ValidationResult.invalid("No se puede desbloquear por existir Bloqueo Tecnico");
		}
		boolean itemsNoBloqueados = !hasItemsStatus(solicitudIndividual, Bloqueada.CODIGO).isValid();
		boolean noHayAlertas = !hasAlertasBloqueantes(solicitudIndividual).isValid();
		boolean noHayBloqueoManual = !hasBloqueoManual(solicitudIndividual).isValid();
		if (itemsNoBloqueados && noHayAlertas && (noHayBloqueoManual || manual))
			return ValidationResult.valid();
		return ValidationResult.invalid("No se puede desbloquear");
	}

	public ValidationResult hasBloqueoManual(SolicitudIndividual solicitudIndividual) {
		Optional<TrazabilidadSolicitudEstHistory> ultimoEstado = solicitudIndividual.getTrazabilidad().getLastEstado();
		if (ultimoEstado.isPresent()) {
			TrazabilidadSolicitudEstHistory last = ultimoEstado.get();
			if (last.getEstado().getCodeEstado().equals(Bloqueada.CODIGO))
				if (!last.isAutomatico())
					return ValidationResult.valid();
		}
		return ValidationResult.invalid("No hay bloqueo manual");
	}

	@Override
	public ValidationResult apply(SolicitudIndividual entidad) {

		List<SolIndValidation> validacionesTransformacion = getValidationsActives();
		setResultados(applyAll(entidad, validacionesTransformacion));
		boolean isValid = getResultados().values().stream().filter(vr -> !vr.isValid()).count() > 0;
		if (isValid)
			return ValidationResult.valid();
		return ValidationResult.invalid(NO_APLICA);

	}

	public ValidationResult hasAlertasBloqueantes(SolicitudIndividual solicitudIndividual) {
		Set<String> codigosAlerta = solicitudIndividual.obtieneAlertas();
		if (!codigosAlerta.isEmpty() && codigosAlerta != null) {
			var fechaPeticion = solicitudIndividual.getSalesOrderDate();
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

	public ValidationResult isTransformable(SolicitudIndividual si) {
		if (bloqueoTecnicoService.tieneBloqueoTecnico(si)) {
			ValidationResult.invalid("No se puede Transformar por existir Bloqueo Tecnico");
		}
		MasDataEstado estadoActual = EstadosUtils.getEstadoActual(si.getTrazabilidad());
		boolean permitida = EstadosUtils.getAccionPermitida(AccionesUsuario.ActualizarPeticion, estadoActual);
		if (permitida)
			return ValidationResult.valid();
		else
			return ValidationResult
					.invalid("No se puede realizar la actualización de la petición por contener el estado "
							+ estadoActual.getNombre());
	}

	public boolean algunaFacturada(SolicitudIndividual solicitudIndividual) {
		try {
			String sql = "SELECT COUNT (*) FROM T_SOLICITUDINDIVIDUAL s " + " INNER JOIN T_SOLINDITEMS i ON i.FK_SOLICITUDINDIVIDUAL = s.ID "
					+ " INNER JOIN T_TRAZABILIDAD t ON t.FK_SOLICITUDINDIVIDUALITEM = i.ID"
					+ " INNER JOIN T_TRAZABILIDADESTHISTORY h ON h.FK_TRAZABILIDAD = t.ID AND h.INACTIVE = false "
					+ " INNER JOIN T_MASDATAESTADO e ON e.ID = h.FK_ESTADO " + " INNER JOIN T_MASDATAESTADOSGRUPO g ON g.ID = e.FK_GRUPOESTADO "
					+ " WHERE g.CODIGO = 'F' AND s.ID = " + solicitudIndividual.getId();
			BigInteger responseBint = (BigInteger) entityManager.createNativeQuery(sql).getSingleResult();
			return responseBint.compareTo(BigInteger.ZERO) > 0;
		} catch (Exception e) {
			log.error("Error al obtener algunaFacturada para " + solicitudIndividual);
		}
		return false;
	}
	
	public boolean todasFacturadas(SolicitudIndividual solicitudIndividual) {
		try {
			String sql = "SELECT COUNT (*) FROM T_SOLICITUDINDIVIDUAL s " + " INNER JOIN T_SOLINDITEMS i ON i.FK_SOLICITUDINDIVIDUAL = s.ID "
					+ " INNER JOIN T_TRAZABILIDAD t ON t.FK_SOLICITUDINDIVIDUALITEM = i.ID"
					+ " INNER JOIN T_TRAZABILIDADESTHISTORY h ON h.FK_TRAZABILIDAD = t.ID AND h.INACTIVE = false "
					+ " INNER JOIN T_MASDATAESTADO e ON e.ID = h.FK_ESTADO " + " INNER JOIN T_MASDATAESTADOSGRUPO g ON g.ID = e.FK_GRUPOESTADO "
					+ " WHERE g.CODIGO = 'FB' AND s.ID = " + solicitudIndividual.getId();
			BigInteger responseBint = (BigInteger) entityManager.createNativeQuery(sql).getSingleResult();
			return responseBint.compareTo(BigInteger.ZERO) == 0;
		} catch (Exception e) {
			log.error("Error al obtener todasFacturadas para " + solicitudIndividual);
		}
		return false;
	}

}