package com.echevarne.sap.cloud.facturacion.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

import com.echevarne.sap.cloud.facturacion.odata.annotations.SapEntitySet;

/**
 * Class for the Entity {@link PrecioEspecial}.
 * This class should not have null attributes.
 */
@Entity
@Table(name = "T_PrecioEspecial",
        indexes = {@Index(name = "PrecioEspecial_byKey", columnList = "pagador, cliente, concepto", unique = true)}
)
@SapEntitySet(creatable = true, updatable = true, searchable = true, deletable = false)
public class PrecioEspecial extends BasicEntity implements Serializable {

    private static final long serialVersionUID = 7417541209655433532L;

    @Basic
    @Column(name = "pagador", nullable = false)
    private String pagador;

    @Basic
    @Column(name = "cliente", nullable = false)
    private String cliente;

    @Basic
    @Column(name = "concepto", nullable = false)
    private String concepto;

    @Basic
    @Column(name = "valorNeto", nullable = false)
    private BigDecimal valorNeto;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getPagador() {
        return pagador;
    }

    public void setPagador(String pagador) {
        this.pagador = pagador;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public BigDecimal getValorNeto() {
        return valorNeto;
    }

    public void setValorNeto(BigDecimal valorNeto) {
        this.valorNeto = valorNeto;
    }

    @Override
    public boolean onEquals(Object obj) {
        if (this == obj) return true;
        if (getClass() != obj.getClass()) return false;
        PrecioEspecial other = (PrecioEspecial) obj;
        if (!pagador.equals(other.pagador)) return false;
        if (!cliente.equals(other.cliente)) return false;
        if (!concepto.equals(other.concepto)) return false;
        if (!valorNeto.equals(other.valorNeto)) return false;
        return true;
    }

}
