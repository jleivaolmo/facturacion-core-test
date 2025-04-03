package com.echevarne.sap.cloud.facturacion.validations;

import java.util.Optional;

final class ValidationSupport {
    private static final ValidationResult valid = new ValidationResult(){
        public boolean isValid(){ return true; }
        public Optional<String> getReason(){ return Optional.of("Ok"); }
    };

    static ValidationResult valid(){
        return valid;
    }
}