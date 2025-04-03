package com.echevarne.sap.cloud.facturacion.odata.extension.utils;
import java.util.List;
import java.util.Map;

import com.echevarne.sap.cloud.facturacion.odata.annotations.ODataEntityStream;
import com.echevarne.sap.cloud.facturacion.odata.annotations.SapEntitySet;
import com.echevarne.sap.cloud.facturacion.odata.annotations.SapEntityType;

import com.echevarne.sap.cloud.facturacion.reflection.AnnotationUtils;
import org.apache.olingo.odata2.api.edm.EdmEntitySet;
import org.apache.olingo.odata2.api.edm.EdmException;
import org.apache.olingo.odata2.api.edm.provider.AnnotationAttribute;
import org.apache.olingo.odata2.api.edm.provider.EntitySet;
import org.apache.olingo.odata2.api.edm.provider.EntityType;
import org.apache.olingo.odata2.api.uri.info.GetEntitySetUriInfo;
import org.apache.olingo.odata2.jpa.processor.core.model.JPAEdmMappingImpl;

public class EntityUtils extends GlobalUtils {

    public static void specifyAnnotationsforEntities(final EntityType entityType, final EntitySet entitySet) {
        Class<?> entity = ((JPAEdmMappingImpl) entityType.getMapping()).getJPAType();

        final SapEntityType sapEntityType = entity.getAnnotation(SapEntityType.class);
        if (sapEntityType != null) {
            final Map<String, Object> memberValues = AnnotationUtils.extractMemberValues(sapEntityType);
            setSapEntityAnnotations(entityType, memberValues);
        }

        final SapEntitySet sapEntitySet = entity.getAnnotation(SapEntitySet.class);
        if (sapEntitySet != null) {
            final Map<String, Object> memberValues = AnnotationUtils.extractMemberValues(sapEntitySet);
            setSapEntityAnnotations(entitySet, memberValues);
        }
    }

    public static void specifyStreamAnnotationforEntities(final EntityType entityType) {
        Class<?> entity = ((JPAEdmMappingImpl) entityType.getMapping()).getJPAType();
        if (entity.getAnnotation(ODataEntityStream.class) != null) {
            entityType.setHasStream(true);
        }
    }

    private static void setSapEntityAnnotations(EntityType entityType, final Map<String, Object> memberValues) {
        final List<AnnotationAttribute> attributes = PropertyUtils.findAttributtes(memberValues);
        entityType.setAnnotationAttributes(attributes);
    }

    private static void setSapEntityAnnotations(EntitySet entitySet, final Map<String, Object> memberValues) {
        final List<AnnotationAttribute> attributes = PropertyUtils.findAttributtes(memberValues);
        entitySet.setAnnotationAttributes(attributes);
    }

    public static Class<?> getJpaEntity(GetEntitySetUriInfo uriParserResultView) throws EdmException {
        EdmEntitySet entitySet = uriParserResultView.getTargetEntitySet();
        return ((JPAEdmMappingImpl) entitySet.getEntityType().getMapping()).getJPAType();
    }

}
