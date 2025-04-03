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
 * Class for the Entity {@link MasDataTipoContrato}.
 * @author Germán Laso
 * @since 17/04/2020
 */
@Entity
@Table(name = "T_MasDataTipoContrato", indexes = {
		@Index(name = "IDX_byTipoContrato", columnList = "tipoContrato", unique = true) })
@Getter
@Setter
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
@SapEntitySet(creatable = false, updatable = false, deletable = false)
@AllArgsConstructor
@NoArgsConstructor
public class MasDataTipoContrato extends BasicMasDataEntity {

	/**
	 *
	 */
	private static final long serialVersionUID = 466509298770990266L;

	@Column(length=3)
	@NaturalId
	private String tipoContrato;

	@Basic
	private String nombreTipoContrato;

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean onEquals(Object obj) {
		if (this == obj)
			return true;
		if (getClass() != obj.getClass())
			return false;
		MasDataTipoContrato other = (MasDataTipoContrato) obj;
		if (tipoContrato == null) {
			if (other.tipoContrato != null)
				return false;
		} else if (!tipoContrato.equals(other.tipoContrato))
			return false;
		if (nombreTipoContrato == null) {
			if (other.nombreTipoContrato != null)
				return false;
		} else if (!nombreTipoContrato.equals(other.nombreTipoContrato))
			return false;
		return true;
	}
}
