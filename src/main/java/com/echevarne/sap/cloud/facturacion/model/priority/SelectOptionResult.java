package com.echevarne.sap.cloud.facturacion.model.priority;

import com.echevarne.sap.cloud.facturacion.model.BasicEntity;

public class SelectOptionResult<T extends BasicEntity> {

    private int priority;
    private T entity;

    public SelectOptionResult(int priority, T entity) {
        this.priority = priority;
        this.entity = entity;
    }

    public int getPriority() {
        return priority;
    }

    public T getEntity() {
        return entity;
    }

    public void addPriority(int priority) {
        this.priority += priority;
    }
}
