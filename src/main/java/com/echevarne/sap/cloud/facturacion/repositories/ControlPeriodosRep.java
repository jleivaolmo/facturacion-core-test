package com.echevarne.sap.cloud.facturacion.repositories;

import com.echevarne.sap.cloud.facturacion.model.facturacion.ControlPeriodos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Class for repository {@link ControlPeriodosRep}.
 *
 * <p>
 * . . .
 * </p>
 * <p>
 * Repository for the Model: ControlPeriodos
 * </p>
 *
 * @author Germán Laso
 * @version 1.0
 * @since 04/05/2021
 *
 */
@Repository("controlPeriodosRep")
public interface ControlPeriodosRep extends JpaRepository<ControlPeriodos, Long> {

    Optional<ControlPeriodos> findById(Long id);
}
