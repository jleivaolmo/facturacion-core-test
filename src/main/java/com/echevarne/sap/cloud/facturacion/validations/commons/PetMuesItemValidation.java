package com.echevarne.sap.cloud.facturacion.validations.commons;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

import com.echevarne.sap.cloud.facturacion.model.solicitudrecibida.PeticionMuestreoItems;
import com.echevarne.sap.cloud.facturacion.validations.ValidationResult;

@FunctionalInterface
public interface PetMuesItemValidation extends Function<PeticionMuestreoItems, ValidationResult> {

    static PetMuesItemValidation evaluar(Predicate<PeticionMuestreoItems> p, String message) {
        return sii -> p.test(sii) ? ValidationResult.valid() : ValidationResult.invalid(message);
    }

    default Map<String, ValidationResult> applyAll(PeticionMuestreoItems t, List<PetMuesItemValidation> validations) {
        HashMap<String, ValidationResult> resultados = new HashMap<String, ValidationResult>();
        int i = 0;
        for(PetMuesItemValidation v: validations){
            i++;
            resultados.put("Validation" + i, v.apply(t));
        };
        return resultados;
    }

}
