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
 * Class for the Entity {@link EntityFieldNameText}.
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
@Table(name = ConstEntities.ENTIDAD_ENTITYFIELDNAMETEXT)
@Getter
@Setter
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_ONLY, region = "read-only-region" )
public class EntityFieldNameText extends BasicEntity {

	private static final long serialVersionUID = -7576381580146432706L;

	@Column(length=2)
	private String claveIdioma;

	@Column(length=60)
	private String textoCampo;

	@ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name = "fk_FieldName", nullable = false)
	@JsonBackReference
	private EntityFieldName field;

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean onEquals(Object obj) {
		if (this == obj)
			return true;
		if (getClass() != obj.getClass())
			return false;
		EntityFieldNameText other = (EntityFieldNameText) obj;
		if (claveIdioma == null) {
			if (other.claveIdioma != null)
				return false;
		} else if (!claveIdioma.equals(other.claveIdioma))
			return false;
		if (textoCampo == null) {
			if (other.textoCampo != null)
				return false;
		} else if (!textoCampo.equals(other.textoCampo))
			return false;
		return true;
	}
}
