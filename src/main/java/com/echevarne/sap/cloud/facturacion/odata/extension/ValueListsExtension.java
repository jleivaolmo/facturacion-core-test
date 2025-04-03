package com.echevarne.sap.cloud.facturacion.odata.extension;

import java.util.ArrayList;
import java.util.List;

import org.apache.olingo.odata2.api.edm.provider.AnnotationAttribute;
import org.apache.olingo.odata2.api.edm.provider.AnnotationElement;

import com.echevarne.sap.cloud.facturacion.odata.annotations.sap.common.ValueListParameter;
import com.echevarne.sap.cloud.facturacion.odata.annotations.sap.common.ValueListParameterDisplayOnly;
import com.echevarne.sap.cloud.facturacion.odata.annotations.sap.common.ValueListParameterIn;
import com.echevarne.sap.cloud.facturacion.odata.annotations.sap.common.ValueListParameterInOut;
import com.echevarne.sap.cloud.facturacion.odata.annotations.sap.common.ValueListParameterOut;

public class ValueListsExtension {

	@SuppressWarnings("serial")
	public static AnnotationElement addValueList(String namespace, String entityName, String entityField, String label, String path,
			String root, String searcheable, ValueListParameter[] parameters) {

		// Mapeamos los parametros
		List<AnnotationElement> record = mapRecord(label, path, root, searcheable, mapParameters(parameters));
		
		/* Annotation */
		AnnotationElement oAnnotation = new AnnotationElement().setName("Annotation")
				.setAttributes(new ArrayList<AnnotationAttribute>() {
					{
						add(new AnnotationAttribute().setName("Term")
								.setText("com.sap.vocabularies.Common.v1.ValueList"));
					}
				}).setChildElements(record);

		List<AnnotationElement> oAnnotationElements = new ArrayList<AnnotationElement>();
		oAnnotationElements.add(oAnnotation);

		/* Annotations */
		AnnotationElement oAnnotations = new AnnotationElement().setName("Annotations")
				.setAttributes(new ArrayList<AnnotationAttribute>() {
					{
						add(new AnnotationAttribute().setName("Target")
								.setNamespace("http://docs.oasis-open.org/odata/ns/edm")
								.setText(namespace + "." + entityName + "/" + entityField));
					}
				}).setChildElements(oAnnotationElements);

		return oAnnotations;

	}
	
	/**
	 * 
	 * @param parameters
	 * @return
	 */
	public static List<AnnotationElement> mapParameters(ValueListParameter[] parameters) {
		
		/* Collections */
		List<AnnotationElement> collections = new ArrayList<AnnotationElement>();
		AnnotationElement collection = new AnnotationElement().setName("Collection");
		
		/* Records */
		List<AnnotationElement> records = new ArrayList<AnnotationElement>();
		for (ValueListParameter parameter : parameters) {

			records.addAll(mapParameterIn(parameter.ValueListParameterIn()));
			records.addAll(mapParameterOut(parameter.ValueListParameterOut()));
			records.addAll(mapParameterInOut(parameter.ValueListParameterInOut()));
			records.addAll(mapParameterDisplayOnly(parameter.ValueListParameterDisplayOnly()));

		}
		
		collection.setChildElements(records);
		collections.add(collection);

		return collections;

	}
	
	/**
	 * 
	 * @param parameters
	 * @return
	 */
	public static List<AnnotationElement> mapParameterIn(ValueListParameterIn[] parameters) {
		
		List<AnnotationElement> result = new ArrayList<AnnotationElement>();
		for(ValueListParameterIn parameter: parameters) {
			
			List<AnnotationElement> annotationElements = new ArrayList<AnnotationElement>();
			annotationElements.add(createPropertyValue(parameter.ValueListProperty(), "ValueListProperty", null, null));
			annotationElements.add(createPropertyValue(null, "LocalDataProperty", parameter.LocalDataProperty(), null));
			
			result.add(
					createRecord("com.sap.vocabularies.Common.v1.ValueListParameterIn")
					.setChildElements(annotationElements)
					);
			
		}
		
		return result;

	}
	
	/**
	 * 
	 * @param parameters
	 * @return
	 */
	public static List<AnnotationElement> mapParameterOut(ValueListParameterOut[] parameters) {

		List<AnnotationElement> result = new ArrayList<AnnotationElement>();
		for(ValueListParameterOut parameter: parameters) {
			
			List<AnnotationElement> annotationElements = new ArrayList<AnnotationElement>();
			annotationElements.add(createPropertyValue(parameter.ValueListProperty(), "ValueListProperty", null, null));
			annotationElements.add(createPropertyValue(null, "LocalDataProperty", parameter.LocalDataProperty(), null));
			
			result.add(
					createRecord("com.sap.vocabularies.Common.v1.ValueListParameterOut")
					.setChildElements(annotationElements)
					);
			
		}
		
		return result;
	}
	
