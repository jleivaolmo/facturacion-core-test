package com.echevarne.sap.cloud.facturacion.model;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import com.echevarne.sap.cloud.facturacion.odata.annotations.SapEntitySet;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * Class for the Entity {@link GestionCambio}.
 *
 * <p>This is a simple description of the method. . .</p>
 *
 * @author Steven Mendez
 * @version 1.0
 * @since 06/24/2019
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(
	name = "T_GestionCambio",
	indexes = {
		@Index(name = "IDX_byEntityId", columnList = "entityId", unique = false),
	}
)
@SapEntitySet(creatable = false, updatable = false, searchable = true, deletable = false)
public class GestionCambio extends BasicEntity {
	private static final long serialVersionUID = 2328862439360439597L;

	@Basic
	private long entityId;

	@Basic
	private long version;

	@Basic
	private String className;

	@Basic
	private boolean active;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "gestion")
	@JsonManagedReference
	private Set<GestionCambioData> data;

	public long getVersion() {
		return version;
	}

	public void setVersion(long version) {
		this.version = version;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public Set<GestionCambioData> getData() {
		return data;
	}

	public void setData(Set<GestionCambioData> data) {
		this.data = data;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}


	public long getEntityId() {
		return entityId;
	}

	public void setEntityId(long entityId) {
		this.entityId = entityId;
	}

	public void addGestionCambioData(GestionCambioData gcd) {
		if( data == null ) data = new HashSet<>();
		gcd.setGestion(this);
		data.add(gcd);
	}
	@Override
	public boolean onEquals(Object obj) {
		if (this == obj)
			return true;
		if (getClass() != obj.getClass())
			return false;
		GestionCambio other = (GestionCambio) obj;
		if (active != other.active)
			return false;
		if (className == null) {
			if (other.className != null)
				return false;
		} else if (!className.equals(other.className))
			return false;
		if (entityId != other.entityId)
			return false;
		if (version != other.version)
			return false;
		return true;
	}



}
