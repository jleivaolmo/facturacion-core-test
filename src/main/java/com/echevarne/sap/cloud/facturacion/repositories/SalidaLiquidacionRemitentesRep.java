package com.echevarne.sap.cloud.facturacion.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.echevarne.sap.cloud.facturacion.model.salidas.SalidaLiquidacionKey;
import com.echevarne.sap.cloud.facturacion.model.salidas.SalidaLiquidacionRemitentes;

@Repository("salidaLiquidacionRemitentesRep")
public interface SalidaLiquidacionRemitentesRep extends JpaRepository<SalidaLiquidacionRemitentes, SalidaLiquidacionKey> {

}
