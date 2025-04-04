package com.echevarne.sap.cloud.facturacion.util;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.echevarne.sap.cloud.facturacion.model.divisores.SplitedBy;

public class SplitterUtils {
	
	/**
	 * 
	 * @param clazz
	 * @param fieldName
	 * @param splitterKey
	 * @return
	 */
	public static String getFieldValueInSplitterKey(Class<?> clazz, String fieldName, String splitterKey) {
		try {
			if (StringUtils.isNullOrEmpty(splitterKey) || splitterKey.equals(StringUtils.ANY))
				return "";
			Map<String, String> splitterKeys = Stream.of(splitterKey.split("_")).map((x) -> x.split(":"))
					.collect(Collectors.toMap(str -> str[0], str -> str[1]));
			Optional<Field> spliterField = Arrays.stream(clazz.getDeclaredFields())
					.filter(field -> field.getName().equals(fieldName)).findAny();
			if (spliterField.isPresent()) {
				String entityFieldCode = spliterField.get().getAnnotation(SplitedBy.class).code();
				return splitterKeys.get(entityFieldCode) != null ? splitterKeys.get(entityFieldCode) : "";
			}
			return "";
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static Optional<Long> getIdFromDivisor(String splitterKey) {
		try {
			if (StringUtils.isNullOrEmpty(splitterKey) || splitterKey.equals(StringUtils.ANY))
				return Optional.empty();
			int fieldIndex = splitterKey.indexOf("id");
			int fieldValueEnd = splitterKey.indexOf("_", fieldIndex);
			if (fieldValueEnd == -1) {
				fieldValueEnd = splitterKey.length();
			}
			String fieldValue = splitterKey.substring(fieldIndex, fieldValueEnd);
			String[] splitFieldValue = fieldValue.split(":");
			return Optional.of(Long.parseLong(splitFieldValue[1]));
		} catch (Exception e) {
			return Optional.empty();
		}
	}
}
