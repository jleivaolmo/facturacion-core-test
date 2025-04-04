package com.echevarne.sap.cloud.facturacion.transformacion.rules;

import static com.echevarne.sap.cloud.facturacion.util.StringUtils.isNullOrEmpty;
import static java.util.Arrays.asList;
import static org.apache.commons.lang3.StringUtils.leftPad;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;
import com.echevarne.sap.cloud.facturacion.constants.ConstEstados;
import com.echevarne.sap.cloud.facturacion.constants.ConstFacturacion;
import com.echevarne.sap.cloud.facturacion.gestionestados.Excluida;
import com.echevarne.sap.cloud.facturacion.model.PrecioEspecial;
import com.echevarne.sap.cloud.facturacion.model.replicated.S4Info;
import com.echevarne.sap.cloud.facturacion.model.replicated.S4Material;
import com.echevarne.sap.cloud.facturacion.model.replicated.S4MaterialSales;
import com.echevarne.sap.cloud.facturacion.model.replicated.S4SalesOfficeRegion;
import com.echevarne.sap.cloud.facturacion.model.solicitudindividual.SolIndItemPricing;
import com.echevarne.sap.cloud.facturacion.model.solicitudindividual.SolIndItems;
import com.echevarne.sap.cloud.facturacion.model.solicitudindividual.SolIndPartner;
import com.echevarne.sap.cloud.facturacion.model.solicitudindividual.SolicitudIndividual;
import com.echevarne.sap.cloud.facturacion.model.solicitudrecibida.PetMuesInterlocutores;
import com.echevarne.sap.cloud.facturacion.model.solicitudrecibida.PeticionMuestreo;
import com.echevarne.sap.cloud.facturacion.model.solicitudrecibida.PeticionMuestreoItems;
import com.echevarne.sap.cloud.facturacion.model.views.PruebasIncongruentesView;
import com.echevarne.sap.cloud.facturacion.repositories.ConversionCentroRep;
import com.echevarne.sap.cloud.facturacion.repositories.ConversionSectorRep;
import com.echevarne.sap.cloud.facturacion.repositories.GruposSectoresRepository;
import com.echevarne.sap.cloud.facturacion.repositories.PrecioEspecialRep;
import com.echevarne.sap.cloud.facturacion.repositories.S4MaterialsRep;
import com.echevarne.sap.cloud.facturacion.repositories.replicated.S4CustomersRepository;
import com.echevarne.sap.cloud.facturacion.repositories.replicated.S4MaterialSalesRepository;
import com.echevarne.sap.cloud.facturacion.repositories.replicated.S4SalesOfficeRegionRepository;
import com.echevarne.sap.cloud.facturacion.repositories.views.PruebasIncongruentesRep;
import com.echevarne.sap.cloud.facturacion.services.S4InfoService;
import com.echevarne.sap.cloud.facturacion.util.InterlocutoresUtil;
import com.echevarne.sap.cloud.facturacion.validations.ValidationResult;
import com.echevarne.sap.cloud.facturacion.validations.commons.ValidacionesPetMues;
import com.echevarne.sap.cloud.facturacion.validations.commons.ValidacionesSolIndItem;
import lombok.AllArgsConstructor;
import lombok.var;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

/**
 * Implementacion de metodos a ejecutar por CALL.
 */
@Slf4j
@Component
@AllArgsConstructor
public class TransformerFunctions {

	public static final ZonedDateTime END_OF_TIME = ZonedDateTime.parse("9999-12-31T00:00:00+01:00[Europe/Paris]");
	public static final String ANY = "*";

	private final ConversionCentroRep conversionCentroRep;
	private final ConversionSectorRep conversionSectorRep;
	private final GruposSectoresRepository gruposSectoresRep;
	private final PrecioEspecialRep precioEspecialRep;
	private final PruebasIncongruentesRep pruebasIncongruentesRep;
	private final S4CustomersRepository s4CustomersRepository;
	private final S4InfoService s4InfoService;
	private final S4MaterialsRep s4MaterialsRep;
	private final S4MaterialSalesRepository s4MaterialSalesRepository;
	private final S4SalesOfficeRegionRepository s4SalesOfficeRegionRep;

