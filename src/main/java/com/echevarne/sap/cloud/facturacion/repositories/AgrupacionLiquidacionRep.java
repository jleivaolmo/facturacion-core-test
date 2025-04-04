package com.echevarne.sap.cloud.facturacion.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.echevarne.sap.cloud.facturacion.model.liquidaciones.AgrupacionLiquidacion;

@Repository("agrupacionLiquidacionRep")
public interface AgrupacionLiquidacionRep extends JpaRepository<AgrupacionLiquidacion, Long> {

	List<AgrupacionLiquidacion> findByEstado(Integer estado);

	@Modifying
	@Query("UPDATE AgrupacionLiquidacion a SET a.enviada = true WHERE a.id IN :ids")
	void updateEnviadaByIds(List<Long> ids);
}
