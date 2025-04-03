package com.echevarne.sap.cloud.facturacion.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.echevarne.sap.cloud.facturacion.model.Log;

/**
 * Class for the repository{@link LogRep}.
 * 
 * <p>This is a the Entity repository for Log. . .</p>
 *
 * @author Steven Ricardo Mendez Brenes
 * @version 1.0
 * @since 06/24/2019
 * 
 */
@Repository("logRep")
public interface LogRep  extends JpaRepository<Log, Long> {

}
