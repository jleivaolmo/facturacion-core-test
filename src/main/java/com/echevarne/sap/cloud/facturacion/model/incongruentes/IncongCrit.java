package com.echevarne.sap.cloud.facturacion.model.incongruentes;

import java.util.Calendar;
import java.util.Set;

import javax.persistence.*;

import com.echevarne.sap.cloud.facturacion.model.BasicEntity;
import com.echevarne.sap.cloud.facturacion.model.ValidityBasicEntity;
import com.echevarne.sap.cloud.facturacion.odata.annotations.Sap;
import com.echevarne.sap.cloud.facturacion.odata.annotations.SapEntitySet;
import com.echevarne.sap.cloud.facturacion.odata.annotations.enums.DisplayFormatEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor()
@EqualsAndHashCode(callSuper = false)
@Table(name = "T_Incong_Crit")
@SapEntitySet(creatable = true, updatable = true, searchable = true, deletable = true)
public class IncongCrit extends BasicEntity {

	private static final long serialVersionUID = -859291282057109261L;

	@Basic
	@Column(name = "validez_desde", nullable = false)
    @Sap(displayFormat = DisplayFormatEnum.Date)
	private Calendar validezDesde = ValidityBasicEntity.getDefaultFechaDesde();

	@Basic
	@Column(name = "validez_hasta", nullable = false)
    @Sap(displayFormat = DisplayFormatEnum.Date)
	private Calendar validezHasta = ValidityBasicEntity.getDefaultFechaHasta();

	@Column(length = 10)
	private String cliente;

	@Column(length = 10)
	private String cia;

	@OneToMany(cascade = CascadeType.ALL, mappedBy="incongCrit")
	@JsonIgnore
	@EqualsAndHashCode.Exclude
	private Set<IncongCond> incongConds;

	@Override
	public boolean onEquals(Object obj) {
		if (this == obj)
			return true;
		if (getClass() != obj.getClass())
			return false;
		IncongCrit other = (IncongCrit) obj;
		if (cia == null) {
			if (other.cia != null)
				return false;
		} else if (!cia.equals(other.cia))
			return false;
		if (cliente == null) {
			if (other.cliente != null)
				return false;
		} else if (!cliente.equals(other.cliente))
			return false;
		return true;
	}
}
