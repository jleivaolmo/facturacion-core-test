package com.echevarne.sap.cloud.facturacion.repositories;

import java.util.Set;
import com.echevarne.sap.cloud.facturacion.model.replicated.S4Info;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import javax.persistence.QueryHint;

/**
 * Class for repository {@link S4InfoRep}.
 *
 * Repository for the Model: ConversionCentro
 */
@Repository("s4Info")
public interface S4InfoRep extends JpaRepository<S4Info, Long> {

	/**
	 * Obtiene las reglas de facturacion vigentes a una fecha con el cliente y
	 * organizacion de ventas pasados por parametro o con "*".
	 *
	 */
	@QueryHints({
		@QueryHint(name = org.hibernate.jpa.QueryHints.HINT_FLUSH_MODE, value = "MANUAL"),
	})
	@Query("SELECT i FROM S4Info i WHERE i.MATERIAL = :material and i.CLIENTE IN (:codigoCliente, '*')"
			+ " and i.ORG_VENTAS IN (:organizacionVentas, '*') and i.DELEGACION IN (:delegacion, '*') and i.CIANR IN (:compania, '*')"
			+ " and i.FECHA_INICIO <= :now and i.FECHA_FIN >= :now ORDER BY i.PRIORIDAD DESC")
	Set<S4Info> findByParams(@Param("material") String material, @Param("codigoCliente") String codigoCliente, @Param("organizacionVentas") String organizacionVentas,
			@Param("delegacion") String delegacion, @Param("compania") String compania, @Param("now") String now);

}
