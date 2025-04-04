package com.echevarne.sap.cloud.facturacion.services;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.Map;
import java.util.Set;

import com.echevarne.sap.cloud.facturacion.dto.ParamPoolFacturacion;
import com.echevarne.sap.cloud.facturacion.model.BasicMessagesEntity;
import com.echevarne.sap.cloud.facturacion.model.facturacion.PeriodosFacturados;

public interface PeriodosFacturadosService extends CrudService<PeriodosFacturados, Long> {

	/**
	 * 
	 * Almacena el periodo realizado en la facturación con log de mensajes
	 * 
	 * @param <T>
	 * @param periodo
	 * @param messages
	 */
	<T extends BasicMessagesEntity> void savePeriodo(PeriodosFacturados periodo, Set<T> messages);

	/**
	 * 
	 * Almacena el periodo realizado en la facturación
	 *
	 * @param periodo
	 */
	void saveAndFlushPeriodo(PeriodosFacturados periodo);

	public PeriodosFacturados crearPeriodo(Set<String> codigosCliente, Date fechaInicioFacturacion, Date fechaFinFacturacion, Set<String> organizacionesVentas,
			Map<Integer, Set<String>> agrupaciones, Long idAgrupacion, ParamPoolFacturacion paramPool, String operacion, BigDecimal monto, String codUsuario,
			String nombreUsuario);

	public void validarPlanificacion(PeriodosFacturados periodo);

	public Map<Integer, Set<String>> getAgrupacionesPeriodo(PeriodosFacturados periodo);

	public ParamPoolFacturacion getParamPoolPeriodo(PeriodosFacturados periodo);

	public void setEstadoPeriodo(Long idPeriodo, Integer estado);

	public boolean procesableMemReservada(Long idPeriodo, String instanceUUID);

	public void registrarLogPlanificadoBatch(Long idLog, String codUsuario, String nombreUsuario) throws ParseException;

}
