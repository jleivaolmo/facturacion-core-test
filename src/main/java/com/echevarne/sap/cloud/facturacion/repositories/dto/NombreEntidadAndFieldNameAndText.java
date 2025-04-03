package com.echevarne.sap.cloud.facturacion.repositories.dto;

import lombok.NonNull;
import lombok.Value;

@Value
public class NombreEntidadAndFieldNameAndText {
    @NonNull
    String nombreEntidad;
    @NonNull
    String nombreCampo;
    @NonNull
    String textoCampo;
}
