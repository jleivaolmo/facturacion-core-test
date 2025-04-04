package com.echevarne.sap.cloud.facturacion.services.impl;

import static com.echevarne.sap.cloud.facturacion.constants.ConstFacturacion.CONDICION_PRECIO_ADICIONAL_FIJO;
import static com.echevarne.sap.cloud.facturacion.constants.ConstFacturacion.CONDICION_PRECIO_BRUTO;
import static com.echevarne.sap.cloud.facturacion.constants.ConstFacturacion.CONDICION_PRECIO_FIJO_PETICION;
import static com.echevarne.sap.cloud.facturacion.constants.ConstFacturacion.CONDICION_PRECIO_VALOR_PERFIL;
import static com.echevarne.sap.cloud.facturacion.constants.ConstFacturacion.CONDITION_CURRENCY_CONTRATOS;
import static com.echevarne.sap.cloud.facturacion.constants.ConstFacturacion.ROL_INTERLOCUTOR_EMPRESA;
import static com.echevarne.sap.cloud.facturacion.constants.ConstFacturacion.ROL_INTERLOCUTOR_PACIENTE;
import static com.echevarne.sap.cloud.facturacion.constants.ConstFacturacion.ROL_INTERLOCUTOR_PROFESIONAL;
import static com.echevarne.sap.cloud.facturacion.constants.ConstFacturacion.ROL_INTERLOCUTOR_VISITADOR;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import javax.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.echevarne.sap.cloud.facturacion.agrupador.AgrupadorDto;
import com.echevarne.sap.cloud.facturacion.constants.ConstFacturacion;
import com.echevarne.sap.cloud.facturacion.gestionestados.Bloqueada;
import com.echevarne.sap.cloud.facturacion.gestionestados.Borrada;
import com.echevarne.sap.cloud.facturacion.gestionestados.Erronea;
import com.echevarne.sap.cloud.facturacion.gestionestados.Excluida;
import com.echevarne.sap.cloud.facturacion.model.contratos.ContratoCapitativo;
import com.echevarne.sap.cloud.facturacion.model.solicitudagrupada.SolAgrItemPartner;
import com.echevarne.sap.cloud.facturacion.model.solicitudagrupada.SolAgrItemPricing;
import com.echevarne.sap.cloud.facturacion.model.solicitudagrupada.SolAgrItems;
import com.echevarne.sap.cloud.facturacion.model.solicitudagrupada.SolAgrItemsKey;
import com.echevarne.sap.cloud.facturacion.model.solicitudagrupada.SolAgrPricing;
import com.echevarne.sap.cloud.facturacion.model.solicitudagrupada.SolicitudesAgrupadas;
import com.echevarne.sap.cloud.facturacion.model.solicitudindividual.SolIndItemPricing;
import com.echevarne.sap.cloud.facturacion.model.solicitudindividual.SolIndItems;
import com.echevarne.sap.cloud.facturacion.model.solicitudindividual.SolIndPartner;
import com.echevarne.sap.cloud.facturacion.model.solicitudindividual.SolicitudIndividual;
import com.echevarne.sap.cloud.facturacion.model.trazabilidad.Trazabilidad;
import com.echevarne.sap.cloud.facturacion.model.trazabilidad.TrazabilidadSolAgrItems;
import com.echevarne.sap.cloud.facturacion.model.trazabilidad.TrazabilidadSolicitud;
import com.echevarne.sap.cloud.facturacion.model.trazabilidad.TrazabilidadSolicitudAgrupada;
import com.echevarne.sap.cloud.facturacion.model.trazabilidad.TrazabilidadSolicitudAgrupado;
import com.echevarne.sap.cloud.facturacion.repositories.SolAgrItemPricingRep;
import com.echevarne.sap.cloud.facturacion.repositories.TrazabilidadRepository;
import com.echevarne.sap.cloud.facturacion.services.AgrupadorItemsService;
import com.echevarne.sap.cloud.facturacion.services.SolAgrItemsService;
import com.echevarne.sap.cloud.facturacion.services.SolIndItemsService;
import com.echevarne.sap.cloud.facturacion.services.SolicitudesAgrupadasService;
import lombok.var;
import lombok.extern.slf4j.Slf4j;

@Service("agrupadorItemsSrv")
@Slf4j
public class AgrupadorItemsServiceImpl implements AgrupadorItemsService {

	@Autowired
	private SolicitudesAgrupadasService solicitudesAgrupadasSrv;
	
	@Autowired
	private SolIndItemsService solIndItemsSrv;
	
	@Autowired
	private SolAgrItemsService solAgrItemsService;
	
	@Autowired
	private SolAgrItemPricingRep solAgrItemPricingRep;
	
	@Autowired
	private TrazabilidadRepository trazabilidadRep;
	
	@Autowired
	private EntityManager entityManager;
	
	private final static Set<String> ESTADOS_NO_FACTURABLES = new HashSet<>(4);
	
