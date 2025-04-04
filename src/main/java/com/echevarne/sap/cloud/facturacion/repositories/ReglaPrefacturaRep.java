package com.echevarne.sap.cloud.facturacion.repositories;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.echevarne.sap.cloud.facturacion.model.ReglaPrefactura;

/**
 * Class for repository {@link ReglaPrefacturaRep}.
 * <p>Repository for the Model: ReglaPrefacturaRep</p>
 */
@Repository("reglaPrefacturaRep")
public interface ReglaPrefacturaRep extends JpaRepository<ReglaPrefactura, Long> {


	@Query("SELECT rp FROM ReglaPrefactura rp WHERE " +
			"rp.organizacionVentas IN ?1 and rp.cliente IN ?2 and rp.oficinaVentas IN ?3 and rp.tipoPeticion IN ?4 " +
			"and rp.empresa IN ?5 and rp.compania IN ?6 and rp.remitente IN ?7 " +
			"and rp.validezDesde <= ?8 and rp.validezHasta >= ?8")
	List<ReglaPrefactura> findByParams(Collection<String> organizaciones, Collection<String> clientes,
									   Collection<String> oficinas, Collection<String> tipos, Collection<String> empresas,
									   Collection<String> companias, Collection<String> remitentes, Date fechaValidez);
}
