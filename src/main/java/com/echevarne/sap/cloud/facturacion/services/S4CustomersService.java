package com.echevarne.sap.cloud.facturacion.services;

import java.util.Optional;

import com.echevarne.sap.cloud.facturacion.model.replicated.S4Customers;

public interface S4CustomersService {

    Optional<S4Customers> findByKUNNR(String kunnr);

}
