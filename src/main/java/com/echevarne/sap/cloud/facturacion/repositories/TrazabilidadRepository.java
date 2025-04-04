package com.echevarne.sap.cloud.facturacion.repositories;

import java.util.List;
import java.util.Optional;
import com.echevarne.sap.cloud.facturacion.model.solicitudrecibida.PeticionMuestreoItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.echevarne.sap.cloud.facturacion.model.trazabilidad.Trazabilidad;

/**
 * @author Steven Mendez
 * @version 1.0
 * @since 09/02/2019
 *
 */
@Repository("trazabilidadRepository")
public interface TrazabilidadRepository extends JpaRepository<Trazabilidad, Long> {
	
	
	/**
	 * 
	 * ACtualización de trazabilidad
	 * 
	 * @param idItem
	 * @param idTrz
	 * @return
	 */
	@Modifying
	@Query(value = "update Trazabilidad trz set fk_SolicitudAgrupadaItem = :idItem where trz.ID = :idTrz", nativeQuery = true)
	int updateTrazabilidaSetFKItemAgrForTrzIdNative(@Param("idItem") Long idItem , @Param("idTrz") Long idTrz);
	
	/**
	 * Busqueda por orden de ventas
	 * 
	 * @param salesOrder
	 * @return
	 */
	Optional<List<Trazabilidad>> findAllBySalesOrder(String salesOrder);
	
	/**
	 * Busqueda por factura
	 * 
	 * @param billingDocument
	 * @return
	 */
	Optional<List<Trazabilidad>> findAllByBillingDocument(String billingDocument);
	
	/**
	 * Busqueda por orden de venta y posicion
	 * 
	 * @param salesOrder
	 * @param salesOrderItem
	 * @return
	 */
	Optional<Trazabilidad> findBySalesOrderAndSalesOrderItem(String salesOrder, String salesOrderItem);

	Optional<Trazabilidad> findByItemRec(PeticionMuestreoItems item);
	
	@Query("SELECT d.codeEstado FROM Trazabilidad t JOIN t.estados e JOIN e.estado d"
			+ " WHERE t.id = ?1 "
			+ " ORDER BY e.sequenceOrder DESC")
	List<String> getUltimoEstado(Long idTrz);

}
 