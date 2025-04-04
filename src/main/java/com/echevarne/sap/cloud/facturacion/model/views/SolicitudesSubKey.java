package com.echevarne.sap.cloud.facturacion.model.views;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@EqualsAndHashCode
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SolicitudesSubKey implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5383111125544572419L;

	@Id
	@Basic
	private Long uuid_parent;
	
	@Id
	@Basic
	private Long uuid;

}
