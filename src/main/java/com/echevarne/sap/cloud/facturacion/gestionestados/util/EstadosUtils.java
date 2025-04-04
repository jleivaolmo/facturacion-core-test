package com.echevarne.sap.cloud.facturacion.gestionestados.util;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;

import com.echevarne.sap.cloud.facturacion.config.ContextProvider;
import com.echevarne.sap.cloud.facturacion.gestionestados.Bloqueada;
import com.echevarne.sap.cloud.facturacion.gestionestados.Borrada;
import com.echevarne.sap.cloud.facturacion.gestionestados.Mutable;
import com.echevarne.sap.cloud.facturacion.gestionestados.MutableHistory;
import com.echevarne.sap.cloud.facturacion.gestionestados.Procesable;
import com.echevarne.sap.cloud.facturacion.gestionestados.Transicionable;
import com.echevarne.sap.cloud.facturacion.model.BasicEstadosHistory;
import com.echevarne.sap.cloud.facturacion.model.masterdata.MasDataEstado;
import com.echevarne.sap.cloud.facturacion.model.masterdata.MasDataEstadosGrupo;
import com.echevarne.sap.cloud.facturacion.model.parametrizacion.AccionesPermitidas;
import com.echevarne.sap.cloud.facturacion.model.parametrizacion.AccionesUsuario;
import com.echevarne.sap.cloud.facturacion.model.trazabilidad.Trazabilidad;
import com.echevarne.sap.cloud.facturacion.model.trazabilidad.TrazabilidadEstHistory;
import com.echevarne.sap.cloud.facturacion.model.trazabilidad.TrazabilidadSolAgrItems;
import com.echevarne.sap.cloud.facturacion.model.trazabilidad.TrazabilidadSolicitud;
import com.echevarne.sap.cloud.facturacion.model.trazabilidad.TrazabilidadSolicitudAgrupada;
import com.echevarne.sap.cloud.facturacion.services.AccionesPermitidasService;
import com.echevarne.sap.cloud.facturacion.services.MasDataEstadoService;

