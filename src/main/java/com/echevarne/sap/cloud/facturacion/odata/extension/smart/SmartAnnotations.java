package com.echevarne.sap.cloud.facturacion.odata.extension.smart;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.echevarne.sap.cloud.facturacion.odata.annotations.sap.common.ValueList;
import com.echevarne.sap.cloud.facturacion.odata.annotations.sap.common.ValueListParameter;
import com.echevarne.sap.cloud.facturacion.odata.annotations.sap.common.ValueListParameterDisplayOnly;
import com.echevarne.sap.cloud.facturacion.odata.annotations.sap.common.ValueListParameterIn;
import com.echevarne.sap.cloud.facturacion.odata.annotations.sap.common.ValueListParameterInOut;
import com.echevarne.sap.cloud.facturacion.odata.annotations.sap.common.ValueListParameterOut;
import com.echevarne.sap.cloud.facturacion.odata.annotations.sap.ui.DataPoint;
import com.echevarne.sap.cloud.facturacion.odata.annotations.sap.ui.HeaderInfo;
import com.echevarne.sap.cloud.facturacion.odata.annotations.sap.ui.Identification;
import com.echevarne.sap.cloud.facturacion.odata.annotations.sap.ui.LineItem;
import com.echevarne.sap.cloud.facturacion.odata.annotations.sap.ui.SelectionFields;
import com.echevarne.sap.cloud.facturacion.odata.extension.utils.PropertyUtils;

import org.apache.commons.lang3.StringUtils;
import org.apache.olingo.odata2.api.edm.provider.AnnotationAttribute;
import org.apache.olingo.odata2.api.edm.provider.AnnotationElement;
import org.apache.olingo.odata2.api.edm.provider.EntityType;
import org.apache.olingo.odata2.api.edm.provider.Property;
import org.apache.olingo.odata2.api.edm.provider.Schema;
import org.apache.olingo.odata2.jpa.processor.core.model.JPAEdmMappingImpl;
import org.springframework.stereotype.Component;

@Component
public class SmartAnnotations extends AbstractSmartAnnotations {

	@Override
	public void addAnnotations(Schema edmSchema, List<AnnotationElement> schemaAnnotations) {
		schemaAnnotations.addAll(addAnnotations(edmSchema));
	}

	/**
	 * Add Smart Annotations (SAP)
	 * 
	 * @param edmSchema
	 * @return
	 */
	public List<AnnotationElement> addAnnotations(Schema edmSchema) {

		List<AnnotationElement> schemaAnnotations = new ArrayList<AnnotationElement>();
		for (EntityType entityType : edmSchema.getEntityTypes()) {
			List<AnnotationElement> entityAnnotations = new ArrayList<AnnotationElement>();

			AnnotationElement headerInfoAnnotation = findHeaderInfo(entityType);
			AnnotationElement lineItemAnnotation = new AnnotationElement().setName(XML_ELEM_COLLECTION);
			AnnotationElement identificationAnnotation = new AnnotationElement().setName(XML_ELEM_COLLECTION);
			AnnotationElement selectionFieldAnnotation = new AnnotationElement().setName(XML_ELEM_COLLECTION);
			AnnotationElement headerFacetsAnnotation = new AnnotationElement().setName(XML_ELEM_COLLECTION);
			AnnotationElement facetsAnnotation = new AnnotationElement().setName(XML_ELEM_COLLECTION);
			Map<Property, Field> fields = PropertyUtils.getAllFieldsOfEntity(entityType);
			for (Map.Entry<Property, Field> fieldMap : fields.entrySet()) {
				ValueList valueList = fieldMap.getValue().getDeclaredAnnotation(ValueList.class);
				if (valueList != null) {
					AnnotationElement valueListAnnotation = createValueList(edmSchema.getNamespace(),
							entityType.getName(), fieldMap.getKey().getName(), valueList);
					schemaAnnotations.add(valueListAnnotation);
				}
				// LineItem
				LineItem lineItem = fieldMap.getValue().getDeclaredAnnotation(LineItem.class);
				if (lineItem != null)
					createLineItem(lineItemAnnotation, edmSchema.getNamespace(), entityType.getName(),
							fieldMap.getKey().getName(), lineItem);
				// Identification
				Identification identification = fieldMap.getValue().getDeclaredAnnotation(Identification.class);
				if (lineItem != null)
					createIdentification(identificationAnnotation, edmSchema.getNamespace(), entityType.getName(),
							fieldMap.getKey().getName(), identification);
				// SelectionFields
				SelectionFields selectionFields = fieldMap.getValue().getDeclaredAnnotation(SelectionFields.class);
				if (lineItem != null)
					createSelectionField(selectionFieldAnnotation, edmSchema.getNamespace(), entityType.getName(),
							fieldMap.getKey().getName(), selectionFields);
				// DataPoint
				DataPoint dataPoint = fieldMap.getValue().getDeclaredAnnotation(DataPoint.class);
				if (dataPoint != null) {
					entityAnnotations.add(createDataPoint(headerFacetsAnnotation, edmSchema.getNamespace(),
							entityType.getName(), fieldMap.getKey().getName(), dataPoint));
					addDataPointToHeaderFacet(headerFacetsAnnotation, dataPoint);
				}
			}

			// addIdentificationToFacet(facetsAnnotation);

			if (lineItemAnnotation.getChildElements() != null)
				entityAnnotations.add(createLineItem(lineItemAnnotation));
			if (identificationAnnotation.getChildElements() != null)
				entityAnnotations.add(createIdentification(identificationAnnotation));
			if (selectionFieldAnnotation.getChildElements() != null)
				entityAnnotations.add(createSelectionField(selectionFieldAnnotation));
			if (headerFacetsAnnotation.getChildElements() != null)
				entityAnnotations.add(createHeaderFacets(headerFacetsAnnotation));
			if (facetsAnnotation.getChildElements() != null)
				entityAnnotations.add(createFacets(facetsAnnotation));
			if (headerInfoAnnotation != null)
				entityAnnotations.add(headerInfoAnnotation);

			if (entityAnnotations.size() > 0)
				schemaAnnotations.add(createAnnotations(entityAnnotations,
						createTargetAttribute(edmSchema.getNamespace(), entityType.getName())));
		}

		return schemaAnnotations;
	}

