package com.echevarne.sap.cloud.facturacion.gestionestados;

import com.echevarne.sap.cloud.facturacion.model.BasicEntity;
import com.echevarne.sap.cloud.facturacion.model.masterdata.MasDataEstado;

public interface MutableHistory {
    
    BasicEntity getEntity();
    MasDataEstado obtenerEstado();
    void inactiveEstado();
    boolean isActive();
    void setMotivosVars(String ... args);

}
