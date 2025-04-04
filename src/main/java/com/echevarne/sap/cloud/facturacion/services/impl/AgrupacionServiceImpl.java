package com.echevarne.sap.cloud.facturacion.services.impl;

import static com.echevarne.sap.cloud.facturacion.constants.ConstFacturacion.CONDICION_PRECIO_ADICIONAL_FIJO;
import static com.echevarne.sap.cloud.facturacion.constants.ConstFacturacion.CONDICION_PRECIO_VALOR_PERFIL;
import static com.echevarne.sap.cloud.facturacion.constants.ConstFacturacion.ROL_INTERLOCUTOR_EMPRESA;
import static com.echevarne.sap.cloud.facturacion.constants.ConstFacturacion.ROL_INTERLOCUTOR_PACIENTE;
import static com.echevarne.sap.cloud.facturacion.constants.ConstFacturacion.ROL_INTERLOCUTOR_PROFESIONAL;
import static com.echevarne.sap.cloud.facturacion.constants.ConstFacturacion.ROL_INTERLOCUTOR_VISITADOR;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.sleuth.annotation.NewSpan;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.echevarne.sap.cloud.facturacion.constants.ConstFacturacion;
import com.echevarne.sap.cloud.facturacion.gestionestados.Bloqueada;
import com.echevarne.sap.cloud.facturacion.gestionestados.Borrada;
import com.echevarne.sap.cloud.facturacion.gestionestados.Erronea;
import com.echevarne.sap.cloud.facturacion.gestionestados.Excluida;
import com.echevarne.sap.cloud.facturacion.gestionestados.Facturable;
import com.echevarne.sap.cloud.facturacion.model.BasicMessagesEntity;
import com.echevarne.sap.cloud.facturacion.model.privados.DatosPagador;
import com.echevarne.sap.cloud.facturacion.model.solicitudagrupada.GroupedSolIndItems;
import com.echevarne.sap.cloud.facturacion.model.solicitudagrupada.SolAgrItemPartner;
import com.echevarne.sap.cloud.facturacion.model.solicitudagrupada.SolAgrItemPricing;
import com.echevarne.sap.cloud.facturacion.model.solicitudagrupada.SolAgrItems;
import com.echevarne.sap.cloud.facturacion.model.solicitudagrupada.SolAgrItemsKey;
import com.echevarne.sap.cloud.facturacion.model.solicitudagrupada.SolAgrPartner;
import com.echevarne.sap.cloud.facturacion.model.solicitudagrupada.SolAgrPricing;
import com.echevarne.sap.cloud.facturacion.model.solicitudagrupada.SolicitudesAgrupadas;
import com.echevarne.sap.cloud.facturacion.model.solicitudindividual.SolIndItemPricing;
import com.echevarne.sap.cloud.facturacion.model.solicitudindividual.SolIndItems;
import com.echevarne.sap.cloud.facturacion.model.solicitudindividual.SolIndPartner;
import com.echevarne.sap.cloud.facturacion.model.solicitudindividual.SolIndPricing;
import com.echevarne.sap.cloud.facturacion.model.solicitudindividual.SolicitudIndividual;
import com.echevarne.sap.cloud.facturacion.model.trazabilidad.Trazabilidad;
import com.echevarne.sap.cloud.facturacion.model.trazabilidad.TrazabilidadSolAgrItems;
import com.echevarne.sap.cloud.facturacion.model.trazabilidad.TrazabilidadSolicitud;
import com.echevarne.sap.cloud.facturacion.model.trazabilidad.TrazabilidadSolicitudAgrupada;
import com.echevarne.sap.cloud.facturacion.model.trazabilidad.TrazabilidadSolicitudAgrupado;
import com.echevarne.sap.cloud.facturacion.repositories.SolAgrItemPricingRep;
import com.echevarne.sap.cloud.facturacion.repositories.SolicitudesAgrupadasRep;
import com.echevarne.sap.cloud.facturacion.services.AgrupacionService;
import com.echevarne.sap.cloud.facturacion.services.AgrupadorItemsService;
import com.echevarne.sap.cloud.facturacion.services.ProcesadorEstadosService;
import com.echevarne.sap.cloud.facturacion.services.SolAgrItemsService;
import com.echevarne.sap.cloud.facturacion.services.SolIndItemsService;
import com.echevarne.sap.cloud.facturacion.services.SolicitudesAgrupadasService;
import com.echevarne.sap.cloud.facturacion.services.TrazabilidadSolicitudService;
import com.echevarne.sap.cloud.facturacion.util.Loggable;
import com.echevarne.sap.cloud.facturacion.util.MessagesUtils;
import com.echevarne.sap.cloud.facturacion.util.NewSpan2;
import com.google.common.collect.Lists;
import lombok.var;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service("agrupacionService")
public class AgrupacionServiceImpl extends CrudServiceImpl<SolicitudesAgrupadas, Long> implements AgrupacionService {

	private final static Set<String> ESTADOS_NO_FACTURABLES = new HashSet<>(4);

	static{
		ESTADOS_NO_FACTURABLES.add(Excluida.CODIGO);
		ESTADOS_NO_FACTURABLES.add(Bloqueada.CODIGO);
		ESTADOS_NO_FACTURABLES.add(Erronea.CODIGO);
		ESTADOS_NO_FACTURABLES.add(Borrada.CODIGO);
	}
	
	private final static int PARTITION_SIZE = 16384;

	private final SolAgrItemsService solAgrItemsService;

	private final TrazabilidadSolicitudService trazabilidadSolicitudSrv;

	private final ProcesadorEstadosService procesadorEstadosService;
	
	private final SolIndItemsService solIndItemsSrv;
	
	private EntityManager entityManager;	
	
	@Autowired
	private AgrupadorItemsService agrupadorItemsSrv;
	
	@Autowired
	private SolAgrItemsService solAgrItemsSrv;
	
	@Autowired
	private SolicitudesAgrupadasService solAgrSrv;
	
	@Autowired
	private SolAgrItemPricingRep solAgrItemPricingRep;
	
	@Autowired
	public final void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	@Autowired
	public AgrupacionServiceImpl(
			final SolicitudesAgrupadasRep solicitudesAgrupadasRep,
			final SolAgrItemsService solAgrItemsService,
			final TrazabilidadSolicitudService trazabilidadSolicitudSrv,
			final ProcesadorEstadosService procesadorEstadosService,
			final SolIndItemsService solIndItemsSrv
	) {
		super(solicitudesAgrupadasRep);
		this.solAgrItemsService = solAgrItemsService;
		this.trazabilidadSolicitudSrv = trazabilidadSolicitudSrv;
		this.procesadorEstadosService = procesadorEstadosService;
		this.solIndItemsSrv = solIndItemsSrv;
	}

	@Override
	@NewSpan
	public List<SolicitudesAgrupadas> findAll() {
		return getCrudRepository().findAll();
	}

