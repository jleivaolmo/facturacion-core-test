package com.echevarne.sap.cloud.facturacion.validations.commons;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

import com.echevarne.sap.cloud.facturacion.gestionestados.util.EstadosUtils;
import com.echevarne.sap.cloud.facturacion.model.masterdata.MasDataEstado;
import com.echevarne.sap.cloud.facturacion.model.solicitudrecibida.PeticionMuestreo;
import com.echevarne.sap.cloud.facturacion.validations.ValidationResult;

@FunctionalInterface
public interface PetMuesValidation extends Function<PeticionMuestreo, ValidationResult> {
    
    static PetMuesValidation evaluar(Predicate<PeticionMuestreo> p, String message) {
        return pm -> p.test(pm) ? ValidationResult.valid() : ValidationResult.invalid(message);
    }

    static PetMuesValidation hasAllItemsStatus(MasDataEstado estado) {
        return evaluar(pm -> EstadosUtils.aplicaEstadoPadre(estado, pm)
        , "Todas las pruebas de la petición estan en facturables.");
    }

    default Map<String, ValidationResult> applyAll(PeticionMuestreo t, List<PetMuesValidation> validations) {
        HashMap<String, ValidationResult> resultados = new HashMap<String, ValidationResult>();
        int i = 0;
        for(PetMuesValidation v: validations){
            i++;
            resultados.put("Validation" + i, v.apply(t));
        };
        return resultados;
    }

}
