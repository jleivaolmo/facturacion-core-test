package com.echevarne.sap.cloud.facturacion.model.reglasfacturacion;

import com.echevarne.sap.cloud.facturacion.model.BasicEntity;
import com.echevarne.sap.cloud.facturacion.util.StringUtils;

import javax.persistence.Basic;
import java.util.Set;

public abstract class ReglaFactBase extends BasicEntity {

    @Basic
    protected boolean porExclusion;

    public boolean apply(Set<? extends ReglaFactBase> lista, Object value) {
        if (porExclusion) {
            return StringUtils.notEqualsAnyOrValue(lista, value);
        } else {
            return StringUtils.equalsAnyOrValue(lista, value);
        }
    }

}
