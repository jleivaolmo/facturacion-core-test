package com.echevarne.sap.cloud.facturacion.services;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.echevarne.sap.cloud.facturacion.model.trazabilidad.TrazabilidadSolicitud;
import com.echevarne.sap.cloud.facturacion.model.trazabilidad.TrazabilidadSolicitudDocContable;

/**
 * Class for services {@link TrazabilidadSolicitudDocContableService}.
 */
public interface TrazabilidadSolicitudDocContableService extends CrudService<TrazabilidadSolicitudDocContable, Long> {

    boolean exists(TrazabilidadSolicitud traza, TrazabilidadSolicitudDocContable.TipoOperacion cob);

    List<TrazabilidadSolicitudDocContable> findByTrazabilidadAndTipoOperacion(
            TrazabilidadSolicitud traza,
            TrazabilidadSolicitudDocContable.TipoOperacion tipoOperacion);
}
