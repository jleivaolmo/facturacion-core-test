package com.echevarne.sap.cloud.facturacion.model.incongruentes;

import javax.persistence.*;

import com.echevarne.sap.cloud.facturacion.model.BasicEntity;
import com.echevarne.sap.cloud.facturacion.odata.annotations.SapEntitySet;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

@Entity
@Data
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor()
@EqualsAndHashCode(callSuper = false)
@Table(
	name = "T_Incong_Cond",
	indexes = {
		@Index(name = "IDX_byMatIntroducido", columnList = "matIntroducido", unique = false),
	}
)
@SapEntitySet(creatable = true, updatable = true, searchable = true, deletable = true)
public class IncongCond extends BasicEntity {

	private static final long serialVersionUID = -3067344718611968209L;

	@Column(length = 18)
	private String matIntroducido;

	@ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name = "fk_IncongMat", nullable = false)
	@JsonIgnore
	@EqualsAndHashCode.Exclude
	private IncongMat incongMat;

	@ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name = "fk_IncongCrit", nullable = false)
	@JsonIgnore
	@EqualsAndHashCode.Exclude
	private IncongCrit incongCrit;

	@Override
	public boolean onEquals(Object obj) {
		if (this == obj)
			return true;
		if (getClass() != obj.getClass())
			return false;
		IncongCond other = (IncongCond) obj;
		if (matIntroducido == null) {
			if (other.matIntroducido != null)
				return false;
		} else if (!matIntroducido.equals(other.matIntroducido))
			return false;
		return true;
	}
}
