package com.echevarne.sap.cloud.facturacion.model.texts;

import java.io.Serializable;

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
public class RegionKey implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5329581762567189040L;

	@Id
	private String codigoRegion;
	
	@Id
	private String codigoPais;
	
}
