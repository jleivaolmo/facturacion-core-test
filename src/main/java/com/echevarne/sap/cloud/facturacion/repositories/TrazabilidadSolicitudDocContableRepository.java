package com.echevarne.sap.cloud.facturacion.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.echevarne.sap.cloud.facturacion.model.trazabilidad.TrazabilidadSolicitud;
import com.echevarne.sap.cloud.facturacion.model.trazabilidad.TrazabilidadSolicitudDocContable;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Repository("trazabilidadSolicitudDocContableRepository")
public interface TrazabilidadSolicitudDocContableRepository extends JpaRepository<TrazabilidadSolicitudDocContable, Long> {

    List<TrazabilidadSolicitudDocContable> findByTrazabilidadAndTipoOperacion(
            TrazabilidadSolicitud traza, TrazabilidadSolicitudDocContable.TipoOperacion tipoOperacion);
}
 