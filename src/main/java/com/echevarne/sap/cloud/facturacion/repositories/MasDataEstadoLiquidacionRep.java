package com.echevarne.sap.cloud.facturacion.repositories;

import javax.persistence.QueryHint;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Repository;
import com.echevarne.sap.cloud.facturacion.model.masterdata.MasDataEstado;
import com.echevarne.sap.cloud.facturacion.model.masterdata.MasDataEstadoLiquidacion;
import com.echevarne.sap.cloud.facturacion.services.MasDataBaseService;

/**
 * Repository for the Model {@link MasDataEstado}
 * @author Hernan Girardi
 * @since 18/06/2020
 */
@Repository("masDataEstadoLiquidacionRep")
public interface MasDataEstadoLiquidacionRep extends JpaRepository<MasDataEstadoLiquidacion, Long>, MasDataBaseService<MasDataEstadoLiquidacion, Long> {
	
	@QueryHints({
		@QueryHint(name = org.hibernate.jpa.QueryHints.HINT_CACHEABLE, value = "true"),
		@QueryHint(name = org.hibernate.jpa.QueryHints.HINT_FLUSH_MODE, value = "MANUAL"),
	})
	MasDataEstadoLiquidacion findByCodigo(Integer codigoEstado);

}
