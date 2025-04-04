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

import lombok.Getter;
import lombok.Setter;

/**
 * Class for the Entity {@link MasDataTipoPeticion}.
 * @author Hernan Girardi
 * @since 24/03/2020
 */
@Entity
@Table(name = "T_MasDataTipoPeticion" , indexes = {
		@Index(name = "IDX_byTipoPeticion", columnList = "tipoPeticion", unique = true) })
@Getter
@Setter
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
@SapEntitySet(creatable = false, updatable = false, deletable = false)
public class MasDataTipoPeticion extends BasicMasDataEntity {

	private static final long serialVersionUID = -212256118626675336L;

	@Basic
	private String nombreTipoPeticion;

	@Column(nullable=false)
	@NaturalId
	private int tipoPeticion;

	@Override
	public boolean onEquals(Object obj) {
		if (this == obj)
			return true;
		if (getClass() != obj.getClass())
			return false;
		MasDataTipoPeticion other = (MasDataTipoPeticion) obj;
		if (nombreTipoPeticion == null) {
			if (other.nombreTipoPeticion != null)
				return false;
		} else if (!nombreTipoPeticion.equals(other.nombreTipoPeticion))
			return false;
		if (active != other.active)
			return false;
		if (tipoPeticion != other.tipoPeticion)
			return false;
		return true;
	}
}
