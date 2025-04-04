package com.echevarne.sap.cloud.facturacion.model.masterdata;

import javax.persistence.*;

import com.echevarne.sap.cloud.facturacion.model.BasicMasDataEntity;
import com.echevarne.sap.cloud.facturacion.odata.annotations.Sap;
import com.echevarne.sap.cloud.facturacion.odata.annotations.enums.PropertyValueListEnum;
import com.google.common.base.Objects;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.NaturalId;

/**
 * Class for the Entity {@link ProcessControlStatus}.
 *
 * @author Hernan Girardi
 * @since 20/04/2020
 */
@Entity
@Table(name = "T_MasDataProcessControlStatus", indexes = {
		@Index(name = "IDX_byCodeStatus", columnList = "codeStatus", unique = true) })
@Getter
@Setter
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class MasDataProcessControlStatus extends BasicMasDataEntity {

	private static final long serialVersionUID = -1043017219840344210L;

	@Column(length=1,nullable=false)
	@Sap(valueList=PropertyValueListEnum.Fixed, text = "descriptionStatus")
	@NaturalId
	private String codeStatus;

	@Basic
	@ColumnDefault("false")
	private int phaseStatus;

	@Basic
	@ColumnDefault("false")
	private boolean finalOfPhase;

	@Basic
	private String descriptionStatus;

	@Override
	public boolean onEquals(Object obj) {
		if (this == obj) return true;
		if (getClass() != obj.getClass()) return false;
		MasDataProcessControlStatus other = (MasDataProcessControlStatus) obj;
		return Objects.equal(codeStatus, other.codeStatus)
			&& Objects.equal(descriptionStatus, other.descriptionStatus)
			&& Objects.equal(active, other.active)
			&& Objects.equal(phaseStatus, other.phaseStatus)
			&& Objects.equal(finalOfPhase, other.finalOfPhase);
	}
}
