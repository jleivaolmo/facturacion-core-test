package com.echevarne.sap.cloud.facturacion.model.masterdata;

import javax.persistence.Basic;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NaturalId;

import com.echevarne.sap.cloud.facturacion.model.BasicMasDataEntity;
import com.echevarne.sap.cloud.facturacion.odata.annotations.SapEntitySet;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Class for the Entity {@link MasDataTipoLiquidacion}.
 * @author Hernan Girardi
 * @since 24/03/2020
 */
@Entity
@Table(name = "T_MasDataTipoLiquidacion" , indexes = {
		@Index(name = "IDX_byTipoLiquidacion", columnList = "tipoLiquidacion", unique = true) })
@Getter
@Setter
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
@SapEntitySet(creatable = false, updatable = false, deletable = false)
@AllArgsConstructor
@NoArgsConstructor
public class MasDataTipoLiquidacion extends BasicMasDataEntity {

	private static final long serialVersionUID = -212256118626675336L;

	@Basic
	private String nombreTipoLiquidacion;

	@Column(nullable=false)
	@NaturalId
	private int tipoLiquidacion;

	@Override
	public boolean onEquals(Object obj) {
		if (this == obj)
			return true;
		if (getClass() != obj.getClass())
			return false;
		MasDataTipoLiquidacion other = (MasDataTipoLiquidacion) obj;
		if (nombreTipoLiquidacion == null) {
			if (other.nombreTipoLiquidacion != null)
				return false;
		} else if (!nombreTipoLiquidacion.equals(other.nombreTipoLiquidacion))
			return false;
		if (active != other.active)
			return false;
		if (tipoLiquidacion != other.tipoLiquidacion)
			return false;
		return true;
	}
}
