package com.echevarne.sap.cloud.facturacion.repositories;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Repository;

import com.echevarne.sap.cloud.facturacion.model.transformacion.Trans;

import javax.persistence.QueryHint;
import java.util.List;

@Repository("transRepository")
public interface TransRepository extends JpaRepository<Trans, Long> {

	@QueryHints({
		@QueryHint(name = org.hibernate.jpa.QueryHints.HINT_CACHEABLE, value = "true"),
	})
	@EntityGraph(
		type = EntityGraph.EntityGraphType.FETCH,
		attributePaths = {
			"rules",
			"rules.criteria",
		}
	)
	@Query("SELECT t from Trans t")
	List<Trans> findAllWithRulesAndCriterias();

}
