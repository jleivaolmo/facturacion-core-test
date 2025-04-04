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
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
@Setter
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_ONLY, region = "read-only-region" )
@Table(name = "IT_EntityStatusNameText")
@SapEntitySet(creatable = false, updatable = false, searchable = true)
@JPAExit(allowAll = true, fieldId = "id", fieldDescription = "nombreStatus")
public class EntityStatusNameText extends BasicEntity{

	/**
	 *
	 */
	private static final long serialVersionUID = -6065140515599137471L;

	@Basic
	private String nombreStatus;

	@Override
	public boolean onEquals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		EntityStatusNameText other = (EntityStatusNameText) obj;
		if (nombreStatus == null) {
			if (other.nombreStatus != null)
				return false;
		} else if (!nombreStatus.equals(other.nombreStatus))
			return false;
		return true;
	}

}
