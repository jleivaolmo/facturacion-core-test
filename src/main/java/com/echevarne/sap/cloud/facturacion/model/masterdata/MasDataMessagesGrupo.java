package com.echevarne.sap.cloud.facturacion.model.masterdata;

import javax.persistence.*;

import com.echevarne.sap.cloud.facturacion.model.BasicMasDataEntity;
import com.echevarne.sap.cloud.facturacion.odata.annotations.SapEntitySet;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NaturalId;

@Getter
@Setter
@Entity
@Table(
    name="T_MasDataMessagesGrupo",
    indexes = {
        @Index(name = "IDX_byCodeGrupo", columnList = "codeGrupo", unique = true),
    }
)
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
@SapEntitySet(creatable = true, updatable = true, searchable = false, deletable = false)
public class MasDataMessagesGrupo extends BasicMasDataEntity {

    private static final long serialVersionUID = 918952433992665806L;

    @Basic
    @Column(length=2)
    @NaturalId
	private String codeGrupo;

    @Basic
	private String nombreGrupo;

    @Override
    public boolean onEquals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        MasDataMessagesGrupo other = (MasDataMessagesGrupo) obj;
        if (codeGrupo == null) {
            if (other.codeGrupo != null)
                return false;
        } else if (!codeGrupo.equals(other.codeGrupo))
            return false;
        if (nombreGrupo == null) {
            if (other.nombreGrupo != null)
                return false;
        } else if (!nombreGrupo.equals(other.nombreGrupo))
            return false;
        return true;
    }

}
