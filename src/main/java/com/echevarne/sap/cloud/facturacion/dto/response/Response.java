package com.echevarne.sap.cloud.facturacion.dto.response;

import java.text.MessageFormat;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.echevarne.sap.cloud.facturacion.exception.FacturacionException.AsyncException;
import com.echevarne.sap.cloud.facturacion.model.ErrorMessages;
import com.echevarne.sap.cloud.facturacion.model.Messages;
import com.echevarne.sap.cloud.facturacion.services.MessagesService;
import com.echevarne.sap.cloud.facturacion.util.DateUtils;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sap.cloud.sdk.odatav2.connectivity.ODataException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Response<T> {
	
	private static final String DEFAULT_MESSAGE = "default";
	private static final String DEFAULT_MESSAGE_CODE = "0000";
	private Status status;
	private String message;
    private T responseBody;
    private ResponseError errors;
    
    // not @Autowired because manually create new instances
    private static MessagesService msgSrv;

	public Response() {
	}

	public Response(MessagesService messagesService) {
    	msgSrv = messagesService;
	}
    
	public Status getStatus() {
		return this.status;
	}

	public Response<T> setStatus(Status status) {
		this.status = status;
		return this;
	}
	
    public String getMessage() {
		return message;
	}
    
    public void setMessage(String message) {
		this.message = message;
	}

	private String templateMessage(String msg) {
    	if (msg.isEmpty()) {
    		return DEFAULT_MESSAGE;
    	}
    	return msg.concat(".").concat(this.getStatus().toString()).toLowerCase();
    }

	private Response<T> setMessage(String msg, String lang, String... args) {
		String message = StringUtils.EMPTY;
		String template = templateMessage(msg);
		Optional<Messages> templateContent = Optional.empty();
		templateContent = msgSrv.getMessageByErrorCode(msg, lang);
		if (templateContent.isPresent() && templateContent.get().getErrorCode().equals(DEFAULT_MESSAGE_CODE)) {
			templateContent = msgSrv.getMessageByKey(template, lang);
		} 
		if (templateContent.isPresent()) {
			message = templateContent.get().getDescription();
			if (templateContent.get().getErrorCode().equals(DEFAULT_MESSAGE_CODE)) {
				this.message = MessageFormat.format(message, template);
			} else {
				this.message = MessageFormat.format(message, args);
			}	
		}
		return this;
	}
	
	private Response<T> setMessage(Throwable ex) {
		this.message = ex.getMessage();
		return this;
	}
	
	public T getResponseBody() {
		return this.responseBody;
	}

	public Response<T> setResponseBody(T responseBody) {
		this.responseBody = responseBody;
		return this;
	}

	public ResponseError getErrors() {
		return this.errors;
	}

	public void setErrors(ResponseError errors) {
		this.errors = errors;
	}
	
    public Response<T> badRequest() {
        this.setStatus(Status.BAD_REQUEST);
        return this;
    }

    public Response<T> success(String msg,String lang, String...args) {
        this.setStatus(Status.SUCCESS);
        this.setMessage(msg,lang,args);
        return this;
    }

	public Response<T> simpleResponse(String msg) {
		this.setStatus(Status.SUCCESS);
		this.message = msg;
		return this;
	}

    public Response<T> unauthorized() {
        this.setStatus(Status.UNAUTHORIZED);
        return this;
    }

    public Response<T> businessException() {
        this.setStatus(Status.BUSINESS_EXCEPTION);
        return this;
    }
    
    public Response<T> businessException(Throwable ex) {
        this.setStatus(Status.BUSINESS_EXCEPTION);
        this.setMessage(ex);
        return this;
    }

	public Response<T> businessException(String msgCode,String lang, String...args) {
		this.setStatus(Status.BUSINESS_EXCEPTION);
		this.setMessage(msgCode,lang,args);
		return this;
	}

    public Response<T> exception() {
        this.setStatus(Status.EXCEPTION);
        return this;
    }

    public Response<T> notFound() {
        this.setStatus(Status.NOT_FOUND);
        return this;
    }

    public void addErrorMsgToResponse(ErrorMessages ex) {
        ResponseError error = new ResponseError();
		error.setMessage(ex.getMessage().getDescription());
		error.setDetails(ex.getDetails());
		error.setCode(ex.getErrorCode());
        error.setTimestamp(DateUtils.today());
        setErrors(error);
    }
    
    public void addErrorMsgToResponse(Exception ex) {
    	String messageException = "Exception";
    	String codeException = "E001";
    	String messageDetail = ex.getMessage();
        ResponseError error = new ResponseError();
         
        if (ex instanceof ODataException){
        	String[] parsedMsg = ex.getMessage().split("\n",3);
        	messageException = parsedMsg[1];
        	messageDetail = parsedMsg[2].split(":",2)[1];
	    	// HARCODE ERROR GENERICO SAP : Ex 0400
	    	codeException = "ERROR_SAP_#";
        }
        else if (ex instanceof HttpRequestMethodNotSupportedException) {
        	messageException = "HttpRequestMethodNotSupportedException";
        	codeException = "E002";
		}
		else if (ex instanceof HttpMediaTypeNotSupportedException) {
			messageException = "HttpMediaTypeNotSupportedException";
        	codeException = "E003";
		}
		else if (ex instanceof HttpMediaTypeNotAcceptableException) {
			messageException = "HttpMediaTypeNotAcceptableException";
        	codeException = "E004";
		}
		else if (ex instanceof MissingPathVariableException) {
			messageException = "MissingPathVariableException";
        	codeException = "E005";
		}
		else if (ex instanceof MissingServletRequestParameterException) {
			messageException = "MissingServletRequestParameterException";
        	codeException = "E006";
		}
		else if (ex instanceof ServletRequestBindingException) {
			messageException = "ServletRequestBindingException";
        	codeException = "E007";
		}
		else if (ex instanceof ConversionNotSupportedException) {
			messageException = "ConversionNotSupportedException";
        	codeException = "E008";
		}
		else if (ex instanceof TypeMismatchException) {
			messageException = "TypeMismatchException";
        	codeException = "E009";
		}
		else if (ex instanceof HttpMessageNotReadableException) {
			messageException = "HttpMessageNotReadableException";
        	codeException = "E010";
		}
		else if (ex instanceof HttpMessageNotWritableException) {
			messageException = "HttpMessageNotWritableException";
        	codeException = "E011";
		}
		else if (ex instanceof MethodArgumentNotValidException) {
			messageException = "MethodArgumentNotValidException";
        	codeException = "E012";
		}
		else if (ex instanceof MissingServletRequestPartException) {
			messageException = "MissingServletRequestPartException";
        	codeException = "E013";
		}
		else if (ex instanceof BindException) {
			messageException = "BindException";
        	codeException = "E014";
		}
		else if (ex instanceof NoHandlerFoundException) {
			messageException = "NoHandlerFoundException";
        	codeException = "E015";
		}
		else if (ex instanceof AsyncRequestTimeoutException) {
			messageException = "AsyncRequestTimeoutException";
        	codeException = "E016";
		}
		else if (ex instanceof AsyncException) {
			messageException = "AsyncExceptionHandler::AsyncException";
	        codeException = "E998";
		}
		else if (ex instanceof JpaSystemException) {
			messageException = "JPA Error";
	        codeException = "E020";
        }
		else if (ex instanceof RestClientResponseException) {
			String responseString = ((RestClientResponseException) ex).getResponseBodyAsString();
			 ObjectMapper mapper = new ObjectMapper();
			 try {
				Response restResponse = mapper.readValue(responseString,Response.class);
				setErrors(restResponse.getErrors());
				return;
			} catch (JsonProcessingException e) {
				messageException = "RestClientResponseException::JsonProcessingException";
	        	codeException = "E999";
			}
		}
        error.setMessage(messageException);
        error.setDetails(messageDetail);
       	//para poder revisar traza completa del error
       	log.error("Ops!: " + ex, ex);
   		error.setCode(codeException);	
        error.setTimestamp(DateUtils.today());
        setErrors(error);
    }
    
    public enum Status {
        SUCCESS, BAD_REQUEST, UNAUTHORIZED, BUSINESS_EXCEPTION, EXCEPTION, NOT_FOUND, ACCEPTED
    }
}