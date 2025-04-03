package com.echevarne.sap.cloud.facturacion.odata.jpa.listeners;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.olingo.odata2.api.commons.InlineCount;
import org.apache.olingo.odata2.api.exception.ODataException;
import org.apache.olingo.odata2.api.processor.ODataContext;
import org.apache.olingo.odata2.api.uri.info.DeleteUriInfo;
import org.apache.olingo.odata2.api.uri.info.GetEntityCountUriInfo;
import org.apache.olingo.odata2.api.uri.info.GetEntitySetCountUriInfo;
import org.apache.olingo.odata2.api.uri.info.GetEntitySetUriInfo;
import org.apache.olingo.odata2.api.uri.info.GetEntityUriInfo;
import org.apache.olingo.odata2.api.uri.info.PutMergePatchUriInfo;
import org.apache.olingo.odata2.core.uri.UriInfoImpl;
import org.apache.olingo.odata2.jpa.processor.api.ODataJPATombstoneContext;
import org.apache.olingo.odata2.jpa.processor.api.exception.ODataJPARuntimeException;

import com.echevarne.sap.cloud.facturacion.config.ContextProvider;
import com.echevarne.sap.cloud.facturacion.repositories.dto.ResultSetData;

import com.echevarne.sap.cloud.facturacion.util.ODataContextUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CacheQueryListener extends CustomQueryListener {

	private static int MAX_AGE_SECONDS = 60*5;
	

    @Override
    public String generateDeltaToken(List<Object> deltas, Query query) {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }
    
	@Override
	public Query getQuery(GetEntitySetUriInfo uriInfo, EntityManager em) throws ODataJPARuntimeException {
		Query q = super.getQuery(uriInfo, em);
		final UriInfoImpl info = (UriInfoImpl) uriInfo;
		EntityGraph<?> namedEntityGraph = em.getEntityGraph("privados-inicial-graph");
		String key = ODataJPATombstoneContext.getDeltaToken();
		ResultSetData rsdata;
		
		q.setHint("javax.persistence.fetchgraph", namedEntityGraph);
		q.setHint("jakarta.persistence.fetchgraph", namedEntityGraph);
		return q;
	}

    @Override
	public Query getQuery(GetEntitySetCountUriInfo uriInfo, EntityManager em) throws ODataJPARuntimeException {
		return null;
	}
	@Override
	public Query getQuery(GetEntityUriInfo uriInfo, EntityManager em) throws ODataJPARuntimeException {
		return null;
	}
	@Override
	public Query getQuery(GetEntityCountUriInfo uriInfo, EntityManager em) throws ODataJPARuntimeException {
		return null;
	}
	@Override
	public Query getQuery(PutMergePatchUriInfo uriInfo, EntityManager em) throws ODataJPARuntimeException {
		return null;
	}
	@Override
	public Query getQuery(DeleteUriInfo uriInfo, EntityManager em) throws ODataJPARuntimeException {
		return null;
	}    

	public Query modifyQuery(EntityManager entityManager, Query originalQuery, ResultSetData rsdata) {
        // Cast the JPA query to a Hibernate-specific query if Hibernate is the JPA provider
        org.hibernate.query.Query hibernateQuery = (org.hibernate.query.Query) originalQuery;
        // Get the existing SQL string
        String originalSql = hibernateQuery.getQueryString();
        // Añadimos la condición de que busque por clave primaria, para acelerar la búsqueda
//        String modifiedSql = originalSql.replaceAll(
//            "(?i)order by ",
//            "AND E1.uuid IN :uuids order by "
//        );
        // Eliminamos todas los filtros, ponemos el de la clave primaria 
        String modifiedSql = originalSql.replaceAll(
                "(?i)where .*? order by",
                "WHERE E1.uuid IN :uuids AND E1.emailUsuario = :emailusuario order by"
            );        
//        caso deltatoken        
//        String modifiedSql = originalSql + " WHERE E1.uuid IN :uuids order by E1.uuid";
        // Create a new query with the modified SQL
        Query newQuery = entityManager.createQuery(modifiedSql);
	    // Transfer parameters from original query to new query
        // Este bloque solo si se conservan los filtros en la query con sus '?'
//	    for (Parameter<?> param : originalQuery.getParameters()) {
//	        // Get the parameter name or position
//	        String paramName = param.getName();
//	        if (paramName != null) {
//	            // Set the value for named parameters
//	            newQuery.setParameter(paramName, originalQuery.getParameterValue(paramName));
//	        } else if (param.getPosition() != null) {
//	            // Set the value for positional parameters (like ?1, ?2, etc.)
//	            newQuery.setParameter(param.getPosition(), originalQuery.getParameterValue(param.getPosition()));
//	        }
//	    }
        // Set the list of UUIDs as a parameter
        newQuery.setParameter("uuids", rsdata.getIdList());
        try {
			newQuery.setParameter("emailusuario", URLDecoder.decode(rsdata.getEmail(), "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			newQuery.setParameter("emailusuario", rsdata.getEmail().replace("%40", "@"));
		}
        return newQuery;
    }

	private String getRequestPath() {
		try {
			ODataContext context = ODataContextUtil.getODataContextPrivados();
			String requestUri = context.getPathInfo().getRequestUri().toString();
			String serviceRoot = context.getPathInfo().getServiceRoot().toString();
			// Check if serviceRoot is a prefix of requestUri
			if (requestUri.startsWith(serviceRoot)) {
				// Return the remainder after the prefix
				return requestUri.substring(serviceRoot.length());
			} else {
				// If not a prefix, return a string that would never provoke a hit
				return "NEVERMATCHINGSTRING";
			}
		} catch (ODataException e) {
			return "NEVERMATCHINGSTRING";
		}
	}
}