import com.google.common.collect.Sets;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class EstadosUtils {

	public static final String NIVEL_ALBARAN = "A";
	public static final String NIVEL_CABECERA = "S";
	public static final String NIVEL_POSICION = "P";
	public static final String ESTADO_INICIAL = "SE";

	private static AccionesPermitidasService accionesPermitidasService;
	private static MasDataEstadoService estadosSrv;
	private static Set<String> processors = Sets.newConcurrentHashSet();
	
	@Autowired
	public EstadosUtils(MasDataEstadoService estadosServices, AccionesPermitidasService accionesPermitidasSrv) {
		EstadosUtils.estadosSrv = estadosServices;
		EstadosUtils.accionesPermitidasService = accionesPermitidasSrv;
	}
	
	private static String getEstadoActualV2(Mutable<?> trazabilidad, EntityManager entityManager) {
		if (trazabilidad instanceof Trazabilidad) {
			String sql = "SELECT TOP 1 e.CODEESTADO  FROM T_TRAZABILIDAD t " + " LEFT JOIN T_TRAZABILIDADESTHISTORY tt ON tt.FK_TRAZABILIDAD = t.ID "
					+ " LEFT JOIN T_MASDATAESTADO e ON tt.FK_ESTADO = e.ID " + " WHERE t.ID = " + trazabilidad.getId() + " ORDER BY tt.SEQUENCEORDER DESC";
			return (String) entityManager.createNativeQuery(sql).getSingleResult();
		} else if (trazabilidad instanceof TrazabilidadSolicitud) {
			String sql = "SELECT TOP 1 e.CODEESTADO  FROM T_TRAZABILIDADSOLICITUD t "
					+ " LEFT JOIN T_TRAZABILIDADSOLESTHISTORY tt ON tt.FK_TRAZABILIDAD = t.ID " + " LEFT JOIN T_MASDATAESTADO e ON tt.FK_ESTADO = e.ID "
					+ " WHERE t.ID = " + trazabilidad.getId() + " ORDER BY tt.SEQUENCEORDER DESC";
			return (String) entityManager.createNativeQuery(sql).getSingleResult();
		} else if (trazabilidad instanceof TrazabilidadSolicitudAgrupada) {
			String sql = "SELECT TOP 1 e.CODEESTADO  FROM T_TRAZABILIDADSOLICITUDAGRUP t "
					+ " LEFT JOIN T_TRAZABILIDADSOLAGRESTHISTORY tt ON tt.FK_TRAZABILIDADSOLICITUDAGRUPADA = t.ID " + " LEFT JOIN T_MASDATAESTADO e ON tt.FK_ESTADO = e.ID "
					+ " WHERE t.ID = " + trazabilidad.getId() + " ORDER BY tt.SEQUENCEORDER DESC";
			return (String) entityManager.createNativeQuery(sql).getSingleResult();
		} else if (trazabilidad instanceof TrazabilidadSolAgrItems) {
			String sql = "SELECT TOP 1 e.CODEESTADO  FROM T_TRAZABILIDADSOLAGRIT t "
					+ " LEFT JOIN T_TRAZABILIDADSOLAGRITESTHISTORY tt ON tt.FK_TRAZABILIDAD = t.ID " + " LEFT JOIN T_MASDATAESTADO e ON tt.FK_ESTADO = e.ID "
					+ " WHERE t.ID = " + trazabilidad.getId() + " ORDER BY tt.SEQUENCEORDER DESC";
			return (String) entityManager.createNativeQuery(sql).getSingleResult();
		}
		return null;
	}

	/**
	 *
	 * Obtiene el ultimo estado de la trazabilidad a nivel de prueba
	 */
	public static MasDataEstado getEstadoActual(TrazabilidadSolicitud trazabilidad) {
		return trazabilidad.getLastEstado().map(BasicEstadosHistory::getEstado).orElse(null);
	}

	/**
	 *
	 * Obtiene el ultimo estado de la trazabilidad a nivel de prueba
	 */
	public static MasDataEstadosGrupo getGrupoEstadoActual(TrazabilidadSolicitud trazabilidad) {
		return trazabilidad.getLastEstado().map(estHist -> estHist.getEstado().getEstadosGrupo()).orElse(null);
	}

	/**
	 *
	 * Obtiene el ultimo estado de la trazabilidad a nivel de prueba
	 */
	public static MasDataEstado getEstadoActual(Trazabilidad trazabilidad) {
		Optional<TrazabilidadEstHistory> lastEstado = trazabilidad.getLastEstado();
		return lastEstado
				.map(BasicEstadosHistory::getEstado)
				.orElse(null);
	}

	/**
	 * Obtiene todos los estados posibles sin repeticion.
	 */
	public static Set<MasDataEstado> getAll() {
		return new HashSet<>(estadosSrv.getAll());
	}

	/**
	 *
	 * Obtiene el anteultimo estado de la trazabilidad a nivel de solicitud
	 */
	public static MasDataEstado getAnteUltimoEstado(TrazabilidadSolicitud trazabilidad) {
		return trazabilidad.getAnteUltimoEstado().map(x -> x.getEstado()).orElse(null);
	}

	/**
	 *
	 * Obtiene el ultimo estado de la trazabilidad a nivel de albaran
	 */
	@SuppressWarnings("unchecked")
	public static <T extends MutableHistory> MasDataEstado getEstadoActual(Mutable<T> trazabilidad) {
		Optional<MutableHistory> lastEstado = (Optional<MutableHistory>) trazabilidad.getLastEstado();
		if (lastEstado.isPresent())
			return lastEstado.get().obtenerEstado();
		else
			return null;
	}

	/**
	 *
	 * Obtiene el anteultimo estado de la trazabilidad a nivel de prueba
	 */
	public static MasDataEstado getAnteUltimoEstado(Trazabilidad trazabilidad) {
		return trazabilidad.getAnteUltimoEstado().map(BasicEstadosHistory::getEstado).orElse(null);
	}

	/**
	 *
	 * Obtiene los estados activos
	 */
	public static Set<String> getProcessorByEstados() {
		return new HashSet<>(estadosSrv.findDistinctCodeEstado());
	}

	/**
	 * Obtiene las acciones permitidas
	 */
	public static List<AccionesPermitidas> getAccionesPermitidas() {
		return accionesPermitidasService.getAll();
	}

	/**
	 *
	 * Actualiza processors
	 */
	public static void setProcessorByEstados() {
		EstadosUtils.processors = getProcessorByEstados();
	}

	/**
	 *
	 * Obtiene y retorna el procesador de cada estado
	 */
	public static Procesable getProcessor(MasDataEstado estado) {
		Procesable procesable = null;

		if (processors.isEmpty()) {
			processors.addAll(getProcessorByEstados());
		}

		if(processors.contains(estado.getCodeEstado())) {
			procesable = (Procesable) ContextProvider.getBean(estado.getCodeEstado());
		}

		return procesable;
	}

	/**
	 *
	 * Seteamos el estado al padre en caso de que aplique
	 */
	public static boolean aplicaEstadoPadre(MasDataEstado estado, Transicionable<?> entidad) {
		if (estado.getCombinacion().isEmpty())
			return false;
		if (estado.isAplicaPadre()) {
			int withStatus = 0;
			boolean algunoIgual = false;
			List<Transicionable<?>> hijos = entidad.obtieneHijos();
			for (Transicionable<?> item : hijos) {
				Mutable<?> trazabilidad = (Mutable<?>) item.obtieneTrazabilidad();
				if (trazabilidad == null) {
					log.error("El siguiente item no tiene asociada una trazabilidad: " + item);
				}
				List<MutableHistory> estados = trazabilidad.obtieneEstados();
				withStatus += checkIfAreEquals(estados, estado);
				algunoIgual = algunoIgual || (trazabilidad.getLastEstado().isPresent() && estado.equals(trazabilidad.getLastEstado().get().obtenerEstado()));
			}
			if (hijos.size() == withStatus) {
				return algunoIgual || hijos.size() ==0;
			}
		}
		return false;
	}
	
	/**
	 *
	 * Seteamos el estado al padre en caso de que aplique
	 */
	public static boolean aplicaEstadoPadreV2(MasDataEstado estado, Transicionable<?> entidad, EntityManager entityManager) {
		if (estado.getCombinacion().isEmpty())
			return false;
		if (estado.isAplicaPadre()) {
			int withStatus = 0;
			boolean algunoIgual = false;
			List<Transicionable<?>> hijos = entidad.obtieneHijos();
			for (Transicionable<?> item : hijos) {
				Mutable<?> trazabilidad = (Mutable<?>) item.obtieneTrazabilidad();
				String estadoActual = getEstadoActualV2(trazabilidad, entityManager);
				withStatus += checkIfAreEqualsV2(estadoActual, estado);
				algunoIgual = algunoIgual || estado.getCodeEstado().equals(estadoActual);
			}
			if (hijos.size() == withStatus) {
				return algunoIgual || hijos.size() ==0;
			}
		}
		return false;
	}

	/**
	 * Chequeamos que el estado de la entidad sea o no el que se pregunta.
	 */
	public static boolean tieneEstadoDiferente(MasDataEstado estado, Transicionable<?> entidad) {
		Mutable<?> trazabilidad = (Mutable<?>) entidad.obtieneTrazabilidad();
		if (trazabilidad.getLastEstado().isPresent()) {
			MutableHistory history = trazabilidad.getLastEstado().get();
			return !history.obtenerEstado().equals(estado);
		}
		else
			return true;
	}
	
	public static boolean tieneEstadoDiferenteV2(MasDataEstado estado, Transicionable<?> entidad, EntityManager entityManager) {
		Mutable<?> trazabilidad = (Mutable<?>) entidad.obtieneTrazabilidad();
		String estadoActual = getEstadoActualV2(trazabilidad, entityManager);
		return !estadoActual.equals(estado.getCodeEstado());
	}

	public static int checkIfAreEquals(List<MutableHistory> estados, MasDataEstado estado) {
		Set<MasDataEstado> estadosCombinacion = estado.getCombinacion().stream().map(comb -> comb.getCombinable())
				.collect(Collectors.toSet());
		if (estadosCombinacion.isEmpty())
			return 0;
		List<String> combinado = estadosCombinacion.stream().map(comb -> comb.getCodeEstado())
				.collect(Collectors.toList());
		return estados.stream().filter(MutableHistory::isActive)
				.map(estadoTrz -> estadoTrz.obtenerEstado().getCodeEstado())
				.anyMatch(estadoPrueba -> combinado.contains(estadoPrueba)) ? 1 : 0;

	}
	
	public static int checkIfAreEqualsV2(String estadoActual, MasDataEstado estado) {
		Set<MasDataEstado> estadosCombinacion = estado.getCombinacion().stream().map(comb -> comb.getCombinable()).collect(Collectors.toSet());
		if (estadosCombinacion.isEmpty())
			return 0;
		List<String> combinado = estadosCombinacion.stream().map(comb -> comb.getCodeEstado()).collect(Collectors.toList());
		return combinado.contains(estadoActual) ? 1 : 0;
	}

	public static MasDataEstado getEstadoInicial() {
		return estadosSrv.findByCodeEstado(ESTADO_INICIAL);
	}

	/**
	 *
	 * Obtiene el estado borrada
	 *
	 * @return
	 */
	public static MasDataEstado getEstadoBorrada() {
		return estadosSrv.findByCodeEstado(Borrada.CODIGO);
	}

	public static MasDataEstado getEstadoBloqueada() {
		return estadosSrv.findByCodeEstado(Bloqueada.CODIGO);
	}

	public static MasDataEstado getEstadoByCode(String codigoEstado) {
		return estadosSrv.findByCodeEstado(codigoEstado);
	}

	/**
	 * Valida si se permite ejecutar una acción o no
	 */
	public static boolean getAccionPermitida(AccionesUsuario accion, MasDataEstado estado) {
		return getAccionPermitida(accion,estado,NIVEL_CABECERA);
	}
	public static boolean getAccionPermitida(AccionesUsuario accion, MasDataEstado estado, String nivel) {
		Optional<AccionesPermitidas> config = accionesPermitidasService.findAccionPermitidaPorEstadoYNivel(accion,
				estado, nivel);
		if (config.isPresent()) {
			return config.get().isPermitido();
		} else
			return false;
	}

	public static boolean getAccionPermitida(AccionesUsuario accion, long codigoEstado) {
		Optional<MasDataEstado> estado = estadosSrv.findById(Long.valueOf(codigoEstado));
		if (estado.isPresent()) {
			return getAccionPermitida(accion, estado.get());
		} else
			return false;
	}

	/**
	 *
	 * Valida si se permite ejecutar una acción o no
	 */
	public static boolean getAccionPermitida(AccionesUsuario accion, MasDataEstado estadoSolicitud,
			MasDataEstado estadoPrueba) {
		Optional<AccionesPermitidas> configS = accionesPermitidasService.findAccionPermitidaPorEstadoYNivel(accion,
				estadoSolicitud, NIVEL_CABECERA);
		Optional<AccionesPermitidas> configP = accionesPermitidasService.findAccionPermitidaPorEstadoYNivel(accion,
				estadoPrueba, NIVEL_POSICION);
		if (configS.isPresent() && configP.isPresent()) {
			return configS.get().isPermitido() && configP.get().isPermitido();
		} else
			return false;
	}

	/**
	 */
	public static boolean getAccionPermitida(AccionesUsuario accion, long codigoEstadoPeticion, long codigoEstadoPrueba) {
		Optional<MasDataEstado> estadoSolicitud = estadosSrv.findById(Long.valueOf(codigoEstadoPeticion));
		Optional<MasDataEstado> estadoPrueba = estadosSrv.findById(Long.valueOf(codigoEstadoPrueba));
		if (estadoSolicitud.isPresent() && estadoPrueba.isPresent()) {
			return getAccionPermitida(accion, estadoSolicitud.get(), estadoPrueba.get());
		} else
			return false;
	}

	public static boolean getAccionPermitida(AccionesUsuario accion, int codigoEstado, String nivel) {
		Optional<MasDataEstado> estado = estadosSrv.findById(Long.valueOf(codigoEstado));
		if (estado.isPresent() && estado.isPresent()) {
			return getAccionPermitida(accion, estado.get(), nivel);
		} else
			return false;
	}

}
