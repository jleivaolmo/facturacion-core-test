package com.echevarne.sap.cloud.facturacion.repositories;

import com.echevarne.sap.cloud.facturacion.model.liquidaciones.EstadoLiquidacionPeticion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Class for repository {@link EstadoLiquidacionPeticionRep}.
 * <p>Repository for the Model: BloqueoCortesia</p>
 */
@Repository("estadoLiquidacionPeticionRep")
public interface EstadoLiquidacionPeticionRep extends JpaRepository<EstadoLiquidacionPeticion, Long> {


	
}
