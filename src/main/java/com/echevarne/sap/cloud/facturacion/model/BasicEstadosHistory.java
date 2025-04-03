package com.echevarne.sap.cloud.facturacion.model;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

import com.echevarne.sap.cloud.facturacion.gestionestados.MutableHistory;
import com.echevarne.sap.cloud.facturacion.model.masterdata.MasDataEstado;
import com.echevarne.sap.cloud.facturacion.model.masterdata.MasDataMotivosEstado;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Abstract super class for all the Messages Entities.
 *
 * @author Germán Laso
 * @since 17/10/2020
 */
@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class BasicEstadosHistory extends BasicEntity implements MutableHistory {

    /**
     *
     */
    private static final long serialVersionUID = 2894082845785089945L;

    public BasicEstadosHistory(MasDataEstado estado, Date fechaEstado) {
		this.estado = estado;
		this.fechaEstado = fechaEstado;
		this.motivo = null;
		this.automatico = true;
	}

    @ManyToOne(fetch = FetchType.LAZY)
    @LazyToOne(LazyToOneOption.NO_PROXY)
    @JoinColumn(name = "fk_Estado", nullable = false)
    private MasDataEstado estado;

    @ManyToOne(fetch = FetchType.LAZY)
    @LazyToOne(LazyToOneOption.NO_PROXY)
    @JoinColumn(name = "fk_Motivo", nullable = true)
    @JsonIgnore
    private MasDataMotivosEstado motivo;

    @Basic
    private Date fechaEstado;

    @Basic
    private boolean automatico;

    @Basic
    private int sequenceOrder;

    @Basic
    private String motivoVar1;

    @Basic
    private String motivoVar2;

    @Basic
    private String motivoVar3;

    @Basic
    private String motivoVar4;

    @Override
    public boolean onEquals(Object obj) {
        if (this == obj)
            return true;
        if (getClass() != obj.getClass())
            return false;
        BasicEstadosHistory other = (BasicEstadosHistory) obj;
        if (automatico != other.automatico)
            return false;
        if (estado == null) {
            if (other.estado != null)
                return false;
        } else if (!estado.equals(other.estado))
            return false;
        if (fechaEstado == null) {
            if (other.fechaEstado != null)
                return false;
        } else if (!fechaEstado.equals(other.fechaEstado))
            return false;
        if (motivo == null) {
            if (other.motivo != null)
                return false;
        } else if (!motivo.equals(other.motivo))
            return false;
        if (motivoVar1 == null) {
            if (other.motivoVar1 != null)
                return false;
        } else if (!motivoVar1.equals(other.motivoVar1))
            return false;
        if (motivoVar2 == null) {
            if (other.motivoVar2 != null)
                return false;
        } else if (!motivoVar2.equals(other.motivoVar2))
            return false;
        if (motivoVar3 == null) {
            if (other.motivoVar3 != null)
                return false;
        } else if (!motivoVar3.equals(other.motivoVar3))
            return false;
        if (motivoVar4 == null) {
            if (other.motivoVar4 != null)
                return false;
        } else if (!motivoVar4.equals(other.motivoVar4))
            return false;
        if (sequenceOrder != other.sequenceOrder)
            return false;
        return true;
    }

}
