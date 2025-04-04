package com.echevarne.sap.cloud.facturacion.services.impl;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.stream.Collectors;

import com.echevarne.sap.cloud.facturacion.model.divisores.SplitedBy;
import com.echevarne.sap.cloud.facturacion.model.divisores.Splittable;
import com.echevarne.sap.cloud.facturacion.model.reglasfacturacion.ReglasFacturacion;
import com.echevarne.sap.cloud.facturacion.reflection.ObjectReflectionUtil;
import com.echevarne.sap.cloud.facturacion.services.SplitterService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("splitterService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class SplitterServiceImpl implements SplitterService {

	private final ObjectReflectionUtil objectReflectionUtil;

	@Autowired
	public SplitterServiceImpl(ObjectReflectionUtil objectReflectionUtil){
		this.objectReflectionUtil = objectReflectionUtil;
	}

	@Override
	public String getSplitterKey(String idRegla, Splittable entity, final Splittable reglaForFind) {
		// Obtener la configuracion de division de factura para la entindad
		// ConfiguracionDivisorFactura configuracion = entity.getSplitter();
		return entity != null ? buildSplitterKey(idRegla, reglaForFind, entity) : idRegla;
	}

	// Por cada campo de configuracion en true
	// obtener el valor de Entity.field indicado en configuracion.facturaPorXXXX con @SplitedBy
	// y armar la key concatenendo esos valores y agregando un codigo de campo para evitar colisiones
	String buildSplitterKey(String idRegla, Object entity, Splittable regla) {
		String byConfig = Arrays.stream(regla.getClass().getDeclaredFields())
				.filter(reglaField -> isSplittedByActiveForField((ReglasFacturacion) regla, reglaField))
				.map(reglaField -> getValue(entity, reglaField))
				.collect(Collectors.joining("_"));
		return StringUtils.isEmpty(byConfig) ? "id:" + idRegla : byConfig + "_id:" + idRegla;

	}

	String getValue(Object entity, Field configurationField) {
		try {
			String entityFieldName = configurationField.getAnnotation(SplitedBy.class).field();
			String entityFieldCode = configurationField.getAnnotation(SplitedBy.class).code();

			final Object fieldValue = objectReflectionUtil.getFieldValueFromObject(entity, entityFieldName);
			String value = entityFieldCode + ":" + fieldValue;
			//Los valores vacíos devuelven error, se añade un espacio en blanco si procede
			if (value.endsWith(":")) {
				value = value + " ";
			}
			return value;
		} catch (NoSuchFieldException | IllegalAccessException | InvocationTargetException e) {
			return "";
		}
	}

	boolean isSplittedByActiveForField(ReglasFacturacion regla, Field reglaField) {
		boolean isSplittedByActive = false;

		try {
			if (reglaField.getAnnotation(SplitedBy.class) != null) {
				isSplittedByActive = (boolean) objectReflectionUtil.getFieldValueFromObject(regla, reglaField.getName());
			}
		} catch (IllegalAccessException | NoSuchFieldException | InvocationTargetException e) {
			// Nothing to do
		}

		return isSplittedByActive;
	}

}
