package com.echevarne.sap.cloud.facturacion.services.impl;

import java.io.IOException;
import java.sql.Timestamp;

import com.echevarne.sap.cloud.facturacion.model.BasicEntity;
import com.echevarne.sap.cloud.facturacion.model.EntityVersionHistory;
import com.echevarne.sap.cloud.facturacion.repositories.EntityVersionHistoryRep;
import com.echevarne.sap.cloud.facturacion.services.EntityVersionHistoryService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("entityVersionHistorySrv")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class EntityVersionHistoryServiceImpl extends CrudServiceImpl<EntityVersionHistory, Long>
		implements EntityVersionHistoryService {

	private final ObjectMapper mapper = new ObjectMapper();

	private final EntityVersionHistoryRep entityVersionHistoryRep;

	@Autowired
	public EntityVersionHistoryServiceImpl(final EntityVersionHistoryRep entityVersionHistoryRep) {
		super(entityVersionHistoryRep);
		this.entityVersionHistoryRep = entityVersionHistoryRep;
	}

	/**
	 * Generate new version based on old entity and new entity
	 *
	 *
	 */
	@Override
	public void persistVersions(BasicEntity obj1, BasicEntity obj2) {
		try {
			if (obj1.getEntityVersion() == 1) {
				String json1 = mapper.writeValueAsString(obj1);

				EntityVersionHistory evh1 = new EntityVersionHistory();
				evh1.setEntityId(obj1.getId());
				evh1.setActive(false);
				evh1.setClassName(obj1.getClass().getCanonicalName());
				evh1.setEntityCreationTimestamp(new Timestamp(System.currentTimeMillis()));
				evh1.setVersion(obj1.getEntityVersion());
				evh1.setJson(json1);

				create(evh1);

			} else if (obj1.getEntityVersion() > 1) {
				deactivate(obj1);
			}

			String newson = mapper.writeValueAsString(obj2);

			EntityVersionHistory evh = new EntityVersionHistory();
			evh.setEntityId(obj2.getId());
			evh.setActive(true);
			evh.setClassName(obj2.getClass().getCanonicalName());
			evh.setEntityCreationTimestamp(new Timestamp(System.currentTimeMillis()));
			evh.setVersion(obj1.getEntityVersion() + 1);
			evh.setJson(newson);

			create(evh);

		} catch (JsonProcessingException e) {
			throw new RuntimeException(e.getMessage());
		}

	}

	/**
	 *
	 * @param obj entity to deactivate from the history
	 */
	private void deactivate(BasicEntity obj) {
		EntityVersionHistory evh = entityVersionHistoryRep.findByClassNameAndEntityIdAndVersion(
				obj.getClass().getCanonicalName(), obj.getId(), obj.getEntityVersion());
		if (evh != null) {
			evh.setActive(false);
			entityVersionHistoryRep.save(evh);
		}
	}

	/**
	 * We have to return based on id the version required
	 *
	 */
	public <T> T getInstanceVersion(Class<T> clazz, long id, long version) {

		EntityVersionHistory evh = entityVersionHistoryRep
				.findByClassNameAndEntityIdAndVersion(clazz.getCanonicalName(), id, version);
		if (evh == null)
			return null;
		T obj = null;
		try {
			obj = mapper.<T>readValue(evh.getJson(), clazz);
		} catch (IOException e) {
			log.error("Ops!", e);
		}

		return obj;
	}

	/**
	 * This method return json version of the History
	 *
	 */
	public <T> String getJsonVersion(Class<T> clazz, long id, long version) {

		EntityVersionHistory evh = entityVersionHistoryRep
				.findByClassNameAndEntityIdAndVersion(clazz.getCanonicalName(), id, version);
		if (evh == null)
			return null;
		return evh.getJson();
	}

}
