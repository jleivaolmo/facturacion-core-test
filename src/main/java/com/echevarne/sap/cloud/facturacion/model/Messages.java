package com.echevarne.sap.cloud.facturacion.model;


import com.echevarne.sap.cloud.facturacion.model.masterdata.MasDataMessagesGrupo;
import com.echevarne.sap.cloud.facturacion.odata.annotations.SapEntitySet;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;
import javax.persistence.*;

/**
 * Class for the Entity {@link Messages}.
 * @author Hernan Girardi
 * @since 20/04/2020
 */
@Entity
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
@Table(name = "T_Messages", indexes={
	@Index(name = "IDX_byLangAndMsgkey",  columnList="lang,msgkey", unique=true),
	@Index(name = "IDX_byLangAndErrorCode",  columnList="lang,errorCode", unique=true)
})
@SapEntitySet(creatable = false, updatable = false, searchable = true, deletable = false)
@AllArgsConstructor
@NoArgsConstructor
public class Messages extends BasicEntity {

	public static final String DEFAULT_KEY = "default";

	private static final long serialVersionUID = -6209454477315586546L;

	@Column(nullable=false)
	private String msgKey;

	@Column(nullable=false)
	private String errorCode;

	@Basic
	private String description;

	@Column(nullable=false)
	private String lang;

	@ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name = "fk_MessagesGrupo", nullable = false)
	@JsonBackReference
	private MasDataMessagesGrupo grupo;

	public String getMsgKey() {
		return this.msgKey;
	}

	public void setMsgKey(String keyMsg) {
		this.msgKey = keyMsg;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	@Override
	public boolean onEquals(Object obj) {

		if (this == obj)
			return true;

		if (getClass() != obj.getClass())
			return false;

		Messages other = (Messages) obj;

		if (msgKey == null) {
			if (other.msgKey != null)
				return false;
		} else if (!msgKey.equals(other.msgKey))
			return false;

		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;

		if (lang == null) {
			if (other.lang != null)
				return false;
		} else if (!lang.equals(other.lang))
			return false;

		if (errorCode == null) {
			if (other.errorCode != null)
				return false;
		} else if (!errorCode.equals(other.errorCode))
			return false;

		return true;
	}
}
