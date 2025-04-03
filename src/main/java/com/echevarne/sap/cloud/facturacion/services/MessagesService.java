package com.echevarne.sap.cloud.facturacion.services;

import java.util.Optional;

import com.echevarne.sap.cloud.facturacion.model.Messages;

/**
 * Class for services {@link MessagesService}.
 * <p>Services for the bussiness logic of the Model: {@link Messages}</p>
 *  
 * @author Hernan Girardi
 * @since 20/04/2020
 */
public interface MessagesService extends CrudService<Messages, Long> {
	
	Optional<Messages> getMessageByKey(String msgKey, String lang);
	Optional<Messages> getMessageByErrorCode(String msgErrorCode, String lang);
	Messages getDefaultMessageNotFound();
}
