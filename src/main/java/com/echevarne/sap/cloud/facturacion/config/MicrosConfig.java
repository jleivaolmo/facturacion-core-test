package com.echevarne.sap.cloud.facturacion.config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@Profile("!test")
public class MicrosConfig {
	
	public enum Param {
		PARAM_1,
		PARAM_2,
		PARAM_3;
	}

	public enum Secret {
		MICROSERVICES_BASICAUTH,
		SFTP_AUTH;
	}

	@Getter
	@AllArgsConstructor
	public static class SecretData{
		private String userName;
		private String value;
		private String metadata;
	}
	
	private Map<String, String> paramMap;
	private Map<String, SecretData> secretMap;

	public String getParam(MicrosConfig.Param param) {
		return paramMap.get(param.name());
	}
	public SecretData getSecret(MicrosConfig.Secret secret) {
		return this.secretMap.get(secret.name());
	}	

	public Map<String, String> getParamMap() {
		return paramMap;
	}	

	// Si esto se ejecuta en 'facturacion-batch', este es el jdbcTemplate
	// a emplear (de los 3 existentes, para los 3 datasources)
    @Autowired(required = false)
	@Qualifier("facturacionDBJdbcTemplate")
	private JdbcTemplate facturacionDBJdbcTemplate;

    @Autowired
    private JdbcTemplate onlyJdbcTemplate;


	
	// Si no se ha podido inyectar el jdbcTemplate (que solo existe en
	// facturacion-batch) es que está ejecutando cualquier otro micro, usamos el
	// jdbcTemplate por defecto.
	private JdbcTemplate getJdbcTemplate() {
		if (facturacionDBJdbcTemplate == null) return onlyJdbcTemplate;
		return facturacionDBJdbcTemplate;
	}

	@PostConstruct
	public void init() {
		populateParams();
		populateSecrets();
	}
	
	private void populateParams() {
		// Retrieve all rows from the T_FACTCONFIGURACION table
		List<Map<String, Object>> rows = getJdbcTemplate()
				.queryForList("SELECT PARAM_NAME, PARAM_VALUE FROM FACTURACION.T_FACTCONFIGURACION");

		// Convert the collection of rows to a HashMap with paramName as the key
		Map<String, Map<String, Object>> rowMap = new HashMap<>();
		for (Map<String, Object> row : rows) {
			String secretName = (String) row.get("PARAM_NAME");
			rowMap.put(secretName, row);
		}
		this.paramMap = new HashMap<String, String>();
		// Iterate over the enumeration values
		for (Param p : Param.values()) {
			// Try to locate the row in the HashMap
			Map<String, Object> row = rowMap.get(p.name());

			if (row == null) {
				throw new IllegalStateException("Values not found in the database for parameter: " + p.name());
			} else {
				this.paramMap.put(p.name(), (String) row.get("PARAM_VALUE"));
			}
		}
		if (rows.size() > Param.values().length)
			log.warn("There were " + (rows.size() - Param.values().length)
					+ " entries found in the T_FACTCONFIGURACION table with no corresponding enum member");
	}	

	private void populateSecrets() {
		// Retrieve all rows from the T_FACTSECRETS table
		List<Map<String, Object>> rows = getJdbcTemplate()
				.queryForList("SELECT SECRETNAME, USERNAME, VALUE, METADATA FROM FACTURACION.T_FACTSECRETS");

		// Convert the collection of rows to a HashMap with secretName as the key
		Map<String, Map<String, Object>> rowMap = new HashMap<>();
		for (Map<String, Object> row : rows) {
			String secretName = (String) row.get("SECRETNAME");
			rowMap.put(secretName, row);
		}
		this.secretMap = new HashMap<String, SecretData>();
		// Iterate over the enumeration values
		for (Secret secret : Secret.values()) {
			// Try to locate the row in the HashMap
			Map<String, Object> row = rowMap.get(secret.name());

			if (row == null) {
				throw new IllegalStateException("Values not found in the database for secret: " + secret.name());
			} else {
				SecretData s = new SecretData((String) row.get("USERNAME"),(String) row.get("VALUE"), (String) row.get("METADATA"));
				this.secretMap.put(secret.name(), s);
			}
		}
		if (rows.size() > Secret.values().length)
			log.warn("There were " + (rows.size() - Secret.values().length)
					+ " entries found in the table T_FACTSECRETS with no corresponding enum member");
	}
}
