package com.echevarne.sap.cloud.facturacion.odata.client;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;

import com.echevarne.sap.cloud.facturacion.odata.annotations.Destination;
import com.echevarne.sap.cloud.facturacion.util.BasicAuth;
import com.sap.core.connectivity.api.configuration.ConnectivityConfiguration;
import com.sap.core.connectivity.api.configuration.DestinationConfiguration;

import lombok.extern.slf4j.Slf4j;

/**
 * Class for the {@link ODataDataSource}.
 * 
 * <p>This is a simple description of the class. . .</p>
 *
 * @author Steven Ricardo Mendez Brenes
 * @version 1.0
 * @since 06/24/2019
 * 
 */
@Slf4j
public class ODataDataSource {

	private static ODataDataSource instance = new ODataDataSource();

	public static ODataDataSource getInstance() {
		return instance;
	}

	private Map<Class<?>, ODataEntityParams> map = new HashMap<>();

	public void register(Class<?> clazz, String table, String protocol, String host, String odataService,
			String authentication) {

		map.put(clazz, new ODataEntityParams(protocol, host, odataService, table, authentication));
	}

	public void register(Class<?> clazz, String table, String destination) throws FileNotFoundException, IOException {

		Properties prop = new Properties();
		prop.load(new FileReader(destination));

		URL url = new URL(prop.getProperty("URL"));

		String user = prop.getProperty("User");
		String password = prop.getProperty("Password");

		String basicAuth = BasicAuth.encode(user, password);

		map.put(clazz, new ODataEntityParams(url.getProtocol(), url.getHost(), url.getPath(), table, basicAuth));
	}

	
	public ODataEntityParams get(Class<?> clazz) {

		ODataEntityParams params = map.get(clazz);
		if (params == null) {

			Destination destination = clazz.getDeclaredAnnotation(Destination.class);
			if (destination == null) {
				throw new RuntimeException(
						"ODATA class must be annotated with @Destination annotation:  class " + clazz.getName());
			}

			String destinationName = destination.name();
			String target = destination.target();

			params = getJndi(destinationName, target);

			if (params == null) {
				params = getFromFolder(clazz, destinationName, target);
			}

			if (params == null) {
				throw new RuntimeException("ODATA class destionation not found:  class " + clazz.getName()
						+ " Desnitation = " + destinationName);
			}

			map.put(clazz, params);
		}

		return params;
	}

	private ODataEntityParams getJndi(String destinationName, String destinationTarget) {

		ODataEntityParams params = null;

		try {
			Context ctx = new InitialContext();

			ConnectivityConfiguration configuration = (ConnectivityConfiguration) ctx
					.lookup("java:comp/env/connectivityConfiguration");

			// Get destination configuration for "destinationName"
			DestinationConfiguration destConfiguration = configuration.getConfiguration(destinationName);

			if (destConfiguration == null) {
				throw new Exception(String.format(
						"Destination %s is not found. Hint:" + " Make sure to have the destination configured.",
						destinationName));
			}

			// Get the destination URL

			URL url = new URL(destConfiguration.getProperty("URL"));

			String user = destConfiguration.getProperty("User");
			String password = destConfiguration.getProperty("Password");

			String basicAuth = BasicAuth.encode(user, password);

			params = new ODataEntityParams(url.getProtocol(), url.getHost(), url.getPath(),	destinationTarget, basicAuth);

			

		} catch (Exception e) {
			log.error("Ops!", e);
		}

		return params;
	}

	//TODO
	private ODataEntityParams getERP(String destinationName) {
		//ErpConfigContext erp = new ErpConfigContext(destinationName);

		return null;
	}

	private ODataEntityParams getFromFolder(Class<?> clazz, String destinationName, String destinationTarget) {
		ODataEntityParams params = null;

		try {
			Properties prop = new Properties();
			prop.load(new FileReader(new File("destinations", destinationName)));

			URL url = new URL(prop.getProperty("URL"));

			String user = prop.getProperty("User");
			String password = prop.getProperty("Password");

			String basicAuth = BasicAuth.encode(user, password);

			params = new ODataEntityParams(url.getProtocol(), url.getHost(), url.getPath(), destinationTarget,
					basicAuth);

		} catch (FileNotFoundException e) {
			log.error("Ops!", e);
		} catch (IOException e) {
			log.error("Ops!", e);
		}

		return params;
	}


}
