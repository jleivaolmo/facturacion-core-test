package com.echevarne.sap.cloud.facturacion.model.masterdata;

import javax.persistence.Basic;
import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

import com.echevarne.sap.cloud.facturacion.model.BasicMasDataEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author Hernan Girardi
 * @since 16/06/2020
 */
@Setter
@Getter
@Entity
@Table(name = "T_MasDataTransicionEstado",
		uniqueConstraints = { @UniqueConstraint(columnNames = {"fk_EstadoOrigen","fk_EstadoDestino"}) })
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
@AllArgsConstructor
@NoArgsConstructor
public class MasDataTransicionEstado extends BasicMasDataEntity {

	private static final long serialVersionUID = -1053512514149313632L;

	@ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name = "fk_EstadoOrigen", nullable = false)
	@JsonBackReference
	MasDataEstado origen;

	@ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name = "fk_EstadoDestino", nullable = false)
	@JsonBackReference
	MasDataEstado destino;

	@Basic
	boolean automatico;

	public MasDataTransicionEstado(MasDataEstado origen, MasDataEstado destino) {
		this.origen = origen;
		this.destino = destino;
	}

	@Override
	public boolean onEquals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		MasDataTransicionEstado other = (MasDataTransicionEstado) obj;
		return 	this.origen.onEquals(other.origen) &&
				this.destino.onEquals(other.destino) &&
				this.automatico == (other.automatico);
	}
}
