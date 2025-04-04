package com.echevarne.sap.cloud.facturacion.model.trazabilidad;

import com.echevarne.sap.cloud.facturacion.model.BasicEntity;
import com.echevarne.sap.cloud.facturacion.model.BasicEstadosHistory;
import com.echevarne.sap.cloud.facturacion.model.masterdata.MasDataEstado;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="T_TrazabilidadSolAgrEstHistory",
        indexes= {@Index(name = "TrazabilidadSolAgrEstHistory_ByEstado",  columnList="fk_Estado"), @Index(name = "IDX_byTrazabilidadSolicitudAgr",  columnList="fk_TrazabilidadSolicitudAgrupada")}
)
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class TrazabilidadSolAgrEstHistory extends BasicEstadosHistory {

    private static final long serialVersionUID = -7972570587213555371L;

    public TrazabilidadSolAgrEstHistory(MasDataEstado estado, TrazabilidadSolicitudAgrupada trazabilidad, Date fechaEstado) {
        super(estado, fechaEstado);
        this.trazabilidadSolicitudAgrupada = trazabilidad;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @LazyToOne(LazyToOneOption.NO_PROXY)
    @JoinColumn(name = "fk_TrazabilidadSolicitudAgrupada", nullable = false)
    @JsonIgnore
    TrazabilidadSolicitudAgrupada trazabilidadSolicitudAgrupada;

    public TrazabilidadSolicitudAgrupada getTrazabilidadSolicitudAgrupada() {
        return trazabilidadSolicitudAgrupada;
    }

    public void setTrazabilidadSolicitudAgrupada(TrazabilidadSolicitudAgrupada trazabilidadSolicitudAgrupada) {
        this.trazabilidadSolicitudAgrupada = trazabilidadSolicitudAgrupada;
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
        TrazabilidadSolAgrEstHistory other = (TrazabilidadSolAgrEstHistory) obj;
        return  super.onEquals(obj) &&
                (this.trazabilidadSolicitudAgrupada == null ? other.trazabilidadSolicitudAgrupada == null : this.trazabilidadSolicitudAgrupada.onEquals(other.trazabilidadSolicitudAgrupada));
    }

}
