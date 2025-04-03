package com.echevarne.sap.cloud.facturacion.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Class for the Entity {@link AgrResultado}.
 *
 * <p>The persistent class. . .T_AgrResultado</p>
 */
@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "T_AgrResultado")
public class AgrResultado extends BasicEntity implements Serializable {

	private static final long serialVersionUID = -1156760544373612168L;

	@Basic
	private Long idTrazSol;

	@Basic
	private Long idTrazItem;

	@Basic
	private Long idRegla;

	@Override
	public boolean onEquals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		AgrResultado other = (AgrResultado) obj;
		return  Objects.equals(this.idTrazSol, other.idTrazSol) &&
				Objects.equals(this.idTrazItem, other.idTrazItem) &&
				Objects.equals(this.idRegla, other.idRegla);
	}

}
