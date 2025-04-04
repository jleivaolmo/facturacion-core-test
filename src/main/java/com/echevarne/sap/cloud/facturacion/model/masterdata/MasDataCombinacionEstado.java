package com.echevarne.sap.cloud.facturacion.model.masterdata;

import javax.persistence.*;

import com.echevarne.sap.cloud.facturacion.constants.ConstEntities;
import com.echevarne.sap.cloud.facturacion.model.BasicMasDataEntity;
import com.echevarne.sap.cloud.facturacion.odata.annotations.SapEntitySet;
import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

/**
 *
 * @author Germán Laso
 * @since 15/02/2020
 */
@Getter
@Setter
@Entity
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
@Table(name=ConstEntities.ENTIDAD_MASDATACOMBINACIONESTADO)
@SapEntitySet(creatable = true, updatable = true, searchable = false, deletable = false)
public class MasDataCombinacionEstado extends BasicMasDataEntity {

    /**
     *
     */
    private static final long serialVersionUID = 5808281489153549928L;

    @ManyToOne(fetch = FetchType.LAZY)
    @LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name = "FK_Estado", nullable = false)
	@JsonBackReference
    private MasDataEstado estado;

    @ManyToOne(fetch = FetchType.LAZY)
    @LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name = "FK_CombinableEstado", nullable = false)
    private MasDataEstado combinable;

    @Override
    public boolean onEquals(Object obj) {
        if (this == obj)
            return true;
        if (getClass() != obj.getClass())
            return false;
        MasDataCombinacionEstado other = (MasDataCombinacionEstado) obj;
        if (estado == null) {
            if (other.estado != null)
                return false;
        } else if (!estado.equals(other.estado))
            return false;
        return true;
    }

}
