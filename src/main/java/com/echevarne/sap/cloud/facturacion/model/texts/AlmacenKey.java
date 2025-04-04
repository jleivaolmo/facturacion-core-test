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
public class AlmacenKey implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2917055774751096089L;

	@Id
	private String codigoCentro;
	
	@Id
	private String codigoAlmacen;


}
