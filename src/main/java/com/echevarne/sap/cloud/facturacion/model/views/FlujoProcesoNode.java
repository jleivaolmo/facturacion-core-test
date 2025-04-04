package com.echevarne.sap.cloud.facturacion.model.views;

import java.io.Serializable;

import javax.persistence.Basic;
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
@Table(name = "V_SOLI_FLUJO_NODES")
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter(AccessLevel.NONE)
@EqualsAndHashCode(callSuper = false)
@IdClass(SolicitudesSubKey.class)
@SapEntitySet(creatable = false, updatable = false, searchable = true)
public class FlujoProcesoNode implements Serializable {
	
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
	private String nodeId;
	
	@Basic
	private String laneId;
	
	@Basic
	private boolean selected;
	
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

	@Basic
	private String child;

}
