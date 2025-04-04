package com.echevarne.sap.cloud.facturacion.model.masterdata;

import javax.persistence.*;

import com.echevarne.sap.cloud.facturacion.model.BasicMasDataEntity;
import com.echevarne.sap.cloud.facturacion.odata.annotations.Sap;
import com.echevarne.sap.cloud.facturacion.odata.annotations.enums.PropertyValueListEnum;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.NaturalId;

/**
 * @Entity {@link MasDataConditionPrice}.
 * @author Hernan Girardi
 * @since 04/06/2020
 */

@Entity
@Table(name = "T_MasDataConditionPrice", indexes = {
	@Index(name = "IDX_byCodeConditionPrice", columnList = "codeConditionPrice", unique = true),
	@Index(name = "IDX_byUngroup", columnList = "ungroup", unique = false)
})
@Getter
@Setter
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class MasDataConditionPrice extends BasicMasDataEntity {

	private static final long serialVersionUID = -4911632852992697090L;

	@Column(length=6,nullable=false)
	@NaturalId
	@Sap(valueList=PropertyValueListEnum.Fixed, text = "descriptionConditionPrice")
	private String codeConditionPrice;

	@Basic
	private String descriptionConditionPrice;

	@Column(length=512)
	private String detailDescConditionPrice;

	@Basic
	@ColumnDefault("false")
	private boolean ungroup;

	@Basic
	@ColumnDefault("false")
	private boolean esPrivado;

	@Basic
	@ColumnDefault("false")
	private boolean enviarAFacturar;

	@Override
	public boolean onEquals(Object obj) {
		if (this == obj)
			return true;
		if (getClass() != obj.getClass())
			return false;
		MasDataConditionPrice other = (MasDataConditionPrice) obj;
		if (codeConditionPrice != other.codeConditionPrice)
			return false;
		if (descriptionConditionPrice == null) {
			if (other.descriptionConditionPrice != null)
				return false;
		} else if (!descriptionConditionPrice.equals(other.descriptionConditionPrice))
			return false;
		if (detailDescConditionPrice == null) {
			if (other.detailDescConditionPrice != null)
				return false;
		} else if (!detailDescConditionPrice.equals(other.detailDescConditionPrice))
			return false;
		if (active != other.active)
			return false;
		if (ungroup != other.ungroup)
			return false;
		return true;
	}

}
