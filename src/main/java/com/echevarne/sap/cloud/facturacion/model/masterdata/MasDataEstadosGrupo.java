package com.echevarne.sap.cloud.facturacion.model.masterdata;

import java.util.Objects;
import java.util.Set;

import javax.persistence.*;

import com.echevarne.sap.cloud.facturacion.model.BasicMasDataEntity;
import com.echevarne.sap.cloud.facturacion.odata.annotations.SapEntitySet;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NaturalId;

@Getter
@Setter
@Entity
@Table(name="T_MasDataEstadosGrupo",
        indexes=@Index(name = "MasDataEstadosGrupo_ByCodigo",  columnList="codigo", unique=true))
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
@SapEntitySet(creatable = true, updatable = true, searchable = false, deletable = false)
@AllArgsConstructor
@NoArgsConstructor
public class MasDataEstadosGrupo extends BasicMasDataEntity {

    /**
	 *
	 */
	private static final long serialVersionUID = 197301031111583589L;

	@Basic
    private String nombre;

    @Basic
    @NaturalId
    private String codigo;

    @Basic
    private int critically;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "estadosGrupo")
    @JsonManagedReference
    private Set<MasDataEstado> estados;

    @Override
    public boolean onEquals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        MasDataEstadosGrupo other = (MasDataEstadosGrupo) obj;
        return  Objects.equals(this.nombre, other.nombre) &&
                Objects.equals(this.codigo, other.codigo);
    }
}
