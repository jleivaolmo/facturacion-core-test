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
public class InterlocutorKey implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5576582394008732150L;

	@Id
	private String rolInterlocutor;
	
	@Id
	private String codigoCliente;
	
}
