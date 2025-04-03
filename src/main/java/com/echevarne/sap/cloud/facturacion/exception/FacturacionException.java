package com.echevarne.sap.cloud.facturacion.exception;

import java.text.MessageFormat;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.echevarne.sap.cloud.facturacion.constants.ConstFacturacion;
import com.echevarne.sap.cloud.facturacion.model.ErrorMessages;
import com.echevarne.sap.cloud.facturacion.model.Messages;
import com.echevarne.sap.cloud.facturacion.services.ErrorMessagesService;
import com.echevarne.sap.cloud.facturacion.services.MessagesService;
import com.echevarne.sap.cloud.facturacion.util.MessagesUtils;

/**
 * A helper class to manage and generate customized / RuntimeExceptions.
 * 
 * @author Hernan Girardi
 * @since 15/04/2020
 */
@Component
public class FacturacionException {
		
	private static ErrorMessagesService errorMessagesSrv;
	private static MessagesService messagesSrv;

	@Autowired
	public FacturacionException(ErrorMessagesService errorMessagesSrv, MessagesService messagesSrv) {
		FacturacionException.errorMessagesSrv = errorMessagesSrv;
		FacturacionException.messagesSrv = messagesSrv;
	}
	
	public static RuntimeException createException(Throwable ex) {
		return new RuntimeException(ex);
	}

	/**
	 * Returns new RuntimeException based on EntityType, ExceptionType and args
	 *
	 * @param entityType
	 * @param exceptionType
	 * @param lang
	 * @return
	 */
	public static RuntimeException createException(EntityType entityType, ExceptionType exceptionType, String lang) {
		ErrorMessages messageTemplate = getMessageTemplate(entityType, exceptionType, lang);
		return createException(exceptionType, messageTemplate);
	}

	/**
	 * Returns new RuntimeException based on EntityType, ExceptionType and args and an Entity.Id
	 *
	 * @param entityType
	 * @param exceptionType
	 * @param keyMessage
	 * @param lang
	 * @return
	 */
	public static RuntimeException createExceptionWithKey(EntityType entityType, ExceptionType exceptionType, String keyMessage, String lang) {
		ErrorMessages messageTemplate = getMessageTemplate(entityType, exceptionType, keyMessage, lang);
		return createException(exceptionType, messageTemplate);
	}

	public static RuntimeException createExceptionWithKey(
			EntityType entityType, ExceptionType exceptionType, String keyMessage, String lang, String... params) {
		ErrorMessages messageTemplate = getMessageTemplate(entityType, exceptionType, keyMessage, lang, params);
		return createException(exceptionType, messageTemplate);
	}

	public static BusinessException createException(String keyMessage, String lang, String... params) {
		ErrorMessages messageTemplate = getMessageTemplate(keyMessage, lang, params);
		return new BusinessException(messageTemplate);
	}

	/**
	 * @param errorCode
	 * @param exceptionType
	 * @param lang
	 * @return
	 */
	public static RuntimeException createException(String errorCode, ExceptionType exceptionType, String lang) {
		ErrorMessages messageTemplate = getMessageTemplate(errorCode, lang);
		return createException(exceptionType, messageTemplate);
	}

	/**
	 * @param errorCode
	 * @param exceptionType
	 * @return
	 */
	public static RuntimeException createException(String errorCode, ExceptionType exceptionType) {
		return createException(errorCode, exceptionType, ConstFacturacion.IDIOMA_DEFAULT.toLowerCase());
	}
	
	/**
	 * 
	 * Excepción mediante lista de mensajes
	 * 
	 * @param messages
	 * @return
	 */


	public static RuntimeException throwExceptionMessageNotFound(String keyNotFound, String lang) {
		if (lang==null)
			lang = ConstFacturacion.IDIOMA_DEFAULT.toLowerCase();
		
		ErrorMessages messageTemplate = getNotFoundMessageTemplate(keyNotFound, lang);
		return createException(ExceptionType.ENTITY_NOT_FOUND, messageTemplate);
	}

