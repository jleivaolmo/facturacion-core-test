package com.echevarne.sap.cloud.facturacion.repositories;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Repository;

import com.echevarne.sap.cloud.facturacion.model.contratos.ContratoCapitativo;

import javax.persistence.QueryHint;

/**
 * Class for repository {@link ContratoCapitativoRep}.
 *
 * <p>
 * . . .
 * </p>
 * <p>
 * Repository for the Model: ContratoCapitativo
 * </p>
 *
 */
@Repository("contratoCapitativoRep")
public interface ContratoCapitativoRep extends JpaRepository<ContratoCapitativo, Long> {

	Optional<ContratoCapitativo> findById(Long id);

	@QueryHints({
		@QueryHint(name = org.hibernate.jpa.QueryHints.HINT_FLUSH_MODE, value = "MANUAL"),
	})
	List<ContratoCapitativo> findByOrganizacionVentasAndCodigoClienteAndValidoDesdeLessThanEqualAndValidoHastaGreaterThanEqual(
			String OrganizacionVenta, String codigoCliente, Calendar startDate, Calendar endDate);

}
