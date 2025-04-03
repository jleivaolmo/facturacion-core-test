package com.echevarne.sap.cloud.facturacion.model.enums;

public enum ProcessStatus {
		RECEPCIONADA("R"), 
		SIMULADA("S"),
		INTERLOCUTORES("I"),
		CLASIFICADA("C"),
		TRANSFORMADA("T"), 
		AGRUPADA("A"),
		PEDIDO("P"),
		FACTURADA("F");

		String value;

		ProcessStatus(String value) {
			this.value = value;
		}

		public String getValue() {
	        return this.value;
		}
	}
