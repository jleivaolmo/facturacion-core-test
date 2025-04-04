package com.echevarne.sap.cloud.facturacion.transformacion.rules;

import static java.util.Arrays.asList;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import com.echevarne.sap.cloud.facturacion.config.ContextProvider;
import com.echevarne.sap.cloud.facturacion.model.conversion.ConversionOrganizacionVentas;
import com.echevarne.sap.cloud.facturacion.model.conversion.ConversionSociedad;
import com.echevarne.sap.cloud.facturacion.model.priority.PriorityUtil;
import com.echevarne.sap.cloud.facturacion.model.priority.SelectOption;
import com.echevarne.sap.cloud.facturacion.model.priority.SelectOptionItem;
import com.echevarne.sap.cloud.facturacion.model.priority.SelectOptionResult;
import com.echevarne.sap.cloud.facturacion.model.solicitudindividual.SolicitudIndividual;
import com.echevarne.sap.cloud.facturacion.model.solicitudrecibida.PeticionMuestreo;
import com.echevarne.sap.cloud.facturacion.repositories.ConversionOrganizacionVentasRep;
import com.echevarne.sap.cloud.facturacion.repositories.ConversionSociedadRep;

import lombok.AllArgsConstructor;
import lombok.var;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class SalesOrganizationUtil {

    public static final String ANY = "*";
    public static final String CLIENTE = "cliente";
    public static final String CODIGO_DELEGACION = "codigoDelegacion";
    public static final String SECTOR = "sector";
    public static final String PRUEBA = "prueba";
    public static final String ERROR_PETICION = "No se encontro la sociedad";
    public static final String ERROR_PRUEBAS = "Se encontraron distintas sociedades para las pruebas";

    private final ConversionSociedadRep conversionSociedadRep;
    private final ConversionOrganizacionVentasRep conversionOrganizacionVentasRep;

    public String getSalesOrganization(SolicitudIndividual si, PeticionMuestreo pm) {
        var fromPruebas = getFromPruebas(si, pm);
        return fromPruebas.orElseGet(() -> getFromPeticion(si, pm));
    }

    private Optional<String> getFromPruebas(SolicitudIndividual si, PeticionMuestreo pm) {
        // para cada prueba hacer la busqueda
        var sociedades = pm.getPruebas().stream()
                .map(pmi -> getFromPrueba(si, pm, pmi.getCodigoMaterial()))
                .filter(Objects::nonNull).distinct().collect(Collectors.toList());
        if(sociedades.isEmpty())
            return Optional.empty();
        if(sociedades.size() > 1)
            throw new TransformerException(ERROR_PRUEBAS);
        return Optional.of(sociedades.get(0));
    }

    private String getFromPrueba(SolicitudIndividual si, PeticionMuestreo pm, String prueba) {
        var cliente = si.getSoldToParty();
        var codigoDelegacion = si.getSalesOffice();
        var sector = si.getOrganizationDivision();
        var clientes = asList(cliente, ANY);
        var cds = asList(codigoDelegacion, ANY);
        var sectores = asList(sector, ANY);
        var pruebas = asList(prueba, ANY);
        var resultados = conversionSociedadRep
                .findByClienteInAndCodigoDelegacionInAndPruebaInAndSectorIn(clientes, cds, pruebas, sectores);
        if(resultados.isEmpty())
            return null;
        // crear el SelectOption con los valores sin * y buscar el de mayor prioridad
        var so = new SelectOption();
        so.put(CLIENTE, new SelectOptionItem(cliente));
        so.put(CODIGO_DELEGACION, new SelectOptionItem(codigoDelegacion));
        so.put(PRUEBA, new SelectOptionItem(prueba));
        so.put(SECTOR, new SelectOptionItem(sector));
        Optional<SelectOptionResult> resultOpt = PriorityUtil.getFirst(resultados, so);
        if(resultOpt.isPresent()) {
            var conversionSociedad = (ConversionSociedad) resultOpt.get().getEntity();
            return conversionSociedad.getSociedad();
        } else
            return null;
    }

    private String getFromPeticion(SolicitudIndividual si, PeticionMuestreo pm) {
    	var codigoDelegacion = pm.getSolicitud().getCodigoOficinaVentas();
        var sector = si.getOrganizationDivision();
        var cds = asList(codigoDelegacion, ANY);
        var sectores = asList(sector, ANY);
        var resultados = conversionOrganizacionVentasRep.findBySectorInAndCodigoDelegacionIn(sectores, cds);
        if(resultados.isEmpty())
            throw new TransformerException(ERROR_PETICION);
        // crear el SelectOption con los valores sin * y buscar el de mayor prioridad
        var so = new SelectOption();
        so.put(CODIGO_DELEGACION, new SelectOptionItem(codigoDelegacion));
        so.put(SECTOR, new SelectOptionItem(sector));
        Optional<SelectOptionResult> resultOpt = PriorityUtil.getFirst(resultados, so);
        if(!resultOpt.isPresent())
            throw new TransformerException(ERROR_PETICION);
        var cov = (ConversionOrganizacionVentas) resultOpt.get().getEntity();
        return cov.getOrganizacionVentas();
    }

}
