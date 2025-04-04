package com.echevarne.sap.cloud.facturacion.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.echevarne.sap.cloud.facturacion.model.solicitudrecibida.SolMuesEstado;

/**
 * @author Steven Mendez
 * @version 1.0
 * @since 09/02/2019
 *
 */
@Repository("solMuesEstadoRepository")
public interface SolMuesEstadoRepository extends JpaRepository<SolMuesEstado, Long> {

	Optional<SolMuesEstado> findById(Long id);

}
