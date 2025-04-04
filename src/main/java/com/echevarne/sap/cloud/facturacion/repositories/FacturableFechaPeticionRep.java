package com.echevarne.sap.cloud.facturacion.repositories;

import java.util.Calendar;
import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.echevarne.sap.cloud.facturacion.model.determinaciones.FacturableFechaPeticion;

/**
 * Class for repository {@link FacturableFechaPeticionRep}.
 * <p>
 * Repository for the Model: FacturableFechaPeticion
 * </p>
 */
@Repository("facturableFechaPeticionRep")
public interface FacturableFechaPeticionRep extends JpaRepository<FacturableFechaPeticion, Long> {


	@Query("SELECT rf FROM FacturableFechaPeticion rf WHERE " +
			"rf.organizacionVentas IN ?1 and rf.cliente IN ?2 and rf.oficinaVentas IN ?3 and rf.tipoPeticion IN ?4 " +
			"and rf.empresa IN ?5 and rf.compania IN ?6 and rf.remitente IN ?7 " +
			"and rf.validezDesde <= ?8 and rf.validezHasta >= ?8")
	List<FacturableFechaPeticion> findByParams(Collection<String> organizaciones, Collection<String> clientes,
                                       Collection<String> oficinas, Collection<Integer> tipos, Collection<String> empresas,
                                       Collection<String> companias, Collection<String> remitentes, Calendar fechaValidez);
                                       
}