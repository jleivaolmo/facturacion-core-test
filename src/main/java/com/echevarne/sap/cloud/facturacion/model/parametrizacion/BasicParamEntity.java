package com.echevarne.sap.cloud.facturacion.model.parametrizacion;

import javax.persistence.MappedSuperclass;

import com.echevarne.sap.cloud.facturacion.model.BasicEntity;

/**
 * Abstract super class for all the Parameters Entities.
 *
 * @author Germán Laso
 * @since 03/03/2021
 */
@MappedSuperclass
public abstract class BasicParamEntity extends BasicEntity {

    /**
     *
     */
    private static final long serialVersionUID = 2362382605409589881L;


    @Override
    public boolean onEquals(Object o) {
        return this.equals(o);
    }
}
