package com.echevarne.sap.cloud.facturacion.exception;

import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.echevarne.sap.cloud.facturacion.dto.response.Response;
import com.echevarne.sap.cloud.facturacion.exception.FacturacionException.AsyncException;
import com.echevarne.sap.cloud.facturacion.exception.FacturacionException.BusinessException;
import com.echevarne.sap.cloud.facturacion.exception.FacturacionException.DuplicateEntityException;
import com.echevarne.sap.cloud.facturacion.exception.FacturacionException.EntityNotFoundException;
import com.echevarne.sap.cloud.facturacion.services.MessagesService;
import com.sap.cloud.sdk.odatav2.connectivity.ODataException;

/**
 * A class for handle all controllers exception and manage http response
 * 
 * @author Hernan Girardi
 * @since 15/04/2020
 */

@ControllerAdvice
@RestController
public class FacturacionResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

	@Autowired
	private MessagesService msgSrv;

	@ExceptionHandler(FacturacionException.EntityNotFoundException.class)
	public final ResponseEntity<Response<?>> handleNotFountExceptions(EntityNotFoundException ex) {
		Response<?> response = new Response(msgSrv).notFound();
		response.addErrorMsgToResponse(ex.customCause);
		return new ResponseEntity<Response<?>>(response, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(FacturacionException.DuplicateEntityException.class)
	public final ResponseEntity<Response<?>> handleDuplicateEntityException(DuplicateEntityException ex) {
		Response<?> response = new Response(msgSrv).badRequest();
		response.addErrorMsgToResponse(ex.customCause);
		return new ResponseEntity<Response<?>>(response, HttpStatus.CONFLICT);
	}

	@ExceptionHandler(BusinessException.class)
	public final ResponseEntity<Response<?>> handleEntityValidationException(BusinessException ex) {
		Response<?> response = new Response(msgSrv).businessException();
		response.addErrorMsgToResponse(ex.customCause);
		return new ResponseEntity<Response<?>>(response, HttpStatus.EXPECTATION_FAILED);
	}
	
	// Excepcion genérica
	@ExceptionHandler(Exception.class)
	public final ResponseEntity<Response<?>> handleException(Exception ex) {
		Response<?> response = new Response(msgSrv).exception();
		response.addErrorMsgToResponse(ex);
		return new ResponseEntity<Response<?>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	// Excepcion Rest Template
	@ExceptionHandler(ODataException.class)
	public final ResponseEntity<Response<?>> handleODataException(ODataException ex) {
		Response<?> response = new Response(msgSrv).exception();
		response.addErrorMsgToResponse(ex);
		return new ResponseEntity<Response<?>>(response, HttpStatus.FAILED_DEPENDENCY);
	}
	
	// Excepcion Rest Template
	@ExceptionHandler(RestClientResponseException.class)
	public final ResponseEntity<Response<?>> handleRestClientException(RestClientResponseException ex) {
		Response<?> response = new Response(msgSrv).exception();
		response.addErrorMsgToResponse(ex);
		return new ResponseEntity<Response<?>>(response, HttpStatus.FAILED_DEPENDENCY);
	}
	
	// Excepcion Async
	@ExceptionHandler(AsyncException.class)
	public final ResponseEntity<Response<?>> handleAsyncException(AsyncException ex) {
		Response<?> response = new Response(msgSrv).exception();
		response.addErrorMsgToResponse(ex);
		return new ResponseEntity<Response<?>>(response, HttpStatus.FAILED_DEPENDENCY);
	}
	
	// Customize inherited exceptions handlers
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		return buildResponseEntity(ex, status);
	}
	
	@Override
	protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		return buildResponseEntity(ex, status);
	}

	@Override
	protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		return buildResponseEntity(ex, status);
	}

	@Override
	protected ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(HttpMediaTypeNotAcceptableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		return buildResponseEntity(ex, status);
	}

	@Override
	protected ResponseEntity<Object> handleMissingPathVariable(MissingPathVariableException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		return buildResponseEntity(ex, status);
	}

	@Override
	protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		return buildResponseEntity(ex, status);
	}

	@Override
	protected ResponseEntity<Object> handleServletRequestBindingException(ServletRequestBindingException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		return buildResponseEntity(ex, status);
	}

	@Override
	protected ResponseEntity<Object> handleConversionNotSupported(ConversionNotSupportedException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		return buildResponseEntity(ex, status);
	}

	@Override
	protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		return buildResponseEntity(ex, status);
	}

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotWritable(HttpMessageNotWritableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		return buildResponseEntity(ex, status);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		return buildResponseEntity(ex, status);
	}

	@Override
	protected ResponseEntity<Object> handleMissingServletRequestPart(MissingServletRequestPartException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		return buildResponseEntity(ex, status);
	}

	@Override
	protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatus status,
			WebRequest request) {
		return buildResponseEntity(ex, status);
	}

	@Override
	protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		return buildResponseEntity(ex, status);
	}

	@Override
	protected ResponseEntity<Object> handleAsyncRequestTimeoutException(AsyncRequestTimeoutException ex,
			HttpHeaders headers, HttpStatus status, WebRequest webRequest) {
		return buildResponseEntity(ex, status);
	}

	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		return buildResponseEntity(ex, status);
	}
	
	private ResponseEntity<Object> buildResponseEntity(Exception ex, HttpStatus status) {
		Response response = new Response(msgSrv).badRequest();
		response.addErrorMsgToResponse(ex);
       	return new ResponseEntity<>(response, status);
   }
}
