package com.echevarne.sap.cloud.facturacion.model.liquidaciones;

import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;
import com.echevarne.sap.cloud.facturacion.model.BasicEntity;
import com.echevarne.sap.cloud.facturacion.odata.annotations.SapEntitySet;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "T_AgrupacionLiquidacionEmailDestinatario")
@SapEntitySet(creatable = true, updatable = true, searchable = false, deletable = true)
public class AgrupacionLiquidacionEmailDestinatario extends BasicEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7295697606925254045L;

	@Basic
	private String emailDestinatario;

	/*
	 * Asociaciones
	 *
	 ********************************************/
	@ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name = "fk_agrupacionliquidacion", nullable = false)
	@JsonBackReference
	@ToString.Exclude
	private AgrupacionLiquidacion agrupacionLiquidacion;

	@Override
	public boolean onEquals(Object o) {
		if (this == o)
			return true;
		if (getClass() != o.getClass())
			return false;
		AgrupacionLiquidacionEmailDestinatario other = (AgrupacionLiquidacionEmailDestinatario) o;
		return Objects.equals(emailDestinatario, other.emailDestinatario);
	}

}
