package com.echevarne.sap.cloud.facturacion.repositories.views;

import com.echevarne.sap.cloud.facturacion.model.texts.DelegacionProductivaText;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("delegacionProductivaTextRep")
public interface DelegacionProductivaTextRep extends JpaRepository<DelegacionProductivaText, Long> {
}
