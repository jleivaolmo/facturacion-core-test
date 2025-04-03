package com.echevarne.sap.cloud.facturacion.services;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import com.echevarne.sap.cloud.facturacion.model.BasicEntity;

/**
 * Interface for the service{@link CrudService}.
 *
 * <p>This is a interface for Services. . .</p>
 *
 * @author Steven Ricardo Mendez Brenes
 * @version 1.0
 * @since 06/24/2019
 *
 */
public interface CrudService<ModelObjectType extends BasicEntity, KeyType extends Serializable> {

	/**
	 * Makes <code>entity</code> persistent.
	 *
	 * @param entity the entity to be persisted.
	 *
	 * @return the generated new persistent entity.
	 */
	ModelObjectType create(ModelObjectType entity);

	ModelObjectType createAndFlush(ModelObjectType entity);

	/**
	 * Updates the entity data into the data store.
	 *
	 * @param entity the entity to be updated.
	 */
	void update(ModelObjectType entity);

	void updateAll(Collection<ModelObjectType> entities);

	ModelObjectType updateAndFlush(ModelObjectType entity);

	/**
	 * Removes the entity from the data stores (making the instance passed as
	 * parameter transient).
	 *
	 * @param entity the entity to be removed/made transient.
	 */
	void remove(ModelObjectType entity);

	/**
	 * Removes the entity from the data stores (making the instance passed as
	 * parameter transient).
	 *
	 * @param entities the entity to be removed/made transient.
	 */
	void removeAll(Collection<ModelObjectType> entities);

	/**
	 * Removes and flush the entity from the data stores (making the instance passed as
	 * parameter transient).
	 *
	 * @param entity the entity to be removed/made transient.
	 */
	void removeAndFlush(ModelObjectType entity);

	/**
	 * Searches the data store for the entity identified by <code>entityId</code>.
	 *
	 * @param entityId the primary identifier of the entity being searched.
	 *
	 * @return the entity identified by <code>entityId</code> or <code>null</code>
	 *         if no entity was found.
	 */
	Optional<ModelObjectType> findById(KeyType entityId);

	/**
	 * Searches the data store for the entity identified by <code>entityId</code>.
	 *
	 * @param entityIds the primary identifier of the entity being searched.
	 *
	 * @return the entity identified by <code>entityIds</code> or <code>null</code>
	 *         if no entity was found.
	 */
	List<ModelObjectType> findAllById(List<KeyType> entityIds);

	/**
	 * Returns all the entities of the given Service Class.
	 *
	 * @return the collection of entities.
	 */
	List<ModelObjectType> getAll(CrudRepository crudRepository);

	List<ModelObjectType> getAll();

	JpaRepository<ModelObjectType, KeyType> getCrudRepository();
}