	@Override
	@Transactional
	@NewSpan
	public SolicitudesAgrupadas buildIndividual(SolicitudIndividual si, boolean esPrivados) {
		SolicitudesAgrupadas solicitudAgrupada = new SolicitudesAgrupadas();
		solicitudAgrupada.setSalesOrderType(esPrivados ? this.getSalesOrderTypePrivados(si) : ConstFacturacion.TIPO_ORDEN_VENTAS_AGRUPADO);
		solicitudAgrupada.setAssignmentReference(si.getPurchaseOrderByCustomer());
		solicitudAgrupada.setPurchaseOrderByCustomer(si.getPurchaseOrderByCustomer());
		solicitudAgrupada.setSalesOrganization(si.getSalesOrganization());
		solicitudAgrupada.setDistributionChannel(si.getDistributionChannel());
		solicitudAgrupada.setOrganizationDivision(si.getOrganizationDivision());
		solicitudAgrupada.setSoldToParty(si.getSoldToParty());
		solicitudAgrupada.setPricingAgrDate(new Date());
		solicitudAgrupada.setCargoPeticion(si.getTrazabilidad().getPeticionRec().getCargoPeticion());
		solicitudAgrupada.setFechaPeticion(si.getSalesOrderDate());
		solicitudAgrupada.setTipoPeticion(si.getTipoPeticion());
		var solicitudAgrupadaSaved = solAgrSrv.create(solicitudAgrupada);
		try {
		agruparItems(solicitudAgrupadaSaved, si, esPrivados);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		setTrazabilidad(solicitudAgrupadaSaved);

		return solicitudAgrupadaSaved;
	}

	private void setTrazabilidad(SolicitudesAgrupadas solicitudesAgrupadas) {
		// Cabecera
		if (solicitudesAgrupadas.getTrazabilidadSolicitudAgrupada() == null) {
			TrazabilidadSolicitudAgrupada trazaSol = new TrazabilidadSolicitudAgrupada();
			trazaSol.setSolicitudesAgrupadas(solicitudesAgrupadas);
			solicitudesAgrupadas.setTrazabilidadSolicitudAgrupada(trazaSol);
		}
		// Posiciones
		solicitudesAgrupadas.getItems().forEach(prueba -> {
			if (prueba.getTrazabilidad() == null) {
				TrazabilidadSolAgrItems itemTrazabilidad = new TrazabilidadSolAgrItems();
				itemTrazabilidad.setSolAgrItems(prueba);
				prueba.setTrazabilidad(itemTrazabilidad);
			}
		});
	}

	@Override
	@Transactional
	@NewSpan
	public SolicitudesAgrupadas buildMultiple(List<SolicitudIndividual> siList, boolean esPrivados) {
		SolicitudesAgrupadas solicitudAgrupada = new SolicitudesAgrupadas();

		SolicitudIndividual si = siList.get(0);

		solicitudAgrupada.setSalesOrderType(esPrivados ? this.getSalesOrderTypePrivados(si) : ConstFacturacion.TIPO_ORDEN_VENTAS_AGRUPADO);
		solicitudAgrupada.setAssignmentReference(si.getPurchaseOrderByCustomer());
		solicitudAgrupada.setPurchaseOrderByCustomer(getCodigoPeticionMultiple(siList));
		solicitudAgrupada.setSalesOrganization(si.getSalesOrganization());
		solicitudAgrupada.setDistributionChannel(si.getDistributionChannel());
		solicitudAgrupada.setOrganizationDivision(si.getOrganizationDivision());
		solicitudAgrupada.setSoldToParty(si.getSoldToParty());
		solicitudAgrupada.setPricingAgrDate(new Date());
		solicitudAgrupada.setCargoPeticion(si.getTrazabilidad().getPeticionRec().getCargoPeticion());
		solicitudAgrupada.setFechaPeticion(si.getSalesOrderDate());
		solicitudAgrupada.setTipoPeticion(si.getTipoPeticion());

		this.create(solicitudAgrupada);


		List<SolIndItems> items = siList.stream().flatMap(solInd -> solInd.getItems().stream())
				.collect(Collectors.toList());
		try {
			agruparItems(solicitudAgrupada, items, true);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		setTrazabilidad(solicitudAgrupada);
		return solicitudAgrupada;
	}

	private String getCodigoPeticionMultiple(List<SolicitudIndividual> siList) {
		String response = "";
		if (siList.size() > 0) {
			response = siList.get(0).getPurchaseOrderByCustomer() + " Y otras";
		}
		return response;
	}

	@Override
	@Transactional
	@NewSpan
	@Loggable
	public void agruparItems(SolicitudesAgrupadas sa, List<SolIndItems> items, boolean esPrivados) throws Exception {
		if (items == null || items.size() == 0) {
			return;
		}

		compactarItems(sa, items);
	}
	
	@Override
	@Transactional
	@NewSpan
	@Loggable
	public void agruparIdItems(SolicitudesAgrupadas sa, List<Long> items, boolean esPrivados) throws Exception {
		if (items == null || items.size() == 0) {
			return;
		}

		compactarIdItems(sa, items);
	}
	
	@Override
	@Transactional
	@NewSpan
	@Loggable
	public void agruparIdItemsNoCompact(Long idSolAgr, List<Long> items) throws Exception {
		var optSolAgr = solAgrSrv.findById(idSolAgr);
		if (!optSolAgr.isPresent()) {
			var msgErr = "No se ha encontrado la agrupación con id " + idSolAgr;
			log.error(msgErr);
			throw new Exception(msgErr);
		}
		var solicitudAgrupada = optSolAgr.get();
		List<SolAgrItems>solItemAgrs = new ArrayList<>();
		int itemCount = 1;
		Map<String, Integer> nuevasPosiciones = new HashMap<>();
		for (Long idSolIndItem : items) {
			var optSolIndItem = solIndItemsSrv.findById(idSolIndItem);
			if (!optSolIndItem.isPresent()) {
				var msgErr = "No se ha encontrado el idSolIndItem " + idSolIndItem + " para agregar a la agrupación con id " + idSolAgr;
				log.error(msgErr);
				throw new Exception(msgErr);
			}
			var solIndItem = optSolIndItem.get();
			if (esItemFacturable(solIndItem)) {
				SolAgrItems solAgrItem = processToSolAgrItemNoCompact(solicitudAgrupada, solIndItem, itemCount, nuevasPosiciones);
				solItemAgrs.add(solAgrItem);
				itemCount++;
			}
		}
		alterConditionPricesNoCompact(solItemAgrs);
		addPrecioZNETNoCompact(solItemAgrs);
	}
	
	private void alterConditionPricesNoCompact(Collection<SolAgrItems> solItemAgrs) {
		for (SolAgrItems saItem : solItemAgrs) {
			var optPriceZVPE = saItem.getPrices().stream().filter(p -> p.getConditionType().equals(CONDICION_PRECIO_VALOR_PERFIL)).findAny();
			if (optPriceZVPE.isPresent()) {
				optPriceZVPE.get().setConditionRateValue(optPriceZVPE.get().getConditionAmount());
			}
		}
	}
	
	private void addPrecioZNETNoCompact(Collection<SolAgrItems> solItemAgrs) {
		for (SolAgrItems saItem : solItemAgrs) {
			var optZpr1 = saItem.getPrices().stream().filter(p -> p.getConditionType().equals(ConstFacturacion.CONDICION_PRECIO_NETO)).findAny();
			if (optZpr1.isPresent()) {
				SolAgrItemPricing prZNet = (SolAgrItemPricing) optZpr1.get().copyWithoutId();
				prZNet.setConditionType(ConstFacturacion.CONDICION_PRECIO_MODIFICADO);
				saItem.getPrices().add(prZNet);
				prZNet.setPosicion(saItem);
				solAgrItemPricingRep.save(prZNet);
			}
		}
		
	}
	
	private SolAgrItems processToSolAgrItemNoCompact(SolicitudesAgrupadas solicitudAgrupada, SolIndItems solIndItem, int itemCount,
			Map<String, Integer> nuevasPosiciones) {
		try {
			Trazabilidad trazabilidad = solIndItem.getTrazabilidad();
			SolicitudIndividual si = solIndItem.getSolicitudInd();
			// En este punto, todas las solicitudes individuales de la agrupación son del
			// mismo sector
			if (solicitudAgrupada.getOrganizationDivision() == null) {
				solicitudAgrupada.setOrganizationDivision(si.getOrganizationDivision());
			}
			// Informar transaction currency
			if (solicitudAgrupada.getTransactionCurrency() == null) {
				solicitudAgrupada.setTransactionCurrency(si.getTransactionCurrency());
			}
			// El pricing date de la agrupación es la fecha de petición más reciente
			setPricingDate(solicitudAgrupada, solIndItem);
			if (solicitudAgrupada.getCargoPeticion() == null) {
				solicitudAgrupada.setCargoPeticion(si.getTrazabilidad().getPeticionRec().getCargoPeticion());
			}
			TrazabilidadSolicitud ts = si.getTrazabilidad();

			SolAgrItems solAgrItems = create(solIndItem, solicitudAgrupada);

			solAgrItems.addRequestedQuantity(solIndItem.getRequestedQuantity());
			solAgrItems.addNetAmount(solIndItem.getNetAmount());
			solAgrItems.addTaxAmount(solIndItem.getTaxAmount());
			solAgrItems.addPointAmount(solIndItem.getPointAmount());

			// Trazabilidad
			trazabilidad.setItemAgr(solAgrItems);

			boolean isAlreadyCreated = solicitudAgrupada.getTrazabilidad().stream()
					.anyMatch((trazabilidadSolicitudAgrupado -> Objects.equals(ts, trazabilidadSolicitudAgrupado.getTrazabilidad())));

			if (!isAlreadyCreated) {
				TrazabilidadSolicitudAgrupado trzAgr = new TrazabilidadSolicitudAgrupado(solicitudAgrupada, ts);
				solicitudAgrupada.addTrazabilidad(trzAgr);
			}

			// Mantenemos jerarquia
			int idAgrItem = itemCount;
			String position = String.valueOf(si.getPurchaseOrderByCustomer()) + String.valueOf(solIndItem.getSalesOrderIndItem());
			nuevasPosiciones.put(position, idAgrItem);
			if (solIndItem.getHigherLevelltem() != 0) {
				String parent = String.valueOf(si.getPurchaseOrderByCustomer()) + String.valueOf(solIndItem.getHigherLevelltem());
				if (nuevasPosiciones.containsKey(parent))
					solAgrItems.setHigherLevelltem(nuevasPosiciones.get(parent));
			}

			// Mapeamos otros campos
			solAgrItems.setSalesOrderAgrItem(itemCount);
			solAgrItems.setTransactionCurrency(solIndItem.getTransactionCurrency());
			solAgrItems.setUnidadProductiva(solIndItem.getUnidadProductiva());
			solAgrItems.setOficinaVentas(si.getSalesOffice());
			solAgrItems.setPurchaseOrderByCustomer(si.getDistributionChannel().equals("PR") ? "" : si.getPurchaseOrderByCustomer());
			mapAndStoreItemPrices(solIndItem.getPrices(), solAgrItems);

			return solAgrItems;

		} catch (Exception e) {
			log.error("Error al procesar " + solIndItem + ": " + e, e);
			throw e;
		}
	}

	@Override
	@Transactional
	@NewSpan
	@Loggable
	public void agruparIdItemsByTmpTable(SolicitudesAgrupadas sa, List<Long> items, boolean esPrivados) throws Exception {
	    if (items == null || items.isEmpty()) {
	        return;
	    }

	    // Create the temporary table
	    createTemporaryTable(sa.getId());

	    // Loop through the items list in batches
	    for (List<Long> batchItems : Lists.partition(items, PARTITION_SIZE)) {
	        insertBatchIntoTemporaryTable(batchItems, sa.getId());
	    }	    
	    // After the data has been loaded, retrieve the results of the query
	    List<GroupedSolIndItems> results = getGroupedItemResults(sa.getId());
	    int itemCount = 1;
	    Map<String, Integer> nuevasPosiciones = new HashMap<>();
	    for (GroupedSolIndItems groupedItem : results) {
	        List<BigInteger> idsForGrouping = getIdsForGrouping(groupedItem, sa.getId());
	        // Now you have the list of SOLINDITEMS_ID for the current grouping, you can further process it
	        itemCount = processIdsForGrouping(sa, idsForGrouping, itemCount, nuevasPosiciones);
	    }	    
	}

	private void createTemporaryTable(Long idAgrup) {
		String sqlString = null;
		var testVariable = System.getProperty("test.environment");
		if ("true".equals(testVariable)) {
			entityManager.createNativeQuery("DROP TABLE IF EXISTS solIndItemsGrouped" + idAgrup).executeUpdate();
			sqlString = "CREATE TEMPORARY TABLE solIndItemsGrouped" + idAgrup + " (" +
					"    SOLINDITEMS_ID BIGINT NOT NULL," +
					"    SALESORDERINDITEM INTEGER," +
					"    TIPOPOSICION VARCHAR(255)," +
					"    MATERIAL VARCHAR(255)," +
					"    PRICEREFERENCEMATERIAL VARCHAR(255)," +
					"    PRODUCTIONPLANT VARCHAR(255)," +
					"    PROFITCENTER VARCHAR(255)," +
					"    DELPRODUCTIVA VARCHAR(255)," +
					"    CODIGODELEGACION VARCHAR(255)," +
					"    GRUPOPRECIO VARCHAR(255)," +
					"    LISTAPRECIO VARCHAR(255)," +
					"    TIPOPETICION VARCHAR(255)," +
					"    ORGANIZATIONDIVISION VARCHAR(255)," +
					"    HIGHERLEVELITEM INTEGER" +
					")";
		} else {
			sqlString = "CREATE LOCAL TEMPORARY TABLE #solIndItemsGrouped" + idAgrup + " (" +
					"    SOLINDITEMS_ID BIGINT NOT NULL," +
					"    SALESORDERINDITEM INTEGER," +
					"    TIPOPOSICION VARCHAR(255)," +
					"    MATERIAL VARCHAR(255)," +
					"    PRICEREFERENCEMATERIAL VARCHAR(255)," +
					"    PRODUCTIONPLANT VARCHAR(255)," +
					"    PROFITCENTER VARCHAR(255)," +
					"    DELPRODUCTIVA VARCHAR(255)," +
					"    CODIGODELEGACION VARCHAR(255)," +
					"    GRUPOPRECIO VARCHAR(255)," +
					"    LISTAPRECIO VARCHAR(255)," +
					"    TIPOPETICION VARCHAR(255)," +
					"    ORGANIZATIONDIVISION VARCHAR(255)," +
					"    HIGHERLEVELITEM INTEGER" +
					")";
			
		}
		entityManager.createNativeQuery(sqlString).executeUpdate();
	}

	private void insertBatchIntoTemporaryTable(List<Long> batchItems, Long idAgrup) {
		String sqlString = null;
		var testVariable = System.getProperty("test.environment");
		if ("true".equals(testVariable)) {
			sqlString = "INSERT INTO solIndItemsGrouped" + idAgrup + " (SOLINDITEMS_ID, SALESORDERINDITEM, "
					+ "    TIPOPOSICION, MATERIAL, PRICEREFERENCEMATERIAL, PRODUCTIONPLANT, PROFITCENTER, "
					+ "    DELPRODUCTIVA, CODIGODELEGACION, GRUPOPRECIO, LISTAPRECIO, TIPOPETICION, ORGANIZATIONDIVISION, HIGHERLEVELITEM) "
					+ "SELECT sii.id, sii.SALESORDERINDITEM, "
					+ "    sii.SALESORDERITEMCATEGORY, sii.MATERIAL, sii.PRICEREFERENCEMATERIAL, sii.PRODUCTIONPLANT, sii.PROFITCENTER, "
					+ "    sii.DELPRODUCTIVA, ti.SALESOFFICE, ti.GRUPOPRECIOCLIENTE, "
					+ "    ti.TARIFA, ti.TIPOPETICION, sii.ORGANIZATIONDIVISION, sii.HIGHERLEVELLTEM FROM T_SolIndItems sii "
					+ "JOIN T_SolicitudIndividual ti ON sii.FK_SOLICITUDINDIVIDUAL = ti.id WHERE sii.id IN :items";

		} else {
			sqlString = "INSERT INTO #solIndItemsGrouped" + idAgrup + " (SOLINDITEMS_ID, SALESORDERINDITEM, "
					+ "    TIPOPOSICION, MATERIAL, PRICEREFERENCEMATERIAL, PRODUCTIONPLANT, PROFITCENTER, "
					+ "    DELPRODUCTIVA, CODIGODELEGACION, GRUPOPRECIO, LISTAPRECIO, TIPOPETICION, ORGANIZATIONDIVISION, HIGHERLEVELITEM) "
					+ "SELECT sii.id, sii.SALESORDERINDITEM, "
					+ "    sii.SALESORDERITEMCATEGORY, sii.MATERIAL, sii.PRICEREFERENCEMATERIAL, sii.PRODUCTIONPLANT, sii.PROFITCENTER, "
					+ "    sii.DELPRODUCTIVA, ti.SALESOFFICE, ti.GRUPOPRECIOCLIENTE, "
					+ "    ti.TARIFA, ti.TIPOPETICION, sii.ORGANIZATIONDIVISION, sii.HIGHERLEVELLTEM FROM T_SolIndItems sii "
					+ "JOIN T_SolicitudIndividual ti ON sii.FK_SOLICITUDINDIVIDUAL = ti.id WHERE sii.id IN :items";
		}
		entityManager.createNativeQuery(sqlString).setParameter("items", batchItems).executeUpdate();
	}
	
	@SuppressWarnings("unchecked")
	private List<GroupedSolIndItems> getGroupedItemResults(Long idAgrup) {
		String sql = null;
		var testVariable = System.getProperty("test.environment");
		if ("true".equals(testVariable)) {
			sql = "SELECT COUNT(1) AS count, TIPOPOSICION, MATERIAL, PRICEREFERENCEMATERIAL, "
					+ "PRODUCTIONPLANT, PROFITCENTER, DELPRODUCTIVA, CODIGODELEGACION, GRUPOPRECIO, LISTAPRECIO, TIPOPETICION, ORGANIZATIONDIVISION "
					+ "FROM solIndItemsGrouped" + idAgrup + " GROUP BY TIPOPOSICION, MATERIAL, PRICEREFERENCEMATERIAL, PRODUCTIONPLANT, "
					+ "PROFITCENTER, DELPRODUCTIVA, CODIGODELEGACION, GRUPOPRECIO, LISTAPRECIO, TIPOPETICION, ORGANIZATIONDIVISION"
					+ " ORDER BY TIPOPOSICION DESC";
		} else {
			sql = "SELECT COUNT(1) AS count, TIPOPOSICION, MATERIAL, PRICEREFERENCEMATERIAL, "
					+ "PRODUCTIONPLANT, PROFITCENTER, DELPRODUCTIVA, CODIGODELEGACION, GRUPOPRECIO, LISTAPRECIO, TIPOPETICION, ORGANIZATIONDIVISION "
					+ "FROM #solIndItemsGrouped" + idAgrup + " GROUP BY TIPOPOSICION, MATERIAL, PRICEREFERENCEMATERIAL, PRODUCTIONPLANT, "
					+ "PROFITCENTER, DELPRODUCTIVA, CODIGODELEGACION, GRUPOPRECIO, LISTAPRECIO, TIPOPETICION, ORGANIZATIONDIVISION"
					+ " ORDER BY TIPOPOSICION DESC";
		}
		return entityManager.createNativeQuery(sql, "GroupedSolIndItemsMapping").getResultList();
	}
	
	@SuppressWarnings("unchecked")
	private List<BigInteger> getIdsForGrouping(GroupedSolIndItems groupedItem, Long idAgrup) {
		String sql = null;
		List<BigInteger> response = null;
		var testVariable = System.getProperty("test.environment");
		if ("true".equals(testVariable)) {
			sql = "SELECT SOLINDITEMS_ID FROM solIndItemsGrouped" + idAgrup + " WHERE " +
					 "TIPOPOSICION = :tipoPosicion AND " +
	                 "MATERIAL = :material AND " +
	                 "(PRICEREFERENCEMATERIAL = :priceReferenceMaterial OR (:priceReferenceMaterial IS NULL AND PRICEREFERENCEMATERIAL IS NULL)) AND " +
	                 "(PRODUCTIONPLANT = :productionPlant OR (:productionPlant IS NULL AND PRODUCTIONPLANT IS NULL)) AND " +
	                 "(PROFITCENTER = :profitCenter OR (:profitCenter IS NULL AND PROFITCENTER IS NULL)) AND " +
	                 "(DELPRODUCTIVA = :delProductiva OR (:delProductiva IS NULL AND DELPRODUCTIVA IS NULL)) AND " +
	                 "CODIGODELEGACION = :codigoDelegacion AND " +
	                 "(GRUPOPRECIO = :grupoPrecio OR (:grupoPrecio IS NULL AND GRUPOPRECIO IS NULL)) AND " +
	                 "(LISTAPRECIO = :listaPrecio OR (:listaPrecio IS NULL AND LISTAPRECIO IS NULL)) AND " +
	                 "TIPOPETICION = :tipoPeticion AND " +
	                 "ORGANIZATIONDIVISION = :organizationDivision ORDER BY HIGHERLEVELITEM";
			
		} else {
	    sql = "SELECT SOLINDITEMS_ID FROM #solIndItemsGrouped" + idAgrup + " WHERE " +
	                 "TIPOPOSICION = :tipoPosicion AND " +
	                 "MATERIAL = :material AND " +
	                 "(PRICEREFERENCEMATERIAL = :priceReferenceMaterial OR (:priceReferenceMaterial IS NULL AND PRICEREFERENCEMATERIAL IS NULL)) AND " +
	                 "(PRODUCTIONPLANT = :productionPlant OR (:productionPlant IS NULL AND PRODUCTIONPLANT IS NULL)) AND " +
	                 "(PROFITCENTER = :profitCenter OR (:profitCenter IS NULL AND PROFITCENTER IS NULL)) AND " +
	                 "(DELPRODUCTIVA = :delProductiva OR (:delProductiva IS NULL AND DELPRODUCTIVA IS NULL)) AND " +
	                 "CODIGODELEGACION = :codigoDelegacion AND " +
	                 "(GRUPOPRECIO = :grupoPrecio OR (:grupoPrecio IS NULL AND GRUPOPRECIO IS NULL)) AND " +
	                 "(LISTAPRECIO = :listaPrecio OR (:listaPrecio IS NULL AND LISTAPRECIO IS NULL)) AND " +
	                 "TIPOPETICION = :tipoPeticion AND " +
	                 "ORGANIZATIONDIVISION = :organizationDivision ORDER BY HIGHERLEVELITEM";
		}
		
		response = entityManager.createNativeQuery(sql)
	            .setParameter("tipoPosicion", groupedItem.getTipoPosicion())
	            .setParameter("material", groupedItem.getMaterial())
	            .setParameter("priceReferenceMaterial", groupedItem.getPriceReferenceMaterial())
	            .setParameter("productionPlant", groupedItem.getProductionPlant())
	            .setParameter("profitCenter", groupedItem.getProfitCenter())
	            .setParameter("delProductiva", groupedItem.getDelProductiva())
	            .setParameter("codigoDelegacion", groupedItem.getCodigoDelegacion())
	            .setParameter("grupoPrecio", groupedItem.getGrupoPrecio())
	            .setParameter("listaPrecio", groupedItem.getListaPrecio())
	            .setParameter("tipoPeticion", groupedItem.getTipoPeticion())
	            .setParameter("organizationDivision", groupedItem.getOrganizationDivision())
	            .getResultList();
		
	    return response;
	}


	private int processIdsForGrouping(SolicitudesAgrupadas sa, List<BigInteger> idsForGrouping, int itemCount, Map<String, Integer> nuevasPosiciones)
			throws Exception {
		return agrupadorItemsSrv.compactarIdItems(sa.getId(), idsForGrouping, itemCount, nuevasPosiciones);
	}	

	@Override
	@Transactional
	@NewSpan2
	@Loggable	
	public void agruparItems(SolicitudesAgrupadas sa, SolicitudIndividual si, boolean esPrivados) throws Exception {
		List<SolIndItems> sortedItems = si.getItems().stream()
				.sorted(Comparator.comparingInt(SolIndItems::getSalesOrderIndItem)).collect(Collectors.toList());
		if (esPrivados) {
			mapearItems(sa, si, sortedItems);
		} else {
			compactarItems(sa, sortedItems);
		}
	}

	@NewSpan2
	private void mapearItems(SolicitudesAgrupadas solicitudAgrupada, SolicitudIndividual si,
			List<SolIndItems> solIndItems) {
		mapAndStoreSolIndHeaderPartners(si, solicitudAgrupada);
		mapAndStoreSolIndHeaderPricings(si, solicitudAgrupada);
		TrazabilidadSolicitud ts = si.getTrazabilidad();
		for (SolIndItems solIndItem : solIndItems) {
			if (esItemFacturable(solIndItem)) {
				Trazabilidad trazabilidad = solIndItem.getTrazabilidad();
				SolAgrItemsKey key = new SolAgrItemsKey(solIndItem, null, entityManager);
				SolAgrItems solAgrItems = new SolAgrItems(key, solicitudAgrupada);
				mapAndStoreItemPrices(solIndItem.getPrices(), solAgrItems);
				solAgrItems.setSalesOrderAgrItem(solIndItem.getSalesOrderIndItem());
				solAgrItems.setHigherLevelltem(solIndItem.getHigherLevelltem());
				solAgrItems.setTransactionCurrency(solIndItem.getTransactionCurrency());
				solAgrItems.setUnidadProductiva(solIndItem.getUnidadProductiva());
				solAgrItems.setOficinaVentas(si.getSalesOffice());
				solAgrItems.setPurchaseOrderByCustomer(si.getPurchaseOrderByCustomer());
				solAgrItems.addRequestedQuantity(solIndItem.getRequestedQuantity());
				solAgrItems.addNetAmount(solIndItem.getNetAmount());
				solAgrItems.addTaxAmount(solIndItem.getTaxAmount());
				solAgrItems.setPriceReferenceMaterial(solIndItem.getPriceReferenceMaterial());
				solAgrItems.setDelProductiva(solIndItem.getDelProductiva());
				solAgrItems.addTrazabilidad(trazabilidad);
				solicitudAgrupada.addItem(solAgrItems);
				trazabilidad.setItemAgr(solAgrItems);
				solAgrItemsSrv.create(solAgrItems);
			}
		}
		TrazabilidadSolicitudAgrupado trzAgr = new TrazabilidadSolicitudAgrupado(solicitudAgrupada, ts);
		solicitudAgrupada.addTrazabilidad(trzAgr);
		alterConditionRateValue(solicitudAgrupada);
	}

	private boolean esItemFacturable(SolIndItems solIndItem) {
		final String codigoEstado = solIndItem.getTrazabilidad().getUltimoEstado();
		return !ESTADOS_NO_FACTURABLES.contains(codigoEstado);
	}

	private void mapAndStoreSolIndHeaderPricings(SolicitudIndividual si, SolicitudesAgrupadas solicitudAgrupada) {
		Set<SolIndPricing> prices = si.getPrices();
		prices.forEach(price -> {
			var solAgrPricing = mapToSolAgrPricing(price);
			solAgrPricing.setSolicitudAgr(solicitudAgrupada);
			solicitudAgrupada.addPrice(solAgrPricing);
		});
	}
	
	private void compactarIdItems(SolicitudesAgrupadas solicitudAgrupada, List<Long> solIndItems) throws Exception {
		String tipoContrato = solicitudAgrupada.getTipoContrato();
		Map<SolAgrItemsKey, SolAgrItems> solAgrItemsByKey = new HashMap<>();
		Map<String, Integer> nuevasPosiciones = new HashMap<>();
		int itemCount = 1;
		for (Long idSolIndItem : solIndItems) {
			var solIndItem = solIndItemsSrv.findById(idSolIndItem).get();
			itemCount = processToCompact(solicitudAgrupada, tipoContrato, solAgrItemsByKey, nuevasPosiciones, itemCount, solIndItem);
		}
		alterConditionRateValue(solicitudAgrupada);
		alterImpuestosYPrecioUnitario(solicitudAgrupada);
	}

	private void compactarItems(SolicitudesAgrupadas solicitudAgrupada, List<SolIndItems> solIndItems) throws Exception {

		String tipoContrato = solicitudAgrupada.getTipoContrato();
		Map<SolAgrItemsKey, SolAgrItems> solAgrItemsByKey = new HashMap<>();
		Map<String, Integer> nuevasPosiciones = new HashMap<>();
		solIndItems.sort(Comparator.comparingInt(SolIndItems::getHigherLevelltem));
		int itemCount = 1;
		for (SolIndItems solIndItem : solIndItems) {
			itemCount = processToCompact(solicitudAgrupada, tipoContrato, solAgrItemsByKey, nuevasPosiciones, itemCount, solIndItem);
		}
		alterConditionRateValue(solicitudAgrupada);
		alterImpuestosYPrecioUnitario(solicitudAgrupada);
	}

	private int processToCompact(SolicitudesAgrupadas solicitudAgrupada, String tipoContrato, Map<SolAgrItemsKey, SolAgrItems> solAgrItemsByKey,
			Map<String, Integer> nuevasPosiciones, int itemCount, SolIndItems solIndItem) throws Exception {
		try {
			if (esItemFacturable(solIndItem)) {
				Trazabilidad trazabilidad = solIndItem.getTrazabilidad();
				SolicitudIndividual si = solIndItem.getSolicitudInd();
				// En este punto, todas las solicitudes individuales de la agrupación son del mismo sector
				if (solicitudAgrupada.getOrganizationDivision() == null) {
					solicitudAgrupada.setOrganizationDivision(si.getOrganizationDivision());
				}
				//Informar transaction currency
				if (solicitudAgrupada.getTransactionCurrency() == null) {
					solicitudAgrupada.setTransactionCurrency(si.getTransactionCurrency());
				}
				//El pricing date de la agrupación es la fecha de petición más reciente
				setPricingDate(solicitudAgrupada, solIndItem);
				if (solicitudAgrupada.getCargoPeticion() == null) {
					solicitudAgrupada.setCargoPeticion(si.getTrazabilidad().getPeticionRec().getCargoPeticion());
				}
				TrazabilidadSolicitud ts = si.getTrazabilidad();

				SolAgrItems solAgrItems = getFromMapOrCreate(solAgrItemsByKey, solIndItem, solicitudAgrupada);

				solAgrItems.addRequestedQuantity(solIndItem.getRequestedQuantity());
				solAgrItems.addNetAmount(solIndItem.getNetAmount());
				solAgrItems.addTaxAmount(solIndItem.getTaxAmount());
				solAgrItems.addPointAmount(solIndItem.getPointAmount());
				
				// Compactar condiciones de precio
				if (solAgrItems.getPrices().isEmpty()
						&& (!ConstFacturacion.TIPO_CONTRATO_CF.equals(tipoContrato) || !ConstFacturacion.TIPO_CONTRATO_CV.equals(tipoContrato))) {
					mapAndStoreItemPrices(solIndItem.getPrices(), solAgrItems);
				}
				
				// Trazabilidad
				trazabilidad.setItemAgr(solAgrItems);

				boolean isAlreadyCreated = solicitudAgrupada.getTrazabilidad()
						.stream().anyMatch((trazabilidadSolicitudAgrupado -> Objects.equals(ts, trazabilidadSolicitudAgrupado.getTrazabilidad())));

				if(!isAlreadyCreated){
					TrazabilidadSolicitudAgrupado trzAgr = new TrazabilidadSolicitudAgrupado(solicitudAgrupada, ts);
					solicitudAgrupada.addTrazabilidad(trzAgr);
				}

				// Mantenemos jerarquia
				int idAgrItem = solAgrItems.getSalesOrderAgrItem() == 0 ? itemCount : solAgrItems.getSalesOrderAgrItem();
				String position = String.valueOf(si.getPurchaseOrderByCustomer()) + String.valueOf(solIndItem.getSalesOrderIndItem());
				nuevasPosiciones.put(position, idAgrItem);
				if (solIndItem.getHigherLevelltem() != 0) {
					String parent = String.valueOf(si.getPurchaseOrderByCustomer()) + String.valueOf(solIndItem.getHigherLevelltem());
					if (nuevasPosiciones.containsKey(parent))
						solAgrItems.setHigherLevelltem(nuevasPosiciones.get(parent));
				}

				// Mapeamos otros campos
				if (solAgrItems.getSalesOrderAgrItem() == 0) {
					solAgrItems.setSalesOrderAgrItem(itemCount);
					solAgrItems.setTransactionCurrency(solIndItem.getTransactionCurrency());
					solAgrItems.setUnidadProductiva(solIndItem.getUnidadProductiva());
					solAgrItems.setOficinaVentas(si.getSalesOffice());
					solAgrItems.setPurchaseOrderByCustomer(si.getDistributionChannel().equals("PR") ? "" : si.getPurchaseOrderByCustomer());
					itemCount++;
				} else {
					incrementConditionPrice(solAgrItems, solIndItem);
				}
			}
		} catch (Exception e) {
			log.error("Error al procesar " + solIndItem, e);
			throw e;
		}
		return itemCount;
	}

	@NewSpan2
	private void alterImpuestosYPrecioUnitario(SolicitudesAgrupadas solicitudAgrupada) {
		Set<SolAgrItems> saItems = solicitudAgrupada.getItems();
		for (SolAgrItems saItem : saItems) {
			var zpr1 = saItem.getPrices().stream().filter(p -> p.getConditionType().equals(ConstFacturacion.CONDICION_PRECIO_NETO)).findAny().get();
			var mwst = saItem.getPrices().stream().filter(p -> p.getConditionType().equals(ConstFacturacion.CONDICION_PRECIO_IVA)).findAny().get();
			zpr1.setConditionRateValue(zpr1.getConditionAmount().divide(saItem.getRequestedQuantity(), 2, RoundingMode.HALF_UP));
			mwst.setConditionAmount(zpr1.getConditionAmount().multiply(mwst.getConditionRateValue()).setScale(2, RoundingMode.HALF_UP)
					.divide(new BigDecimal(100), 2, RoundingMode.HALF_UP));
		}

	}

	private void setPricingDate(SolicitudesAgrupadas solicitudAgrupada, SolIndItems solIndItem) {
		var pricingDate = solicitudAgrupada.getPricingAgrDate();
		var pricingDateItem = solIndItem.getSolicitudInd().getPricingDate();
		if (pricingDate == null || pricingDateItem.compareTo(pricingDate) > 0) {
			solicitudAgrupada.setPricingAgrDate(pricingDateItem);
		}
	}

	@NewSpan2
	private void alterConditionRateValue(SolicitudesAgrupadas solicitudAgrupada) {
		Set<SolAgrItems> saItems = solicitudAgrupada.getItems();
		for (SolAgrItems saItem : saItems) {
			saItem.getPrices().forEach(x -> {
				if (Arrays.stream(ConstFacturacion.CONDICIONES_ENVIO_CONDVALUE)
						.anyMatch(y -> y.equals(x.getConditionType()))) {
					x.setConditionRateValue(x.getConditionAmount());
				}
			});
		}
	}

	@Override
	@Transactional
	@NewSpan
	public SolicitudesAgrupadas doSaveSolicitudesAgrupadas(SolicitudesAgrupadas solicitudAgrupada,
			Set<BasicMessagesEntity> messages) {

		// Persistimos
		SolicitudesAgrupadas saCreated = this.create(solicitudAgrupada);
		// colectamos todos los item agrupados ya persistidos en el paso anterior
		Set<SolAgrItems> itemsAgrupadosSaved = saCreated.getItems();

		MessagesUtils.addInformation(messages, "0605");

		// Actualiza la trazabilidad de las solicitudes
		updateTrazabilidadSolicitud(saCreated, messages);

		// Actualiza trazabilidad de las pruebas
		for (SolAgrItems item : itemsAgrupadosSaved) {
			procesadorEstadosService.setEstado(item, Facturable.CODIGO, messages, null);
		}
		procesadorEstadosService.setEstado(saCreated, Facturable.CODIGO, messages, null);
		return saCreated;
	}


	/**
	 *
	 * Actualiza la trazabilidad a nivel de solicitud
	 */
	@NewSpan2
	private void updateTrazabilidadSolicitud(SolicitudesAgrupadas solAgr,
			Set<BasicMessagesEntity> messages) {
		procesadorEstadosService.procesarEstados(solAgr, messages);

		final Set<TrazabilidadSolicitudAgrupado> trazabilidadSolicitudAgrupados = solAgr.getTrazabilidad();
		for (TrazabilidadSolicitudAgrupado trzAgr: trazabilidadSolicitudAgrupados) {
			final TrazabilidadSolicitud trazabilidadSolicitud = trzAgr.getTrazabilidad();
			trazabilidadSolicitud.addAgrupados(trzAgr);

			trazabilidadSolicitudSrv.update(trazabilidadSolicitud);
		}
	}

	@NewSpan2
	private void incrementConditionPrice(SolAgrItems solAgrItems, SolIndItems solIndItem) {
		solIndItem.getPrices().forEach(price -> {
			solAgrItems.getPrices().stream()
					.filter(priceAgr -> priceAgr.getConditionType().equals(price.getConditionType())).findAny()
					.ifPresent(priceAgr -> {
						if (price.getConditionQuantity().compareTo(BigDecimal.ZERO) == 0) {
							priceAgr.addConditionAmount(price.getConditionAmount());
						}
					});
		});
	}

	/**
	 *
	 * Se usa una clase con los atributos que se tienen que comparar Se implemento
	 * el equals y el hashcode para usarla como clave de un Map crear un objeto de
	 * clave con datos de solIndItems y buscarlo en el Map
	 */
	protected SolAgrItems getFromMapOrCreate(Map<SolAgrItemsKey, SolAgrItems> solAgrItemsByKey, SolIndItems solIndItems,
											 SolicitudesAgrupadas solicitudAgrupada) {
		String tipoContrato = solicitudAgrupada.getTipoContrato();
		final SolAgrItemsKey key = new SolAgrItemsKey(solIndItems, tipoContrato, entityManager);
		SolAgrItems solAgrItems = solAgrItemsByKey.get(key);
		if (solAgrItems == null) {
			solAgrItems = new SolAgrItems(key, solicitudAgrupada);
			if (tipoContrato == null) {
				mapAndStoreSolIndPartners(solIndItems.getSolicitudInd().getPartners(), solAgrItems);
			}
			solAgrItemsService.create(solAgrItems);

			solAgrItemsByKey.put(key, solAgrItems);

		}
		return solAgrItems;
	}
	
	protected SolAgrItems create(SolIndItems solIndItems, SolicitudesAgrupadas solicitudAgrupada) {
		String tipoContrato = solicitudAgrupada.getTipoContrato();
		final SolAgrItemsKey key = new SolAgrItemsKey(solIndItems, tipoContrato);
		var solAgrItems = new SolAgrItems(key, solicitudAgrupada);
		if (tipoContrato == null) {
			mapAndStoreSolIndPartners(solIndItems.getSolicitudInd().getPartners(), solAgrItems);
		}
		solAgrItemsService.create(solAgrItems);
		return solAgrItems;
	}

	/**
	 *
	 * Mapeamos las condiciones de precio
	 */
	private void mapAndStoreItemPrices(Set<SolIndItemPricing> prices, SolAgrItems solAgrItems) {
		prices.forEach(price -> {
			if (!CONDICION_PRECIO_ADICIONAL_FIJO.equals(price.getConditionType())) {
				SolAgrItemPricing solAgrItemPricing = mapToSolAgrItemPricing(price);
				solAgrItemPricing.setPosicion(solAgrItems);
				solAgrItems.addPrice(solAgrItemPricing);
			}
		});
	}

	/**
	 * Mapeamos los interlocutores desde la cabecera a la posicion
	 */
	private void mapAndStoreSolIndPartners(Set<SolIndPartner> partners, SolAgrItems solAgrItems) {
		partners.stream()
				.filter(pr -> !pr.getPartnerFunction().equals(ROL_INTERLOCUTOR_PACIENTE)
						&& !pr.getPartnerFunction().equals(ROL_INTERLOCUTOR_PROFESIONAL)
						&& !pr.getPartnerFunction().equals(ROL_INTERLOCUTOR_VISITADOR)
						&& !pr.getPartnerFunction().equals(ROL_INTERLOCUTOR_EMPRESA))
				.forEach(partner -> {
					var solAgrPartner = mapToSolAgrItemPartner(partner);
					solAgrPartner.setPosicion(solAgrItems);
					solAgrItems.addPartner(solAgrPartner);
				});
	}

	/**
	 * Mapeamos los interlocutores desde la cabecera a la posicion
	 */
	private void mapAndStoreSolIndHeaderPartners(SolicitudIndividual si, SolicitudesAgrupadas solicitudAgrupada) {
		final Set<SolIndPartner> partners = si.getPartners();
		partners.stream()
			.filter(pr -> !ROL_INTERLOCUTOR_PACIENTE.equals(pr.getPartnerFunction())
					&& !ROL_INTERLOCUTOR_PROFESIONAL.equals(pr.getPartnerFunction())
					&& !ROL_INTERLOCUTOR_VISITADOR.equals(pr.getPartnerFunction())
					&& !ROL_INTERLOCUTOR_EMPRESA.equals(pr.getPartnerFunction())
			)
			.forEach(partner -> {
				SolAgrPartner solAgrPartner = mapToSolAgrPartner(partner);
				solAgrPartner.setSolicitudAgr(solicitudAgrupada);
				solicitudAgrupada.addPartner(solAgrPartner);
			});
	}

	/**
	 *
	 * Mapeamos los precios
	 *
	 * @param solIndPricing
	 * @return
	 */

	private SolAgrPricing mapToSolAgrPricing(SolIndPricing solIndPricing) {
		SolAgrPricing solAgrPricing = new SolAgrPricing();
		solAgrPricing.setPricingProcedureStep(solIndPricing.getPricingProcedureStep());
		solAgrPricing.setPricingProcedureCounter(solIndPricing.getPricingProcedureCounter());
		solAgrPricing.setConditionType(solIndPricing.getConditionType());
		solAgrPricing.setConditionRateValue(solIndPricing.getConditionRateValue());
		solAgrPricing.setConditionAmount(solIndPricing.getConditionAmount());
		solAgrPricing.setConditionCurrency(solIndPricing.getConditionCurrency());
		solAgrPricing.setConditionQuantity(solIndPricing.getConditionQuantity());
		solAgrPricing.setConditionQuantityUnit(solIndPricing.getConditionQuantityUnit());
		solAgrPricing.setTransactionCurrency(solIndPricing.getTransactionCurrency());
		return solAgrPricing;
	}

	private SolAgrItemPricing mapToSolAgrItemPricing(SolIndItemPricing solIndItemPricing) {
		SolAgrItemPricing solAgrItemPricing = new SolAgrItemPricing();
		solAgrItemPricing.setPricingProcedureStep(solIndItemPricing.getPricingProcedureStep());
		solAgrItemPricing.setPricingProcedureCounter(solIndItemPricing.getPricingProcedureCounter());
		solAgrItemPricing.setConditionType(solIndItemPricing.getConditionType());
		solAgrItemPricing.setConditionAmount(solIndItemPricing.getConditionAmount());
		solAgrItemPricing.setConditionRateValue(
				solIndItemPricing.getConditionCurrency().equals(ConstFacturacion.CONDITION_CURRENCY_PUNTOS)
						|| solIndItemPricing.getConditionType().equals(ConstFacturacion.CONDICION_PRECIO_NETO)
								? solIndItemPricing.getConditionAmount()
								: solIndItemPricing.getConditionRateValue());
		solAgrItemPricing.setConditionCurrency(
				solIndItemPricing.getConditionCurrency().equals(ConstFacturacion.CONDITION_CURRENCY_PUNTOS)
						? ConstFacturacion.CONDITION_CURRENCY_EUROS
						: solIndItemPricing.getConditionCurrency());
		solAgrItemPricing.setConditionQuantity(solIndItemPricing.getConditionQuantity());
		solAgrItemPricing.setConditionQuantityUnit(solIndItemPricing.getConditionQuantityUnit());
		solAgrItemPricing.setTransactionCurrency(
				solIndItemPricing.getTransactionCurrency().equals(ConstFacturacion.CONDITION_CURRENCY_PUNTOS)
						? ConstFacturacion.CONDITION_CURRENCY_EUROS
						: solIndItemPricing.getTransactionCurrency());
		return solAgrItemPricing;
	}

	/**
	 *
	 * Copiamos los interlocutores desde la solicitud a la solicitud
	 *
	 * @param solIndPartner
	 * @return
	 */
	private SolAgrPartner mapToSolAgrPartner(SolIndPartner solIndPartner) {
		SolAgrPartner solAgrPartner = new SolAgrPartner();
		solAgrPartner.setPartnerFunction(solIndPartner.getPartnerFunction());
		solAgrPartner.setCustomer(solIndPartner.getCustomer());
		solAgrPartner.setSupplier(solIndPartner.getSupplier());
		solAgrPartner.setPersonnel(solIndPartner.getPersonnel());
		solAgrPartner.setContactPerson(solIndPartner.getContactPerson());
		return solAgrPartner;
	}

	/**
	 *
	 * Heredamos los interlocutores desde la solicitud a la posicion
	 *
	 * @param solIndPartner
	 * @return
	 */
	private SolAgrItemPartner mapToSolAgrItemPartner(SolIndPartner solIndPartner) {
		SolAgrItemPartner solAgrItemPartner = new SolAgrItemPartner();
		solAgrItemPartner.setPartnerFunction(solIndPartner.getPartnerFunction());
		solAgrItemPartner.setCustomer(solIndPartner.getCustomer());
		solAgrItemPartner.setSupplier(solIndPartner.getSupplier());
		solAgrItemPartner.setPersonnel(solIndPartner.getPersonnel());
		solAgrItemPartner.setContactPerson(solIndPartner.getContactPerson());
		return solAgrItemPartner;
	}

	private String getSalesOrderTypePrivados(SolicitudIndividual si) {
		String salesOrderType = null;

		if ("0072".equals(si.getSalesOffice())) {
			salesOrderType = ConstFacturacion.TIPO_ORDEN_VENTAS_PRIVADOS;
		} else {
			if (si.getTrazabilidad() != null && si.getTrazabilidad().getDatosPagador() != null) {
				DatosPagador dp = si.getTrazabilidad().getDatosPagador();
				if (ConstFacturacion.IDIOMA_DEFAULT.equals(dp.getCodigoPaisPagador())) {
					if ("NIF".equals(dp.getTipoDocumentoPagador()) && StringUtils.isNotBlank(dp.getNumeroDocumentoPagador())) {
						if (StringUtils.isNumeric(dp.getNumeroDocumentoPagador().subSequence(0, 1))) {
							salesOrderType = ConstFacturacion.TIPO_ORDEN_VENTAS_ZSPR;
						} else {
							salesOrderType = ConstFacturacion.TIPO_ORDEN_VENTAS_PRIVADOS;
						}
					} else {
						salesOrderType = ConstFacturacion.TIPO_ORDEN_VENTAS_ZSPR;
					}
				} else {
					salesOrderType = ConstFacturacion.TIPO_ORDEN_VENTAS_ZSPR;
				}
			}
		}

		return salesOrderType;
	}
}
