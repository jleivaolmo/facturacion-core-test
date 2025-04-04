package com.echevarne.sap.cloud.facturacion.repositories;

import com.echevarne.sap.cloud.facturacion.model.masterdata.MasDataFlujoLanes;
import com.echevarne.sap.cloud.facturacion.services.MasDataBaseService;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("masDataFlujoLanesRep")
public interface MasDataFlujoLanesRep extends JpaRepository<MasDataFlujoLanes,Long>, MasDataBaseService<MasDataFlujoLanes, Long> {

}