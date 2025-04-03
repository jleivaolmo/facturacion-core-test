package com.echevarne.sap.cloud.facturacion.model.entities;

import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NaturalId;

import com.echevarne.sap.cloud.facturacion.constants.ConstEntities;
import com.echevarne.sap.cloud.facturacion.model.BasicEntity;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Cacheable
@AllArgsConstructor
@NoArgsConstructor
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_ONLY, region = "read-only-region" )
@Table(
	name = ConstEntities.ENTIDAD_ENTITYLISTNAME,
	indexes = {
		@Index(name = "IDX_byNombreEntidad", columnList = "nombreEntidad", unique = true),
	}
	
)
public class EntityListName extends BasicEntity {

	private static final long serialVersionUID = 8611526697547824863L;

	@Column(length=40)
	@NaturalId
	private String nombreEntidad;

	@Basic
	private int grupoEntidad;

	@Basic
	private Integer statusObjeto;


	@OneToMany(cascade = CascadeType.ALL, mappedBy = "entity")
	@JsonManagedReference
	private Set<EntityListNameText> texts;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "entity")
	@JsonManagedReference
	private Set<EntityFieldName> fields;

	@Override
	public boolean onEquals(Object obj) {
		if (this == obj)
			return true;
		if (getClass() != obj.getClass())
			return false;
		EntityListName other = (EntityListName) obj;
		if (grupoEntidad != other.grupoEntidad)
			return false;
		if (nombreEntidad == null) {
			if (other.nombreEntidad != null)
				return false;
		} else if (!nombreEntidad.equals(other.nombreEntidad))
			return false;
		if (statusObjeto != other.statusObjeto)
			return false;
		return true;
	}

}
