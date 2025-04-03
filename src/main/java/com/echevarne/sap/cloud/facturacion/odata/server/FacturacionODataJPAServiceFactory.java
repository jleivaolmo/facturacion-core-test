package com.echevarne.sap.cloud.facturacion.odata.server;

import javax.persistence.EntityManagerFactory;

import org.apache.olingo.odata2.api.ODataCallback;
import org.apache.olingo.odata2.api.ODataDebugCallback;
import org.apache.olingo.odata2.api.ep.EntityProvider;
import org.apache.olingo.odata2.api.exception.ODataApplicationException;
import org.apache.olingo.odata2.api.processor.ODataErrorCallback;
import org.apache.olingo.odata2.api.processor.ODataErrorContext;
import org.apache.olingo.odata2.api.processor.ODataResponse;
import org.apache.olingo.odata2.api.processor.ODataSingleProcessor;
import org.apache.olingo.odata2.jpa.processor.api.ODataJPAContext;
import org.apache.olingo.odata2.jpa.processor.api.ODataJPAServiceFactory;
import org.apache.olingo.odata2.jpa.processor.api.OnJPAWriteContent;
import org.apache.olingo.odata2.jpa.processor.api.exception.ODataJPARuntimeException;
import org.apache.olingo.odata2.jpa.processor.api.model.JPAEdmExtension;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;
import com.echevarne.sap.cloud.facturacion.config.ContextProvider;
import com.echevarne.sap.cloud.facturacion.odata.client.OnDBWriteContent;
import com.echevarne.sap.cloud.facturacion.util.ODataContextUtil;


/**
 * Class for the Entity {@link FacturacionODataJPAServiceFactory}.
 * 
 * <p>
 * oDataJPAContext needs EntityManager Factory To serve as model in the OData
 * Context. . .
 * </p>
 *
 * @author Steven Ricardo Mendez Brenes
 * @version 1.0
 * @since 06/24/2019
 * 
 */
@Component
@Slf4j
public class FacturacionODataJPAServiceFactory extends ODataJPAServiceFactory {

	public static final String PUNIT_NAME = "Technicals";
	private static final int PAGE_SIZE = 1000;
	public static final OnJPAWriteContent onDBWriteContent = new OnDBWriteContent();

	/**
	 * Init context for Odata
	 * 
	 * @return
	 * @throws ODataJPARuntimeException
	 */
	@Override
	public ODataJPAContext initializeODataJPAContext() throws ODataJPARuntimeException {

		/* Get Context */
		ODataJPAContext oDataJPAContext = getODataJPAContext();

		try {

			/* Set properties */
			EntityManagerFactory entityManagerFactory = (EntityManagerFactory) ContextProvider.getBean("entityManagerFactory");
			oDataJPAContext.setEntityManagerFactory(entityManagerFactory);
			oDataJPAContext.setPersistenceUnitName(PUNIT_NAME);
			
			oDataJPAContext.setPageSize(PAGE_SIZE);
			oDataJPAContext.setDefaultNaming(false);

			/* Set extensions */
			JPAEdmExtension edmExtension = (JPAEdmExtension) ContextProvider.getBean("JPAExtension");
			oDataJPAContext.setJPAEdmExtension(edmExtension);

			/* Context util */
			ODataContextUtil.setODataContext(oDataJPAContext.getODataContext());

			/* Set error level */ 
			setErrorLevel(); 
			
			/* Debug */
			oDataJPAContext.getODataContext().setDebugMode(true);

			setOnWriteJPAContent(onDBWriteContent);

		} catch (Exception e) {
			log.error("Ops!", e);
		}

		return oDataJPAContext;
	}

	private void setErrorLevel() {
		setDetailErrors(true);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends ODataCallback> T getCallback(final Class<T> callbackInterface) {

		T callback;
		if(callbackInterface.isAssignableFrom(ODataDebugCallback.class)){
			callback = (T) new ScenarioDebugCallback();
		} else if (callbackInterface.isAssignableFrom(MyErrorCallback.class)) {
			callback = (T) new MyErrorCallback();
		} else {
			callback = (T) super.getCallback(callbackInterface);
		}
		/*
		return (T) (callbackInterface.isAssignableFrom(ODataDebugCallback.class) ? new ScenarioDebugCallback()
				: super.getCallback(callbackInterface));
		 */

		return callback;
	}

	private final class ScenarioDebugCallback implements ODataDebugCallback {
		@Override
		public boolean isDebugEnabled() {
			return true;
		}
	}

	static class MyErrorCallback implements ODataErrorCallback {
		@Override
		public ODataResponse handleError(ODataErrorContext context) throws ODataApplicationException {
			context.getException().printStackTrace();
			log.info(context.getException().getClass().getName() + ":" + context.getMessage());
			return EntityProvider.writeErrorDocument(context);
		}
	}

	@Override
	public ODataSingleProcessor createCustomODataProcessor(ODataJPAContext oDataJPAContext) {
		return new FacturacionODataJPAProcessor(oDataJPAContext);
	}

}
