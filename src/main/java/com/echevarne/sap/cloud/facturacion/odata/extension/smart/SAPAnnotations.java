package com.echevarne.sap.cloud.facturacion.odata.extension.smart;

import java.util.Map;

import com.echevarne.sap.cloud.facturacion.model.entities.EntityListName;
import com.echevarne.sap.cloud.facturacion.odata.extension.utils.EntityUtils;
import com.echevarne.sap.cloud.facturacion.odata.extension.utils.NavigationPropertyUtils;

import org.apache.olingo.odata2.api.edm.provider.EntitySet;
import org.apache.olingo.odata2.api.edm.provider.EntityType;
import org.springframework.stereotype.Component;

@Component
public class SAPAnnotations extends AbstractAnnotations {
	@Override
	protected void customizeEntityType(
			EntitySet entitySet,
			EntityType type,
			Map<String, Map<String, String>> textByFieldNameByLcNombreEntidad
	) {
		super.customizeEntityType(entitySet, type, textByFieldNameByLcNombreEntidad);
		NavigationPropertyUtils.addAnnotations(type);
		EntityUtils.specifyAnnotationsforEntities(type, entitySet);
	}
}
