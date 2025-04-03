package com.echevarne.sap.cloud.facturacion.validations;

import java.util.Map;
import java.util.Optional;

public abstract class BasicValidation {
	
	private Map<String, ValidationResult> resultadosValidaciones;
	
	public Map<String, ValidationResult> getResultados(){
		return this.resultadosValidaciones;
	}
	
	protected void setResultados(Map<String, ValidationResult> resultados) {
		resultadosValidaciones = resultados;
	}
	
	protected static ValidationResult setValidationResult(boolean resultado, String message) {
		return  new ValidationResult(){
            public boolean isValid(){ return resultado; }
            public Optional<String> getReason(){ return Optional.of(message); }
        };
	}

}