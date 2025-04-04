package com.echevarne.sap.cloud.facturacion.services;

import com.echevarne.sap.cloud.facturacion.model.BasicMessagesEntity;
import com.echevarne.sap.cloud.facturacion.model.facturacion.FacturasGeneradas;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface FacturasGeneradasService extends CrudService<FacturasGeneradas, Long> {

    /**
     *
     * Almacena la factura realizada en la facturación con log de mensajes
     *
     * @param <T>
     * @param generada
     */
    <T extends BasicMessagesEntity> void saveGenerada(FacturasGeneradas generada);


    /**
     * Obtiene factura para una agrupación
     *
     * @param idAgrupacion
     * @return
     */
    List<FacturasGeneradas> getByAgrupacion(Long idAgrupacion);

    /**
     * Obtiene factura mediante orden de ventas
     *
     * @param salesOrder
     * @return
     */
    Optional<FacturasGeneradas> getBySalesOrder(String salesOrder);
    
    /**
     * Obtiene factura para un UUID
     *
     * @param idAgrupacion
     * @return
     */
    Optional<FacturasGeneradas> getByUUID(String UUID);

    /**
     * Actualizamos los documentos recibidos por SAP
     * @param codigoPedido
     * @param codigoFactura
     * @param documentoFinanciero
     */
    void updateDocuments(String codigoPedido, String codigoFactura, String documentoFinanciero, Date fechaFactura, Date fechaVencimiento, String formaPago);
    

    public void updateSalesOrder(String salesOrder, String UUID);

}
