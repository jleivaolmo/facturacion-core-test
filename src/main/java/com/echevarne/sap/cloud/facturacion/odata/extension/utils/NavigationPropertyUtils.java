package com.echevarne.sap.cloud.facturacion.odata.extension.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.echevarne.sap.cloud.facturacion.odata.annotations.SapNavProperty;
import com.echevarne.sap.cloud.facturacion.reflection.AnnotationUtils;
import com.echevarne.sap.cloud.facturacion.util.StringUtils;
import com.google.common.base.CaseFormat;

import org.apache.olingo.odata2.api.edm.provider.AnnotationAttribute;
import org.apache.olingo.odata2.api.edm.provider.EntityType;
import org.apache.olingo.odata2.api.edm.provider.NavigationProperty;
import org.apache.olingo.odata2.jpa.processor.core.model.JPAEdmMappingImpl;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NavigationPropertyUtils extends GlobalUtils {

    public static void addAnnotations(EntityType entityType) {
        List<NavigationProperty> navProperties = entityType.getNavigationProperties();
        if (navProperties != null) {
            navProperties.forEach(navProperty -> addSapAnnotation(entityType, navProperty));
        }
    }

    private static void addSapAnnotation(EntityType entityType, NavigationProperty navProperty) {
        JPAEdmMappingImpl mapping = ((JPAEdmMappingImpl) navProperty.getMapping());
        if (mapping != null) {
            Class<?> entityClass = ((JPAEdmMappingImpl) entityType.getMapping()).getJPAType();
            if (entityClass == null)
                return;
            Field navField = null;
            try {
                navField = entityClass.getDeclaredField(navProperty.getName());
            } catch (NoSuchFieldException | SecurityException e) {
                try {
                    navField = entityClass.getSuperclass().getDeclaredField(navProperty.getName());
                } catch (NoSuchFieldException | SecurityException e2) {
                    log.info("No se ha podido determinar las anotaciones de la navigation property");
                }
            }
            if (navField != null)
                navProperty.setAnnotationAttributes(setSAPAttributes(navField));
        }
    }

    private static List<AnnotationAttribute> setSAPAttributes(Field field) {
        if (field.getAnnotation(SapNavProperty.class) != null) {
            return new ArrayList<>(addFieldAnnotation(field));
        } else {
            return addDefaultSapAttributes();
        }
    }

    private static List<AnnotationAttribute> addDefaultSapAttributes() {
        final Class<? extends Annotation> clazz = SapNavProperty.class;
        final Map<String, Object> memberValues = AnnotationUtils.extractDefaultMemberValues(clazz);

        List<AnnotationAttribute> result = new ArrayList<>();
        findSapAttributes(result, memberValues);

        return result;
    }

    public static void findSapAttributes(List<AnnotationAttribute> result, Map<String, Object> memberValues) {
        memberValues.forEach((key, value) -> {
            if (!StringUtils.isNullOrEmpty(value.toString())) {
                String vKey = CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_HYPHEN, key);
                result.add(new AnnotationAttribute().setNamespace(SAP_NAMESPACE).setPrefix(SAP_PREFIX).setName(vKey)
                        .setText(String.valueOf(value.toString())));
            }
        });
    }

    private static List<AnnotationAttribute> addFieldAnnotation(Field field) {
        SapNavProperty annotation = field.getAnnotation(SapNavProperty.class);

        final Map<String, Object> memberValues = AnnotationUtils.extractMemberValues(annotation);

        List<AnnotationAttribute> result = new ArrayList<>();
        findSapAttributes(result, memberValues);

        return result;
    }
}
