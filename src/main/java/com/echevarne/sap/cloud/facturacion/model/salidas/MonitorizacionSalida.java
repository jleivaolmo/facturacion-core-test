package com.echevarne.sap.cloud.facturacion.model.salidas;

import com.echevarne.sap.cloud.facturacion.model.BasicEntity;
import com.echevarne.sap.cloud.facturacion.odata.annotations.Sap;
import com.echevarne.sap.cloud.facturacion.odata.annotations.SapEntitySet;
import com.echevarne.sap.cloud.facturacion.odata.annotations.enums.DisplayFormatEnum;
import com.echevarne.sap.cloud.facturacion.odata.annotations.enums.FilterRestrictionsEnum;
import com.echevarne.sap.cloud.facturacion.odata.annotations.enums.PropertyValueListEnum;
import com.echevarne.sap.cloud.facturacion.odata.annotations.enums.ValueListEntitiesEnum;
import com.echevarne.sap.cloud.facturacion.odata.annotations.sap.common.ValueList;
import com.echevarne.sap.cloud.facturacion.odata.annotations.sap.common.ValueListParameter;
import com.echevarne.sap.cloud.facturacion.odata.annotations.sap.common.ValueListParameterDisplayOnly;
import com.echevarne.sap.cloud.facturacion.odata.annotations.sap.common.ValueListParameterInOut;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

import javax.persistence.*;
import java.util.Calendar;

@Entity
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "T_MONITORIZACIONSALIDA")
@Cacheable(false)
@SapEntitySet(creatable = true, updatable = false, searchable = true)
public class MonitorizacionSalida extends BasicEntity {

    private static final long serialVersionUID = 8412179138902345201L;

    public enum Estado {
        SUCCESS,
        FAILED
    }

    @Basic
    private String uuidProceso;

    @Basic
    private String factura;

    @Basic
    @ColumnDefault("false")
    private boolean prefactura;

    @Basic
    @ColumnDefault("false")
    private boolean rectificativa;

    @Basic
    @Enumerated(EnumType.STRING)
    private Estado estado;

    @Basic
    private String message;

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

    @ColumnDefault("true")
    private boolean procesoAutomatico;

    @CreationTimestamp
    @Sap(displayFormat = DisplayFormatEnum.Date)
    protected Calendar fechaProceso = Calendar.getInstance();

    @Override
    public boolean onEquals(Object o) {
        return this.equals(o);
    }
}
