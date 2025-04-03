package com.echevarne.sap.cloud.facturacion.util;


import com.echevarne.sap.cloud.facturacion.model.solicitudrecibida.PeticionMuestreoItems;
import com.echevarne.sap.cloud.facturacion.model.solicitudrecibida.SolicitudMuestreo;
import lombok.var;

import java.util.*;
import java.util.stream.Collectors;

public class PerfilesUtils {


    public static List<PeticionMuestreoItems> getPruebasPerfil(PeticionMuestreoItems perfil) {
        if (!perfil.getEsPerfil()) return new ArrayList<>();
        var idItem = perfil.getIdItem();
        return perfil.getPeticion().getPruebas().stream()
                .filter(i -> i.getIdParent() == idItem)
                .collect(Collectors.toList());
    }

    


    public static long countPerfiles(SolicitudMuestreo solicitudMuestreo) {
        return solicitudMuestreo.getPeticion().stream().flatMap(p -> p.getPruebas().stream())
                .filter(p -> p.getEsPerfil()).count();
    }

    public static List<PeticionMuestreoItems> getPerfiles(SolicitudMuestreo solicitudMuestreo) {
        return solicitudMuestreo.getPeticion().stream().flatMap(p -> p.getPruebas().stream())
                .filter(p -> p.getEsPerfil()).collect(Collectors.toList());
    }
}
