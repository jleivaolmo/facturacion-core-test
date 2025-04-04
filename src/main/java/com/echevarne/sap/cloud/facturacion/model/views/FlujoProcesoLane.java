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
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "V_SOLI_FLUJO_LANES")
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter(AccessLevel.NONE)
@EqualsAndHashCode(callSuper = false)
@IdClass(SolicitudesSubKey.class)
@Cacheable(false)
@SapEntitySet(creatable = false, updatable = false, searchable = true)
public class FlujoProcesoLane implements Serializable {
	
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
	private String laneId;
	
	@Basic
	private int position;
	
	@Basic
	private String icon;
	
	@Basic
	private String label;
	
	@Basic
	private String state;
	
	@Basic
	private String text;
	
}
