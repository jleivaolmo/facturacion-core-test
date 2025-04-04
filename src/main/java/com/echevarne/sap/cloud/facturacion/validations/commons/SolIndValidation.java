package com.echevarne.sap.cloud.facturacion.validations.commons;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

import javax.persistence.EntityManager;

import com.echevarne.sap.cloud.facturacion.gestionestados.util.EstadosUtils;
import com.echevarne.sap.cloud.facturacion.model.masterdata.MasDataEstado;
import com.echevarne.sap.cloud.facturacion.model.solicitudindividual.SolicitudIndividual;
import com.echevarne.sap.cloud.facturacion.validations.ValidationResult;

@FunctionalInterface
public interface SolIndValidation extends Function<SolicitudIndividual, ValidationResult> { 
    
    static SolIndValidation hasPeticionNetAmount() {
        return evaluar(si -> si.getTotalNetAmount() == null, "No se ha podido determinar el precio de la petición.");
    }

    static SolIndValidation hasPeticionItems() {
        return evaluar(si -> !(si.getItems() != null && !si.getItems().isEmpty()), "No se han encontrado items en la petición.");
    }

    static SolIndValidation hasAllItemsStatus(MasDataEstado estado) {
        return evaluar(si -> EstadosUtils.aplicaEstadoPadre(estado, si)
        , "Todas las pruebas de la petición estan en facturables.");
    }
    
    static SolIndValidation hasAllItemsStatusV2(MasDataEstado estado, EntityManager entityManager) {
        return evaluar(si -> EstadosUtils.aplicaEstadoPadreV2(estado, si, entityManager)
        , "Todas las pruebas de la petición estan en facturables.");
    }


    static SolIndValidation evaluar(Predicate<SolicitudIndividual> p, String message) {
        return si -> p.test(si) ? ValidationResult.valid() : ValidationResult.invalid(message);
    }

    default Map<String, ValidationResult> applyAll(SolicitudIndividual t, List<SolIndValidation> validations) {
        HashMap<String, ValidationResult> resultados = new HashMap<String, ValidationResult>();
        int i = 0;
        for(SolIndValidation v: validations){
            i++;
            resultados.put("Validation" + i, v.apply(t));
        };
        return resultados;
    }
    
    default Map<String, ValidationResult> applyAllV2(SolicitudIndividual t, List<SolIndValidation> validations) {
        HashMap<String, ValidationResult> resultados = new HashMap<String, ValidationResult>();
        int i = 0;
        for(SolIndValidation v: validations){
            i++;
            resultados.put("Validation" + i, v.apply(t));
        };
        return resultados;
    }

}