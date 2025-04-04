package com.echevarne.sap.cloud.facturacion.services.impl;

import com.echevarne.sap.cloud.facturacion.model.solicitudindividual.SolicitudIndividual;
import com.echevarne.sap.cloud.facturacion.model.solicitudrecibida.PeticionMuestreo;
import com.echevarne.sap.cloud.facturacion.model.actualizacion.BloqueoTecnico;
import com.echevarne.sap.cloud.facturacion.model.trazabilidad.TrazabilidadSolicitud;
import com.echevarne.sap.cloud.facturacion.repositories.BloqueoTecnicoRep;
import com.echevarne.sap.cloud.facturacion.services.BloqueoTecnicoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service("bloqueoTecnicoService")
public class BloqueoTecnicoServiceImpl extends CrudServiceImpl<BloqueoTecnico, Long>
        implements BloqueoTecnicoService {

    private final BloqueoTecnicoRep bloqueoTecnicoRep;
    private static final String CF_INSTANCE_GUID = "CF_INSTANCE_GUID";
    
    @Autowired
    public BloqueoTecnicoServiceImpl(BloqueoTecnicoRep bloqueoTecnicoRep) {
        super(bloqueoTecnicoRep);
        this.bloqueoTecnicoRep = bloqueoTecnicoRep;
    }

    @Override
    @Transactional(readOnly = true)
    public List<BloqueoTecnico> findBloqueosActivos() {
        return bloqueoTecnicoRep.findActivos();
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<BloqueoTecnico> findBloqueosActivosPorUUIDInstance(String instanceUUID) {
        return bloqueoTecnicoRep.findActivosUUIDInstance(instanceUUID);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<BloqueoTecnico> getByTrazabilidad(TrazabilidadSolicitud trazabilidadSolicitud) {
        return bloqueoTecnicoRep.findByTrazabilidadSolicitud(trazabilidadSolicitud);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean tieneBloqueoTecnico(TrazabilidadSolicitud trazabilidadSolicitud) {
        return bloqueoTecnicoRep.countByTrazabilidadSolicitud(trazabilidadSolicitud)>0;
    }

    @Override
    public boolean tieneBloqueoTecnico(PeticionMuestreo peticion) {
        return tieneBloqueoTecnico(peticion.getTrazabilidad());
    }

    @Override
    public boolean tieneBloqueoTecnico(SolicitudIndividual si) {
        return tieneBloqueoTecnico(si.getTrazabilidad());
    }

    @Override
    @Transactional
    public BloqueoTecnico bloquearPeticion(PeticionMuestreo peticion) {
        return bloqueoTecnicoRep.save(crearBloqueoIndividual(peticion));
    }

    @Override
    public void desbloquearPeticion(PeticionMuestreo peticion) {
        desbloquear(peticion.getTrazabilidad());
    }

    @Override
    public BloqueoTecnico bloquearSolicitudIndividual(SolicitudIndividual solicitudIndividual) {
        return crearBloqueo(solicitudIndividual.getTrazabilidad());
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public synchronized BloqueoTecnico crearBloqueo(TrazabilidadSolicitud trazabilidadSolicitud) {
        BloqueoTecnico bloqueo = new BloqueoTecnico();
        bloqueo.setTrazabilidadSolicitud(trazabilidadSolicitud);
        bloqueo.setInicioBloqueo(Timestamp.from(Instant.now().minus(1, ChronoUnit.HOURS)));
        String instanceUUID = System.getenv(CF_INSTANCE_GUID);
        bloqueo.setUuidInstance(instanceUUID);
        return bloqueoTecnicoRep.save(bloqueo);
    }

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public synchronized void desbloquear(TrazabilidadSolicitud trazabilidadSolicitud) {
		try {
			Optional<BloqueoTecnico> optBloqueo = bloqueoTecnicoRep.findByTrazabilidadSolicitud(trazabilidadSolicitud);
			if (!optBloqueo.isPresent())
				return;
			BloqueoTecnico bloqueo = optBloqueo.get();
			bloqueoTecnicoRep.delete(bloqueo);
		} catch (Exception e) {
			log.error("No se ha podido desbloquear el bloqueoTecnico de trzSol =" + trazabilidadSolicitud.getId() + ": " + e, e);
			throw e;
		}
	}
    
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public synchronized void desbloquear(long idBloqueoTecnico) {
		try {
			Optional<BloqueoTecnico> optBloqueo = bloqueoTecnicoRep.findById(idBloqueoTecnico);
			if (!optBloqueo.isPresent())
				return;
			BloqueoTecnico bloqueo = optBloqueo.get();
			bloqueoTecnicoRep.delete(bloqueo);
		} catch (Exception e) {
			log.error("No se ha podido desbloquear el bloqueoTecnico con id=" + idBloqueoTecnico + ": " + e, e);
			throw e;
		}
	}

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public synchronized List<BloqueoTecnico> crearBloqueoMultiple(List<PeticionMuestreo> peticiones) {
        return bloqueoTecnicoRep.saveAll(peticiones.stream().map(pm -> crearBloqueoIndividual(pm)).collect(Collectors.toList()));
    }

    private synchronized BloqueoTecnico crearBloqueoIndividual(PeticionMuestreo peticionMuestreo) {
        BloqueoTecnico bloqueo = new BloqueoTecnico();
        bloqueo.setTrazabilidadSolicitud(peticionMuestreo.getTrazabilidad());
        bloqueo.setInicioBloqueo(new Timestamp(System.currentTimeMillis()));
        String instanceUUID = System.getenv(CF_INSTANCE_GUID);
        bloqueo.setUuidInstance(instanceUUID);
        return bloqueo;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void desbloquearMultiple(Iterable<SolicitudIndividual> solicitudes) {
        List<TrazabilidadSolicitud> trazabilidadSolicituds = new ArrayList<>();
        if (solicitudes.iterator().hasNext())
            solicitudes.iterator().forEachRemaining(x -> trazabilidadSolicituds.add(x.getTrazabilidad()));
            if (!trazabilidadSolicituds.isEmpty())
                bloqueoTecnicoRep.deleteByTrazabilidadSolicitudIsIn(trazabilidadSolicituds);
    }

}
