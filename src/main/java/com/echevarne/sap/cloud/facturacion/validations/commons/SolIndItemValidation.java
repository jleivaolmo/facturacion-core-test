package com.echevarne.sap.cloud.facturacion.validations.commons;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

import com.echevarne.sap.cloud.facturacion.constants.ConstFacturacion;
import com.echevarne.sap.cloud.facturacion.model.solicitudindividual.SolIndItems;
import com.echevarne.sap.cloud.facturacion.validations.ValidationResult;

@FunctionalInterface
public interface SolIndItemValidation extends Function<SolIndItems, ValidationResult> {
    
    static SolIndItemValidation hasConditionNetOrModifiedPrice() {
        return evaluar(sii -> 
            !sii.getPrices().stream()
            .filter(itemPricing -> itemPricing.getConditionType().equals(ConstFacturacion.CONDICION_PRECIO_BRUTO) ||
                    itemPricing.getConditionType().equals(ConstFacturacion.CONDICION_PRECIO_MODIFICADO))
            .findAny().isPresent(), 
            "No hay condicion de precio ZPR0 o ZNET");
    }

    static SolIndItemValidation hasConditionVatIncluded() {
        return evaluar(sii ->
                        !sii.getPrices().stream()
                                .anyMatch(itemPricing -> itemPricing.getConditionType().equals(ConstFacturacion.CONDICION_PRECIO_IVA)),
                "No hay condicion de impuesto (MWST)");
    }

    static SolIndItemValidation hasCurrencyZFI() {
        return evaluar(sii -> 
            sii.getPrices().stream()
            .filter(itemPricing -> itemPricing.getConditionType().equals(ConstFacturacion.CONDICION_PRECIO_BRUTO))
            .filter(pricing -> pricing.getConditionCurrency().equals(ConstFacturacion.CONDITION_CURRENCY_CONTRATOS))
            .findAny().isPresent(),
            "Tiene moneda ZFI");
    }

    static SolIndItemValidation hasContrato() {
        return evaluar(sii -> sii.getSolicitudInd().getContrato() != null,
            "Tiene contrato");
    }

    static SolIndItemValidation hasContratoAndZFI() {
        return evaluar(sii -> 
            sii.getPrices().stream()
            .filter(itemPricing -> itemPricing.getConditionType().equals(ConstFacturacion.CONDICION_PRECIO_BRUTO))
            .filter(pricing -> pricing.getConditionCurrency().equals(ConstFacturacion.CONDITION_CURRENCY_CONTRATOS))
            .findAny().isPresent() ? sii.getSolicitudInd().getContrato() != null ? true : false : true,
            "Tiene moneda ZFI pero no hay contrato");
    }

    static SolIndItemValidation evaluar(Predicate<SolIndItems> p, String message) {
        return sii -> p.test(sii) ? ValidationResult.valid() : ValidationResult.invalid(message);
    }

    default Map<String, ValidationResult> applyAll(SolIndItems t, List<SolIndItemValidation> validations) {
        HashMap<String, ValidationResult> resultados = new HashMap<String, ValidationResult>();
        int i = 0;
        for(SolIndItemValidation v: validations){
            i++;
            resultados.put("Validation" + i, v.apply(t));
        };
        return resultados;
    }

}
