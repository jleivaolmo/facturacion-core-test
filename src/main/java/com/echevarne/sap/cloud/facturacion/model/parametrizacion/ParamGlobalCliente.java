package com.echevarne.sap.cloud.facturacion.model.parametrizacion;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.echevarne.sap.cloud.facturacion.model.masterdata.MasDataEstado;
import com.echevarne.sap.cloud.facturacion.odata.annotations.SapEntitySet;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "T_ParamGlobalCliente")
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = false, includeFieldNames = false)
@SapEntitySet(creatable = true, updatable = true, searchable = true, deletable = true)
@AllArgsConstructor
@NoArgsConstructor
public class ParamGlobalCliente extends BasicParamEntity{


    /**
     *
     */
    private static final long serialVersionUID = -7707407047714369841L;

    @Basic
    @Column(length = 10, nullable = false)
    private String codigoCliente;

    @Basic
    private boolean poolFacturacion;

    @Basic
    private TipologiaCliente tipoCliente;

}
