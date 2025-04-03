package com.echevarne.sap.cloud.facturacion.controllers;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.echevarne.sap.cloud.facturacion.model.BasicEntity;
import com.echevarne.sap.cloud.facturacion.services.CrudService;

/**
 * Class for the management of the controllers {@link AbstractBasicCrudController}.
 * 
 * <p>. . .</p>
 * <p>Base class for all controllers</p>
 *
 * @author Steven Ricardo Mendez Brenes
 * @version 1.0
 * @since 06/24/2019
 * 
 */
public abstract class AbstractBasicCrudController<T extends BasicEntity, K extends Serializable> {

	private CrudService<T, K> service;

  /**
   * 
   * @return 
   */
	@GetMapping("/")
	public List<T> list() {
		return service.getAll(service.getCrudRepository());
	}

  /**
   * 
   * @param id
   * @return 
   */
	@GetMapping("/{id}")
	public T get(@PathVariable("id") K id) {
		Optional<T> t = service.findById(id);
		if( t.isPresent()) {
			return t.get();
		}
		return null;
	}

  /**
   * 
   * @param t 
   */
	@PostMapping("/")
	public void create(@RequestBody T t) {
		service.create(t);
	}

  /**
   * 
   * @param t 
   */
	@PutMapping("/")
	public void update(@RequestBody T t) {
		service.update(t);
	}

  /**
   * 
   * @param id 
   */
	@DeleteMapping("/{id}")
	public void delete(@PathVariable("id") K id) {
		Optional<T> entity = service.findById(id);
		if (entity.isPresent()) {
			service.remove(entity.get());
		}
	}

  /**
   * 
   * @param service 
   */
	public void setService(CrudService<T, K> service) {
		this.service = service;
	}

}
