package com.echevarne.sap.cloud.facturacion.model.masterdata;

import javax.persistence.*;

import com.echevarne.sap.cloud.facturacion.model.BasicMasDataEntity;
import com.echevarne.sap.cloud.facturacion.odata.annotations.SapEntitySet;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NaturalId;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
@Table(
	name="T_MasDataMotivosCancelacion",
	indexes = {
		@Index(name = "MasDataMotivosCancelacion_byCodigo", columnList = "codigo", unique = true),
	}
)
@SapEntitySet(creatable = true, updatable = true, searchable = false, deletable = false)
public class MasDataMotivosCancelacion extends BasicMasDataEntity {

	/**
	 *
	 */
	private static final long serialVersionUID = 1962057720246907060L;

	@Basic
	@NaturalId
	private String codigo;

	@Basic
	private String descripcion;

	@Override
	public boolean onEquals(Object obj) {
		if (this == obj)
			return true;
		if (getClass() != obj.getClass())
			return false;
		MasDataMotivosCancelacion other = (MasDataMotivosCancelacion) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		if (descripcion == null) {
			if (other.descripcion != null)
				return false;
		} else if (!descripcion.equals(other.descripcion))
			return false;
		return true;
	}
}
