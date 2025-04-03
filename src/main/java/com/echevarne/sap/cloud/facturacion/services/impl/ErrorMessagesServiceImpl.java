package com.echevarne.sap.cloud.facturacion.services.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.echevarne.sap.cloud.facturacion.constants.ConstFacturacion;
import com.echevarne.sap.cloud.facturacion.exception.EntityType;
import com.echevarne.sap.cloud.facturacion.exception.ExceptionType;
import com.echevarne.sap.cloud.facturacion.model.ErrorMessages;
import com.echevarne.sap.cloud.facturacion.model.Messages;
import com.echevarne.sap.cloud.facturacion.repositories.ErrorMessagesRep;
import com.echevarne.sap.cloud.facturacion.services.ErrorMessagesService;
import com.echevarne.sap.cloud.facturacion.services.MessagesService;

@Service("errorMessagesSrv")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class ErrorMessagesServiceImpl extends CrudServiceImpl<ErrorMessages, Long> implements ErrorMessagesService {

	private final ErrorMessagesRep errorMessagesRep;

	private final MessagesService messagesSrv;

	@Autowired
	public ErrorMessagesServiceImpl(ErrorMessagesRep errorMessagesRep, MessagesService messagesSrv) {
		super(errorMessagesRep);
		this.errorMessagesRep = errorMessagesRep;
		this.messagesSrv = messagesSrv;
	}

	@Override
	public ErrorMessages getByMessageKey(String msgKey, String lang) {
		if (lang != null) {
			return getByMessageKeyAndLang(msgKey, lang);
		} else {
			return getByMessageKey(msgKey);
		}
	}

	private ErrorMessages getByMessageKey(String msgKey) {
		Optional<Messages> msg = messagesSrv.getMessageByKey(msgKey, ConstFacturacion.IDIOMA_DEFAULT.toLowerCase());
		if (msg.isPresent()) {
			Optional<ErrorMessages> errMsg = errorMessagesRep.findByMessage(msg.get());
			if (errMsg.isPresent()) {
				return errMsg.get();
			} else {
				ErrorMessages err = new ErrorMessages();
				err.setMessage(messagesSrv.getDefaultMessageNotFound());
				return err;
			}
		} else {
			ErrorMessages err = new ErrorMessages();
			err.setMessage(messagesSrv.getDefaultMessageNotFound());
			return err;
		}
	}

	private ErrorMessages getByMessageKeyAndLang(String msgKey, String lang) {
		Optional<Messages> msg = messagesSrv.getMessageByKey(msgKey, lang);
		if (msg.isPresent()) {
			Optional<ErrorMessages> errMsg = errorMessagesRep.findByMessage(msg.get());
			if (errMsg.isPresent()) {
				return errMsg.get();
			} else {
				ErrorMessages err = new ErrorMessages();
				err.setMessage(messagesSrv.getDefaultMessageNotFound());
				return err;
			}
		} else {
			ErrorMessages err = new ErrorMessages();
			err.setMessage(messagesSrv.getDefaultMessageNotFound());
			return err;
		}
	}

	@Override
	public ErrorMessages findByErrorCode(String errorCode) {
		Optional<ErrorMessages> errMsg = errorMessagesRep.findByErrorCode(errorCode);
		if (!errMsg.isPresent())
			throw exception(EntityType.ERRORMESSAGES, ExceptionType.ENTITY_NOT_FOUND);
		return errMsg.get();
	}

	@Override
	public ErrorMessages findByMessage(Messages msg) {
		Optional<ErrorMessages> errMsg = errorMessagesRep.findByMessage(msg);
		if (!errMsg.isPresent())
			throw exception(EntityType.ERRORMESSAGES, ExceptionType.ENTITY_NOT_FOUND);
		return errMsg.get();
	}
}
