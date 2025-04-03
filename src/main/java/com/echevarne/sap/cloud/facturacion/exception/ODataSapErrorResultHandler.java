package com.echevarne.sap.cloud.facturacion.exception;

import javax.annotation.Nonnull;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.json.JsonSanitizer;
import com.sap.cloud.sdk.odatav2.connectivity.ODataException;
import com.sap.cloud.sdk.odatav2.connectivity.ODataExceptionType;
import com.sap.cloud.sdk.odatav2.connectivity.ODataQuery;
import com.sap.cloud.sdk.odatav2.connectivity.internal.DefaultErrorResultHandler;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
@Slf4j
public class ODataSapErrorResultHandler
		extends DefaultErrorResultHandler<ODataSapErrorResultHandler.ErpODataException> {
	
	private static final String ERROR_JSON_ELEMENT = "error";
	private static final String INNER_ERROR_JSON_ELEMENT = "innererror";
	private static final String ERROR_DETAILS_JSON_ARRAY = "errordetails";
	private static final String ERROR_MESSAGES_JSON_ELEMENT = "message";
	private static final String ERROR_CODE_JSON_ELEMENT = "code";

	@NoArgsConstructor
	public static class ErpODataException extends ODataException {
		private static final long serialVersionUID = -1995254562234140438L;

		@Setter
		@Getter
		private transient ODataQuery query;

		private void setHttpStatusCode(final int httpStatusCode) {
			this.httpStatusCode = httpStatusCode;
		}

		protected ErpODataException(final String message) {
			super(ODataExceptionType.OTHER, message);
		}
	}

	@Override
	protected Class<ErpODataException> getExceptionType() {
		return ErpODataException.class;
	}

	/**
	 * This method takes an error in the form of an error message, an error JSON or
	 * an HTML document, the origin (i.e. the query that caused it) and the HTTP
	 * status code and produces an {@link ErpODataException}.
	 *
	 * @param message        The error message as String.
	 * @param origin         The query that caused the error.
	 * @param httpStatusCode The HTTP status code returned with the error.
	 *
	 * @return An {@link ErpODataException}.
	 */
	@Override
	@Nonnull
	public ErpODataException createError(final String message, final Object origin, final int httpStatusCode) {
		final boolean isHtml = message.trim().startsWith("<");
		final boolean isJson = message.trim().startsWith("{");

		String shortMessage = "";
		@SuppressWarnings("unused")
		String fullMessage = "";
		if (isHtml) {
			shortMessage = getMessageFromHtml(message);
			fullMessage = message;
		} else if (isJson) {
			shortMessage = getMessageFromJson(message);
			fullMessage = getPrettyPrintedJson(message);
		} else {
			fullMessage = message;
		}

		final ErpODataException exception = new ErpODataException();
		exception.setMessage(shortMessage);
		exception.setCode(String.valueOf(httpStatusCode));
		exception.setHttpStatusCode(httpStatusCode);

		if (origin instanceof ODataQuery) {
			exception.setQuery((ODataQuery) origin);
		} else if (origin == null) {
			log.error(this.getClass().getName()+"::createError(): httpStatusCode="+httpStatusCode+" message= "+message);
		}

		return exception;
	}
	
	/**
	 * 
	 * @param message
	 * @return
	 */
	private String getPrettyPrintedJson(final String message) {
		return new GsonBuilder().setPrettyPrinting().create()
				.toJson(JsonParser.parseString(JsonSanitizer.sanitize(message)));
	}

    /**
     * This method extracts the short error message from the complete error JSON returned by an OData endpoint.
     *
     * @param errorJson
     *            The error JSON returned by the OData endpoint.
     * @return The short error message extracted from the JSON.
     */
    private String getMessageFromJson( final String errorJson )
    {
        try {
            final JsonElement jsonElement = JsonParser.parseString(JsonSanitizer.sanitize(errorJson));
            final JsonArray errorDetails = jsonElement.getAsJsonObject().get(ERROR_JSON_ELEMENT).getAsJsonObject().get(INNER_ERROR_JSON_ELEMENT).getAsJsonObject().get(ERROR_DETAILS_JSON_ARRAY).getAsJsonArray(); 
            return getFromMessagesArray(errorDetails);
        }
        catch( Exception e ) {
        	return errorJson;
        }
     }
    
    private String getFromMessagesArray(JsonArray errorDetailsArray) 
    { 
    	String mensajes = "";
    	for (JsonElement msg : errorDetailsArray) {
            	String textoMsg = msg.getAsJsonObject()
	              .get(ERROR_MESSAGES_JSON_ELEMENT).toString();
            	String errorCode = msg.getAsJsonObject()
      	              .get(ERROR_CODE_JSON_ELEMENT).toString();
                mensajes = mensajes.concat(errorCode)
            		   .concat(" - ")
            		   .concat(textoMsg)
            		   .replaceAll("\"", "")
            		   .replaceAll("\\$", "")
               		   .concat("\n");
        }
        return mensajes;
    }

    /**
     * This method creates a readable error message from an HTML error document returned by an OData endpoint.
     *
     * @param htmlDocument
     *            The HTML document returned by the OData endpoint.
     * @return A readable error message parsed from the document.
     */
    private String getMessageFromHtml( final String htmlDocument )
    {
        final org.jsoup.nodes.Document document = Jsoup.parse(htmlDocument);
        final String title = document.title();
        String errorTextHeader = "";
        for( final Element element : document.getElementsByClass("errorTextHeader") ) {
            errorTextHeader = element.text();
        }
        return title + ". " + errorTextHeader + ". ";
    }
}
