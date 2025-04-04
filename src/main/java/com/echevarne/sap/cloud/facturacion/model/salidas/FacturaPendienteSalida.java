package com.echevarne.sap.cloud.facturacion.model.salidas;

import com.echevarne.sap.cloud.facturacion.odata.annotations.SapEntitySet;
import lombok.*;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Entity
@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter(AccessLevel.NONE)
@EqualsAndHashCode(callSuper = false)
@Table(name = "V_FACTURAPENDIENTESALIDA")
@SapEntitySet(creatable = false, updatable = false, searchable = true, deletable = false)
public class FacturaPendienteSalida implements Serializable {

    private static final long serialVersionUID = -1740688037168753783L;

    @Id
    @Basic
    private String billingDocument;

    @Basic
    private Date fechaFactura;

}
