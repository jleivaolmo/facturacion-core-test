package com.echevarne.sap.cloud.facturacion.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.echevarne.sap.cloud.facturacion.model.liquidaciones.AgrupacionLiquidacionDetalle;

@Repository("agrupacionLiquidacionDetalleRep")
public interface AgrupacionLiquidacionDetalleRep extends JpaRepository<AgrupacionLiquidacionDetalle, Long> {

}
