package com.echevarne.sap.cloud.facturacion.services.impl;

import java.util.Set;
import com.echevarne.sap.cloud.facturacion.model.replicated.S4Info;
import com.echevarne.sap.cloud.facturacion.repositories.S4InfoRep;
import com.echevarne.sap.cloud.facturacion.services.S4InfoService;
import com.echevarne.sap.cloud.facturacion.util.DateUtils;
import com.echevarne.sap.cloud.facturacion.util.StringUtils;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("S4InfoSrv")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
@AllArgsConstructor
public class S4InfoServiceImpl implements S4InfoService {

    private final S4InfoRep s4InfoRep;

	@Override
	public Set<S4Info> findByParams(String material, String codigoCliente, String organizacionVentas, String delegacion, String compania) {
		try {
			String now = DateUtils.todayStrS4();
			String materialFmt = StringUtils.formatStringValueWithLeadingZeros(material, 18);
			return s4InfoRep.findByParams(materialFmt, codigoCliente, organizacionVentas, delegacion, compania, now);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
}
