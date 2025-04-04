package com.echevarne.sap.cloud.facturacion.model.tablasZ;

import java.io.Serializable;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class ZSDT70046Key implements Serializable {

	private static final long serialVersionUID = 1631412537909944834L;

	private String KUNRG;

	private String KUNNR;

	private String PMATN;

	public ZSDT70046Key(String kUNRG, String kUNNR, String pMATN) {
		super();
		KUNRG = kUNRG;
		KUNNR = kUNNR;
		PMATN = pMATN;
	}

}
