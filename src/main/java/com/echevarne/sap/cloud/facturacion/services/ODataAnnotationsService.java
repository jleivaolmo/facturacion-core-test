package com.echevarne.sap.cloud.facturacion.services;

import java.util.List;

import org.apache.olingo.odata2.api.edm.provider.ComplexType;
import org.apache.olingo.odata2.api.edm.provider.Schema;

import com.echevarne.sap.cloud.facturacion.model.entities.EntityListName;

/**
 * <p>Business Services logic for ODataAnnotationsServices </p>
 * @author Germán Laso
 * @since 26/03/2020
 */
public interface ODataAnnotationsService {

	public void addEntitiesAndFieldsAnnotations(final Schema edmSchema);
	public void addSchemaAnnotations(final Schema edmSchema);
	public List<ComplexType> getComplexTypes(final Schema edmSchema);

}
