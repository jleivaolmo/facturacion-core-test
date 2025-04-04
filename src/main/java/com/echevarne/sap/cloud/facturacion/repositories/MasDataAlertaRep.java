package com.echevarne.sap.cloud.facturacion.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Repository;

import com.echevarne.sap.cloud.facturacion.model.masterdata.MasDataAlerta;
import com.echevarne.sap.cloud.facturacion.services.MasDataBaseService;

import javax.persistence.QueryHint;

/**
 * Repository for the Model {@link MasDataAlerta}
 * @author Hernan Girardi
 * @since 25/08/2020
 */

@Repository("masDataAlertaRep")
public interface MasDataAlertaRep extends JpaRepository<MasDataAlerta,Long>, MasDataBaseService<MasDataAlerta,Long> {

    @QueryHints({
            @QueryHint(name = org.hibernate.jpa.QueryHints.HINT_CACHEABLE, value = "true"),
            @QueryHint(name = org.hibernate.jpa.QueryHints.HINT_FLUSH_MODE, value = "MANUAL"),
    })
    Optional<MasDataAlerta> findByCodigoAlertaAndActive(String codigoAlerta, boolean active);

    @QueryHints({
            @QueryHint(name = org.hibernate.jpa.QueryHints.HINT_CACHEABLE, value = "true"),
            @QueryHint(name = org.hibernate.jpa.QueryHints.HINT_FLUSH_MODE, value = "MANUAL"),
    })
    List<MasDataAlerta> findByBloqueaAndActive(boolean bloquea, boolean active);

    @QueryHints({
            @QueryHint(name = org.hibernate.jpa.QueryHints.HINT_CACHEABLE, value = "true"),
            @QueryHint(name = org.hibernate.jpa.QueryHints.HINT_FLUSH_MODE, value = "MANUAL"),
    })
    List<MasDataAlerta> findByTratablePrivadosAndActive(boolean privados, boolean active);
}