	/**
	 * 
	 * @param parameters
	 * @return
	 */
	public static List<AnnotationElement> mapParameterInOut(ValueListParameterInOut[] parameters) {

		List<AnnotationElement> result = new ArrayList<AnnotationElement>();
		for(ValueListParameterInOut parameter: parameters) {
			
			List<AnnotationElement> annotationElements = new ArrayList<AnnotationElement>();
			annotationElements.add(createPropertyValue(parameter.ValueListProperty(), "ValueListProperty", null, null));
			annotationElements.add(createPropertyValue(null, "LocalDataProperty", parameter.LocalDataProperty(), null));
			
			result.add(
					createRecord("com.sap.vocabularies.Common.v1.ValueListParameterInOut")
					.setChildElements(annotationElements)
					);
			
		}
		
		return result;
	}
	
	/**
	 * 
	 * @param parameters
	 * @return
	 */
	public static List<AnnotationElement> mapParameterDisplayOnly(ValueListParameterDisplayOnly[] parameters) {

		List<AnnotationElement> result = new ArrayList<AnnotationElement>();
		for(ValueListParameterDisplayOnly parameter: parameters) {
			
			List<AnnotationElement> annotationElements = new ArrayList<AnnotationElement>();
			annotationElements.add(createPropertyValue(parameter.ValueListProperty(), "ValueListProperty", null, null));			
			result.add(
					createRecord("com.sap.vocabularies.Common.v1.ValueListParameterDisplayOnly")
					.setChildElements(annotationElements)
					);
			
		}
		
		return result;
	}

	/**
	 * 
	 * @param label
	 * @param path
	 * @param root
	 * @param searcheable
	 * @param parameterList
	 * @return
	 */
	public static List<AnnotationElement> mapRecord(String label, String path, String root, String searcheable,
			List<AnnotationElement> parameterList) {

		/* Record */
		List<AnnotationElement> recordElements = new ArrayList<AnnotationElement>();
		AnnotationElement record = createRecord(null)
				.setChildElements(createRecordChilds(label, path, root, searcheable, parameterList));
		recordElements.add(record);
		
		return recordElements;
	}

	
	public static List<AnnotationElement> createRecordChilds(String label, String path, String root, String searcheable,
			List<AnnotationElement> parameterList) {
		
		List<AnnotationElement> result = new ArrayList<AnnotationElement>();
		if(!label.isEmpty())
		result.add(createPropertyValue(label, "Label", null, null));
		if(!root.isEmpty())
		result.add(createPropertyValue(root, "CollectionRoot", null, null));
		
		result.add(createPropertyValue(path, "CollectionPath", null, null));
		result.add(createPropertyValue(null, "SearchSupported", null, searcheable));
		result.add(createPropertyValue(null, "Parameters", null, null).setChildElements(parameterList));
		 
		 return result;
		
	}
	
	/**
	 * 
	 * @param type
	 * @return
	 */
	public static AnnotationElement createRecord(String type) {

		AnnotationElement oElement = new AnnotationElement().setName("Record");
		if(type != null) {
			ArrayList<AnnotationAttribute> oAttributes = new ArrayList<AnnotationAttribute>();
			AnnotationAttribute oAttribute1 = new AnnotationAttribute().setName("Type").setText(type);
			oAttributes.add(oAttribute1);
			oElement.setAttributes(oAttributes);
		}
		return oElement;

	}

	/**
	 * 
	 * @param string
	 * @param property
	 * @param propertyPath
	 * @param bool
	 * @return
	 */
	public static AnnotationElement createPropertyValue(String string, String property, String propertyPath, String bool) {

		/* Elemento */
		AnnotationElement oElement = new AnnotationElement().setName("PropertyValue");

		/* Lista de atributos */
		ArrayList<AnnotationAttribute> oAttributes = new ArrayList<AnnotationAttribute>();
		
		/* Atributo Property */
		if (property != null) {
			AnnotationAttribute oAttribute2 = new AnnotationAttribute().setName("Property").setText(property);
			oAttributes.add(oAttribute2);
		}
		
		/* Atributo String */
		if (string != null) {
			AnnotationAttribute oAttribute1 = new AnnotationAttribute().setName("String").setText(string);
			oAttributes.add(oAttribute1);
		}		

		/* Atributo PropertyPath */
		if (propertyPath != null) {
			AnnotationAttribute oAttribute3 = new AnnotationAttribute().setName("PropertyPath").setText(propertyPath);
			oAttributes.add(oAttribute3);
		}

		/* Atributo Bool */
		if (bool != null) {
			AnnotationAttribute oAttribute4 = new AnnotationAttribute().setName("Bool").setText(bool);
			oAttributes.add(oAttribute4);
		}

		/* Seteamos Atributos */
		oElement.setAttributes(oAttributes);

		return oElement;

	}

}