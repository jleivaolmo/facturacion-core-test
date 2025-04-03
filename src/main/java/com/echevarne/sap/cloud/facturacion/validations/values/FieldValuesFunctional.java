package com.echevarne.sap.cloud.facturacion.validations.values;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

import com.echevarne.sap.cloud.facturacion.model.BasicEntity;
import com.echevarne.sap.cloud.facturacion.validations.ValidationResult;

@FunctionalInterface
public interface FieldValuesFunctional extends Function<BasicEntity, ValidationResult> {

    static FieldValuesFunctional evaluar(Predicate<BasicEntity> p, String message) {
        return si -> p.test(si) ? ValidationResult.valid() : ValidationResult.invalid(message);
    }

    static FieldValuesFunctional validateFieldsValues(BasicEntity entidad, String field, Object value) {
        // TODO cambiar el true por la validacion de cada campo contra el repositorio que toque
        return evaluar(basic -> true, "El valor es incorrecto");
    }

    default Map<String, ValidationResult> applyAll(BasicEntity t, List<FieldValuesFunctional> validations) {
        HashMap<String, ValidationResult> resultados = new HashMap<String, ValidationResult>();
        int i = 0;
        for (FieldValuesFunctional v : validations) {
            i++;
            resultados.put("Validation" + i, v.apply(t));
        }
        ;
        return resultados;
    }

}
