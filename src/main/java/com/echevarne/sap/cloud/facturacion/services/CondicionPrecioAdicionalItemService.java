package com.echevarne.sap.cloud.facturacion.services;

import java.util.Optional;

import com.echevarne.sap.cloud.facturacion.model.privados.CondicionPrecioAdicionalItem;
import com.echevarne.sap.cloud.facturacion.model.trazabilidad.Trazabilidad;

public interface CondicionPrecioAdicionalItemService extends CrudService<CondicionPrecioAdicionalItem, Long> {

    Optional<CondicionPrecioAdicionalItem> findByTrazabilidadAndConditionType(Trazabilidad trazabilidad, String conditionType);

}