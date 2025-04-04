package com.echevarne.sap.cloud.facturacion.model.views;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import com.echevarne.sap.cloud.facturacion.odata.annotations.SapEntitySet;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Data
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Getter
@Setter(AccessLevel.NONE)
@EqualsAndHashCode(callSuper = false)
@Table(name = "V_PRUE_FLUJO")
@IdClass(PruebasSubKey.class)
@Cacheable(false)
@SapEntitySet(creatable = false, updatable = false, searchable = true)
public class FlujoProcesoPrueba implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1561500258012166524L;
	
	/*
	 * Clave
	 *  
	 *******************************************/
	@Id
	@Basic
	private Long uuid_parent;
	
	@Id
	@Basic
	private Long uuid;
	
	/*
	 * Campos
	 *  
	 *******************************************/
	
	@Basic
	private String id;
	
	@Basic
	private String lane;
	
	@Basic
	private String title;
	
	@Basic
	private String type;
	
	@Basic
	private String state;
	
	@Basic
	private String stateText;
	
	@Basic
	private boolean focused;
	
	@Basic
	private String texts;
	
	@Basic
	private boolean highlighted;

}
