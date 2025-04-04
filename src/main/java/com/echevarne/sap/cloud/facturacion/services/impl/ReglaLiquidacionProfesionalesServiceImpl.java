package com.echevarne.sap.cloud.facturacion.services.impl;

import java.util.Calendar;
import java.util.List;
import com.echevarne.sap.cloud.facturacion.model.liquidaciones.ReglaLiquidacionProfesionales;
import com.echevarne.sap.cloud.facturacion.repositories.ReglaLiquidacionProfesionalesRep;
import com.echevarne.sap.cloud.facturacion.services.ReglaLiquidacionProfesionalesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("reglaLiquidacionProfesionalesSrv")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class ReglaLiquidacionProfesionalesServiceImpl extends CrudServiceImpl<ReglaLiquidacionProfesionales, Long>
		implements ReglaLiquidacionProfesionalesService {

	private final ReglaLiquidacionProfesionalesRep reglaLiquidacionProfesionalesRep;

	@Autowired
	public ReglaLiquidacionProfesionalesServiceImpl(final ReglaLiquidacionProfesionalesRep reglaLiquidacionProfesionalesRep) {
		super(reglaLiquidacionProfesionalesRep);
		this.reglaLiquidacionProfesionalesRep = reglaLiquidacionProfesionalesRep;
	}

	@Override
	public List<ReglaLiquidacionProfesionales> findReglas(String codigoCliente, String oficinaVentas, String codigoProfesional, String codigoCompania,
			String delProductiva, String undProductiva, Boolean esMuestraRemitida, String codigoPrueba, String codigoOrganizacion, String codigoConcepto,
			Integer tipoPeticion, Calendar fechaPeticion) {
		return reglaLiquidacionProfesionalesRep.findReglas(codigoCliente, oficinaVentas, codigoProfesional, codigoCompania, delProductiva, undProductiva,
				esMuestraRemitida, codigoPrueba, codigoOrganizacion, codigoConcepto, tipoPeticion, fechaPeticion);
	}

}
