package com.echevarne.sap.cloud.facturacion.services.impl;

import static com.echevarne.sap.cloud.facturacion.util.Destinations.Enum.BATCH;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import com.echevarne.sap.cloud.facturacion.config.ContextProvider;
import com.echevarne.sap.cloud.facturacion.constants.ConstFacturacion;
import com.echevarne.sap.cloud.facturacion.dto.ParamPoolFacturacion;
import com.echevarne.sap.cloud.facturacion.model.BasicMessagesEntity;
import com.echevarne.sap.cloud.facturacion.model.facturacion.ControlPeriodos;
import com.echevarne.sap.cloud.facturacion.model.facturacion.PeriodosFacturados;
import com.echevarne.sap.cloud.facturacion.model.facturacion.PeriodosFacturadosCias;
import com.echevarne.sap.cloud.facturacion.model.facturacion.PeriodosFacturadosClientes;
import com.echevarne.sap.cloud.facturacion.model.facturacion.PeriodosFacturadosCodDelegacion;
import com.echevarne.sap.cloud.facturacion.model.facturacion.PeriodosFacturadosCodigosPeticion;
import com.echevarne.sap.cloud.facturacion.model.facturacion.PeriodosFacturadosOrgVentas;
import com.echevarne.sap.cloud.facturacion.model.facturacion.PeriodosFacturadosPruebas;
import com.echevarne.sap.cloud.facturacion.model.facturacion.PeriodosFacturadosRemitentes;
import com.echevarne.sap.cloud.facturacion.model.facturacion.PeriodosFacturadosSectores;
import com.echevarne.sap.cloud.facturacion.model.facturacion.PeriodosFacturadosTipologias;
import com.echevarne.sap.cloud.facturacion.model.facturacion.PeriodosFacturadosTipologiasClaves;
import com.echevarne.sap.cloud.facturacion.model.facturacion.PeriodosFacturadosTiposPeticion;
import com.echevarne.sap.cloud.facturacion.repositories.ParametrosGeneralesRep;
import com.echevarne.sap.cloud.facturacion.repositories.PeriodosFacturadosRep;
import com.echevarne.sap.cloud.facturacion.services.PeriodosFacturadosService;
import com.echevarne.sap.cloud.facturacion.services.SolicitudesAgrupadasService;
import com.echevarne.sap.cloud.facturacion.util.DateUtils;
import com.echevarne.sap.cloud.facturacion.util.Destinations;
import com.echevarne.sap.cloud.facturacion.util.StringUtils;
import lombok.var;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("periodosFacturadosSrv")
public class PeriodosFacturadosServiceImpl extends CrudServiceImpl<PeriodosFacturados, Long> implements PeriodosFacturadosService {

	private static final String UMBRALLOGPLANIFICADO = "UMBRALLOGPLANIFICADO";
	private static final String DEFINICIONLOGGRANDE = "DEFINICIONLOGGRANDE";
	private static final String HORALOGPLANIFICADO = "HORALOGPLANIFICADO";
	private static final String HORAFINLOGPLANIFICADO = "HORAFINLOGPLANIFICADO";
	private static final String MINUTOSENTRELOGSPF = "MINUTOSENTRELOGSPF";
	private static final String MSPROCESOLOGPRUEBA = "MSPROCESOLOGPRUEBA";
	private static final String LANZAR_LOG_PLANIFICADO_JOB = "LanzarLogPlanificadoJob";
	public static final String ANY = "*";
	private static final String CF_INSTANCE_GUID = "CF_INSTANCE_GUID";
	private static final long MEMORY_BY_POSITION = 1000 * 30;

	@Autowired
	private EntityManager entityManager;

	@Autowired
	private ParametrosGeneralesRep parametrosGeneralesRep;
	
	@Autowired
	private SolicitudesAgrupadasService solAgrSrv;
	
