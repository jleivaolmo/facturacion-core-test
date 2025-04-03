package com.echevarne.sap.cloud.facturacion.odata.extension.smart;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.echevarne.sap.cloud.facturacion.model.entities.EntityListName;

import com.echevarne.sap.cloud.facturacion.odata.extension.utils.PropertyUtils;
import org.apache.olingo.odata2.api.edm.provider.EntitySet;
import org.apache.olingo.odata2.api.edm.provider.EntityType;
import org.apache.olingo.odata2.api.edm.provider.Property;
import org.apache.olingo.odata2.api.edm.provider.Schema;

import static java.util.Collections.EMPTY_MAP;

public abstract class AbstractAnnotations implements AnnotationCommons {

	public void addAnnotations(Schema edmSchema, Map<String, Map<String, String>> textByFieldNameByLcNombreEntidad) {
		Map<String, EntityType> entityTypeByName =  edmSchema.getEntityTypes().stream()
				.collect(Collectors.toMap(EntityType::getName, Function.identity()));

		List<EntitySet> entitiesSet = edmSchema.getEntityContainers().get(0).getEntitySets();
		entitiesSet.forEach(entitySet -> {
			EntityType type = entityTypeByName.get(entitySet.getEntityType().getName());
			if (type != null) {
				customizeEntityType(entitySet, type, textByFieldNameByLcNombreEntidad);
			}
		});
	}

	protected void customizeEntityType(EntitySet entitySet, EntityType type, Map<String, Map<String, String>> textByFieldNameByLcNombreEntidad) {
		// Agregamos atributos al entityType
		Map<String, String> textByFieldName = textByFieldNameByLcNombreEntidad.getOrDefault(type.getName().toLowerCase(), EMPTY_MAP);
		final List<Property> properties = PropertyUtils.mapEntityProperties(type, textByFieldName);
		type.setProperties(properties);
	}
}
