package com.echevarne.sap.cloud.facturacion.repositories;

import java.util.List;

import com.echevarne.sap.cloud.facturacion.repositories.dto.NombreEntidadAndFieldNameAndText;
import lombok.NonNull;
import lombok.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.echevarne.sap.cloud.facturacion.model.entities.EntityListName;

import javax.persistence.QueryHint;

/**
 * Class for repository {@link EntityListNameRep}.
 *
 * <p>. . .</p>
 * <p>Repository for the Model: EntityListName</p>
 */
@Repository("entityListNameRep")
public interface EntityListNameRep extends JpaRepository<EntityListName, Long> {

	EntityListName findByNombreEntidad(String nombreEntidad);

    @QueryHints({
            @QueryHint(name = org.hibernate.jpa.QueryHints.HINT_CACHEABLE, value = "true"),
            @QueryHint(name = org.hibernate.jpa.QueryHints.HINT_FLUSH_MODE, value = "MANUAL"),
    })
    @Query("select new com.echevarne.sap.cloud.facturacion.repositories.dto.NombreEntidadAndFieldNameAndText(eln.nombreEntidad, efn.nombreCampo, efnt.textoCampo) from EntityListName eln inner join eln.fields as efn inner join efn.texts as efnt WHERE efnt.claveIdioma = :idioma")
    List<NombreEntidadAndFieldNameAndText> findAllNombreEntidadAndFieldNameAndText(@Param("idioma") String idioma);


}