	@Autowired
	public PeriodosFacturadosServiceImpl(final PeriodosFacturadosRep periodosFacturadosRep) {
		super(periodosFacturadosRep);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public PeriodosFacturados crearPeriodo(Set<String> codigosCliente, Date fechaInicioFacturacion, Date fechaFinFacturacion, Set<String> organizacionesVentas,
			Map<Integer, Set<String>> agrupaciones, Long idAgrupacion, ParamPoolFacturacion paramPool, String operacion, BigDecimal monto, String codUsuario,
			String nombreUsuario) {
		try {
			if (log.isDebugEnabled())
				log.debug("Almacenamos el log de facturacion en una nueva transacción");
			PeriodosFacturados periodo = new PeriodosFacturados();
			periodo.setIdAgrupacion(idAgrupacion);
			periodo.setOperacion(operacion);
			periodo.setMonto(monto);
			rellenarListas(codigosCliente, organizacionesVentas, agrupaciones, paramPool, periodo);
			Calendar clFechaInicioFacturacion = DateUtils.convertToCalendar(fechaInicioFacturacion);
			periodo.setFechaInicioFacturacion(clFechaInicioFacturacion);
			Calendar clFechaFinFacturacion = DateUtils.convertToCalendar(fechaFinFacturacion);
			periodo.setFechaFinFacturacion(clFechaFinFacturacion);
			periodo.setFechaEjecucion(Calendar.getInstance());
			periodo.setCodUsuario(codUsuario);
			periodo.setNombreUsuario(nombreUsuario);
			ControlPeriodos control = new ControlPeriodos();
			control.setPeriodo(periodo);
			periodo.setControl(control);
			validarPlanificacion(periodo);
			var response = createAndFlush(periodo);
			if (response.getControl().getEstadoActual() == ControlPeriodos.ESTADO_PLANIFICADO) {
				log.info("Se registra periodo " + periodo.getId() + " como PLANIFICADO");
				registrarLogPlanificadoBatch(periodo.getId(), codUsuario, nombreUsuario);
			} else {
				String instanceUUID = System.getenv(CF_INSTANCE_GUID);
				periodo.setUuidInstance(instanceUUID);
			}
			return response;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void setEstadoPeriodo(Long idPeriodo, Integer estado) {
		var periodo = this.findById(idPeriodo).get();
		periodo.getControl().setEstadoActual(estado);
		if (estado == ControlPeriodos.ESTADO_COMENZADO) {
			periodo.getControl().setFechaInicio(new Timestamp(System.currentTimeMillis()));
			String instanceUUID = System.getenv(CF_INSTANCE_GUID);
			periodo.setUuidInstance(instanceUUID);
		}
	}

	private void rellenarListas(Set<String> codigosCliente, Set<String> organizacionesVentas, Map<Integer, Set<String>> agrupaciones,
			ParamPoolFacturacion paramPool, PeriodosFacturados periodo) {
		if (codigosCliente.size() == 0) {
			codigosCliente.add(ANY);
		}
		codigosCliente.forEach(i -> {
			var elemento = new PeriodosFacturadosClientes();
			elemento.setCodigoCliente(i);
			periodo.addClientes(elemento);
		});
		organizacionesVentas.forEach(i -> {
			var elemento = new PeriodosFacturadosOrgVentas();
			elemento.setOrgVentas(i);
			periodo.addOrgVentas(elemento);
		});
		if (agrupaciones != null) {
			agrupaciones.keySet().forEach(i -> {
				var elemento = new PeriodosFacturadosTipologias();
				elemento.setTipologia(i);
				var listaClaves = agrupaciones.get(i);
				listaClaves.forEach(c -> {
					var elementoClave = new PeriodosFacturadosTipologiasClaves();
					elementoClave.setClave(c);
					elemento.addClaves(elementoClave);
				});
				periodo.addTipologias(elemento);
			});
		}
		if (paramPool != null) {
			paramPool.getCodigosDelegacion().forEach(i -> {
				var elemento = new PeriodosFacturadosCodDelegacion();
				elemento.setCodDelegacion(i);
				periodo.addCodDelegacion(elemento);
			});
			paramPool.getCodigosPeticion().forEach(i -> {
				var elemento = new PeriodosFacturadosCodigosPeticion();
				elemento.setCodigoPeticion(i);
				periodo.addCodigoPeticion(elemento);
			});
			paramPool.getCompanias().forEach(i -> {
				var elemento = new PeriodosFacturadosCias();
				elemento.setCompania(i);
				periodo.addCias(elemento);
			});
			paramPool.getPruebas().forEach(i -> {
				var elemento = new PeriodosFacturadosPruebas();
				elemento.setPrueba(i);
				periodo.addPruebas(elemento);
			});
			paramPool.getRemitentes().forEach(i -> {
				var elemento = new PeriodosFacturadosRemitentes();
				elemento.setRemitente(i);
				periodo.addRemitentes(elemento);
			});
			paramPool.getSectores().forEach(i -> {
				var elemento = new PeriodosFacturadosSectores();
				elemento.setSector(i);
				periodo.addSectores(elemento);
			});
			paramPool.getTiposPeticion().forEach(i -> {
				var elemento = new PeriodosFacturadosTiposPeticion();
				elemento.setTipoPeticion(i);
				periodo.addTiposPeticion(elemento);
			});
		}
	}

	@Override
	public <T extends BasicMessagesEntity> void savePeriodo(PeriodosFacturados periodo, Set<T> messages) {
		createAndFlush(periodo);
	}

	@Override
	public void saveAndFlushPeriodo(PeriodosFacturados periodo) {
		createAndFlush(periodo);
	}

	@Override
	public void validarPlanificacion(PeriodosFacturados periodo) {
		try {
			var umbralLog = parametrosGeneralesRep.findByNombre(UMBRALLOGPLANIFICADO);
			var defLogGrande = parametrosGeneralesRep.findByNombre(DEFINICIONLOGGRANDE);
			long lngDefLogGrande = Long.valueOf(defLogGrande.getValor());
			long maxPositionsToProcess = Long.valueOf(umbralLog.getValor());
			long numLineasLog = getNumLineasLogFacturacion(periodo);
			periodo.setNumLineasPedido(numLineasLog);
			String instanceUUID = System.getenv(CF_INSTANCE_GUID);
			long numLineasEnProceso = getNumLineasEnProceso(instanceUUID);
			boolean procesablePorMem = isPedidoProcesablePorMemoria(numLineasLog);
			boolean procesablePorMemReservada = (numLineasLog + numLineasEnProceso < maxPositionsToProcess);
			if (!procesablePorMem || !procesablePorMemReservada || numLineasLog >= lngDefLogGrande) {
				periodo.getControl().setEstadoActual(ControlPeriodos.ESTADO_PLANIFICADO);
			} else {
				periodo.getControl().setEstadoActual(ControlPeriodos.ESTADO_COMENZADO);
			}
		} catch (Exception e) {
			log.error("Error al procesar planificación de periodo " + periodo + ". " + e, e);
		}
	}

	private long getNumLineasLogFacturacion(PeriodosFacturados periodo) {
		Long numLineasLog = null;
		if (periodo.getIdAgrupacion() != null) {
			var solAgr = solAgrSrv.findById(periodo.getIdAgrupacion()).get();
			numLineasLog = solAgr.getItems().stream().map(i -> i.getTrazabilidades()).flatMap(Collection::stream).count();
		} else {
			Set<Integer> tipologias = null;
			if (periodo.getTipologias().size() > 0) {
				tipologias = periodo.getTipologias().stream().map(PeriodosFacturadosTipologias::getTipologia).collect(Collectors.toSet());
			} else {
				Integer[] numerosArray = { 1, 2, 3, 4 };
				tipologias = new HashSet<>(Arrays.asList(numerosArray));
			}
			numLineasLog = tipologias.stream().mapToLong(t -> numLineasLogTipologia(t, periodo)).sum();
		}
		return numLineasLog;
	}

	@Override
	public boolean procesableMemReservada(Long idPeriodo, String instanceUUID) {
		try {
			var periodo = this.findById(idPeriodo).get();
			long numLineasLog = periodo.getNumLineasPedido() != null ? periodo.getNumLineasPedido() : getNumLineasLogFacturacion(periodo);
			long numLineasEnProceso = getNumLineasEnProceso(instanceUUID);
			var param = parametrosGeneralesRep.findByNombre(UMBRALLOGPLANIFICADO);
			long maxPositionsToProcess = Long.valueOf(param.getValor());
			return numLineasLog + numLineasEnProceso < maxPositionsToProcess;
		} catch (Exception e) {
			log.error("Error al obtener procesableMemReservada de periodo " + idPeriodo + ". " + e, e);
			return false;
		}
	}

	private long numLineasLogTipologia(Integer tipologia, PeriodosFacturados periodo) {
		var optTipologia = periodo.getTipologias().stream().filter(t -> t.getTipologia().equals(tipologia)).findAny();
		String clavesTipologia = "";
		if (optTipologia.isPresent()) {
			clavesTipologia = formatList(optTipologia.get().getClaves());
		}
		String tablaActividad = getTablaActividad(tipologia);
		String campoClaveTipologia = getCampoClaveTipologia(tipologia);
		SimpleDateFormat sdfIni = null;
		SimpleDateFormat sdfFin = null;
		var testVariable = System.getProperty("test.environment");
		if ("true".equals(testVariable)) {
			sdfIni = new SimpleDateFormat("yyyy-MM-dd");
			sdfFin = new SimpleDateFormat("yyyy-MM-dd");
		} else {
			sdfIni = new SimpleDateFormat("yyyy/MM/dd 00:00:00.000");
			sdfFin = new SimpleDateFormat("yyyy/MM/dd 23:59:59.000");
		}
		Date dtmFechaIni = periodo.getFechaInicioFacturacion().getTime();
		String strFechaIni = sdfIni.format(dtmFechaIni);
		Date dtmFechaFin = periodo.getFechaFinFacturacion().getTime();
		String strFechaFin = sdfFin.format(dtmFechaFin);
		String listaClientes = formatList(periodo.getClientes());
		String listaOrgVentas = formatList(periodo.getOrganizacionesVentas());
		String listaOfiVentas = formatList(periodo.getCodigosDelegacion());
		String listaSectores = formatList(periodo.getSectores());
		String listaTipoPeticion = formatList(periodo.getTiposPeticion());
		String listaCodsPeticion = formatList(periodo.getCodigosPeticion());
		String listaCompanias = formatList(periodo.getCompanias());
		String listaRemitentes = formatList(periodo.getRemitentes());
		String listaPruebas = formatList(periodo.getPruebas());
		String sql = "SELECT COUNT(*) FROM " + tablaActividad + " WHERE FECHAFACTURACION >='" + strFechaIni + "' AND FECHAFACTURACION <='" + strFechaFin
				+ "' AND FECHAPETICION >='" + strFechaIni + "' AND FECHAPETICION <='" + strFechaFin + "' " + " AND CODIGOORGANIZACION IN " + listaOrgVentas;
		if (!listaClientes.contains("*") && !listaClientes.isEmpty()) {
			sql += " AND CODIGOCLIENTE IN " + listaClientes + " ";
		}
		if (!listaOfiVentas.contains("*") && !listaOfiVentas.isEmpty()) {
			sql += " AND OFICINAVENTAS IN " + listaOfiVentas + " ";
		}
		if (!listaSectores.contains("*") && !listaSectores.isEmpty()) {
			sql += " AND SECTORVENTAS IN " + listaSectores + " ";
		}
		if (!clavesTipologia.isEmpty()) {
			sql += " AND " + campoClaveTipologia + " IN " + clavesTipologia + " ";
		}
		if (!listaTipoPeticion.contains("0") && !listaTipoPeticion.isEmpty()) {
			sql += " AND TIPOPETICION IN " + listaTipoPeticion + " ";
		}
		if (!listaCodsPeticion.contains("*") && !listaCodsPeticion.isEmpty()) {
			sql += " AND CODIGOPETICION IN " + listaCodsPeticion + " ";
		}
		if (!listaCompanias.contains("*") && !listaCompanias.isEmpty()) {
			sql += " AND INTERLOCUTORCOMPANIA IN " + listaCompanias + " ";
		}
		if (!listaRemitentes.contains("*") && !listaRemitentes.isEmpty()) {
			sql += " AND INTERLOCUTORREMITENTE IN " + listaRemitentes + " ";
		}
		if (!listaPruebas.contains("*") && !listaPruebas.isEmpty()) {
			sql += " AND CODIGOPRUEBA IN " + listaPruebas + " ";
		}
		log.info("Se va a lanzar la consulta " + sql);
		BigInteger responseBint = (BigInteger) entityManager.createNativeQuery(sql).getSingleResult();
		long lngResponse = responseBint == null ? 0 : responseBint.longValue();
		log.info("El total de lineas en proceso es " + lngResponse + " para el log " + periodo + " y tipologia " + tipologia);
		return lngResponse;

	}

	private long getNumLineasEnProceso(String instanceUUID) {

		String sql = "SELECT SUM (NUMLINEASPEDIDO) FROM T_FACTLOGPERIODOS p INNER JOIN T_FACTLOGCONTROL c "
				+ " ON c.FK_PERIODOFACTURADO = p.ID WHERE UUIDINSTANCE='" + instanceUUID + "' AND c.ESTADOACTUAL=0 ";
		log.info("Se va a lanzar la consulta " + sql);
		BigInteger responseBint = (BigInteger) entityManager.createNativeQuery(sql).getSingleResult();
		Long lngResponse = responseBint == null ? 0 : responseBint.longValue();
		log.info("El total de lineas en proceso es " + lngResponse + " para el uuidInstance " + instanceUUID);
		return lngResponse;
	}

	private String getCampoClaveTipologia(Integer tipologia) {
		switch (tipologia) {
		case 1:
			return "DIVISORKEY";
		case 2:
		case 3:
		case 4:
			return "NUMEROCONTRATO";
		}
		return "";
	}

	private String getTablaActividad(Integer tipologia) {
		switch (tipologia) {
		case 1:
			return "V_AGRACTOMEDICO";
		case 2:
			return "V_AGRCAPITATIVOVARIABLE";
		case 3:
			return "V_AGRCAPITATIVOFIJO";
		case 4:
			return "V_AGRFIJOPORPETICION";
		}
		return "";
	}

	private String formatList(Set<?> elements) {
		Set<String> strElements = new HashSet<>();
		elements.forEach(e -> {
			String strElement = "'" + e + "'";
			strElements.add(strElement);
		});
		return strElements.isEmpty() ? "" : "(" + String.join(",", strElements) + ")";
	}

	private boolean isPedidoProcesablePorMemoria(Long numLineasPedido) {
		if (numLineasPedido != null) {
			var memoriaEstimadaAUsar = numLineasPedido * MEMORY_BY_POSITION;
			var availableMem = getAvailableMem();
			if (log.isTraceEnabled()) {
				log.trace("La memoria estimada a usar es " + memoriaEstimadaAUsar + ". La memoria disponible es " + availableMem);
			}
			return availableMem >= memoriaEstimadaAUsar;
		} else {
			return true;
		}
	}

	private long getUsedMem() {
		MemoryMXBean memBean = ManagementFactory.getMemoryMXBean();
		MemoryUsage heap = memBean.getHeapMemoryUsage();
		MemoryUsage nonheap = memBean.getNonHeapMemoryUsage();
		long totmemUsed = (heap == null ? 0 : heap.getUsed()) + (nonheap == null ? 0 : nonheap.getUsed());
		return totmemUsed;
	}

	private long getMaxMem() {
		MemoryMXBean memBean = ManagementFactory.getMemoryMXBean();
		MemoryUsage heap = memBean.getHeapMemoryUsage();
		MemoryUsage nonheap = memBean.getNonHeapMemoryUsage();
		long totmemUsed = (heap == null ? 0 : heap.getMax()) + (nonheap == null ? 0 : nonheap.getMax());
		return totmemUsed;
	}

	private long getAvailableMem() {
		return getMaxMem() - getUsedMem();
	}

	@Override
	public Map<Integer, Set<String>> getAgrupacionesPeriodo(PeriodosFacturados periodo) {
		Map<Integer, Set<String>> response = new HashMap<>();
		for (var tipologia : periodo.getTipologias()) {
			var claves = tipologia.getClaves().stream().map(PeriodosFacturadosTipologiasClaves::getClave).collect(Collectors.toSet());
			response.put(tipologia.getTipologia(), claves);
		}
		return response;
	}

	@Override
	public ParamPoolFacturacion getParamPoolPeriodo(PeriodosFacturados periodo) {
		ParamPoolFacturacion response = new ParamPoolFacturacion();
		response.setClientes(
				periodo.getClientes().stream().map(PeriodosFacturadosClientes::getCodigoCliente).filter(c -> !c.equals(ANY)).collect(Collectors.toSet()));
		response.setCodigosDelegacion(
				periodo.getCodigosDelegacion().stream().map(PeriodosFacturadosCodDelegacion::getCodDelegacion).collect(Collectors.toSet()));
		response.setCodigosPeticion(
				periodo.getCodigosPeticion().stream().map(PeriodosFacturadosCodigosPeticion::getCodigoPeticion).collect(Collectors.toSet()));
		response.setCompanias(periodo.getCompanias().stream().map(PeriodosFacturadosCias::getCompania).collect(Collectors.toSet()));
		response.setFechaIniFacturacion(periodo.getFechaInicioFacturacion().getTime());
		response.setFechaFinFacturacion(periodo.getFechaFinFacturacion().getTime());
		response.setOrganizacionesVentas(periodo.getOrganizacionesVentas().stream().map(PeriodosFacturadosOrgVentas::getOrgVentas).collect(Collectors.toSet()));
		response.setPruebas(periodo.getPruebas().stream().map(PeriodosFacturadosPruebas::getPrueba).collect(Collectors.toSet()));
		response.setRemitentes(periodo.getRemitentes().stream().map(PeriodosFacturadosRemitentes::getRemitente).collect(Collectors.toSet()));
		response.setSectores(periodo.getSectores().stream().map(PeriodosFacturadosSectores::getSector).collect(Collectors.toSet()));
		response.setTiposPeticion(periodo.getTiposPeticion().stream().map(PeriodosFacturadosTiposPeticion::getTipoPeticion).collect(Collectors.toSet()));
		return response;
	}

	@Override
	public void registrarLogPlanificadoBatch(Long idLog, String codUsuario, String nombreUsuario) throws ParseException {
		try {
			String urlGet = "api/triggers";
			String urlPost = "/api/scheduleCronJob";
			String urlPut = "/api/rescheduleJob";
			RestTemplate restTemplate = createRestTemplate(BATCH);
			ParameterizedTypeReference<Object> typeRef = new ParameterizedTypeReference<Object>() {
			};
			HttpHeaders headers = new HttpHeaders();
			headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
			headers.set(HttpHeaders.ACCEPT_LANGUAGE, ConstFacturacion.IDIOMA_DEFAULT.toLowerCase());
			HttpEntity<String> entity = new HttpEntity<>(StringUtils.EMPTY, headers);
			ArrayList<?> responseGet = (ArrayList<?>) restTemplate.exchange(urlGet, HttpMethod.GET, entity, typeRef).getBody();
			List<Date> registeredDates = new ArrayList<>();
			HashMap<Date, String> hmTriggerReschedule = new HashMap<>();
			HashMap<String, Long> hmNumLineasTrigger = new HashMap<>();
			var perFact = findById(idLog).get();
			var numLineasPerFact = perFact.getNumLineasPedido();
			Date registerDate = null;
			for (Object r : responseGet) {
				LinkedHashMap<?, ?> lhm = (LinkedHashMap<?, ?>) r;
				var jobName = (String) lhm.get("jobName");
				if (jobName.equals(LANZAR_LOG_PLANIFICADO_JOB)) {
					var nextFireTime = (String) lhm.get("nextFireTime");
					Date stringDateToDate = nextFireTime.isEmpty() ? new Date() : DateUtils.stringDateToDate(nextFireTime);
					registeredDates.add(stringDateToDate);
					var jobDataMap = (LinkedHashMap<?, ?>) lhm.get("jobDataMap");
					var idLogPl = Long.valueOf((String) jobDataMap.get("idLog"));
					var perFactPl = findById(idLogPl).get();
					var numLineasPerFactPl = perFactPl.getNumLineasPedido();
					var triggerName = (String) lhm.get("triggerName");
					if (numLineasPerFact < numLineasPerFactPl) {
						// para reagendar
						log.info("Se tiene que reagendar el trigger " + triggerName + ". numLineasPerFact=" + numLineasPerFact + " numLineasPerFactPl="
								+ numLineasPerFactPl);
						hmTriggerReschedule.put(stringDateToDate, triggerName);
						hmNumLineasTrigger.put(triggerName, numLineasPerFactPl);
					}
				}
			}
			// Definir fecha de registro
			var paramHoraLog = parametrosGeneralesRep.findByNombre(HORALOGPLANIFICADO);
			var horaIniLog = Integer.valueOf(paramHoraLog.getValor().substring(0, 2));
			if (!hmTriggerReschedule.isEmpty()) {
				var dates = hmTriggerReschedule.keySet();
				var datesList = new ArrayList<>(dates);
				Collections.sort(datesList);
				registerDate = datesList.get(0);
				Collections.sort(datesList, Collections.reverseOrder());
				moveTriggers(horaIniLog, datesList, hmTriggerReschedule, hmNumLineasTrigger, restTemplate, urlPut, typeRef);
			} else {
				Collections.sort(registeredDates, Collections.reverseOrder());
				if (registeredDates.size() > 0) {
					Date lastDate = registeredDates.get(0);
					registerDate = getNextDate(lastDate, numLineasPerFact, horaIniLog);
				} else {
					registerDate = getFirstTodayDateAssignment();
				}
			}
			// Registrar schedule
			log.info("Se va a agendar el log=" + idLog + " con fecha " + registerDate);
			var cron = DateUtils.dateToCron(registerDate);
			String strSchedule = buildSchedule(cron, idLog, codUsuario, nombreUsuario);
			HttpHeaders headersPost = new HttpHeaders();
			headersPost.setContentType(MediaType.APPLICATION_JSON);
			headersPost.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
			headersPost.set(HttpHeaders.ACCEPT_LANGUAGE, ConstFacturacion.IDIOMA_DEFAULT.toLowerCase());
			HttpEntity<String> entitySchedule = new HttpEntity<>(strSchedule, headersPost);
			restTemplate.exchange(urlPost, HttpMethod.POST, entitySchedule, typeRef);
		} catch (Exception e) {
			log.error("Error en registrarLogPlanificadoBatch: " + e, e);
			throw e;
		}
	}

	private void moveTriggers(int horaInicioPf, ArrayList<Date> datesList, HashMap<Date, String> hmTriggerReschedule, HashMap<String, Long> hmNumLineasTrigger,
			RestTemplate restTemplate, String url, ParameterizedTypeReference<Object> typeRef) {
		for (var dateTrigger : datesList) {
			var triggerName = hmTriggerReschedule.get(dateTrigger);
			var numLineasTrigger = hmNumLineasTrigger.get(triggerName);
			moveTrigger(triggerName, numLineasTrigger, dateTrigger, horaInicioPf, restTemplate, url, typeRef);
		}
	}

	private void moveTrigger(String triggerPl, long numLineasTrigger, Date lastDate, int horaInicioPf, RestTemplate restTemplate, String url,
			ParameterizedTypeReference<Object> typeRef) {
		var nextDate = getNextDate(lastDate, numLineasTrigger, horaInicioPf);
		var cron = DateUtils.dateToCron(nextDate);
		String strSchedule = buildReschedule(triggerPl, cron);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.set(HttpHeaders.ACCEPT_LANGUAGE, ConstFacturacion.IDIOMA_DEFAULT.toLowerCase());
		HttpEntity<String> entitySchedule = new HttpEntity<>(strSchedule, headers);
		restTemplate.exchange(url, HttpMethod.PUT, entitySchedule, typeRef);
	}

	private Date getNextDate(Date lastDate, long numLineasLog, int horaInicioPf) {
		Calendar calendarFinLog = getCalendarFinLog(lastDate);
		var paramMinLog = parametrosGeneralesRep.findByNombre(MINUTOSENTRELOGSPF);
		var minutosEntreLogs = Integer.valueOf(paramMinLog.getValor());
		Date registerDate = null;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(lastDate);
		calendar.add(Calendar.MINUTE, minutosEntreLogs);
		log.info("date=" + calendar.getTime() + " calendarFinLog=" + calendarFinLog.getTime());
		if (calendar.compareTo(calendarFinLog) >= 0) {
			registerDate = getFirstTomorrowDateAssignment(calendarFinLog);
		} else {
			Calendar fechaPrevistaFinLog = getFechaPrevistaFinLog(calendar.getTime(), numLineasLog);
			log.info("fechaPrevistaFinLog=" + fechaPrevistaFinLog.getTime() + " calendarFinLog=" + calendarFinLog.getTime());
			if (fechaPrevistaFinLog.compareTo(calendarFinLog) > 0) {
				registerDate = getFirstTomorrowDateAssignment(calendarFinLog);
			} else {
				registerDate = calendar.getTime();
			}
		}
		return registerDate;
	}

	private Calendar getCalendarFinLog(Date lastDate) {
		var paramHoraLog = parametrosGeneralesRep.findByNombre(HORAFINLOGPLANIFICADO);
		var horaFinLog = Integer.valueOf(paramHoraLog.getValor().substring(0, 2));
		var minutoFinLog = Integer.valueOf(paramHoraLog.getValor().substring(2));
		Calendar calendarFinLog = Calendar.getInstance();
		calendarFinLog.set(Calendar.HOUR_OF_DAY, horaFinLog);
		calendarFinLog.set(Calendar.MINUTE, minutoFinLog);
		Calendar calendarLastDate = Calendar.getInstance();
		calendarLastDate.setTime(lastDate);
		var dayLastDate = calendarLastDate.get(Calendar.DAY_OF_YEAR);
		var hourLastDate = calendarLastDate.get(Calendar.HOUR_OF_DAY);
		if (hourLastDate <= horaFinLog) {
			calendarFinLog.set(Calendar.DAY_OF_YEAR, dayLastDate);
		} else {
			calendarFinLog.set(Calendar.DAY_OF_YEAR, dayLastDate + 1);
		}
		return calendarFinLog;
	}

	private Calendar getFechaPrevistaFinLog(Date fechaInicio, long numLineasLog) {
		var paramMsLog = parametrosGeneralesRep.findByNombre(MSPROCESOLOGPRUEBA);
		var msLog = Integer.valueOf(paramMsLog.getValor());
		long totalMsLog = numLineasLog * msLog;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fechaInicio);
		calendar.setTimeInMillis(calendar.getTimeInMillis() + totalMsLog);
		return calendar;
	}

	private Date getFirstTodayDateAssignment() {
		var paramHoraLog = parametrosGeneralesRep.findByNombre(HORALOGPLANIFICADO);
		var horaLog = Integer.valueOf(paramHoraLog.getValor().substring(0, 2));
		var minutoLog = Integer.valueOf(paramHoraLog.getValor().substring(2));
		Calendar calendar = Calendar.getInstance();
		int hourDiff = getHourDiff(calendar);
		calendar.set(Calendar.HOUR_OF_DAY, horaLog - hourDiff);
		calendar.set(Calendar.MINUTE, minutoLog);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}

	private int getHourDiff(Calendar calendar) {
		int hourSrv = calendar.get(Calendar.HOUR_OF_DAY);
		Date toDate = calendar.getTime();
		Instant instant = toDate.toInstant();
		ZonedDateTime zonedDateTime = instant.atZone(ZoneId.of("Europe/Madrid"));
		int hourClient = zonedDateTime.getHour();
		int diffHour = hourClient - hourSrv;
		return diffHour;
	}

	private Date getFirstTomorrowDateAssignment(Calendar calendarFinLog) {
		var paramHoraLog = parametrosGeneralesRep.findByNombre(HORALOGPLANIFICADO);
		var horaLog = Integer.valueOf(paramHoraLog.getValor().substring(0, 2));
		var minutoLog = Integer.valueOf(paramHoraLog.getValor().substring(2));
		Calendar calendar = Calendar.getInstance();
		int hourDiff = getHourDiff(calendar);
		int dayFinLog = calendarFinLog.get(Calendar.DAY_OF_YEAR);
		calendar.set(Calendar.DAY_OF_YEAR, dayFinLog);
		calendar.set(Calendar.HOUR_OF_DAY, horaLog - hourDiff);
		calendar.set(Calendar.MINUTE, minutoLog);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}

	private RestTemplate createRestTemplate(Destinations.Enum destination) {
		final Destinations destinations = ContextProvider.getBean(Destinations.class);
		return destinations.createRestTemplate(destination);
	}

	private String buildSchedule(String cron, Long idLog, String codUsuario, String nombreUsuario) {
		JSONObject json = new JSONObject();
		json.put("jobName", LANZAR_LOG_PLANIFICADO_JOB);
		json.put("cronExpression", cron);
		json.put("jsonParams", buildParamsSchedule(idLog, codUsuario, nombreUsuario));
		return json.toString();
	}

	private String buildParamsSchedule(Long idLog, String codUsuario, String nombreUsuario) {
		JSONObject json = new JSONObject();
		json.put("idLog", idLog);
		json.put("codUsuario", codUsuario);
		json.put("nombreUsuario", nombreUsuario);
		return json.toString();
	}

	private String buildReschedule(String triggerPl, String cron) {
		JSONObject json = new JSONObject();
		json.put("triggerName", triggerPl);
		json.put("cronExpression", cron);
		return json.toString();
	}
	
}
