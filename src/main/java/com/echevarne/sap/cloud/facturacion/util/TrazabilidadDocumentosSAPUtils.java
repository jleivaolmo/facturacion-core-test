package com.echevarne.sap.cloud.facturacion.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.echevarne.sap.cloud.facturacion.constants.ConstFacturacion;
import com.echevarne.sap.cloud.facturacion.model.solicitudindividual.SolIndItemPricing;
import com.echevarne.sap.cloud.facturacion.model.solicitudindividual.SolIndItems;
import com.echevarne.sap.cloud.facturacion.model.trazabilidad.Trazabilidad;
import com.echevarne.sap.cloud.facturacion.model.trazabilidad.TrazabilidadDocumentosSAP;
import com.echevarne.sap.cloud.facturacion.services.MasDataTipoDocumentoSAPService;

import lombok.var;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class TrazabilidadDocumentosSAPUtils {

	@Autowired
	private MasDataTipoDocumentoSAPService masDataTipoDocumentoSAPService;

	public TrazabilidadDocumentosSAP createTrazabilidadDocumentosSAP(SolIndItems item, String index, String codigo, String value) {
		try {
			var tipoDocumento = masDataTipoDocumentoSAPService.findByCodigo(codigo);
			Trazabilidad trazabilidad = item.getTrazabilidad();

			// TIPODOCUMENTO
			TrazabilidadDocumentosSAP trzDocumentoSAP = new TrazabilidadDocumentosSAP();
			trzDocumentoSAP.setTipoDocumento(tipoDocumento.get());
			trzDocumentoSAP.setItem(index);
			trzDocumentoSAP.setValor(value);
			trzDocumentoSAP.setTrazabilidad(trazabilidad);

			// PRUEBA
			if (StringUtils.isNotBlank(item.getMaterial())) {
				trzDocumentoSAP.setPrueba(item.getMaterial());
			}

			// CLIENTE
			if (StringUtils.isNotBlank(item.getSolicitudInd().getSoldToParty())) {
				trzDocumentoSAP.setCliente(item.getSolicitudInd().getSoldToParty());
			}

			// REMITENTE
			if (item.getSolicitudInd().getInterlocutorRemitente().isPresent()
					&& StringUtils.isNotBlank(item.getSolicitudInd().getInterlocutorRemitente().get().getCustomer())) {
				trzDocumentoSAP.setRemitente(item.getSolicitudInd().getInterlocutorRemitente().get().getCustomer());
			}

			// COMPANIA
			if (item.getSolicitudInd().getInterlocutorCompania().isPresent()
					&& StringUtils.isNotBlank(item.getSolicitudInd().getInterlocutorCompania().get().getCustomer())) {
				trzDocumentoSAP.setCompania(item.getSolicitudInd().getInterlocutorCompania().get().getCustomer());
			}

			// OFICINAVENTAS
			if (StringUtils.isNotBlank(item.getSolicitudInd().getSalesOffice())) {
				trzDocumentoSAP.setOficinaVentas(item.getSolicitudInd().getSalesOffice());
			}

			// TIPOPETICION
			trzDocumentoSAP.setTipoPeticion(item.getSolicitudInd().getTipoPeticion());

			// UNIDADES
			BigDecimal unidades = BigDecimal.ZERO;
			if (item.getRequestedQuantity() != null && item.getRequestedQuantity().compareTo(BigDecimal.ZERO) != 0) {
				unidades = item.getRequestedQuantity();
			}
			trzDocumentoSAP.setUnidades(unidades);

			// PRECIOUNITARIONETO
			BigDecimal precioUnitarioNeto = BigDecimal.ZERO;
			if (item.getNetAmount() != null && item.getNetAmount().compareTo(BigDecimal.ZERO) != 0 && item.getRequestedQuantity() != null
					&& item.getRequestedQuantity().compareTo(BigDecimal.ZERO) != 0) {
				precioUnitarioNeto = item.getNetAmount().divide(item.getRequestedQuantity(), 2, RoundingMode.HALF_UP);
			}
			trzDocumentoSAP.setPrecio_unitario_neto(precioUnitarioNeto);

			// IMPORTETOTALNETO
			BigDecimal importeTotalNeto = BigDecimal.ZERO;
			if (item.getNetAmount() != null && item.getNetAmount().compareTo(BigDecimal.ZERO) != 0) {
				importeTotalNeto = item.getNetAmount();
			}
			trzDocumentoSAP.setImporte_total_neto(importeTotalNeto);

			// IMPORTETOTALBRUTO
			BigDecimal importeTotalBruto = this.getImporteBruto(item);
			trzDocumentoSAP.setImporte_total_bruto(importeTotalBruto);

			// PRECIOUNITARIOBRUTO
			BigDecimal precioUnitarioBruto = BigDecimal.ZERO;
			if (importeTotalBruto != null && importeTotalBruto.compareTo(BigDecimal.ZERO) != 0 && item.getRequestedQuantity() != null
					&& item.getRequestedQuantity().compareTo(BigDecimal.ZERO) != 0) {
				precioUnitarioBruto = importeTotalBruto.divide(item.getRequestedQuantity(), 2, RoundingMode.HALF_UP);
			}
			trzDocumentoSAP.setPrecio_unitario_bruto(precioUnitarioBruto);

			// PETLIMS
			if (item.getTrazabilidad().getItemRec() != null && item.getTrazabilidad().getItemRec().getPeticion() != null
					&& item.getTrazabilidad().getItemRec().getPeticion().getSolicitud() != null
					&& item.getTrazabilidad().getItemRec().getPeticion().getSolicitud().getCodigoPeticionLims() != null) {
				trzDocumentoSAP.setPetLims(item.getTrazabilidad().getItemRec().getPeticion().getSolicitud().getCodigoPeticionLims());
			}

			// ORGVENTAS
			if (item.getSolicitudInd().getSalesOrganization() != null)
				trzDocumentoSAP.setOrgVentas(item.getSolicitudInd().getSalesOrganization());

			// IMPORTEPUNTOS
			for (SolIndItemPricing a : item.getPrices()) {
				if (a.getConditionType().equals(ConstFacturacion.CONDICION_PRECIO_BRUTO)
						&& a.getConditionCurrency().equals(ConstFacturacion.CONDITION_CURRENCY_CONTRATOS)) {
					trzDocumentoSAP.setImporte_puntos(a.getConditionAmount());
					break;
				} else {
					trzDocumentoSAP.setImporte_puntos(BigDecimal.ZERO);
				}
			}

			// FECHA
			if (item.getTrazabilidad().getFechaFactura() != null)
				trzDocumentoSAP.setFecha(new Timestamp(item.getTrazabilidad().getFechaFactura().getTime()));

			// CANAL
			if (item.getSolicitudInd().getDistributionChannel() != null)
				trzDocumentoSAP.setCanal(item.getSolicitudInd().getDistributionChannel());

			// CONDITIONRATEVALUE
			for (SolIndItemPricing price : item.getPrices()) {
				if ((price.getConditionType().equals("ZRD1") || price.getConditionType().equals("ZRDC") || price.getConditionType().equals("ZRDF")
						|| price.getConditionType().equals("ZRDT") || price.getConditionType().equals("ZDPR")) && price.getConditionRateValue() != null
						&& price.getConditionRateValue().compareTo(BigDecimal.ZERO) != 0) {
					trzDocumentoSAP.setConditionRateValue(price.getConditionRateValue());
					break;
				}
			}

			// DESCUENTO ZRDT
			for (SolIndItemPricing price : item.getPrices()) {
				if (price.getConditionType().equals("ZRDT")) {
					trzDocumentoSAP.setDescuentoZRDT(price.getConditionAmount());
					break;
				}
			}

			return trzDocumentoSAP;
		} catch (Exception e) {
			log.error("TrazabilidadDocumentosSAPUtils - createTrazabilidadDocumentosSAP - ERROR", e);
			return null;
		}
	}

	public BigDecimal getImporteBruto(SolIndItems solIndItems) {
		BigDecimal importeBruto = BigDecimal.ZERO;
		var importeZNET = solIndItems.getPrices().stream().filter(p -> p.getConditionType().equals(ConstFacturacion.CONDICION_PRECIO_MODIFICADO)).findFirst();
		if (importeZNET.isPresent()) {
			importeBruto = importeZNET.get().getConditionAmount();
		} else {
			var importeNeto = solIndItems.getPrices().stream().filter(p -> p.getConditionType().equals(ConstFacturacion.CONDICION_PRECIO_NETO)).findFirst()
					.get().getConditionAmount();
			var optImporteZDR1 = solIndItems.getPrices().stream().filter(p -> p.getConditionType().equals("ZRD1")).findFirst();
			var importeZDR1 = optImporteZDR1.isPresent() ? optImporteZDR1.get().getConditionAmount() : BigDecimal.ZERO;
			var optImporteZRDC = solIndItems.getPrices().stream().filter(p -> p.getConditionType().equals("ZRDC")).findFirst();
			var importeZRDC = optImporteZRDC.isPresent() ? optImporteZRDC.get().getConditionAmount() : BigDecimal.ZERO;
			var optImporteZRDF = solIndItems.getPrices().stream().filter(p -> p.getConditionType().equals("ZRDF")).findFirst();
			var importeZRDF = optImporteZRDF.isPresent() ? optImporteZRDF.get().getConditionAmount() : BigDecimal.ZERO;
			var optImporteZRDT = solIndItems.getPrices().stream().filter(p -> p.getConditionType().equals("ZRDT")).findFirst();
			var importeZRDT = optImporteZRDT.isPresent() ? optImporteZRDT.get().getConditionAmount() : BigDecimal.ZERO;
			var optImporteZDPS = solIndItems.getPrices().stream().filter(p -> p.getConditionType().equals("ZDPS")).findFirst();
			var importeZDPS = optImporteZDPS.isPresent() ? optImporteZDPS.get().getConditionAmount() : BigDecimal.ZERO;
			var optImporteZDPR = solIndItems.getPrices().stream().filter(p -> p.getConditionType().equals("ZDPR")).findFirst();
			var importeZDPR = optImporteZDPR.isPresent() ? optImporteZDPR.get().getConditionAmount() : BigDecimal.ZERO;
			importeBruto = importeNeto.subtract(importeZDR1).subtract(importeZRDC).subtract(importeZRDF).subtract(importeZRDT).subtract(importeZDPS)
					.subtract(importeZDPR);
		}

		return importeBruto;
	}
}
