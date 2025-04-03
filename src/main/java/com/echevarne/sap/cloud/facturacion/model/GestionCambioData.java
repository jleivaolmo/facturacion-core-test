package com.echevarne.sap.cloud.facturacion.model;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import com.echevarne.sap.cloud.facturacion.odata.annotations.SapEntitySet;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

/**
 * Class for the Entity {@link GestionCambioData}.
 *
 * <p>This is a simple description of the method. . .</p>
 *
 * @author Steven Ricardo Mendez Brenes
 * @version 1.0
 * @since 06/24/2019
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "T_GestionCambioData")
@SapEntitySet(creatable = false, updatable = false, searchable = true, deletable = false)
public class GestionCambioData extends BasicEntity {
	private static final long serialVersionUID = 5070698449322460709L;

	public enum Action {
		INSERT, UPDATE, DELETE
	};

	@Basic
	private String entityKey;

	@Basic
	private String entityClass;

	@Basic
	private String entityField;

	@Basic
	@Enumerated(EnumType.STRING)
	private Action action;

	@Basic
	private String oldValue;

	@Basic
	private String newValue;

	@ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name = "fk_GestionCambio", nullable = false)
	@JsonBackReference
	private GestionCambio gestion;

	public GestionCambio getGestion() {
		return gestion;
	}

	public void setGestion(GestionCambio gestion) {
		this.gestion = gestion;
	}

	public String getEntityKey() {
		return entityKey;
	}

	public void setEntityKey(String entityKey) {
		this.entityKey = entityKey;
	}

	public String getEntityClass() {
		return entityClass;
	}

	public void setEntityClass(String entityClass) {
		this.entityClass = entityClass;
	}

	public String getEntityField() {
		return entityField;
	}

	public void setEntityField(String entityField) {
		this.entityField = entityField;
	}

	public Action getAction() {
		return action;
	}

	public void setAction(Action action) {
		this.action = action;
	}

	public String getOldValue() {
		return oldValue;
	}

	public void setOldValue(String oldValue) {
		this.oldValue = oldValue;
	}

	public String getNewValue() {
		return newValue;
	}

	public void setNewValue(String newValue) {
		this.newValue = newValue;
	}

	@Override
	public boolean onEquals(Object obj) {
		if (this == obj)
			return true;
		if (getClass() != obj.getClass())
			return false;
		GestionCambioData other = (GestionCambioData) obj;
		if (action != other.action)
			return false;
		if (entityClass == null) {
			if (other.entityClass != null)
				return false;
		} else if (!entityClass.equals(other.entityClass))
			return false;
		if (entityField == null) {
			if (other.entityField != null)
				return false;
		} else if (!entityField.equals(other.entityField))
			return false;
		if (entityKey == null) {
			if (other.entityKey != null)
				return false;
		} else if (!entityKey.equals(other.entityKey))
			return false;
		if (newValue == null) {
			if (other.newValue != null)
				return false;
		} else if (!newValue.equals(other.newValue))
			return false;
		if (oldValue == null) {
			if (other.oldValue != null)
				return false;
		} else if (!oldValue.equals(other.oldValue))
			return false;
		return true;
	}

}
