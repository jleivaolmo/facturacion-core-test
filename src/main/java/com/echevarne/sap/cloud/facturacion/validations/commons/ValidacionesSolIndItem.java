package com.echevarne.sap.cloud.facturacion.validations.commons;

import static java.util.Arrays.asList;
import static org.apache.commons.lang3.StringUtils.leftPad;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.echevarne.sap.cloud.facturacion.constants.ConstEstados;
import com.echevarne.sap.cloud.facturacion.constants.ConstFacturacion;
import com.echevarne.sap.cloud.facturacion.gestionestados.Bloqueada;
import com.echevarne.sap.cloud.facturacion.gestionestados.Excluida;
import com.echevarne.sap.cloud.facturacion.gestionestados.util.EstadosUtils;
import com.echevarne.sap.cloud.facturacion.model.MatConceptoFact;
import com.echevarne.sap.cloud.facturacion.model.determinaciones.BloqueoCortesia;
import com.echevarne.sap.cloud.facturacion.model.determinaciones.FacturableFechaPeticion;
import com.echevarne.sap.cloud.facturacion.model.masterdata.MasDataEstado;
import com.echevarne.sap.cloud.facturacion.model.masterdata.MasDataTipoPosicion;
import com.echevarne.sap.cloud.facturacion.model.priority.PriorityUtil;
import com.echevarne.sap.cloud.facturacion.model.priority.SelectOption;
import com.echevarne.sap.cloud.facturacion.model.priority.SelectOptionItem;
import com.echevarne.sap.cloud.facturacion.model.priority.SelectOptionResult;
import com.echevarne.sap.cloud.facturacion.model.solicitudindividual.SolIndItems;
import com.echevarne.sap.cloud.facturacion.model.solicitudindividual.SolIndPartner;
import com.echevarne.sap.cloud.facturacion.model.solicitudindividual.SolicitudIndividual;
import com.echevarne.sap.cloud.facturacion.model.solicitudrecibida.PeticionMuestreoItems;
import com.echevarne.sap.cloud.facturacion.model.trazabilidad.TrazabilidadClasificacion;
import com.echevarne.sap.cloud.facturacion.model.trazabilidad.TrazabilidadEstHistory;
import com.echevarne.sap.cloud.facturacion.repositories.replicated.S4MaterialSalesRepository;
import com.echevarne.sap.cloud.facturacion.services.BloqueoCortesiaService;
import com.echevarne.sap.cloud.facturacion.services.FacturableFechaPeticionService;
import com.echevarne.sap.cloud.facturacion.services.MasDataTipoPosicionService;
import com.echevarne.sap.cloud.facturacion.services.MatConceptoFactService;
import com.echevarne.sap.cloud.facturacion.util.DateUtils;
import com.echevarne.sap.cloud.facturacion.validations.BasicValidation;
import com.echevarne.sap.cloud.facturacion.validations.ValidationResult;

import lombok.var;

@Component("validacionesSolIndItem")
public class ValidacionesSolIndItem extends BasicValidation implements SolIndItemValidation {

	private static final String NO_APLICA = "No se han cumplido las condiciones.";
	public static final String CODIGO = "FB";
	public static final String ANY = "*";
	public static final Integer ANY_INTEGER = 0;
	public static final String CLIENTE = "cliente";
	public static final String OFICINA_VENTAS = "oficinaVentas";
	public static final String TIPO_PETICION = "tipoPeticion";
	public static final String COMPANIA = "compania";
	public static final String EMPRESA = "empresa";
	public static final String REMITENTE = "remitente";

	protected final BloqueoCortesiaService bloqueoCortesiaService;
	private final FacturableFechaPeticionService facturableFechaPeticionSrv;
	private final MasDataTipoPosicionService masDataTipoPosicionService;
	private final S4MaterialSalesRepository s4MaterialSalesRepository;
	private final MatConceptoFactService matConceptoFactService;


	protected static List<SolIndItemValidation> getValidationsActives() {
		return new ArrayList<>();
	}

