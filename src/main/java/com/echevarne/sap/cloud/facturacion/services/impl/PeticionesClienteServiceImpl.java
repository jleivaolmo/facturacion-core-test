package com.echevarne.sap.cloud.facturacion.services.impl;

import com.echevarne.sap.cloud.facturacion.model.actualizacion.PeticionesCliente;
import com.echevarne.sap.cloud.facturacion.model.actualizacion.ProcesoActualizacion;
import com.echevarne.sap.cloud.facturacion.repositories.PeticionesClienteRep;
import com.echevarne.sap.cloud.facturacion.services.PeticionesClienteService;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.Tuple;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service("peticionesClienteService")
@Transactional(readOnly = true)
public class PeticionesClienteServiceImpl implements PeticionesClienteService {

    @Autowired
    PeticionesClienteRep peticionesClienteRep;

    @Autowired
    EntityManager em;

    @Override
    public List<PeticionesCliente> findAll() {
        return peticionesClienteRep.findAll();
    }

    @Override
    public List<PeticionesCliente> findAllById(Iterable<Long> ids) {
        return peticionesClienteRep.findAllById(ids);
    }

    @Override
    public Optional<PeticionesCliente> findById(Long id) {
        return peticionesClienteRep.findById(id);
    }

    @Override
    public List<PeticionesCliente> findAllByListaCodigos(List<String> listaCodigos) {
        return peticionesClienteRep.findAllByListaCodigos(listaCodigos);
    }

    @Override
    public List<PeticionesCliente> findAllByCodigoCliente(String codigoCliente) {
        return peticionesClienteRep.findAllByCodigoCliente(codigoCliente);
    }

    @Override
    public Integer countByCodigoCliente(String codigoCliente) {
        return peticionesClienteRep.countByCodigoCliente(codigoCliente);
    }

    @Override
    public int countByParams(ProcesoActualizacion procesoActualizacion) {
		List<Tuple> resultList = findByParams(procesoActualizacion);
		return resultList.size();
    }

    @Override
	public List<Tuple> findByParams(ProcesoActualizacion procesoActualizacion) {
		var builder = em.getCriteriaBuilder();
    	var query = builder.createTupleQuery();
		var root = query.from(PeticionesCliente.class);
		setCabeceraSelect(query, root);
		Predicate filterByParamsPredicate = PeticionesClienteRep.filterByParamsPredicate(procesoActualizacion, root, builder);
		query.where(filterByParamsPredicate);
		setCabeceraGroupBy(query, root);
		var typedQuery = em.createQuery(query);
		var resultList = typedQuery.getResultList();
		return resultList;
	}

	@SuppressWarnings("rawtypes")
	private void setCabeceraGroupBy(CriteriaQuery<Tuple> query, Root<PeticionesCliente> root) {
		var listExpGroup = new ArrayList<Expression>();
		listExpGroup.add(root.get("codigoPeticion"));
		var arrayExpGroup = new Expression[listExpGroup.size()];
		listExpGroup.toArray(arrayExpGroup);
		query.groupBy(arrayExpGroup);
		
	}

	@SuppressWarnings("rawtypes")
	private void setCabeceraSelect(CriteriaQuery<Tuple> query, Root<PeticionesCliente> root) {
		var listSelection = new ArrayList<Selection>();
		listSelection.add(root.get("codigoPeticion").alias("codigoPeticion"));
		var arraySelection = new Selection[listSelection.size()];
		listSelection.toArray(arraySelection);
		query.multiselect(arraySelection);
	}

	

}
