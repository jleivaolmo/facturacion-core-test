package com.echevarne.sap.cloud.facturacion.model.entities;

import javax.persistence.Basic;
import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.echevarne.sap.cloud.facturacion.model.BasicEntity;
import com.echevarne.sap.cloud.facturacion.odata.annotations.JPAExit;
import com.echevarne.sap.cloud.facturacion.odata.annotations.SapEntitySet;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_ONLY, region = "read-only-region" )
@Table(name = "IT_EntityGroupNameText")
// @Table(name = ConstEntities.ENTIDAD_ENTITYFIELDNAMETEXT)
// @SequenceAlias(name = ConstSequences.SECUENCIA_ENTITYFIELDNAMETEXT)
@SapEntitySet(creatable = true, updatable = true, searchable = true)
@JPAExit(allowAll = true, fieldId = "id", fieldDescription = "nombreGrupo")
public class EntityGroupNameText extends BasicEntity{

	/**
	 *
	 */
	private static final long serialVersionUID = -6065140515599137471L;

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
		EntityGroupNameText other = (EntityGroupNameText) obj;
		if (nombreGrupo == null) {
			if (other.nombreGrupo != null)
				return false;
		} else if (!nombreGrupo.equals(other.nombreGrupo))
			return false;
		return true;
	}

}
