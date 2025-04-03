package com.echevarne.sap.cloud.facturacion.validations.dto;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.echevarne.sap.cloud.facturacion.validations.ValidationResult;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Component
public class ResultadoValidacion {
	
	private ValidationResult resultado;
	private Map<String, ValidationResult> errores;
	
	public ValidationResult getResultado() {
		return resultado;
	}
	public void setResultado(ValidationResult resultado) {
		this.resultado = resultado;
	}
	public Map<String, ValidationResult> getErrores() {
		return errores;
	}
	public void setErrores(Map<String, ValidationResult> errores) {
		this.errores = errores;
	}
	
}
