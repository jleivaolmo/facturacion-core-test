package com.echevarne.sap.cloud.facturacion.exception;

import java.util.Set;
import com.echevarne.sap.cloud.facturacion.constants.ConstFacturacion;
import com.echevarne.sap.cloud.facturacion.dto.response.FacturacionResponses;
import com.echevarne.sap.cloud.facturacion.dto.response.Response;
import com.echevarne.sap.cloud.facturacion.dto.response.ResponseError;
import com.echevarne.sap.cloud.facturacion.exception.FacturacionException.BusinessException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AbstractExceptionHandler {

	
	
	public static RuntimeException exception(Throwable ex) {
       return FacturacionException.createException(ex);
    }

	 /**
     * Returns a new RuntimeException
     *
     * @param entityType {@link EntityType}
     * @param exceptionType {@link ExceptionType}
     * @param lang {@link String}
     * @return
     */
    public static RuntimeException exception(EntityType entityType, ExceptionType exceptionType, String lang) {
        return FacturacionException.createException(entityType, exceptionType, lang);
    }

    public static RuntimeException exception(EntityType entityType, ExceptionType exceptionType) {
        return FacturacionException.createException(entityType, exceptionType, ConstFacturacion.IDIOMA_DEFAULT.toLowerCase());
    }

    /**
     * Returns a new RuntimeException with a {@code keyMessage} + {@code exception.getMessage()} as customized message
     *
     * @param entityType {@link EntityType}
     * @param exceptionType {@link ExceptionType}
     * @param keyMessage
     * @param lang
     * @return
     */
    public static RuntimeException exceptionWithKey(EntityType entityType, ExceptionType exceptionType, String keyMessage, String lang) {
        return FacturacionException.createExceptionWithKey(entityType, exceptionType, keyMessage, lang);
    }

    public static RuntimeException exceptionWithKey(EntityType entityType, ExceptionType exceptionType, String keyMessage) {
        return FacturacionException.createExceptionWithKey(entityType, exceptionType, keyMessage, ConstFacturacion.IDIOMA_DEFAULT.toLowerCase());
    }

    public static RuntimeException exceptionWithKey(EntityType entityType, ExceptionType exceptionType, String keyMessage, String... params) {
        return FacturacionException.createExceptionWithKey(entityType, exceptionType, keyMessage, ConstFacturacion.IDIOMA_DEFAULT.toLowerCase(), params);
    }

    public static RuntimeException exceptionMessageNotFound(String keyNotFound, String lang) {
    	return FacturacionException.throwExceptionMessageNotFound(keyNotFound, lang);
    }

    public static BusinessException exceptionWithKey(String keyMessage, String... params) {
        return FacturacionException.createException(keyMessage, ConstFacturacion.IDIOMA_DEFAULT.toLowerCase(), params);
    }

    /**
	 *
	 * @param <T>
	 * @param body
	 * @param msg
	 * @param args
	 * @return
	 */
	public static <T> ResponseEntity<Response<?>> buildResponse(T body, String msg, String... args) {
		return FacturacionResponses.buildResponse(body, msg, args);
	}

	public static <T> ResponseEntity<Response<?>> buildResponseError(ResponseError error) {
		return FacturacionResponses.buildResponseError(error);
	}

	
	/**
	 *
	 * Convertimos la entidad a un JSON para ser interpretada por UI5
	 *
	 */
	public String convertToJson(Object object) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.writeValueAsString(object);
		} catch (JsonProcessingException e) {
			log.error("Errror al convertir el objeto en JSON (convertToJson)", e);
			return "";
		}
	}
	
}
