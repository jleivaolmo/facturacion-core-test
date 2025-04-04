package com.echevarne.sap.cloud.facturacion.model.trazabilidad;

import java.util.Date;

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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="T_TrazabilidadAlbEstHistory",
	indexes= {@Index(name = "TrazabilidadAlbEstHistory_ByEstado",  columnList="fk_Estado"), @Index(name = "IDX_byTrazabilidadAlbaran",  columnList="fk_Trazabilidad")}
)
public class TrazabilidadAlbaranEstHistory extends BasicEstadosHistory {

    /**
     *
     */
    private static final long serialVersionUID = -5978658966575246883L;

	/**
	 *
	 * @param estado
	 * @param trazabilidad
	 * @param fechaEstado
	 */
	public TrazabilidadAlbaranEstHistory(MasDataEstado estado, TrazabilidadAlbaran trazabilidad, Date fechaEstado) {
		super(estado, fechaEstado);
		this.trazabilidad = trazabilidad;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name = "fk_Trazabilidad", nullable = false)
	@JsonIgnore
	TrazabilidadAlbaran trazabilidad;

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