	private void addIdentificationToFacet(AnnotationElement facetsAnnotation) {
		List<AnnotationElement> childs = facetsAnnotation.getChildElements();
		if (childs == null)
			childs = new ArrayList<AnnotationElement>();
		childs.add(createReferenceFacet(null, null, XML_TERM_IDENTIFICATION, null));
		facetsAnnotation.setChildElements(childs);
	}

	private AnnotationElement findHeaderInfo(EntityType entityType) {
		Class<?> entity = ((JPAEdmMappingImpl) entityType.getMapping()).getJPAType();
		HeaderInfo headerInfo = entity.getDeclaredAnnotation(HeaderInfo.class);
		if (headerInfo != null) {
			List<AnnotationElement> elements = new ArrayList<AnnotationElement>();
			if (!StringUtils.isEmpty(headerInfo.singular()))
				elements.add(createPropertyValue(headerInfo.singular(), XML_VALU_TYPE_NAME, null, null));
			if (!StringUtils.isEmpty(headerInfo.plural()))
				elements.add(createPropertyValue(headerInfo.plural(), XML_VALU_TYPE_NAME_PLURAL, null, null));
			if (!StringUtils.isEmpty(headerInfo.image()))
				elements.add(createPropertyValue(headerInfo.image(), XML_VALU_TYPE_IMAGE_URL, null, null));

			List<AnnotationAttribute> term = createTermAttritube(XML_TERM_HEADER_INFO);
			return createAnnotation(Collections.singletonList(createRecord(null, elements)), term);
		}
		return null;
	}

	private AnnotationElement createHeaderFacets(AnnotationElement collection) {
		List<AnnotationAttribute> term = createTermAttritube(XML_TERM_HEADER_FACETS);
		return createAnnotation(Collections.singletonList(collection), term);
	}

	private AnnotationElement createFacets(AnnotationElement collection) {
		List<AnnotationAttribute> term = createTermAttritube(XML_TERM_FACETS);
		return createAnnotation(Collections.singletonList(collection), term);
	}

	private void addDataPointToHeaderFacet(AnnotationElement headerFacetsAnnotation, DataPoint dataPoint) {
		if (dataPoint.isHeaderFacet()) {
			List<AnnotationElement> childs = headerFacetsAnnotation.getChildElements();
			if (childs == null)
				childs = new ArrayList<AnnotationElement>();
			childs.add(createReferenceFacet(null, null, XML_TERM_DATAPOINT, dataPoint.qualifier()));
			headerFacetsAnnotation.setChildElements(childs);
		}
	}

	private AnnotationElement createDataPoint(AnnotationElement headerFacetsAnnotation, String namespace,
			String entityName, String entityField, DataPoint dataPoint) {

		List<AnnotationElement> elements = new ArrayList<AnnotationElement>();
		elements.add(createPropertyValue(null, XML_VALU_VALUE, entityField, null));
		elements.add(createPropertyValue(dataPoint.title(), XML_VALU_TITLE, null, null));
		List<AnnotationAttribute> term = createTermAttritube(XML_TERM_DATAPOINT);
		List<AnnotationAttribute> qualifier = createQualifierAttritube(dataPoint.qualifier());
		return createAnnotation(Collections.singletonList(createRecord(XML_VALU_RECORD_TYPE_DATAPOINTTYPE, elements)),
				term, qualifier);

	}

