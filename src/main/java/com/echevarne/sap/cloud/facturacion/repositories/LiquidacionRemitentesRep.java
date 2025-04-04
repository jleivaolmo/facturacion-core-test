package com.echevarne.sap.cloud.facturacion.repositories;

import java.util.Calendar;
import java.util.List;
import javax.persistence.Tuple;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.echevarne.sap.cloud.facturacion.model.liquidaciones.LiquidacionRemitentes;

@Repository("liquidacionRemitentesRep")
public interface LiquidacionRemitentesRep extends JpaRepository<LiquidacionRemitentes, Long> {

	@Query("SELECT l.idTrazabilidadSolicitud, l.codigoRemitente, l.grupoProveedor FROM LiquidacionRemitentes l " + " WHERE l.proveedor = ?1 "
			+ " AND l.agrupador = ?2 AND l.facturaUnica = ?3 AND l.sociedad = ?4 AND l.organizacionCompras = ?5 "
			+ " AND l.fechaActividad >= ?6 AND l.fechaActividad <= ?7")
	List<Tuple> getLista(String proveedor, String agrupador, Boolean facturaUnica, String sociedad, String organizacionCompras, Calendar fechaInicio,
			Calendar fechaFin);

}