	public ValidacionesSolIndItem(
			final FacturableFechaPeticionService facturableFechaPeticionSrv,
			final BloqueoCortesiaService bloqueoCortesiaService,
			final MasDataTipoPosicionService masDataTipoPosicionService,
			final S4MaterialSalesRepository s4MaterialSalesRepository,
			final MatConceptoFactService matConceptoFactService
	) {
		this.facturableFechaPeticionSrv = facturableFechaPeticionSrv;
		this.bloqueoCortesiaService = bloqueoCortesiaService;
		this.s4MaterialSalesRepository = s4MaterialSalesRepository;
		this.masDataTipoPosicionService = masDataTipoPosicionService;
		this.matConceptoFactService = matConceptoFactService;
	}

	public ValidationResult hasItemPrice(SolIndItems entidad) {
		List<SolIndItemValidation> validacionesTransformacion = new ArrayList<SolIndItemValidation>();
		validacionesTransformacion.add(SolIndItemValidation.hasConditionNetOrModifiedPrice());
		setResultados(applyAll(entidad, validacionesTransformacion));
		boolean isValid = getResultados().values().stream().filter(vr -> !vr.isValid()).count() == 1;
		if (isValid)
			return ValidationResult.valid();
		return ValidationResult.invalid(NO_APLICA);

	}

	public ValidationResult hasItemTax(SolIndItems entidad) {
		List<SolIndItemValidation> validacionesTransformacion = new ArrayList<SolIndItemValidation>();
		validacionesTransformacion.add(SolIndItemValidation.hasConditionVatIncluded());
		setResultados(applyAll(entidad, validacionesTransformacion));
		boolean isValid = getResultados().values().stream().filter(vr -> !vr.isValid()).count() == 1;
		if (isValid)
			return ValidationResult.valid();
		return ValidationResult.invalid(NO_APLICA);

	}

	public ValidationResult esZPTConCotizacion(SolIndItems sii) {
		boolean esMonedaZPT = sii.getPrices().stream()
		.anyMatch(itemPricing -> ConstFacturacion.CONDICION_PRECIO_BRUTO.equals(itemPricing.getConditionType())
				&& ConstFacturacion.CONDITION_CURRENCY_PUNTOS.equals(itemPricing.getConditionCurrency()));
		boolean noTieneCotizacion = sii.getSolicitudInd().getTipoCotizacion() == null || sii.getSolicitudInd().getTipoCotizacion().isEmpty();
		if (esMonedaZPT && noTieneCotizacion) {
			return ValidationResult.invalid("Tiene moneda ZPT pero no hay cotización");
		}
		return ValidationResult.valid();
	}
	
	public boolean tieneConcepto(SolIndItems sii) {
		if (StringUtils.isBlank(sii.getPriceReferenceMaterial()))
			return false;

		return true;
	}

	public boolean esFacturable(SolIndItems sii) {
		if (ConstFacturacion.TIPO_POSICION_NO_FACTURABLE.equals(sii.getSalesOrderItemCategory()))
			return false;

		return true;
	}

	public boolean conceptoIgualAMaestro(SolIndItems sii) {
		List<MatConceptoFact> listaMateriales = matConceptoFactService.findByCodigoMaterial(sii.getMaterial());
		if (listaMateriales == null || listaMateriales.size() == 0) {
			return false;
		} else {
			Calendar fechaSalesOrder = Calendar.getInstance();
			fechaSalesOrder.setTime(sii.getSolicitudInd().getSalesOrderDate());
			for (MatConceptoFact material : listaMateriales) {
				Calendar fechaDesde = material.getValidezDesde();
				Calendar fechaHasta = material.getValidezHasta();

				if (fechaDesde != null && fechaHasta != null && fechaDesde.before(fechaSalesOrder)
						&& fechaHasta.after(fechaSalesOrder)) {
					return true;
				}
			}
		}

		return false;
	}

	public boolean tienePrecioResultadoCorrecto(SolIndItems sii) {
		String matnr = sii.getMaterial();
		String vkorg = sii.getSolicitudInd().getSalesOrganization();
		String vtweg = ConstFacturacion.CANAL_DISTRIBUCION_COMUN;
		var s4MaterialsSalesOpt = s4MaterialSalesRepository.findByMATNRAndVKORGAndVTWEG(matnr, vkorg, vtweg);
		if (s4MaterialsSalesOpt.isPresent()) {
			var s4MatSales = s4MaterialsSalesOpt.get();
			String mvgr1 = s4MatSales.getMVGR1();
			if ("1".equals(mvgr1) || "2".equals(mvgr1)) {
				return true;
			}
		}
		return false;
	}

