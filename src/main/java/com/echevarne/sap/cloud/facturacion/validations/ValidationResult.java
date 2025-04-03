package com.echevarne.sap.cloud.facturacion.validations;

import java.util.Optional;

public interface ValidationResult {

	static ValidationResult valid(){
        return ValidationSupport.valid();
    }
    
    static ValidationResult invalid(String reason){
        return new Invalid(reason);
    }
    
    boolean isValid();
    
    //el texto del mensaje Reason debe estar properties por i18n
    Optional<String> getReason();
   
}