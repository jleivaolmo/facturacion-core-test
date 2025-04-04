package com.echevarne.sap.cloud.facturacion.model.facturacion;

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
@Table(name = ConstEntities.ENTIDAD_FACTLOGCONTROLTIPOLOGIA)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(includeFieldNames = false)
@SapEntitySet(creatable = false, updatable = false, searchable = true, deletable = false)
public class ControlPeriodosTipologia extends BasicEntity {

	/**
	 *
	 */
	private static final long serialVersionUID = 7255433845615105493L;

	@Basic
	private String nombreTipologia;

	@Basic
    private int total;

	@ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name = "fk_Control", nullable = false)
	@JsonBackReference
	private ControlPeriodos control;

	@Override
	public boolean onEquals(Object obj) {
		if (this == obj)
			return true;
		if (getClass() != obj.getClass())
			return false;
		ControlPeriodosTipologia other = (ControlPeriodosTipologia) obj;
		if (nombreTipologia == null) {
			if (other.nombreTipologia != null)
				return false;
		} else if (!nombreTipologia.equals(other.nombreTipologia))
			return false;
		return true;
	}

}
