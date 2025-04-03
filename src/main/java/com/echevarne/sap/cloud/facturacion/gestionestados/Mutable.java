package com.echevarne.sap.cloud.facturacion.gestionestados;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.echevarne.sap.cloud.facturacion.model.masterdata.MasDataEstado;
import com.echevarne.sap.cloud.facturacion.model.masterdata.MasDataMotivosEstado;

public interface Mutable<T extends MutableHistory> {
    
    /**
     * 
     * @return
     */
    Optional<T> newInstance(MasDataEstado estado, MasDataMotivosEstado motivo, boolean automatico, boolean afectaImporte);
    
    Optional<T> newInstanceV2(MasDataEstado estado, MasDataMotivosEstado motivo, boolean automatico, boolean afectaImporte, Integer sequenceOrder);

    /**
     * 
     * @return
     */
    Optional<T> getLastEstado();

    /**
     * 
     * @return
     */
	Optional<T> getAnteUltimoEstado();

    /**
     * 
     * @param estadoTofind
     * @return
     */
	Optional<T> getLastEstado(MasDataEstado estadoTofind);

    /**
	 * 
	 * Añadimos el orden correspondiente
	 * @return
	 */
	int getOrder();
	
	Long getId();

    /**
     * 
     * Crea un nuevo estado para la entidad mutable
     * 
     * @return
     */
	T createHistory();

    /**
     * 
     * Añade un nuevo estado para la entidad mutable
     * 
     * @return
     */
    void addHistory(T history);

    /**
     * 
     * Recupera la fecha del estado
     * 
     * @param codeEstado
     * @return
     */
	Date getFechaEstado(String codeEstado);

    /**
     * 
     * Recupera la fecha del estado
     * @return
     */
	List<MutableHistory> obtieneEstados();

    /**
     * Obtiene los argumentos para el mensaje
     *  
     * @return
     */
    String getMessageArgs();

    /**
     * Obtiene el id del mensaje
     *  
     * @return
     */
    String getMessageId();

}
