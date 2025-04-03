package com.echevarne.sap.cloud.facturacion.mappers;

import com.fasterxml.jackson.annotation.ObjectIdGenerator;
import com.fasterxml.jackson.annotation.ObjectIdResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;

/**
 * @author David Bolet
 * @since 16/11/2021
 */
@Component
@Slf4j
public class EntityIdResolver
        implements ObjectIdResolver {

    @Autowired
    private EntityManager entityManager;

    public EntityIdResolver(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public EntityIdResolver() {
    }

    @Override
    public void bindItem(
            final ObjectIdGenerator.IdKey id,
            final Object pojo) {
    log.debug("I'm here");
    }

    @Override
    public Object resolveId(final ObjectIdGenerator.IdKey id) {
        return this.entityManager.find(id.scope, id.key);
    }

    @Override
    public ObjectIdResolver newForDeserialization(final Object context) {
        return this;
    }

    @Override
    public boolean canUseFor(final ObjectIdResolver resolverType) {
        return false;
    }

}