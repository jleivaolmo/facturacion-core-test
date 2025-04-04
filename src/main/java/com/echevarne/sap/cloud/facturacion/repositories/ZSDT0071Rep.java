package com.echevarne.sap.cloud.facturacion.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.echevarne.sap.cloud.facturacion.model.tablasZ.ZSDT0071;
import com.echevarne.sap.cloud.facturacion.model.tablasZ.ZSDT70071Key;

@Repository("ZSDT0071Rep")
public interface ZSDT0071Rep extends JpaRepository<ZSDT0071, ZSDT70071Key> {

}
