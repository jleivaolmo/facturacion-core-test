package com.echevarne.sap.cloud.facturacion.services;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.echevarne.sap.cloud.facturacion.gestionestados.Transicionable;
import com.echevarne.sap.cloud.facturacion.model.BasicMessagesEntity;
import com.echevarne.sap.cloud.facturacion.model.masterdata.MasDataEstado;
import com.echevarne.sap.cloud.facturacion.model.masterdata.MasDataMotivosEstado;
import com.echevarne.sap.cloud.facturacion.model.solicitudrecibida.PeticionMuestreo;
import com.echevarne.sap.cloud.facturacion.model.solicitudrecibida.PeticionMuestreoItems;
import com.echevarne.sap.cloud.facturacion.model.trazabilidad.Trazabilidad;
import com.echevarne.sap.cloud.facturacion.model.trazabilidad.TrazabilidadSolicitudMessages;

/**
 * Class for services {@link ProcesadorEstadosService}.
 * 
 * <p>
 * Services for the bussiness logic of estados
 * </p>
 *
 */
public interface ProcesadorEstadosService {

        /**
         * 
         * Método que procesa los estados de una entidad
         * 
         * @param <?>
         * @param entity
         * @param messages
         * @return
         */
        <T extends BasicMessagesEntity> Map<MasDataEstado, Set<Transicionable<?>>> procesarEstados(
                        Transicionable<?> entity, Set<T> messages);

        /**
         * 
         * Método generico para setear un estado a una entidad (En base a estructura)
         * 
         * @param <K>
         * @param entity
         * @return
         */
        <T extends BasicMessagesEntity> boolean setEstado(Transicionable<?> entity, MasDataEstado destino,
                        Set<T> messages, MasDataMotivosEstado motivo);

        /**
         * 
         * Método generico para setear un estado a una entidad (En base a estructura)
         * 
         * @param <K>
         * @param entity
         * @return
         */
        <T extends BasicMessagesEntity> boolean setEstado(Transicionable<?> entity, MasDataEstado destino,
                        Set<T> messages, MasDataMotivosEstado motivo, boolean manual, boolean afectaImporte);

        /**
         * 
         * Método generico para setear un estado a una entidad (En base a string) Estado
         * sin motivo y automatico
         * 
         * @param <K>
         * @param entity
         * @return
         */
        <T extends BasicMessagesEntity> boolean setEstado(Transicionable<?> entity, String destino, Set<T> messages);

        /**
         * 
         * Método generico para setear un estado a una entidad (En base a string) Estado
         * automatico
         * 
         * @param <K>
         * @param entity
         * @return
         */
        <T extends BasicMessagesEntity> boolean setEstado(Transicionable<?> entity, String destino, Set<T> messages,
                        MasDataMotivosEstado motivo);
        
        <T extends BasicMessagesEntity> boolean setEstadoV2(Transicionable<?> entity, String destino, Set<T> messages,
                MasDataMotivosEstado motivo);

        /**
         * 
         * Método generico para setear un estado a una entidad (En base a string) Estado
         * con opcion a ser manual o automatico
         * 
         * @param <K>
         * @param entity
         * @return
         */
        <T extends BasicMessagesEntity> boolean setEstado(Transicionable<?> entity, String destino, Set<T> messages,
                        MasDataMotivosEstado motivo, boolean manual, boolean afectaImporte);

        /**
         *
         * @param entity
         * @param destinos
         * @param messages
         * @param motivo
         * @param manual
         * @param <T>
         * @return
         */
        <T extends BasicMessagesEntity> boolean fuerzaTransiciones(Transicionable<?> entity, List<String> destinos, Set<T> messages,
                                                                   MasDataMotivosEstado motivo, boolean manual);
        
        <T extends BasicMessagesEntity> boolean fuerzaTransicionesV2(Transicionable<?> entity, List<String> destinos, Set<T> messages,
                MasDataMotivosEstado motivo, boolean manual);

        boolean bloquearPrueba(PeticionMuestreoItems entity);

        boolean desbloquearPrueba(PeticionMuestreoItems entity, Set<BasicMessagesEntity> messages);

        boolean desbloquearPeticion(PeticionMuestreo entity, Set<BasicMessagesEntity> messages);

        boolean excluirPrueba(Trazabilidad trazabilidad, Set<TrazabilidadSolicitudMessages> messages, boolean manual);
        
        boolean incluirPrueba(Trazabilidad trazabilidad, Set<TrazabilidadSolicitudMessages> messages);

		<T extends BasicMessagesEntity> boolean setEstadoV2(Transicionable<?> entity, String destino, Set<T> messages, MasDataMotivosEstado motivo,
				boolean manual, boolean afectaImporte);
}
