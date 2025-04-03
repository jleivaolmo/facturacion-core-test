package com.echevarne.sap.cloud.facturacion.odata.extension.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.persistence.EmbeddedId;
import javax.persistence.Id;

import org.apache.olingo.odata2.api.annotation.edm.EdmProperty;
import org.apache.olingo.odata2.api.edm.EdmSimpleTypeKind;
import org.apache.olingo.odata2.api.edm.provider.AnnotationAttribute;
import org.apache.olingo.odata2.api.edm.provider.EntityType;
import org.apache.olingo.odata2.api.edm.provider.Property;
import org.apache.olingo.odata2.api.edm.provider.SimpleProperty;
import org.apache.olingo.odata2.jpa.processor.api.exception.ODataJPAModelException;
import org.apache.olingo.odata2.jpa.processor.core.access.model.JPATypeConverter;
import org.apache.olingo.odata2.jpa.processor.core.model.JPAEdmMappingImpl;

import com.echevarne.sap.cloud.facturacion.odata.annotations.Sap;
import com.echevarne.sap.cloud.facturacion.odata.annotations.Searcheable;
import com.echevarne.sap.cloud.facturacion.odata.annotations.Sortable;
import com.echevarne.sap.cloud.facturacion.odata.annotations.enums.FieldSortEnum;
import com.echevarne.sap.cloud.facturacion.reflection.AnnotationUtils;
import com.echevarne.sap.cloud.facturacion.reflection.ObjectReflectionUtil;
import com.echevarne.sap.cloud.facturacion.reflection.impl.ObjectReflectionUtilImpl;
import com.echevarne.sap.cloud.facturacion.util.ODataContextUtil;
import com.echevarne.sap.cloud.facturacion.util.StringUtils;
import com.google.common.base.CaseFormat;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PropertyUtils extends GlobalUtils {

    private final static Set<String> INVALID_PROP_NAMES = new HashSet<>();

    private final static ObjectReflectionUtil OBJECT_REFLECTION_UTIL = new ObjectReflectionUtilImpl();

    private final static Map<String, Object> DEFAULT_SAP_ATTRIBUTE_MEMBER_VALUES = new HashMap<>();

    private final static List<AnnotationAttribute> EMPTY_ANNOTATION_ATTRIBUTES = Collections.emptyList();

    private final static Map<String, List<AnnotationAttribute>> SAP_ANNOTATION_ATTRIBUTES_BY_FQCN = new ConcurrentHashMap<>();

    static{
        INVALID_PROP_NAMES.add("entityVersion");
        INVALID_PROP_NAMES.add("lastUpdatedTimestamp");


        Method[] methods = Sap.class.getDeclaredMethods();
        for (Method method : methods) {
            DEFAULT_SAP_ATTRIBUTE_MEMBER_VALUES.put(method.getName(), method.getDefaultValue());
        }
    }

    public static Map<Property, Field> getAllFieldsOfEntity(EntityType entityType) {
        Map<Property, Field> fieldByPropery = new HashMap<>();

        Class<?> jpaType =  ((JPAEdmMappingImpl) entityType.getMapping()).getJPAType();
        final Set<Field> fields = OBJECT_REFLECTION_UTIL.getAllFields(jpaType);
        final Map<String, Field> fieldByName = fields.stream()
                .collect(Collectors.toMap(Field::getName, Function.identity()));

        for (Property property : entityType.getProperties()) {
            JPAEdmMappingImpl mapping = ((JPAEdmMappingImpl) property.getMapping());
            if (mapping != null){
                Field field = fieldByName.get(mapping.getInternalName());
                if (field != null) {
                    fieldByPropery.put(property, field);
                }
            }
        }

        return fieldByPropery;
    }

    public static String getSearchField(Class<?> entityClass) {
        String searchFieldName = null;
        for (Field field : entityClass.getDeclaredFields()) {
            Searcheable searcheableField = field.getDeclaredAnnotation(Searcheable.class);
            if (searcheableField != null) {
                searchFieldName = field.getName();
                break;
            }
        }
        return searchFieldName;
    }

	public static String getSortableFields(Class<?> entityClass) {
		StringBuilder responseStrBuilder = new StringBuilder();
		List<String> sortableFieldsList = new ArrayList<>();
		try {
			for (Field field : entityClass.getDeclaredFields()) {
				Sortable sortableField = field.getDeclaredAnnotation(Sortable.class);
				if (sortableField != null) {
					int priority = sortableField.priority();
					FieldSortEnum order = sortableField.order();
					int index = priority > sortableFieldsList.size() ? sortableFieldsList.size() : priority - 1;
					sortableFieldsList.add(index, field.getName() + " " + order.toString());
				}
			}
			sortableFieldsList.forEach(f -> {
                responseStrBuilder.append(f);
				if (sortableFieldsList.indexOf(f) < sortableFieldsList.size() - 1) {
                    responseStrBuilder.append(",");
				}
			});
		} catch (Exception e) {
			log.error("Ops!", e);
		}
		return responseStrBuilder.toString();
	}

	public static String getIdField(Class<?> entityClass) {
	    StringBuilder responseStrBuilder = new StringBuilder();
	    Class<?> currentClass = entityClass;
	    Annotation idAnnotation = null;
	    try {
	        //outerLoop: // Label for the outer while loop                  
	        while (currentClass != null && idAnnotation == null) {
	            for (Field field : currentClass.getDeclaredFields()) {
	                idAnnotation = field.getDeclaredAnnotation(Id.class);
	                if (idAnnotation == null) {
	                    idAnnotation = field.getDeclaredAnnotation(EmbeddedId.class);
	                }
	                if (idAnnotation != null) {
	                	if (responseStrBuilder.length()>0) {
	                		 responseStrBuilder.append(", ");
	                	}
	                    responseStrBuilder.append(field.getName());
	                    responseStrBuilder.append(" asc");
	                    //break outerLoop; // Breaks out of both loops
	                }
	            }
	            currentClass = currentClass.getSuperclass(); // Move to the superclass
	        }
	    } catch (Exception e) {
	        log.error("Ops!", e);
	    }
	    return responseStrBuilder.toString();
	}

    public static List<Property> mapEntityProperties(EntityType entityType, Map<String, String> textByFieldName) {
        final Map<String, String> labelByLcPropertyName = createMapLabelByLcPropertyName(textByFieldName);

        final List<Property> validProperties = entityType.getProperties().stream()
                .filter((property) -> isValidProperty(property.getName()))
                .collect(Collectors.toList());

        for (Property validProperty : validProperties) {
            setAnnotationAttributes(validProperty, entityType, labelByLcPropertyName);
        }
        addExtraFields(validProperties, entityType, labelByLcPropertyName);
        return validProperties;
    }

    private static void addExtraFields(List<Property> properties, EntityType entityType,
                                      Map<String, String> labelByLcPropertyName) {
        Set<String> existingFieldNames = properties.stream().map(Property::getName).collect(Collectors.toSet());
        try {

            List<Field> fields = getAllExtraFieldsOfEntity(entityType);
            for (Field field : fields) {
                if (!existingFieldNames.contains(field.getName())) {
                    Property extraField = getExtraFields(field, entityType, labelByLcPropertyName);
                    properties.add(extraField);

                    existingFieldNames.add(extraField.getName());
                }
            }
        } catch (ODataJPAModelException e) {
            log.error("Ops!", e);
        }
    }

    private static List<Field> getAllExtraFieldsOfEntity(EntityType entityType) {
        List<Field> fields = new ArrayList<>();
        for (Field field : ((JPAEdmMappingImpl) entityType.getMapping()).getJPAType().getDeclaredFields()) {
            EdmProperty edmProperty = field.getDeclaredAnnotation(EdmProperty.class);
            if (edmProperty != null) {
                fields.add(field);
            }
        }
        return fields;
    }

    private static Property getExtraFields(Field field, EntityType entityType, Map<String, String> labelByLcPropertyName)
            throws ODataJPAModelException {
        Property property = buildProperty(field);

        Optional<AnnotationAttribute> annotationAttributeOptional = getLabelAnnotationAttribute(entityType.getName(), field.getName(), labelByLcPropertyName);
        List<AnnotationAttribute> sapAnnotationAttributes = getSAPAttributes(field);

        int targetSize = sapAnnotationAttributes.size() + (annotationAttributeOptional.isPresent() ? 1 : 0) + 1;

        List<AnnotationAttribute> annotationAttributeList = new ArrayList<>(targetSize);
        annotationAttributeOptional.ifPresent(annotationAttributeList::add);
        annotationAttributeList.addAll(sapAnnotationAttributes);

        if (applyAnnotationDateFormat(property, annotationAttributeList)) {
            annotationAttributeList.add(createDisplayFormatAnnotationAttribute(DATE_FORMAT));
        }
        property.setAnnotationAttributes(annotationAttributeList);
        return property;
    }

    private static Property buildProperty(Field field) throws ODataJPAModelException {
        SimpleProperty property = new SimpleProperty();
        property.setName(field.getName());
        EdmSimpleTypeKind simpleTypeKind = JPATypeConverter.convertToEdmSimpleType(field.getType(), null);
        property.setType(simpleTypeKind);
        // JPAEdmMapping mapping = new JPAEdmMappingImpl();
        // ((Mapping) mapping).setInternalName(field.getName());
        // mapping.setJPAType(field.getType());
        // mapping.setVirtualAccess(true);
        // property.setMapping((Mapping) mapping);
        return property;
    }

    private static Map<String, String> createMapLabelByLcPropertyName(Map<String, String> textByFieldName) {
        Set<Map.Entry<String, String>> fieldNameAndTextEntrySet = textByFieldName.entrySet();

        Map<String, String> labelByLcPropertyName = new HashMap<>(fieldNameAndTextEntrySet.size());
        for(Map.Entry<String, String> fieldNameAndTextEntry: fieldNameAndTextEntrySet){
            String fieldName =  fieldNameAndTextEntry.getKey();
            String label = fieldNameAndTextEntry.getValue();

            String fieldNameLc = fieldName.toLowerCase();
            labelByLcPropertyName.put(fieldNameLc, label);
        }
        return labelByLcPropertyName;
    }

    public static List<AnnotationAttribute> findAttributtes(final Map<String, Object> memberValues) {
        List<AnnotationAttribute> result = new ArrayList<>(memberValues.size());
        findSapAttributes(result, memberValues);
        return result;
    }

    private static void findSapAttributes(List<AnnotationAttribute> result, Map<String, Object> memberValues) {
        // Recorremos los miembros de la anotacion
        memberValues.forEach((key, value) -> {
            if (!StringUtils.isNullOrEmpty(value.toString())) {
                String vKey = CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_HYPHEN, key);
                result.add(new AnnotationAttribute().setNamespace(SAP_NAMESPACE).setPrefix(SAP_PREFIX).setName(vKey)
                        .setText(String.valueOf(value.toString())));
            }
        });
    }

    private static boolean isValidProperty(String propertyName) {
        // TODO this have to set UI.Hidden
        return !INVALID_PROP_NAMES.contains(propertyName);
    }

    private static List<AnnotationAttribute> addFieldAnnotation(Field field) {
        final Sap sap = field.getAnnotation(Sap.class);
        final Map<String, Object> memberValues = AnnotationUtils.extractMemberValues(sap);

        return findAttributtes(memberValues);
    }

    private static Property setAnnotationAttributes(Property property, EntityType entityType,
                                                    Map<String, String> labelByLcPropertyName) {

        Optional<AnnotationAttribute> labelAnnotationAttributeOptional = getLabelAnnotationAttribute(entityType.getName(), property.getName(), labelByLcPropertyName);
        List<AnnotationAttribute> sapAttributes = getSAPAttributes(entityType, property);

        int targetSize = (labelAnnotationAttributeOptional.isPresent() ? 1 : 0) + sapAttributes.size() + 1;

        List<AnnotationAttribute> annotationAttributeList = new ArrayList<>(targetSize);
        labelAnnotationAttributeOptional.ifPresent(annotationAttributeList::add);

        annotationAttributeList.addAll(sapAttributes);

        if (applyAnnotationDateFormat(property, annotationAttributeList)) {
            annotationAttributeList.add(createDisplayFormatAnnotationAttribute(DATE_FORMAT));
        }

        property.setAnnotationAttributes(annotationAttributeList);
        return property;
    }

    private static boolean applyAnnotationDateFormat(Property property, List<AnnotationAttribute> annotations) {
        boolean applyAnnotationDateFormat = false;

        if (property instanceof SimpleProperty) {
            SimpleProperty simpleProperty = (SimpleProperty) property;
            EdmSimpleTypeKind edmSimpleTypeKind = simpleProperty.getType();
            String name = edmSimpleTypeKind.name();
            if ("Date".equals(name) || "DateTime".equals(name)) {
                applyAnnotationDateFormat = annotations.stream()
                        .noneMatch(ann -> DISPLAY_FORMAT.equalsIgnoreCase(ann.getName()));
            }
        }
        return applyAnnotationDateFormat;
    }

    private static AnnotationAttribute createDisplayFormatAnnotationAttribute(String formatToDisplay) {
        return new AnnotationAttribute()
                .setNamespace(SAP_NAMESPACE)
                .setPrefix(SAP_PREFIX)
                .setName(DISPLAY_FORMAT)
                .setText(formatToDisplay);
    }

    private static Optional<AnnotationAttribute> getLabelAnnotationAttribute(String entityName, String propertyName,
                                                                             Map<String, String> labelByLcPropertyName) {
        AnnotationAttribute annotationAttribute = null;
        String label = setByDatabase(propertyName, labelByLcPropertyName);
        if (label == null) {
            label = setByResource(entityName, propertyName);
        }

        // Seteamos la etiqueta como un atributo de la propiedad
        if (label != null) {
            annotationAttribute = new AnnotationAttribute()
                    .setNamespace(SAP_NAMESPACE)
                    .setPrefix(SAP_PREFIX)
                    .setName(LABEL)
                    .setText(label);
        }

        return Optional.ofNullable(annotationAttribute);
    }

    private static String setByDatabase(String propertyName, Map<String, String> labelByLcPropertyName) {
        return labelByLcPropertyName.get(propertyName.toLowerCase());
    }

    private static String setByResource(String entityName, String propertyName) {
        String value = null;

        // Buscamos el Resource con los textos
        ResourceBundle i18n = ODataContextUtil.getResourceBundle("i18n");
        if (i18n != null) {
            // Buscamos el especifico para la entidad
            Optional<String> valueOptional = getStrFromResource(i18n, entityName.toLowerCase() + SEPARATOR + propertyName.toLowerCase());
            if (valueOptional.isPresent()) {
                value = valueOptional.get();
            } else {
                // Buscamos el general para todas las entidades
                valueOptional =  getStrFromResource(i18n, ALL_ENTITIES + SEPARATOR + propertyName);
                if (valueOptional.isPresent()) {
                    value = valueOptional.get();
                }
            }
        }
        return value;
    }

    private static Optional<String> getStrFromResource(ResourceBundle resourceBundle, String key) {
        String value = null;

        if(resourceBundle.containsKey(key)){ // Avoid throwing an exception. Exceptions are not very cheap
            value = resourceBundle.getString(key);
        }

        return Optional.ofNullable(value);
    }

    private static List<AnnotationAttribute> getSAPAttributes(EntityType entityType, Property property) {
        Class<?> jpaType =  ((JPAEdmMappingImpl) entityType.getMapping()).getJPAType();
        String fqcn = jpaType.getCanonicalName();
        String key = fqcn + "--" + property.getName();

        List<AnnotationAttribute> annotationAttributes = SAP_ANNOTATION_ATTRIBUTES_BY_FQCN.get(key);
        if (annotationAttributes == null){
            annotationAttributes = getSAPAttributesInternal(entityType, property);
            SAP_ANNOTATION_ATTRIBUTES_BY_FQCN.put(key, annotationAttributes);
        }

        return annotationAttributes;
    }

    private static List<AnnotationAttribute> getSAPAttributesInternal(EntityType entityType, Property property) {
        Map<Property, Field> fields = getAllFieldsOfEntity(entityType);
        Field field = fields.get(property);

        List<AnnotationAttribute> sapAttributes = EMPTY_ANNOTATION_ATTRIBUTES;
        if (field != null) {
            sapAttributes = getSAPAttributes(field);
        }

        return sapAttributes;
    }

    private static List<AnnotationAttribute> getSAPAttributes(Field field) {
        if (field.getAnnotation(Sap.class) != null) {
            return addFieldAnnotation(field);
        } else {
            return addDefaultSapAttributes();
        }
    }

    private static List<AnnotationAttribute> addDefaultSapAttributes() {
        List<AnnotationAttribute> result = new ArrayList<>(DEFAULT_SAP_ATTRIBUTE_MEMBER_VALUES.size());
        findSapAttributes(result, DEFAULT_SAP_ATTRIBUTE_MEMBER_VALUES);
        return result;
    }
}
