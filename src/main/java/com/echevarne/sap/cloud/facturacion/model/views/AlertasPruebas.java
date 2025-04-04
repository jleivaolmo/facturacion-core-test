package com.echevarne.sap.cloud.facturacion.model.views;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import com.echevarne.sap.cloud.facturacion.odata.annotations.Sap;
import com.echevarne.sap.cloud.facturacion.odata.annotations.SapEntitySet;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "V_PRUE_ALERTAS")
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter(AccessLevel.NONE)
@EqualsAndHashCode(callSuper = false)
@IdClass(PruebasSubKey.class)
@SapEntitySet(creatable = false, updatable = false, searchable = true)
public class AlertasPruebas implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2182122931545306940L;
	
	/*
	 * Clave
	 *  
	 *******************************************/
	@Id
	@Basic
	private Long uuid;

	@Id
	@Basic
	private Long uuid_parent;
	
	/*
	 * Campos
	 *  
	 *******************************************/
	@Basic
	@Sap(text = "nombreAlerta")
	private String codigoAlerta;
	
	@Basic
	private String nombreAlerta;

	@Basic
	private String descripcionAlerta;
	
}
