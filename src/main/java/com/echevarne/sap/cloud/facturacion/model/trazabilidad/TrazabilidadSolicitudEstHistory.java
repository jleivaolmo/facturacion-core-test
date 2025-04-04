package com.echevarne.sap.cloud.facturacion.model.trazabilidad;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.echevarne.sap.cloud.facturacion.model.BasicEntity;
import com.echevarne.sap.cloud.facturacion.model.BasicEstadosHistory;
import com.echevarne.sap.cloud.facturacion.model.masterdata.MasDataEstado;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

@Entity
@Table(name="T_TrazabilidadSolEstHistory",
	indexes= {@Index(name = "TrazabilidadSolEstHistory_ByEstado",  columnList="fk_Estado"), @Index(name = "IDX_byTrazabilidadSolicitud",  columnList="fk_Trazabilidad")}
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class TrazabilidadSolicitudEstHistory extends BasicEstadosHistory {

	private static final long serialVersionUID = -7972570587213555371L;

	public TrazabilidadSolicitudEstHistory(MasDataEstado estado, TrazabilidadSolicitud trazabilidad, Date fechaEstado) {
		super(estado, fechaEstado);
		this.trazabilidad = trazabilidad;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name = "fk_Trazabilidad", nullable = false)
	@JsonIgnore
	TrazabilidadSolicitud trazabilidad;
	
	@Basic
	Boolean afectaImporte;

	@Override
	public MasDataEstado obtenerEstado() {
		return this.getEstado();
	}

	@Override
	public void inactiveEstado() {
		this.setInactive(true);
	}

	@Override
	public boolean isActive() {
		return !this.getInactive();
	}

	@Override
	public BasicEntity getEntity() {
		return this;
	}

	@Override
	public void setMotivosVars(String... args) {
		if(args==null) return;
		if(args.length > 0)
		this.setMotivoVar1(args[0]);
		if(args.length > 1)
		this.setMotivoVar2(args[1]);
		if(args.length > 2)
		this.setMotivoVar3(args[2]);
		if(args.length > 3)
		this.setMotivoVar4(args[3]);
	}

	@Override
	public boolean onEquals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		TrazabilidadEstHistory other = (TrazabilidadEstHistory) obj;
		return  super.onEquals(obj) &&
				(this.trazabilidad == null ? other.trazabilidad == null : this.trazabilidad.onEquals(other.trazabilidad));
	}

}
