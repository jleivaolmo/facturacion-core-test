package com.echevarne.sap.cloud.facturacion.model.replicated;

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
public class S4KeyMaterialSales implements Serializable{
	
	private static final long serialVersionUID = 1397391983964434315L;

	@Id
	@Basic
	private String MATNR;
	
	@Id
	@Basic
	private String VKORG;
	
	@Id
	@Basic
	private String VTWEG;
	
}