package com.echevarne.sap.cloud.facturacion.services.impl;

import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.echevarne.sap.cloud.facturacion.model.AgrReglasFacturacion;
import com.echevarne.sap.cloud.facturacion.model.reglasfacturacion.ReglaFactBase;
import com.echevarne.sap.cloud.facturacion.model.reglasfacturacion.ReglasFacturacion;
import com.echevarne.sap.cloud.facturacion.model.replicated.S4Info;
import com.echevarne.sap.cloud.facturacion.model.solicitudindividual.SolIndItems;
import com.echevarne.sap.cloud.facturacion.model.solicitudindividual.SolicitudIndividual;
import com.echevarne.sap.cloud.facturacion.model.solicitudrecibida.PeticionMuestreo;
import com.echevarne.sap.cloud.facturacion.repositories.ReglasFacturacionRep;
import com.echevarne.sap.cloud.facturacion.services.ReglasFacturacionService;
import com.echevarne.sap.cloud.facturacion.services.S4InfoService;
import com.echevarne.sap.cloud.facturacion.util.StringUtils;

import lombok.var;

@Service("reglasFacturacionSrv")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class ReglasFacturacionServiceImpl extends CrudServiceImpl<ReglasFacturacion, Long>
        implements ReglasFacturacionService {


    private final ReglasFacturacionRep reglasFacturacionRep;

	private final S4InfoService s4InfoSrv;

	@Autowired
	public ReglasFacturacionServiceImpl(ReglasFacturacionRep reglasFacturacionRep, S4InfoService s4InfoSrv) {
		super(reglasFacturacionRep);
		this.reglasFacturacionRep = reglasFacturacionRep;
		this.s4InfoSrv = s4InfoSrv;
	}

    @Override
	@Transactional(readOnly = true)
	public List<ReglasFacturacion> findVigentesByParamsOrDefault(Calendar fechaVigencia, String codigoCliente,
            String organizacionVentas) {
        return reglasFacturacionRep.findVigentesByParamsOrDefault(fechaVigencia, codigoCliente, organizacionVentas);
    }

    @Override
	@Transactional(readOnly = true)
    public List<ReglasFacturacion> findAll() {
        return reglasFacturacionRep.findAll();
    }

	@Override
	public boolean existsReglaFacturacion(Long id) {
		return reglasFacturacionRep.existsById(id);
	}

	@Override
    public void removeAll() {
        reglasFacturacionRep.deleteAll();
    }

    @Override
	@Transactional(readOnly = true)
    public boolean aplica(ReglasFacturacion regla, SolicitudIndividual si, PeticionMuestreo pm, SolIndItems item) {
        var material = item.getMaterial();
		var concepto = item.getPriceReferenceMaterial();
		var remitida = pm.isEsMuestraRemitida();
		var unidadProductiva = item.getUnidadProductiva() != null ? item.getUnidadProductiva() : "";
		var referenciaCliente = pm.getCodigoReferenciaCliente() != null ? pm.getCodigoReferenciaCliente() : "";
		var tipoPeticion = si.getTipoPeticion();
		var codIntCompania = si.getInterlocutorCompania().map(x -> x.getCustomer()).orElse("");
		var intRemitente = si.getInterlocutorRemitente();
		var codIntRemitente = intRemitente.isPresent() ? intRemitente.get().getCustomer() : "";
		var provinciaRemitente = pm.interlocutorRemitente().isPresent()
				? pm.interlocutorRemitente().get().getProvinciaInterlocutor()
				: "";
		var documentoUnico = si.getDocumentoUnico() != null ? si.getDocumentoUnico() : "";
		var codigoPoliza = si.getCodigoPoliza() != null ? si.getCodigoPoliza() : "";
		var codigoOperacion = si.getCodigoOperacion() != null ? si.getCodigoOperacion() : "";
		var tarifa = si.getTarifa() != null ? si.getTarifa() : "";
		var codigoDelegacion = si.getSalesOffice();
		Set<String> especialidades = s4InfoSrv.findByParams(material, si.getSoldToParty(), si.getSalesOrganization(),
                codigoDelegacion, codIntCompania).stream().map(e -> e.getUN_PROD_CLIENTE()).collect(Collectors.toSet());
		especialidades.add("*");

		return this.apply(regla.isIncluyeDelegacion(), regla.getCodigoDelegacion(), codigoDelegacion)
			// && StringUtils.equalsAnyOrValue(this.delegacionEmisora, codigoDelegacion)
				&& this.apply(regla.isIncluyeRemitente(), regla.getInterlocutorRemitente(), codIntRemitente)
				&& this.apply(regla.isIncluyeCompania(), regla.getInterlocutorCompania(), codIntCompania)
				&& this.apply(regla.isIncluyeProvRemitente(), regla.getProvinciaRemitente(), provinciaRemitente != null? provinciaRemitente : "")
				&& this.apply(regla.isIncluyeTipoPeticion(), regla.getTipoPeticion(), tipoPeticion)
				&& StringUtils.equalsAnyOrValue(regla.isIncluyeMuestraRemitida(), regla.getMuestraRemitida(), remitida? "X": StringUtils.EMPTY)
				&& this.apply(regla.isIncluyeTarifa(), regla.getTarifa(), tarifa)
				&& this.apply(regla.isIncluyePrueba(), regla.getCodigoPrueba(), material)
				&& this.apply(regla.isIncluyeConcepto(), regla.getConceptoFacturacion(), concepto)
				&& this.apply(regla.isIncluyeUnidadProductiva(), regla.getUnidadProductiva(), unidadProductiva)
				&& this.apply(regla.isIncluyeEspecialidadCliente(), regla.getEspecialidadCliente(), especialidades)
				&& this.apply(regla.isIncluyeOperacion(), regla.getCodigoOperacion(), codigoOperacion)
				&& this.apply(regla.isIncluyeReferenciaCliente(), regla.getCodigoReferenciaCliente(), referenciaCliente)
				&& this.apply(regla.isIncluyeDocumentoUnico(), regla.getDocumentoUnico(), documentoUnico)
				&& this.apply(regla.isIncluyePoliza(), regla.getCodigoPoliza(), codigoPoliza);

    }

	@Override
	@Transactional(readOnly = true)
    public boolean aplicaNew(ReglasFacturacion regla, AgrReglasFacturacion reglaForFind) {

		boolean aplica = this.apply(regla.isIncluyeDelegacion(), regla.getCodigoDelegacion(), reglaForFind.getCodigoDelegacion())
				&& this.apply(regla.isIncluyeRemitente(), regla.getInterlocutorRemitente(), reglaForFind.getInterlocutorRemitente())
				&& this.apply(regla.isIncluyeCompania(), regla.getInterlocutorCompania(), reglaForFind.getInterlocutorCompania())
				&& this.apply(regla.isIncluyeProvRemitente(), regla.getProvinciaRemitente(), reglaForFind.getProvinciaRemitente() != null? reglaForFind.getProvinciaRemitente() : "")
				&& this.apply(regla.isIncluyeTipoPeticion(), regla.getTipoPeticion(), reglaForFind.getTipoPeticion())
				&& StringUtils.equalsAnyOrValue(regla.isIncluyeMuestraRemitida(), regla.getMuestraRemitida(), reglaForFind.isMuestraRemitida()? "X": StringUtils.EMPTY)
				&& this.apply(regla.isIncluyeTarifa(), regla.getTarifa(), reglaForFind.getTarifa())
				&& this.apply(regla.isIncluyePrueba(), regla.getCodigoPrueba(), reglaForFind.getMaterial())
				&& this.apply(regla.isIncluyeConcepto(), regla.getConceptoFacturacion(), reglaForFind.getConceptoFacturacion())
				&& this.apply(regla.isIncluyeUnidadProductiva(), regla.getUnidadProductiva(), reglaForFind.getUnidadProductiva())
				&& this.apply(regla.isIncluyeOperacion(), regla.getCodigoOperacion(), reglaForFind.getCodigoOperacion())
				&& this.apply(regla.isIncluyeReferenciaCliente(), regla.getCodigoReferenciaCliente(), reglaForFind.getCodigoReferenciaCliente())
				&& this.apply(regla.isIncluyeDocumentoUnico(), regla.getDocumentoUnico(), reglaForFind.getDocumentoUnico())
				&& this.apply(regla.isIncluyePoliza(), regla.getCodigoPoliza(), reglaForFind.getCodigoPoliza());

		if(aplica){
			aplica = applyEspecialidad(regla, reglaForFind);
		}
		return aplica;

	}

	private boolean applyEspecialidad(ReglasFacturacion regla, AgrReglasFacturacion reglaForFind){
		Set<String> especialidades = Collections.emptySet();
		if(regla.isFacturaPorEspecialidadCliente()){
			especialidades = s4InfoSrv.findByParams(reglaForFind.getMaterial(), regla.getCodigoCliente(), regla.getOrganizacionVentas(),
					reglaForFind.getCodigoDelegacion(), reglaForFind.getInterlocutorCompania()).stream().map(S4Info::getUN_PROD_CLIENTE).collect(Collectors.toSet());
			String especialidadesSeparar = String.join("-", especialidades);
			reglaForFind.setEspecialidadCliente(especialidadesSeparar);
		}
		if(!(regla.isIncluyeEspecialidadCliente() && regla.getEspecialidadCliente().stream().anyMatch(ec -> ec.getEspecialidadCliente().equals("*")))){
			if(especialidades.isEmpty()){
				especialidades = s4InfoSrv.findByParams(reglaForFind.getMaterial(), regla.getCodigoCliente(), regla.getOrganizacionVentas(),
						reglaForFind.getCodigoDelegacion(), reglaForFind.getInterlocutorCompania()).stream().map(S4Info::getUN_PROD_CLIENTE).collect(Collectors.toSet());
			}
			especialidades.add("*");
			reglaForFind.setEspecialidadAplicar(especialidades);
			return this.apply(regla.isIncluyeEspecialidadCliente(), regla.getEspecialidadCliente(), reglaForFind.getEspecialidadAplicar());
		}
		return true;
	}

    private boolean apply(boolean incluye, Set<? extends ReglaFactBase> lista, Object value) {
		try {
			if (!incluye) {
				return StringUtils.notEqualsAnyOrValue(lista, value);
			} else {
				return StringUtils.equalsAnyOrValue(lista, value);
			}
		} catch (Exception ex) {
			return false;
		}
	}

	private boolean apply(boolean incluye, Set<? extends ReglaFactBase> lista, Set<String> values) {
		boolean anyMatch = values.stream().anyMatch(value -> apply(true, lista, value));
		return incluye? anyMatch: !anyMatch;
	}

}