	private void createLineItem(AnnotationElement lineItemAnnotation, String namespace, String entityName,
			String entityField, LineItem lineItem) {
		List<AnnotationElement> elements = lineItemAnnotation.getChildElements();
		if (elements == null)
			elements = new ArrayList<AnnotationElement>();
		elements.add(createLineItem(namespace, entityName, entityField, lineItem));
		lineItemAnnotation.setChildElements(elements);
	}

	private AnnotationElement createLineItem(String namespace, String entityName, String entityField,
			LineItem lineItem) {
		AnnotationElement propertyValue = createPropertyValue(null, XML_VALU_VALUE, entityField, null);
		return createRecord(XML_VALU_RECORD_TYPE_DATAFIELD, Collections.singletonList(propertyValue));
	}

	private AnnotationElement createLineItem(AnnotationElement collection) {
		List<AnnotationAttribute> term = createTermAttritube(XML_TERM_LINEITEM);
		return createAnnotation(Collections.singletonList(collection), term);
	}

	private void createIdentification(AnnotationElement lineItemAnnotation, String namespace, String entityName,
			String entityField, Identification identification) {
		List<AnnotationElement> elements = lineItemAnnotation.getChildElements();
		if (elements == null)
			elements = new ArrayList<AnnotationElement>();
		elements.add(createIdentification(namespace, entityName, entityField, identification));
		lineItemAnnotation.setChildElements(elements);
	}

	private AnnotationElement createIdentification(String namespace, String entityName, String entityField,
			Identification identification) {
		AnnotationElement propertyValue = createPropertyValue(null, XML_VALU_VALUE, entityField, null);
		return createRecord(XML_VALU_RECORD_TYPE_DATAFIELD, Collections.singletonList(propertyValue));
	}

	private AnnotationElement createIdentification(AnnotationElement collection) {
		List<AnnotationAttribute> term = createTermAttritube(XML_TERM_IDENTIFICATION);
		return createAnnotation(Collections.singletonList(collection), term);
	}

	private void createSelectionField(AnnotationElement lineItemAnnotation, String namespace, String entityName,
			String entityField, SelectionFields selectionField) {
		List<AnnotationElement> elements = lineItemAnnotation.getChildElements();
		if (elements == null)
			elements = new ArrayList<AnnotationElement>();
		elements.add(createSelectionField(namespace, entityName, entityField, selectionField));
		lineItemAnnotation.setChildElements(elements);
	}

	private AnnotationElement createSelectionField(String namespace, String entityName, String entityField,
			SelectionFields selectionField) {
		return createPropertyPath(entityField);
	}

	private AnnotationElement createSelectionField(AnnotationElement collection) {
		List<AnnotationAttribute> term = createTermAttritube(XML_TERM_SELECTION_FIELDS);
		return createAnnotation(Collections.singletonList(collection), term);
	}

	/**
	 * 
	 * @param namespace
	 * @param name
	 * @param name2
	 * @param valueList
	 * @return
	 */
	private AnnotationElement createValueList(String namespace, String entityName, String entityField,
			ValueList valueList) {
		return createValueList(namespace, entityName, entityField, valueList.Label(),
				valueList.CollectionPath().entitySet, valueList.CollectionRoot(),
				Boolean.toString(valueList.SearchSupported()), valueList.Parameters());
	}

	/**
	 * 
	 * @param namespace
	 * @param entityName
	 * @param entityField
	 * @param label
	 * @param path
	 * @param root
	 * @param searcheable
	 * @param parameters
	 * @return
	 */
	private AnnotationElement createValueList(String namespace, String entityName, String entityField, String label,
			String path, String root, String searcheable, ValueListParameter[] parameters) {

		List<AnnotationElement> recordChilds = createRecordElements(label, path, root, searcheable, parameters);
		List<AnnotationElement> record = Collections.singletonList(createRecord(null, recordChilds));
		List<AnnotationAttribute> term = createTermAttritube(XML_TERM_VALUE_LIST);
		List<AnnotationElement> annotation = Collections.singletonList(createAnnotation(record, term));
		List<AnnotationAttribute> target = createTargetAttribute(namespace, entityName, entityField);
		return createAnnotations(annotation, target);

	}

