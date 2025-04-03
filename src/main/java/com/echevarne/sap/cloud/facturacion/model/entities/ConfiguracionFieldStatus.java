package com.echevarne.sap.cloud.facturacion.model.entities;

import javax.persistence.*;

import com.echevarne.sap.cloud.facturacion.model.BasicEntity;
import com.echevarne.sap.cloud.facturacion.model.masterdata.MasDataEstadosGrupo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

@Entity
@Table(
	name = "IT_ConfiguracionFieldStatus",
	indexes = {
		@Index(name = "IDX_byConfigurationLevel", columnList = "configurationLevel", unique = false),
	}
)
@Getter
@Setter
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_ONLY, region = "read-only-region" )
// @SequenceAlias(name = ConstSequences.SECUENCIA_CONFIGURA)
@AllArgsConstructor
@NoArgsConstructor
public class ConfiguracionFieldStatus extends BasicEntity {

	/**
	 *
	 */
	private static final long serialVersionUID = 5695269552225453231L;

	@Column
	private String configurationLevel;

	@Column
	private Boolean allowChanges;


	@ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name = "fk_MasDataEstadosGrupo", nullable = false)
	private MasDataEstadosGrupo masDataEstadosGrupo;

	@ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name = "fk_EntityFieldName", nullable = false)
	private EntityFieldName entityFieldName;

	@Override
	public boolean onEquals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		ConfiguracionFieldStatus other = (ConfiguracionFieldStatus) obj;
		if (allowChanges == null) {
			if (other.allowChanges != null)
				return false;
		} else if (!allowChanges.equals(other.allowChanges))
			return false;
		if (configurationLevel == null) {
			if (other.configurationLevel != null)
				return false;
		} else if (!configurationLevel.equals(other.configurationLevel))
			return false;
		if (entityFieldName == null) {
			if (other.entityFieldName != null)
				return false;
		} else if (!entityFieldName.equals(other.entityFieldName))
			return false;
		if (masDataEstadosGrupo == null) {
			if (other.masDataEstadosGrupo != null)
				return false;
		} else if (!masDataEstadosGrupo.equals(other.masDataEstadosGrupo))
			return false;
		return true;
	}

}
