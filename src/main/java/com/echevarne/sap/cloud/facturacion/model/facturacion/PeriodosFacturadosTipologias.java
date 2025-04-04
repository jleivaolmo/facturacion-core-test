package com.echevarne.sap.cloud.facturacion.model.facturacion;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;
import com.echevarne.sap.cloud.facturacion.constants.ConstEntities;
import com.echevarne.sap.cloud.facturacion.model.BasicEntity;
import com.echevarne.sap.cloud.facturacion.odata.annotations.SapEntitySet;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = ConstEntities.ENTIDAD_FACTLOGPERIODOSTIPOLOGIAS)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(callSuper = false, includeFieldNames = false)
@SapEntitySet(creatable = false, updatable = false, searchable = true, deletable = false)
public class PeriodosFacturadosTipologias extends BasicEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3025197747829732182L;

	@Basic
	private Integer tipologia;

	/*
	 * Asociaciones
	 *
	 ********************************************/
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "periodoTipologia")
	@JsonManagedReference
	@ToString.Exclude
	private Set<PeriodosFacturadosTipologiasClaves> claves = new HashSet<>();
	
	@ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name = "fk_PeriodoFacturado", nullable = false)
	@JsonBackReference
	@ToString.Exclude
	private PeriodosFacturados periodo;
	
	/*
	 * EntityMethods
	 *
	 ********************************************/
	public void addClaves(PeriodosFacturadosTipologiasClaves clave) {
		clave.setPeriodoTipologia(this);
		this.claves.add(clave);
	}

	@Override
	public boolean onEquals(Object obj) {
		if (this == obj)
			return true;
		if (getClass() != obj.getClass())
			return false;
		PeriodosFacturadosTipologias other = (PeriodosFacturadosTipologias) obj;
		return Objects.equals(tipologia, other.tipologia);
	}

}
