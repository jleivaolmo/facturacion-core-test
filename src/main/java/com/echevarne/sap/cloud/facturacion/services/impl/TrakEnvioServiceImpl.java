package com.echevarne.sap.cloud.facturacion.services.impl;

import static com.echevarne.sap.cloud.facturacion.util.Destinations.Enum.TRAK;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.echevarne.sap.cloud.facturacion.constants.ConstFacturacion;
import com.echevarne.sap.cloud.facturacion.dto.ConfirmacionFactura;
import com.echevarne.sap.cloud.facturacion.dto.RequestMethod;
import com.echevarne.sap.cloud.facturacion.model.facturacion.FacturaTrak;
import com.echevarne.sap.cloud.facturacion.model.replicated.S4Info;
import com.echevarne.sap.cloud.facturacion.model.replicated.S4MaterialSales;
import com.echevarne.sap.cloud.facturacion.model.solicitudindividual.SolIndItemPricing;
import com.echevarne.sap.cloud.facturacion.model.solicitudindividual.SolIndItems;
import com.echevarne.sap.cloud.facturacion.model.solicitudindividual.SolicitudIndividual;
import com.echevarne.sap.cloud.facturacion.model.tablasZ.ZPETIVALO;
import com.echevarne.sap.cloud.facturacion.model.tablasZ.ZSDT0046;
import com.echevarne.sap.cloud.facturacion.model.tablasZ.ZSDT0071;
import com.echevarne.sap.cloud.facturacion.model.tablasZ.ZSDT70046Key;
import com.echevarne.sap.cloud.facturacion.model.tablasZ.ZSDT70071Key;
import com.echevarne.sap.cloud.facturacion.model.texts.MaterialesText;
import com.echevarne.sap.cloud.facturacion.model.trazabilidad.Trazabilidad;
import com.echevarne.sap.cloud.facturacion.repositories.FacturaTrakRep;
import com.echevarne.sap.cloud.facturacion.repositories.MaterialesTextRep;
import com.echevarne.sap.cloud.facturacion.repositories.TrazabilidadRepository;
import com.echevarne.sap.cloud.facturacion.repositories.ZSDT0046Rep;
import com.echevarne.sap.cloud.facturacion.repositories.ZSDT0071Rep;
import com.echevarne.sap.cloud.facturacion.repositories.replicated.S4MaterialSalesRepository;
import com.echevarne.sap.cloud.facturacion.services.S4InfoService;
import com.echevarne.sap.cloud.facturacion.services.TrakEnvioService;
import com.echevarne.sap.cloud.facturacion.services.ZPETIVALOService;
import com.echevarne.sap.cloud.facturacion.util.Destinations;
import com.echevarne.sap.cloud.facturacion.util.NewSpan2;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("trakEnvioSrv")
@AllArgsConstructor
public class TrakEnvioServiceImpl implements TrakEnvioService {

	private static final String CONFIRMACION_FACTURAS_URI = "/invoice-management/confirm";
	private static final String ENVIO_PETICION_URI = "/method-management/methods";

	private final Destinations destinations;
	private final TrazabilidadRepository trazabilidadRepository;
	private final FacturaTrakRep facturaTrakRep;
	private final ZSDT0071Rep ZSDT0071Rep;
	private final ZSDT0046Rep ZSDT0046Rep;
	private final MaterialesTextRep materialesTextRep;
	private final S4MaterialSalesRepository s4MaterialSalesRepository;

	@Autowired
	private ZPETIVALOService ZPETIVALOService;

	@Autowired
	private S4InfoService s4InfoService;

	@Autowired
	private SolIndItemPricingServiceImpl solIndItemPricingService;

	@Override
	@Async("trakAsync")
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void registrarFacturas(String codigoPedido, String numeroFactura, Date facturaDate, String indicador, Set<String> codigosPeticion,
			String rectificativa) {
		Set<String> codigosPeticionEnviar = new HashSet<>();
		if (codigoPedido != null) {
			Optional<List<Trazabilidad>> trazabilidades = trazabilidadRepository.findAllBySalesOrder(codigoPedido);
			if (trazabilidades.isPresent()) {
				codigosPeticionEnviar.addAll(trazabilidades.get().stream().map(trazabilidad -> {
					return trazabilidad.getItemRec().getPeticion().getSolicitud().getCodigoPeticionLims();
				}).collect(Collectors.toSet()));
			}
		} else {
			codigosPeticionEnviar.addAll(codigosPeticion);
		}
		for (String codigo : codigosPeticionEnviar) {
			FacturaTrak facturaTrak = new FacturaTrak();
			facturaTrak.setCodigoPedido(codigoPedido);
			facturaTrak.setNumeroFactura(numeroFactura);
			facturaTrak.setFacturaDate(new Timestamp(facturaDate.getTime()));
			facturaTrak.setIndicador(indicador);
			facturaTrak.setCodigoPeticion(codigo);
			facturaTrak.setEstado(FacturaTrak.PENDIENTE);
			facturaTrak.setRectificativa(rectificativa);
			facturaTrakRep.save(facturaTrak);
		}
	}

