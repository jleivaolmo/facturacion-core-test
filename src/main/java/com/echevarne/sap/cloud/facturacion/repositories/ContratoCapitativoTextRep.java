package com.echevarne.sap.cloud.facturacion.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.echevarne.sap.cloud.facturacion.model.contratos.ContratoCapitativoText;

/**
 * Class for repository {@link ContratoCapitativoTextRep}.
 * 
 * <p>
 * . . .
 * </p>
 * <p>
 * Repository for the Model: ContratoCapitativo
 * </p>
 *
 * @author Germán Laso
 * @version 1.0
 * @since 17/04/2020
 * 
 */
@Repository("contratoCapitativoTextRep")
public interface ContratoCapitativoTextRep extends JpaRepository<ContratoCapitativoText, Long> {

	Optional<ContratoCapitativoText> findById(Long id);

}
