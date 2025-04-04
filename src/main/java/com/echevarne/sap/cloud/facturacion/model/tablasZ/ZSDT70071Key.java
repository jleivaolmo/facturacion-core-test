package com.echevarne.sap.cloud.facturacion.model.tablasZ;

import java.io.Serializable;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class ZSDT70071Key implements Serializable {

	private static final long serialVersionUID = 8185197606142904806L;

	private String VKBUR;

	private String ZZREMNR;

	private String ZZCIANR;

	private String ZZCARGO;

	public ZSDT70071Key(String vKBUR, String zZREMNR, String zZCIANR, String zZCARGO) {
		super();
		VKBUR = vKBUR;
		ZZREMNR = zZREMNR;
		ZZCIANR = zZCIANR;
		ZZCARGO = zZCARGO;
	}
}
