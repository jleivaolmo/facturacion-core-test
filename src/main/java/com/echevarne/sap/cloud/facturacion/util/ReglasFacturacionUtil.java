package com.echevarne.sap.cloud.facturacion.util;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import com.echevarne.sap.cloud.facturacion.constants.ConstFacturacion;
import com.echevarne.sap.cloud.facturacion.model.AgrReglasFacturacion;
import com.echevarne.sap.cloud.facturacion.model.solicitudindividual.SolIndItemPricing;
import com.echevarne.sap.cloud.facturacion.model.solicitudindividual.SolIndItems;
import com.echevarne.sap.cloud.facturacion.model.solicitudindividual.SolicitudIndividual;
import com.echevarne.sap.cloud.facturacion.model.solicitudrecibida.PeticionMuestreo;
import com.echevarne.sap.cloud.facturacion.services.S4InfoService;
import lombok.AllArgsConstructor;
import lombok.var;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ReglasFacturacionUtil {

	public final S4InfoService s4InfoSrv;
	
	public static final String BAREMADO = "B";
	public static final String NO_BAREMADO = "NB";

	/**
	 *
	 * Crea una regla de facturación en base a los valores de las solicitudes
	 */
	public AgrReglasFacturacion createReglaByValues(SolicitudIndividual si, PeticionMuestreo pm, SolIndItems item, Optional<SolIndItemPricing> condition) {
		var regla = new AgrReglasFacturacion();

		/*
		*	Datos para aplicar
		*/
		var material = item.getMaterial();
		var concepto = item.getPriceReferenceMaterial();
		var remitida = pm.isEsMuestraRemitida();
		var unidadProductiva = item.getUnidadProductiva() != null ? item.getUnidadProductiva() : "";
		var referenciaCliente = pm.getCodigoReferenciaCliente() != null ? pm.getCodigoReferenciaCliente() : "";
		var tipoPeticion = pm.getTipoPeticion();
		//Ajustar compania igual que remitente.
		var codIntCompania = si.getInterlocutorCompania().map(x -> x.getCustomer()).orElse("");
		var intRemitente = si.getInterlocutorRemitente();
		var codIntRemitente = intRemitente.isPresent() ? intRemitente.get().getCustomer() : "";
		var provinciaRemitente = pm.interlocutorRemitente().isPresent()
				? pm.interlocutorRemitente().get().getProvinciaInterlocutor(): "";
		var documentoUnico = si.getDocumentoUnico() != null ? si.getDocumentoUnico() : "";
		var codigoPoliza = si.getCodigoPoliza() != null ? si.getCodigoPoliza() : "";
		var codigoOperacion = si.getCodigoOperacion() != null ? si.getCodigoOperacion() : "";
		var tarifa = si.getTarifa() != null ? si.getTarifa() : "";
		var codigoDelegacion = si.getSalesOffice();
		Set<String> especialidadesAplicar = s4InfoSrv.findByParams(material, si.getSoldToParty(), si.getSalesOrganization(),
				codigoDelegacion, codIntCompania).stream().map(e -> e.getUN_PROD_CLIENTE()).collect(Collectors.toSet());
		especialidadesAplicar.add("*");

		/*
		*	Solo separadores
		*/
		// var materialSeparar = getMaterialForRule(si, item);
		var codigoPeticion = si.getPurchaseOrderByCustomer();
		var codigoMoneda = condition.isPresent()? condition.get().getConditionCurrency() : "";
		var grupoSector = item.getGrupoSector() != null ? item.getGrupoSector() : "";
		String especialidadesSeparar = s4InfoSrv.findByParams(material, si.getSoldToParty(), si.getSalesOrganization(),
				codigoDelegacion, codIntCompania).stream().map(e -> e.getUN_PROD_CLIENTE()).collect(Collectors.joining("-"));

		regla.setEspecialidadAplicar(especialidadesAplicar);
		// regla.setMaterialAplicar(materialAplicar);

		regla.setCodigoDelegacion(codigoDelegacion);
		regla.setTipoPeticion(tipoPeticion);
		regla.setInterlocutorCompania(codIntCompania);
		regla.setInterlocutorRemitente(codIntRemitente);
		regla.setUnidadProductiva(unidadProductiva);
		regla.setDocumentoUnico(documentoUnico);
		regla.setProvinciaRemitente(provinciaRemitente);
		regla.setCodigoPoliza(codigoPoliza);
		regla.setCodigoOperacion(codigoOperacion);
		regla.setCodigoPrueba(material);
		regla.setCodigoReferenciaCliente(referenciaCliente);
		regla.setMuestraRemitida(remitida);
		regla.setConceptoFacturacion(concepto);
		regla.setEspecialidadCliente(especialidadesSeparar);
		regla.setTarifa(tarifa);

		regla.setCodigoPeticion(codigoPeticion);
		regla.setCodigoMoneda(codigoMoneda);
		regla.setSectorPrueba(grupoSector);

		return regla;

	}

	/**
	 *
	 * En caso de que la prueba sea parte de un perfil, se agrupa dentro del mismo perfil
	 *
	 * @param si
	 * @param item
	 * @return
	 */
	private String getMaterialForRule(SolicitudIndividual si, SolIndItems item) {
		if(item.getHigherLevelltem() != 0) {
			Optional<SolIndItems> perfil = si.getItems().stream()
			.filter(itm -> itm.getSalesOrderIndItem() == item.getHigherLevelltem())
			.findFirst();
			if(perfil.isPresent())
				return perfil.get().getMaterial();
		}
		return item.getMaterial();
	}

	/**
	 *
	 * Crea una regla de facturación en base a los valores de las solicitudes
	 */
	public AgrReglasFacturacion createReglaByValuesPeticion(SolicitudIndividual si, PeticionMuestreo pm) {

		var regla = new AgrReglasFacturacion();

		/*
		*	Datos para aplicar
		*/
		var remitida = pm.isEsMuestraRemitida();
		var referenciaCliente = pm.getCodigoReferenciaCliente() != null ? pm.getCodigoReferenciaCliente() : "";
		var tipoPeticion = pm.getTipoPeticion();
		var codIntCompania = si.getInterlocutorCompania().map(x -> x.getCustomer()).orElse("");
		var codIntRemitente = si.getInterlocutorRemitente().map(x -> x.getCustomer()).orElse("");
		var provinciaRemitente = pm.interlocutorRemitente().isPresent()? pm.interlocutorRemitente().get().getProvinciaInterlocutor(): "";
		var documentoUnico = si.getDocumentoUnico() != null ? si.getDocumentoUnico() : "";
		var codigoPoliza = si.getCodigoPoliza() != null ? si.getCodigoPoliza() : "";
		var codigoOperacion = si.getCodigoOperacion() != null ? si.getCodigoOperacion() : "";
		var tarifa = si.getTarifa() != null ? si.getTarifa() : "";
		var codigoDelegacion = si.getSalesOffice();

		/*
		*	Solo separadores
		*/
		var codigoPeticion = si.getPurchaseOrderByCustomer();


		regla.setCodigoDelegacion(codigoDelegacion);
		regla.setTipoPeticion(tipoPeticion);
		regla.setInterlocutorCompania(codIntCompania);
		regla.setInterlocutorRemitente(codIntRemitente);
		regla.setDocumentoUnico(documentoUnico);
		regla.setProvinciaRemitente(provinciaRemitente);
		regla.setCodigoPoliza(codigoPoliza);
		regla.setCodigoOperacion(codigoOperacion);
		regla.setCodigoReferenciaCliente(referenciaCliente);
		regla.setMuestraRemitida(remitida);
		regla.setTarifa(tarifa);

		regla.setCodigoPeticion(codigoPeticion);

		return regla;

	}

	/**
	 *
	 * Crea una regla de facturación en base a los valores de las solicitudes
	 */
	public AgrReglasFacturacion updateReglaByValuesPrueba(AgrReglasFacturacion regla, SolicitudIndividual si, SolIndItems item, Optional<SolIndItemPricing> condition) {

		var material = item.getMaterial();
		var concepto = item.getPriceReferenceMaterial();
		var unidadProductiva = item.getUnidadProductiva() != null ? item.getUnidadProductiva() : "";

		// var materialSeparar = getMaterialForRule(si, item);
		var codigoMoneda = "";
		if (condition.isPresent()) {
			if (condition.get().getConditionType().equals(ConstFacturacion.CONDICION_PRECIO_MODIFICADO)) {
				codigoMoneda = ConstFacturacion.CONDITION_CURRENCY_EUROS;
			} else {
				codigoMoneda = condition.get().getConditionCurrency();
			}
		}

		var grupoSector = item.getGrupoSector() != null ? item.getGrupoSector() : "";
		var codigoBaremo = item.getCodigoBaremo() != null && !item.getCodigoBaremo().isEmpty() ? BAREMADO : NO_BAREMADO;
		
		regla.setMaterial(material);
		regla.setUnidadProductiva(unidadProductiva);
		regla.setCodigoPrueba(material);
		regla.setConceptoFacturacion(concepto);

		regla.setCodigoMoneda(codigoMoneda);
		regla.setSectorPrueba(grupoSector);
		regla.setCodigoBaremo(codigoBaremo);

		return regla;
	}

}
