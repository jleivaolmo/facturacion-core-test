package com.echevarne.sap.cloud.facturacion.services;

import java.util.Optional;

import com.echevarne.sap.cloud.facturacion.model.masterdata.MasDataEstado;
import com.echevarne.sap.cloud.facturacion.model.parametrizacion.AccionesPermitidas;
import com.echevarne.sap.cloud.facturacion.model.parametrizacion.AccionesUsuario;

/**
 * Interface for the service{@link AccionesPermitidasService}.
 *
 * <p>This is a interface for Services. . .</p>
 *
 * @author Germán Laso
 * @version 1.0
 * @since 08/03/2021
 *
 */
public interface AccionesPermitidasService extends CrudService<AccionesPermitidas, Long> {

    Optional<AccionesPermitidas> findAccionPermitidaPorEstado(AccionesUsuario accion, MasDataEstado estado);

    Optional<AccionesPermitidas> findAccionPermitidaPorEstadoYNivel(AccionesUsuario accion, MasDataEstado estado, String nivel);

}
