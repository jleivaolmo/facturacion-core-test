package com.echevarne.sap.cloud.facturacion.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.echevarne.sap.cloud.facturacion.model.trazabilidad.Trazabilidad;
import com.echevarne.sap.cloud.facturacion.model.trazabilidad.TrazabilidadClasificacion;

@Repository("TrazabilidadClasificacionRep")
public interface TrazabilidadClasificacionRepository extends JpaRepository<TrazabilidadClasificacion, Long> {

	List<TrazabilidadClasificacion> findByTrazabilidadPrueba(Trazabilidad trazabilidadPrueba);

}
