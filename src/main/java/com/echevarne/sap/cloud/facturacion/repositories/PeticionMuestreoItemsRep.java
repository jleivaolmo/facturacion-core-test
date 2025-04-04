package com.echevarne.sap.cloud.facturacion.repositories;

import com.echevarne.sap.cloud.facturacion.model.masterdata.MasDataEstado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.echevarne.sap.cloud.facturacion.model.solicitudrecibida.PeticionMuestreoItems;

import java.sql.Timestamp;
import java.util.List;

/**
 * Class for repository {@link PeticionMuestreoItemsRep}.
 * 
 * <p>. . .</p>
 * <p>Repository for the Model: PeticionMuestreoItems</p>
 *
 * @author Steven Ricardo Mendez Brenes
 * @version 1.0
 * @since 06/24/2019
 * 
 */
@Repository("peticionMuestreoItemsRep")
public interface PeticionMuestreoItemsRep extends JpaRepository<PeticionMuestreoItems, Long> {

	PeticionMuestreoItems findByCodigoPrueba(String codigoPrueba);

	@Query("FROM PeticionMuestreoItems pmi INNER JOIN pmi.peticion pm INNER JOIN pm.solicitud sm INNER JOIN Trazabilidad t ON t.itemRec=pmi " +
			"INNER JOIN TrazabilidadEstHistory tzh ON tzh.trazabilidad=t AND tzh.inactive=false AND tzh.estado=:estado  " +
			"WHERE sm.esMixta=true and pm.esPrivado=false and pm.fechas.fechaPeticion<=:from")
	List<PeticionMuestreoItems> findPruebasMixtasPorEstadoYFecha(@Param("from") Timestamp from, @Param("estado") MasDataEstado estado);

}
