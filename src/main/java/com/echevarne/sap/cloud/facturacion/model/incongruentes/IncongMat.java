package com.echevarne.sap.cloud.facturacion.model.incongruentes;

import java.util.Set;

import javax.persistence.*;

import com.echevarne.sap.cloud.facturacion.model.BasicEntity;
import com.echevarne.sap.cloud.facturacion.odata.annotations.SapEntitySet;
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
@Table(
		name = "T_Incong_Mat",
		indexes = {
			@Index(name = "IDX_byMatRechazado", columnList = "matRechazado", unique = false),
		}
)
@SapEntitySet(creatable = true, updatable = true, searchable = true, deletable = true)
public class IncongMat extends BasicEntity {

	private static final long serialVersionUID = -4638938621342829974L;

	@Column(length = 18)
	private String matRechazado;

	@OneToMany(cascade = CascadeType.ALL, mappedBy="incongMat")
	@JsonIgnore
	@EqualsAndHashCode.Exclude
	private Set<IncongCond> incongConds;

	@Override
	public boolean onEquals(Object obj) {
		if (this == obj)
			return true;
		if (getClass() != obj.getClass())
			return false;
		IncongMat other = (IncongMat) obj;
		if (matRechazado == null) {
			if (other.matRechazado != null)
				return false;
		} else if (!matRechazado.equals(other.matRechazado))
			return false;
		return true;
	}
}
