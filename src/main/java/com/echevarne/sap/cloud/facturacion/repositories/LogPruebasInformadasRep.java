package com.echevarne.sap.cloud.facturacion.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.echevarne.sap.cloud.facturacion.model.prInfoYTerceros.LogPruebasInformadas;

@Repository("logPruebasInformadasRep")
public interface LogPruebasInformadasRep extends JpaRepository<LogPruebasInformadas, Long> {
}
