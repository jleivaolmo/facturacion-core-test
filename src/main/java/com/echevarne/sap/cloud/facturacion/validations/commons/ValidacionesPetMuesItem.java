package com.echevarne.sap.cloud.facturacion.validations.commons;

import java.util.ArrayList;
import java.util.List;

import com.echevarne.sap.cloud.facturacion.gestionestados.Bloqueada;
import com.echevarne.sap.cloud.facturacion.model.masterdata.MasDataEstado;
import com.echevarne.sap.cloud.facturacion.model.solicitudrecibida.PeticionMuestreoItems;
import com.echevarne.sap.cloud.facturacion.validations.BasicValidation;
import com.echevarne.sap.cloud.facturacion.validations.ValidationResult;

import org.springframework.stereotype.Component;

@Component
public class ValidacionesPetMuesItem extends BasicValidation implements PetMuesItemValidation {

	private static final String NO_APLICA = "No se han cumplido las condiciones.";

    public ValidacionesPetMuesItem() {}
	
	protected static List<PetMuesItemValidation> getValidationsActives() {
    	List<PetMuesItemValidation> validaciones = new ArrayList<PetMuesItemValidation>();
    	return validaciones;
    }

    public ValidationResult puedeDesbloquear(PeticionMuestreoItems peticionMuestreoItem, MasDataEstado origen) {
		if(!origen.getCodeEstado().equals(Bloqueada.CODIGO)) return ValidationResult.valid();  
		boolean bloqueoAutomatico = peticionMuestreoItem.isBloqueoAutomatico();
        // return bloqueoAutomatico ? ValidationResult.invalid("Sigue bloqueada") : ValidationResult.valid();
        return ValidationResult.valid();
    }

    @Override
    public ValidationResult apply(PeticionMuestreoItems entidad) {
        setResultados(applyAll(entidad, getValidationsActives()));
		boolean isValid = getResultados().values().stream().filter(vr -> !vr.isValid()).count() > 0;
		if (isValid) return ValidationResult.valid();
		return ValidationResult.invalid(NO_APLICA);
    }
    
}
