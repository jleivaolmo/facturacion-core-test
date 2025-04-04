package com.echevarne.sap.cloud.facturacion.model;

import java.text.MessageFormat;
import java.util.Objects;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

import com.echevarne.sap.cloud.facturacion.model.masterdata.MasDataMessagesGrupo;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Abstract super class for all the Messages Entities.
 *
 * @author Germán Laso
 * @since 17/10/2020
 */
@MappedSuperclass
public class BasicMessagesEntity extends BasicEntity {

	/**
	 *
	 */
	private static final long serialVersionUID = -2999600813053231071L;

	/*
	 * Campos
	 *
	 ********************************************/
	@Column(length = 20)
	private String type;

	@Basic
	private String var1;

	@Basic
	private String var2;

	@Basic
	private String var3;

	@Basic
	private String var4;

	@Basic
	private String message;

	@Basic
	private int sequenceOrder;

	/*
	 * Asociaciones
	 *
	 ********************************************/
	@ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name = "fk_Messages", nullable = false)
	@JsonIgnore
	private Messages messages;

	@ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name = "fk_MasDataMessagesGrupo")
	@JsonIgnore
	private MasDataMessagesGrupo grupo;

	/*
	 * Getters & Setters
	 *
	 ********************************************/
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getVar1() {
		return var1;
	}

	public void setVar1(String var1) {
		this.var1 = var1;
	}

	public String getVar2() {
		return var2;
	}

	public void setVar2(String var2) {
		this.var2 = var2;
	}

	public String getVar3() {
		return var3;
	}

	public void setVar3(String var3) {
		this.var3 = var3;
	}

	public String getVar4() {
		return var4;
	}

	public void setVar4(String var4) {
		this.var4 = var4;
	}

	public Messages getMessages() {
		return messages;
	}

	public void setMessages(Messages messages) {
		this.messages = messages;
	}

	public String getMessage() {
		return MessageFormat.format(messages.getDescription(), var1, var2, var3, var4);
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getSequenceOrder() {
		return sequenceOrder;
	}

	public void setSequenceOrder(int sequenceOrder) {
		this.sequenceOrder = sequenceOrder;
	}

	public MasDataMessagesGrupo getGrupo() {
		return grupo;
	}

	public void setGrupo(MasDataMessagesGrupo grupo) {
		this.grupo = grupo;
	}

	/*
	 * Entity Methods
	 *
	 ********************************************/

	@Override
	public boolean onEquals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		BasicMessagesEntity other = (BasicMessagesEntity) obj;
		return  Objects.equals(this.type, other.type) &&
				Objects.equals(this.var1, other.var1) &&
				Objects.equals(this.var2, other.var2) &&
				Objects.equals(this.var3, other.var3) &&
				Objects.equals(this.var4, other.var4) &&
				Objects.equals(this.sequenceOrder, other.sequenceOrder);
	}

}
