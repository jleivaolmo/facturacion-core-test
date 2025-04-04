package com.echevarne.sap.cloud.facturacion.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.echevarne.sap.cloud.facturacion.model.incongruentes.IncongCrit;
import com.echevarne.sap.cloud.facturacion.services.ValidityBaseService;

@Repository("incongCritRep")
public interface IncongCritRep extends JpaRepository<IncongCrit, Long> {

}
