package com.echevarne.sap.cloud.facturacion.repositories;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.echevarne.sap.cloud.facturacion.model.reglasfacturacion.ReglasFacturacion;
import com.echevarne.sap.cloud.facturacion.services.ValidityBaseService;

@Repository("reglasFacturacionRep")
public interface ReglasFacturacionRep
		extends JpaRepository<ReglasFacturacion, Long> {

	/**
	 * Obtiene las reglas de facturacion vigentes a una fecha con el cliente y
	 * organizacion de ventas pasados por parametro o con "*".
	 *
	 */
	@EntityGraph(
			type = EntityGraph.EntityGraphType.FETCH,
			attributePaths = {
					"organizacionText",
					"clienteText",
					"delegacionText",
					"muestraRemitidaText"
			}
	)
	@Query("SELECT rf FROM ReglasFacturacion rf WHERE rf.validezDesde <= ?1 and rf.validezHasta >= ?1 "
			+ "and rf.codigoCliente IN (?2, '*') and rf.organizacionVentas IN (?3, '*')")
	List<ReglasFacturacion> findVigentesByParamsOrDefault(Calendar fechaVigencia, String codigoCliente, String organizacionVentas);

}
