package com.echevarne.sap.cloud.facturacion.repositories;

import java.util.Optional;

import com.echevarne.sap.cloud.facturacion.model.divisores.GruposSectores;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("gruposSectoresRepo")
public interface GruposSectoresRepository extends JpaRepository<GruposSectores, Long> {

	Optional<GruposSectores> findBySalesOrganizationAndSector(String salesOrganization, String sector);

}
