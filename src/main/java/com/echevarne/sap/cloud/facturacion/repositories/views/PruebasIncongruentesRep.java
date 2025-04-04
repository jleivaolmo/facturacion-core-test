package com.echevarne.sap.cloud.facturacion.repositories.views;

import java.util.Calendar;
import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Repository;

import com.echevarne.sap.cloud.facturacion.model.views.PruebasIncongruentesView;

import javax.persistence.QueryHint;

@Repository("pruebasIncongruentesRep")
public interface PruebasIncongruentesRep extends JpaRepository<PruebasIncongruentesView, Long> {

	@QueryHints({
		@QueryHint(name = org.hibernate.jpa.QueryHints.HINT_FLUSH_MODE, value = "MANUAL"),
	})
	List<PruebasIncongruentesView> findByCodigoClienteInAndCodigoCompaniaIn(Collection<String> cliente, Collection<String> compania);

	@QueryHints({
		@QueryHint(name = org.hibernate.jpa.QueryHints.HINT_FLUSH_MODE, value = "MANUAL"),
	})
	List<PruebasIncongruentesView> findByCodigoClienteAndCodigoCompaniaAndMaterialProvocanteAndMaterialRechazableAndFechaInicio(String codigoCliente, String codigoCompania,
			String materialProvocante, String materialRechazable, Calendar fechaInicio);

}
