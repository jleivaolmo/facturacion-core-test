package com.echevarne.sap.cloud.facturacion.model.salidas;

import com.echevarne.sap.cloud.facturacion.model.BasicEntity;
import com.echevarne.sap.cloud.facturacion.odata.annotations.Sap;
import com.echevarne.sap.cloud.facturacion.odata.annotations.SapEntitySet;
import com.echevarne.sap.cloud.facturacion.odata.annotations.enums.FilterRestrictionsEnum;
import com.echevarne.sap.cloud.facturacion.odata.annotations.enums.PropertyValueListEnum;
import com.echevarne.sap.cloud.facturacion.odata.annotations.enums.ValueListEntitiesEnum;
import com.echevarne.sap.cloud.facturacion.odata.annotations.sap.common.ValueList;
import com.echevarne.sap.cloud.facturacion.odata.annotations.sap.common.ValueListParameter;
import com.echevarne.sap.cloud.facturacion.odata.annotations.sap.common.ValueListParameterDisplayOnly;
import com.echevarne.sap.cloud.facturacion.odata.annotations.sap.common.ValueListParameterInOut;
import lombok.*;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "T_ASIGNACIONTIPOLOGIAENVIO", uniqueConstraints = {@UniqueConstraint(columnNames = {"tipoReferencia", "idReferencia"})})
@Cacheable(false)
@SapEntitySet(creatable = true, updatable = true, searchable = false)
public class AsignacionTipologiaEnvio extends BasicEntity {

    public enum TipoReferencia {
        CONTRATO,
        DETERMINACIONFACTURA,
        REGLAFACTURACION
    }

    @Basic
    @Enumerated(EnumType.STRING)
    private TipoReferencia tipoReferencia;

    @Basic
    private Long idReferencia;

    @Basic
    @ValueList(
            CollectionPath = ValueListEntitiesEnum.S4Customers,
            Label = "TipologiaEnvio",
            Parameters = {
                    @ValueListParameter(
                            ValueListParameterInOut = {
                                    @ValueListParameterInOut(ValueListProperty = "tipologiaEnvio", LocalDataProperty = "tipologiaEnvio")
                            },
                            ValueListParameterDisplayOnly = {
                                    @ValueListParameterDisplayOnly(ValueListProperty = "descripcion")
                            }
                    )
            }
    )
    @Sap(filterRestriction = FilterRestrictionsEnum.Multiple, valueList = PropertyValueListEnum.Standard, text = "tipologiaEnvioTxt/id")
    private Long tipologiaEnvio;

    @ManyToOne(fetch = FetchType.LAZY)
    @LazyToOne(LazyToOneOption.NO_PROXY)
    @JoinColumn(
            name = "tipologiaEnvio",
            insertable = false,
            updatable = false,
            foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT)
    )
    private TipologiaEnvio tipologiaEnvioTxt;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        AsignacionTipologiaEnvio that = (AsignacionTipologiaEnvio) o;
        return getTipoReferencia() == that.getTipoReferencia() && Objects.equals(getIdReferencia(), that.getIdReferencia());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getTipoReferencia(), getIdReferencia());
    }

    @Override
    public boolean onEquals(Object o) {
        return equals(o);
    }
}
