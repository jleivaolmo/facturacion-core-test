package com.echevarne.sap.cloud.facturacion.repositories;

import java.util.Calendar;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.echevarne.sap.cloud.facturacion.model.liquidaciones.LiquidacionRemitentesDetalle;

@Repository("liquidacionRemitentesDetalleRep")
public interface LiquidacionRemitentesDetalleRep extends JpaRepository<LiquidacionRemitentesDetalle, Long> {

	@Query("SELECT DISTINCT l.idTrazabilidad FROM LiquidacionRemitentesDetalle l WHERE (l.sociedad = ?1 OR ?1 = 'null') "
			+ " AND (l.proveedor IN (?2) OR ?2 = 'null') AND (l.codigoRemitente = ?3 OR ?3 = 'null') AND (oficinaVentas = ?4 OR ?4='null') "
			+ " AND l.fechaActividad >= ?5 AND l.fechaActividad <= ?6 AND (l.grupoProveedor IN (?7) OR ?7 = 'null') AND l.estado = 8 ")
	List<Long> getLista(String sociedad, String proveedor, String remitente, String oficinaVentas, Calendar fechaInicio, Calendar fechaFin,
			String grupoProveedor);

}
