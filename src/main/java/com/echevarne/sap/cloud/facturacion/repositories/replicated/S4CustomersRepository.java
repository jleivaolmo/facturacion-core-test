package com.echevarne.sap.cloud.facturacion.repositories.replicated;

import java.util.Optional;

import javax.persistence.QueryHint;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Repository;

import com.echevarne.sap.cloud.facturacion.model.replicated.S4Customers;

/**
 * Class for the repository{@link S4CustomersRepository}.
 *
 * <p>
 * This is the Entity repository for SolicitudMuestreo. . .
 * </p>
 *
 * @author Steven Ricardo Mendez Brenes
 * @version 1.0
 * @since 06/24/2019
 *
 */
@Repository("s4CustomersRepository")
public interface S4CustomersRepository extends JpaRepository<S4Customers, String> {

	@QueryHints({ @QueryHint(name = org.hibernate.jpa.QueryHints.HINT_FLUSH_MODE, value = "MANUAL"), })
	Optional<S4Customers> findBySTCD1(String stcd1);

	@QueryHints({ @QueryHint(name = org.hibernate.jpa.QueryHints.HINT_FLUSH_MODE, value = "MANUAL"), })
	Optional<S4Customers> findBySTCD2(String stcd2);

	@QueryHints({ @QueryHint(name = org.hibernate.jpa.QueryHints.HINT_FLUSH_MODE, value = "MANUAL"), })
	Optional<S4Customers> findBySTCD3(String stcd3);
	
	@QueryHints({ @QueryHint(name = org.hibernate.jpa.QueryHints.HINT_FLUSH_MODE, value = "MANUAL"), })
	Optional<S4Customers> findBySTCD4(String stcd4);
	
	@QueryHints({ @QueryHint(name = org.hibernate.jpa.QueryHints.HINT_FLUSH_MODE, value = "MANUAL"), })
	Optional<S4Customers> findBySTCD5(String stcd5);

	@QueryHints({ @QueryHint(name = org.hibernate.jpa.QueryHints.HINT_FLUSH_MODE, value = "MANUAL"), })
	Optional<S4Customers> findBySTCEG(String stceg);
}
