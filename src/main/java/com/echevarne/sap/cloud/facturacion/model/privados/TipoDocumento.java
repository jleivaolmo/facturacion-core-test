package com.echevarne.sap.cloud.facturacion.model.privados;

import java.util.Arrays;
import java.util.Optional;

public enum TipoDocumento {
	
	NIF(1), NIE(2), PASAPORTE(3), CHIP(4), OTROS(5);
	
	int value;
	
	TipoDocumento(int valor){
		this.value = valor;
	}
		
	public int getValue() {
		return this.value;
	}
	
	public static TipoDocumento getByValue(int outValue) {
		Optional<TipoDocumento> optTipoDoc = Arrays.stream(TipoDocumento.values()).filter(x -> x.value == outValue).findAny();
		if( optTipoDoc.isPresent())
			return optTipoDoc.get();
		else
			return OTROS;
	}
	
}