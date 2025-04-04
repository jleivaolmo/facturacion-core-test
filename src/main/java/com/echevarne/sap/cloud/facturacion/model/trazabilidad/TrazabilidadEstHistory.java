package com.echevarne.sap.cloud.facturacion.model.trazabilidad;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
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
@Table(name="T_TrazabilidadEstHistory",
	indexes= {
		@Index(name = "IX_TrazabilidadEstHistory_fk_Estado",  columnList="fk_Estado", unique = false),
		@Index(name = "IX_TrazabilidadEstHistory_fk_Trazabilidad",  columnList="fk_Trazabilidad", unique = false),
		@Index(name = "UX_TrazabilidadEstHistory_fk_trazabilidad_sequenceOrder",  columnList="fk_Trazabilidad,sequenceOrder", unique = false)
	}
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
//@EntityListeners(TrazaHistEntityListener.class)
public class TrazabilidadEstHistory extends BasicEstadosHistory {

	private static final long serialVersionUID = -6021037029782345558L;

	@ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name = "fk_Trazabilidad", nullable = false)
	@JsonIgnore
	Trazabilidad trazabilidad;

	public TrazabilidadEstHistory(MasDataEstado estado, Trazabilidad trazabilidad,  Date fechaEstado) {
		super(estado, fechaEstado);
		this.trazabilidad = trazabilidad;
	}

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


