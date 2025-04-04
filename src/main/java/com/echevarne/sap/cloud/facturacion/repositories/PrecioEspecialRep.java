package com.echevarne.sap.cloud.facturacion.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.echevarne.sap.cloud.facturacion.model.PrecioEspecial;

/**
 * Class for repository {@link PrecioEspecialRep}.
 * 
 * <p>Repository for the Model: Precios especiales</p>
 */
@Repository("precioEspecialRep")
public interface PrecioEspecialRep extends JpaRepository<PrecioEspecial, Long> {

	PrecioEspecial findByPagadorAndClienteAndConcepto(String pagador, String cliente, String concepto);

}
