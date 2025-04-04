package com.echevarne.sap.cloud.facturacion.repositories;

import com.echevarne.sap.cloud.facturacion.model.salidas.FacturaPendienteSalida;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository("FacturaPendienteSalidaRep")
public interface FacturaPendienteSalidaRepository extends JpaRepository<FacturaPendienteSalida, Long> {

    List<FacturaPendienteSalida> findByFechaFacturaGreaterThanEqual(Date startDate);

}
