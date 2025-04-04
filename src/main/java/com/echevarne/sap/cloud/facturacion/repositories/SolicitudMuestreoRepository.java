package com.echevarne.sap.cloud.facturacion.repositories;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.echevarne.sap.cloud.facturacion.model.solicitudrecibida.PeticionMuestreo;
import com.echevarne.sap.cloud.facturacion.model.solicitudrecibida.SolicitudMuestreo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Class for the repository{@link SolicitudMuestreoRepository}.
 * 
 * <p>This is the Entity repository for SolicitudMuestreo. . .</p>
 *
 * @author Steven Ricardo Mendez Brenes
 * @version 1.0
 * @since 06/24/2019
 * 
 */
@Repository("solicitudMuestreoRepository")
public interface SolicitudMuestreoRepository extends JpaRepository<SolicitudMuestreo, Long> {

	Optional<SolicitudMuestreo> findById(Long id);

	SolicitudMuestreo findByCodigoPeticion(String codigoPeticion);

	@Query("SELECT pm.codigoPeticion FROM SolicitudMuestreo sm JOIN sm.peticion pm WHERE sm.codigoPeticion=:codigoSolicitud")
    List<String> findCodigosPeticionesInSolicitud(@Param("codigoSolicitud") String codigoSolicitud);

	@Query("SELECT pm FROM SolicitudMuestreo sm JOIN sm.peticion pm WHERE sm.codigoPeticion=:codigoSolicitud")
    List<PeticionMuestreo> findPeticionesInSolicitud(@Param("codigoSolicitud") String codigoSolicitud);

	@Query("FROM SolicitudMuestreo sm WHERE sm.esMixta=true and sm.entityCreationTimestamp<=:from")
    List<SolicitudMuestreo> findSolicitudesMixtasFromDate(@Param("from") Timestamp from);
	
	@Query("FROM SolicitudMuestreo sm JOIN sm.peticion pm JOIN pm.fechas dt WHERE dt.fechaPeticion =:fecha")
    List<SolicitudMuestreo> findSolicitudesMuestreoFecha(@Param("fecha") Date fecha);
}
