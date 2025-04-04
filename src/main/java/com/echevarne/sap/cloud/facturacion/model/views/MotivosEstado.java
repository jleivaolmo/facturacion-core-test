package com.echevarne.sap.cloud.facturacion.model.views;

import java.text.MessageFormat;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.echevarne.sap.cloud.facturacion.odata.annotations.SapEntitySet;
import com.echevarne.sap.cloud.facturacion.util.StringUtils;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "V_SOLI_MOTIVOESTADOS")
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter(AccessLevel.NONE)
@EqualsAndHashCode(callSuper = false)
@SapEntitySet(creatable = false, updatable = false, searchable = false, deletable = false)
public class MotivosEstado {

	/*
	 * Clave
	 * 
	 *******************************************/
	@Id
	@Basic
	private Long uuid;

	@Basic
	private Long uuid_parent;

	/*
	 * Campos
	 * 
	 *******************************************/
	@Basic
	private String codigo;

	@Basic
	private String descripcion;

	@Basic
	@Getter(AccessLevel.NONE)
	private String message;

	@Basic
	private String codigoAlerta;

	@Basic
	private String nombreAlerta;

	@Basic
	private String descripcionAlerta;

	@Basic
	private String var1;

	@Basic
	private String var2;

	@Basic
	private String var3;

	@Basic
	private String var4;

	/*
	 * Getters
	 * 
	 ********************************************/
	public String getMessage() {
		if (this.message != null)
			return MessageFormat.format(this.message, this.var1 == null ? StringUtils.EMPTY : this.var1,
					this.var2 == null ? StringUtils.EMPTY : this.var2,
					this.var3 == null ? StringUtils.EMPTY : this.var3,
					this.var4 == null ? StringUtils.EMPTY : this.var4);
		else
			return StringUtils.EMPTY;
	}

}
