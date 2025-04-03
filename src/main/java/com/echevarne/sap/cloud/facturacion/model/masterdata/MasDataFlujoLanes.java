package com.echevarne.sap.cloud.facturacion.model.masterdata;

import javax.persistence.Basic;
import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.echevarne.sap.cloud.facturacion.model.BasicMasDataEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
@Getter
@Setter
@Table(name = "T_MasDataFlujoLanes")
public class MasDataFlujoLanes extends BasicMasDataEntity {

    private static final long serialVersionUID = -7677476724833822106L;

    @Basic
    private int codeLane;

	@Basic
	private String descriptionLane;

    @Basic
    private String application;

    @Override
    public boolean onEquals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        MasDataFlujoLanes other = (MasDataFlujoLanes) obj;
        if (descriptionLane == null) {
            if (other.descriptionLane != null)
                return false;
        } else if (!descriptionLane.equals(other.descriptionLane))
            return false;
        if (application == null) {
            if (other.application != null)
                return false;
        } else if (!application.equals(other.application))
            return false;
        if (codeLane != other.codeLane)
			return false;
        return true;
    }

}
