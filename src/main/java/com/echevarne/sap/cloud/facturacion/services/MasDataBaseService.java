package com.echevarne.sap.cloud.facturacion.services;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.NoRepositoryBean;

import com.echevarne.sap.cloud.facturacion.model.BasicMasDataEntity;

import javax.persistence.QueryHint;

/**
 * Class for all Services wich its Entity who heritage from {@link BasicMasDataEntity}  implement Query Methods by entity status, {@code active} field.
 * @author Hernan Girardi
 * @since 23/04/2020
 */

@NoRepositoryBean
public interface MasDataBaseService<ModelObjectType extends BasicMasDataEntity, KeyType extends Serializable> {

	@QueryHints({
		@QueryHint(name = org.hibernate.jpa.QueryHints.HINT_CACHEABLE, value = "true"),
		@QueryHint(name = org.hibernate.jpa.QueryHints.HINT_FLUSH_MODE, value = "MANUAL"),
	})
	List<ModelObjectType> findByActive(boolean active);
}
