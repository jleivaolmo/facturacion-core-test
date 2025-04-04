package com.echevarne.sap.cloud.facturacion.gestionestados;

import java.util.HashMap;
import java.util.Map;

import com.echevarne.sap.cloud.facturacion.exception.AbstractExceptionHandler;
import com.echevarne.sap.cloud.facturacion.model.masterdata.MasDataMotivosEstado;
import com.echevarne.sap.cloud.facturacion.services.MasDataMotivosEstadoService;
import com.echevarne.sap.cloud.facturacion.validations.commons.ValidacionesPetMues;
import com.echevarne.sap.cloud.facturacion.validations.commons.ValidacionesPetMuesItem;
import com.echevarne.sap.cloud.facturacion.validations.commons.ValidacionesSolInd;
import com.echevarne.sap.cloud.facturacion.validations.commons.ValidacionesSolIndItem;

import org.springframework.beans.factory.annotation.Autowired;

public abstract class FacturacionEstado extends AbstractExceptionHandler implements Procesable {

    public static final String ALBARAN = "A";
    public static final String SOLICITUD = "S";
    public static final String PRUEBA = "P";

    @Autowired
    protected MasDataMotivosEstadoService motivosEstadoSrv;

    @Autowired
    ValidacionesPetMues validacionesPetMues;

    @Autowired
    ValidacionesPetMuesItem validacionesPetMuesItem;

    @Autowired
    ValidacionesSolInd validacionesSolInd;

    @Autowired
    ValidacionesSolIndItem validacionesSolIndItem;

    protected Map<MasDataMotivosEstado, String[]> findMotivo(String codigoMotivoEstado) {
        return findMotivo(codigoMotivoEstado, new String[0]);
    }

    protected Map<MasDataMotivosEstado, String[]> findMotivo(String codigoMotivoEstado, String[] args) {
        Map<MasDataMotivosEstado, String[]> motivo = new HashMap<>();
        motivo.put(motivosEstadoSrv.findByCodigoAndActive(codigoMotivoEstado, true).orElse(null), args);
        return motivo;
    }

    public static String getCodigo() {
        return "";
    }

}
