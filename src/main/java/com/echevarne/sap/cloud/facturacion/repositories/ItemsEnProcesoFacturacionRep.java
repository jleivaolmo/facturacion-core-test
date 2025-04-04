package com.echevarne.sap.cloud.facturacion.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.echevarne.sap.cloud.facturacion.model.views.ItemsEnProcesoFacturacion;

@Repository("itemsEnProcesoFacturacionRep")
public interface ItemsEnProcesoFacturacionRep extends JpaRepository<ItemsEnProcesoFacturacion, Long> {

	List<ItemsEnProcesoFacturacion> findByCodigoPeticion(String codigoPeticion);
}
