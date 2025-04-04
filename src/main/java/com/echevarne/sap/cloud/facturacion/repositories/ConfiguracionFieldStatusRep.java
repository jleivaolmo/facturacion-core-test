package com.echevarne.sap.cloud.facturacion.repositories;

import java.util.List;
import java.util.Optional;

import com.echevarne.sap.cloud.facturacion.model.entities.ConfiguracionFieldStatus;
import com.echevarne.sap.cloud.facturacion.model.masterdata.MasDataEstadosGrupo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.QueryHint;

/**
 * Class for repository {@link ConfiguracionFieldStatusRep}.
 *
 * @author davidbolet
 * @version 1.0
 * @since 29/01/2021
 */
@Repository("configuracionFieldStatusRep")
public interface ConfiguracionFieldStatusRep extends JpaRepository<ConfiguracionFieldStatus,Long> {

	@QueryHints({
			@QueryHint(name = org.hibernate.jpa.QueryHints.HINT_CACHEABLE, value = "true"),
			@QueryHint(name = org.hibernate.jpa.QueryHints.HINT_FLUSH_MODE, value = "MANUAL"),
	})
	@Query("SELECT cfs FROM ConfiguracionFieldStatus cfs  "
			+ "INNER JOIN cfs.entityFieldName efn "
			+ "INNER JOIN efn.entity eln "
			+ "INNER JOIN cfs.masDataEstadosGrupo mdeg "
			+ "WHERE cfs.configurationLevel = :level "
			+ "AND efn.nombreCampo = :nombreCampo "
			+ "AND eln.nombreEntidad = :nombreEntidad "
			+ "AND mdeg = :estado")
	Optional<ConfiguracionFieldStatus> findByEntidadNombreNivelYEstado(@Param("nombreEntidad") String nombreEntidad,
																			@Param("nombreCampo") String nombreCampo,
																			@Param("level") String level,
																			@Param("estado") MasDataEstadosGrupo estado);

	@QueryHints({
			@QueryHint(name = org.hibernate.jpa.QueryHints.HINT_CACHEABLE, value = "true"),
			@QueryHint(name = org.hibernate.jpa.QueryHints.HINT_FLUSH_MODE, value = "MANUAL"),
	})
    Optional<List<ConfiguracionFieldStatus>> findByInactive(boolean inactive);
}
