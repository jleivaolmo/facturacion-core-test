package com.echevarne.sap.cloud.facturacion.odata.extension;

import java.util.ArrayList;
import java.util.List;

import com.echevarne.sap.cloud.facturacion.odata.extension.smart.AbstractAnnotations;
import com.echevarne.sap.cloud.facturacion.odata.extension.smart.AbstractSmartAnnotations;
import com.echevarne.sap.cloud.facturacion.odata.extension.utils.ComplexTypeUtils;

import org.apache.olingo.odata2.api.edm.provider.ComplexType;
import org.apache.olingo.odata2.api.edm.provider.Schema;

import lombok.Setter;

public abstract class AbstractAnnotationExtension {
	@Setter
	protected AbstractAnnotations sapAnnotations;
	@Setter
	protected AbstractSmartAnnotations smartAnnotations;
	@Setter
	protected AbstractAnnotations odataAnnotations;

	public AbstractAnnotationExtension() {}

	public abstract AbstractSmartAnnotations getSmartAnnotations();
	public abstract AbstractAnnotations getSapAnnotations();

	protected List<ComplexType> mapComplexTypes(Schema edmSchema) {
		final List<ComplexType> complexTypes = new ArrayList<>();
		complexTypes.add(ComplexTypeUtils.getFacturarClienteComplexType(edmSchema.getNamespace()));
		complexTypes.add(ComplexTypeUtils.getResponseErrorComplexType(edmSchema.getNamespace()));
		complexTypes.add(ComplexTypeUtils.getResponseComplexType(edmSchema.getNamespace()));

		complexTypes.add(ComplexTypeUtils.getIdentificadorAgrupaciones(edmSchema.getNamespace()));
		complexTypes.add(ComplexTypeUtils.getListadoAgrupaciones(edmSchema.getNamespace()));
		complexTypes.add(ComplexTypeUtils.getFacturacionIndividualComplexType(edmSchema.getNamespace()));
		return complexTypes;
	}
}
