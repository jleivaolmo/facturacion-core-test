package com.echevarne.sap.cloud.facturacion.model.trazabilidad;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;


import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

import com.echevarne.sap.cloud.facturacion.constants.ConstEntities;
import com.echevarne.sap.cloud.facturacion.model.BasicEntity;
import com.echevarne.sap.cloud.facturacion.model.masterdata.MasDataProcessControlStatus;
import com.echevarne.sap.cloud.facturacion.odata.annotations.SapEntitySet;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Hernan Girardi
 * @since 28/04/2020
 */
@Entity
@Table(name = ConstEntities.ENTIDAD_TRAZABILIDADSOLICITUDPROCESSSTATUS)
@Getter
@Setter
@SapEntitySet(creatable = false, updatable = false, searchable = true, deletable = false)
public class TrazabilidadSolicitudProcessStatus extends BasicEntity {

	private static final long serialVersionUID = 7606471629277557185L;

	/*
	 * Campos
	 *
	 ********************************************/
	@Basic
	private int sequenceOrder;

	@Basic
	private boolean haveErrors;

	/*
	 * Asociaciones
	 *
	 ********************************************/
	@ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name = "fk_TrazabilidadSolicitud", nullable = false)
	@JsonIgnore
	private TrazabilidadSolicitud trazabilidad;

	@ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name = "fk_MasDataProcessControlStatus", nullable = false)
	@JsonIgnore
	private MasDataProcessControlStatus processControlStatus;

	@OneToMany(cascade = CascadeType.ALL, mappedBy="trzSolProcessStatus")
	@JsonIgnore
	private Set<TrazabilidadSolicitudMessages> trzSolMessages = new HashSet<>();

	/*
	 * Entity Methods
	 *
	 ********************************************/

	@Override
	public boolean onEquals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		TrazabilidadSolicitudProcessStatus other = (TrazabilidadSolicitudProcessStatus) obj;
		return  Objects.equals(this.sequenceOrder, other.sequenceOrder)  &&
				Objects.equals(this.haveErrors, other.haveErrors)  &&
				Objects.equals(this.trazabilidad, other.trazabilidad) &&
				Objects.equals(this.processControlStatus, other.processControlStatus);
	}
}
