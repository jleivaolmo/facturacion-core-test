package com.echevarne.sap.cloud.facturacion.model.masterdata;

import javax.persistence.*;

import com.echevarne.sap.cloud.facturacion.model.BasicMasDataEntity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * Class for the Entity {@link MasDataEstadoEjecucion}.
 * @author Hernan Girardi
 * @since 24/03/2020
 */
@Entity
@Table(name = "T_MasDataEstadoEjecucion")
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
@AllArgsConstructor
@NoArgsConstructor
public class MasDataEstadoEjecucion extends BasicMasDataEntity {

	private static final long serialVersionUID = -212256118626675336L;

	@Basic
	private String nombreEstado;

	@Column(nullable=false)
	private int codigoEstado;

	public String getNombreEstado() {
		return this.nombreEstado;
	}

	public void setNombreEstado(String nombreEstado) {
		this.nombreEstado = nombreEstado;
	}

	public int getCodigoEstado() {
		return this.codigoEstado;
	}

	public void setCodigoEstado(int codigoEstado) {
		this.codigoEstado = codigoEstado;
	}

	@Override
	public boolean onEquals(Object obj) {
		if (this == obj)
			return true;
		if (getClass() != obj.getClass())
			return false;
		MasDataEstadoEjecucion other = (MasDataEstadoEjecucion) obj;
		if (nombreEstado == null) {
			if (other.nombreEstado != null)
				return false;
		} else if (!nombreEstado.equals(other.nombreEstado))
			return false;
		if (active != other.active)
			return false;
		if (codigoEstado != other.codigoEstado)
			return false;
		return true;
	}
}
