package com.echevarne.sap.cloud.facturacion.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.echevarne.sap.cloud.facturacion.model.tablasZ.ZSDT0046;
import com.echevarne.sap.cloud.facturacion.model.tablasZ.ZSDT70046Key;

@Repository("ZSDT0046Rep")
public interface ZSDT0046Rep extends JpaRepository<ZSDT0046, ZSDT70046Key> {

}
