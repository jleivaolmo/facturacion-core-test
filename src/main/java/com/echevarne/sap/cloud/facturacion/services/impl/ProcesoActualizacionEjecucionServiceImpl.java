package com.echevarne.sap.cloud.facturacion.services.impl;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.echevarne.sap.cloud.facturacion.model.actualizacion.ProcesoActualizacionEjecucion;
import com.echevarne.sap.cloud.facturacion.model.actualizacion.ProcesoActualizacionLog;
import com.echevarne.sap.cloud.facturacion.repositories.ProcesoActualizacionEjecucionRep;
import com.echevarne.sap.cloud.facturacion.repositories.ProcesoActualizacionLogRep;
import com.echevarne.sap.cloud.facturacion.repositories.ProcesoActualizacionRep;
import com.echevarne.sap.cloud.facturacion.services.ProcesoActualizacionEjecucionService;
import com.echevarne.sap.cloud.facturacion.util.DateUtils;

import lombok.var;

@Service("procesoActualizacionEjecucionService")
public class ProcesoActualizacionEjecucionServiceImpl extends CrudServiceImpl<ProcesoActualizacionEjecucion, Long>
        implements ProcesoActualizacionEjecucionService {

    private static Map<String, ProcesoActualizacionEjecucion> cache = Collections.synchronizedMap(new HashMap<>()); // TODO ATL: This will cause problems

    private final ProcesoActualizacionEjecucionRep procesoActualizacionEjecucionRep;
    private final ProcesoActualizacionRep procesoActualizacionRep;
    private final ProcesoActualizacionLogRep procesoActualizacionLogRep;

    @Autowired
    public ProcesoActualizacionEjecucionServiceImpl(
            ProcesoActualizacionEjecucionRep procesoActualizacionEjecucionRep,
            ProcesoActualizacionRep procesoActualizacionRep,
            ProcesoActualizacionLogRep procesoActualizacionLogRep
    ) {
        super(procesoActualizacionEjecucionRep);
        this.procesoActualizacionEjecucionRep = procesoActualizacionEjecucionRep;
        this.procesoActualizacionRep = procesoActualizacionRep;
        this.procesoActualizacionLogRep = procesoActualizacionLogRep;
    }

    private void addToCache(ProcesoActualizacionEjecucion procesoActualizacionEjecucion) {
        ProcesoActualizacionEjecucion procesoActualizacionEjecucionCached = new ProcesoActualizacionEjecucion();
        procesoActualizacionEjecucionCached.setId(null);
        procesoActualizacionEjecucionCached.setProcessId(procesoActualizacionEjecucion.getProcessId());
        procesoActualizacionEjecucionCached.setTotalPeticiones(procesoActualizacionEjecucion.getTotalPeticiones());
        procesoActualizacionEjecucionCached.setNumProcesadas(procesoActualizacionEjecucion.getNumProcesadas());
        procesoActualizacionEjecucionCached.setInicioProceso(procesoActualizacionEjecucion.getInicioProceso());
        procesoActualizacionEjecucionCached.setProcesoActualizacion(procesoActualizacionEjecucion.getProcesoActualizacion());
        procesoActualizacionEjecucionCached.setLogList(new HashSet<>());
        cache.put(procesoActualizacionEjecucion.getProcessId(), procesoActualizacionEjecucionCached);
    }

    @Override
    @Transactional
    public ProcesoActualizacionEjecucion programarEjecucion(Long idProcesoActualizacion) {
    	var procAct = procesoActualizacionRep.findById(idProcesoActualizacion).get();
        ProcesoActualizacionEjecucion procesoActualizacionEjecucion = new ProcesoActualizacionEjecucion();
        procesoActualizacionEjecucion.setProcesoActualizacion(procAct);
        procesoActualizacionEjecucion.setEntityCreationTimestamp(Timestamp.from(Instant.now()));
        procesoActualizacionEjecucion.setProcessId(UUID.randomUUID().toString());
        procesoActualizacionEjecucion.setFechaInicio(procAct.getFechaInicio());
        procesoActualizacionEjecucion.setHoraInicio(procAct.getHoraInicio());
        procesoActualizacionEjecucion.setFinProceso(null);
        procesoActualizacionEjecucion.setNumEjecucion(procAct.getListaEjecuciones().size()+1);
        procesoActualizacionEjecucion.setNumProcesadas(0);
        procesoActualizacionEjecucion.setEstado(ProcesoActualizacionEjecucion.ESTADO_PLANIFICADO);
        procAct.addEjecucion(procesoActualizacionEjecucion);
        procesoActualizacionRep.save(procAct);
        return procesoActualizacionEjecucion;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public ProcesoActualizacionEjecucion comenzar(String processUUID, Integer totalElementos, String instanceUUID) {
        ProcesoActualizacionEjecucion pr = getProcesoActualizacionEjecucionFromCache(processUUID);
        if (pr == null) {
            throw new RuntimeException(String.format("Ejecucion con uuid %s no encontrada ", processUUID));
        }
        pr.setTotalPeticiones(totalElementos);
        pr.setNumProcesadas(0);
        pr.setEstado(ProcesoActualizacionEjecucion.ESTADO_ENCURSO);
        pr.setInicioProceso(Calendar.getInstance());
        pr.setUuidInstance(instanceUUID);
        procesoActualizacionEjecucionRep.save(pr);
        addToCache(pr);
        return pr;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public ProcesoActualizacionEjecucion comenzarEjecucionPuntual(Integer totalElementos, String queryParams, String instanceUUID) {
        ProcesoActualizacionEjecucion procesoActualizacionEjecucion = new ProcesoActualizacionEjecucion();
        procesoActualizacionEjecucion.setProcesoActualizacion(null);
        procesoActualizacionEjecucion.setEntityCreationTimestamp(Timestamp.from(Instant.now()));
        procesoActualizacionEjecucion.setProcessId(UUID.randomUUID().toString());
        procesoActualizacionEjecucion.setFechaInicio(Calendar.getInstance());
        procesoActualizacionEjecucion.setHoraInicio(new Time(LocalTime.now().toSecondOfDay()*1000));
        procesoActualizacionEjecucion.setInicioProceso(Calendar.getInstance());
        procesoActualizacionEjecucion.setFinProceso(null);
        procesoActualizacionEjecucion.setNumProcesadas(0);
        procesoActualizacionEjecucion.setTotalPeticiones(totalElementos);
        procesoActualizacionEjecucion.setEstado(ProcesoActualizacionEjecucion.ESTADO_ENCURSO);
        procesoActualizacionEjecucion.setUuidInstance(instanceUUID);
        procesoActualizacionEjecucionRep.save(procesoActualizacionEjecucion);
        addToCache(procesoActualizacionEjecucion);
        return procesoActualizacionEjecucion;
    }

    @Override
    public synchronized void actualizar(String uuid, String codigoPeticion, Long duration) {
        ProcesoActualizacionEjecucion pr = getProcesoActualizacionEjecucionFromCache(uuid);
        if (pr == null) return;
        pr.setNumProcesadas(pr.getNumProcesadas()+1);
        ProcesoActualizacionLog log = ProcesoActualizacionLog.builder().procesoActualizacionEjecucion(pr).estadoLog(1).codigoPeticion(codigoPeticion).build();
        pr.addLog(log);
        cache.put(uuid,pr);
    }

    @Override
    public synchronized void actualizarError(String uuid, String codigoPeticion, String errorMessage) {
        ProcesoActualizacionEjecucion pr = getProcesoActualizacionEjecucionFromCache(uuid);
        if (pr == null) return;
        pr.setNumErrores(pr.getNumErrores()+1);
        ProcesoActualizacionLog log = ProcesoActualizacionLog.builder().codigoPeticion(codigoPeticion).procesoActualizacionEjecucion(pr).estadoLog(2).errorMessage(errorMessage).build();
        pr.addLog(log);
        cache.put(uuid,pr);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public ProcesoActualizacionEjecucion finalizar(String processUUID) {
        ProcesoActualizacionEjecucion cached = getProcesoActualizacionEjecucionFromCache(processUUID);
        if (cached == null) return null;
        cached.setFinProceso(Calendar.getInstance());
        cached.setEstado(cached.getNumErrores()>0? ProcesoActualizacionEjecucion.ESTADO_ERRONEO : ProcesoActualizacionEjecucion.ESTADO_FINALIZADO);
        Optional<ProcesoActualizacionEjecucion> optPr = procesoActualizacionEjecucionRep.findByProcessId(processUUID);
        if (!optPr.isPresent()) return null;
        ProcesoActualizacionEjecucion procesoFinal = optPr.get();
        procesoFinal.setFinProceso(cached.getFinProceso());
        long timeInMillis = procesoFinal.getFinProceso().getTimeInMillis();
        var hourDiff = DateUtils.getHourDiff();
        long oneHourInMillis = 60 * 60 * 1000;
		long newTimeInMillis = timeInMillis + hourDiff * oneHourInMillis;
        Time timeClient = new Time(newTimeInMillis);
        procesoFinal.setHoraFin(timeClient);
        procesoFinal.setEstado(cached.getEstado());
        procesoFinal.setNumProcesadas(cached.getNumProcesadas());
        procesoFinal.setNumErrores(cached.getNumErrores());
        if (procesoFinal.getLogList() == null) {
            procesoFinal.setLogList(new HashSet<>());
        }
        cached.getLogList().forEach(log -> procesoFinal.getLogList().add(log));
        procesoFinal.getLogList().forEach(log -> log.setProcesoActualizacionEjecucion(procesoFinal));
        cached.getLogList().clear();
        procesoActualizacionEjecucionRep.save(procesoFinal);
        procesoActualizacionLogRep.setProcesoEjecucion(procesoFinal, procesoFinal.getProcessId());
        cache.remove(processUUID);
        cached = null;
        return procesoFinal;
    }

    private ProcesoActualizacionEjecucion getProcesoActualizacionEjecucionFromCache(String processUUID) {
        ProcesoActualizacionEjecucion pr;
        if (cache.containsKey(processUUID)) {
            pr = cache.get(processUUID);
        } else {
            Optional<ProcesoActualizacionEjecucion> optPr = procesoActualizacionEjecucionRep.findByProcessId(processUUID);
            if (optPr.isPresent()) {
                pr = optPr.get();
                addToCache(pr);
            } else {
                return null;
            }
        }
        return pr;
    }

    @Override
    @Transactional(readOnly = true)
    public ProcesoActualizacionEjecucion findByProcessId(String processId) {
        if (cache.containsKey(processId) && !cache.get(processId).haTerminado()) {
            return cache.get(processId);
        } else {
            Optional<ProcesoActualizacionEjecucion> optPr = procesoActualizacionEjecucionRep.findByProcessId(processId);
            if (optPr.isPresent()) {
                return optPr.get();
            } else {
                return null;
            }
        }
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<ProcesoActualizacionEjecucion> findByUUIDInstance(String processId){
    	return procesoActualizacionEjecucionRep.findByUUIDInstance(processId);
    }

    @Override
    public Collection<ProcesoActualizacionEjecucion> getActives() {
        return cache.values();
    }


    @Override
    @Transactional(readOnly = true)
    public List<ProcesoActualizacionEjecucion> findScheduled() {
        return procesoActualizacionEjecucionRep.findByEstado(ProcesoActualizacionEjecucion.ESTADO_PLANIFICADO);
    }

}
