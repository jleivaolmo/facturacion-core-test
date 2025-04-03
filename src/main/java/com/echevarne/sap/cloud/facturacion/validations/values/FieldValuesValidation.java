package com.echevarne.sap.cloud.facturacion.validations.values;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.echevarne.sap.cloud.facturacion.model.BasicEntity;
import com.echevarne.sap.cloud.facturacion.validations.BasicValidation;
import com.echevarne.sap.cloud.facturacion.validations.ValidationResult;

import org.springframework.stereotype.Component;

@Component("validacionesFieldValue")
public class FieldValuesValidation extends BasicValidation implements FieldValuesFunctional {

    private static final String NO_APLICA = "No se han cumplido las condiciones.";

    protected static List<FieldValuesFunctional> getValidationsActives() {
        List<FieldValuesFunctional> validaciones = new ArrayList<>();
        return validaciones;
    }

    public ValidationResult validateAllFields(Object entidad, Map<String, Object> fields) {
        if(entidad instanceof BasicEntity){
            BasicEntity basicEntity = (BasicEntity) entidad;
            List<FieldValuesFunctional> validaciones = new ArrayList<>();
            for (Map.Entry<String, Object> field : fields.entrySet()) {
                validaciones.add(FieldValuesFunctional.validateFieldsValues(basicEntity, field.getKey(), field.getValue()));
            }
            setResultados(applyAll(basicEntity, validaciones));
            boolean isValid = getResultados().values().stream().filter(vr -> !vr.isValid()).count() > 0;
            if (isValid)
                return ValidationResult.valid();
            return ValidationResult.invalid(NO_APLICA);

        }else{
            return ValidationResult.valid(); 
        }
	}

    @Override
    public ValidationResult apply(BasicEntity entidad) {
        setResultados(applyAll(entidad, getValidationsActives()));
        boolean isValid = getResultados().values().stream().filter(vr -> !vr.isValid()).count() > 0;
        if (isValid)
            return ValidationResult.valid();
        return ValidationResult.invalid(NO_APLICA);

    }

}