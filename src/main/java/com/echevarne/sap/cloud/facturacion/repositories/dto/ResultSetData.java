package com.echevarne.sap.cloud.facturacion.repositories.dto;

import java.sql.Timestamp;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultSetData {
	String uuid;
	String uri;
	String email;
	int totalCount;
	Timestamp created;
	Timestamp lastUpdated;
	List<Long> idList;
}
