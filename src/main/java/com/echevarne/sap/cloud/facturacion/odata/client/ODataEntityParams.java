package com.echevarne.sap.cloud.facturacion.odata.client;

import java.util.HashMap;
import java.util.Map;

public class ODataEntityParams {

	static enum PARAM {
		PROTOCOL, HOST, ODATA_SERVICE, TABLE, AUTHORIZATION
	};

	private Map<PARAM, String> map = new HashMap<>();

	public  ODataEntityParams(String protocol, String host, String odataService, String table, String authorization) {
		map.put(PARAM.PROTOCOL, protocol);
		map.put(PARAM.HOST, host);
		map.put(PARAM.ODATA_SERVICE, odataService);
		map.put(PARAM.TABLE, table);
		map.put(PARAM.AUTHORIZATION, authorization);
	}

	public String getProtocol() {
		return map.get(PARAM.PROTOCOL);
	}

	public String getHost() {
		return map.get(PARAM.HOST);
	}

	public String getService() {
		return map.get(PARAM.ODATA_SERVICE);
	}

	public String getTable() {
		return map.get(PARAM.TABLE);
	}

	public String getAuthorization() {
		return map.get(PARAM.AUTHORIZATION);
	}
}
