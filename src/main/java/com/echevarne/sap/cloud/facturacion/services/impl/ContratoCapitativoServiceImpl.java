package com.echevarne.sap.cloud.facturacion.services.impl;

import java.util.Calendar;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import com.echevarne.sap.cloud.facturacion.model.contratos.ContCapitativoInterlocutores;
import com.echevarne.sap.cloud.facturacion.model.contratos.ContCapitativoOficinaVenta;
import com.echevarne.sap.cloud.facturacion.model.contratos.ContCapitativoPolizas;
import com.echevarne.sap.cloud.facturacion.model.contratos.ContCapitativoProvRemitente;
import com.echevarne.sap.cloud.facturacion.model.contratos.ContratoCapitativo;
import com.echevarne.sap.cloud.facturacion.repositories.ContCapitativoInterlocutoresRep;
import com.echevarne.sap.cloud.facturacion.repositories.ContCapitativoOficinaVentaRep;
import com.echevarne.sap.cloud.facturacion.repositories.ContCapitativoPolizasRep;
import com.echevarne.sap.cloud.facturacion.repositories.ContCapitativoProvRemitenteRep;
import com.echevarne.sap.cloud.facturacion.repositories.ContratoCapitativoRep;
import com.echevarne.sap.cloud.facturacion.services.ContratoCapitativoService;
import com.echevarne.sap.cloud.facturacion.util.StringUtils;
import lombok.var;

@Service("contratoCapitativoService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class ContratoCapitativoServiceImpl extends CrudServiceImpl<ContratoCapitativo, Long>
		implements ContratoCapitativoService {

	private final ContratoCapitativoRep contratoCapitativoRep;
	
	@Autowired
	private ContCapitativoInterlocutoresRep contratoInterlocutoresRep;

	@Autowired
	private ContCapitativoOficinaVentaRep contratoOficinaVentasRep;

	@Autowired
	private ContCapitativoPolizasRep contratoPolizasRep;

	@Autowired
	private ContCapitativoProvRemitenteRep contratoProvRemitenteRep;

	@Autowired
	public ContratoCapitativoServiceImpl(ContratoCapitativoRep contratoCapitativoRep) {
		super(contratoCapitativoRep);
		this.contratoCapitativoRep = contratoCapitativoRep;
	}

	@Override
	public List<ContratoCapitativo> findContratoActivo(String organizacionVenta, String codigoCliente, Calendar startDate,
			Calendar endDate) {
		Assert.notNull(startDate, "startDate can't be null");
		Assert.notNull(endDate, "endDate can't be null");

		return contratoCapitativoRep
				.findByOrganizacionVentasAndCodigoClienteAndValidoDesdeLessThanEqualAndValidoHastaGreaterThanEqual(
						organizacionVenta, codigoCliente, startDate, endDate);

	}

	@Override
	public boolean existsContratoCapitativo(Long id) {
		return contratoCapitativoRep.existsById(id);
	}


	@Override
	@Transactional
	public void createAssociatedEntities(Long id) {
		var contrato = contratoCapitativoRep.findById(id).get();
		if (contrato.getInterlocutores().isEmpty()) {
			var contInterlocutores = new ContCapitativoInterlocutores();
			contInterlocutores.setCodigoInterlocutor(StringUtils.ANY);
			contInterlocutores.setContrato(contrato);
			contrato.getInterlocutores().add(contInterlocutores);
			contratoInterlocutoresRep.save(contInterlocutores);
		}

		if (contrato.getOficinasVentaMultiple().isEmpty()) {
			var contOficinaVentas = new ContCapitativoOficinaVenta();
			contOficinaVentas.setCodigoOficinaVenta(StringUtils.ANY);
			contOficinaVentas.setContrato(contrato);
			contrato.getOficinasVentaMultiple().add(contOficinaVentas);
			contratoOficinaVentasRep.save(contOficinaVentas);
		}

		if (contrato.getPolizas().isEmpty()) {
			var contPolizas = new ContCapitativoPolizas();
			contPolizas.setCodigoPoliza(StringUtils.ANY);
			contPolizas.setContrato(contrato);
			contrato.getPolizas().add(contPolizas);
			contratoPolizasRep.save(contPolizas);
		}

		if (contrato.getProvRemitenteMultiple().isEmpty()) {
			var contProvRemitente = new ContCapitativoProvRemitente();
			contProvRemitente.setProvinciaRemitente(StringUtils.ANY);
			contProvRemitente.setContrato(contrato);
			contrato.getProvRemitenteMultiple().add(contProvRemitente);
			contratoProvRemitenteRep.save(contProvRemitente);
		}

	}
}
