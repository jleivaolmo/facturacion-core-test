package com.echevarne.sap.cloud.facturacion.services;

import java.util.Set;

import com.echevarne.sap.cloud.facturacion.model.replicated.S4Info;

public interface S4InfoService {
    
	Set<S4Info> findByParams(String material, String codigoCliente,
            String organizacionVentas, String delegacion, String compania);

}