	private final SalesOrganizationUtil salesOrganizationUtil;
	private final ValidacionesPetMues validacionesPetMues;
	private final ValidacionesSolIndItem validacionesSolIndItem;

    public enum TipoPeticion {
        AMBULATORIO(1), INGRESADOS(2), EMPRESA(3), EST_CLINICOS(4), INDUSTRIA(5), VETERINARIA(6);

        private final int value;

        private TipoPeticion(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

	/**
	 * Validate BusinnesPartner.Customer by CIF/NIF
	 * <p>
	 * method referenced by a tranformation rule
	 */
	public String getSAPCustomerID(ReflectionUtil ru) {
		String vCodigoCliente = null;
		try {
			var pm = (PeticionMuestreo) ru.get("pm");
			var vNifCliente = pm.getNifCliente();
			String codigoCliente = pm.getCodigoCliente();

			if (!isNullOrEmpty(codigoCliente)) {
				if (hasOnlyDigits(codigoCliente))
					codigoCliente = leftPad(codigoCliente, 10, "0");
				var s4CustomersOpt = s4CustomersRepository.findById(codigoCliente);
				if (s4CustomersOpt.isPresent())
					vCodigoCliente = codigoCliente;
			} else if (!isNullOrEmpty(vNifCliente)) {
				var s4CustomersOpt = s4CustomersRepository.findBySTCD1(vNifCliente);
				if (s4CustomersOpt.isPresent())
					vCodigoCliente = s4CustomersOpt.get().getKUNNR();
				
				if (StringUtils.isBlank(vCodigoCliente)) {
					s4CustomersOpt = s4CustomersRepository.findBySTCD2(vNifCliente);
					if (s4CustomersOpt.isPresent())
						vCodigoCliente = s4CustomersOpt.get().getKUNNR();
				}
				
				if (StringUtils.isBlank(vCodigoCliente)) {
					s4CustomersOpt = s4CustomersRepository.findBySTCD3(vNifCliente);
					if (s4CustomersOpt.isPresent())
						vCodigoCliente = s4CustomersOpt.get().getKUNNR();
				}
				
				if (StringUtils.isBlank(vCodigoCliente)) {
					s4CustomersOpt = s4CustomersRepository.findBySTCD4(vNifCliente);
					if (s4CustomersOpt.isPresent())
						vCodigoCliente = s4CustomersOpt.get().getKUNNR();
				}
				
				if (StringUtils.isBlank(vCodigoCliente)) {
					s4CustomersOpt = s4CustomersRepository.findBySTCD5(vNifCliente);
					if (s4CustomersOpt.isPresent())
						vCodigoCliente = s4CustomersOpt.get().getKUNNR();
				}
				
				if (StringUtils.isBlank(vCodigoCliente)) {
					s4CustomersOpt = s4CustomersRepository.findBySTCEG(vNifCliente);
					if (s4CustomersOpt.isPresent())
						vCodigoCliente = s4CustomersOpt.get().getKUNNR();
				}
			}
		} catch (Exception e) {
			if(log.isDebugEnabled()){
				log.debug("No se ha podido determinar el soldToParty - Transformacion", e);
			}
		}
		return vCodigoCliente;
	}

	private boolean hasOnlyDigits(String codigoCliente) {
		return codigoCliente.matches("[0-9]+");
	}

	public String createCustomerKey(ReflectionUtil ru) throws Exception {
		PetMuesInterlocutores ic = (PetMuesInterlocutores) ru.get("ic");
		if (ic == null) {
			ic = (PetMuesInterlocutores) ru.get("sm_interlocutor");
		}
		return InterlocutoresUtil.createCustomerKey(ic.getCodigoInterlocutor(), ic.getRolInterlocutor());
	}

	public static ZonedDateTime currentDate(ReflectionUtil ru) {
		return ZonedDateTime.now();
	}

	public static ZonedDateTime endOfTimeDate(ReflectionUtil ru) {
		return END_OF_TIME;
	}

	public void createPresupuestoPricing(ReflectionUtil ru) {
		try {
			SolicitudIndividual si = (SolicitudIndividual) ru.get("si");
			PeticionMuestreo pm = (PeticionMuestreo) ru.get("pm");
			SolIndItems siItem = (SolIndItems) ru.get("si_items");

			String pagador = si.getSoldToParty();
			String cliente = pm.getCodigoInterlocutorByRol("ZC");
			String concepto = siItem.getPriceReferenceMaterial();//pmItem.getCodigoMaterialFacturacion();
			PrecioEspecial precioEspecial = precioEspecialRep.findByPagadorAndClienteAndConcepto(pagador, cliente,
					concepto);
			if (precioEspecial != null) {
				SolIndItemPricing pricing = new SolIndItemPricing();
				pricing.setConditionType(ConstFacturacion.CONDICION_PRECIO_MODIFICADO);
				pricing.setConditionCurrency(ConstFacturacion.CONDITION_CURRENCY_EUROS);
				pricing.setConditionRateValue(precioEspecial.getValorNeto());
				pricing.setConditionQuantity(new BigDecimal(1));
				pricing.setPosicion(siItem);
				Set<SolIndItemPricing> prices = siItem.getPrices();
				if (prices == null) {
					prices = new HashSet<>();
					siItem.setPrices(prices);
				}
				prices.add(pricing);
			}
		} catch (Exception e) {
			if(log.isDebugEnabled()){
				log.debug("No se ha podido añadir el pricing al presupuesto - Transformacion", e);
			}
		}
	}

	/**
	 *
	 * Convierte la oficina de ventas
	 */
	public String getSalesOffice(ReflectionUtil ru) {
		try {
			var pm = (PeticionMuestreo) ru.get("pm");
			var salesOffice = pm.getSolicitud().getCodigoOficinaVentas().trim();
			if (salesOffice.length() < 4)
				salesOffice = leftPad(salesOffice, 4, "0"); // completa con ceros
			return salesOffice;
		} catch (Exception e) {
			if(log.isDebugEnabled()){
				log.debug("No se ha podido establecer la oficina de ventas - Transformacion", e);
			}
		}
		return null;
	}

	/**
	 *
	 * Convierte la delegación productiva
	 */
	public String getDelegacionProductiva(ReflectionUtil ru) {
		try {
			var pm_items = (PeticionMuestreoItems) ru.get("sm_items");
			String delProductiva = pm_items.getDelegacionProductiva();
			if(delProductiva==null) return null;
			var delegacionProductiva = delProductiva.trim();
			if (delegacionProductiva.length() < 4)
				delegacionProductiva = leftPad(delegacionProductiva, 4, "0"); // completa con ceros
			return delegacionProductiva;
		} catch (Exception e) {
			if(log.isDebugEnabled()){
				log.info("No se ha podido establecer la delegación productiva - Transformacion");
			}
		}
		return null;
	}

	/**
	 *
	 * Convierte el sector
	 */
	public String getOrganizationDivision(ReflectionUtil ru) {
		try {
			PeticionMuestreo pm = (PeticionMuestreo) ru.get("pm");
			int tipoPeticion = pm.getTipoPeticion();
			var sector = conversionSectorRep.findByTipoPeticion(tipoPeticion);
			if (sector.isPresent())
				return sector.get().getSector();
		} catch (Exception e) {
			if(log.isDebugEnabled()){
				log.debug("No se ha podido añadir el sector de ventas - Transformacion", e);
			}
		}
		return null;
	}

	public String getSalesOrganization(ReflectionUtil ru) throws Exception {
		PeticionMuestreo pm = (PeticionMuestreo) ru.get("pm");
		SolicitudIndividual si = (SolicitudIndividual) ru.get("si");
		return salesOrganizationUtil.getSalesOrganization(si, pm);
	}

	public void getPruebaIncongruente(ReflectionUtil ru) {
		try {
			PeticionMuestreoItems pmi = (PeticionMuestreoItems) ru.get("sm_items");
			SolIndItems sii = (SolIndItems) ru.get("si_items");
			PeticionMuestreo pm = pmi.getPeticion();
			SolicitudIndividual si = sii.getSolicitudInd();
			String cliente = si.getSoldToParty();
			String compania = si.getPartners().stream()
					.filter(p -> ConstFacturacion.ROL_INTERLOCUTOR_COMPANIA.equals(p.getPartnerFunction()))
					.findFirst().map(SolIndPartner::getCustomer).orElse(null);
			var clientes = asList(cliente, ANY);
			var companias = asList(compania, ANY);
			List<PruebasIncongruentesView> listPruebasInc = pruebasIncongruentesRep
					.findByCodigoClienteInAndCodigoCompaniaIn(clientes, companias);
			List<PruebasIncongruentesView> listPruebasIncSorted = listPruebasInc.stream()
					.sorted(Comparator.comparingInt(PruebasIncongruentesView::getValorPrioridad))
					.collect(Collectors.toList());
			List<String> listaRechazables = listPruebasIncSorted.stream().map(PruebasIncongruentesView::getMaterialRechazable)
					.collect(Collectors.toList());
			if (listaRechazables.contains(sii.getMaterial())) {
				List<String> materialesProvocantes = listPruebasIncSorted.stream().filter(p -> p.getMaterialRechazable().equals(sii.getMaterial()))
						.map(PruebasIncongruentesView::getMaterialProvocante).collect(Collectors.toList());
				if (pm.getPruebas().stream().anyMatch(e -> materialesProvocantes.contains(e.getCodigoMaterial())))
					sii.setSalesOrderItemCategory(ConstFacturacion.TIPO_POSICION_INCONGRUENTE);
			}
		} catch (Exception e) {
			if(log.isDebugEnabled()){
				log.debug("No se ha podido establecer la prueba incongruente - Transformacion", e);
			}
		}
	}

	public void getPruebaExcluida(ReflectionUtil ru) throws Exception {
		try {
			PeticionMuestreoItems pmi = (PeticionMuestreoItems) ru.get("sm_items");
			SolIndItems sii = (SolIndItems) ru.get("si_items");
			var optUltimoEstado = pmi.getTrazabilidad().getLastEstado();
			if (optUltimoEstado.isPresent()) {
				var ultimoEstado = optUltimoEstado.get();
				if (ultimoEstado.getEstado().getCodeEstado().equals(Excluida.CODIGO) && ultimoEstado.getMotivo() != null
						&& ultimoEstado.getMotivo().getCodigo().equals(ConstEstados.MOTIVO_ESTADO_EXMA)) {
					sii.setSalesOrderItemCategory(ConstFacturacion.TIPO_POSICION_EXCLUIDA);
				}
			}
		} catch (Exception e) {
			if(log.isDebugEnabled()){
				log.debug("No se ha podido establecer la prueba excluida - Transformacion", e);
			}
		}
	}

	public void getPruebaNoFacturable(ReflectionUtil ru) throws Exception {
		try {
			PeticionMuestreoItems pmi = (PeticionMuestreoItems) ru.get("sm_items");
			if (pmi.getIdParent() > 0)
				return;
			SolIndItems sii = (SolIndItems) ru.get("si_items");
			SolicitudIndividual si = sii.getSolicitudInd();
			String vkorg = si.getSalesOrganization();
			String matnr = pmi.getCodigoMaterial();
			Optional<S4MaterialSales> s4MaterialsSalesOpt = s4MaterialSalesRepository.findByMATNRAndVKORGAndVTWEG(matnr, vkorg, ConstFacturacion.CANAL_DISTRIBUCION_COMUN);
			if(s4MaterialsSalesOpt.isPresent() && ConstFacturacion.S4_POSICION_NO_FACTURABLE.equals(s4MaterialsSalesOpt.get().getMTPOS())) {
				sii.setSalesOrderItemCategory(ConstFacturacion.TIPO_POSICION_NO_FACTURABLE);
			}
		} catch (Exception e) {
			if(log.isDebugEnabled()){
				log.debug("No se ha podido establecer la prueba excluida - Transformacion");
			}
		}
	}

	public void getBloqueoCortesia(ReflectionUtil ru) {
		try {
			SolIndItems sii = (SolIndItems) ru.get("si_items");
            var aplicaBloqueoCortesia = validacionesSolIndItem.aplicaBloqueoCortesia(sii).isValid();
			var listaTiposNoAceptados = Arrays.asList(ConstFacturacion.TIPO_POSICION_NO_FACTURABLE,
			ConstFacturacion.TIPO_POSICION_EXCLUIDA, ConstFacturacion.TIPO_POSICION_INCONGRUENTE,
			ConstFacturacion.TIPO_POSICION_BLOQUEO_RECHAZO);
			if (aplicaBloqueoCortesia && !listaTiposNoAceptados.contains(sii.getSalesOrderItemCategory())) {
				sii.setSalesOrderItemCategory(ConstFacturacion.TIPO_POSICION_BLOQUEO_CORTESIA);
			}
		} catch (Exception e) {
			if(log.isDebugEnabled()){
				log.debug("No se ha podido establecer bloqueo de cortesia - Transformacion");
			}
		}
	}

	public String getItemPlant(ReflectionUtil ru) {

		try {
			PeticionMuestreoItems pmItem = (PeticionMuestreoItems) ru.get("sm_items");

			var delegacionProductiva = pmItem.getDelegacionProductiva().trim();
			if (delegacionProductiva.length() < 4)
				delegacionProductiva = leftPad(delegacionProductiva, 4, "0"); // completa con ceros

			var result = conversionCentroRep.findByDelegacionProductiva(delegacionProductiva);
			if (result.isPresent()) {
				return result.get().getCentro();
			}

		} catch (Exception e) {
			if(log.isDebugEnabled()){
				log.debug("No se ha podido establecer el centro de la prueba - Transformacion");
			}
		}

		return null;
	}

	public String getUnidadProductiva(ReflectionUtil ru) {

		try {
			SolicitudIndividual si 		= (SolicitudIndividual) ru.get("si");
			SolIndItems si_item 		= (SolIndItems) ru.get("si_items");

			String matnr = si_item.getMaterial();
			String vkorg = si.getSalesOrganization();
			var s4MaterialsSalesOpt = s4MaterialSalesRepository.findByMATNRAndVKORGAndVTWEG(matnr, vkorg, ConstFacturacion.CANAL_DISTRIBUCION_COMUN);
			if (s4MaterialsSalesOpt.isPresent()) {
				return s4MaterialsSalesOpt.get().getMVGR3();
			}

		} catch (Exception e) {
			if(log.isDebugEnabled()){
				log.debug("No se ha podido establecer la unidad productiva - Transformacion", e);
			}
		}

		return null;

	}

	public String getItemOrganizationDivision(ReflectionUtil ru) {

		try {
			SolIndItems si_item	= (SolIndItems) ru.get("si_items");
			String matnr = si_item.getMaterial();
			var pm = (PeticionMuestreo) ru.get("pm");
			SolicitudIndividual si = (SolicitudIndividual) ru.get("si");

			// En el caso de Estudios Clínicos o Industria, el sector de prueba coincide con el de cabecera
			if (pm.getTipoPeticion() == TipoPeticion.EST_CLINICOS.getValue() || pm.getTipoPeticion() == TipoPeticion.INDUSTRIA.getValue()) {
				return si.getOrganizationDivision();
			}
			// En los otros casos...
			String sectorMaterial = getSectorS4Material(matnr);
			// Si la réplica no tiene sector, asignamos el sector de una de sus pruebas
			// Si no hay pruebas con sector, asignamos el sector de la cabecera en el sector de la prueba
			if (sectorMaterial == null || sectorMaterial.isEmpty()) {
				Optional<String> primerSectorOptional = pm.getPruebas()
						.stream()
						.map(PeticionMuestreoItems::getCodigoMaterial)
						.map(this::getSectorS4Material)
						.filter(sector -> sector != null && !sector.isEmpty())
						.findFirst();
				sectorMaterial = primerSectorOptional.orElseGet(si::getOrganizationDivision);
			}
			return sectorMaterial;
		} catch (Exception e) {
			if(log.isDebugEnabled()){
				log.debug("No se ha podido establecer el sector de la prueba - Transformacion", e);
			}
		}
		return null;

	}

	private Optional<S4Material> getS4Material(String matnr) {
		Optional<S4Material> s4MaterialsOpt = s4MaterialsRep.findByMATNR(matnr);
		if (!s4MaterialsOpt.isPresent()) {
			s4MaterialsOpt = s4MaterialsRep.findByMATNR(leftPad(matnr, 18, '0'));
		}
		return s4MaterialsOpt;
	}

	private String getSectorS4Material(String matnr) {
		return getS4Material(matnr).map(S4Material::getSPART).orElse(null);
	}

	public String getItemGrupoSector(ReflectionUtil ru) {

		try {
			SolicitudIndividual si = (SolicitudIndividual) ru.get("si");
			SolIndItems si_item	= (SolIndItems) ru.get("si_items");
			//El sector ya se ha calculado, si el orden de la ejecución está correctamente definido
			String sector = si_item.getOrganizationDivision();
			var grupoSectorOpt = gruposSectoresRep.findBySalesOrganizationAndSector(si.getSalesOrganization(), sector);
			if (grupoSectorOpt.isPresent()) {
				return grupoSectorOpt.get().getGrupo();
			}
		} catch (Exception e) {
			if(log.isDebugEnabled()){
				log.debug("No se ha podido establecer el grupo de sector de la prueba - Transformacion", e);
			}
		}
		return null;

	}

	/**
	 *
	 * Obtiene la fecha de validación de la prueba
	 */
	public static Date getFechaValidacion(ReflectionUtil ru) {

		try {
			var pm_items = (PeticionMuestreoItems) ru.get("sm_items");
			var fechaValidacion = pm_items.getFechaEstado("VA");
			if (fechaValidacion == null) {
				fechaValidacion = pm_items.getFechaEstado("AU");
			}
			if(fechaValidacion != null) return fechaValidacion;
		} catch (Exception e) {
			log.error("No se ha podido establecer la fecha de validación - Transformacion", e);
		}
		return null;

	}

	public boolean tratablePorPrivados(ReflectionUtil ru) {
		try {
			PeticionMuestreo pm 		= (PeticionMuestreo) ru.get("pm");
			ValidationResult tratablePrivados = validacionesPetMues.isTratablePrivados(pm);
			return tratablePrivados.isValid() || pm.isEsPrivado();
		} catch (Exception e) {
			if(log.isDebugEnabled()){
				log.debug("No se ha podido establecer si es tratable por privados - Transformacion", e);
			}
		}
		return false;
	}


	/**
	 *
	 * Obtiene la provincia según la delegacion
	 */
	public String getProvinciaDelegacion(ReflectionUtil ru) {
		try {
			var pm = (PeticionMuestreo) ru.get("pm");
			var salesOffice = pm.getSolicitud().getCodigoOficinaVentas().trim();
			if (salesOffice.length() < 4)
				salesOffice = leftPad(salesOffice, 4, "0"); // completa con ceros
			if(!isNullOrEmpty(salesOffice)) {
				Optional<S4SalesOfficeRegion> s4SalesOfficeRegion = s4SalesOfficeRegionRep.findById(salesOffice);
				if(s4SalesOfficeRegion.isPresent()) {
					return s4SalesOfficeRegion.get().getREGION();
				}
			}
		} catch (Exception e) {
			if(log.isDebugEnabled()){
				log.debug("No se ha podido establecer la provincia de la delegación - Transformacion", e);
			}
		}
		return null;

	}

	public void getRechazoPorMotivo(ReflectionUtil ru) throws Exception {
		try {
			PeticionMuestreoItems pmi = (PeticionMuestreoItems) ru.get("sm_items");
			SolIndItems sii = (SolIndItems) ru.get("si_items");
			if (Objects.nonNull(pmi.getMotivoRechazo()) && !pmi.getMotivoRechazo().isEmpty()) {
				sii.setSalesOrderItemCategory(ConstFacturacion.TIPO_POSICION_BLOQUEO_RECHAZO);
			}
		} catch (Exception e) {
			if(log.isDebugEnabled()){
				log.debug("No se ha podido establecer la prueba excluida - rechazo", e);
			}
		}
	}

	public String getCodigoBaremo(ReflectionUtil ru) {
		String response = null;
		try {
			SolicitudIndividual si = (SolicitudIndividual) ru.get("si");
			SolIndItems si_item = (SolIndItems) ru.get("si_items");
			Set<S4Info> listS4Info = s4InfoService.findByParams(si_item.getMaterial(), si.getSoldToParty(), si.getSalesOrganization(), si.getSalesOffice(),
					si.getInterlocutorCompania().map(x -> x.getCustomer()).orElse(""));
			response = listS4Info.stream().map(S4Info::getKDMAT).distinct().collect(Collectors.joining(";"));
		} catch (Exception e) {
			log.error("No se ha podido establecer el código baremo - Transformacion: " + e, e);
		}
		return response;
	}

}
