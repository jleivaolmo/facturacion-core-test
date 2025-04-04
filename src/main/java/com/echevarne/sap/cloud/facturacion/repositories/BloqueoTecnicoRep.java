package com.echevarne.sap.cloud.facturacion.repositories;

import com.echevarne.sap.cloud.facturacion.model.actualizacion.BloqueoTecnico;
import com.echevarne.sap.cloud.facturacion.model.trazabilidad.TrazabilidadSolicitud;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Class for repository {@link BloqueoTecnico}.
 * <p>Repository for the Model: BloqueoTecnico</p>
 */
@Repository("bloqueoTecnicoRep")
public interface BloqueoTecnicoRep extends JpaRepository<BloqueoTecnico, Long> {

	@Query("SELECT bt FROM BloqueoTecnico bt WHERE bt.inicioBloqueo <= CURRENT_TIMESTAMP and (bt.finBloqueo is null or bt.finBloqueo >= CURRENT_TIMESTAMP)")
	List<BloqueoTecnico> findActivos();
	
	@Query("SELECT bt FROM BloqueoTecnico bt WHERE bt.uuidInstance = ?1")
	List<BloqueoTecnico> findActivosUUIDInstance(String UUIDInstance);

	Optional<BloqueoTecnico> findByTrazabilidadSolicitud(TrazabilidadSolicitud trazabilidadSolicitud);

	@Query("SELECT COUNT(bt) FROM BloqueoTecnico bt WHERE bt.trazabilidadSolicitud = ?1")
	Integer countByTrazabilidadSolicitud(TrazabilidadSolicitud trazabilidadSolicitud);

	@Modifying
	@Query("delete from BloqueoTecnico bt where bt.trazabilidadSolicitud in :trazabilidades")
	void deleteByTrazabilidadSolicitudIsIn(@Param("trazabilidades") List<TrazabilidadSolicitud> trazabilidades);
}
