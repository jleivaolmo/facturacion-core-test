package com.echevarne.sap.cloud.facturacion.model.incongruentes;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.echevarne.sap.cloud.facturacion.model.BasicEntity;
import com.echevarne.sap.cloud.facturacion.odata.annotations.SapEntitySet;

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
@Table(name = "T_Incong_Sec")
@SapEntitySet(creatable = true, updatable = true, searchable = true, deletable = true)
public class IncongSec extends BasicEntity {

	private static final long serialVersionUID = -4173672163831310333L;

	@Basic
	private boolean cliente;

	@Basic
	private boolean cia;

	@Column(precision = 2)
    private int prioridad;

	@Override
	public boolean onEquals(Object obj) {
		if (this == obj)
			return true;
		if (getClass() != obj.getClass())
			return false;
		IncongSec other = (IncongSec) obj;
		if (cia != other.cia)
			return false;
		if (cliente != other.cliente)
			return false;
		if (prioridad != other.prioridad)
			return false;
		return true;
	}

}
