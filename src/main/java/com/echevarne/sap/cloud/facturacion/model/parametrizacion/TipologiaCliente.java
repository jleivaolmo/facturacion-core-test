package com.echevarne.sap.cloud.facturacion.model.parametrizacion;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TipologiaCliente {

    Grande("GC"), Pequeño("PE");

    private final String tipologiaCliente;

}
