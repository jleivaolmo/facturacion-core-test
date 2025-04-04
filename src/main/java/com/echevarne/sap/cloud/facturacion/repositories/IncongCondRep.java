package com.echevarne.sap.cloud.facturacion.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.echevarne.sap.cloud.facturacion.model.incongruentes.IncongCond;

@Repository("incongCondRep")
public interface IncongCondRep extends JpaRepository<IncongCond, Long> {
	
	List<IncongCond> findByMatIntroducido(String matIntroducido);

}
