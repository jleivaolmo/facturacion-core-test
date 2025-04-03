package com.echevarne.sap.cloud.facturacion.services;

import com.echevarne.sap.cloud.facturacion.model.ErrorMessages;
import com.echevarne.sap.cloud.facturacion.model.Messages;

/**
 * Class for services {@link ErrorMessagesService}.
 * <p>Services for the bussiness logic of the Model: {@link ErrorMessages}</p>
 *  
 * @author Hernan Girardi
 * @since 20/04/2020
 */
public interface ErrorMessagesService extends CrudService<ErrorMessages, Long> {
	public ErrorMessages findByErrorCode(String errorCode);
	public ErrorMessages findByMessage(Messages msg);
	public ErrorMessages getByMessageKey(String msgKey, String lang);
}
