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
 * Class for the Entity {@link MasDataTipoPosicion}.
 * @author Hernan Girardi
 * @since 24/03/2020
 */
@Entity
@Table(name = "T_MasDataTipoPosicion", indexes = {
		@Index(name = "IDX_byTipoPosicion", columnList = "tipoPosicion", unique = true) })
@Getter
@Setter
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
@SapEntitySet(creatable = false, updatable = false, deletable = false)
@AllArgsConstructor
@NoArgsConstructor
public class MasDataTipoPosicion extends BasicMasDataEntity {

	private static final long serialVersionUID = 4436448692594259381L;

	@Basic
	private String nombreTipoPosicion;

	@Column(length=5,nullable=false)
	@NaturalId
	private String tipoPosicion;

	@Column
	private boolean excludes;

	@Override
	public boolean onEquals(Object obj) {
		if (this == obj)
			return true;
		if (getClass() != obj.getClass())
			return false;
		MasDataTipoPosicion other = (MasDataTipoPosicion) obj;

		if (nombreTipoPosicion == null) {
			if (other.nombreTipoPosicion != null)
				return false;
		} else if (!nombreTipoPosicion.equals(other.nombreTipoPosicion))
			return false;
		if (tipoPosicion == null) {
			if (other.tipoPosicion != null)
				return false;
		} else if (!tipoPosicion.equals(other.tipoPosicion))
			return false;

		if (active != other.active)
			return false;
		if (excludes != other.excludes)
			return false;

		return true;
	}

}
