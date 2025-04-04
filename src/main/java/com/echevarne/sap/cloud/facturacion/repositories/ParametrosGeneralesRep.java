package com.echevarne.sap.cloud.facturacion.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.echevarne.sap.cloud.facturacion.model.parametrizacion.ParametrosGenerales;

@Repository("parametrosGeneralesRep")
public interface ParametrosGeneralesRep extends JpaRepository<ParametrosGenerales, Long> {
	
	ParametrosGenerales findByNombre(String nombre);
	
}
