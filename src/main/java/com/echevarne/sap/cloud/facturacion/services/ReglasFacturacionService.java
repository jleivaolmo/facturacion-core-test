package com.echevarne.sap.cloud.facturacion.services;

import java.util.Calendar;
import java.util.List;

import com.echevarne.sap.cloud.facturacion.model.AgrReglasFacturacion;
import com.echevarne.sap.cloud.facturacion.model.reglasfacturacion.ReglasFacturacion;
import com.echevarne.sap.cloud.facturacion.model.solicitudindividual.SolIndItems;
import com.echevarne.sap.cloud.facturacion.model.solicitudindividual.SolicitudIndividual;
import com.echevarne.sap.cloud.facturacion.model.solicitudrecibida.PeticionMuestreo;

/**
 * Class for services {@link ReglasFacturacionService}.
 * 
 * <p>. . .</p>
 * <p>Services for the bussiness logic of the Model: ReglasFacturacion</p>
 *
 * @author Germán Laso
 * @version 1.0
 * @since 18/10/2020
 * 
 */
public interface ReglasFacturacionService extends CrudService<ReglasFacturacion, Long> {

    List<ReglasFacturacion> findVigentesByParamsOrDefault(Calendar fecha, String codigoCliente, String organizacionVentas);

    List<ReglasFacturacion> findAll();

    boolean existsReglaFacturacion(Long id);

    boolean aplica(ReglasFacturacion regla, SolicitudIndividual si, PeticionMuestreo pm, SolIndItems item);

    boolean aplicaNew(ReglasFacturacion regla, AgrReglasFacturacion reglaForFind);

    void removeAll();
    
}