	// public ValidationResult hasContratoCapitativoZFI(SolIndItems entidad) {
	// List<SolIndItemValidation> validacionesTransformacion = new
	// ArrayList<SolIndItemValidation>();
	// validacionesTransformacion.add(SolIndItemValidation.hasContratoAndZFI());
	// setResultados(applyAll(entidad, validacionesTransformacion));
	// boolean isValid = getResultados().values().stream().filter(vr ->
	// !vr.isValid()).count() == 2;
	// if (isValid) return ValidationResult.valid();
	// return ValidationResult.invalid(NO_APLICA);
	// }

	@Override
	public ValidationResult apply(SolIndItems entidad) {

		List<SolIndItemValidation> validacionesTransformacion = getValidationsActives();
		setResultados(applyAll(entidad, validacionesTransformacion));
		boolean isValid = getResultados().values().stream().filter(vr -> !vr.isValid()).count() > 0;
		if (isValid)
			return ValidationResult.valid();
		return ValidationResult.invalid(NO_APLICA);

	}

	public ValidationResult hasContratoCapitativoZFI(SolIndItems sii) {
		boolean esMonedaZFI = sii.getPrices().stream()
				.anyMatch(itemPricing -> itemPricing.getConditionType().equals(ConstFacturacion.CONDICION_PRECIO_BRUTO)
						&& itemPricing.getConditionCurrency() != null
						&& itemPricing.getConditionCurrency().equals(ConstFacturacion.CONDITION_CURRENCY_CONTRATOS));
		boolean noTieneContrato = sii.getSolicitudInd().getContrato() == null;
		if (esMonedaZFI && noTieneContrato) {
			return ValidationResult.invalid("Tiene moneda ZFI pero no hay contrato");
		}
		return ValidationResult.valid();
	}

	public ValidationResult facturaPorFechaPeticion(SolIndItems solIndItem) {

		SolicitudIndividual si = solIndItem.getSolicitudInd();

		String organizacion = leftPad(si.getSalesOrganization(), 4, "0");
		String cliente = leftPad(si.getSoldToParty(), 10, "0");
		String oficinaVentas = leftPad(si.getSalesOffice(), 4, "0");

		int tipoPeticion = si.getTipoPeticion();

		Optional<SolIndPartner> interlocutorEmpresa = si.getInterlocutorEmpresa();
		Optional<SolIndPartner> interlocutorCompania = si.getInterlocutorCompania();
		Optional<SolIndPartner> interlocutorRemitente = si.getInterlocutorRemitente();

		String empresa = interlocutorEmpresa.isPresent() ? interlocutorEmpresa.get().getCustomer() : "";
		String compania = interlocutorCompania.isPresent() ? interlocutorCompania.get().getCustomer() : "";
		String remitente = interlocutorRemitente.isPresent() ? interlocutorRemitente.get().getCustomer() : "";
		Calendar fechaValidez = Calendar.getInstance();

		List<String> organizaciones = asList(organizacion, ANY);
		List<String> clientes = asList(cliente, ANY);
		List<String> oficinas = asList(oficinaVentas, ANY);
		List<Integer> tipos = asList(tipoPeticion, ANY_INTEGER);
		List<String> empresas = asList(empresa, ANY);
		List<String> companias = asList(compania, ANY);
		List<String> remitentes = asList(remitente, ANY);

		List<FacturableFechaPeticion> reglas = facturableFechaPeticionSrv.findByParams(organizaciones, clientes,
				oficinas, tipos, empresas, companias, remitentes, fechaValidez);

		// crear el SelectOption con los valores sin * y buscar el de mayor prioridad
		var so = new SelectOption();
		so.put(CLIENTE, new SelectOptionItem(cliente));
		so.put(OFICINA_VENTAS, new SelectOptionItem(oficinaVentas));
		so.put(TIPO_PETICION, new SelectOptionItem(tipoPeticion));
		so.put(EMPRESA, new SelectOptionItem(empresa));
		so.put(COMPANIA, new SelectOptionItem(compania));
		so.put(REMITENTE, new SelectOptionItem(remitente));
		Optional<SelectOptionResult> resultOpt = PriorityUtil.getFirst(reglas, so);
		if (resultOpt.isPresent()) {
			return ValidationResult.valid();
		}
		return ValidationResult.invalid("No se encontró SelectOption");
	}

