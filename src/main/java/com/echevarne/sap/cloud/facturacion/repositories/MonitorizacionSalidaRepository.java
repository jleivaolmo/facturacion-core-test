package com.echevarne.sap.cloud.facturacion.repositories;

import com.echevarne.sap.cloud.facturacion.model.salidas.MonitorizacionSalida;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository("MonitorizacionSalidaRep")
public interface MonitorizacionSalidaRepository extends JpaRepository<MonitorizacionSalida, Long> {

    @Query("SELECT DISTINCT t.billingDocument " +
            "FROM Trazabilidad t " +
            "WHERE t.billingDocument IS NOT NULL " +
            "AND t.billingDocument NOT IN (SELECT DISTINCT m.factura FROM MonitorizacionSalida m WHERE m.estado = 'SUCCESS')")
    List<String> findFacturasPendientes();

    @Query("SELECT DISTINCT t.billingDocument " +
            "FROM Trazabilidad t " +
            "WHERE t.billingDocument IS NOT NULL " +
            "AND t.fechaFactura >= :startDate " +
            "AND t.billingDocument NOT IN (SELECT DISTINCT m.factura FROM MonitorizacionSalida m WHERE m.estado = 'SUCCESS')")
    List<String> findFacturasPendientes(@Param("startDate") Date startDate);

}
