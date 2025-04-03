package com.echevarne.sap.cloud.facturacion.odata.extension.smart;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;
import org.apache.olingo.odata2.api.edm.provider.AnnotationAttribute;
import org.apache.olingo.odata2.api.edm.provider.AnnotationElement;
import org.apache.olingo.odata2.api.edm.provider.Schema;

public abstract class AbstractSmartAnnotations implements AnnotationCommons {

	public abstract void addAnnotations(Schema edmSchema, List<AnnotationElement> schemaAnnotations);

	public AnnotationElement createAnnotationElement(List<AnnotationElement> elements, List<AnnotationAttribute> attributes, String elementName) {
		return new AnnotationElement().setName(elementName).setAttributes(attributes).setChildElements(elements);
	}
	
	public List<AnnotationAttribute> createAnnotationAttribute(String name, String text, String namespace){
		return Collections.singletonList(new AnnotationAttribute().setName(name).setText(text).setNamespace(namespace));
	}
	
	public List<AnnotationAttribute> createAnnotationAttribute(String name, String text){
		return Collections.singletonList(new AnnotationAttribute().setName(name).setText(text));
	}

	public AnnotationElement createAnnotations(List<AnnotationElement> elements, List<AnnotationAttribute> target) {
		return createAnnotationElement(elements, target, XML_ELEM_ANNOTATIONS);
	}
	
	public AnnotationElement createAnnotation(List<AnnotationElement> elements, List<AnnotationAttribute> term) {
		return createAnnotationElement(elements, term, XML_ELEM_ANNOTATION);
	}
	
	public AnnotationElement createAnnotation(List<AnnotationElement> elements, List<AnnotationAttribute> term, List<AnnotationAttribute> qualifier) {
		List<AnnotationAttribute> attributes = Stream.concat(term.stream(), qualifier.stream()).collect(Collectors.toList());
		return createAnnotationElement(elements, attributes, XML_ELEM_ANNOTATION);
	}

	public List<AnnotationAttribute> createTargetAttribute(String namespace, String entityName, String entityField) {
		String text = namespace + XML_DOT + entityName + XML_SLASH + entityField;
		return createAnnotationAttribute(XML_ATTR_TARGET, text, XML_MS_NAMESPACE);
	}
	
	public List<AnnotationAttribute> createTargetAttribute(String namespace, String entityName) {
		String text = namespace + XML_DOT + entityName;
		return createAnnotationAttribute(XML_ATTR_TARGET, text, XML_MS_NAMESPACE);
	}
	
	public List<AnnotationAttribute> createTermAttritube(String termValue){
		return createAnnotationAttribute(XML_ATTR_TERM, termValue);
	}
	
	public List<AnnotationAttribute> createQualifierAttritube(String qualifier){
		return createAnnotationAttribute(XML_ATTR_QUALIFIER, qualifier);
	}

	public AnnotationElement createRecord(String type, List<AnnotationElement> childs) {
		
		AnnotationElement record = new AnnotationElement().setName(XML_ELEM_RECORD);
		if(!StringUtils.isEmpty(type)) {
			List<AnnotationAttribute> oAttributes = Collections.singletonList(new AnnotationAttribute().setName(XML_ATTR_RECORD_TYPE).setText(type));			
			record.setAttributes(oAttributes);
		}
		if(childs != null) {
			record.setChildElements(childs);
		}
		return record;

	}
	
	public AnnotationElement createPropertyValue(String string, String property, String path, String bool, String annotationPath, String propertyPath) {

		ArrayList<AnnotationAttribute> oAttributes = new ArrayList<AnnotationAttribute>();
		if (!StringUtils.isEmpty(property)) oAttributes.add(new AnnotationAttribute().setName(XML_ATTR_PROPERTY).setText(property));
		if (!StringUtils.isEmpty(string)) oAttributes.add(new AnnotationAttribute().setName(XML_ATTR_STRING).setText(string));
		if (!StringUtils.isEmpty(path)) oAttributes.add(new AnnotationAttribute().setName(XML_ATTR_PATH).setText(path));
		if (!StringUtils.isEmpty(propertyPath)) oAttributes.add(new AnnotationAttribute().setName(XML_ATTR_PROP_PATH).setText(propertyPath));
		if (!StringUtils.isEmpty(bool)) oAttributes.add(new AnnotationAttribute().setName(XML_ATTR_BOOL).setText(bool));
		if (!StringUtils.isEmpty(annotationPath)) oAttributes.add(new AnnotationAttribute().setName(XML_ATTR_ANNOTATION_PATH).setText(annotationPath));
		return new AnnotationElement().setName(XML_ELEM_PROP_VALUE).setAttributes(oAttributes);

	}

	public AnnotationElement createPropertyValue(String string, String property, String path, String bool) {		
		return createPropertyValue(string, property, path, bool, null, null);
	}
	
	public AnnotationElement createPropertyValue(String string, String property, String path, String bool, String propertyPath) {		
		return createPropertyValue(string, property, path, bool, null, propertyPath);
	}
	
	public AnnotationElement createPropertyPath(String value) {
		return new AnnotationElement().setName(XML_ELEM_PROP_PATH).setText(value);
	}
	
	public AnnotationElement createReferenceFacet(String id, String label, String annotation, String target) {
		List<AnnotationElement> elements = new ArrayList<AnnotationElement>();
		if (!StringUtils.isEmpty(id)) elements.add(createPropertyValue(id, XML_VALU_ID, null, null));
		if (!StringUtils.isEmpty(label)) elements.add(createPropertyValue(label, XML_VALU_LABEL, null, null));
		String annotationPath = XML_REFERENCE + annotation;
		if (!StringUtils.isEmpty(target)) annotationPath += XML_HASH + target;
		elements.add(createPropertyValue(null, XML_VALU_TARGET, null, null, annotationPath, null));
		return createRecord(XML_TERM_REFERENCE_FACET, elements);
	}

}
