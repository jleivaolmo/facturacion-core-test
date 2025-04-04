package com.echevarne.sap.cloud.facturacion.services.impl;

import java.math.BigInteger;
import java.util.Calendar;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.echevarne.sap.cloud.facturacion.model.liquidaciones.PropuestaLiquidacion;
import com.echevarne.sap.cloud.facturacion.model.liquidaciones.PropuestaLiquidacionPeticiones;
import com.echevarne.sap.cloud.facturacion.repositories.PropuestaLiquidacionPeticionesRep;
import com.echevarne.sap.cloud.facturacion.repositories.PropuestaLiquidacionRep;
import com.echevarne.sap.cloud.facturacion.services.PropuestaLiquidacionService;
import com.echevarne.sap.cloud.facturacion.services.TrazabilidadSolicitudService;
import lombok.var;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("propuestaLiquidacionSrv")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class PropuestaLiquidacionServiceImpl extends CrudServiceImpl<PropuestaLiquidacion, Long> implements PropuestaLiquidacionService {

	private final PropuestaLiquidacionRep propuestaLiquidacionRep;

	@Autowired
	private TrazabilidadSolicitudService trazabilidadSolicitudSrv;
	
	@Autowired
	private PropuestaLiquidacionPeticionesRep propuestaLiquidacionPeticionesRep;

	private static final String CF_INSTANCE_GUID = "CF_INSTANCE_GUID";
	private static final String ANY = "*";

	@Autowired
	public PropuestaLiquidacionServiceImpl(final PropuestaLiquidacionRep propuestaLiquidacionRep) {
		super(propuestaLiquidacionRep);
		this.propuestaLiquidacionRep = propuestaLiquidacionRep;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public Long controlConcurrenciaLiquidacion(Integer tipoLiquidacion, String sociedad, String proveedor, String grupoProveedor,
			Calendar calFechaIni, Calendar calFechaFin, String nombreProceso, String codUsuario, String nombreUsuario) {
		String instanceUUID = System.getenv(CF_INSTANCE_GUID);
		Set<PropuestaLiquidacion> propLiq = null;
		if (!proveedor.equals(ANY) && !grupoProveedor.equals(ANY)) {
			propLiq = propuestaLiquidacionRep.findByParamsProvGr(proveedor, grupoProveedor, tipoLiquidacion, sociedad, calFechaIni, calFechaFin);
		} else if (proveedor.equals(ANY) && !grupoProveedor.equals(ANY)) {
			propLiq = propuestaLiquidacionRep.findByParamsGr(grupoProveedor, tipoLiquidacion, sociedad, calFechaIni, calFechaFin);
		} else if (!proveedor.equals(ANY) && grupoProveedor.equals(ANY)) {
			propLiq = propuestaLiquidacionRep.findByParamsProv(proveedor, tipoLiquidacion, sociedad, calFechaIni, calFechaFin);
		} else if (proveedor.equals(ANY) && grupoProveedor.equals(ANY)) {
			propLiq = propuestaLiquidacionRep.findByParams(tipoLiquidacion, sociedad, calFechaIni, calFechaFin);
		}
		
		if (propLiq != null && propLiq.size() > 0) {
			var idPropuesta = propLiq.stream().findAny().get().getId();
			log.error("La propuesta de liquidación " + idPropuesta + " entra en conflicto con la que se quiere lanzar con parámetros " + tipoLiquidacion + " "
					+ sociedad + " " + proveedor + " " + calFechaIni + " " + calFechaFin);
			throw new RuntimeException("No puede ejecutarse la liquidación por entrar en conflicto con otra liquidación en curso");
		} else {
			var newPropLiq = new PropuestaLiquidacion();
			newPropLiq.setTipoLiquidacion(tipoLiquidacion);
			newPropLiq.setSociedad(sociedad);
			newPropLiq.setProveedor(proveedor);
			newPropLiq.setGrupoProveedor(grupoProveedor);
			newPropLiq.setFechaInicioPeriodo(calFechaIni);
			newPropLiq.setFechaFinPeriodo(calFechaFin);
			newPropLiq.setFechaCreacion(Calendar.getInstance());
			newPropLiq.setNombreProceso(nombreProceso);
			newPropLiq.setCodUsuario(codUsuario);
			newPropLiq.setNombreUsuario(nombreUsuario);
			newPropLiq.setUuidInstance(instanceUUID);
			createAndFlush(newPropLiq);
			return newPropLiq.getId();
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void finalizaControlConcurrenciaLiquidacion(Long idCtrl) {
		var propLiq = propuestaLiquidacionRep.findById(idCtrl).get();
		propLiq.setFechaFinalizacion(Calendar.getInstance());
	}

	@Override
	public Set<PropuestaLiquidacion> findByUuidInstanceInProcess(String instanceUUID) {
		return propuestaLiquidacionRep.findByUUIDInProcess(instanceUUID);
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void marcarPeticiones(List listId, int indexIdSol, Set<Long> idsCtrl, Set<Long> listaPetMarcadas) {
		for (Object i : listId) {
			var arr = (Object[]) i;
			long idSol = ((BigInteger) arr[indexIdSol]).longValue();
			if (!listaPetMarcadas.contains(idSol)) {
				listaPetMarcadas.add(idSol);
				var idCtrl = idsCtrl.stream().findFirst().get();
				var propLiq = propuestaLiquidacionRep.findById(idCtrl).get();
				var trzSol = trazabilidadSolicitudSrv.findById(idSol).get();
				PropuestaLiquidacionPeticiones propLiqPet = new PropuestaLiquidacionPeticiones();
				propLiqPet.setPropuestaLiquidacion(propLiq);
				propLiqPet.setTrazabilidadSolicitud(trzSol);
				propLiq.getPeticiones().add(propLiqPet);
				trzSol.getPropLiqPeticiones().add(propLiqPet);
				propuestaLiquidacionPeticionesRep.save(propLiqPet);
			}
		}
	}
}