	public ValidationResult puedeDesbloquear(SolIndItems sii, MasDataEstado origen) {
		if (!origen.getCodeEstado().equals(Bloqueada.CODIGO))
			return ValidationResult.valid();
		boolean bloqueoAutomatico = sii.getTrazabilidad().getItemRec().isBloqueoAutomatico();
		return bloqueoAutomatico ? ValidationResult.invalid("Sigue bloqueada") : ValidationResult.valid();
	}

	public ValidationResult tipoCondicionExcluida(SolIndItems solIndItem) {
		Optional<MasDataTipoPosicion> positionType = Optional.empty();
		if (solIndItem.getSalesOrderItemCategory() != null) {
			positionType = masDataTipoPosicionService.findByTipoPosicion(solIndItem.getSalesOrderItemCategory());
		}
		boolean tipoPosicionExcluyente = positionType.isPresent() && positionType.get().isExcludes();
		if (tipoPosicionExcluyente) {
			return ValidationResult.valid();
		}
		return ValidationResult.invalid("No es excluida");
	}

	public ValidationResult hasExcluidaManual(SolIndItems sii) {
		Optional<TrazabilidadEstHistory> ultimoEstado = sii.getTrazabilidad().getLastEstado();
		if (ultimoEstado.isPresent()) {
			if (ultimoEstado.get().getEstado().getCodeEstado().equals(Excluida.CODIGO))
				if (ultimoEstado.get().getMotivo() != null
						&& ultimoEstado.get().getMotivo().getCodigo().equals(ConstEstados.MOTIVO_ESTADO_EXMA)
						&& sii.getSalesOrderItemCategory().equals(ConstFacturacion.TIPO_POSICION_EXCLUIDA)
                        && !ultimoEstado.get().isAutomatico())
					return ValidationResult.valid();
		}
		return ValidationResult.invalid("No esta excluida manualmente.");
	}

	public ValidationResult aplicaBloqueoCortesia(SolIndItems solIndItem) {
		SolicitudIndividual si = solIndItem.getSolicitudInd();

		String organizacion = leftPad(si.getSalesOrganization(), 4, "0");
		String cliente = leftPad(si.getSoldToParty(), 10, "0");
		String oficinaVentas = leftPad(si.getSalesOffice(), 4, "0");

		int tipoPeticion = si.getTipoPeticion();

		Optional<SolIndPartner> interlocutorEmpresa = si.getInterlocutorEmpresa();
		Optional<SolIndPartner> interlocutorCompania = si.getInterlocutorCompania();
		Optional<SolIndPartner> interlocutorRemitente = si.getInterlocutorRemitente();

		String empresa = interlocutorEmpresa.isPresent() ? interlocutorEmpresa.get().getCustomer() : "";
		String compania = interlocutorCompania.isPresent() ? interlocutorCompania.get().getCustomer() : "";
		String remitente = interlocutorRemitente.isPresent() ? interlocutorRemitente.get().getCustomer() : "";
		Calendar fechaValidez = DateUtils.convertToCalendar(si.getSalesOrderDate());

		List<String> organizaciones = asList(organizacion, ANY);
		List<String> clientes = asList(cliente, ANY);
		List<String> oficinas = asList(oficinaVentas, ANY);
		List<Integer> tipos = asList(tipoPeticion, ANY_INTEGER);
		List<String> empresas = asList(empresa, ANY);
		List<String> companias = asList(compania, ANY);
		List<String> remitentes = asList(remitente, ANY);

		final Calendar fechaValidezQuartos = DateUtils.roundToQuarterMinutes(fechaValidez);

		List<BloqueoCortesia> reglas = bloqueoCortesiaService.findByParams(organizaciones, clientes, oficinas, tipos,
				empresas, companias, remitentes, fechaValidezQuartos);

		// crear el SelectOption con los valores sin * y buscar el de mayor prioridad
		var so = new SelectOption();
		so.put(CLIENTE, new SelectOptionItem(cliente));
		so.put(OFICINA_VENTAS, new SelectOptionItem(oficinaVentas));
		so.put(TIPO_PETICION, new SelectOptionItem(tipoPeticion));
		so.put(EMPRESA, new SelectOptionItem(empresa));
		so.put(COMPANIA, new SelectOptionItem(compania));
		so.put(REMITENTE, new SelectOptionItem(remitente));
		Optional<SelectOptionResult> resultOpt = PriorityUtil.getFirst(reglas, so);
		if (resultOpt.isPresent()) {
			return ValidationResult.valid();
		}
		return ValidationResult.invalid("No se encontró SelectOption");
	}

