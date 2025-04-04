package com.echevarne.sap.cloud.facturacion.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.echevarne.sap.cloud.facturacion.model.contratos.ContCapitativoInterlocutores;

/**
 * Class for repository {@link ContCapitativoInterlocutoresRep}.
 * 
 * <p>. . .</p>
 * <p>Repository for the Model: ContCapitativoInterlocutores</p>
 *
 * @author Germán Laso
 * @version 1.0
 * @since 17/04/2020
 * 
 */
@Repository("contCapitativoInterlocutoresRep")
public interface ContCapitativoInterlocutoresRep extends JpaRepository<ContCapitativoInterlocutores, Long> {

	Optional<ContCapitativoInterlocutores> findById(Long id);

}
