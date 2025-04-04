package com.echevarne.sap.cloud.facturacion.repositories.replicated;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Repository;

import com.echevarne.sap.cloud.facturacion.model.replicated.S4CuentasMayor;

import javax.persistence.QueryHint;

/**
 * Repository for the Model {@link S4CuentasMayor}
 */
@Repository("s4CuentasMayorRep")
public interface S4CuentasMayorRepository extends JpaRepository<S4CuentasMayor, String> {

	@QueryHints({
		@QueryHint(name = org.hibernate.jpa.QueryHints.HINT_FLUSH_MODE, value = "MANUAL"),
	})
	Optional<S4CuentasMayor> findByCodigoOperacionAndIdMedioPagoAndCodigoDelegacion(String codigoOperacion, String idMetodoPago, String codigoDelegacion);

}
