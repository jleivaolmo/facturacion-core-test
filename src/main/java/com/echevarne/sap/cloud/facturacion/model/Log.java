package com.echevarne.sap.cloud.facturacion.model;

import java.sql.Timestamp;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

import com.echevarne.sap.cloud.facturacion.odata.annotations.SapEntitySet;

/**
 * Class for the Entity {@link Log}.
 *
 * <p>This is a simple description of the class. . .</p>
 *
 * @author Steven Ricardo Mendez Brenes
 * @version 1.0
 * @since 06/24/2019
 */
@Entity
@Table(name = "T_Log")
@SapEntitySet(creatable = false, updatable = false, searchable = true, deletable = false)
public class Log extends BasicEntity {
	private static final long serialVersionUID = -1528994215206572157L;

	@Basic
	private Timestamp timestamp;

	@Basic
	private String module;

	@Basic
	private String className;

	@Basic
	private String codigoPeticion;

	@Basic
	private String level;

	@Basic
	private String message;

	@Lob
	@Column(length=5000)
	private String stack;

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getCodigoPeticion() {
		return codigoPeticion;
	}

	public void setCodigoPeticion(String codigoPeticion) {
		this.codigoPeticion = codigoPeticion;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getStack() {
		return stack;
	}

	public void setStack(String stack) {
		this.stack = stack;
	}

	@Override
	public boolean onEquals(Object obj) {
		if (this == obj)
			return true;
		if (getClass() != obj.getClass())
			return false;
		Log other = (Log) obj;
		if (className == null) {
			if (other.className != null)
				return false;
		} else if (!className.equals(other.className))
			return false;
		if (level == null) {
			if (other.level != null)
				return false;
		} else if (!level.equals(other.level))
			return false;
		if (codigoPeticion == null) {
			if (other.codigoPeticion != null)
				return false;
		} else if (!codigoPeticion.equals(other.codigoPeticion))
			return false;
		if (message == null) {
			if (other.message != null)
				return false;
		} else if (!message.equals(other.message))
			return false;
		if (module == null) {
			if (other.module != null)
				return false;
		} else if (!module.equals(other.module))
			return false;
		if (stack == null) {
			if (other.stack != null)
				return false;
		} else if (!stack.equals(other.stack))
			return false;
		if (timestamp == null) {
			if (other.timestamp != null)
				return false;
		} else if (!timestamp.equals(other.timestamp))
			return false;
		return true;
	}

}
