package com.echevarne.sap.cloud.facturacion.model;

import javax.persistence.*;

/**
 * Class for the Entity {@link EntityVersionHistory}.
 *
 * <p>This is a simple description of the method. . .</p>
 *
 * @author Steven Mendez
 * @version 1.0
 * @since 06/24/2019
 */
@Entity
@Table(
		name = "T_EntityVersionHistory",
		indexes = {
			@Index(name = "IDX_byClassNameEntityIdVersion", columnList = "className,entityId,version", unique = false),
		}
)
public class EntityVersionHistory extends BasicEntity {

	private static final long serialVersionUID = 7538192627870580L;

	@Basic
	private long entityId;

	@Basic
	private long version;

	@Basic
	private String className;

	@Basic
	private boolean active;

	@Lob
	@Column(length=5000)
	private String json;

	public long getVersion() {
		return version;
	}

	public void setVersion(long version) {
		this.version = version;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getJson() {
		return json;
	}

	public void setJson(String json) {
		this.json = json;
	}
	@Override
	public boolean onEquals(Object obj) {
		if (this == obj)
			return true;
		if (getClass() != obj.getClass())
			return false;
		EntityVersionHistory other = (EntityVersionHistory) obj;
		if (active != other.active)
			return false;
		if (className == null) {
			if (other.className != null)
				return false;
		} else if (!className.equals(other.className))
			return false;
		if (json == null) {
			if (other.json != null)
				return false;
		} else if (!json.equals(other.json))
			return false;
		if (version != other.version)
			return false;
		return true;
	}

	public long getEntityId() {
		return entityId;
	}

	public void setEntityId(long entityId) {
		this.entityId = entityId;
	}

	public String asString() {
		return "EntityVersionHistory [entityId=" + entityId + ", version=" + version + ", className=" + className
				+ ", active=" + active + ", json=" + json + "]";
	}

}
