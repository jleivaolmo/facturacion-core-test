package com.echevarne.sap.cloud.facturacion.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.echevarne.sap.cloud.facturacion.model.texts.MaterialesText;

@Repository("materialesTextRep")
public interface MaterialesTextRep extends JpaRepository<MaterialesText, String> {

}
