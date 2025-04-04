package com.echevarne.sap.cloud.facturacion.model.reglasfacturacion;

import com.echevarne.sap.cloud.facturacion.model.BasicEntity;
import com.echevarne.sap.cloud.facturacion.odata.annotations.SapEntitySet;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "T_ConfiguracionReglas")
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SapEntitySet(creatable = true, updatable = true, searchable = true)
public class ConfiguracionReglas extends BasicEntity {

    @Basic
    private String nombreSet;

    @Basic
    private boolean porExclusion;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @LazyToOne(LazyToOneOption.NO_PROXY)
    @JoinColumn(name="fk_ReglaFacturacion")
    @JsonBackReference
    private ReglasFacturacion regla;

    @Override
    public boolean onEquals(Object obj) {
        if (this == obj)
            return true;
        if (getClass() != obj.getClass())
            return false;
        ConfiguracionReglas other = (ConfiguracionReglas) obj;
        return Objects.equals(nombreSet, other.getNombreSet())
                && Objects.equals(regla, other.getRegla())
                && Objects.equals(porExclusion, other.isPorExclusion());
    }
}
