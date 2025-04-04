package com.echevarne.sap.cloud.facturacion.model;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import com.echevarne.sap.cloud.facturacion.odata.annotations.Sap;

/**
 * Abstract super class for all the Master Data Entities.
 *
 * @author Hernan Girardi
 * @since 23/04/2020
 */
@MappedSuperclass
public abstract class BasicMasDataEntity extends BasicEntity {

	private static final long serialVersionUID = -1743006636222517818L;

	@Column(name = "active", nullable = false)
	@Sap(filterable=true)
	protected boolean active;

	public boolean isActive() {
		return this.active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public boolean getActive() {
		return this.active;
	}


	@Override
	public boolean onEquals(Object o) {
		return this.equals(o);
	}

}
