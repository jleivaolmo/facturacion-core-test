package com.echevarne.sap.cloud.facturacion.repositories;

import java.util.Calendar;
import java.util.Collection;
import java.util.List;

import com.echevarne.sap.cloud.facturacion.model.determinaciones.BloqueoCortesia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Repository;

import javax.persistence.QueryHint;


/**
 * Class for repository {@link BloqueoCortesiaRep}.
 * <p>Repository for the Model: BloqueoCortesia</p>
 */
@Repository("bloqueoCortesiaRep")
public interface BloqueoCortesiaRep extends JpaRepository<BloqueoCortesia, Long> {


	@Query("SELECT rf FROM BloqueoCortesia rf WHERE " +
			"rf.organizacionVentas IN ?1 and rf.cliente IN ?2 and rf.oficinaVentas IN ?3 and rf.tipoPeticion IN ?4 " +
			"and rf.empresa IN ?5 and rf.compania IN ?6 and rf.remitente IN ?7 " +
			"and rf.validezDesde <= ?8 and rf.validezHasta >= ?8")
	@QueryHints({
		@QueryHint(name = org.hibernate.jpa.QueryHints.HINT_FLUSH_MODE, value = "MANUAL"),
	})
	List<BloqueoCortesia> findByParams(Collection<String> organizaciones, Collection<String> clientes,
                                       Collection<String> oficinas, Collection<Integer> tipos, Collection<String> empresas,
                                       Collection<String> companias, Collection<String> remitentes, Calendar fechaValidez);
}
