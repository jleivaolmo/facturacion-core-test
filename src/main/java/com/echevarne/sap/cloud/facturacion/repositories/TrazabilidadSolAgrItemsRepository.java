package com.echevarne.sap.cloud.facturacion.repositories;

import com.echevarne.sap.cloud.facturacion.model.trazabilidad.TrazabilidadSolAgrItems;
import com.echevarne.sap.cloud.facturacion.model.trazabilidad.TrazabilidadSolicitud;
import com.echevarne.sap.cloud.facturacion.model.trazabilidad.TrazabilidadSolicitudAgrupado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author David Bolet
 * @version 1.0
 * @since 28/04/2020
 *
 */
@Repository("trazabilidadSolAgrItemsRepository")
public interface TrazabilidadSolAgrItemsRepository extends JpaRepository<TrazabilidadSolAgrItems, Long> {


}
