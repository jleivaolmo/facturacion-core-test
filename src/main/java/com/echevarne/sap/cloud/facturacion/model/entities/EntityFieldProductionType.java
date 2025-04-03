package com.echevarne.sap.cloud.facturacion.model.entities;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

import com.echevarne.sap.cloud.facturacion.model.BasicEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "IT_EntityFieldProductionType")
@Getter
@Setter
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_ONLY, region = "read-only-region" )
@AllArgsConstructor
@NoArgsConstructor
public class EntityFieldProductionType extends BasicEntity {
	private static final long serialVersionUID = 1L;

	@Column(length=40)
	private String nombreTipo;

	@OneToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name = "fk_EntityFieldsName", nullable = false)
	@JsonBackReference
	private EntityFieldName entityFieldName;

	@Override
	public boolean onEquals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		EntityFieldProductionType other = (EntityFieldProductionType) obj;
		if (entityFieldName == null) {
			if (other.entityFieldName != null)
				return false;
		} else if (!entityFieldName.equals(other.entityFieldName))
			return false;
		if (nombreTipo == null) {
			if (other.nombreTipo != null)
				return false;
		} else if (!nombreTipo.equals(other.nombreTipo))
			return false;
		return true;
	}

}
