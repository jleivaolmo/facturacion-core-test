package com.echevarne.sap.cloud.facturacion.repositories;

import java.util.Optional;

import com.echevarne.sap.cloud.facturacion.model.privados.CondicionPrecioAdicionalItem;
import com.echevarne.sap.cloud.facturacion.model.trazabilidad.Trazabilidad;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("condicionPrecioAdicionalItemRep")
public interface CondicionPrecioAdicionalItemRep extends JpaRepository<CondicionPrecioAdicionalItem, Long>{

    Optional<CondicionPrecioAdicionalItem> findByTrazabilidadAndConditionType(Trazabilidad trazabilidad, String conditionType);

}
