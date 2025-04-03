package com.echevarne.sap.cloud.facturacion.mappers;

import com.echevarne.sap.cloud.facturacion.model.BasicEntity;
import com.echevarne.sap.cloud.facturacion.resources.BaseResource;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface BaseMapper  <ModelObjectType extends BasicEntity, KeyResource extends BaseResource>{

    ModelObjectType convertToEntity(KeyResource r);

    ModelObjectType convertUpdateToEntity(KeyResource r, ModelObjectType t);

    Optional<KeyResource> convertToResource(Optional<ModelObjectType> entity);

    List<KeyResource> convertToResources(List<ModelObjectType> entities);

    Set<KeyResource> convertToSetResources(Set<ModelObjectType> entities);

}
