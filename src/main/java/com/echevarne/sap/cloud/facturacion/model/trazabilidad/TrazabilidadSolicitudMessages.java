package com.echevarne.sap.cloud.facturacion.model.trazabilidad;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.echevarne.sap.cloud.facturacion.constants.ConstEntities;
import com.echevarne.sap.cloud.facturacion.model.BasicMessagesEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;

import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

@Entity
@Table(name = ConstEntities.ENTIDAD_TRAZABILIDADSOLICITUDMESSAGES)
public class TrazabilidadSolicitudMessages extends BasicMessagesEntity {

	private static final long serialVersionUID = 166475788904147809L;

	@ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name = "fk_TrzSolProcessStatus", nullable = false)
	@JsonIgnore
	private TrazabilidadSolicitudProcessStatus trzSolProcessStatus;

	public TrazabilidadSolicitudProcessStatus getTrzSolProcessStatus() {
		return trzSolProcessStatus;
	}

	public void setTrzSolProcessStatus(TrazabilidadSolicitudProcessStatus trzSolProcessStatus) {
		this.trzSolProcessStatus = trzSolProcessStatus;
	}

	@Override
	public boolean onEquals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		TrazabilidadSolicitudMessages other = (TrazabilidadSolicitudMessages) obj;
		return super.onEquals(obj)
			&& Objects.equals(this.trzSolProcessStatus, other.trzSolProcessStatus);
	}

}
