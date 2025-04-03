package com.echevarne.sap.cloud.facturacion.model.entities;

import java.util.Set;

import javax.persistence.*;

import com.echevarne.sap.cloud.facturacion.constants.ConstEntities;
import com.echevarne.sap.cloud.facturacion.model.BasicEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

/**
 * Class for the Entity {@link EntityFieldName}.
 *
 * <p>The persistent class. . .</p>
 * <p>This is a simple description of the class. . .</p>
 *
 * @author Germán Laso
 * @version 1.0
 * @since 20/04/2020
 *
 */
@Entity
@Table(
		name = ConstEntities.ENTIDAD_ENTITYFIELDSNAME,
		indexes = {
			@Index(name = "IDX_byNombreCampo", columnList = "nombreCampo", unique = false),
		}
)
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_ONLY, region = "read-only-region" )
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EntityFieldName extends BasicEntity {

	private static final long serialVersionUID = 1544995561599528877L;

	@Column(length=40)
	private String nombreCampo;

	@Basic
	private int tipoCampo;

	@ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name = "fk_EntityName", nullable = false)
	@JsonBackReference
	private EntityListName entity;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "field")
	@JsonManagedReference
	private Set<EntityFieldNameText> texts;


	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean onEquals(Object obj) {
		if (this == obj)
			return true;
		if (getClass() != obj.getClass())
			return false;
		EntityFieldName other = (EntityFieldName) obj;
		if (nombreCampo == null) {
			if (other.nombreCampo != null)
				return false;
		} else if (!nombreCampo.equals(other.nombreCampo))
			return false;
		if (tipoCampo != other.tipoCampo)
			return false;
		return true;
	}
}
