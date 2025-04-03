package com.echevarne.sap.cloud.facturacion.odata.processor.requests;

import java.util.List;

import javax.persistence.EntityManager;

import org.apache.olingo.odata2.api.exception.ODataApplicationException;
import org.apache.olingo.odata2.api.exception.ODataException;
import org.apache.olingo.odata2.api.uri.info.GetEntitySetUriInfo;

public interface JPARequestProcessor {

    public List<Object> retrieveData(GetEntitySetUriInfo uriParserResultView, EntityManager em) throws ODataApplicationException, ODataException;

}