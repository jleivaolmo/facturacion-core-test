package com.echevarne.sap.cloud.facturacion.services.impl;

import java.util.Optional;

import com.echevarne.sap.cloud.facturacion.constants.ConstFacturacion;
import com.echevarne.sap.cloud.facturacion.model.Messages;
import com.echevarne.sap.cloud.facturacion.repositories.MessagesRep;
import com.echevarne.sap.cloud.facturacion.services.MessagesService;
import com.echevarne.sap.cloud.facturacion.util.MessagesUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Hernan Girardi
 * @since 20/04/2020
 */
@Service("messagesSrv")
// @Transactional(propagation = Propagation.REQUIRED, rollbackFor =
// Exception.class)
public class MessagesServiceImpl extends CrudServiceImpl<Messages, Long> implements MessagesService {

	// private static final String DEFAULT_SUCCESS = "Success Process!";

	private final MessagesRep messagesRep;

	@Autowired
	public MessagesServiceImpl(final MessagesRep messagesRep){
		super(messagesRep);
		this.messagesRep = messagesRep;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public Optional<Messages> getMessageByKey(String msgKey, String lang) {

		Optional<Messages> msg;
		if (lang != null)
			msg = messagesRep.findByMsgKeyAndLang(msgKey, lang.toLowerCase());
		else
			msg = messagesRep.findByMsgKeyAndLang(msgKey, ConstFacturacion.IDIOMA_DEFAULT.toLowerCase());

		if (msg.isPresent())
			return msg;
		else
			return getNotFoundMessage(msgKey, lang.toLowerCase());

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public Optional<Messages> getMessageByErrorCode(String msgErrorCode, String lang) {
		Optional<Messages> msg;
		if (lang != null)
			msg = messagesRep.findByErrorCodeAndLang(msgErrorCode, lang.toLowerCase());
		else
			msg = messagesRep.findByErrorCodeAndLang(msgErrorCode, ConstFacturacion.IDIOMA_DEFAULT.toLowerCase());
		if (msg.isPresent())
			return msg;
		else
			return getNotFoundMessage(msgErrorCode, lang.toLowerCase());
	}

	@Override
	public Messages getDefaultMessageNotFound() {
		Messages msgErrResponse = new Messages();
		msgErrResponse.setDescription("No se encontró el mensaje de error");
		return msgErrResponse;
	}

	/**
	 * Default message
	 */
	private Optional<Messages> getNotFoundMessage(String msgErrorCode, String lang) {
		String templateKey = MessagesUtils.MSGDEFAULT;
		Optional<Messages> msgBase = messagesRep.findByMsgKeyAndLang(templateKey, lang);
		//Esto no se tiene que hacer aquí, o se modificaría el mensaje de base de datos. Mas adelante ya se formatea el mensaje
		//if (msgBase.isPresent()) {
		//	msgBase.get().setDescription(MessageFormat.format(msgBase.get().getDescription(), msgErrorCode));
		//}
		return msgBase;
	}

}
