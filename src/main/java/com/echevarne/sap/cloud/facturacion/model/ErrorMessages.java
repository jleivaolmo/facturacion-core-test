package com.echevarne.sap.cloud.facturacion.model;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * Class for the Entity {@link ErrorMessages}.
 * @author Hernan Girardi
 * @since 20/04/2020
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(
		name = "T_ErrorMessages",
		indexes = {
			@Index(name = "IDX_byErrorCode", columnList = "errorCode", unique = false),
		}
)
public class ErrorMessages extends BasicEntity {

	private static final long serialVersionUID = -4997221800633519588L;

	@Basic
	private String errorCode;

	@ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name = "fk_Message", nullable = false)
	@JsonManagedReference
	private Messages message;

	@Basic
	private String details;

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public Messages getMessage() {
		return message;
	}

	public void setMessage(Messages message) {
		this.message = message;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	@Override
	public boolean onEquals(Object obj) {
		if (this == obj)
			return true;

		if (getClass() != obj.getClass())
			return false;

		ErrorMessages other = (ErrorMessages) obj;

		if (errorCode == null) {
			if (other.errorCode != null)
				return false;
		} else if (!errorCode.equals(other.errorCode))
			return false;

		if (details == null) {
			if (other.details != null)
				return false;
		} else if (!details.equals(other.details))
			return false;

		if (message == null) {
			if (other.message != null)
				return false;
		} else if (!message.onEquals(other.message))
			return false;

		return true;
	}

}
