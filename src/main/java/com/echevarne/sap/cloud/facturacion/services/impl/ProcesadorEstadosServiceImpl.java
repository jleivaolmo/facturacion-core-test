package com.echevarne.sap.cloud.facturacion.services.impl;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;
import com.echevarne.sap.cloud.facturacion.constants.ConstEstados;
import com.echevarne.sap.cloud.facturacion.constants.ConstFacturacion;
import com.echevarne.sap.cloud.facturacion.exception.AbstractExceptionHandler;
import com.echevarne.sap.cloud.facturacion.gestionestados.*;
import com.echevarne.sap.cloud.facturacion.gestionestados.util.EstadosUtils;
import com.echevarne.sap.cloud.facturacion.model.BasicMessagesEntity;
import com.echevarne.sap.cloud.facturacion.model.masterdata.MasDataEstado;
import com.echevarne.sap.cloud.facturacion.model.masterdata.MasDataMotivosEstado;
import com.echevarne.sap.cloud.facturacion.model.masterdata.MasDataTransicionEstado;
import com.echevarne.sap.cloud.facturacion.model.solicitudrecibida.PeticionMuestreo;
import com.echevarne.sap.cloud.facturacion.model.solicitudrecibida.PeticionMuestreoItems;
import com.echevarne.sap.cloud.facturacion.model.trazabilidad.*;
import com.echevarne.sap.cloud.facturacion.services.*;
import com.echevarne.sap.cloud.facturacion.util.MessagesUtils;
import lombok.var;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service("procesadorEstadosService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class ProcesadorEstadosServiceImpl extends AbstractExceptionHandler implements ProcesadorEstadosService {

	private final MasDataTransicionEstadoService transicionesSrv;
	private final MasDataEstadoService estadosSrv;

	private final MasDataMotivosEstadoService motivosEstadoService;

	private final TrazabilidadEstHistoryService trazabilidadHistory;

	private final TrazabilidadSolEstHistoryService trazabilidadSolHistory;

	private final TrazabilidadAlbEstHistoryService trazabilidadAlbHistory;

	private final TrazabilidadSolAgrEstHistoryService trazabilidadSolAgrEstHistoryService;

	private final TrazabilidadSolAgrItemsEstHistoryService trazabilidadSolAgrItemsEstHistoryService;

	private final TrazabilidadService trazabilidadSrv;

    private final PeticionMuestreoItemsService peticionMuestreoItemsService;

	private final PeticionMuestreoService peticionMuestreoService;
	
	@Autowired
	private EntityManager entityManager;
	
	@Autowired
	public ProcesadorEstadosServiceImpl(
			MasDataTransicionEstadoService transicionesSrv,
			MasDataEstadoService estadosSrv,
			MasDataMotivosEstadoService motivosEstadoService,
			TrazabilidadEstHistoryService trazabilidadHistory,
			TrazabilidadSolEstHistoryService trazabilidadSolHistory,
			TrazabilidadAlbEstHistoryService trazabilidadAlbHistory,
			TrazabilidadSolAgrEstHistoryService trazabilidadSolAgrEstHistoryService,
			TrazabilidadSolAgrItemsEstHistoryService trazabilidadSolAgrItemsEstHistoryService,
			TrazabilidadService trazabilidadSrv,
			PeticionMuestreoItemsService peticionMuestreoItemsService,
			PeticionMuestreoService peticionMuestreoService
	) {
		this.transicionesSrv = transicionesSrv;
		this.estadosSrv = estadosSrv;
		this.motivosEstadoService = motivosEstadoService;
		this.trazabilidadHistory = trazabilidadHistory;
		this.trazabilidadSolHistory = trazabilidadSolHistory;
		this.trazabilidadAlbHistory = trazabilidadAlbHistory;
		this.trazabilidadSolAgrEstHistoryService = trazabilidadSolAgrEstHistoryService;
		this.trazabilidadSolAgrItemsEstHistoryService = trazabilidadSolAgrItemsEstHistoryService;
		this.trazabilidadSrv = trazabilidadSrv;
		this.peticionMuestreoItemsService = peticionMuestreoItemsService;
		this.peticionMuestreoService = peticionMuestreoService;
	}


	/**
	 * Métodos del servicio
	 */
	@Override
	@Transactional
	public <T extends BasicMessagesEntity> Map<MasDataEstado, Set<Transicionable<?>>> procesarEstados(
			Transicionable<?> entity, Set<T> messages) {
		final Map<MasDataEstado, Set<Transicionable<?>>> realizadosFinal = new HashMap<>();
		final Map<MasDataEstado, Set<Transicionable<?>>> realizados = new HashMap<>();
		List<Transicionable<?>> childs = entity.obtieneHijos();
		Optional<List<String>> destinos = entity.obtieneDestinos();
		// Ejecutar hijos
		for (Transicionable<?> child : childs) {
			realizadosFinal.putAll(procesarEstados(child, messages));
		}
		if (destinos.isPresent()) {
			procesarEntidad(entity, destinos.get(), realizados, messages, null, false, true);
			realizadosFinal.putAll(realizados);
		} else {
			procesarEntidad(entity, Collections.emptyList(), realizados, messages, null, false, true);
		}
		// Validamos si los estados realizados aplican al padre
		checkEstadosRealizados(entity, realizados, messages);
		return realizadosFinal;
	}

	@Override
	public <T extends BasicMessagesEntity> boolean setEstado(Transicionable<?> entity, MasDataEstado destino,
			Set<T> messages, MasDataMotivosEstado motivo) {
		return setEstado(entity, destino.getCodeEstado(), messages, motivo, false, true);
	}

	@Override
	public <T extends BasicMessagesEntity> boolean setEstado(Transicionable<?> entity, MasDataEstado destino,
			Set<T> messages, MasDataMotivosEstado motivo, boolean manual, boolean afectaImporte) {
		return setEstado(entity, destino.getCodeEstado(), messages, motivo, manual, afectaImporte);
	}

	@Override
	public <T extends BasicMessagesEntity> boolean setEstado(Transicionable<?> entity, String destino,
			Set<T> messages) {
		return setEstado(entity, destino, messages, null, false, true);
	}

	@Override
	public <T extends BasicMessagesEntity> boolean setEstado(Transicionable<?> entity, String destino, Set<T> messages,
			MasDataMotivosEstado motivo) {
		return setEstado(entity, destino, messages, motivo, false, true);
	}

	@Override
	public <T extends BasicMessagesEntity> boolean fuerzaTransiciones(Transicionable<?> entity, List<String> destinos,
			Set<T> messages, MasDataMotivosEstado motivo, boolean manual) {
		Map<MasDataEstado, Set<Transicionable<?>>> realizados = new HashMap<>();
		boolean result = procesarEntidad(entity, destinos, realizados, messages, motivo, manual, true);
		// Validamos si los estados realizados aplican al padre
		checkEstadosRealizados(entity, realizados, messages);
		return result;
	}
	
	@Override
	public <T extends BasicMessagesEntity> boolean fuerzaTransicionesV2(Transicionable<?> entity, List<String> destinos,
			Set<T> messages, MasDataMotivosEstado motivo, boolean manual) {
	    if (log.isTraceEnabled()) {
	        log.trace("Ejecución de fuerzaTransicionesV2");
	    }		
		Map<MasDataEstado, Set<Transicionable<?>>> realizados = new HashMap<>();
		boolean result = procesarEntidadV2(entity, destinos, realizados, messages, motivo, manual, true);
		// Validamos si los estados realizados aplican al padre
		checkEstadosRealizadosV2(entity, realizados, messages);
		return result;
	}

	@Override
	public <T extends BasicMessagesEntity> boolean setEstado(Transicionable<?> entity, String destino, Set<T> messages,
			MasDataMotivosEstado motivo, boolean manual, boolean afectaImporte) {
		Map<MasDataEstado, Set<Transicionable<?>>> realizados = new HashMap<>();
		List<String> destinos = Collections.singletonList(destino);
		boolean result = procesarEntidad(entity, destinos, realizados, messages, motivo, manual, afectaImporte);
		// Validamos si los estados realizados aplican al padre
		checkEstadosRealizados(entity, realizados, messages);
		return result;
	}

	@Override
	public boolean excluirPrueba(Trazabilidad trazabilidad, Set<TrazabilidadSolicitudMessages> messages,
			boolean manual) {

		boolean esPerfil = trazabilidad.getItemRec().getEsPerfil();
		if (esPerfil) {
			int idItem = trazabilidad.getItemInd().getSalesOrderIndItem();
			final List<PeticionMuestreoItems> peticionMuestreoItemsList = trazabilidad.getItemInd().getSolicitudInd().getItems().stream()
					.filter(i -> i.getHigherLevelltem() == idItem).map(sii -> sii.getTrazabilidad().getItemRec()).collect(Collectors.toList());
			for(PeticionMuestreoItems peticionMuestreoItems: peticionMuestreoItemsList){
				excluirPrueba(peticionMuestreoItems.getTrazabilidad(), messages, false);
			}
		}
		// Si se trata de una posición NO facturable, incongruente, etc, no puede
		// excluirse de forma manual
		var listaTiposNoAceptados = Arrays.asList(ConstFacturacion.TIPO_POSICION_NO_FACTURABLE,
				ConstFacturacion.TIPO_POSICION_EXCLUIDA, ConstFacturacion.TIPO_POSICION_INCONGRUENTE,
				ConstFacturacion.TIPO_POSICION_BLOQUEO_RECHAZO);
		if (!listaTiposNoAceptados.contains(trazabilidad.getItemInd().getSalesOrderItemCategory())) {
			trazabilidad.getItemInd().setSalesOrderItemCategory(ConstFacturacion.TIPO_POSICION_EXCLUIDA);
			var response = setEstado(trazabilidad.getItemInd(), Excluida.CODIGO, messages, null, manual, true);
			trazabilidadSrv.update(trazabilidad);
			return response;
		} else {
			return false;
		}
	}

	@Override
	public boolean incluirPrueba(Trazabilidad trazabilidad, Set<TrazabilidadSolicitudMessages> messages) {
		boolean esPerfil = trazabilidad.getItemRec().getEsPerfil();
		if (esPerfil) {
			int idItem = trazabilidad.getItemInd().getSalesOrderIndItem();
			trazabilidad.getItemInd().getSolicitudInd().getItems().stream()
					.filter(i -> i.getHigherLevelltem() == idItem).map(sii -> sii.getTrazabilidad().getItemRec())
					.allMatch(p -> incluirPrueba(p.getTrazabilidad(), messages));
		}
		// Solo se permite incluir manualmente las pruebas excluidas manualmente
		if (trazabilidad.getItemInd().getSalesOrderItemCategory().equals(ConstFacturacion.TIPO_POSICION_EXCLUIDA)) {
			// Reseteando el salesOrderItemCategory se permite saltar a estado Facturable
			// desde el estado Excluida manualmente
			trazabilidad.getItemInd().setSalesOrderItemCategory("");
			Optional<List<String>> destinos = trazabilidad.getItemInd().obtieneDestinos();
			if(destinos.isPresent()){
				boolean response = fuerzaTransiciones(trazabilidad.getItemInd(), destinos.get(), messages, null, false);
				trazabilidadSrv.update(trazabilidad);
				return response;
			}
		}
		return false;
	}

	@Override
	public boolean bloquearPrueba(PeticionMuestreoItems entity) {
		Trazabilidad trazabilidad = entity.obtieneTrazabilidad();
		if (!trazabilidad.getLastEstado().isPresent() || Bloqueada.CODIGO.equals(trazabilidad.getUltimoEstado())) {
			return false;
		}
		if (trazabilidad.getAnteUltimoEstado().isPresent()) {
			Set<BasicMessagesEntity> messages = new HashSet<>();
			entity.setBloqueoAutomatico(true);
			Optional<MasDataMotivosEstado> optMotivo = motivosEstadoService.findByCodigoAndActive("BLKM", true);
			return setEstado(entity, Bloqueada.CODIGO, messages, optMotivo.orElse(null));
		}
		return false;
	}

	@Override
    public boolean desbloquearPrueba(PeticionMuestreoItems entity, Set<BasicMessagesEntity> messages) {
        Trazabilidad trazabilidad = entity.obtieneTrazabilidad();
        if (!trazabilidad.getLastEstado().isPresent() || !Bloqueada.CODIGO.equals(trazabilidad.getUltimoEstado())) {
            return false;
        }
        if (trazabilidad.getAnteUltimoEstado().isPresent()) {
            String codigoEstadoDondeIr = trazabilidad.getAnteUltimoEstado().get().getEstado().getCodeEstado();
			entity.setBloqueoAutomatico(false);
			boolean result = setEstado(entity, codigoEstadoDondeIr, messages);
			peticionMuestreoItemsService.update(entity);
			return result;
        }
        return false;
    }

	@Override
	public boolean desbloquearPeticion(PeticionMuestreo entity, Set<BasicMessagesEntity> messages) {
		TrazabilidadSolicitud trazabilidad = entity.obtieneTrazabilidad();
		if (!trazabilidad.getLastEstado().isPresent() || !Bloqueada.CODIGO.equals(trazabilidad.getUltimoEstado())) {
			return false;
		}
		if (trazabilidad.getAnteUltimoEstado().isPresent()) {
			String codigoEstadoDondeIr = trazabilidad.getAnteUltimoEstado().get().getEstado().getCodeEstado();
			boolean result = setEstado(entity, codigoEstadoDondeIr, messages,null, true, true);
			peticionMuestreoService.update(entity);
			return result;
		}
		return false;
	}

	/**************************************
	 *
	 * Metodos de apoyo
	 *
	 **************************************/

	/**
	 *
	 * Procesamos la entidad sean transicionable
	 */
	private <T extends BasicMessagesEntity> boolean procesarEntidad(Transicionable<?> entity,
			List<String> codigosEstado, Map<MasDataEstado, Set<Transicionable<?>>> realizados, Set<T> messages,
			MasDataMotivosEstado motivo, boolean manual, boolean afectaImporte) {

		// Obtiene la trazabilidad
		Mutable<?> trazabilidad = (Mutable<?>) entity.obtieneTrazabilidad();
		// Obtiene el estado actual
		MasDataEstado estadoActual = getEstadoActual(entity, trazabilidad, realizados, messages);
		// Establece cual es son las transiciones validas (a realizar)
		List<MasDataTransicionEstado> transicionesConfiguradas = validaTransicionPorConfiguracion(estadoActual,
				codigosEstado);
		return realizarTransiciones(entity, trazabilidad, transicionesConfiguradas, realizados, motivo, messages,
				manual, afectaImporte);

	}
	
	private <T extends BasicMessagesEntity> boolean procesarEntidadV2(Transicionable<?> entity,
			List<String> codigosEstado, Map<MasDataEstado, Set<Transicionable<?>>> realizados, Set<T> messages,
			MasDataMotivosEstado motivo, boolean manual, boolean afectaImporte) {

		// Obtiene la trazabilidad
		Mutable<?> trazabilidad = (Mutable<?>) entity.obtieneTrazabilidad();
		// Obtiene el estado actual
		String estadoActual = getEstadoActualV2(trazabilidad);
		// Establece cual es son las transiciones validas (a realizar)
		List<MasDataTransicionEstado> transicionesConfiguradas = validaTransicionPorConfiguracionV2(estadoActual,
				codigosEstado);
		return realizarTransicionesV2(entity, trazabilidad, transicionesConfiguradas, realizados, motivo, messages,
				manual, afectaImporte);

	}

	/**
	 *
	 * Obtiene el estado actual
	 *
	 * @param entity
	 * @param trazabilidad
	 * @param realizados
	 * @param messages
	 * @return
	 *
	 */
	private <T extends BasicMessagesEntity> MasDataEstado getEstadoActual(Transicionable<?> entity,
			Mutable<?> trazabilidad, Map<MasDataEstado, Set<Transicionable<?>>> realizados, Set<T> messages) {
		MasDataEstado estadoActual = EstadosUtils.getEstadoActual(trazabilidad);
		if (estadoActual == null) {
			saveHistoricoEstado(getEstadoInicial(), trazabilidad, null, true, true);
			List<String> estadosAutomaticos = getEstadosAutomaticos(getEstadoInicial());
			if (!estadosAutomaticos.isEmpty()) {
				procesarEntidad(entity, estadosAutomaticos, realizados, messages, null, false, true);
			}
			estadoActual = EstadosUtils.getEstadoActual(trazabilidad);
		}
		return estadoActual;
	}
	
	private String getEstadoActualV2(Mutable<?> trazabilidad) {
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
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private <T extends BasicMessagesEntity> boolean realizarTransiciones(Transicionable<?> entity,
			Mutable<?> trazabilidad, List<MasDataTransicionEstado> transicionesConfiguradas,
			Map<MasDataEstado, Set<Transicionable<?>>> realizados, MasDataMotivosEstado motivo, Set<T> messages,
			boolean manual, boolean afectaImporte) {
		boolean setted = false;
		for (MasDataTransicionEstado transicion : transicionesConfiguradas) {

			if (validaTransicionPorCondiciones(entity, transicion, manual)) {
				Map<MasDataMotivosEstado, String[]> mapMotivo = obtieneMotivoDelEstado(entity, transicion, manual);
				desactivarAnterior((Mutable) trazabilidad);
				// Almacena el nuevo estado
				MasDataMotivosEstado motivoEstado = getMotivo(motivo, mapMotivo);
				saveHistoricoEstado(transicion.getDestino(), trazabilidad, motivoEstado,
						getEsAutomatico(transicion.getDestino(), motivoEstado, manual), afectaImporte, getVariables(mapMotivo));
				MessagesUtils.addSuccess(messages, getEntityMessageId(trazabilidad),
						transicion.getDestino().getNombre(), getEntityMessageArgs(trazabilidad), null, null);
				// Guardamos el estado transicionado
				addEstadoRealizado(realizados, entity, transicion.getDestino());
				setted = true;
				manual = false;
				// Busca cambios automaticos y realiza recursividad
				List<String> estadosAutomaticos = getEstadosAutomaticos(transicion.getDestino());
				if (!estadosAutomaticos.isEmpty()) {
					procesarEntidad(entity, estadosAutomaticos, realizados, messages, null, false, true);
				}
			}
		}
		return setted;
	}
	
	private <T extends BasicMessagesEntity> boolean realizarTransicionesV2(Transicionable<?> entity,
			Mutable<?> trazabilidad, List<MasDataTransicionEstado> transicionesConfiguradas,
			Map<MasDataEstado, Set<Transicionable<?>>> realizados, MasDataMotivosEstado motivo, Set<T> messages,
			boolean manual, boolean afectaImporte) {
		boolean setted = false;
		for (MasDataTransicionEstado transicion : transicionesConfiguradas) {

			if (validaTransicionPorCondicionesV2(entity, transicion, manual)) {
				Map<MasDataMotivosEstado, String[]> mapMotivo = obtieneMotivoDelEstado(entity, transicion, manual);
				// Almacena el nuevo estado
				MasDataMotivosEstado motivoEstado = getMotivo(motivo, mapMotivo);
				saveHistoricoEstadoV2(transicion.getDestino(), trazabilidad, motivoEstado,
						getEsAutomatico(transicion.getDestino(), motivoEstado, manual), afectaImporte, getVariables(mapMotivo));
				MessagesUtils.addSuccess(messages, getEntityMessageId(trazabilidad),
						transicion.getDestino().getNombre(), getEntityMessageArgs(trazabilidad), null, null);
				// Guardamos el estado transicionado
				addEstadoRealizado(realizados, entity, transicion.getDestino());
				setted = true;
				manual = false;
				// Busca cambios automaticos y realiza recursividad
				List<String> estadosAutomaticos = getEstadosAutomaticos(transicion.getDestino());
				if (!estadosAutomaticos.isEmpty()) {
					procesarEntidadV2(entity, estadosAutomaticos, realizados, messages, null, false, true);
				}
			}
		}
		return setted;
	}

	private boolean getEsAutomatico(MasDataEstado estado, MasDataMotivosEstado motivo, boolean manual) {
		if (estado == null || motivo == null || motivo.getCodigo() == null) return true;
		if (Bloqueada.CODIGO.equals(estado.getCodeEstado()) &&
				( motivo.getCodigo().equals(ConstEstados.PREFIJO_ESTADO_BLKA) || motivo.getCodigo().equals(ConstEstados.PREFIJO_ESTADO_BLKPAU))) {
			return true;
		}
		return !manual;
	}

	/**
	 * Obtiene los motivos del estado
	 */
	private String[] getVariables(Map<MasDataMotivosEstado, String[]> mapMotivo) {
		for (Map.Entry<MasDataMotivosEstado, String[]> motivoEstado : mapMotivo.entrySet()) {
			if (motivoEstado.getKey() != null) {
				return motivoEstado.getValue();
			}
		}
		return new String[0];
	}

	/**
	 *
	 * Obtiene el motivo del estado
	 */
	private MasDataMotivosEstado getMotivo(MasDataMotivosEstado motivo, Map<MasDataMotivosEstado, String[]> mapMotivo) {
		if (motivo != null)
			return motivo;
		for (Map.Entry<MasDataMotivosEstado, String[]> motivoEstado : mapMotivo.entrySet()) {
			if (motivoEstado.getKey() != null) {
				return motivoEstado.getKey();
			}
		}
		return null;
	}

	/**
	 *
	 * En base a los estados realizados validamos si hay acciones por realizar
	 */
	private <T extends BasicMessagesEntity> void checkEstadosRealizados(Transicionable<?> entity,
			Map<MasDataEstado, Set<Transicionable<?>>> realizados, Set<T> messages) {
		Transicionable<?> padre = entity.obtienePadre();
		if (padre == null)
			return;
		Set<Map.Entry<MasDataEstado, Set<Transicionable<?>>>> realizadosCopy = new HashSet<>(realizados.entrySet());
		realizadosCopy.forEach(realizado -> {
			boolean aplicaPadre = EstadosUtils.aplicaEstadoPadre(realizado.getKey(), entity);
			if (aplicaPadre && EstadosUtils.tieneEstadoDiferente(realizado.getKey(), padre)) {
				List<String> destino = Collections.singletonList(realizado.getKey().getCodeEstado());
				procesarEntidad(padre, destino, realizados, messages, null, false, true);
				checkEstadosRealizados(padre, realizados, messages);
			}
		});
	}
	
	private <T extends BasicMessagesEntity> void checkEstadosRealizadosV2(Transicionable<?> entity,
			Map<MasDataEstado, Set<Transicionable<?>>> realizados, Set<T> messages) {
		Transicionable<?> padre = entity.obtienePadre();
		if (padre == null)
			return;
		Set<Map.Entry<MasDataEstado, Set<Transicionable<?>>>> realizadosCopy = new HashSet<>(realizados.entrySet());
		realizadosCopy.forEach(realizado -> {
			boolean aplicaPadre = EstadosUtils.aplicaEstadoPadreV2(realizado.getKey(), entity, entityManager);
			if (aplicaPadre && EstadosUtils.tieneEstadoDiferenteV2(realizado.getKey(), padre, entityManager)) {
				List<String> destino = Collections.singletonList(realizado.getKey().getCodeEstado());
				procesarEntidadV2(padre, destino, realizados, messages, null, false, true);
				checkEstadosRealizadosV2(padre, realizados, messages);
			}
		});
	}

	private String getEntityMessageArgs(Mutable<?> entity) {
		return entity.getMessageArgs();
	}

	private String getEntityMessageId(Mutable<?> entity) {
		return entity.getMessageId();
	}

	/**
	 * Obtiene el motivo del estado en base a las condiciones que se establezcan
	 */
	private Map<MasDataMotivosEstado, String[]> obtieneMotivoDelEstado(Transicionable<?> entity,
			MasDataTransicionEstado transicion, boolean manual) {
		Procesable procesadorDestino = EstadosUtils.getProcessor(transicion.getDestino());
		return procesadorDestino != null ? entity.obtieneMotivo(procesadorDestino, transicion.getOrigen(), manual)
				: null;
	}

	/**
	 *
	 * Valida si a los estados que se intenta transicionar existe en la
	 * configuracion (tabla de transiciones)
	 */
	private List<MasDataTransicionEstado> validaTransicionPorConfiguracion(MasDataEstado estadoOrigen,
			List<String> estadosDestino) {
		List<MasDataTransicionEstado> transiciones;
		if (estadosDestino.isEmpty()) {
			transiciones = Collections.emptyList();
		} else {
			transiciones = transicionesSrv.findAllActiveByOrigenCodeAndDestinoCode(estadoOrigen, estadosDestino);
		}
		return transiciones;
	}
	
	private List<MasDataTransicionEstado> validaTransicionPorConfiguracionV2(String estadoOrigen, List<String> estadosDestino) {
		List<MasDataTransicionEstado> transiciones;
		if (estadosDestino.isEmpty()) {
			transiciones = Collections.emptyList();
		} else {
			transiciones = transicionesSrv.findAllActiveByOrigenCodeAndDestinoCode(estadoOrigen, estadosDestino);
		}
		return transiciones;
	}

	/**
	 * Determina si la transición es valida en base a condiciones en cada estado al
	 * nivel de los objetos que son transicionables (entity)
	 *
	 */
	private boolean validaTransicionPorCondiciones(Transicionable<?> entity, MasDataTransicionEstado transicion,
			boolean manual) {
		Procesable procesadorDestino = EstadosUtils.getProcessor(transicion.getDestino());
		return procesadorDestino != null && entity.transicionar(procesadorDestino, transicion.getOrigen(), manual);
	}
	
	private boolean validaTransicionPorCondicionesV2(Transicionable<?> entity, MasDataTransicionEstado transicion,
			boolean manual) {
		Procesable procesadorDestino = EstadosUtils.getProcessor(transicion.getDestino());
		return procesadorDestino != null && entity.transicionarV2(procesadorDestino, transicion.getOrigen(), manual);
	}

	/**
	 * Obtiene el estado inicial
	 */
	private MasDataEstado getEstadoInicial() {
		return estadosSrv.findByCodeEstado(EstadosUtils.ESTADO_INICIAL);
	}

	/**
	 * Obtiene los estados automaticos para un estado origen
	 */
	private List<String> getEstadosAutomaticos(MasDataEstado estadoOrigen) {
		List<MasDataTransicionEstado> transicionesAutomaticas = transicionesSrv
				.findAllByOrigenAndAutomatico(estadoOrigen, true);
		return transicionesAutomaticas
				.stream()
				.map(MasDataTransicionEstado::getDestino)
				.map(MasDataEstado::getCodeEstado)
				.collect(Collectors.toList());
	}

	/**
	 *
	 * Establece un nuevo estado asociado a la solicitud con motivo (cabecera)
	 */
	@SuppressWarnings("unchecked")
	private <T extends MutableHistory> void saveHistoricoEstado(MasDataEstado estado, Mutable<T> trazabilidad,
			MasDataMotivosEstado motivo, boolean automatico, boolean afectaImporte, String... motivosVar) {
		Optional<T> history = trazabilidad.newInstance(estado, motivo, automatico, afectaImporte);
		if (history.isPresent()) {
			MutableHistory createdHistory = createHistory(history.get());
			history.get().setMotivosVars(motivosVar);
			trazabilidad.addHistory((T)createdHistory);
		}
	}
	
	private <T extends MutableHistory> void saveHistoricoEstadoV2(MasDataEstado estado, Mutable<T> trazabilidad, MasDataMotivosEstado motivo,
			boolean automatico, boolean afectaImporte, String... motivosVar) {
		String[] arrMotivoVar = { "", "", "", "" };
		if (motivosVar != null) {
			for (int i = 0; i < motivosVar.length; i++) {
				arrMotivoVar[i] = motivosVar[i];
			}
		}
		Long idMotivo = motivo==null?null:motivo.getId();
		if (trazabilidad instanceof Trazabilidad) {
			trazabilidadHistory.insertTrazabilidadEstHistory("Generic", automatico, arrMotivoVar[0], arrMotivoVar[1], arrMotivoVar[2], arrMotivoVar[3],
					estado.getId(), idMotivo, trazabilidad.getId());
		} else if (trazabilidad instanceof TrazabilidadSolicitud) {
			trazabilidadSolHistory.insertTrazabilidadEstHistory("Generic", automatico, arrMotivoVar[0], arrMotivoVar[1], arrMotivoVar[2], arrMotivoVar[3],
					estado.getId(), idMotivo, trazabilidad.getId(), afectaImporte);
		} else if (trazabilidad instanceof TrazabilidadSolicitudAgrupada) {
			trazabilidadSolAgrEstHistoryService.insertTrazabilidadEstHistory("Generic", automatico, arrMotivoVar[0], arrMotivoVar[1], arrMotivoVar[2],
					arrMotivoVar[3], estado.getId(), idMotivo, trazabilidad.getId());
		} else if (trazabilidad instanceof TrazabilidadSolAgrItems) {
			trazabilidadSolAgrItemsEstHistoryService.insertTrazabilidadEstHistory("Generic", automatico, arrMotivoVar[0], arrMotivoVar[1], arrMotivoVar[2],
					arrMotivoVar[3], estado.getId(), idMotivo, trazabilidad.getId());
		}
	}

	/**
	 *
	 * Almacenamos el historial
	 *
	 * @param <T>
	 * @param t
	 */
	private <T extends MutableHistory> MutableHistory createHistory(T t) {
		MutableHistory history = null;
		if (t instanceof TrazabilidadAlbaranEstHistory) {
			history = trazabilidadAlbHistory.create((TrazabilidadAlbaranEstHistory) t);
		} else if (t instanceof TrazabilidadSolicitudEstHistory) {
			history = trazabilidadSolHistory.create((TrazabilidadSolicitudEstHistory) t);
		} else if (t instanceof TrazabilidadEstHistory) {
			history = trazabilidadHistory.create((TrazabilidadEstHistory) t);
		} else if (t instanceof TrazabilidadSolAgrEstHistory) {
			history = trazabilidadSolAgrEstHistoryService.create((TrazabilidadSolAgrEstHistory) t);
		} else if (t instanceof TrazabilidadSolAgrItemsEstHistory) {
			history = trazabilidadSolAgrItemsEstHistoryService.create((TrazabilidadSolAgrItemsEstHistory) t);
		}

		return history;

		// TODO: Esto funciona pero los test fallan, hay alguna forma de que sea
		// generico sin que falle?

		// BasicEntity entity = t.getEntity();
		// if (entity != null) {
		// JpaRepository jpaRepository = (JpaRepository)
		// ContextProvider.getRepository(entity);
		// if (jpaRepository != null) {
		// jpaRepository.save(entity);
		// }
		// }
	}

	/**
	 *
	 * Desactiva el estado origen de la transición
	 * DB: añadido que no solo desactive el anterior, sino todos los demas, ya que solo puede haber un estado activo
	 *
	 */
	private void desactivarAnterior(Mutable<MutableHistory> trazabilidad) {
		trazabilidad.obtieneEstados().forEach(x -> x.inactiveEstado());
	}
	
	private void addEstadoRealizado(Map<MasDataEstado, Set<Transicionable<?>>> realizados, Transicionable<?> entity,
			MasDataEstado destino) {
		if (realizados.containsKey(destino)) {
			realizados.get(destino).add(entity);
		} else {
			Set<Transicionable<?>> items = new HashSet<Transicionable<?>>();
			items.add(entity);
			realizados.put(destino, items);
		}
	}

	@Override
	public <T extends BasicMessagesEntity> boolean setEstadoV2(Transicionable<?> entity, String destino, Set<T> messages, MasDataMotivosEstado motivo) {
	    if (log.isTraceEnabled()) {
	        log.trace("Ejecucion de setEstadoV2 para " + entity + " con destino " + destino);
	    }
	    return setEstadoV2(entity, destino, messages, motivo, false, true);
	}

	@Override
	public <T extends BasicMessagesEntity> boolean setEstadoV2(Transicionable<?> entity, String destino, Set<T> messages,
			MasDataMotivosEstado motivo, boolean manual, boolean afectaImporte) {
		Map<MasDataEstado, Set<Transicionable<?>>> realizados = new HashMap<>();
		List<String> destinos = Collections.singletonList(destino);
		boolean result = procesarEntidadV2(entity, destinos, realizados, messages, motivo, manual, afectaImporte);
		// Validamos si los estados realizados aplican al padre
		checkEstadosRealizadosV2(entity, realizados, messages);
		return result;
	}

}
