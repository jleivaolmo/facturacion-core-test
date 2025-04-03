package com.echevarne.sap.cloud.facturacion.odata.jpa.listeners;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.echevarne.sap.cloud.facturacion.odata.jpa.jpql.CustomJPAQueryBuilder;

import org.apache.olingo.odata2.api.exception.ODataException;
import org.apache.olingo.odata2.api.uri.info.DeleteUriInfo;
import org.apache.olingo.odata2.api.uri.info.GetEntityCountUriInfo;
import org.apache.olingo.odata2.api.uri.info.GetEntitySetCountUriInfo;
import org.apache.olingo.odata2.api.uri.info.GetEntitySetUriInfo;
import org.apache.olingo.odata2.api.uri.info.GetEntityUriInfo;
import org.apache.olingo.odata2.api.uri.info.PutMergePatchUriInfo;
import org.apache.olingo.odata2.jpa.processor.api.ODataJPAQueryExtensionEntityListener;
import org.apache.olingo.odata2.jpa.processor.api.exception.ODataJPARuntimeException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomQueryListener extends ODataJPAQueryExtensionEntityListener {

    @Override
    public String generateDeltaToken(List<Object> deltas, Query query) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.000");
        Date date = new Date(System.currentTimeMillis());
        dateFormat.format(date);
        return dateFormat.format(date);
    }

    @Override
    public Query getQuery(GetEntitySetUriInfo uriInfo, EntityManager em) throws ODataJPARuntimeException {
        try {
            CustomJPAQueryBuilder jpqlQueryBuilder = new CustomJPAQueryBuilder();
            return jpqlQueryBuilder.buildQuery(uriInfo, em);
        } catch (ODataException e) {
            log.error("Ops!", e);
            return super.getQuery(uriInfo, em);
        }
    }

    @Override
    public Query getQuery(GetEntityUriInfo uriInfo, EntityManager em) throws ODataJPARuntimeException {
        try {
            CustomJPAQueryBuilder jpqlQueryBuilder = new CustomJPAQueryBuilder();
            return jpqlQueryBuilder.buildQuery(uriInfo, em);
        } catch (ODataException e) {
            log.error("Ops!", e);
            return super.getQuery(uriInfo, em);
        }
    }

    @Override
    public Query getQuery(GetEntityCountUriInfo uriInfo, EntityManager em) throws ODataJPARuntimeException {
        try {
            CustomJPAQueryBuilder jpqlQueryBuilder = new CustomJPAQueryBuilder();
            return jpqlQueryBuilder.buildQuery(uriInfo, em);
        } catch (ODataException e) {
            log.error("Ops!", e);
            return super.getQuery(uriInfo, em);
        }
    }

    @Override
    public Query getQuery(GetEntitySetCountUriInfo uriInfo, EntityManager em) throws ODataJPARuntimeException {
        try {
            CustomJPAQueryBuilder jpqlQueryBuilder = new CustomJPAQueryBuilder();
            return jpqlQueryBuilder.buildQuery(uriInfo, em);
        } catch (ODataException e) {
            log.error("Ops!", e);
            return super.getQuery(uriInfo, em);
        }
    }

    @Override
    public Query getQuery(PutMergePatchUriInfo uriInfo, EntityManager em) throws ODataJPARuntimeException {
        return super.getQuery(uriInfo, em);
    }

    @Override
    public Query getQuery(DeleteUriInfo uriInfo, EntityManager em) throws ODataJPARuntimeException {
        return super.getQuery(uriInfo, em);
    }

    @Override
    public boolean isTombstoneSupported() {
        return true;
    }

}
