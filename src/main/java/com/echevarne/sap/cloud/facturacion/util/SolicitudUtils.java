package com.echevarne.sap.cloud.facturacion.util;

import com.echevarne.sap.cloud.facturacion.constants.ConstFacturacion;

public class SolicitudUtils {

    public static boolean isPruebaPerfil(String tipoPosicion) {
        if (tipoPosicion.equals(ConstFacturacion.TIPO_POSICION_PRUEBAPERFIL))
            return true;
        return false;
    }

    public static boolean isPerfil(String tipoPosicion) {
        if (tipoPosicion.equals(ConstFacturacion.TIPO_POSICION_PERFIL))
            return true;
        return false;
    }

    public static boolean isPrueba(String tipoPosicion) {
        if (tipoPosicion.equals(ConstFacturacion.TIPO_POSICION_PRUEBA))
            return true;
        return false;
    }

}
