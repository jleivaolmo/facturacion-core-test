package com.echevarne.sap.cloud.facturacion.services.impl;

import java.util.Calendar;
import java.util.List;
import com.echevarne.sap.cloud.facturacion.model.liquidaciones.ReglaLiquidacionRemitentes;
import com.echevarne.sap.cloud.facturacion.repositories.ReglaLiquidacionRemitentesRep;
import com.echevarne.sap.cloud.facturacion.services.ReglaLiquidacionRemitentesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("reglaLiquidacionRemitentesSrv")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class ReglaLiquidacionRemitentesServiceImpl extends CrudServiceImpl<ReglaLiquidacionRemitentes, Long> implements ReglaLiquidacionRemitentesService {

	private final ReglaLiquidacionRemitentesRep reglaLiquidacionRemitentesRep;

	@Autowired
	public ReglaLiquidacionRemitentesServiceImpl(final ReglaLiquidacionRemitentesRep reglaLiquidacionRemitentesRep) {
		super(reglaLiquidacionRemitentesRep);
		this.reglaLiquidacionRemitentesRep = reglaLiquidacionRemitentesRep;
	}

	@Override
	public List<ReglaLiquidacionRemitentes> findReglas(String codigoCliente, String oficinaVentas, String codigoRemitente, String codigoCompania,
			String delProductiva, String undProductiva, Boolean esMuestraRemitida, String codigoPrueba, String codigoOrganizacion, String codigoConcepto,
			Integer tipoPeticion, Calendar fechaPeticion) {
		return reglaLiquidacionRemitentesRep.findReglas(codigoCliente, oficinaVentas, codigoRemitente, codigoCompania, delProductiva, undProductiva,
				esMuestraRemitida, codigoPrueba, codigoOrganizacion, codigoConcepto, tipoPeticion, fechaPeticion);
	}

}
