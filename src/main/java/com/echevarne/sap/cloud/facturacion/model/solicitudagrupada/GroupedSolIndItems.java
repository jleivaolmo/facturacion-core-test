package com.echevarne.sap.cloud.facturacion.model.solicitudagrupada;

import java.math.BigInteger;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupedSolIndItems {
    private BigInteger count;
    private String tipoPosicion;
    private String material;
    private String priceReferenceMaterial;
    private String productionPlant;
    private String profitCenter;
    private String delProductiva;
    private String codigoDelegacion;
    private String grupoPrecio;
    private String listaPrecio;
    private String tipoPeticion;
    private String organizationDivision;
}