	@Override
	@NewSpan2
	public void enviarFacturas(String codigoPedido, String numeroFactura, Date facturaDate, String indicador, String codigoPeticion, String rectificativa)
			throws Exception {

		final HttpClient httpClient = destinations.getHttpClient(TRAK.getValue());
		String valueRect = rectificativa == null ? "" : rectificativa;
		ConfirmacionFactura confirmacionFactura = new ConfirmacionFactura(codigoPeticion, numeroFactura, facturaDate, indicador, valueRect);
		final String requestBody = convertToJson(confirmacionFactura);
		log.info("Request a enviar a TRAK: {}. Factura: {}", requestBody, numeroFactura);
		final StringEntity stringEntity = new StringEntity(requestBody);
		final HttpPost httpPost = new HttpPost(CONFIRMACION_FACTURAS_URI);
		httpPost.addHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE);
		httpPost.setEntity(stringEntity);
		final HttpResponse response = httpClient.execute(httpPost);
		final String responseBody = EntityUtils.toString(response.getEntity());
		final int statusCode = response.getStatusLine().getStatusCode();
		log.info("Factura enviada a TRAK. Status: {}. Response: {}", statusCode, responseBody);
		if (statusCode == 502) {
			throw new Exception("No se ha podido establecer conexión con Trak");
		}
	}

	@NewSpan2
	public void registrarPeticionValorada(SolicitudIndividual si) {
		log.info("TrakEnvioServiceImpl - enviarPeticionValorada - INICIO - "+si.getPurchaseOrderByCustomer());
		try {
			RequestMethod request = new RequestMethod();

			// INICIO RECOGER DATOS DE LA TABLA ZPETIVALO
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

			String VKORG = si.getSalesOrganization() == null ? "" : si.getSalesOrganization();
			String KUNNR = si.getSoldToParty() == null ? "" : si.getSoldToParty();
			String VKBUR = si.getSalesOffice() == null ? "" : si.getSalesOffice();
			String ZZCARGO = si.getTrazabilidad().getPeticionRec().getCargoPeticion() == null ? "" : si.getTrazabilidad().getPeticionRec().getCargoPeticion();
			String ZZTIPO = String.valueOf(si.getTipoPeticion());
			Integer DATAB = Integer.parseInt(sdf.format(si.getTrazabilidad().getPeticionRec().getEntityCreationTimestamp()));
			String STCD1 = si.getTrazabilidad().getPeticionRec().getNifCliente() == null ? "" : si.getTrazabilidad().getPeticionRec().getNifCliente();

			String ZZREMNR = "";
			if (si.getInterlocutorRemitente().isPresent())
				ZZREMNR = si.getInterlocutorRemitente().get().getCustomer();

			String ZZCIANR = "";
			if (si.getInterlocutorCompania().isPresent())
				ZZCIANR = si.getInterlocutorCompania().get().getCustomer();

			List<ZPETIVALO> listZpetivalo = new ArrayList<ZPETIVALO>();
			for (ZPETIVALO z : ZPETIVALOService.getAll()) {
				if ((z.getVKORG() == null || VKORG.equals(z.getVKORG())) && (z.getKUNNR() == null || KUNNR.equals(z.getKUNNR())) && (z.getVKBUR() == null || VKBUR.equals(z.getVKBUR()))
						&& (z.getZZREMNR() == null || ZZREMNR.equals(z.getZZREMNR())) && (z.getZZCIANR() == null || ZZCIANR.equals(z.getZZCIANR()))
						&& (z.getZZCARGO() == null || ZZCARGO.equals(z.getZZCARGO())) && (z.getZZTIPO() == null || ZZTIPO.equals(z.getZZTIPO()))
						&& (z.getSTCD1() == null || STCD1.equals(z.getSTCD1())) && (z.getDATAB() == null || Integer.parseInt(z.getDATAB()) <= DATAB)
						&& ((z.getZZMETODO().equals("PV-PRESU") && si.getPurchaseOrderByCustomer().startsWith("A")) || !z.getZZMETODO().equals("PV-PRESU"))) {
					listZpetivalo.add(z);
				}
			}
			// FIN RECOGER DATOS DE LA TABLA ZPETIVALO

			String capitativo = "";
			if (si.getSalesOrderType().equals(ConstFacturacion.TIPO_AGRUPACION_FACTURA_CAPFIJO)
					|| si.getSalesOrderType().equals(ConstFacturacion.TIPO_AGRUPACION_FACTURA_CAPVARIABLE))
				capitativo = "X";

			String privado = "";
			Optional<ZSDT0071> z71 = ZSDT0071Rep.findById(new ZSDT70071Key(VKBUR, ZZREMNR, ZZCIANR, ZZCARGO));
			if (z71.isPresent()) {
				privado = z71.get().getZZACTI();
			}

			String data = "";
			for (ZPETIVALO z : listZpetivalo) {
				// CABECERA
				String device = "";
				if(z.getZZDEVICE() != null) {
					device=z.getZZDEVICE();
				}
				
				String bruto = "";
				if(z.getZZBRUTO() != null) {
					bruto=z.getZZBRUTO();
				}
				
				String path = "";
				if(z.getZZPATH() != null) {
					path=z.getZZPATH();
				}
				
				String impre = "";
				if(z.getZZIMPRE() != null) {
					impre=z.getZZIMPRE();
				}
				
				String pdf = "";
				if(z.getZZPDF() != null) {
					pdf=z.getZZPDF();
				}
				
				String ftext = "";
				if(z.getZZFTEXT() != null) {
					ftext=z.getZZFTEXT();
				}
				
				data = "CAB#" + si.getPurchaseOrderByCustomer() + "#" + si.getPurchaseOrderByCustomer() + "#" + z.getZZMETODO() + "#" + device + "#"
						+ path + "#" + impre + "#" + pdf + "#" + ftext + "#" + si.getSalesOrderType() + "#"
						+ si.getSalesOrganization() + "#" + si.getOrganizationDivision() + "#" + si.getTransactionCurrency() + "#" + capitativo + "#" + ""
						+ "#" + privado + "#"+ bruto;

				// DETALLE
				for (SolIndItems prueba : si.getItems()) {
					Optional<ZSDT0046> z46 = null;
					if (StringUtils.isNotBlank(si.getSoldToParty()) && si.getInterlocutorCompania().isPresent()
							&& StringUtils.isNotBlank(prueba.getPriceReferenceMaterial())) {
						z46 = ZSDT0046Rep.findById(
								new ZSDT70046Key(si.getSoldToParty(), si.getInterlocutorCompania().get().getCustomer(), prueba.getPriceReferenceMaterial()));
					}

					BigDecimal precioUnitario = BigDecimal.ZERO;
					if (z46 != null && z46.isPresent()) {
						precioUnitario = new BigDecimal(z46.get().getNETWR());
					} else {
						if (prueba.getNetAmount() != null && prueba.getNetAmount().compareTo(BigDecimal.ZERO) != 0 && prueba.getRequestedQuantity() != null
								&& prueba.getRequestedQuantity().compareTo(BigDecimal.ZERO) != 0) {
							precioUnitario = prueba.getNetAmount().divide(prueba.getRequestedQuantity(), 2, RoundingMode.HALF_UP);
						}
					}

					Optional<MaterialesText> mat = materialesTextRep.findById(prueba.getMaterial());
					String nombrePrueba = null;
					if (mat.isPresent())
						nombrePrueba = mat.get().getNombreMaterial();

					Optional<S4MaterialSales> materialSales = s4MaterialSalesRepository.findByMATNRAndVKORGAndVTWEG(prueba.getMaterial(), VKORG,
							prueba.getGrupoSector());
					String derechoADescuento = "";
					if (materialSales.isPresent() && materialSales.get().getSKTOF() != "")
						derechoADescuento = materialSales.get().getSKTOF();

					String pruebaSinPrecio = (prueba.getNetAmount() == null || prueba.getNetAmount().equals(BigDecimal.ZERO)) ? "X" : "";

					String conditionCurrency = "";
					for (SolIndItemPricing obj : prueba.getPrices()) {
						if (ConstFacturacion.CONDICION_PRECIO_BRUTO.equals(obj.getConditionType()) && StringUtils.isNotBlank(obj.getConditionCurrency())) {
							conditionCurrency = obj.getConditionCurrency();
							break;
						}
					}

					data += "|DET#" + si.getPurchaseOrderByCustomer() + "#" + si.getPurchaseOrderByCustomer() + "#" + prueba.getSalesOrderIndItem() + "#"
							+ prueba.getMaterial() + "#" + nombrePrueba + "#" + prueba.getRequestedQuantity() + "#" + precioUnitario + "#"
							+ prueba.getNetAmount() + "#" + prueba.getSalesOrderItemCategory() + "#" + derechoADescuento + "#"
							+ prueba.getPriceReferenceMaterial() + "#" + pruebaSinPrecio + "#" + conditionCurrency;
				}

				// BAREMOS
				for (SolIndItems prueba : si.getItems()) {
					Set<S4Info> s4info = s4InfoService.findByParams(prueba.getMaterial(), si.getSoldToParty(), si.getSalesOrganization(), si.getSalesOffice(),
							si.getInterlocutorCompania().map(x -> x.getCustomer()).orElse(""));

					int linea = 0;
					for (S4Info s : s4info) {
						linea++;

						String precioUnitario = "0";
						if (s.getIMPORTE() != null && s.getIMPORTE().equals("0.00") && prueba.getNetAmount() != null && prueba.getRequestedQuantity() != null) {
							precioUnitario = String.valueOf(prueba.getNetAmount().divide(prueba.getRequestedQuantity(), 2, RoundingMode.HALF_UP));
						} else if (s.getIMPORTE() != null) {
							precioUnitario = s.getIMPORTE();
						}

						data += "|BAR#" + si.getPurchaseOrderByCustomer() + "#" + si.getPurchaseOrderByCustomer() + "#" + linea + "#" + s.getKDMAT() + "#"
								+ s.getCANTIDAD() + "#" + precioUnitario + "#" + si.getTransactionCurrency();
					}
				}

				// IVA
				for (SolIndItems prueba : si.getItems()) {
					List<SolIndItemPricing> ivas = new ArrayList<SolIndItemPricing>();
					for (SolIndItemPricing p : prueba.getPrices()) {
						if (ConstFacturacion.CONDICION_PRECIO_IVA.equals(p.getConditionType())) {
							ivas.add(p);
						}
					}

					if (ivas.size() == 0) {
						data += "|IVA#" + si.getPurchaseOrderByCustomer() + "#" + si.getPurchaseOrderByCustomer() + "#1#0#0#0";
					}
					
					Map<BigDecimal, BigDecimal> mapaSumaConditionAmount = new HashMap<BigDecimal, BigDecimal>();
					for (SolIndItemPricing iva : ivas) {
						if (mapaSumaConditionAmount.containsKey(iva.getConditionRateValue())) {
							mapaSumaConditionAmount.put(iva.getConditionRateValue(),
									mapaSumaConditionAmount.get(iva.getConditionRateValue()).add(iva.getConditionAmount()));
						} else {
							mapaSumaConditionAmount.put(iva.getConditionRateValue(), iva.getConditionAmount());
						}
					}

					BigDecimal conditionRateValue = null;
					int linea = 0;
					for (SolIndItemPricing iva : ivas) {
						if (linea == 0) {
							linea++;
						} else {
							if (conditionRateValue.compareTo(iva.getConditionRateValue()) == 0) {
								linea++;
							} else {
								linea = 1;
							}
						}

						conditionRateValue = iva.getConditionRateValue();
						BigDecimal importeTotal = mapaSumaConditionAmount.get(conditionRateValue);

						BigDecimal baseImponible = BigDecimal.ZERO;
						if (conditionRateValue != null && conditionRateValue.compareTo(BigDecimal.ZERO) != 0 && importeTotal != null
								&& importeTotal.compareTo(BigDecimal.ZERO) != 0) {
						    baseImponible = importeTotal.divide(conditionRateValue, 2, RoundingMode.HALF_UP).multiply(new BigDecimal(100));
						}

						data += "|IVA#" + si.getPurchaseOrderByCustomer() + "#" + si.getPurchaseOrderByCustomer() + "#" + linea + "#" + conditionRateValue.stripTrailingZeros() + "#"
								+ baseImponible.stripTrailingZeros() + "#" + importeTotal.stripTrailingZeros();
					}
				}

				// DESCUENTOS
				for (SolIndItems prueba : si.getItems()) {
					List<SolIndItemPricing> descuentoZRDC = new ArrayList<SolIndItemPricing>();
					List<SolIndItemPricing> descuentoZRDT = new ArrayList<SolIndItemPricing>();
					List<SolIndItemPricing> descuentoZRD1 = new ArrayList<SolIndItemPricing>();
					List<SolIndItemPricing> descuentoZRDF = new ArrayList<SolIndItemPricing>();
					List<SolIndItemPricing> descuentoZDPR = new ArrayList<SolIndItemPricing>();
					List<SolIndItemPricing> descuentoZDPS = new ArrayList<SolIndItemPricing>();

					for (SolIndItemPricing p : prueba.getPrices()) {
						if ("ZRDC".equals(p.getConditionType())) {
							descuentoZRDC.add(p);
						} else if ("ZRDT".equals(p.getConditionType())) {
							descuentoZRDT.add(p);
						} else if ("ZRD1".equals(p.getConditionType())) {
							descuentoZRD1.add(p);
						} else if ("ZRDF".equals(p.getConditionType())) {
							descuentoZRDF.add(p);
						} else if ("ZDPR".equals(p.getConditionType())) {
							descuentoZDPR.add(p);
						} else if ("ZDPS".equals(p.getConditionType())) {
							descuentoZDPS.add(p);
						}
					}
					
					Map<BigDecimal, BigDecimal> mapaSumaConditionAmountZRDC = new HashMap<BigDecimal, BigDecimal>();
					for (SolIndItemPricing s : descuentoZRDC) {
						if (s.getConditionRateValue().compareTo(new BigDecimal(0)) == -1) {
							if (mapaSumaConditionAmountZRDC.containsKey(s.getConditionRateValue())) {
								mapaSumaConditionAmountZRDC.put(s.getConditionRateValue(),
										mapaSumaConditionAmountZRDC.get(s.getConditionRateValue()).add(s.getConditionAmount()));
							} else {
								mapaSumaConditionAmountZRDC.put(s.getConditionRateValue(), s.getConditionAmount());
							}
						}
					}

					Map<BigDecimal, BigDecimal> mapaSumaConditionAmountZRDT = new HashMap<BigDecimal, BigDecimal>();
					for (SolIndItemPricing s : descuentoZRDT) {
						if (s.getConditionRateValue().compareTo(new BigDecimal(0)) == -1) {
							if (mapaSumaConditionAmountZRDT.containsKey(s.getConditionRateValue())) {
								mapaSumaConditionAmountZRDT.put(s.getConditionRateValue(),
										mapaSumaConditionAmountZRDT.get(s.getConditionRateValue()).add(s.getConditionAmount()));
							} else {
								mapaSumaConditionAmountZRDT.put(s.getConditionRateValue(), s.getConditionAmount());
							}
						}
					}

					Map<BigDecimal, BigDecimal> mapaSumaConditionAmountZRD1 = new HashMap<BigDecimal, BigDecimal>();
					for (SolIndItemPricing s : descuentoZRD1) {
						if (s.getConditionRateValue().compareTo(new BigDecimal(0)) == -1) {
							if (mapaSumaConditionAmountZRD1.containsKey(s.getConditionRateValue())) {
								mapaSumaConditionAmountZRD1.put(s.getConditionRateValue(),
										mapaSumaConditionAmountZRD1.get(s.getConditionRateValue()).add(s.getConditionAmount()));
							} else {
								mapaSumaConditionAmountZRD1.put(s.getConditionRateValue(), s.getConditionAmount());
							}
						}
					}

					Map<BigDecimal, BigDecimal> mapaSumaConditionAmountZRDF = new HashMap<BigDecimal, BigDecimal>();
					for (SolIndItemPricing s : descuentoZRDF) {
						if (s.getConditionRateValue().compareTo(new BigDecimal(0)) == -1) {
							if (mapaSumaConditionAmountZRDF.containsKey(s.getConditionRateValue())) {
								mapaSumaConditionAmountZRDF.put(s.getConditionRateValue(),
										mapaSumaConditionAmountZRDF.get(s.getConditionRateValue()).add(s.getConditionAmount()));
							} else {
								mapaSumaConditionAmountZRDF.put(s.getConditionRateValue(), s.getConditionAmount());
							}
						}
					}

					Map<BigDecimal, BigDecimal> mapaSumaConditionAmountZDPR = new HashMap<BigDecimal, BigDecimal>();
					for (SolIndItemPricing s : descuentoZDPR) {
						if (s.getConditionRateValue().compareTo(new BigDecimal(0)) == -1) {
							if (mapaSumaConditionAmountZDPR.containsKey(s.getConditionRateValue())) {
								mapaSumaConditionAmountZDPR.put(s.getConditionRateValue(),
										mapaSumaConditionAmountZDPR.get(s.getConditionRateValue()).add(s.getConditionAmount()));
							} else {
								mapaSumaConditionAmountZDPR.put(s.getConditionRateValue(), s.getConditionAmount());
							}
						}
					}

					BigDecimal conditionRateValue = null;
					int linea = 0;
					for (SolIndItemPricing s : descuentoZRDC) {
						if (s.getConditionRateValue().compareTo(new BigDecimal(0)) == -1) {
							if (linea == 0) {
								linea++;
							} else {
								if (conditionRateValue.compareTo(s.getConditionRateValue()) == 0) {
									linea++;
								} else {
									linea = 1;
								}
							}

							conditionRateValue = s.getConditionRateValue();

							BigDecimal importeTotal = mapaSumaConditionAmountZRDC.get(conditionRateValue);

							BigDecimal baseImponible = BigDecimal.ZERO;
							if (conditionRateValue != null && conditionRateValue.compareTo(BigDecimal.ZERO) != 0 && importeTotal != null
									&& importeTotal.compareTo(BigDecimal.ZERO) != 0) {
							    baseImponible = importeTotal.divide(conditionRateValue, 2, RoundingMode.HALF_UP).multiply(new BigDecimal(100));
							}

							data += "|DES#" + si.getPurchaseOrderByCustomer() + "#" + si.getPurchaseOrderByCustomer() + "#" + linea + "#" + conditionRateValue
									+ "#" + baseImponible + "#" + importeTotal;
						}
					}

					linea = 0;
					for (SolIndItemPricing s : descuentoZRDT) {
						if (s.getConditionRateValue().compareTo(new BigDecimal(0)) == -1) {
							if (linea == 0) {
								linea++;
							} else {
								if (conditionRateValue.compareTo(s.getConditionRateValue()) == 0) {
									linea++;
								} else {
									linea = 1;
								}
							}

							conditionRateValue = s.getConditionRateValue();

							BigDecimal importeTotal = mapaSumaConditionAmountZRDT.get(conditionRateValue);

							BigDecimal baseImponible = BigDecimal.ZERO;
							if (conditionRateValue != null && conditionRateValue.compareTo(BigDecimal.ZERO) != 0 && importeTotal != null
									&& importeTotal.compareTo(BigDecimal.ZERO) != 0) {
							    baseImponible = importeTotal.divide(conditionRateValue, 2, RoundingMode.HALF_UP).multiply(new BigDecimal(100));
							}

							data += "|DES#" + si.getPurchaseOrderByCustomer() + "#" + si.getPurchaseOrderByCustomer() + "#" + linea + "#" + conditionRateValue
									+ "#" + baseImponible + "#" + importeTotal;
						}
					}

					linea = 0;
					for (SolIndItemPricing s : descuentoZRD1) {
						if (s.getConditionRateValue().compareTo(new BigDecimal(0)) == -1) {
							if (linea == 0) {
								linea++;
							} else {
								if (conditionRateValue.compareTo(s.getConditionRateValue()) == 0) {
									linea++;
								} else {
									linea = 1;
								}
							}

							conditionRateValue = s.getConditionRateValue();

							BigDecimal importeTotal = mapaSumaConditionAmountZRD1.get(conditionRateValue);

							BigDecimal baseImponible = BigDecimal.ZERO;
							if (conditionRateValue != null && conditionRateValue.compareTo(BigDecimal.ZERO) != 0 && importeTotal != null
									&& importeTotal.compareTo(BigDecimal.ZERO) != 0) {
							    baseImponible = importeTotal.divide(conditionRateValue, 2, RoundingMode.HALF_UP).multiply(new BigDecimal(100));
							}

							data += "|DES#" + si.getPurchaseOrderByCustomer() + "#" + si.getPurchaseOrderByCustomer() + "#" + linea + "#" + conditionRateValue
									+ "#" + baseImponible + "#" + importeTotal;
						}
					}

					linea = 0;
					for (SolIndItemPricing s : descuentoZRDF) {
						if (s.getConditionRateValue().compareTo(new BigDecimal(0)) == -1) {
							if (linea == 0) {
								linea++;
							} else {
								if (conditionRateValue.compareTo(s.getConditionRateValue()) == 0) {
									linea++;
								} else {
									linea = 1;
								}
							}

							conditionRateValue = s.getConditionRateValue();

							BigDecimal importeTotal = mapaSumaConditionAmountZRDF.get(conditionRateValue);

							BigDecimal baseImponible = BigDecimal.ZERO;
							if (conditionRateValue != null && conditionRateValue.compareTo(BigDecimal.ZERO) != 0 && importeTotal != null
									&& importeTotal.compareTo(BigDecimal.ZERO) != 0) {
							    baseImponible = importeTotal.divide(conditionRateValue, 2, RoundingMode.HALF_UP).multiply(new BigDecimal(100));
							}

							data += "|DES#" + si.getPurchaseOrderByCustomer() + "#" + si.getPurchaseOrderByCustomer() + "#" + linea + "#" + conditionRateValue
									+ "#" + baseImponible + "#" + importeTotal;
						}
					}

					linea = 0;
					for (SolIndItemPricing s : descuentoZDPR) {
						if (s.getConditionRateValue().compareTo(new BigDecimal(0)) == -1) {
							if (linea == 0) {
								linea++;
							} else {
								if (conditionRateValue.compareTo(s.getConditionRateValue()) == 0) {
									linea++;
								} else {
									linea = 1;
								}
							}

							conditionRateValue = s.getConditionRateValue();

							BigDecimal importeTotal = mapaSumaConditionAmountZDPR.get(conditionRateValue);

							BigDecimal baseImponible = BigDecimal.ZERO;
							if (conditionRateValue != null && conditionRateValue.compareTo(BigDecimal.ZERO) != 0 && importeTotal != null
									&& importeTotal.compareTo(BigDecimal.ZERO) != 0) {
							    baseImponible = importeTotal.divide(conditionRateValue, 2, RoundingMode.HALF_UP).multiply(new BigDecimal(100));
							}

							data += "|DES#" + si.getPurchaseOrderByCustomer() + "#" + si.getPurchaseOrderByCustomer() + "#" + linea + "#" + conditionRateValue
									+ "#" + baseImponible + "#" + importeTotal;
						}
					}

					linea = 0;
					for (SolIndItemPricing s : descuentoZDPS) {
						if (linea == 0) {
							linea++;
						} else {
							if (conditionRateValue.compareTo(s.getConditionRateValue()) == 0) {
								linea++;
							} else {
								linea = 1;
							}
						}

						conditionRateValue = s.getConditionRateValue();

						data += "|DES#" + si.getPurchaseOrderByCustomer() + "#" + si.getPurchaseOrderByCustomer() + "#" + linea + "#" + 0 + "#" + 0 + "#"
								+ conditionRateValue;
					}
				}

				// RECARGOS
				for (SolIndItems prueba : si.getItems()) {
					List<SolIndItemPricing> descuentoZRDC = new ArrayList<SolIndItemPricing>();
					List<SolIndItemPricing> descuentoZRDT = new ArrayList<SolIndItemPricing>();
					List<SolIndItemPricing> descuentoZRD1 = new ArrayList<SolIndItemPricing>();
					List<SolIndItemPricing> descuentoZRDF = new ArrayList<SolIndItemPricing>();
					List<SolIndItemPricing> descuentoZDPR = new ArrayList<SolIndItemPricing>();

					for (SolIndItemPricing p : prueba.getPrices()) {
						if ("ZRDC".equals(p.getConditionType())) {
							descuentoZRDC.add(p);
						} else if ("ZRDT".equals(p.getConditionType())) {
							descuentoZRDT.add(p);
						} else if ("ZRD1".equals(p.getConditionType())) {
							descuentoZRD1.add(p);
						} else if ("ZRDF".equals(p.getConditionType())) {
							descuentoZRDF.add(p);
						} else if ("ZDPR".equals(p.getConditionType())) {
							descuentoZDPR.add(p);
						}
					}
					
					Map<BigDecimal, BigDecimal> mapaSumaConditionAmountZRDC = new HashMap<BigDecimal, BigDecimal>();
					for (SolIndItemPricing s : descuentoZRDC) {
						if (s.getConditionRateValue().compareTo(new BigDecimal(0)) == 1) {
							if (mapaSumaConditionAmountZRDC.containsKey(s.getConditionRateValue())) {
								mapaSumaConditionAmountZRDC.put(s.getConditionRateValue(),
										mapaSumaConditionAmountZRDC.get(s.getConditionRateValue()).add(s.getConditionAmount()));
							} else {
								mapaSumaConditionAmountZRDC.put(s.getConditionRateValue(), s.getConditionAmount());
							}
						}
					}

					Map<BigDecimal, BigDecimal> mapaSumaConditionAmountZRDT = new HashMap<BigDecimal, BigDecimal>();
					for (SolIndItemPricing s : descuentoZRDT) {
						if (s.getConditionRateValue().compareTo(new BigDecimal(0)) == 1) {
							if (mapaSumaConditionAmountZRDT.containsKey(s.getConditionRateValue())) {
								mapaSumaConditionAmountZRDT.put(s.getConditionRateValue(),
										mapaSumaConditionAmountZRDT.get(s.getConditionRateValue()).add(s.getConditionAmount()));
							} else {
								mapaSumaConditionAmountZRDT.put(s.getConditionRateValue(), s.getConditionAmount());
							}
						}
					}

					Map<BigDecimal, BigDecimal> mapaSumaConditionAmountZRD1 = new HashMap<BigDecimal, BigDecimal>();
					for (SolIndItemPricing s : descuentoZRD1) {
						if (s.getConditionRateValue().compareTo(new BigDecimal(0)) == 1) {
							if (mapaSumaConditionAmountZRD1.containsKey(s.getConditionRateValue())) {
								mapaSumaConditionAmountZRD1.put(s.getConditionRateValue(),
										mapaSumaConditionAmountZRD1.get(s.getConditionRateValue()).add(s.getConditionAmount()));
							} else {
								mapaSumaConditionAmountZRD1.put(s.getConditionRateValue(), s.getConditionAmount());
							}
						}
					}

					Map<BigDecimal, BigDecimal> mapaSumaConditionAmountZRDF = new HashMap<BigDecimal, BigDecimal>();
					for (SolIndItemPricing s : descuentoZRDF) {
						if (s.getConditionRateValue().compareTo(new BigDecimal(0)) == 1) {
							if (mapaSumaConditionAmountZRDF.containsKey(s.getConditionRateValue())) {
								mapaSumaConditionAmountZRDF.put(s.getConditionRateValue(),
										mapaSumaConditionAmountZRDF.get(s.getConditionRateValue()).add(s.getConditionAmount()));
							} else {
								mapaSumaConditionAmountZRDF.put(s.getConditionRateValue(), s.getConditionAmount());
							}
						}
					}

					Map<BigDecimal, BigDecimal> mapaSumaConditionAmountZDPR = new HashMap<BigDecimal, BigDecimal>();
					for (SolIndItemPricing s : descuentoZDPR) {
						if (s.getConditionRateValue().compareTo(new BigDecimal(0)) == 1) {
							if (mapaSumaConditionAmountZDPR.containsKey(s.getConditionRateValue())) {
								mapaSumaConditionAmountZDPR.put(s.getConditionRateValue(),
										mapaSumaConditionAmountZDPR.get(s.getConditionRateValue()).add(s.getConditionAmount()));
							} else {
								mapaSumaConditionAmountZDPR.put(s.getConditionRateValue(), s.getConditionAmount());
							}
						}
					}

					BigDecimal conditionRateValue = null;
					int linea = 0;
					for (SolIndItemPricing s : descuentoZRDC) {
						if (s.getConditionRateValue().compareTo(new BigDecimal(0)) == 1) {
							if (linea == 0) {
								linea++;
							} else {
								if (conditionRateValue.compareTo(s.getConditionRateValue()) == 0) {
									linea++;
								} else {
									linea = 1;
								}
							}

							conditionRateValue = s.getConditionRateValue();

							BigDecimal importeTotal = mapaSumaConditionAmountZRDC.get(conditionRateValue);

							BigDecimal baseImponible = BigDecimal.ZERO;
							if (conditionRateValue != null && conditionRateValue.compareTo(BigDecimal.ZERO) != 0 && importeTotal != null
									&& importeTotal.compareTo(BigDecimal.ZERO) != 0) {
							    baseImponible = importeTotal.divide(conditionRateValue, 2, RoundingMode.HALF_UP).multiply(new BigDecimal(100));
							}

							data += "|REC#" + si.getPurchaseOrderByCustomer() + "#" + si.getPurchaseOrderByCustomer() + "#" + linea + "#" + conditionRateValue
									+ "#" + baseImponible + "#" + importeTotal;
						}
					}

					linea = 0;
					for (SolIndItemPricing s : descuentoZRDT) {
						if (s.getConditionRateValue().compareTo(new BigDecimal(0)) == 1) {
							if (linea == 0) {
								linea++;
							} else {
								if (conditionRateValue.compareTo(s.getConditionRateValue()) == 0) {
									linea++;
								} else {
									linea = 1;
								}
							}

							conditionRateValue = s.getConditionRateValue();

							BigDecimal importeTotal = mapaSumaConditionAmountZRDT.get(conditionRateValue);

							BigDecimal baseImponible = BigDecimal.ZERO;
							if (conditionRateValue != null && conditionRateValue.compareTo(BigDecimal.ZERO) != 0 && importeTotal != null
									&& importeTotal.compareTo(BigDecimal.ZERO) != 0) {
							    baseImponible = importeTotal.divide(conditionRateValue, 2, RoundingMode.HALF_UP).multiply(new BigDecimal(100));
							}

							data += "|REC#" + si.getPurchaseOrderByCustomer() + "#" + si.getPurchaseOrderByCustomer() + "#" + linea + "#" + conditionRateValue
									+ "#" + baseImponible + "#" + importeTotal;
						}
					}

					linea = 0;
					for (SolIndItemPricing s : descuentoZRD1) {
						if (s.getConditionRateValue().compareTo(new BigDecimal(0)) == 1) {
							if (linea == 0) {
								linea++;
							} else {
								if (conditionRateValue.compareTo(s.getConditionRateValue()) == 0) {
									linea++;
								} else {
									linea = 1;
								}
							}

							conditionRateValue = s.getConditionRateValue();

							BigDecimal importeTotal = mapaSumaConditionAmountZRD1.get(conditionRateValue);

							BigDecimal baseImponible = BigDecimal.ZERO;
							if (conditionRateValue != null && conditionRateValue.compareTo(BigDecimal.ZERO) != 0 && importeTotal != null
									&& importeTotal.compareTo(BigDecimal.ZERO) != 0) {
							    baseImponible = importeTotal.divide(conditionRateValue, 2, RoundingMode.HALF_UP).multiply(new BigDecimal(100));
							}

							data += "|REC#" + si.getPurchaseOrderByCustomer() + "#" + si.getPurchaseOrderByCustomer() + "#" + linea + "#" + conditionRateValue
									+ "#" + baseImponible + "#" + importeTotal;
						}
					}

					linea = 0;
					for (SolIndItemPricing s : descuentoZRDF) {
						if (s.getConditionRateValue().compareTo(new BigDecimal(0)) == 1) {
							if (linea == 0) {
								linea++;
							} else {
								if (conditionRateValue.compareTo(s.getConditionRateValue()) == 0) {
									linea++;
								} else {
									linea = 1;
								}
							}

							conditionRateValue = s.getConditionRateValue();

							BigDecimal importeTotal = mapaSumaConditionAmountZRDF.get(conditionRateValue);

							BigDecimal baseImponible = BigDecimal.ZERO;
							if (conditionRateValue != null && conditionRateValue.compareTo(BigDecimal.ZERO) != 0 && importeTotal != null
									&& importeTotal.compareTo(BigDecimal.ZERO) != 0) {
							    baseImponible = importeTotal.divide(conditionRateValue, 2, RoundingMode.HALF_UP).multiply(new BigDecimal(100));
							}

							data += "|REC#" + si.getPurchaseOrderByCustomer() + "#" + si.getPurchaseOrderByCustomer() + "#" + linea + "#" + conditionRateValue
									+ "#" + baseImponible + "#" + importeTotal;
						}
					}

					linea = 0;
					for (SolIndItemPricing s : descuentoZDPR) {
						if (s.getConditionRateValue().compareTo(new BigDecimal(0)) == 1) {
							if (linea == 0) {
								linea++;
							} else {
								if (conditionRateValue.compareTo(s.getConditionRateValue()) == 0) {
									linea++;
								} else {
									linea = 1;
								}
							}

							conditionRateValue = s.getConditionRateValue();

							BigDecimal importeTotal = mapaSumaConditionAmountZDPR.get(conditionRateValue);

							BigDecimal baseImponible = BigDecimal.ZERO;
							if (conditionRateValue != null && conditionRateValue.compareTo(BigDecimal.ZERO) != 0 && importeTotal != null
									&& importeTotal.compareTo(BigDecimal.ZERO) != 0) {
							    baseImponible = importeTotal.divide(conditionRateValue, 2, RoundingMode.HALF_UP).multiply(new BigDecimal(100));
							}

							data += "|REC#" + si.getPurchaseOrderByCustomer() + "#" + si.getPurchaseOrderByCustomer() + "#" + linea + "#" + conditionRateValue
									+ "#" + baseImponible + "#" + importeTotal;
						}
					}
				}

				// PUNTOS
				for (SolIndItems prueba : si.getItems()) {
					List<SolIndItemPricing> puntos = new ArrayList<SolIndItemPricing>();

					for (SolIndItemPricing p : prueba.getPrices()) {
						if (ConstFacturacion.CONDICION_PRECIO_BRUTO.equals(p.getConditionType())
								&& ConstFacturacion.CONDITION_CURRENCY_PUNTOS.equals(p.getConditionCurrency())) {
							puntos.add(p);
						}
					}
					
					int linea = 0;
					for (SolIndItemPricing s : puntos) {
						linea++;

						BigDecimal precioUnitarioPuntos = BigDecimal.ZERO;
						if (s.getConditionRateValue() != null && s.getConditionRateValue().compareTo(BigDecimal.ZERO) != 0
								&& prueba.getRequestedQuantity() != null && prueba.getRequestedQuantity().compareTo(BigDecimal.ZERO) != 0) {
							precioUnitarioPuntos = s.getConditionRateValue().divide(prueba.getRequestedQuantity(), 2, RoundingMode.HALF_UP);
						}

						BigDecimal precioUnitarioEuros = BigDecimal.ZERO;
						if (s.getConditionAmount() != null && s.getConditionAmount().compareTo(BigDecimal.ZERO) != 0 && precioUnitarioPuntos != null
								&& precioUnitarioPuntos.compareTo(BigDecimal.ZERO) != 0) {
							precioUnitarioEuros = s.getConditionAmount().multiply(precioUnitarioPuntos);
						}

						data += "|ZPT#" + si.getPurchaseOrderByCustomer() + "#" + si.getPurchaseOrderByCustomer() + "#" + linea + "#" + s.getConditionAmount()
								+ "#" + precioUnitarioPuntos + "#" + precioUnitarioEuros;
					}
				}

				// TARIFAS
				String priceReferenceMaterial = null;
				int linea = 0;
				for (SolIndItems prueba : si.getItems()) {
					if (linea == 0) {
						linea++;
					} else {
						if (priceReferenceMaterial != null && priceReferenceMaterial.equals(prueba.getPriceReferenceMaterial())) {
							linea++;
						} else {
							linea = 1;
						}
					}

					if(prueba.getPriceReferenceMaterial() != null)
						priceReferenceMaterial = prueba.getPriceReferenceMaterial();

					data += "|TAR#" + si.getPurchaseOrderByCustomer() + "#" + si.getPurchaseOrderByCustomer() + "#" + linea + "#" + priceReferenceMaterial + "#"
							+ si.getTarifa() + "#" + sdf.format(si.getSalesOrderDate()) + "#" + "99991231";
				}

				// FIN
				data += "|FIN#" + si.getPurchaseOrderByCustomer() + "#" + si.getPurchaseOrderByCustomer();

				request.setMethodName(z.getZZMETODO());
				request.setMethodData(data);

				this.enviarPeticionValorada(request);
			}
		} catch (Exception e) {
			log.error("TrakEnvioServiceImpl - enviarPeticionValorada - ERROR: "+e.getMessage(), e);
		}
		log.info("TrakEnvioServiceImpl - enviarPeticionValorada - FIN - "+si.getPurchaseOrderByCustomer());
	}

	@Override
	@NewSpan2
	public void enviarPeticionValorada(RequestMethod request) throws Exception {
		HttpClient httpClient = destinations.getHttpClient(TRAK.getValue());
		String requestBody = convertToJson(request);
		log.info("TrakEnvioServiceImpl - enviarPeticionValorada - REQUEST: " + requestBody);
		StringEntity stringEntity = new StringEntity(requestBody);
		HttpPost httpPost = new HttpPost(ENVIO_PETICION_URI);
		httpPost.addHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE);
		httpPost.setEntity(stringEntity);
		HttpResponse response = httpClient.execute(httpPost);
		String responseBody = EntityUtils.toString(response.getEntity());
		int statusCode = response.getStatusLine().getStatusCode();
		log.info("TrakEnvioServiceImpl - enviarPeticionValorada - RESPONSE: " + responseBody);

		if (statusCode == 502)
			throw new Exception("STATUSCODE 502: " + responseBody);
	}

	private String convertToJson(Object object) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.writeValueAsString(object);
		} catch (JsonProcessingException e) {
			log.error("Errror al convertir el objeto en JSON (convertToJson)", e);
			return "";
		}
	}
}
