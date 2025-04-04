package com.echevarne.sap.cloud.facturacion.repositories;

import java.util.Optional;

import com.echevarne.sap.cloud.facturacion.model.masterdata.MasDataEstado;
import com.echevarne.sap.cloud.facturacion.model.parametrizacion.AccionesPermitidas;
import com.echevarne.sap.cloud.facturacion.model.parametrizacion.AccionesUsuario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Repository;

import javax.persistence.QueryHint;

/**
 * Class for repository {@link AccionesPermitidasRep}.
 *
 * <p>. . .</p>
 * <p>Repository for the Model: AccionesPermitidas</p>
 *
 * @author Germán Laso
 * @version 1.0
 * @since 08/03/2021
 *
 */
@Repository("accionesPermitidasRep")
public interface AccionesPermitidasRep extends JpaRepository<AccionesPermitidas, Long> {

    @QueryHints({
            @QueryHint(name = org.hibernate.jpa.QueryHints.HINT_CACHEABLE, value = "true"),
            @QueryHint(name = org.hibernate.jpa.QueryHints.HINT_FLUSH_MODE, value = "MANUAL"),
    })
    Optional<AccionesPermitidas> findById(Long id);

    @QueryHints({
            @QueryHint(name = org.hibernate.jpa.QueryHints.HINT_CACHEABLE, value = "true"),
            @QueryHint(name = org.hibernate.jpa.QueryHints.HINT_FLUSH_MODE, value = "MANUAL"),
    })
    Optional<AccionesPermitidas> findByAccionAndEstado(AccionesUsuario accion, MasDataEstado estado);

    @QueryHints({
            @QueryHint(name = org.hibernate.jpa.QueryHints.HINT_CACHEABLE, value = "true"),
            @QueryHint(name = org.hibernate.jpa.QueryHints.HINT_FLUSH_MODE, value = "MANUAL"),
    })
    Optional<AccionesPermitidas> findByAccionAndEstadoAndNivel(AccionesUsuario accion, MasDataEstado estado, String nivel);
}
