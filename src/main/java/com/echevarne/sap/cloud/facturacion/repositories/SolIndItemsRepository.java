package com.echevarne.sap.cloud.facturacion.repositories;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.echevarne.sap.cloud.facturacion.model.solicitudindividual.SolIndItems;
import com.echevarne.sap.cloud.facturacion.model.solicitudindividual.SolicitudIndividual;

import java.util.List;

@Repository("solIndItemsRepository")
public interface SolIndItemsRepository extends JpaRepository<SolIndItems, Long> {

    @EntityGraph(
       type = EntityGraph.EntityGraphType.FETCH,
       attributePaths = {
         "trazabilidad",
         "solicitudInd",
         "solicitudInd.trazabilidad"
       }
    )
    @Query("SELECT i FROM SolIndItems i WHERE i.id IN :ids")
    List<SolIndItems> findAllByIdWithTrazabilidadAndSolicitudIndAndItsTrazabilidad(@Param("ids") List<Long> ids);
    
	List<SolIndItems> findAllBySolicitudIndAndHigherLevelltemAndSalesOrderItemCategory(SolicitudIndividual si, int higherLevelItem,
			String salesOrderItemCategory);
}
