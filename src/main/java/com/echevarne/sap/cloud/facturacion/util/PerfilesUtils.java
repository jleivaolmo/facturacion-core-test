package com.echevarne.sap.cloud.facturacion.util;

import com.echevarne.sap.cloud.facturacion.gestionestados.ValidadaAutorizada;

import com.echevarne.sap.cloud.facturacion.model.solicitudrecibida.PetMuesItemEstado;
import com.echevarne.sap.cloud.facturacion.model.solicitudrecibida.PeticionMuestreoItems;
import com.echevarne.sap.cloud.facturacion.model.solicitudrecibida.SolicitudMuestreo;
import lombok.var;

import java.util.*;
import java.util.stream.Collectors;

public class PerfilesUtils {

	public static Date getFechaDeValidacionMasReciente(PeticionMuestreoItems perfil) {
		if (perfil.getEsPerfil()) {
			List<PeticionMuestreoItems> items = getPruebasValidadasPerfil(perfil);
			
			Collections.sort(items, Comparator.comparing((PeticionMuestreoItems x) -> {
			    Optional<PetMuesItemEstado> estadoVA = x.getEstado(ValidadaAutorizada.CODIGO);
			    Optional<PetMuesItemEstado> estadoAU = x.getEstado(ValidadaAutorizada.CODIGO_AU);
			    return estadoVA.map(PetMuesItemEstado::getFechaEstado)
			                   .orElseGet(() -> estadoAU.map(PetMuesItemEstado::getFechaEstado)
			                                            .orElse(new Date()));  
			}).reversed());
			Date response = null;
			response = items.get(0).getFechaEstado(ValidadaAutorizada.CODIGO);
			if (response == null) {
				response = items.get(0).getFechaEstado(ValidadaAutorizada.CODIGO_AU);
			}
			return response;
		}
		return null;
	}

    public static List<PeticionMuestreoItems> getPruebasPerfil(PeticionMuestreoItems perfil) {
        if (!perfil.getEsPerfil()) return new ArrayList<>();
        var idItem = perfil.getIdItem();
        return perfil.getPeticion().getPruebas().stream()
                .filter(i -> i.getIdParent() == idItem)
                .collect(Collectors.toList());
    }

    public static List<PeticionMuestreoItems> getPruebasValidadasPerfil(PeticionMuestreoItems perfil) {
        if (!perfil.getEsPerfil()) return new ArrayList<>();
        var idItem = perfil.getIdItem();
        return perfil.getPeticion().getPruebas().stream()
                .filter(i -> i.getIdParent() == idItem)
                .filter(i -> i.contieneValidada()).collect(Collectors.toList());
    }

    //Ponemos fecha de validac ion al perfil en caso de que no la tenga
    public static void setValidacionPerfil(PeticionMuestreoItems perfil) {
        if (perfil.getEsPerfil() && !perfil.contieneValidada()) {
            List<PeticionMuestreoItems> validadas = getPruebasValidadasPerfil(perfil);
            if (validadas.size()>0 && validadas.size() == getPruebasPerfil(perfil).size()) {
                PetMuesItemEstado pe = new PetMuesItemEstado();
                pe.setCodigoEstado(ValidadaAutorizada.CODIGO);
                pe.setPrueba(perfil);
                pe.setFechaEstado(PerfilesUtils.getFechaDeValidacionMasReciente(perfil));
                perfil.getEstados().add(pe);
            }
        }
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
