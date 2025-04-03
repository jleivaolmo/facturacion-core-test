package com.echevarne.sap.cloud.facturacion.model.masterdata;

import java.util.Objects;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

import com.echevarne.sap.cloud.facturacion.model.BasicMasDataEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.*;

/**
 *
 * @author Hernan Girardi
 * @since 24/08/2020
 */
@Entity
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
@Table(
	name="T_MasDataMotivosEstado",
	indexes= {
		@Index(name = "MasDataMotivosEstado_ByCodigo",  columnList="codigo", unique=true)
})
public class MasDataMotivosEstado extends BasicMasDataEntity {

	private static final long serialVersionUID = 6302018834893893750L;

	@Basic
	@NaturalId
	private String codigo;

	@Basic
	private String descripcion;

	@Basic
	private String mensaje;

	@OneToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name = "fk_Alerta")
	@JsonBackReference
	private MasDataAlerta alerta;

	@Override
	public boolean onEquals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		MasDataMotivosEstado other = (MasDataMotivosEstado) obj;
		return  Objects.equals(this.descripcion, other.descripcion) &&
				Objects.equals(this.mensaje, other.mensaje);

	}
}
