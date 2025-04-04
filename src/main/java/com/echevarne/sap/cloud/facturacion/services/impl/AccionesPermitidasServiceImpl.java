package com.echevarne.sap.cloud.facturacion.services.impl;

import java.util.Optional;

import com.echevarne.sap.cloud.facturacion.model.masterdata.MasDataEstado;
import com.echevarne.sap.cloud.facturacion.model.parametrizacion.AccionesPermitidas;
import com.echevarne.sap.cloud.facturacion.model.parametrizacion.AccionesUsuario;
import com.echevarne.sap.cloud.facturacion.repositories.AccionesPermitidasRep;
import com.echevarne.sap.cloud.facturacion.services.AccionesPermitidasService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Interface for the service{@link AccionesPermitidasService}.
 *
 * <p>
 * This is a interface for Services. . .
 * </p>
 *
 * @author Germán Laso
 * @version 1.0
 * @since 08/03/2021
 *
 */
@Service("accionesPermitidasSrv")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class AccionesPermitidasServiceImpl extends CrudServiceImpl<AccionesPermitidas, Long>
        implements AccionesPermitidasService {

    private final AccionesPermitidasRep accionesPermitidasRep;

    @Autowired
    public AccionesPermitidasServiceImpl(final AccionesPermitidasRep accionesPermitidasRep){
        super(accionesPermitidasRep);
        this.accionesPermitidasRep = accionesPermitidasRep;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AccionesPermitidas> findAccionPermitidaPorEstado(AccionesUsuario accion, MasDataEstado estado) {
        return accionesPermitidasRep.findByAccionAndEstado(accion, estado);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AccionesPermitidas> findAccionPermitidaPorEstadoYNivel(AccionesUsuario accion, MasDataEstado estado, String nivel) {
        return accionesPermitidasRep.findByAccionAndEstadoAndNivel(accion, estado, nivel);
    }
}
