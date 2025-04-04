package com.echevarne.sap.cloud.facturacion.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.echevarne.sap.cloud.facturacion.model.contratos.ContCapitativoOficinaVenta;
import com.echevarne.sap.cloud.facturacion.repositories.ContCapitativoOficinaVentaRep;
import com.echevarne.sap.cloud.facturacion.services.ContCapitativoOficinasVentasService;

/**
 *
 * @author Germán Laso
 * @version 1.0
 *
 */
@Service("contCapitativoOficinasVentasService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class ContCapitativoOficinasVentasServiceImpl extends CrudServiceImpl<ContCapitativoOficinaVenta, Long> implements ContCapitativoOficinasVentasService {

    @Autowired
	public ContCapitativoOficinasVentasServiceImpl(final ContCapitativoOficinaVentaRep contCapitativoOficinaVentaRep) {
		super(contCapitativoOficinaVentaRep);
	}
}