	static{
		ESTADOS_NO_FACTURABLES.add(Excluida.CODIGO);
		ESTADOS_NO_FACTURABLES.add(Bloqueada.CODIGO);
		ESTADOS_NO_FACTURABLES.add(Erronea.CODIGO);
		ESTADOS_NO_FACTURABLES.add(Borrada.CODIGO);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public SolicitudesAgrupadas createSolicitudesAgrupadas(AgrupadorDto key) {
		try {
			SolicitudesAgrupadas solicitudesAgrupadas = new SolicitudesAgrupadas();
			ContratoCapitativo contrato = key.getContratoCapitativo();
			solicitudesAgrupadas.setSalesOrderType(key.getClaseDocumento());
			solicitudesAgrupadas.setSalesOrganization(key.getOrganizacionVentas());
			solicitudesAgrupadas.setDistributionChannel(key.getCanalDistribucion());
			solicitudesAgrupadas.setSoldToParty(key.getCodigoCliente());
			solicitudesAgrupadas.setTextoFactura(key.getTextoFactura());
			solicitudesAgrupadas.setAgrupacionKey(key.getAgrupacionKey());
			solicitudesAgrupadas.setDivisorKey(key.getDivisorKey());
			solicitudesAgrupadas.setBasedInObject(key.getObjectId());
			if (contrato != null) {
				solicitudesAgrupadas.setTipoContrato(contrato.getTipoContrato());
			}
			if (key.getTipologiaAgrupacion() == 3) {
				addAgrupadaPricing(solicitudesAgrupadas, contrato);
			}
			setTrazabilidad(solicitudesAgrupadas);
			solicitudesAgrupadasSrv.create(solicitudesAgrupadas);
			return solicitudesAgrupadas;
		} catch (Exception e) {
			log.error("Se ha producido un error al crear la solicitud agrupada con key " + key + ": " + e, e);
			throw e;
		}
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public int compactarIdItems(Long idSolAgr, List<BigInteger> solIndItems, int itemCount, Map<String, Integer> nuevasPosiciones) throws Exception  {
		var optSolAgr = solicitudesAgrupadasSrv.findById(idSolAgr);
		if (!optSolAgr.isPresent()) {
			var msgErr = "No se ha encontrado la agrupación con id " + idSolAgr;
			log.error(msgErr);
			throw new Exception(msgErr);
		}
		var solicitudAgrupada = optSolAgr.get();
		String tipoContrato = solicitudAgrupada.getTipoContrato();
		Map<SolAgrItemsKey, SolAgrItems> solAgrItemsByKey = new HashMap<>();
		for (BigInteger idBigInt : solIndItems) {
			var idSolIndItem = idBigInt.longValue();
			var optSolIndItem = solIndItemsSrv.findById(idSolIndItem);
			if (!optSolIndItem.isPresent()) {
				var msgErr = "No se ha encontrado el idSolIndItem " + idSolIndItem + " para agregar a la agrupación con id " + idSolAgr;
				log.error(msgErr);
				throw new Exception(msgErr);
			}
			var solIndItem = optSolIndItem.get();
			itemCount = processToCompact(solicitudAgrupada, tipoContrato, solAgrItemsByKey, nuevasPosiciones, itemCount, solIndItem);
		}
		var solItemAgrs = solAgrItemsByKey.values();		
		alterConditionPrices(solItemAgrs);
		addPrecioZNET(solItemAgrs);
		return itemCount;
	}
	
	private void addPrecioZNET(Collection<SolAgrItems> solItemAgrs) {
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
					// Compactar condiciones de precio
					if (!ConstFacturacion.TIPO_CONTRATO_CF.equals(tipoContrato) && !ConstFacturacion.TIPO_CONTRATO_CV.equals(tipoContrato)) {
						//Para AM y FP
						mapAndStoreItemPrices(solIndItem.getPrices(), solAgrItems);
					} else {
						//Para CV y CF
						mapAndStoreItemPricesCapitativos(solIndItem.getPrices(), solAgrItems, solIndItem.getRequestedQuantity());
					}
				} else {
					incrementConditionPrice(solAgrItems, solIndItem, tipoContrato);
				}
			}
		} catch (Exception e) {
			log.error("Error al procesar " + solIndItem +": " + e, e);
			throw e;
		}
		return itemCount;
	}
	
	private void mapAndStoreItemPrices(Set<SolIndItemPricing> prices, SolAgrItems solAgrItems) {
		prices.forEach(price -> {
			if (!CONDICION_PRECIO_ADICIONAL_FIJO.equals(price.getConditionType())) {
				SolAgrItemPricing solAgrItemPricing = mapToSolAgrItemPricing(price);
				solAgrItemPricing.setPosicion(solAgrItems);
				solAgrItems.addPrice(solAgrItemPricing);
			}
		});
	}
	
	private void mapAndStoreItemPricesCapitativos(Set<SolIndItemPricing> prices, SolAgrItems solAgrItems, BigDecimal requestedQuantity) {
		prices.forEach(price -> {
			if (CONDICION_PRECIO_BRUTO.equals(price.getConditionType())) {
				SolAgrItemPricing solAgrItemPricing = mapToSolAgrItemPricing(price);
				solAgrItemPricing.setConditionQuantity(requestedQuantity);
				solAgrItemPricing.setPosicion(solAgrItems);
				solAgrItems.addPrice(solAgrItemPricing);
			}
		});
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
	
	private void incrementConditionPrice(SolAgrItems solAgrItems, SolIndItems solIndItem, String tipoContrato) {
		solIndItem.getPrices().forEach(price -> {
			solAgrItems.getPrices().stream().filter(priceAgr -> priceAgr.getConditionType().equals(price.getConditionType())).findAny().ifPresent(priceAgr -> {
				if (ConstFacturacion.TIPO_CONTRATO_CF.equals(tipoContrato) || ConstFacturacion.TIPO_CONTRATO_CV.equals(tipoContrato)) {
					priceAgr.addConditionRateValue(price.getConditionRateValue());
					priceAgr.addConditionAmount(price.getConditionAmount());
					//Condition quantity se ha de informar con el mismo valor que requested quantity
					priceAgr.addConditionQuantity(solIndItem.getRequestedQuantity());
				}
				else if (price.getConditionType().equals(ConstFacturacion.CONDICION_PRECIO_NETO)) {
					priceAgr.addConditionRateValue(price.getConditionAmount());
				}
				else if (price.getConditionType().equals(ConstFacturacion.CONDICION_PRECIO_VALOR_PERFIL)) {
					priceAgr.addConditionRateValue(price.getConditionRateValue());
					priceAgr.addConditionAmount(price.getConditionAmount());
				}
			});
		});
	}
	
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
	
	private SolAgrItemPartner mapToSolAgrItemPartner(SolIndPartner solIndPartner) {
		SolAgrItemPartner solAgrItemPartner = new SolAgrItemPartner();
		solAgrItemPartner.setPartnerFunction(solIndPartner.getPartnerFunction());
		solAgrItemPartner.setCustomer(solIndPartner.getCustomer());
		solAgrItemPartner.setSupplier(solIndPartner.getSupplier());
		solAgrItemPartner.setPersonnel(solIndPartner.getPersonnel());
		solAgrItemPartner.setContactPerson(solIndPartner.getContactPerson());
		return solAgrItemPartner;
	}
	
	private void setPricingDate(SolicitudesAgrupadas solicitudAgrupada, SolIndItems solIndItem) {
		var pricingDate = solicitudAgrupada.getPricingAgrDate();
		var pricingDateItem = solIndItem.getSolicitudInd().getPricingDate();
		if (pricingDate == null || pricingDateItem.compareTo(pricingDate) > 0) {
			solicitudAgrupada.setPricingAgrDate(pricingDateItem);
		}
	}
	
	private boolean esItemFacturable(SolIndItems solIndItem) {
		List<String> ultimoEstado = trazabilidadRep.getUltimoEstado(solIndItem.getTrazabilidad().getId());
		final String codigoEstado = ultimoEstado.size() > 0 ? ultimoEstado.get(0) : "";
		return !ESTADOS_NO_FACTURABLES.contains(codigoEstado);
	}

	private void addAgrupadaPricing(SolicitudesAgrupadas agrupada, ContratoCapitativo contrato) {
		SolAgrPricing headerPricing = new SolAgrPricing();
		headerPricing.setConditionRateValue(contrato.getImporteContrato());
		headerPricing.setConditionType(ConstFacturacion.CONDICION_PRECIO_CAB_CON_CF);
		headerPricing.setConditionCurrency(ConstFacturacion.CURRENCY_DEFAULT);
		headerPricing.setSolicitudAgr(agrupada);
		agrupada.addPrice(headerPricing);
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
	
	private void alterConditionPrices(Collection<SolAgrItems> solItemAgrs) {
		for (SolAgrItems saItem : solItemAgrs) {
			var optPriceZFIP = saItem.getPrices().stream().filter(p -> p.getConditionType().equals(CONDICION_PRECIO_FIJO_PETICION)).findAny();
			if (optPriceZFIP.isPresent()) {
				optPriceZFIP.get().setConditionRateValue(optPriceZFIP.get().getConditionAmount());
				var optPriceZPR0 = saItem.getPrices().stream().filter(p -> p.getConditionType().equals(CONDICION_PRECIO_BRUTO)).findAny();
				if (optPriceZPR0.isPresent() && optPriceZPR0.get().getConditionCurrency().equals(CONDITION_CURRENCY_CONTRATOS)
						&& optPriceZPR0.get().getConditionRateValue().compareTo(BigDecimal.ZERO) == 0) {
					optPriceZFIP.get().setConditionRateValue(BigDecimal.ZERO);
				}
			}
			var optPriceZVPE = saItem.getPrices().stream().filter(p -> p.getConditionType().equals(CONDICION_PRECIO_VALOR_PERFIL)).findAny();
			if (optPriceZVPE.isPresent()) {
				optPriceZVPE.get().setConditionRateValue(optPriceZVPE.get().getConditionAmount());
			}
		}
	}

}