	public boolean esPerfilValidado(SolIndItems solIndItem) {
		if (solIndItem.getTrazabilidad().getItemRec().getEsPerfil()) {
			var idItem = solIndItem.getSalesOrderIndItem();
			return !solIndItem.getSolicitudInd().getItems().stream().filter(i -> i.getHigherLevelltem() == idItem)
					.filter(x -> !Excluida.CODIGO.equals(x.getTrazabilidad().getUltimoEstado()))
					.anyMatch(fi -> !fi.contieneValidada());
		} else {
			return false;
		}
	}

	public boolean esPerfilConPruebasErroneas(SolIndItems solIndItem) {
		if (solIndItem.getTrazabilidad().getItemRec().getEsPerfil()) {
			var idItem = solIndItem.getSalesOrderIndItem();
			return solIndItem.getSolicitudInd().getItems().stream().filter(i -> i.getHigherLevelltem() == idItem)
					.filter(x -> !Excluida.CODIGO.equals(x.getTrazabilidad().getUltimoEstado()))
					.anyMatch(fi -> fi.esErronea());
		} else {
			return false;
		}
	}

	public boolean esPerfilConPruebasDiferenteTipo(SolIndItems solIndItem) {


		if (solIndItem.getTrazabilidad().getItemRec().getEsPerfil()
				&& !Excluida.CODIGO.equals(solIndItem.getTrazabilidad().getUltimoEstado())) {
			var idItem = solIndItem.getSalesOrderIndItem();
			return !solIndItem.getSolicitudInd().getItems().stream().filter(i -> i.getHigherLevelltem() == idItem)
					.filter(x -> !ConstFacturacion.TIPO_POSICION_INCONGRUENTE.equals(x.getSalesOrderItemCategory()))
					.filter(x -> !Excluida.CODIGO.equals(x.getTrazabilidad().getUltimoEstado()))
					.map(sii -> sii.getTrazabilidad().getItemRec())
					.allMatch(ppp -> sonDelMismoTipo(solIndItem.getTrazabilidad().getItemRec(), ppp));
		} else {
			return false;
		}
	}

	private Boolean sonDelMismoTipo(PeticionMuestreoItems perfil, PeticionMuestreoItems prueba) {
		Optional<TrazabilidadClasificacion> optTrazabilidadClasPerfil = Optional.ofNullable(perfil.getTrazabilidad().getTrzClasificacion());
		Optional<TrazabilidadClasificacion> optTrazabilidadClasPrueba = Optional.ofNullable(prueba.getTrazabilidad().getTrzClasificacion());
		if (optTrazabilidadClasPerfil.isPresent() && !optTrazabilidadClasPrueba.isPresent())
			return false;
		if (!optTrazabilidadClasPerfil.isPresent() && optTrazabilidadClasPrueba.isPresent())
			return false;
		if (!optTrazabilidadClasPerfil.isPresent() && !optTrazabilidadClasPrueba.isPresent())
			return true;
		return optTrazabilidadClasPerfil.get().getTipologia().getCodeTipologia()
				.equals(optTrazabilidadClasPrueba.get().getTipologia().getCodeTipologia());
	}

	public boolean esPerfilConTodasPruebasExcluidas(SolIndItems solIndItem) {
		if (solIndItem.getTrazabilidad().getItemRec().getEsPerfil()) {
			var idItem = solIndItem.getSalesOrderIndItem();
			return !solIndItem.getSolicitudInd().getItems().stream().filter(i -> i.getHigherLevelltem() == idItem)
					.allMatch(
							ppp -> EstadosUtils.aplicaEstadoPadre(EstadosUtils.getEstadoByCode(Excluida.CODIGO), ppp));
		} else {
			return false;
		}
	}
}
