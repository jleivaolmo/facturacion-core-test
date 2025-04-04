package com.echevarne.sap.cloud.facturacion.services.impl;

import java.text.MessageFormat;
import java.util.Optional;

import com.echevarne.sap.cloud.facturacion.constants.ConstFacturacion;
import com.echevarne.sap.cloud.facturacion.model.Messages;
import com.echevarne.sap.cloud.facturacion.model.solicitudindividual.SolIndItems;
import com.echevarne.sap.cloud.facturacion.model.solicitudrecibida.SolicitudMuestreo;
import com.echevarne.sap.cloud.facturacion.model.trazabilidad.TrazabilidadSolicitudMessages;
import com.echevarne.sap.cloud.facturacion.repositories.TrazabilidadSolicitudMessagesRep;
import com.echevarne.sap.cloud.facturacion.services.MessagesService;
import com.echevarne.sap.cloud.facturacion.services.TrazabilidadSolicitudMessagesService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("trzSolMessagesSrv")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class TrazabilidadSolicitudMessagesServiceImpl extends CrudServiceImpl<TrazabilidadSolicitudMessages, Long> implements TrazabilidadSolicitudMessagesService {

	private static final String TYPE_ABORT = "A";
	private static final String TYPE_ERROR = "E";
	private static final String TYPE_INFORMATION = "I";
	private static final String TYPE_WARNING = "W";
	private static final String TYPE_SUCCESS = "S";

	private static final String OP_CREATE = "create";
	private static final String OP_UPDATE = "update";
	private static final String OP_TRANSFORM = "transform";

	private static final String PHASE_INIT = "init";
	private static final String PHASE_SOL_IND = "solind";
	private static final String PHASE_SIMUL = "simul";
	private static final String PHASE_TIPOLOGIA_CLASIF = "tipologiaclasif";
	private static final String PHASE_CLASIF = "clasif";
	private static final String PHASE_END = "end";

	private static final String STATUS_ERROR = "error";
	private static final String STATUS_SUCCESS = "success";

	private final MessagesService messagesSrv;


	@Autowired
	public TrazabilidadSolicitudMessagesServiceImpl(
			MessagesService messagesSrv,
			TrazabilidadSolicitudMessagesRep trzSolMessagesRep
	) {
		super(trzSolMessagesRep);
		this.messagesSrv = messagesSrv;
	}

	@Override
	public TrazabilidadSolicitudMessages getInitTransform(String codigoPeticion) {
		return getInfoMsg(codigoPeticion, OP_TRANSFORM, PHASE_INIT);
	}

	@Override
	public TrazabilidadSolicitudMessages getSolIndTransform(String codigoPeticion) {
		return getSuccessMsg(codigoPeticion, OP_TRANSFORM, PHASE_SOL_IND);
	}

	@Override
	public TrazabilidadSolicitudMessages getSimul(String codigoPeticion) {
		return getSuccessMsg(codigoPeticion, OP_TRANSFORM, PHASE_SIMUL);
	}

	@Override
	public TrazabilidadSolicitudMessages getClasif(String codigoPeticion) {
		return getSuccessMsg(codigoPeticion, OP_TRANSFORM, PHASE_CLASIF);
	}

	@Override
	public TrazabilidadSolicitudMessages getFinTransform(String codigoPeticion) {
		return getSuccessMsg(codigoPeticion, OP_TRANSFORM, PHASE_END);
	}

	@Override
	public TrazabilidadSolicitudMessages getErrorClasif(String codigoPeticion, SolIndItems item) {
		TrazabilidadSolicitudMessages trzSolMsg = new TrazabilidadSolicitudMessages();
		trzSolMsg.setType(TYPE_ERROR);
		String msgKey = SolicitudMuestreo.class.getSimpleName().concat(".").concat(OP_TRANSFORM).concat(".").concat(PHASE_CLASIF).concat(".").concat(STATUS_ERROR).toLowerCase();
		Optional<Messages> msg = messagesSrv.getMessageByKey(msgKey, ConstFacturacion.IDIOMA_DEFAULT.toLowerCase());
		trzSolMsg.setMessages(msg.get());
		trzSolMsg.setVar1(codigoPeticion);
		trzSolMsg.setVar2(item.getId().toString());
		return trzSolMsg;
	}

	@Override
	public TrazabilidadSolicitudMessages getErrorTipologiaClasif(String codigoPeticion, SolIndItems item) {
		TrazabilidadSolicitudMessages trzSolMsg = new TrazabilidadSolicitudMessages();
		trzSolMsg.setType(TYPE_ERROR);
		String msgKey = SolicitudMuestreo.class.getSimpleName().concat(".").concat(OP_TRANSFORM).concat(".").concat(PHASE_TIPOLOGIA_CLASIF).concat(".").concat(STATUS_ERROR).toLowerCase();
		Optional<Messages> msg = messagesSrv.getMessageByKey(msgKey , ConstFacturacion.IDIOMA_DEFAULT.toLowerCase());
		trzSolMsg.setMessages(msg.get());
		trzSolMsg.setVar1(codigoPeticion);
		trzSolMsg.setVar2(item.getId().toString());
		return trzSolMsg;
	}

	@Override
	public TrazabilidadSolicitudMessages getInitCreate(String codigoPeticion) {
		return getInfoMsg(codigoPeticion, OP_CREATE, PHASE_INIT);
	}

	@Override
	public TrazabilidadSolicitudMessages getUpdate(String codigoPeticion) {
		return getSuccessMsg(codigoPeticion, OP_UPDATE, "");
	}

	@Override
	public TrazabilidadSolicitudMessages getCreate(String codigoPeticion) {
		return getSuccessMsg(codigoPeticion, OP_CREATE, "");
	}

	private TrazabilidadSolicitudMessages getInfoMsg(String codigoPeticion, String op, String fase) {
		TrazabilidadSolicitudMessages trzSolMsg = new TrazabilidadSolicitudMessages();
		trzSolMsg.setType(TYPE_INFORMATION);
		String msgKey = SolicitudMuestreo.class.getSimpleName().concat(".").concat(op).concat(".").concat(fase).toLowerCase();
		Optional<Messages> msg = messagesSrv.getMessageByKey(msgKey , ConstFacturacion.IDIOMA_DEFAULT.toLowerCase());
		trzSolMsg.setMessages(msg.get());
		trzSolMsg.setVar1(codigoPeticion);
		return trzSolMsg;
	}

	private TrazabilidadSolicitudMessages getSuccessMsg(String codigoPeticion, String op, String fase) {
		TrazabilidadSolicitudMessages trzSolMsg = new TrazabilidadSolicitudMessages();
		trzSolMsg.setType(TYPE_SUCCESS);
		String msgKey = null;
		if (fase.isEmpty())
		{
			msgKey = SolicitudMuestreo.class.getSimpleName().concat(".").concat(op).concat(".").concat(STATUS_SUCCESS).toLowerCase();
		}
		else
		{
			msgKey = SolicitudMuestreo.class.getSimpleName().concat(".").concat(op).concat(".").concat(fase).concat(".").concat(STATUS_SUCCESS).toLowerCase();
		}
		Optional<Messages> msg = messagesSrv.getMessageByKey(msgKey , ConstFacturacion.IDIOMA_DEFAULT.toLowerCase());
		trzSolMsg.setMessages(msg.get());
		trzSolMsg.setVar1(codigoPeticion);
		return trzSolMsg;
	}

	@Override
	public String getTextMessage(TrazabilidadSolicitudMessages trzSolMessages) {
		return MessageFormat.format(trzSolMessages.getMessages().getDescription(), trzSolMessages.getVar1(), trzSolMessages.getVar2(), trzSolMessages.getVar3(), trzSolMessages.getVar4());
	}



}
