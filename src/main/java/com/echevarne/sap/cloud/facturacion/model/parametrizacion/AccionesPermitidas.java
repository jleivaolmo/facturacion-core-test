package com.echevarne.sap.cloud.facturacion.model.parametrizacion;

import javax.persistence.*;

import com.echevarne.sap.cloud.facturacion.model.masterdata.MasDataEstado;
import com.echevarne.sap.cloud.facturacion.odata.annotations.SapEntitySet;
import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

@Entity
@Table(
        name = "T_AccionesPermitidas",
        indexes = {
                @Index(name = "IDX_byAccionEstado", columnList = "accion,fk_estado", unique = false),
        }
)
@Builder(toBuilder = true)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@ToString(callSuper = false, includeFieldNames = false)
@SapEntitySet(creatable = true, updatable = true, searchable = false, deletable = true)
public class AccionesPermitidas extends BasicParamEntity {

    /**
     *
     */
    private static final long serialVersionUID = 6156371505579978330L;

    @Basic
    @Enumerated(EnumType.STRING)
    private AccionesUsuario accion;

    @ManyToOne(fetch = FetchType.LAZY)
    @LazyToOne(LazyToOneOption.NO_PROXY)
    @JsonBackReference
    @JoinColumn(name = "fk_estado", nullable = false)
    private MasDataEstado estado;

    @Column(length=1)
    private String nivel;

    @Basic
    private boolean permitido;
}
