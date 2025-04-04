package com.echevarne.sap.cloud.facturacion.repositories;

import java.util.Calendar;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.echevarne.sap.cloud.facturacion.model.liquidaciones.ReglaLiquidacionProfesionales;

@Repository("reglaLiquidacionProfesionalesRep")
public interface ReglaLiquidacionProfesionalesRep extends JpaRepository<ReglaLiquidacionProfesionales, Long> {

	@Query("SELECT r FROM ReglaLiquidacionProfesionales r JOIN r.companias c JOIN r.delsProductiva d JOIN r.pruebas p "
			+ " JOIN r.clientes l JOIN r.oficinasVentas o JOIN r.profesionales f JOIN r.undsProductivas u " + " JOIN r.conceptosFact n JOIN r.tiposPet t"
			+ " WHERE (l.codigoCliente = ?1 OR l.codigoCliente = '*') " + " AND (o.oficinaVentas = ?2 OR o.oficinaVentas = '*') "
			+ " AND (f.codigoProfesional = ?3 OR f.codigoProfesional = '*') " + " AND (c.compania = ?4 OR c.compania = '*') "
			+ " AND (d.delegacionProductiva = ?5 OR d.delegacionProductiva = '*') " + " AND (u.unidadProductiva = ?6 OR u.unidadProductiva = '*') "
			+ " AND (r.esMuestraRemitida = ?7 OR r.esMuestraRemitida = false) " + " AND (p.prueba = ?8 OR p.prueba ='*') " + " AND r.codigoOrganizacion = ?9 "
			+ " AND (n.codigoConcepto = ?10 OR n.codigoConcepto ='*') " + " AND (t.tipoPeticion = ?11 OR t.tipoPeticion ='0') "
			+ " AND r.validoDesde <= ?12 AND r.validoHasta >= ?12 ORDER BY r.prioridad")
	List<ReglaLiquidacionProfesionales> findReglas(String codigoCliente, String oficinaVentas, String codigoProfesional, String codigoCompania,
			String delProductiva, String unidadProductiva, Boolean esMuestraRemitida, String codigoPrueba, String codigoOrganizacion, String codigoConcepto,
			Integer tipoPeticion, Calendar fechaPeticion);

}
