package com.echevarne.sap.cloud.facturacion.services.impl;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Timestamp;
import java.text.MessageFormat;

import com.echevarne.sap.cloud.facturacion.model.Log;
import com.echevarne.sap.cloud.facturacion.repositories.LogRep;
import com.echevarne.sap.cloud.facturacion.services.LogService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("logSrv")
@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
public class LogServiceImpl extends CrudServiceImpl<Log, Long> implements LogService {

	private final String errorLevel;

	@Autowired
	public LogServiceImpl(@Value("${local.log.error.level}") final String errorLevel, final LogRep logRep){
		super(logRep);
		this.errorLevel = errorLevel;
	}

	/**
	 * To print errors
	 */
	@Override
	public void log(String level, String module, String className, String message, Throwable throwable) {

		if(errorLevel.equals(level)) {
			Log log = fill(level, module, className,message, throwable);
			super.create(log);
		}

	}

	/**
	 * To print errors
	 */
	@Override
	public void log(String level, String module, String className, String message,
			Throwable throwable, String codigoPeticion) {

		Log log = fill(level, module, className,message, throwable);
		log.setCodigoPeticion(codigoPeticion);
		super.create(log);

	}

	private Log fill(String level, String module, String className, String message, Throwable throwable) {

		Log log = new Log();

		log.setTimestamp(new Timestamp(System.currentTimeMillis()));
		log.setLevel(level);
		log.setModule(module);
		log.setClassName(className);
		if (message != null && message.length() > 255) {
			message = message.substring(0, 255);
		}
		log.setMessage(message);
//		getStack(throwable);
		//log.setStack(getStack(throwable));

		return log;

	}

	/**
	 * Get full stack of the error
	 */
	@SuppressWarnings("unused")
	private String getStack(Throwable throwable) {
		if (throwable == null)
			return null;

		log.error("Ops!", throwable);

		StringWriter errors = new StringWriter();
		throwable.printStackTrace(new PrintWriter(errors));
		return errors.toString();
	}

	@Override
	public String format(String message, Object ... arguments) {
		return MessageFormat.format(message, arguments);
	}

}
