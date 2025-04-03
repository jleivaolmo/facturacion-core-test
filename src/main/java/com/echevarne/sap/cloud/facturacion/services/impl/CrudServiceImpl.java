package com.echevarne.sap.cloud.facturacion.services.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.orm.ObjectRetrievalFailureException;
import com.echevarne.sap.cloud.facturacion.exception.AbstractExceptionHandler;
import com.echevarne.sap.cloud.facturacion.model.BasicEntity;
import lombok.extern.slf4j.Slf4j;

/**
 * Crudservice Impl base development of the CRUD application
 *
 */
@Slf4j
public abstract class CrudServiceImpl<ModelObjectType extends BasicEntity, KeyType extends Serializable> extends AbstractExceptionHandler {

	private final JpaRepository<ModelObjectType, KeyType> repository;
	private final Class<?> domainClass = calculateDomainClass();
	
	public CrudServiceImpl(JpaRepository<ModelObjectType, KeyType> repository){
		this.repository = repository;
	}

	public ModelObjectType create(ModelObjectType entity) {
		entity.setEntityCreationTimestamp(new java.sql.Timestamp(System.currentTimeMillis()));
		entity.setInactive(false);
		return saveEntity(entity);
	}

  /**
   * This method is useful when we need to save entity but we need to clear cache as well.
   */
	public ModelObjectType createAndFlush(ModelObjectType entity) {
		return repository.saveAndFlush(entity);
	}

  /**
   *
   * @param entity to update based on generics
   */
	public void update(ModelObjectType entity) {
		saveEntity(entity);
	}

	public void updateAll(Collection<ModelObjectType> entities) {
		repository.saveAll(entities);
	}

  /**
   * This method is useful when we need to update entity but we need to clear cache as well.
   *
   * @param entity to update
   */
	public ModelObjectType updateAndFlush(ModelObjectType entity) {
		return repository.saveAndFlush(entity);
	}

	private ModelObjectType saveEntity(ModelObjectType entity) {

		return repository.save(entity);
	}

	public void remove(ModelObjectType entity) {
		repository.delete(entity);
	}

	public void removeAll(Collection<ModelObjectType> entities) {
		repository.deleteAll(entities);
	}

	public void removeAndFlush(ModelObjectType entity) {
		repository.delete(entity);
		repository.flush();
	}

	public JpaRepository<ModelObjectType, KeyType> getCrudRepository() {
		return this.repository;
	}

  /**
   * Based on Spring Data we are getting this information
   */
	public Optional<ModelObjectType> findById(KeyType entityId) {

		try {
			return repository.findById(entityId);
		} catch (ObjectRetrievalFailureException e) {
			if (log.isWarnEnabled()) {
				log.warn(String.format("object instance of %s identified by %s not found in the database",
						domainClass.getName(), entityId));
			}
			return Optional.empty();
		}
	}

	public List<ModelObjectType> findAllById(List<KeyType> entityIds) {

		return repository.findAllById(entityIds);

	}

	public List<ModelObjectType> getAll(CrudRepository crudRepository) {
		return (List<ModelObjectType>) crudRepository.findAll();
	}

	public List<ModelObjectType> getAll() {
		return getAll( getCrudRepository() );
	}

	protected Class<?> calculateDomainClass() {
		ParameterizedType thisType = (ParameterizedType) getClass().getGenericSuperclass();
		return  (Class<?>) thisType.getActualTypeArguments()[0];
	}

}
