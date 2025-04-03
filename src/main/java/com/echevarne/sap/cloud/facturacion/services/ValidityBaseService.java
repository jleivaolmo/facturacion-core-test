package com.echevarne.sap.cloud.facturacion.services;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.springframework.data.repository.NoRepositoryBean;

import com.echevarne.sap.cloud.facturacion.model.ValidityBasicEntity;

/**
 * Class for all Services wich its Entity who heritage from {@link ValidityBasicEntity}  implement Query Methods by validity period dates, {@code validezDesde}
 * and {@code validezHasta} field.
 * @author Hernan Girardi
 * @since 23/04/2020
 */

@NoRepositoryBean
public interface ValidityBaseService<ModelObjectType extends ValidityBasicEntity, KeyType extends Serializable> {

	public List<ModelObjectType> findByValidezDesdeLessThanEqualAndValidezHastaGreaterThanEqual(Date validoDesde, Date validoHasta);

}
