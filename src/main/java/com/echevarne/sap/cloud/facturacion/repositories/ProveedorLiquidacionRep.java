package com.echevarne.sap.cloud.facturacion.repositories;

import java.util.Calendar;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.echevarne.sap.cloud.facturacion.model.liquidaciones.ProveedorLiquidacion;

@Repository("proveedorLiquidacionRep")
public interface ProveedorLiquidacionRep extends JpaRepository<ProveedorLiquidacion, Long> {

	@Query("SELECT pr FROM ProveedorLiquidacion pr JOIN pr.provLiqRemitenteProfesional rp WHERE pr.organizacionVentas = ?1 "
			+ " AND pr.delegacion = ?2 AND rp.remitenteProfesional = ?3 AND pr.sector = ?4 "
			+ " AND ((pr.fechaInicio <= ?5 AND pr.fechaFin >= ?5) OR (pr.fechaInicio <= ?6 AND pr.fechaFin >= ?6))")
	List<ProveedorLiquidacion> findSolapamientos(String organizacionVentas, String delegacion, String remitenteProfesional, String sector, Calendar fechaIni,
			Calendar fechaFin);

	@Query("SELECT pr FROM ProveedorLiquidacion pr WHERE pr.organizacionVentas = ?1 AND pr.proveedor = ?2 ORDER BY pr.fechaInicio DESC")
	List<ProveedorLiquidacion> findByProveedor(String organizacionVentas, String idProveedorLiquidacion);

}