	/**
	 * Returns new RuntimeException based on template and args
	 *
	 * @param messageTemplate
	 * @param exceptionType
	 * @return
	 */
	private static RuntimeException createException(ExceptionType exceptionType, ErrorMessages messageTemplate) {
		switch (exceptionType) {
		case ENTITY_NOT_FOUND:
			return new EntityNotFoundException(messageTemplate);
		case DUPLICATE_ENTITY:
			return new DuplicateEntityException(messageTemplate);
		case BUSINESS_EXCEPTION:
			return new BusinessException(messageTemplate);
		default:
			return new RuntimeException("Default - Not hanlded Exception!!!");
		}
	}
	
	private static ErrorMessages getMessageTemplate(EntityType entityType, ExceptionType exceptionType, String lang) {
		return getMessageTemplate(entityType, exceptionType, null, lang);
	}
		
	private static ErrorMessages getMessageTemplate(
			EntityType entityType, ExceptionType exceptionType, String messageKey, String lang, String... params) {
		String templateWithKey = entityType.name().concat(".").concat(exceptionType.getValue()).toLowerCase();
		if(messageKey != null)
			templateWithKey = templateWithKey.concat(".").concat(messageKey);
		return getMessageTemplate(templateWithKey, lang, params);
	}

	private static ErrorMessages getMessageTemplate(String template, String lang, String... params) {
		Optional<ErrorMessages> templateContent = Optional.ofNullable(errorMessagesSrv.getByMessageKey(template, lang));
		if (templateContent.isPresent()) {
			return format(templateContent.get(), params);
		}
		return defaultErrorMessage(template);
	}

	private static ErrorMessages getNotFoundMessageTemplate(String keyNotFound, String lang) {
		
		String templateKey = MessagesUtils.MSGDEFAULT;
		Optional<Messages> msgBase = messagesSrv.getMessageByKey(templateKey, lang);
		ErrorMessages templateContent = new ErrorMessages();
		templateContent.setErrorCode("0100");
		if(msgBase.isPresent()){
			msgBase.get().setDescription("ERROR!");
			templateContent.setDetails(MessageFormat.format(msgBase.get().getDescription(), keyNotFound));
			templateContent.setMessage(msgBase.get());
		}
		return templateContent;
	}

	private static ErrorMessages format(ErrorMessages errorMsg, String... params) {
		errorMsg.setDetails(errorMsg.getDetails());	// aca hay que formatear con parametros ???
		Messages msg = errorMsg.getMessage();
		if(params.length > 0) {
			msg.setDescription(MessageFormat.format(msg.getDescription(), params));
		} else {
			msg.setDescription(msg.getDescription());
		}
		errorMsg.setMessage(msg);
		return errorMsg;
	}

	private static ErrorMessages defaultErrorMessage(String template) {
		ErrorMessages result = new ErrorMessages();		
		result.setDetails("defaultErrorMessage()");
		return result;
	}

	
	// Customized Exceptions
	@SuppressWarnings("serial")
	public static class EntityNotFoundException extends RuntimeException {
		public ErrorMessages customCause;
		public EntityNotFoundException(ErrorMessages errorMsg) {
			super(errorMsg.getMessage().getDescription());
			customCause = errorMsg;
		}
	}

	@SuppressWarnings("serial")
	public static class DuplicateEntityException extends RuntimeException {
		public ErrorMessages customCause;
		public DuplicateEntityException(ErrorMessages errorMsg) {
			super(errorMsg.getMessage().getDescription());
			customCause = errorMsg;	
		}
	}

	@SuppressWarnings("serial")
	public static class BusinessException extends RuntimeException {
		public ErrorMessages customCause;
		public BusinessException(ErrorMessages errorMsg) {
			super(errorMsg.getMessage().getDescription());
			customCause = errorMsg;			
		}
	}
	

	
	@SuppressWarnings("serial")
	public static class AsyncException extends RuntimeException {
		public AsyncException(Throwable ex) {
			super(ex.getMessage());
		}
	}

}