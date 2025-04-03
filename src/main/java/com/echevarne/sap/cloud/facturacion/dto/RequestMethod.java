package com.echevarne.sap.cloud.facturacion.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestMethod implements Serializable {

	private static final long serialVersionUID = 5196797687826024350L;

	private String methodName;

	private String methodData;
}
