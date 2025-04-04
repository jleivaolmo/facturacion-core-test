package com.echevarne.sap.cloud.facturacion.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.echevarne.sap.cloud.facturacion.model.SociedadCentroAlmacen;

@Repository("sociedadCentroAlmacenRep")
public interface SociedadCentroAlmacenRep extends JpaRepository<SociedadCentroAlmacen, Long> {
	
	SociedadCentroAlmacen findBySociedad(String sociedad);

}
