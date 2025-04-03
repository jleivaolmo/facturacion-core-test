package com.echevarne.sap.cloud.facturacion.gestioncambios;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.Table;


/**
 *
 * @author Steven Mendez Brenes
 *
 */
public class AnnotationUtil {

	public static List<String> getUniqueIndexFields(Class<?> clazz) {

		List<String> ids = new ArrayList<>();
		Table table = clazz.getDeclaredAnnotation(Table.class);

		if (table != null && table.indexes().length != 0) {
			final Map<String, String> columnNameToFieldNameMap = createColumnNameToFieldNameMap(clazz);

			for (Index index : table.indexes()) {
				if (index.unique()) {
					for (String id : index.columnList().split(",")) {
						String fieldName = columnNameToFieldNameMap.getOrDefault(id, id);
						ids.add(fieldName);
					}
					break;
				}
			}
		}

		return ids;
	}

	private static Map<String, String> createColumnNameToFieldNameMap(Class<?> clazz) {
		Map<String, String> columnNameToFieldNameMap = new HashMap<>();

		Class<?> superClass = clazz.getSuperclass();
		if (superClass != null && !Object.class.equals(superClass)) {
			Map<String, String> superColumnNameToFieldNameMap = createColumnNameToFieldNameMap(superClass);
			columnNameToFieldNameMap.putAll(superColumnNameToFieldNameMap);
		}

		for(Field field: clazz.getDeclaredFields()) {
			field.setAccessible(true);

			String columnName = null;

			final Column column = field.getAnnotation(Column.class);
			if (column != null) {
				if (!column.name().isEmpty()){
					columnName = column.name();
				}
			}

			if (columnName == null) {
				final JoinColumn joinColumn = field.getAnnotation(JoinColumn.class);
				if (joinColumn != null) {
					if (!joinColumn.name().isEmpty()){
						columnName = joinColumn.name();
					}
				}
			}

			if (columnName != null) {
				columnNameToFieldNameMap.put(columnName, field.getName());
			}

		}

		return columnNameToFieldNameMap;
	}
}
