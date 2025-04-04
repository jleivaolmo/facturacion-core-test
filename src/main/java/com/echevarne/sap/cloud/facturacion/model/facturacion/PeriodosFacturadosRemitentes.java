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
@Table(name = ConstEntities.ENTIDAD_FACTLOGPERIODOSREMITENTES)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SapEntitySet(creatable = false, updatable = false, searchable = true, deletable = false)
public class PeriodosFacturadosRemitentes extends BasicEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4460375533476404068L;

	@Basic
	private String remitente;

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
		PeriodosFacturadosRemitentes other = (PeriodosFacturadosRemitentes) obj;
		return Objects.equals(remitente, other.remitente);
	}
	
	@Override
	public String toString() {
		return remitente;
	}

}
