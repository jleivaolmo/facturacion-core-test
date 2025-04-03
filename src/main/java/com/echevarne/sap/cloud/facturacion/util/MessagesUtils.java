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
import com.echevarne.sap.cloud.facturacion.model.masterdata.MasDataMessagesGrupo;

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

	

	private static final AtomicBoolean testMode = new AtomicBoolean(false);

	private static final Map<String,Set<BasicMessagesEntity>> messagesCache = new ConcurrentHashMap<>();

	@SuppressWarnings("static-access")
	@Autowired
	private void loadServices(MessagesService messagesService) {
		this.messagesSrv = messagesService;
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

	

	/**
	 *
	 * Returns order of messages
	 */
	private static <T extends BasicMessagesEntity> int getOrder(Set<T> messages) {
		Optional<T> maxValue = messages.stream().max(Comparator.comparing(BasicMessagesEntity::getSequenceOrder));
		return maxValue.isPresent() ? maxValue.get().getSequenceOrder() + 1 : 1;
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

	

	

}
