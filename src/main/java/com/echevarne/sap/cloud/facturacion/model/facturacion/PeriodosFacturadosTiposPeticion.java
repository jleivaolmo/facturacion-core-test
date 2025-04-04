package com.echevarne.sap.cloud.facturacion.model.facturacion;

import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;
import com.echevarne.sap.cloud.facturacion.constants.ConstEntities;
import com.echevarne.sap.cloud.facturacion.model.BasicEntity;
import com.echevarne.sap.cloud.facturacion.odata.annotations.SapEntitySet;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = ConstEntities.ENTIDAD_FACTLOGPERIODOSTIPOSPETICION)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SapEntitySet(creatable = false, updatable = false, searchable = true, deletable = false)
public class PeriodosFacturadosTiposPeticion extends BasicEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3699916997016262265L;

	@Basic
	private Integer tipoPeticion;

	/*
	 * Asociaciones
	 *
	 ********************************************/
	@ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name = "fk_PeriodoFacturado", nullable = false)
	@JsonBackReference
	@ToString.Exclude
	private PeriodosFacturados periodo;

	@Override
	public boolean onEquals(Object obj) {
		if (this == obj)
			return true;
		if (getClass() != obj.getClass())
			return false;
		PeriodosFacturadosTiposPeticion other = (PeriodosFacturadosTiposPeticion) obj;
		return Objects.equals(tipoPeticion, other.tipoPeticion);
	}
	
	@Override
	public String toString() {
		return String.valueOf(tipoPeticion);
	}

}
