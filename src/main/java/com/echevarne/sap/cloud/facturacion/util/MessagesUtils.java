package com.echevarne.sap.cloud.facturacion.util;

import java.text.MessageFormat;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.echevarne.sap.cloud.facturacion.constants.ConstFacturacion;
import com.echevarne.sap.cloud.facturacion.exception.EntityType;
import com.echevarne.sap.cloud.facturacion.exception.ExceptionType;
import com.echevarne.sap.cloud.facturacion.model.BasicMessagesEntity;
import com.echevarne.sap.cloud.facturacion.model.Messages;
import com.echevarne.sap.cloud.facturacion.model.facturacion.LogFacturacion;
import com.echevarne.sap.cloud.facturacion.model.masterdata.MasDataMessagesGrupo;
import com.echevarne.sap.cloud.facturacion.model.trazabilidad.TrazabilidadSolicitudMessages;
import com.echevarne.sap.cloud.facturacion.services.MasDataMessagesGrupoService;
import com.echevarne.sap.cloud.facturacion.services.MessagesService;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class MessagesUtils<T> {

	public static final String MSGDEFAULT = EntityType.MESSAGES.name().concat(".")
			.concat(ExceptionType.ENTITY_NOT_FOUND.getValue()).toLowerCase();
	public static final String TYPE_ABORT = "A";
	public static final String TYPE_ERROR = "E";
	public static final String TYPE_INFORMATION = "I";
	public static final String TYPE_WARNING = "W";
	public static final String TYPE_SUCCESS = "S";

	public static MessagesService messagesSrv;

	public static MasDataMessagesGrupoService masDataMessagesGrupoServicio;

	private static final AtomicBoolean testMode = new AtomicBoolean(false);

	private static final Map<String,Set<BasicMessagesEntity>> messagesCache = new ConcurrentHashMap<>();

	@SuppressWarnings("static-access")
	@Autowired
	private void loadServices(MessagesService messagesService) {
		this.messagesSrv = messagesService;
	}

	@SuppressWarnings("static-access")
	@Autowired
	public final void setMasDataMessagesGrupoService(MasDataMessagesGrupoService masDataMessagesGrupoSrv) {
		this.masDataMessagesGrupoServicio = masDataMessagesGrupoSrv;
	}

	public static void setTestMode(boolean testM) {
		testMode.set(testM);
	}

	public static boolean getTestMode() {
		return testMode.get();
	}

	public static <T extends BasicMessagesEntity>  void addMessagesCache(String keyId, Set<T> messages) {
		if (testMode.get()) {
			if (!messagesCache.containsKey(keyId)) {
				messagesCache.put(keyId, (Set<BasicMessagesEntity>) messages);
			}
		}
	}

	public static <T extends BasicMessagesEntity>  Set<BasicMessagesEntity> getMessagesCache(String keyId) {
		return messagesCache.get(keyId);
	}

	@SuppressWarnings("unchecked")
	public static <T extends BasicMessagesEntity> T createEntity(Set<T> messages)
			throws InstantiationException, IllegalAccessException {
		return (T) new BasicMessagesEntity();
	}

	public static <T extends BasicMessagesEntity> void addMessage(Set<T> messages, String messageCode, String variable1,
			String variable2, String variable3, String variable4, String messageType) {
		if (messageCode == null) {
			log.warn("Null messageCode! Message won't be added.");
			if(log.isDebugEnabled()){
				Thread.dumpStack();
			}

			return;
		}
		Optional<Messages> message = messagesSrv.getMessageByErrorCode(messageCode, ConstFacturacion.IDIOMA_DEFAULT);
		if (message.isPresent()) {
			BasicMessagesEntity msg;
			try {
				msg = createEntity(messages);
				msg.setType(messageType);
				msg.setMessages(message.get());
				if (message.get().getMsgKey().equals(MSGDEFAULT))
					msg.setVar1(messageCode);
				else
					msg.setVar1(variable1);
				msg.setVar2(variable2);
				msg.setVar3(variable3);
				msg.setVar4(variable4);
				msg.setSequenceOrder(getOrder(messages));
				Optional<MasDataMessagesGrupo> grupo = masDataMessagesGrupoServicio.findByCodeGrupo(messageCode.substring(0, 2));
				grupo.ifPresent(msg::setGrupo);

				messages.add((T) msg);
			} catch (InstantiationException | IllegalAccessException e) {
				log.error("Ops!", e);
			}
		}
	}

	/**
	 *
	 * Returns order of messages
	 */
	private static <T extends BasicMessagesEntity> int getOrder(Set<T> messages) {
		Optional<T> maxValue = messages.stream().max(Comparator.comparing(BasicMessagesEntity::getSequenceOrder));
		return maxValue.isPresent() ? maxValue.get().getSequenceOrder() + 1 : 1;
	}

	public static <T extends BasicMessagesEntity> void addMessage(Set<T> messages, String messageCode,
			String messageType) {
		addMessage(messages, messageCode, null, null, null, null, messageType);
	}

	public static <T extends BasicMessagesEntity> void addError(Set<T> messages, String messageCode) {
		addMessage(messages, messageCode, null, null, null, null, TYPE_ERROR);
	}

	public static <T extends BasicMessagesEntity> void addAbort(Set<T> messages, String messageCode) {
		addMessage(messages, messageCode, null, null, null, null, TYPE_ABORT);
	}

	public static <T extends BasicMessagesEntity> void addSuccess(Set<T> messages, String messageCode) {
		addMessage(messages, messageCode, null, null, null, null, TYPE_SUCCESS);
	}

	public static <T extends BasicMessagesEntity> void addInformation(Set<T> messages, String messageCode) {
		addMessage(messages, messageCode, null, null, null, null, TYPE_INFORMATION);
	}

	public static <T extends BasicMessagesEntity> void addWarning(Set<T> messages, String messageCode) {
		addMessage(messages, messageCode, null, null, null, null, TYPE_WARNING);
	}

	public static <T extends BasicMessagesEntity> void addInformation(Set<T> messages, String messageCode,
			String variable1, String variable2, String variable3, String variable4) {
		addMessage(messages, messageCode, variable1, variable2, variable3, variable4, TYPE_INFORMATION);
	}

	public static <T extends BasicMessagesEntity> void addAbort(Set<T> messages, String messageCode, String variable1,
			String variable2, String variable3, String variable4) {
		addMessage(messages, messageCode, variable1, variable2, variable3, variable4, TYPE_ABORT);
	}

	public static <T extends BasicMessagesEntity> void addError(Set<T> messages, String messageCode, String variable1,
			String variable2, String variable3, String variable4) {
		addMessage(messages, messageCode, variable1, variable2, variable3, variable4, TYPE_ERROR);
	}

	public static <T extends BasicMessagesEntity> void addSuccess(Set<T> messages, String messageCode, String variable1,
			String variable2, String variable3, String variable4) {
		addMessage(messages, messageCode, variable1, variable2, variable3, variable4, TYPE_SUCCESS);
	}

	public static <T extends BasicMessagesEntity> void addWarning(Set<T> messages, String messageCode, String variable1,
			String variable2, String variable3, String variable4) {
		addMessage(messages, messageCode, variable1, variable2, variable3, variable4, TYPE_WARNING);
	}

	public static <T extends BasicMessagesEntity> void addException(Set<T> messages, String messageCode, Exception ex) {
		addMessage(messages, messageCode, messageFromException(ex), null, null, null, TYPE_ERROR);
	}

	public static <T extends BasicMessagesEntity> void addException(Set<T> messages, String messageCode, Exception ex,
			String variable1, String variable2, String variable3) {
		addMessage(messages, messageCode, variable1, variable2, variable3, messageFromException(ex), TYPE_ERROR);
	}

	public static <T extends BasicMessagesEntity> void addException(Set<T> messages, String messageCode, Exception ex,
			String variable1, String variable2) {
		addMessage(messages, messageCode, variable1, variable2, messageFromException(ex), null, TYPE_ERROR);
	}

	public static <T extends BasicMessagesEntity> void addException(Set<T> messages, String messageCode, Exception ex,
			String variable1) {
		addMessage(messages, messageCode, variable1, messageFromException(ex), null, null, TYPE_ERROR);
	}

	public static <T extends BasicMessagesEntity> void changeMessage(Set<T> messages, String messageCode,
			String variable1, String variable2, String variable3, String variable4) {
		messages.stream().filter(msg -> msg.getMessages().getErrorCode().equals(messageCode)).findAny()
				.ifPresent(msg -> {
					msg.setVar1(variable1);
					msg.setVar2(variable2);
					msg.setVar3(variable3);
					msg.setVar4(variable4);
				});
	}

	/**
	 *
	 * Obtiene el mensaje desde una excepción
	 *
	 */
	public static String messageFromException(Exception ex) {
		String result = "";
		if (ex != null) {
			result = ex.getMessage();
		} else {
			return result;
		}
		return result.substring(0, Math.min(result.length(), 200));
	}

	public static <T extends BasicMessagesEntity> boolean haveErrors(Set<T> messages) {
		return messages.stream().anyMatch(x -> x.getType().equals(TYPE_ERROR));
	}

	/**
	 *
	 * @param errors
	 * @return
	 */
	public static <T extends BasicMessagesEntity> String messagesToJson(Set<T> errors) {
		final StringBuilder messageBuilder = new StringBuilder();
		errors.forEach(x -> {
			Messages msgBase = x.getMessages();
			String shortMessage = MessageFormat.format(msgBase.getDescription(), x.getVar1(), x.getVar2(), x.getVar3(),
					x.getVar4());
			messageBuilder.append("Error code ").append(x.getMessages().getErrorCode()).append(": ")
					.append(shortMessage).append(".\n");

		});
		return messageBuilder.toString();
	}

	public static <T extends BasicMessagesEntity> Set<TrazabilidadSolicitudMessages> mapMessagesToTrazSolMessages(Set<T> messages) {
		Set<TrazabilidadSolicitudMessages> trzMessages = new HashSet<>();
		messages.forEach(msg -> {
			TrazabilidadSolicitudMessages trzMessage = new TrazabilidadSolicitudMessages();
			trzMessage.setType(msg.getType());
			trzMessage.setVar1(msg.getVar1());
			trzMessage.setVar2(msg.getVar2());
			trzMessage.setVar3(msg.getVar3());
			trzMessage.setVar4(msg.getVar4());
			trzMessage.setSequenceOrder(msg.getSequenceOrder());
			trzMessage.setMessages(msg.getMessages());
			trzMessages.add(trzMessage);
		});
		return trzMessages;
	}

	public static <T extends BasicMessagesEntity> Set<LogFacturacion> mapMessagesToLogFacturacion(Set<T> messages) {
		Set<LogFacturacion> logFacturacion = new HashSet<LogFacturacion>();
		messages.forEach(msg -> {
			LogFacturacion logMessage = new LogFacturacion();
			logMessage.setType(msg.getType());
			logMessage.setVar1(msg.getVar1());
			logMessage.setVar2(msg.getVar2());
			logMessage.setVar3(msg.getVar3());
			logMessage.setVar4(msg.getVar4());
			logMessage.setSequenceOrder(msg.getSequenceOrder());
			logMessage.setMessages(msg.getMessages());
			logFacturacion.add(logMessage);
		});
		return logFacturacion;
	}

}
