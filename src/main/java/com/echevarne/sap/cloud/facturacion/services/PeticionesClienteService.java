package com.echevarne.sap.cloud.facturacion.services;

import com.echevarne.sap.cloud.facturacion.model.actualizacion.PeticionesCliente;
import com.echevarne.sap.cloud.facturacion.model.actualizacion.ProcesoActualizacion;
import java.util.List;
import java.util.Optional;

import javax.persistence.Tuple;

public interface PeticionesClienteService {

    List<PeticionesCliente> findAllByCodigoCliente(String codigoCliente);

    Integer countByCodigoCliente(String codigoCliente);

    int countByParams(ProcesoActualizacion procesoActualizacion);

    List<PeticionesCliente> findAllByListaCodigos(List<String> listaCodigos);

    List<PeticionesCliente> findAll();

    List<PeticionesCliente> findAllById(Iterable<Long> ids);

    Optional<PeticionesCliente> findById(Long id);
    
    public List<Tuple> findByParams(ProcesoActualizacion procesoActualizacion);
}
