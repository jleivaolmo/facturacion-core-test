package com.echevarne.sap.cloud.facturacion.services.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.echevarne.sap.cloud.facturacion.constants.ConstLiquidacion;
import com.echevarne.sap.cloud.facturacion.model.liquidaciones.AgrupacionLiquidacion;
import com.echevarne.sap.cloud.facturacion.model.liquidaciones.EstadoLiquidacionPeticion;
import com.echevarne.sap.cloud.facturacion.model.masterdata.MasDataEstadoLiquidacion;
import com.echevarne.sap.cloud.facturacion.model.trazabilidad.TrazabilidadSolicitud;
import com.echevarne.sap.cloud.facturacion.repositories.AgrupacionLiquidacionRep;
import com.echevarne.sap.cloud.facturacion.services.AgrupacionLiquidacionService;
import com.echevarne.sap.cloud.facturacion.services.MasDataEstadoLiquidacionService;
import com.echevarne.sap.cloud.facturacion.services.TrazabilidadSolicitudService;
import lombok.var;

@Service("agrupacionLiquidacionSrv")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class AgrupacionLiquidacionServiceImpl extends CrudServiceImpl<AgrupacionLiquidacion, Long> implements AgrupacionLiquidacionService {

	private final AgrupacionLiquidacionRep agrupacionLiquidacionRep;
	
	@Autowired
	private MasDataEstadoLiquidacionService masDataEstadoLiquidacionSrv;
	
	@Autowired
	private TrazabilidadSolicitudService trazabilidadSolSrv;

	@Autowired
	public AgrupacionLiquidacionServiceImpl(final AgrupacionLiquidacionRep agrupacionLiquidacionRep) {
		super(agrupacionLiquidacionRep);
		this.agrupacionLiquidacionRep = agrupacionLiquidacionRep;
	}

	@Override
	public List<AgrupacionLiquidacion> findByEstado(Integer estado) {
		return agrupacionLiquidacionRep.findByEstado(estado);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public void cambioEstadoInNewTx(Long idAgrupacionLiquidacion, int tipoLiquidacion, String metodo) {
		var agrLiq = agrupacionLiquidacionRep.findById(idAgrupacionLiquidacion).get();
		var mdEstadoErr = masDataEstadoLiquidacionSrv.getEstado(ConstLiquidacion.ESTADO_ERROR);
		agrLiq.setEstado(mdEstadoErr);
		if (metodo.equals(ConstLiquidacion.LIQUIDACION)) {
			var mdEstadoLiq = masDataEstadoLiquidacionSrv.getEstado(ConstLiquidacion.ESTADO_LIQUIDABLE);
			agrLiq.getDetalle().forEach(d -> {
				var trz = d.getTrazabilidad();
				if (trz != null) {
					createEstadoLiquidacion(trz, tipoLiquidacion, mdEstadoLiq);
				}
			});
		}
	}
	
	@Override
	public void updateEnviadaByIds(List<Long> ids) {
		this.agrupacionLiquidacionRep.updateEnviadaByIds(ids);
	}
	
	private void createEstadoLiquidacion(TrazabilidadSolicitud t, int tipoLiquidacion, MasDataEstadoLiquidacion mdEstadoNuevo) {
		var estadoSolLiq = t.getEstadosLiquidacionPeticion().stream().filter(e -> e.getTipoLiquidacion() == tipoLiquidacion && !e.getInactive())
				.findAny().get();
		estadoSolLiq.setInactive(true);
		var estadoLiquidacionPeticion = new EstadoLiquidacionPeticion();
		estadoLiquidacionPeticion.setEstado(mdEstadoNuevo);
		estadoLiquidacionPeticion.setTipoLiquidacion(tipoLiquidacion);
		estadoLiquidacionPeticion.setImporteLiquidacion1(estadoSolLiq.getImporteLiquidacion1());
		estadoLiquidacionPeticion.setImporteLiquidacion2(estadoSolLiq.getImporteLiquidacion2());
		estadoLiquidacionPeticion.setImporteLiquidacion3(estadoSolLiq.getImporteLiquidacion3());
		estadoLiquidacionPeticion.setImporteLiquidacion4(estadoSolLiq.getImporteLiquidacion4());
		estadoLiquidacionPeticion.setImporteLiquidacion5(estadoSolLiq.getImporteLiquidacion5());
		estadoLiquidacionPeticion.setTrazabilidad(t);
		t.getEstadosLiquidacionPeticion().add(estadoLiquidacionPeticion);
		trazabilidadSolSrv.update(t);
	}
}
