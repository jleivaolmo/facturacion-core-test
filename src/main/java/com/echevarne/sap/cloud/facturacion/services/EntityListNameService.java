package com.echevarne.sap.cloud.facturacion.services;

import com.echevarne.sap.cloud.facturacion.model.entities.EntityListName;
import com.echevarne.sap.cloud.facturacion.repositories.dto.NombreEntidadAndFieldNameAndText;

import java.util.List;

/**
 * Interface for the service{@link EntityListNameService}.
 */
public interface EntityListNameService extends CrudService<EntityListName, Long> {

    List<NombreEntidadAndFieldNameAndText> findAllNombreEntidadAndFieldNameAndText(String idioma);
}
