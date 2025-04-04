package com.echevarne.sap.cloud.facturacion.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.echevarne.sap.cloud.facturacion.model.trazabilidad.Trazabilidad;
import com.echevarne.sap.cloud.facturacion.model.trazabilidad.TrazabilidadClasificacion;
import com.echevarne.sap.cloud.facturacion.repositories.TrazabilidadClasificacionRepository;
import com.echevarne.sap.cloud.facturacion.services.TrazabilidadClasificacionService;

@Service("trazabilidadClasificacionSrv")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class TrazabilidadClasificacionServiceImpl extends CrudServiceImpl<TrazabilidadClasificacion, Long> implements TrazabilidadClasificacionService {

	private final TrazabilidadClasificacionRepository trazabilidadClasificacionRep;

	@Autowired
	public TrazabilidadClasificacionServiceImpl(final TrazabilidadClasificacionRepository trazabilidadClasificacionRep){
		super(trazabilidadClasificacionRep);
		this.trazabilidadClasificacionRep = trazabilidadClasificacionRep;
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<TrazabilidadClasificacion> findByTrazabilidadPrueba(Trazabilidad trazabilidadPrueba) {
		List<TrazabilidadClasificacion> trazabilidadClasificacionList =
				trazabilidadClasificacionRep.findByTrazabilidadPrueba(trazabilidadPrueba);
		if (trazabilidadClasificacionList == null || trazabilidadClasificacionList.isEmpty()) {
			return Optional.empty();
		}
		return trazabilidadClasificacionList.stream().sorted((tr0,tr1) -> {return (int) (tr0.getEntityVersion()-tr1.getEntityVersion());}).findFirst();
	}

}
