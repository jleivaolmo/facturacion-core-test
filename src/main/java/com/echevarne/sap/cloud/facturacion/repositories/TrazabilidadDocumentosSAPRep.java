package com.echevarne.sap.cloud.facturacion.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.echevarne.sap.cloud.facturacion.model.trazabilidad.TrazabilidadDocumentosSAP;

@Repository("trazabilidadDocumentosSAPRep")
public interface TrazabilidadDocumentosSAPRep extends JpaRepository<TrazabilidadDocumentosSAP, Long> {

	@Query("SELECT trd FROM TrazabilidadDocumentosSAP trd WHERE trd.tipoDocumento.codigo = ?1 "
			+ " AND trd.valor = ?2")
	List<TrazabilidadDocumentosSAP> findByParams(String codigo, String valor);

}
