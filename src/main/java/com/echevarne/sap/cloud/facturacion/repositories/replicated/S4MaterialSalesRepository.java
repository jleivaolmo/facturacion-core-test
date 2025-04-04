package com.echevarne.sap.cloud.facturacion.repositories.replicated;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Repository;
import com.echevarne.sap.cloud.facturacion.model.replicated.S4MaterialSales;

import javax.persistence.QueryHint;

@Repository("s4MaterialSalesRepository")
public interface S4MaterialSalesRepository extends JpaRepository<S4MaterialSales, String> {

	@QueryHints({
		@QueryHint(name = org.hibernate.jpa.QueryHints.HINT_FLUSH_MODE, value = "MANUAL"),
	})
	Optional<S4MaterialSales> findByMATNRAndVKORGAndVTWEG(String matnr, String vkorg, String vtweg);

}
