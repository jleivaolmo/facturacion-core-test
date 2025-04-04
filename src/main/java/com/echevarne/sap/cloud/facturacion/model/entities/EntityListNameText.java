package com.echevarne.sap.cloud.facturacion.model.entities;

import javax.persistence.*;

import com.echevarne.sap.cloud.facturacion.constants.ConstEntities;
import com.echevarne.sap.cloud.facturacion.model.BasicEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

/**
 * Class for the Entity {@link EntityListNameText}.
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
@Getter
@Setter
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_ONLY, region = "read-only-region" )
@Table(name = ConstEntities.ENTIDAD_ENTITYLISTNAMETEXT)
public class EntityListNameText extends BasicEntity {

	/**
	 *
	 */
	private static final long serialVersionUID = -5961981095015215441L;

	@Column(length=2)
	private String claveIdioma;

	@Column(length=60)
	private String textoEntidad;

	@ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name = "fk_EntityName", nullable = false)
	@JsonBackReference
	private EntityListName entity;

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean onEquals(Object obj) {
		if (this == obj)
			return true;
		if (getClass() != obj.getClass())
			return false;
		EntityListNameText other = (EntityListNameText) obj;
		if (claveIdioma == null) {
			if (other.claveIdioma != null)
				return false;
		} else if (!claveIdioma.equals(other.claveIdioma))
			return false;
		if (textoEntidad == null) {
			if (other.textoEntidad != null)
				return false;
		} else if (!textoEntidad.equals(other.textoEntidad))
			return false;
		return true;
	}



}