	/**
	 * 
	 * @param label
	 * @param path
	 * @param root
	 * @param searcheable
	 * @param parameters
	 * @return
	 */
	private List<AnnotationElement> createRecordElements(String label, String path, String root, String searcheable,
			ValueListParameter[] parameters) {

		List<AnnotationElement> result = new ArrayList<AnnotationElement>();

		if (!StringUtils.isEmpty(label))
			result.add(createPropertyValue(label, XML_VALU_LABEL, null, null));
		if (!StringUtils.isEmpty(root))
			result.add(createPropertyValue(root, XML_VALU_COLLECTION_ROOT, null, null));
		result.add(createPropertyValue(path, XML_VALU_COLLECTION_PATH, null, null));
		result.add(createPropertyValue(null, XML_VALU_SEARCH_SUPPORTED, null, searcheable));

		result.add(mapParameters(parameters));

		return result;

	}

	/**
	 * 
	 * @param parameters
	 * @return
	 */
	private AnnotationElement mapParameters(ValueListParameter[] parameters) {

		/* Records */
		List<AnnotationElement> collectionRecords = new ArrayList<AnnotationElement>();
		for (ValueListParameter parameter : parameters) {
			collectionRecords.addAll(mapParameterIn(parameter.ValueListParameterIn()));
			collectionRecords.addAll(mapParameterOut(parameter.ValueListParameterOut()));
			collectionRecords.addAll(mapParameterInOut(parameter.ValueListParameterInOut()));
			collectionRecords.addAll(mapParameterDisplayOnly(parameter.ValueListParameterDisplayOnly()));
		}
		List<AnnotationElement> recordParameters = Collections.singletonList(
				new AnnotationElement().setName(XML_ELEM_COLLECTION).setChildElements(collectionRecords));
		return createPropertyValue(null, XML_VALU_PARAMETERS, null, null).setChildElements(recordParameters);

	}

	/**
	 * 
	 * @param parameters
	 * @return
	 */
	private List<AnnotationElement> mapParameterIn(ValueListParameterIn[] parameters) {

		List<AnnotationElement> annotationElements = new ArrayList<AnnotationElement>();
		for (ValueListParameterIn parameter : parameters) {
			annotationElements
					.add(createPropertyValue(parameter.ValueListProperty(), XML_VALU_PROP_VALLIST, null, null));
			annotationElements
					.add(createPropertyValue(null, XML_VALU_PROP_LOCAL, null, null, parameter.LocalDataProperty()));
		}
		return annotationElements.size() == 0 ? annotationElements
				: Collections.singletonList(createRecord(XML_TERM_VL_IN, annotationElements));

	}

	/**
	 * 
	 * @param parameters
	 * @return
	 */
	private List<AnnotationElement> mapParameterOut(ValueListParameterOut[] parameters) {

		List<AnnotationElement> annotationElements = new ArrayList<AnnotationElement>();
		for (ValueListParameterOut parameter : parameters) {
			annotationElements
					.add(createPropertyValue(parameter.ValueListProperty(), XML_VALU_PROP_VALLIST, null, null));
			annotationElements
					.add(createPropertyValue(null, XML_VALU_PROP_LOCAL, null, null, parameter.LocalDataProperty()));
		}
		return annotationElements.size() == 0 ? annotationElements
				: Collections.singletonList(createRecord(XML_TERM_VL_OUT, annotationElements));

	}

	/**
	 * 
	 * @param parameters
	 * @return
	 */
	private List<AnnotationElement> mapParameterInOut(ValueListParameterInOut[] parameters) {

		List<AnnotationElement> annotationElements = new ArrayList<AnnotationElement>();
		for (ValueListParameterInOut parameter : parameters) {
			annotationElements
					.add(createPropertyValue(parameter.ValueListProperty(), XML_VALU_PROP_VALLIST, null, null));
			annotationElements
					.add(createPropertyValue(null, XML_VALU_PROP_LOCAL, null, null, parameter.LocalDataProperty()));
		}
		return annotationElements.size() == 0 ? annotationElements
				: Collections.singletonList(createRecord(XML_TERM_VL_IN_OUT, annotationElements));

	}

	/**
	 * 
	 * @param parameters
	 * @return
	 */
	private List<AnnotationElement> mapParameterDisplayOnly(ValueListParameterDisplayOnly[] parameters) {

		List<AnnotationElement> annotationElements = new ArrayList<AnnotationElement>();
		for (ValueListParameterDisplayOnly parameter : parameters) {
			annotationElements
					.add(createPropertyValue(parameter.ValueListProperty(), XML_VALU_PROP_VALLIST, null, null));
		}
		return annotationElements.size() == 0 ? annotationElements
				: Collections.singletonList(createRecord(XML_TERM_VL_DISPLAY_ONLY, annotationElements));

	}

}